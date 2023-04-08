package com.fyp.covidhelper.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Log {
    @Id
    private String log_id;

    private String log_desc;

    private LocalDateTime log_time;

    private String log_devicename;

    private String log_osversion;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getLog_desc() {
        return log_desc;
    }

    public void setLog_desc(String log_desc) {
        this.log_desc = log_desc;
    }

    public LocalDateTime getLog_time() {
        return log_time;
    }

    public void setLog_time(LocalDateTime log_time) {
        this.log_time = log_time;
    }

    public String getLog_devicename() {
        return log_devicename;
    }

    public void setLog_devicename(String log_devicename) {
        this.log_devicename = log_devicename;
    }

    public String getLog_osversion() {
        return log_osversion;
    }

    public void setLog_osversion(String log_osversion) {
        this.log_osversion = log_osversion;
    }


}
