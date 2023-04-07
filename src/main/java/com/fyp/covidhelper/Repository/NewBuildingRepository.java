package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BuildingRepository extends JpaRepository<Building,String> {
    @Modifying
    @Query("update Building b set b.district=?2 where b.id=?1")
    void setDistrict(Integer id,String district);

    @Modifying
    @Query("update Building b set b.name=?2 where b.id=?1")
    void setName(Integer id,String name);

    @Modifying
    @Query("update Building b set b.latedate=?2 where b.id=?1")
    void setLatedate(Integer id, LocalDate latedate);

    @Query("select count(b) from Building b where b.district=?1 and b.name=?2")
    int checkExistOfBuilding(String district, String name);

    @Query("select b.id from Building b where b.district=?1 and b.name=?2")
    int getId(String district, String name);
}
