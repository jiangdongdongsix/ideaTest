package com.iqes.entity;

import javax.persistence.*;

/**
 * 桌类型
 */
@Table(name = "TABLE_TYPE")
@Entity
public class TableType extends IdEntity{

    /**
     * 桌型名称如“A”、“B”
     */
     private String tableTypeName;
    /**
     * 桌型用餐最小人数
     */
    private Integer eatMinNumber;
    /**
     * 桌型用餐最大人数
     */
    private Integer eatMaxNumber;
    /**
     * 用餐时间
     */
    private Integer eatTime;

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }

    public Integer getEatMinNumber() {
        return eatMinNumber;
    }

    public void setEatMinNumber(Integer eatMinNumber) {
        this.eatMinNumber = eatMinNumber;
    }

    public Integer getEatMaxNumber() {
        return eatMaxNumber;
    }

    public void setEatMaxNumber(Integer eatMaxNumber) {
        this.eatMaxNumber = eatMaxNumber;
    }

    public Integer getEatTime() {
        return eatTime;
    }

    public void setEatTime(Integer eatTime) {
        this.eatTime = eatTime;
    }
}
