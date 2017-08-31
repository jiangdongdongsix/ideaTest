package com.iqes.entity;

import javax.persistence.*;

/**
 * 桌类型
 */

@Table(name = "TABLE_TYPE")
@Entity
public class TableType extends IdEntity{

    /**
     * 用餐人数
     */
    private Integer eatNumber;
    /**
     * 用餐时间
     */
    private Integer eatTime;

    /**
     *
     * 排队顾客信息
     */

    /**
     *
     * 桌号
     */

    public Integer getEatNumber() {
        return eatNumber;
    }

    public void setEatNumber(Integer eatNumber) {
        this.eatNumber = eatNumber;
    }

    public Integer getEatTime() {
        return eatTime;
    }

    public void setEatTime(Integer eatTime) {
        this.eatTime = eatTime;
    }
}
