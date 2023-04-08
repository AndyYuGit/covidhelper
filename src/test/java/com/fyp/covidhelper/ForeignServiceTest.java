package com.fyp.covidhelper;

import com.fyp.covidhelper.Service.ForeignApiService;
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
public class ForeignServiceTest {
    @Autowired
    ForeignApiService foreignApiService;

    @Test
    public void testCallForeignApi() throws IOException {
        String str= foreignApiService.callForeignApi("https://randomuser.me/api/");
        assertNotEquals(null,str);
    }

    @Test
    public void testCallForeignApi_connectException() throws IOException {
        Assertions.assertThrows(ConnectException.class,()->{
            String str= foreignApiService.callForeignApi("https://test.com");
        });
    }

    @Test
    public void testCallForeignApi_malformedURLException() throws IOException {
        Assertions.assertThrows(MalformedURLException.class,()->{
            String str= foreignApiService.callForeignApi(null);
        });
    }
}
