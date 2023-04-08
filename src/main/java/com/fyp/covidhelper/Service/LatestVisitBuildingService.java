package com.fyp.covidhelper.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Entity.Building;
import com.fyp.covidhelper.Entity.BuildingMappedSuperClass;
import com.fyp.covidhelper.Entity.LatestVisitBuilding;
import com.fyp.covidhelper.Repository.LatestVisitBuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class LatestVisitBuildingService {
    @Autowired
    LatestVisitBuildingRepository latestVisitBuildingRepository;

    @Autowired
    private RedisService redisService;

    public List<LatestVisitBuilding> getLatestVisitBuildingList() throws JsonProcessingException {
        String tempString=redisService.hget("latestVisitBuilding","today");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<LatestVisitBuilding> tempList=objectMapper.readValue(tempString, new TypeReference<ArrayList<LatestVisitBuilding>>(){});
        return tempList;
    }

    public void deleteAllLatestVisitBuildings(){
        latestVisitBuildingRepository.deleteAll();
    }


    public void saveLatestVisitBuilding(BuildingMappedSuperClass building){
        LatestVisitBuilding latestVisitBuilding=new LatestVisitBuilding(building);
        latestVisitBuildingRepository.save(latestVisitBuilding);
    }

    @Logging("schedule:fetch latest visit buildings schedule")
    @Scheduled(cron = "0 0 23 * * ?")
    public void fetchLatestVisitBuildings() throws JsonProcessingException {
        List<LatestVisitBuilding> tempList=new ArrayList<>();
        Iterable<LatestVisitBuilding> iterable = latestVisitBuildingRepository.findAll();
        if(iterable!=null){
            iterable.forEach((i)->{
                tempList.add(i);
            });
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String s=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tempList);
        redisService.hset("latestVisitBuilding","today",s);
    }
    public int existsBuildingByDistrictAndName(String district, String name){
        return latestVisitBuildingRepository.existsBuildingByDistrictAndName(district,name);
    }

}
