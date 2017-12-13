package com.iqes.entity.dto;

import com.iqes.entity.QueueHistory;

/**
 * @author 54312
 * 历史记录转订单再传递给前台
 */
public class OrderQueueHistoryDTO {

    private String queueNumber;
    private String queueStartTime;
    private String queueEndTime;
    private String tableTypeDescribe;
    private Integer eatMaxNumber;
    private Integer eatMinNumber;


    public OrderQueueHistoryDTO(){

    }

    public OrderQueueHistoryDTO(QueueHistory queueHistory){
        this.queueNumber=queueHistory.getQueueNumber();
        this.queueStartTime=queueHistory.getQueueStartTime();
        this.tableTypeDescribe=queueHistory.getTableType().getDescribe();
        this.queueEndTime=queueHistory.getQueueEndTime();
        this.eatMaxNumber=queueHistory.getTableType().getEatMaxNumber();
        this.eatMinNumber=queueHistory.getTableType().getEatMinNumber();
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Integer getEatMaxNumber() {
        return eatMaxNumber;
    }

    public void setEatMaxNumber(Integer eatMaxNumber) {
        this.eatMaxNumber = eatMaxNumber;
    }

    public Integer getEatMinNumber() {
        return eatMinNumber;
    }

    public void setEatMinNumber(Integer eatMinNumber) {
        this.eatMinNumber = eatMinNumber;
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

    public String getTableTypeDescribe() {
        return tableTypeDescribe;
    }

    public void setTableTypeDescribe(String tableTypeDescribe) {
        this.tableTypeDescribe = tableTypeDescribe;
    }

    @Override
    public String toString() {
        return "OrderQueueHistoryDTO{" +
                "queueId='" + queueNumber + '\'' +
                ", queueStartTime='" + queueStartTime + '\'' +
                ", queueEndTime='" + queueEndTime + '\'' +
                ", tableTypeDescribe='" + tableTypeDescribe + '\'' +
                ", eatMaxNumber=" + eatMaxNumber +
                ", eatMinNumber=" + eatMinNumber +
                '}';
    }
}
