package com.iqes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *排队管理队列实体表
 * */
@Entity
@Table(name = "queue_info")
public class QueueInfo {
    /**
     *
     *主键
     * */
    private long id;
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
    private String eatNumber;
    /**
     *
     *座位号
     * */
    private String seatNumber;
    /**
     *
     *是否被叫号标志
     * */
    private String extractFlag;
    /**
     *
     *桌型id
     * */
    private String tableTypeId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getEatNumber() {
        return eatNumber;
    }

    public void setEatNumber(String eatNumber) {
        this.eatNumber = eatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    public String getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(String tableTypeId) {
        this.tableTypeId = tableTypeId;
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
