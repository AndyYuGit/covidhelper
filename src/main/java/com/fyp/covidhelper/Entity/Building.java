package com.fyp.covidhelper.Entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "buildings", schema = "covidhelper")
public class Buildings {
    private String district;
    @Id
    private Integer id;

    private String buildings;

    private LocalDate latedate;

    private String cases;

    private double latitude;

    private double longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBuildings() {
        return buildings;
    }

    public void setBuildings(String buildings) {
        this.buildings = buildings;
    }

    public LocalDate getLatedate() {
        return latedate;
    }

    public void setLatedate(LocalDate latedate) {
        this.latedate = latedate;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    




}
