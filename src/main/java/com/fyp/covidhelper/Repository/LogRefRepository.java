package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Model.Buildings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingsRepository extends JpaRepository<Buildings,String> {
}
