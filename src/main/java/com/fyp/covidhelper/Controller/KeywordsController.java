package com.fyp.covidhelper.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Service.KeywordsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/keywords")
public class KeywordsController {
    @Autowired
    KeywordsService keywordsService;

    @GetMapping("/Information")
    @ResponseBody
    @Logging("getting information in message:")
    @Operation(summary = "getting information in message")
    public List<String> getInformation(@RequestParam  String message,String deviceID,String model,String osVersion,String appVersion) throws JsonProcessingException {
        //keywordsService.findAll();
        //^may delete in formal version because it fetchs periodally
        return keywordsService.getInformationByKeywords(message.toLowerCase());
    }
}
