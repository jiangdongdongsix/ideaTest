package com.iqes.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 54312
 */
@Table(name = "restaurant_area")
@Entity
public class RestaurantArea extends IdEntity{

    private String areaName;

    private String areaDescribe;

    public String getAreaDescribe() {
        return areaDescribe;
    }

    public void setAreaDescribe(String areaDescribe) {
        this.areaDescribe = areaDescribe;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
