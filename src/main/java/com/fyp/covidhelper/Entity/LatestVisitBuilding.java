package com.fyp.covidhelper.Entity;


import javax.persistence.*;

@Entity
@Table(name = "latestvisitbuilding", schema = "covidhelper",uniqueConstraints = { @UniqueConstraint(columnNames = { "district", "name" }) })
public class LatestVisitBuilding extends BuildingMappedSuperClass {
    public LatestVisitBuilding(){
        super();
    }
    public LatestVisitBuilding(BuildingMappedSuperClass building){
        super(building);
    }

}
