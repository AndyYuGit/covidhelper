package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.LatestVisit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LatestVisitRepository extends JpaRepository<LatestVisit,String> {

}
