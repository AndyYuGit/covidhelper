package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.Building;
import com.fyp.covidhelper.Service.BuildingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,Integer> {
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
    int existsBuildingByDistrictAndName(String district, String name);

    @Query("select count(b) from Building b where b.district=?1 and b.name like %?2%")
    int existsBuildingByDistrictAndNameLike(String district, String name);

    @Query("select b.id from Building b where b.district=?1 and b.name=?2")
    int findIdByDistrictAndName(String district, String name);

    @Query("select b.id from Building b where b.district=?1 and b.name like %?2%")
    Page<Integer> findIdByDistrictAndNameLike(String district, String name,Pageable pageable);


    @Query("select b.latitude from Building b where b.id=?1")
    Double findLatitudeById(Integer id);

    @Query("select b.latitude from Building b where b.district=?1 and b.name=?2")
    Double findLatitudeByDistrictAndName(String district, String name);

    @Query("select b.latitude from Building b where b.district=?1 and b.name like %?2%")
    Page<Double> findLatitudeByDistrictAndNameLike(String district, String name, Pageable pageable);

    @Query("select b.longitude from Building b where b.id=?1")
    Double findLongitudeById(Integer id);

    @Query("select b.longitude from Building b where b.district=?1 and b.name=?2")
    Double findLongitudeByDistrictAndName(String district, String name);

    @Query("select b.longitude from Building b where b.district=?1 and b.name like %?2%")
    Page<Double> findLongitudeByDistrictAndNameLike(String district, String name, Pageable pageable);

    @Query("select b.id as id,b.district as district, b.name as name from Building b")
    List<BuildingService.ProjectIdAndDistrictAndBuildings> findByIdAndDistrictAndName();
}
