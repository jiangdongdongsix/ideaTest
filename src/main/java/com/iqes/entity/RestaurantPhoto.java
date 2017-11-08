package com.iqes.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author 54312
 */

@Entity
@Table(name = "restaurant_photo")
public class RestaurantPhoto extends  IdEntity{

    private String url;

    /**
     * 0:直立机
     * 1：App
     */
    private String displayArea;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayArea() {
        return displayArea;
    }

    public void setDisplayArea(String displayArea) {
        this.displayArea = displayArea;
    }
}
