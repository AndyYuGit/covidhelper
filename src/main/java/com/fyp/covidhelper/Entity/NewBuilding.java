package com.fyp.covidhelper.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;

@Entity
@Table(name = "newbuilding", schema = "covidhelper",uniqueConstraints = { @UniqueConstraint(columnNames = { "district", "name" }) })
public class NewBuilding extends BuildingMappedSuperClass{
    public NewBuilding(){
        super();
    }

    public NewBuilding(BuildingMappedSuperClass building){
        super(building);
    }
}
