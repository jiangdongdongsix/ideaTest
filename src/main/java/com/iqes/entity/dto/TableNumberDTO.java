package com.iqes.entity.dto;

import com.iqes.entity.IdEntity;

/**
 * @author 54312
 */
public class TableNumberDTO extends IdEntity {

    private String tableName;

    private String tableTypeDescribe;

    private String area;

    private Integer eatMaxNumber;

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableTypeDescribe() {
        return tableTypeDescribe;
    }

    public void setTableTypeDescribe(String tableTypeDescribe) {
        this.tableTypeDescribe = tableTypeDescribe;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getEatMaxNumber() {
        return eatMaxNumber;
    }

    public void setEatMaxNumber(Integer eatMaxNumber) {
        this.eatMaxNumber = eatMaxNumber;
    }

    @Override
    public String toString() {
        return "TableNumberDTO{" +
                "tableName='" + tableName + '\'' +
                ", tableTypeDescribe='" + tableTypeDescribe + '\'' +
                ", area='" + area + '\'' +
                ", eatMaxNumber=" + eatMaxNumber +
                '}';
    }
}
