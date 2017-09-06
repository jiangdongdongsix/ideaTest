package com.iqes.entity.vo;
/**
 * 预约排队 返回等候时间和人数
 * */
public class WaitTimeModel {

    private static final long serialVersionUID = 1L;
    /**
     * 排队号
     * */
    private String queueId;
    /**
     * 选择的桌型
     * */
    private String tableType;
    /**
     * 等候的人数
     * */
    private Integer waitPopulation;
    /**
     * 预估等候时间
     * */
    private String waitTime;

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Integer getWaitPopulation() {
        return waitPopulation;
    }

    public void setWaitPopulation(Integer waitPopulation) {
        this.waitPopulation = waitPopulation;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
}
