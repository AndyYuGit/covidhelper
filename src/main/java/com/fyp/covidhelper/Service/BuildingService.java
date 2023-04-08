package com.fyp.covidhelper.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Entity.Building;
import com.fyp.covidhelper.Entity.LatestVisitBuilding;
import com.fyp.covidhelper.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.SyncFailedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuildingService  {
    //Belows are object for json from foreign apis
    public static class TempBuilding{
        public String getDistrict() {
            return District;
        }

        public String getBuildname() {
            return Buildname;
        }

        public String getLastdate() {
            return Lastdate;
        }

        public String getRelatedcases() {
            return Relatedcases;
        }

        public String District;
        public String Buildname;
        public String Lastdate;
        public String Relatedcases;
    }
    public static class Geometry{
        public String type;

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public List<Double> coordinates;
    }

    public static class Feature{
        public String type;
        public Object properties;
        public Geometry getGeometry() {
            return geometry;
        }

        public Geometry geometry;
        public Object bbox;
    }

    public static class FeatureCollection{
        public List<Feature> getFeatures() {
            return features;
        }

        public String getType() {
            return type;
        }

        public Object getQuery() {
            return query;
        }

        public List<Feature> features;
        public String type;
        public Object query;
    }

    public interface ProjectIdAndDistrictAndBuildings{
        Integer getId();
        String getDistrict();
        String getName();
    }

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private LatestVisitBuildingService latestVisitBuildingService;

    @Autowired
    private NewBuildingService newBuildingService;

    @Autowired
    private ForeignApiService foreignApiService;

    @Autowired
    private RedisService redisService;

    public Map<String, List<String>> getDistrictsBuildings() throws JsonProcessingException {
        String tempString=redisService.hget("districtsBuildings","list");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Map<String, List<String>> tempMap=objectMapper.readValue(tempString, new TypeReference<>() {
        });
        return tempMap;
    }

    //Get the buildings with latest positive cases every day
    @Logging("schedule:get latest positive buildings")
    @Scheduled(cron = "0 0 23 * * ?")
    public void getLatestPositiveBuildings() throws IOException {
        //request data from data.gov.hk
        StringBuilder sb=new StringBuilder();
        sb.append("https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2Fwww.chp.gov.hk%2Ffiles%2Fmisc%2Fbuilding_list_eng.csv%22%2C%22section%22%3A1%2C%22format%22%3A%22json%22%2C%22sorts%22%3A%5B%5B3%2C%22desc%22%5D%5D%2C%22filters%22%3A%5B%5B3%2C%22ct%22%2C%5B%22");
        String month= String.valueOf(LocalDate.now().getMonthValue());
        String year= String.valueOf(LocalDate.now().getYear());
        if(LocalDate.now().getDayOfMonth()<=2){
            month=String.valueOf(LocalDate.now().minusMonths(1).getMonthValue());
            if(LocalDate.now().getMonthValue()==1){
                year=String.valueOf(LocalDate.now().getYear()-1);
            }
        }
        sb.append(month);
        sb.append("%2F");
        sb.append(year);
        sb.append("%22%5D%5D%5D%7D");
        String rtnData= foreignApiService.callForeignApi(sb.toString());
        rtnData=rtnData.replace("Building name","Buildname");
        rtnData=rtnData.replace("Last date of visit of the case(s)","Lastdate");
        rtnData=rtnData.replace("Related cases","Relatedcases");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<TempBuilding> tempBuildings = objectMapper.readValue(rtnData,new TypeReference<ArrayList<TempBuilding>>(){});
        if(tempBuildings.isEmpty()){
            return;
        }
        String firstLastdate=tempBuildings.get(0).getLastdate();
        //clear temporary tables
        latestVisitBuildingService.deleteAllLatestVisitBuildings();
        newBuildingService.deleteAllNewBuildings();
        Integer rowCountOfBuilding=(int)buildingRepository.count();
        for(int i=0;i< tempBuildings.size();i++){
            String tmpLastdate=tempBuildings.get(i).getLastdate();
            if(!firstLastdate.equals(tmpLastdate)){
                //Only get the latest day
                break;
            }
            else{
                //send the information to db
                String district=tempBuildings.get(i).getDistrict();
                String buildname=tempBuildings.get(i).getBuildname();
                String[]strs=tmpLastdate.split("/");
                LocalDate tmpLocalDate=LocalDate.of(Integer.parseInt(strs[2]),Integer.parseInt(strs[1]),Integer.parseInt(strs[0]));
                Integer id;
                if(buildingRepository.existsBuildingByDistrictAndName(district,buildname)>=1){
                    //exist
                    id=buildingRepository.findIdByDistrictAndName(district,buildname);
                    buildingRepository.setLatedate(id,tmpLocalDate);
                    if(latestVisitBuildingService.existsBuildingByDistrictAndName(district,buildname)<1){
                        latestVisitBuildingService.saveLatestVisitBuilding(createBuilding(district,buildname,tmpLocalDate,buildingRepository.findLatitudeById(id),buildingRepository.findLongitudeById(id),i));
                    }
                }
                else{
                    boolean coordinatesChanged=false;
                    //assimilate coordinates for building which its court is already in db
                    List <Double>coordinates=assimilateCoordinates(null,district,buildname,rowCountOfBuilding);
                    //not exist, request coordinates from foreign api
                    if(coordinates==null){
                        coordinates=getCoordinates(district,buildname);
                        coordinatesChanged=true;
                    }
                    id=(int)buildingRepository.count()+1;
                    saveBuilding(createBuilding(district,buildname,tmpLocalDate,coordinates.get(1), coordinates.get(0),id));
                    latestVisitBuildingService.saveLatestVisitBuilding(createBuilding(district,buildname,tmpLocalDate,coordinates.get(1), coordinates.get(0),id));
                    if(coordinatesChanged){
                        newBuildingService.saveNewBuilding(createBuilding(district,buildname,tmpLocalDate,coordinates.get(1), coordinates.get(0),id));
                    }

                }
            }
        }

    }
    public Building createBuilding(String district,String name, LocalDate latedate,Double lat,Double lon,Integer id){
        Building building=new Building();
        building.setDistrict(district);
        building.setName(name);
        building.setLatedate(latedate);
        if(lat!=null &&lon !=null){
            building.setLatitude(lat);
            building.setLongitude(lon);
        }
        building.setId(id);
        return building;
    }

    public void saveBuilding(Building building){
        buildingRepository.save(building);
    }

    public List<Double> getCoordinates(String district, String name) throws IOException{
        StringBuilder sb =new StringBuilder();
        sb.append("https://api.geoapify.com/v1/geocode/search?text=");
        sb.append(name.replaceAll("[^a-zA-Z\\d]", "%20"));
        sb.append("%20");
        sb.append(district.replace(" ","%20"));
        sb.append("&apiKey=c950342db53a4db7a4767f4b90bc2777");
        String rtnData=foreignApiService.callForeignApi(sb.toString());
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        if(rtnData.contains("FeatureCollection")){
            FeatureCollection featureCollection = objectMapper.readValue(rtnData, FeatureCollection.class);
            if(!featureCollection.getFeatures().isEmpty()){
                return featureCollection.getFeatures().get(0).getGeometry().getCoordinates();
            }
        }
        //no coordinate returns by foreign api
        List<Double> rtnNull=new ArrayList<>();
        rtnNull.add(null);
        rtnNull.add(null);
        return rtnNull;
    }

    public List<Double> assimilateCoordinates(List<Double> coordinates, String district, String buildname,Integer rowCountOfBuilding){
        if(buildname.contains(",")){
            String[] tempBuildName=buildname.split(",");
            if(tempBuildName.length>0){
                if(buildingRepository.existsBuildingByDistrictAndNameLike(district,tempBuildName[tempBuildName.length-1])>=1){
                    Page<Integer> idPage=buildingRepository.findIdByDistrictAndNameLike(district,tempBuildName[tempBuildName.length-1],PageRequest.of(0,1));
                    List<Integer> idList=idPage.getContent();
                    if(idList.get(0)<=rowCountOfBuilding){
                        Page<Double> longitudePage=buildingRepository.findLongitudeByDistrictAndNameLike(district,tempBuildName[tempBuildName.length-1],PageRequest.of(0,1));
                        Page<Double> latitudePage=buildingRepository.findLatitudeByDistrictAndNameLike(district,tempBuildName[tempBuildName.length-1],PageRequest.of(0,1));
                        List<Double> longitudeList=longitudePage.getContent();
                        List<Double> latitudeList=latitudePage.getContent();
                        coordinates=new ArrayList<>();
                        coordinates.add(longitudeList.get(0));
                        coordinates.add(latitudeList.get(0));
                    }

                }
            }
        }
        return coordinates;
    }

    @Logging("schedule:fetch districtsBuildings")
    @Scheduled(cron = "0 0 18 * * ?")
    public void fetchDistrictsBuildings() throws JsonProcessingException {
        Map<String,List<String>> tempMap=new HashMap<>();
        List<ProjectIdAndDistrictAndBuildings> tempBuildings=buildingRepository.findByIdAndDistrictAndName();
        tempBuildings.forEach(b -> {
            if(!tempMap.containsKey(b.getDistrict())){
                tempMap.put(b.getDistrict(),new ArrayList<>());
                tempMap.get(b.getDistrict()).add(b.getName());
            }
            tempMap.get(b.getDistrict()).add(b.getName());
        });
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String s=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tempMap);
        redisService.hset("districtsBuildings","list",s);
    }

    public List<Double> getLongLatByDistrictAndBuilding(String district, String building, HttpSession session) throws Exception {
        List<Double>coordinates=new ArrayList<>();
        Double longitude= getLongOfBuildingFromSessionOrRedis(session,district,building);
        Double latitude= getLatOfBuildingFromSessionOrRedis(session,district,building);
        if(longitude==null||latitude==null){
            longitude=buildingRepository.findLongitudeByDistrictAndName(district,building);
            latitude=buildingRepository.findLatitudeByDistrictAndName(district,building);
            session.setAttribute(district+"_"+building+"_longitude",longitude);
            session.setAttribute(district+"_"+building+"_latitude",latitude);
            putLongOfBuilding(district,building,longitude);
            putLatOfBuilding(district,building,latitude);
        }
        coordinates.add(longitude);
        coordinates.add(latitude);
        return coordinates;
    }

    public void putLongOfBuilding(String district, String building,Double longitude) throws Exception {
        redisService.hset(district+"_"+building, "long", String.valueOf(longitude));
    }


    public void putLatOfBuilding(String district, String building,Double latitude) throws Exception {
        redisService.hset(district+"_"+building, "lat", String.valueOf(latitude));
    }

    public Double getLongOfBuildingFromSessionOrRedis(HttpSession session, String district, String building) throws Exception {
        Object tempLong=session.getAttribute(district+"_"+building+"_longitude");
        String longitude=null;
        if(tempLong!=null){
            longitude= String.valueOf(tempLong);
        }
        if (longitude != null) {
            return Double.valueOf(longitude);
        }
        longitude = redisService.hget(district+"_"+building, "long");
        if (longitude != null) {
            return Double.valueOf(longitude);
        }
        return null;
    }

    public Double getLatOfBuildingFromSessionOrRedis(HttpSession session, String district, String building) throws Exception {
        Object tempLat=session.getAttribute(district+"_"+building+"_latitude");
        String latitude= null;
        if(tempLat!=null){
            latitude=String.valueOf(tempLat);
        }
        if (latitude != null) {
            return Double.valueOf(latitude);
        }
        latitude = redisService.hget(district+"_"+building, "lat");
        if (latitude != null) {
            return Double.valueOf(latitude);
        }
        return null;
    }

}

