package com.fyp.covidhelper.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Entity.Keywords;
import com.fyp.covidhelper.Entity.LatestVisitBuilding;
import com.fyp.covidhelper.Repository.KeywordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class KeywordsService{
    @Autowired
    KeywordsRepository keywordsRepository;

    @Autowired
    private RedisService redisService;

    @Logging("schedule:find all keywords")
    @Scheduled(cron = "0 0 23 * * ?")
    public void findAll() throws JsonProcessingException {
        List<Keywords> tempList=new ArrayList<>();
        Iterable<Keywords> iterable = keywordsRepository.findAll();
        if(iterable!=null){
            iterable.forEach((i)->{
               tempList.add(i);
            });
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String s=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tempList);
        redisService.hset("keywordsList","informationList",s);
    }

    public List<String> getInformationByKeywords(String message) throws JsonProcessingException {
        boolean foundKeyword=false;
        List<String> informationList=new ArrayList<>();
        String tempString=redisService.hget("keywordsList","informationList");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Keywords> tempList=objectMapper.readValue(tempString, new TypeReference<ArrayList<Keywords>>(){});
        for(Keywords k:tempList){
            if(message.contains(k.getKeyword())){
                foundKeyword=true;
                informationList.add(k.getInformation());
            }
        }
        if(foundKeyword==false){
            informationList.add("Sorry, no information is found by your query");
        }
        return informationList;
    }
}