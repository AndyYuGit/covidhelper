package com.fyp.covidhelper.Entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "logging_event", schema = "covidhelper")

public class Logging_event {
    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getFormatted_message() {
        return formatted_message;
    }

    public void setFormatted_message(String formatted_message) {
        this.formatted_message = formatted_message;
    }

    public String getLogger_name() {
        return logger_name;
    }

    public void setLogger_name(String logger_name) {
        this.logger_name = logger_name;
    }

    public String getLevel_string() {
        return level_string;
    }

    public void setLevel_string(String level_string) {
        this.level_string = level_string;
    }

    public String getThread_name() {
        return thread_name;
    }

    public void setThread_name(String thread_name) {
        this.thread_name = thread_name;
    }

    public short getReference_flag() {
        return reference_flag;
    }

    public void setReference_flag(short reference_flag) {
        this.reference_flag = reference_flag;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getCaller_filename() {
        return caller_filename;
    }

    public void setCaller_filename(String caller_filename) {
        this.caller_filename = caller_filename;
    }

    public String getCaller_class() {
        return caller_class;
    }

    public void setCaller_class(String caller_class) {
        this.caller_class = caller_class;
    }

    public String getCaller_method() {
        return caller_method;
    }

    public void setCaller_method(String caller_method) {
        this.caller_method = caller_method;
    }

    public int getCaller_line() {
        return caller_line;
    }

    public void setCaller_line(int caller_line) {
        this.caller_line = caller_line;
    }

    public BigInteger getEvent_id() {
        return event_id;
    }

    public void setEvent_id(BigInteger event_id) {
        this.event_id = event_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Logging_event that = (Logging_event) o;

        if (reference_flag != that.reference_flag) return false;
        if (caller_line != that.caller_line) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;
        if (formatted_message != null ? !formatted_message.equals(that.formatted_message) : that.formatted_message != null)
            return false;
        if (logger_name != null ? !logger_name.equals(that.logger_name) : that.logger_name != null) return false;
        if (level_string != null ? !level_string.equals(that.level_string) : that.level_string != null) return false;
        if (thread_name != null ? !thread_name.equals(that.thread_name) : that.thread_name != null) return false;
        if (device_id != null ? !device_id.equals(that.device_id) : that.device_id != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (os_version != null ? !os_version.equals(that.os_version) : that.os_version != null) return false;
        if (app_version != null ? !app_version.equals(that.app_version) : that.app_version != null) return false;
        if (caller_filename != null ? !caller_filename.equals(that.caller_filename) : that.caller_filename != null)
            return false;
        if (caller_class != null ? !caller_class.equals(that.caller_class) : that.caller_class != null) return false;
        if (caller_method != null ? !caller_method.equals(that.caller_method) : that.caller_method != null)
            return false;
        return event_id != null ? event_id.equals(that.event_id) : that.event_id == null;
    }

    @Override
    public int hashCode() {
        int result = datetime != null ? datetime.hashCode() : 0;
        result = 31 * result + (formatted_message != null ? formatted_message.hashCode() : 0);
        result = 31 * result + (logger_name != null ? logger_name.hashCode() : 0);
        result = 31 * result + (level_string != null ? level_string.hashCode() : 0);
        result = 31 * result + (thread_name != null ? thread_name.hashCode() : 0);
        result = 31 * result + (int) reference_flag;
        result = 31 * result + (device_id != null ? device_id.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (os_version != null ? os_version.hashCode() : 0);
        result = 31 * result + (app_version != null ? app_version.hashCode() : 0);
        result = 31 * result + (caller_filename != null ? caller_filename.hashCode() : 0);
        result = 31 * result + (caller_class != null ? caller_class.hashCode() : 0);
        result = 31 * result + (caller_method != null ? caller_method.hashCode() : 0);
        result = 31 * result + (int) caller_line;
        result = 31 * result + (event_id != null ? event_id.hashCode() : 0);
        return result;
    }

    public LocalDateTime datetime;
    public String formatted_message;
    public String logger_name;
    public String level_string;
    public String thread_name;
    public short reference_flag;
    public String device_id;
    public String model;
    public String os_version;
    public String app_version;
    public String caller_filename;
    public String caller_class;
    public String caller_method;
    public int caller_line;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger event_id;
}
