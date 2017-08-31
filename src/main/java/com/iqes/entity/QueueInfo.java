package com.iqes.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *排队管理队列实体表
 * */
@Entity
@Table(name = "queue_info")
public class QueueInfo extends IdEntity{
    /**
     *
     *客户名称
     * */
    private String customerName;
    /**
     *
     *排队状态
     * */
    private String queueState;
    /**
     *
     *联系方式
     * */
    private String customerTel;
    /**
     *
     *用餐人数
     * */
    private Integer eatNumber;
    /**
     *
     *座位号
     * */
    private TableNumber tableNumber;
    /**
     *
     *是否被叫号标志
     * */
    private String extractFlag;
    /**
     *
     *桌型
     * */
    private TableType tableType;
    /**
     *
     *排队开始时间
     * */
    private String queueStartTime;
    /**
     *
     *排队结束时间
     * */
    private String queueEndTime;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getQueueState() {
        return queueState;
    }

    public void setQueueState(String queueState) {
        this.queueState = queueState;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public Integer getEatNumber() {
        return eatNumber;
    }

    public void setEatNumber(Integer eatNumber) {
        this.eatNumber = eatNumber;
    }

    @JoinColumn(name = "table_number_id")
    @OneToOne
    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(TableNumber tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    @JoinColumn(name = "table_type_id")
    @ManyToOne
    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public String getQueueStartTime() {
        return queueStartTime;
    }

    public void setQueueStartTime(String queueStartTime) {
        this.queueStartTime = queueStartTime;
    }

    public String getQueueEndTime() {
        return queueEndTime;
    }

    public void setQueueEndTime(String queueEndTime) {
        this.queueEndTime = queueEndTime;
    }
}
