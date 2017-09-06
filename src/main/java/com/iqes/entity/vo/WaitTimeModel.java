package com.iqes.entity.vo;
/**
 * 预约排队 返回等候时间和人数
 * */
public class WaitTimeModel {

    private static final long serialVersionUID = 1L;
    /**
     * 排队号
     * */
    private long queueId;
    /**
     * 选择的桌型
     * */
    private String tableType;
    /**
     * 等候的人数
     * */
    private long waitPopulation;
    /**
     * 预估等候时间
     * */
    private long waitTime;

    public long getQueueId() {
        return queueId;
    }

    public void setQueueId(long queueId) {
        this.queueId = queueId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
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
}
