package com.iqes.entity;

import javax.persistence.*;


/**
 * 餐桌
 * @author 54312
 */
@Table(name = "TABLE_NUMBER")
@Entity
public class TableNumber extends IdEntity{

    /**
     * name
     */
    private String name;
    private TableType tableType;

    /**
     * 桌子的状态 就餐中还是空桌
     * 0:空桌
     * 1：就餐中
     */
    private String state;

    /**
     *
     * 桌子的区域
     */
    private RestaurantArea restaurantArea;

    @JoinColumn(name = "area_id")
    @ManyToOne
    public RestaurantArea getRestaurantArea() {
        return restaurantArea;
    }

    public void setRestaurantArea(RestaurantArea restaurantArea) {
        this.restaurantArea = restaurantArea;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JoinColumn(name = "table_type_id")
    @ManyToOne
    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableNumber that = (TableNumber) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
