package com.fyp.covidhelper.Controller;

import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Entity.Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fyp.covidhelper.Service.AdminsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {

    @Autowired
    AdminsService adminsService;

    @GetMapping("/getAdminsEmailPhoneNum")
    @ResponseBody
    @Logging("getting information in message:")
    public List<String> getAdminsEmailPhoneNum(Integer id){
        return adminsService.getAdminsEmailPhoneNum(id);
    }
}
