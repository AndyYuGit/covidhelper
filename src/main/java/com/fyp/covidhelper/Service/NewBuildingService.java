package com.fyp.covidhelper.Service;

import com.fyp.covidhelper.Entity.LatestVisit;
import com.fyp.covidhelper.Repository.LatestVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class LatestVisitService {
    @Autowired
    LatestVisitRepository latestVisitRepository;

    public void deleteAllLatestVisit(){
        latestVisitRepository.deleteAll();
    }


    public void saveLatestVisit(String district, String building, Integer id, Double lat, Double lon){
        LatestVisit latestVisit=new LatestVisit();
        latestVisit.setDistrict(district);
        latestVisit.setBuilding(building);
        latestVisit.setId(id);
        if(lat!=null &&lon !=null){
            latestVisit.setLatitude(lat);
            latestVisit.setLongitude(lon);
        }
        latestVisitRepository.save(latestVisit);
    }
}
