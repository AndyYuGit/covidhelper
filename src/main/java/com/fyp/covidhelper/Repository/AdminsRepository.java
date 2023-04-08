package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AdminsRepository extends JpaRepository<Admins,Integer> {

}
