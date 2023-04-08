package com.fyp.covidhelper;

import com.fyp.covidhelper.Service.AdminsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminsServiceTests {

    @Autowired
    AdminsService adminsService;

    HttpSession session;

    @BeforeEach
    public void setup(){
        session=new MockHttpSession();
    }

    @Test
    public void testGetAdminEmailFromSessionOrRedis_withSessionInfo() throws Exception {
        session.setAttribute("getAdminEmailPhoneNum_id0_email","test@covidhelper.com");
        String result= adminsService.getAdminEmailFromSessionOrRedis(session,0);
        assertEquals("test@covidhelper.com",result);
    }

    @Test
    public void testGetAdminEmailFromSessionOrRedis_withRedisInfo() throws Exception {
        String result= adminsService.getAdminEmailFromSessionOrRedis(session,0);
        assertEquals("test@covidhelper.com",result);
    }

    @Test
    public void testGetAdminEmailFromSessionOrRedis_null() throws Exception {
        String result= adminsService.getAdminEmailFromSessionOrRedis(session,-1);
        assertEquals(null,result);
    }

    @Test
    public void testGetAdminPhoneNumFromSessionOrRedis_withSessionInfo() throws Exception {
        session.setAttribute("getAdminEmailPhoneNum_id0_phoneNum","12345678");
        String result= adminsService.getAdminPhoneNumFromSessionOrRedis(session,0);
        assertEquals("12345678",result);
    }

    @Test
    public void testGetAdminPhoneNumFromSessionOrRedis_withRedisInfo() throws Exception {
        String result= adminsService.getAdminPhoneNumFromSessionOrRedis(session,0);
        assertEquals("12345678",result);
    }

    @Test
    public void testGetAdminPhoneNumFromSessionOrRedis_null() throws Exception {
        String result= adminsService.getAdminPhoneNumFromSessionOrRedis(session,-1);
        assertEquals(null,result);
    }

    @Test
    public void testGetAdminEmailPhoneNum_withSessionInfo() throws Exception {
        session.setAttribute("getAdminEmailPhoneNum_id0_email","test@covidhelper.com");
        session.setAttribute("getAdminEmailPhoneNum_id0_phoneNum","12345678");
        List<String> result= adminsService.getAdminEmailPhoneNum(0,session);
        assertAll(
                ()->assertEquals("test@covidhelper.com",result.get(0)),
                ()->assertEquals("12345678",result.get(1))
        );
    }

    @Test
    public void testGetAdminEmailPhoneNum_withRedisInfo() throws Exception {
        List<String> result= adminsService.getAdminEmailPhoneNum(0,session);
        assertAll(
                ()->assertEquals("test@covidhelper.com",result.get(0)),
                ()->assertEquals("12345678",result.get(1))
        );
    }

    @Test
    public void testGetAdminEmailPhoneNum_noSuchElementException() throws Exception {
        Assertions.assertThrows(NoSuchElementException.class,()->{
            List<String> result= adminsService.getAdminEmailPhoneNum(99999999,session);
        });
    }

}
