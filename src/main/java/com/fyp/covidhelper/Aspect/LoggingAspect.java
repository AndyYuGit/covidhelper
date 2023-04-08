package com.fyp.covidhelper.Aspect;

import com.fyp.covidhelper.Annotation.Logging;
import com.fyp.covidhelper.Entity.Building;
import com.fyp.covidhelper.Entity.BuildingMappedSuperClass;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Aspect
@Component
@Transactional(rollbackFor = Exception.class)
public class LoggingAspect {
    public void loggingAspect(Logging logging,String aspectFor,String deviceID,String model, String osVersion,String appVersion){
        String name = logging.value();
        final Logger logger = LoggerFactory.getLogger(getClass());
        try {
            if(aspectFor.contains("fail")){
                logger.error(name+" "+aspectFor,deviceID,model,osVersion,appVersion);
            }
            else{
                logger.info(name+" "+aspectFor,deviceID,model,osVersion,appVersion);
            }
        } catch (Throwable t){
            logger.error(t.toString());
        }
    }

    //BuildingController.getDistrictsBuildings
    @Before("execution(* com.fyp.covidhelper.Controller.BuildingController.getDistrictsBuildings(..))&&@annotation(logging)")
    public void getDistrictsBuildingsBefore(JoinPoint jp, Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"start",(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Controller.BuildingController.getDistrictsBuildings(..))&&@annotation(logging)")
    public void getDistrictsBuildingsAfterReturning(JoinPoint jp,Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"completed",(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Controller.BuildingController.getDistrictsBuildings(..))&&@annotation(logging)",throwing="t")
    public void getDistrictsBuildingsAfterThrowing(JoinPoint jp,Logging logging, Throwable t) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"fail "+t,(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }


    //BuildingController.getLongLatOfBuildings
    @Before("execution(* com.fyp.covidhelper.Controller.BuildingController.getLongLatOfBuildings(..))&&@annotation(logging)")
    public void getLongLatOfBuildingsBefore(JoinPoint jp, Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+args[1]+" "+"start",(String)args[2],(String)args[3],(String)args[4],(String)args[5]);
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Controller.BuildingController.getLongLatOfBuildings(..))&&@annotation(logging)")
    public void getLongLatOfBuildingsAfterReturning(JoinPoint jp,Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+args[1]+" "+"completed",(String)args[2],(String)args[3],(String)args[4],(String)args[5]);
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Controller.BuildingController.getLongLatOfBuildings(..))&&@annotation(logging)",throwing="t")
    public void getLongLatOfBuildingsAfterThrowing(JoinPoint jp,Logging logging, Throwable t) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+args[1]+" "+"fail "+t,(String)args[2],(String)args[3],(String)args[4],(String)args[5]);
    }


    //KeywordsController.getInformation
    @Before("execution(* com.fyp.covidhelper.Controller.KeywordsController.getInformation(..))&&@annotation(logging)")
    public void getInformationBefore(JoinPoint jp, Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"start",(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Controller.KeywordsController.getInformation(..))&&@annotation(logging)")
    public void getInformationAfterReturning(JoinPoint jp,Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"completed",(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Controller.KeywordsController.getInformation(..))&&@annotation(logging)",throwing="t")
    public void getInformationAfterThrowing(JoinPoint jp,Logging logging, Throwable t) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"fail "+t,(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }


    //AdminsController.getAdminsEmailPhoneNum
    @Before("execution(* com.fyp.covidhelper.Controller.AdminsController.getAdminEmailPhoneNum(..))&&@annotation(logging)")
    public void getAdminsEmailPhoneNumBefore(JoinPoint jp, Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"start",(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Controller.AdminsController.getAdminEmailPhoneNum(..))&&@annotation(logging)")
    public void getAdminsEmailPhoneNumAfterReturning(JoinPoint jp,Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"completed",(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Controller.AdminsController.getAdminEmailPhoneNum(..))&&@annotation(logging)",throwing="t")
    public void getAdminsEmailPhoneNumAfterThrowing(JoinPoint jp,Logging logging, Throwable t) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,args[0]+" "+"fail "+t,(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }


    //LatestVisitBuildingController.getLatestVisitBuildings
    @Before("execution(* com.fyp.covidhelper.Controller.LatestVisitBuildingController.getLatestVisitBuildings(..))&&@annotation(logging)")
    public void getLatestVisitBuildingsBefore(JoinPoint jp,Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,"start",(String)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Controller.LatestVisitBuildingController.getLatestVisitBuildings(..))&&@annotation(logging)")
    public void getLatestVisitBuildingsAfterReturning(JoinPoint jp,Logging logging) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,"completed",(String)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Controller.LatestVisitBuildingController.getLatestVisitBuildings(..))&&@annotation(logging)",throwing="t")
    public void getLatestVisitBuildingsAfterThrowing(JoinPoint jp,Logging logging, Throwable t) throws Throwable {
        Object[] args = jp.getArgs();
        loggingAspect(logging,"fail "+t,(String)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }


    //BuildingService.getLatestPositiveBuildings
    @Before("execution(* com.fyp.covidhelper.Service.BuildingService.getLatestPositiveBuildings(..))&&@annotation(logging)")
    public void getLatestPositiveBuildingsBefore(Logging logging) throws Throwable {
        loggingAspect(logging,"start","","","","");
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Service.BuildingService.getLatestPositiveBuildings(..))&&@annotation(logging)")
    public void getLatestPositiveBuildingsAfterReturning(Logging logging) throws Throwable {
        loggingAspect(logging,"completed","","","","");
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Service.BuildingService.getLatestPositiveBuildings(..))&&@annotation(logging)",throwing="t")
    public void getLatestPositiveBuildingsAfterThrowing(Logging logging, Throwable t) throws Throwable {
        loggingAspect(logging,"fail "+t,"","","","");
    }

    //BuildingService.fetchDistrictsBuildings
    @Before("execution(* com.fyp.covidhelper.Service.BuildingService.fetchDistrictsBuildings(..))&&@annotation(logging)")
    public void fetchDistrictsBuildingsBefore(Logging logging) throws Throwable {
        loggingAspect(logging,"start","","","","");
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Service.BuildingService.fetchDistrictsBuildings(..))&&@annotation(logging)")
    public void fetchDistrictsBuildingsAfterReturning(Logging logging) throws Throwable {
        loggingAspect(logging,"completed","","","","");
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Service.BuildingService.fetchDistrictsBuildings(..))&&@annotation(logging)",throwing="t")
    public void fetchDistrictsBuildingsAfterThrowing(Logging logging, Throwable t) throws Throwable {
        loggingAspect(logging,"fail "+t,"","","","");
    }


    //KeywordsService.findAll
    @Before("execution(* com.fyp.covidhelper.Service.KeywordsService.findAll(..))&&@annotation(logging)")
    public void findAllBefore(Logging logging) throws Throwable {
        loggingAspect(logging,"start","","","","");
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Service.KeywordsService.findAll(..))&&@annotation(logging)")
    public void findAllAfterReturning(Logging logging) throws Throwable {
        loggingAspect(logging,"completed","","","","");
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Service.KeywordsService.findAll(..))&&@annotation(logging)",throwing="t")
    public void findAllAfterThrowing(Logging logging, Throwable t) throws Throwable {
        loggingAspect(logging,"fail "+t,"","","","");
    }


    //LatestVisitBuildingService.fetchLatestVisitBuildings
    @Before("execution(* com.fyp.covidhelper.Service.LatestVisitBuildingService.fetchLatestVisitBuildings(..))&&@annotation(logging)")
    public void fetchLatestVisitBuildingsBefore(Logging logging) throws Throwable {
        loggingAspect(logging,"start","","","","");
    }

    @AfterReturning(pointcut="execution(* com.fyp.covidhelper.Service.LatestVisitBuildingService.fetchLatestVisitBuildings(..))&&@annotation(logging)")
    public void fetchLatestVisitBuildingsAfterReturning(Logging logging) throws Throwable {
        loggingAspect(logging,"completed","","","","");
    }

    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Service.LatestVisitBuildingService.fetchLatestVisitBuildings(..))&&@annotation(logging)",throwing="t")
    public void fetchLatestVisitBuildingsAfterThrowing(Logging logging, Throwable t) throws Throwable {
        loggingAspect(logging,"fail "+t,"","","","");
    }


    //RedisService.executeSync
    @AfterThrowing(pointcut="execution(* com.fyp.covidhelper.Service.RedisService.executeSync(..))&&@annotation(logging)",throwing="t")
    public void executeSyncAfterThrowing(Logging logging, Throwable t) throws Throwable {
        loggingAspect(logging,"fail "+t,"","","","");
    }



}