package com.fyp.covidhelper.Service;

import com.fyp.covidhelper.Entity.Buildings;
import com.fyp.covidhelper.Repository.BuildingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuildingsService {
    @Autowired
    private BuildingsRepository buildingsRepository;

    public void createBuilding(String district, String building, LocalDate latedate){
        Building entityBuilding= new Building();
        entityBuilding.district=district;
        entityBuilding.building=building;
        entityBuilding.latedate=latedate;
    }

    public

    public static class Building{
        public String district;
        public String building;
        public LocalDate latedate;
    }
}
