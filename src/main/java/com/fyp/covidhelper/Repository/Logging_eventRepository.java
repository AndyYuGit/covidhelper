package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.Logging_event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface Logging_eventRepository extends JpaRepository<Logging_event,Integer> {


}