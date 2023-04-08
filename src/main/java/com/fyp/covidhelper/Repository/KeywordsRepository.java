
package com.fyp.covidhelper.Repository;

import com.fyp.covidhelper.Entity.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface KeywordsRepository extends JpaRepository<Keywords,Integer> {

}
