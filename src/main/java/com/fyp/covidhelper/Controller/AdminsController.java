package com.fyp.covidhelper.Controller;

import com.fyp.covidhelper.Annotation.Logging;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fyp.covidhelper.Service.AdminsService;

import javax.servlet.http.HttpSession;;
import java.util.List;

@RestController
@RequestMapping(path = "/admins")
public class AdminsController {

    @Autowired
    AdminsService adminsService;



    @GetMapping("/AdminEmailPhoneNum")
    @Operation(summary = "getting admin's email and phone num with id")
    @ResponseBody
    @Logging("getting admin's email and phone num with id")
    public List<String> getAdminEmailPhoneNum(@RequestParam  Integer id,String deviceID,String model,String osVersion,String appVersion,HttpSession session) throws Exception {
        return adminsService.getAdminEmailPhoneNum(id,session);
    }
}
