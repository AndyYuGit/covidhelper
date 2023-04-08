package com.fyp.covidhelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fyp.covidhelper.Service.KeywordsService;
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
public class KeywordsServiceTest {

    @Autowired
    KeywordsService keywordsService;

    @BeforeEach
    public void setup(){

    }

    @Test
    public void testGetInformationByKeywords_Single() throws JsonProcessingException {
        List<String> list=keywordsService.getInformationByKeywords("government");
        assertAll(
                ()->{
                    assertEquals("To find latest government news release, you can visit the press release: https://www.dh.gov.hk/english/press/press.html ",list.get(0));
                }
        );
    }

    @Test
    public void testGetInformationByKeywords_Multiple() throws JsonProcessingException {
        List<String> list=keywordsService.getInformationByKeywords("government vaccination");
        assertAll(
                ()->{
                    assertEquals("To find latest government news release, you can visit the press release: https://www.dh.gov.hk/english/press/press.html ",list.get(0));
                },
                ()->{
                    assertEquals("You can receive vaccination from venues throughout the 18 districts, you can find the locations of the venues from Vaccination in https://chp-dashboard.geodata.gov.hk/covid-19/en.html ",list.get(1));
                }
        );
    }

    @Test
    public void testGetInformationByKeywords_notFound() throws JsonProcessingException {
        List<String> list=keywordsService.getInformationByKeywords("test");
        assertAll(
                ()->{
                    assertEquals("Sorry, no information is found by your query",list.get(0));
                }
        );
    }


}
