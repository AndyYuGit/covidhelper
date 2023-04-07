package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.Buildings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BuildingsRepository extends JpaRepository<Buildings,String> {
    @Modifying
    @Query("update Buildings b set b.latedate=?3 where b.district=?1 and b.building=?2")
    void setLatedate(String district, String building, LocalDate latedate);

}
