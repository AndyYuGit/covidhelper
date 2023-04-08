package com.fyp.covidhelper;

import com.fyp.covidhelper.Service.LatestVisitBuildingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LatestVisitBuildingServiceTest {
    @Autowired
    LatestVisitBuildingService latestVisitBuildingService;

    @Test
    public void testExistsBuildingByDistrictAndName_exist(){
        int result= latestVisitBuildingService.existsBuildingByDistrictAndName("Central & Western","Cartwright Gardens");
        assertEquals(1,result);
    }

    @Test
    public void testExistsBuildingByDistrictAndName_notExist(){
        int result= latestVisitBuildingService.existsBuildingByDistrictAndName("Central & Western","Test");
        assertEquals(0,result);
    }

}
