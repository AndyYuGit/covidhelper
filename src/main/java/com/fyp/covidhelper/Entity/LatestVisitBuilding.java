package com.fyp.covidhelper.Entity;


import javax.persistence.*;

@Entity
@Table(name = "latestvisit", schema = "covidhelper")
public class LatestVisit {
    @Id
    private Integer id;

    private String building;

    private String district;

    private double latitude;

    private double longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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
