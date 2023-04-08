package com.fyp.covidhelper.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "building", schema = "covidhelper",uniqueConstraints = { @UniqueConstraint(columnNames = { "district", "name" }) })
public class Building extends BuildingMappedSuperClass{

}
