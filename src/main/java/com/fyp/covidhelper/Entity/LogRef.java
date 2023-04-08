package com.fyp.covidhelper.Entity;


import javax.persistence.*;

@Entity
@Table(name = "logref", schema = "covidhelper")
public class LogRef {
    @Id
    private String id;

    private String ref_desc;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef_desc() {
        return ref_desc;
    }

    public void setRef_desc(String ref_desc) {
        this.ref_desc = ref_desc;
    }

}
