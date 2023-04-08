package com.fyp.covidhelper;

import com.fyp.covidhelper.Entity.Building;
import com.fyp.covidhelper.Service.BuildingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BuildingServiceTest {
    @Autowired
    BuildingService buildingService;

    HttpSession session;

    @BeforeEach
    public void setup(){
        session=new MockHttpSession();
    }

    @Test
    public void testCreateBuilding_withLongLat(){
        String district="district";
        String name="name";
        LocalDate latedate=LocalDate.now();
        Double latitude=1.1;
        Double longitude=1.1;
        Integer id=1;
        Building building=buildingService.createBuilding(district,name,latedate,latitude,longitude,id);
        assertAll(
                ()->assertEquals(district,building.getDistrict()),
                ()->assertEquals(name,building.getName()),
                ()->assertEquals(latedate,building.getLatedate()),
                ()->assertEquals(latitude,building.getLatitude()),
                ()->assertEquals(longitude,building.getLongitude()),
                ()->assertEquals(id,building.getId())
        );
    }

    @Test
    public void testCreateBuilding_withoutLongLat(){
        String district="district";
        String name="name";
        LocalDate latedate=LocalDate.now();
        Integer id=1;
        Building building=buildingService.createBuilding(district,name,latedate,null,null,id);
        assertAll(
                ()->assertEquals(district,building.getDistrict()),
                ()->assertEquals(name,building.getName()),
                ()->assertEquals(latedate,building.getLatedate()),
                ()->assertEquals(null,building.getLatitude()),
                ()->assertEquals(null,building.getLongitude()),
                ()->assertEquals(id,building.getId())
        );
    }

    @Test
    public void testGetCoordinates(){
        assertDoesNotThrow(()->{
            List<Double> list=buildingService.getCoordinates("Test & Run","@#$vgrwgerw4f~ frber}+*7||'");
        });
    }

    @Test
    public void testAssimilateCoordinates(){
        List<Double> list=buildingService.assimilateCoordinates(null,"Central & Western","Test,Centenary Mansion",30000);
        List<Double> finalList = list;
        assertAll(
                ()->assertEquals(114.1236107, finalList.get(0)),
                ()->assertEquals(22.2819173,finalList.get(1))
        );
    }

    @Test
    public void testAssimilateCoordinates_null(){
        List<Double> list=buildingService.assimilateCoordinates(null,"Test","Test",30000);
        assertEquals(null,list);
    }

    @Test
    public void testGetLongOfBuildingFromSessionOrRedis_withSessionInfo() throws Exception {
        session.setAttribute("district_building_longitude","1.1");
        Double longitude= buildingService.getLongOfBuildingFromSessionOrRedis(session,"district","building");
        assertEquals(1.1,longitude);
    }

    @Test
    public void testGetLongOfBuildingFromSessionOrRedis_withRedisInfo() throws Exception {
        Double longitude= buildingService.getLongOfBuildingFromSessionOrRedis(session,"district","building");
        assertEquals(1.1,longitude);
    }

    @Test
    public void testGetLongOfBuildingFromSessionOrRedis_numberFormatException() throws Exception {
        Assertions.assertThrows(NumberFormatException.class,()->{
            Double longitude= buildingService.getLongOfBuildingFromSessionOrRedis(session,"test","test");
        });

    }

    @Test
    public void testGetLatOfBuildingFromSessionOrRedis_withSessionInfo() throws Exception {
        session.setAttribute("district_building_latitude","2.2");
        Double longitude= buildingService.getLatOfBuildingFromSessionOrRedis(session,"district","building");
        assertEquals(2.2,longitude);
    }

    @Test
    public void testGetLatOfBuildingFromSessionOrRedis_withRedisInfo() throws Exception {
        Double longitude= buildingService.getLatOfBuildingFromSessionOrRedis(session,"district","building");
        assertEquals(2.2,longitude);
    }

    @Test
    public void testGetLatOfBuildingFromSessionOrRedis_numberFormatException() throws Exception {
        Assertions.assertThrows(NumberFormatException.class,()->{
            Double latitude= buildingService.getLatOfBuildingFromSessionOrRedis(session,"test","test");
        });
    }

    @Test
    public void testGetLongLatByDistrictAndBuilding_withSessionInfo() throws Exception {
        session.setAttribute("district_building_longitude","1.1");
        session.setAttribute("district_building_latitude","2.2");
        List<Double> result= buildingService.getLongLatByDistrictAndBuilding("district","building",session);
        assertAll(
                ()->assertEquals(1.1,result.get(0)),
                ()->assertEquals(2.2,result.get(1))
        );
    }

    @Test
    public void testGetLongLatByDistrictAndBuilding_withRedisInfo() throws Exception {
        List<Double> result= buildingService.getLongLatByDistrictAndBuilding("district","building",session);
        assertAll(
                ()->assertEquals(1.1,result.get(0)),
                ()->assertEquals(2.2,result.get(1))
        );
    }

    @Test
    public void testGetLongLatByDistrictAndBuilding_numberFormatException() throws Exception {
        Assertions.assertThrows(NumberFormatException.class,()->{
            List<Double> result= buildingService.getLongLatByDistrictAndBuilding("test","test",session);
        });
    }


}
