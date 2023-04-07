package com.fyp.covidhelper.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "building", schema = "covidhelper")
public class Building {
    private String district;
    @Id
    private Integer id;

    private String name;

    private LocalDate latedate;

    private String cases;

    private Double latitude;

    private Double longitude;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Building building = (Building) o;

        if (!district.equals(building.district)) return false;
        if (!id.equals(building.id)) return false;
        return name.equals(building.name);
    }

    @Override
    public int hashCode() {
        int result = district.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
