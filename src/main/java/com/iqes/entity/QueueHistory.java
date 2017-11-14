package com.iqes.entity;

import javax.persistence.*;

@Table(name = "queue_history")
@Entity
public class QueueHistory extends IdEntity{

    private static final long serialVersionUID = 1L;
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
     *座位号
     * 接收前台的座位号
     * */
    private String seatNum;
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
    /**
     *
     *排队号
     * */
    private Long queueId;
    /**
     *
     *抽号次数
     * */
    private Integer extractCount;

    /**
     *
     *叫号次数
     * */

    private Integer callCount;
    /**
     * 是否选坐标志
     */
    private Boolean seatFlag;

    /**
     *第一次抽号的时间
     */
    private String firstExtractTime;

    public QueueHistory(){
    }

    public QueueHistory(QueueInfo queueInfo){

        this.customerName=queueInfo.getCustomerName();
        this.customerTel=queueInfo.getCustomerTel();
        this.eatNumber=queueInfo.getEatNumber();
        this.queueId=queueInfo.getId();
        this.extractCount=queueInfo.getExtractCount();
        this.queueState=queueInfo.getQueueState();
        this.extractFlag=queueInfo.getExtractFlag();
        this.seatFlag=queueInfo.getSeatFlag();
        this.tableNumber=queueInfo.getTableNumber();
        this.tableType=queueInfo.getTableType();
        this.seatNum=queueInfo.getSeatNum();
        this.callCount=queueInfo.getCallCount();
        this.queueStartTime=queueInfo.getQueueStartTime();
        this.queueEndTime=queueInfo.getQueueEndTime();
        this.firstExtractTime=queueInfo.getFirstExtractTime();
    }


    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public String getFirstExtractTime() {
        return firstExtractTime;
    }

    public void setFirstExtractTime(String firstExtractTime) {
        this.firstExtractTime = firstExtractTime;
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

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public void setQueueEndTime(String queueEndTime) {
        this.queueEndTime = queueEndTime;
    }

    public Integer getExtractCount() {
        return extractCount;
    }

    public void setExtractCount(Integer extractCount) {
        this.extractCount = extractCount;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Boolean getSeatFlag() {
        return seatFlag;
    }

    public void setSeatFlag(Boolean seatFlag) {
        this.seatFlag = seatFlag;
    }
}
