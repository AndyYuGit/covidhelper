package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.LatestVisitBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface LatestVisitBuildingRepository extends JpaRepository<LatestVisitBuilding,Integer> {
    @Query("select count(b) from LatestVisitBuilding b where b.district=?1 and b.name=?2")
    int existsBuildingByDistrictAndName(String district, String name);
}
