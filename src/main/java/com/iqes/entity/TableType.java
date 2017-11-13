package com.iqes.entity;

import javax.persistence.*;

/**
 * 桌类型
 * @author 54312
 */

@Table(name = "TABLE_TYPE")
@Entity
public class TableType extends IdEntity implements Comparable<TableType>{

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
    /**
     * 餐桌描述
     */
    private String describe;

    /**
     *
     * 提前推送桌数
     */
    private Integer pushNumbers;

    public Integer getPushNumbers() {
        return pushNumbers;
    }

    public void setPushNumbers(Integer pushNumbers) {
        this.pushNumbers = pushNumbers;
    }

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "TableType{" +
                "tableTypeName='" + tableTypeName + '\'' +
                ", eatMinNumber=" + eatMinNumber +
                ", eatMaxNumber=" + eatMaxNumber +
                ", eatTime=" + eatTime +
                ", describe='" + describe + '\'' +
                '}';
    }

    @Override
    public int compareTo(TableType o) {
        if (o == null) {
            return 1;
        }
        int value = this.eatMaxNumber - o.eatMaxNumber;
        if (value == 0) {
            value = this.eatMinNumber - o.eatMinNumber;
        }
        return value;
    }
}
