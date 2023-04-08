package com.fyp.covidhelper.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins", schema = "covidhelper")
public class Admins {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(Integer phone_num) {
        this.phone_num = phone_num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admins admins = (Admins) o;

        if (id != null ? !id.equals(admins.id) : admins.id != null) return false;
        if (email != null ? !email.equals(admins.email) : admins.email != null) return false;
        if (name != null ? !name.equals(admins.name) : admins.name != null) return false;
        return phone_num != null ? phone_num.equals(admins.phone_num) : admins.phone_num == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone_num != null ? phone_num.hashCode() : 0);
        return result;
    }

    @Id
    private Integer id;



    private String email;

    private String name;

    private Integer phone_num;
}
