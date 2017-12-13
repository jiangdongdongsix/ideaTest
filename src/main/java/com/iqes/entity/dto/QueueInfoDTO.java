package com.iqes.entity.dto;

import com.iqes.entity.QueueHistory;
import com.iqes.utils.TimeFormatTool;

import java.text.ParseException;

/**
 * @author 54312
 * 排队历史记录上传云端所用实体类
 */
public class QueueInfoDTO {

    private String customerName;
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
    private Long tableId;
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
    private Long tableTypeId;
    /**
     * 桌型描述
     */
    private String tableTypeDescribe;
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
     * 排队时间
     */
    private Integer queueTime;
    /**
     *
     *排队号
     * */
    private String queueNumber;
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

    private String queueState;

    private String tableNumber;

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public QueueInfoDTO(){

    }

    public QueueInfoDTO(QueueHistory queueInfo) throws ParseException {
        this.tableTypeDescribe=queueInfo.getTableType().getDescribe();
        this.queueTime= TimeFormatTool.diffTime(queueInfo.getQueueStartTime(),queueInfo.getQueueEndTime());
        this.customerName=queueInfo.getCustomerName();
        this.customerTel=queueInfo.getCustomerTel();
        this.eatNumber=queueInfo.getEatNumber();
        this.queueNumber=queueInfo.getQueueNumber();
        this.extractCount=queueInfo.getExtractCount();
        this.queueState=queueInfo.getQueueState();
        this.extractFlag=queueInfo.getExtractFlag();
        this.seatFlag=queueInfo.getSeatFlag();
        if (queueInfo.getTableNumber()!=null) {
            this.tableId = queueInfo.getTableNumber().getId();
        }
        this.tableTypeId=queueInfo.getTableType().getId();
        this.seatNum=queueInfo.getSeatNum();
        this.callCount=queueInfo.getCallCount();
        this.queueStartTime=queueInfo.getQueueStartTime();
        this.queueEndTime=queueInfo.getQueueEndTime();
        this.firstExtractTime=queueInfo.getFirstExtractTime();
        this.tableNumber=queueInfo.getTableNumber().getName();
    }

    public String getTableTypeDescribe() {
        return tableTypeDescribe;
    }

    public void setTableTypeDescribe(String tableTypeDescribe) {
        this.tableTypeDescribe = tableTypeDescribe;
    }

    public Integer getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(Integer queueTime) {
        this.queueTime = queueTime;
    }

    public String getQueueState() {
        return queueState;
    }

    public void setQueueState(String queueState) {
        this.queueState = queueState;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    public long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(long tableTypeId) {
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

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public void setTableTypeId(Long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
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

    public String getFirstExtractTime() {
        return firstExtractTime;
    }

    public void setFirstExtractTime(String firstExtractTime) {
        this.firstExtractTime = firstExtractTime;
    }

    @Override
    public String toString() {
        return "QueueInfoDTO{" +
                "customerName='" + customerName + '\'' +
                ", customerTel='" + customerTel + '\'' +
                ", eatNumber=" + eatNumber +
                ", tableId=" + tableId +
                ", seatNum='" + seatNum + '\'' +
                ", extractFlag='" + extractFlag + '\'' +
                ", tableTypeId=" + tableTypeId +
                ", queueStartTime='" + queueStartTime + '\'' +
                ", queueEndTime='" + queueEndTime + '\'' +
                ", queueId=" + queueNumber +
                ", extractCount=" + extractCount +
                ", callCount=" + callCount +
                ", seatFlag=" + seatFlag +
                ", firstExtractTime='" + firstExtractTime + '\'' +
                '}';
    }
}
