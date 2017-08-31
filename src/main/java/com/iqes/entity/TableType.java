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
    private QueueInfo queueInfo;

    /**
     *
     * 桌号
     */
    private TableNumber tableNumber;

    @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="tableType")
    public QueueInfo getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(QueueInfo queueInfo) {
        this.queueInfo = queueInfo;
    }

    @Column(name = "EAT_NUMBER")
    public Integer getEatNumber() {
        return eatNumber;
    }

    public void setEatNumber(Integer eatNumber) {
        this.eatNumber = eatNumber;
    }

    @Column(name = "EAT_TIME")
    public Integer getEatTime() {
        return eatTime;
    }

    public void setEatTime(Integer eatTime) {
        this.eatTime = eatTime;
    }

    @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="tableType")
    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(TableNumber tableNumber) {
        this.tableNumber = tableNumber;
    }
}
