package com.iqes.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.*;
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

    @GeneratedValue
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "QUEUE_STATE")
    public String getQueueState() {
        return queueState;
    }

    public void setQueueState(String queueState) {
        this.queueState = queueState;
    }

    @Column(name = "CUSTOMER_TEL")
    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    @Column(name = "EAT_NUMBER")
    public Integer getEatNumber() {
        return eatNumber;
    }

    @Column(name = "EXTRACT_FLAG")
    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    @Column(name = "QUEUE_START_TIME")
    public String getQueueStartTime() {
        return queueStartTime;
    }

    public void setQueueStartTime(String queueStartTime) {
        this.queueStartTime = queueStartTime;
    }

    @Column(name = "QUEUE_END_TIME")
    public String getQueueEndTime() {
        return queueEndTime;
    }

    public void setQueueEndTime(String queueEndTime) {
        this.queueEndTime = queueEndTime;
    }

    public void setEatNumber(Integer eatNumber) {
        this.eatNumber = eatNumber;
    }


    @JoinColumn(name = "TABLE_NUMBER_ID",unique = true)
    @OneToOne
    @Column(name = "TABLE_NUMBER")
    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(TableNumber tableNumber) {
        this.tableNumber = tableNumber;
    }

    @JoinColumn(name = "TABLE_TYPE_ID")
    @ManyToOne
    @Column(name = "TABLE_TYPE")
    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }
}
