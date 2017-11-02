package com.iqes.entity;

import com.iqes.entity.IdEntity;
import com.iqes.entity.TableNumber;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 54312
 */
@Table(name = "restaurant_area")
@Entity
public class RestaurantArea extends IdEntity{

    private String areaName;

    private TableNumber tableNumber;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
