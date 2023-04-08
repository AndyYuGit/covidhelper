package com.fyp.covidhelper.Entity;

import javax.persistence.*;

@Entity
@Table(name = "keywords", schema = "covidhelper")
public class Keywords {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keywords keywords = (Keywords) o;

        if (id != null ? !id.equals(keywords.id) : keywords.id != null) return false;
        if (keyword != null ? !keyword.equals(keywords.keyword) : keywords.keyword != null) return false;
        return information != null ? information.equals(keywords.information) : keywords.information == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        return result;
    }

    @Id
    private Integer id;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    private String keyword;

    private String information;
}
