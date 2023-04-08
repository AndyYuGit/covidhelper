package com.fyp.covidhelper.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Entity.LatestVisitBuilding;
import com.fyp.covidhelper.Service.LatestVisitBuildingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/latestvisitbuilding")
public class LatestVisitBuildingController {
    @Autowired
    LatestVisitBuildingService latestVisitBuildingService;

    @GetMapping("/")
    @ResponseBody
    @Logging("getting latest visit buildings")
    @Operation(summary = "getting latest visit buildings")
    public List<LatestVisitBuilding> getLatestVisitBuildings(@RequestParam String deviceID,String model,String osVersion,String appVersion) throws JsonProcessingException {
        //latestVisitBuildingService.fetchLatestVisitBuildings();
        //^may delete in formal version because it fetchs periodally
        return latestVisitBuildingService.getLatestVisitBuildingList();
    }
}
