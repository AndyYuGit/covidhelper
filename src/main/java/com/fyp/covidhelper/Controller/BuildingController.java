package com.fyp.covidhelper.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;

    @GetMapping("/DistrictsBuildings")
    @ResponseBody
    @Logging("getting buildings from")
    @Operation(summary = "getting buildings from district")
    public List<String> getDistrictsBuildings(@RequestParam String district,String deviceID,String model,String osVersion,String appVersion) throws JsonProcessingException {
        //buildingService.fetchDistrictsBuildings();
        //^may delete in formal version because it fetchs periodally
        return buildingService.getDistrictsBuildings().get(district);
    }

    @GetMapping("/BuildingLatLong")
    @ResponseBody
    @Logging("getting longitude and latitude of")
    @Operation(summary = "getting longitude and latitude of building")
    public List<Double> getLongLatOfBuildings(@RequestParam String district, String building, String deviceID, String model, String osVersion, String appVersion, HttpSession session) throws Exception {
        return buildingService.getLongLatByDistrictAndBuilding(district,building,session);
    }


}
