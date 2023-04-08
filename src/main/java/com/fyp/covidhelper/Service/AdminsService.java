package com.fyp.covidhelper.Service;

import com.fyp.covidhelper.Entity.Admins;
import com.fyp.covidhelper.Repository.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminsService {
    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    RedisService redisService;

    public List<String> getAdminEmailPhoneNum(Integer id,HttpSession session) throws Exception {
        String adminEmail=getAdminEmailFromSessionOrRedis(session,id);
        String adminPhoneNum=getAdminPhoneNumFromSessionOrRedis(session, id);
        List<String> tempList=new ArrayList<>();
        if(adminEmail!=null&&adminPhoneNum!=null){
            //found in redis or session
            tempList.add(adminEmail);
            tempList.add(adminPhoneNum);
        }
        else{
            //fetch from db
            Optional<Admins> optional=adminsRepository.findById(id);
            if(!optional.isEmpty()){
                Admins admins=optional.get();
                tempList.add(admins.getEmail());
                tempList.add(admins.getPhone_num().toString());
                session.setAttribute("getAdminEmailPhoneNum_id"+id.toString()+"_email",tempList.get(0));
                session.setAttribute("getAdminEmailPhoneNum_id"+id.toString()+"_phoneNum",tempList.get(1));
                putAdminEmailIntoRedis(tempList.get(0),id);
                putAdminPhoneNumIntoRedis(tempList.get(1), id);
            }
        }
        return tempList;
    }

    public void putAdminEmailIntoRedis(String email, Integer id) throws Exception {
        redisService.hset("getAdminEmailPhoneNum_id"+id.toString(), "email", email);
    }


    public void putAdminPhoneNumIntoRedis(String phoneNum, Integer id) throws Exception {
        redisService.hset("getAdminEmailPhoneNum_id"+id.toString(), "phone_num", phoneNum);
    }

    public String getAdminEmailFromSessionOrRedis(HttpSession session, Integer id) throws Exception {
        String email = (String) session.getAttribute("getAdminEmailPhoneNum_id"+id.toString()+"_email");
        if (email != null) {
            return email;
        }
        email = redisService.hget("getAdminEmailPhoneNum_id"+id, "email");
        if (email != null) {
            return email;
        }
        return null;
    }

    public String getAdminPhoneNumFromSessionOrRedis(HttpSession session, Integer id) throws Exception {
        String phoneNum = (String) session.getAttribute("getAdminEmailPhoneNum_id"+id.toString()+"_phoneNum");
        if (phoneNum != null) {
            return phoneNum;

        }
        phoneNum = redisService.hget("getAdminEmailPhoneNum_id"+id, "phone_num");
        if (phoneNum != null) {
            return phoneNum;
        }
        return null;
    }


}
