package com.iqes.entity.dto;

import com.iqes.entity.vo.WaitTimeModel;

/**
 * @author 54312
 * 排队记录转订单传递
 */
public class OrderQueueInfoDTO {

    private String queueNumber;
    private String queueStartTime;
    private Long queueWaitTime;
    private Long queueWaitTable;
    private Integer eatMaxNumber;
    private Integer eatMinNumber;
    private String extractFlag;
    private String tableTypeDescribe;


    public OrderQueueInfoDTO(){

    }
    public OrderQueueInfoDTO(WaitTimeModel waitTimeModel){

        this.queueNumber=waitTimeModel.getQueueNumber();
        this.queueStartTime=waitTimeModel.getQueueStartTime();
        this.queueWaitTime=waitTimeModel.getWaitTime();
        this.queueWaitTable=waitTimeModel.getWaitPopulation();
        this.eatMaxNumber=waitTimeModel.getTableType().getEatMaxNumber();
        this.eatMinNumber=waitTimeModel.getTableType().getEatMinNumber();
        this.extractFlag=waitTimeModel.getExtractFlag();
        this.tableTypeDescribe=waitTimeModel.getTableType().getDescribe();

    }

    public Long getQueueWaitTime() {
        return queueWaitTime;
    }

    public void setQueueWaitTime(Long queueWaitTime) {
        this.queueWaitTime = queueWaitTime;
    }

    public Long getQueueWaitTable() {
        return queueWaitTable;
    }

    public void setQueueWaitTable(Long queueWaitTable) {
        this.queueWaitTable = queueWaitTable;
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

    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    public String getTableTypeDescribe() {
        return tableTypeDescribe;
    }

    public void setTableTypeDescribe(String tableTypeDescribe) {
        this.tableTypeDescribe = tableTypeDescribe;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getQueueStartTime() {
        return queueStartTime;
    }

    public void setQueueStartTime(String queueStartTime) {
        this.queueStartTime = queueStartTime;
    }

    @Override
    public String toString() {
        return "OrderQueueInfoDTO{" +
                "queueId='" + queueNumber + '\'' +
                ", queueStartTime='" + queueStartTime + '\'' +
                ", queueWaitTime=" + queueWaitTime +
                ", queueWaitTable=" + queueWaitTable +
                ", eatMaxNumber=" + eatMaxNumber +
                ", eatMinNumber=" + eatMinNumber +
                ", extractFlag='" + extractFlag + '\'' +
                '}';
    }
}
