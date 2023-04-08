package com.fyp.covidhelper.Service;

import com.fyp.covidhelper.Entity.Building;
import com.fyp.covidhelper.Entity.BuildingMappedSuperClass;
import com.fyp.covidhelper.Entity.NewBuilding;
import com.fyp.covidhelper.Repository.NewBuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class NewBuildingService{
    @Autowired
    NewBuildingRepository newBuildingRepository;

    public void deleteAllNewBuildings(){
        newBuildingRepository.deleteAll();
    }


    public void saveNewBuilding(BuildingMappedSuperClass building){
        NewBuilding newBuilding=new NewBuilding(building);
        newBuildingRepository.save(newBuilding);
    }
}
