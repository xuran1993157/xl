package com.xl.common.bean;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lyc_address")
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "create_time")
    private Date create_time;

    @Column(name = "path")
    private String path;

    @Column(name = "province")
    private String province;

    @Column(name = "region")
    private String region;

    @Column(name = "parent_id")
    private Integer parent_id;

    @Column(name = "last_update_time")
    private Date last_update_time;

    @Column(name = "last_operator_Id")
    private Integer last_operator_Id;

    @Column(name = "last_operator_name")
    private String last_operator_name;

    @Column(name = "serial_no")
    private String serial_no;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Date getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(Date last_update_time) {
        this.last_update_time = last_update_time;
    }

    public Integer getLast_operator_Id() {
        return last_operator_Id;
    }

    public void setLast_operator_Id(Integer last_operator_Id) {
        this.last_operator_Id = last_operator_Id;
    }

    public String getLast_operator_name() {
        return last_operator_name;
    }

    public void setLast_operator_name(String last_operator_name) {
        this.last_operator_name = last_operator_name;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }
}