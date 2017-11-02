package com.iqes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 54312
 */
@Entity
@Table(name = "seating_chart")
public class SeatingChart extends IdEntity{

    private String url;
    private String describe;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
