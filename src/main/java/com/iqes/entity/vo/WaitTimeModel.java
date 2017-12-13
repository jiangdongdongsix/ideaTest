package com.iqes.entity.vo;

import com.iqes.entity.TableType;

/**
 * 预约排队 返回等候时间和人数
 * */
public class WaitTimeModel {

    private static final long serialVersionUID = 1L;
    /**
     * 排队号
     * */
    private Long queueId;
    /**
     * 选择的桌型
     * */
    private TableType tableType;
    /**
     * 等候的人数
     * */
    private long waitPopulation;
    /**
     * 预估等候时间
     * */
    private long waitTime;
    /**
     * 是否抽号标志
     * */
    private String extractFlag;
    /**
     * 排队开始时间
     * */
    private String queueStartTime;

    /**
     *排队号
     */
    private String queueNumber;

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public long getWaitPopulation() {
        return waitPopulation;
    }

    public void setWaitPopulation(long waitPopulation) {
        this.waitPopulation = waitPopulation;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    public String getQueueStartTime() {
        return queueStartTime;
    }

    public void setQueueStartTime(String queueStartTime) {
        this.queueStartTime = queueStartTime;
    }
}
