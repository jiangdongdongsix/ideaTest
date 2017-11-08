package com.iqes.entity.dto;

import com.iqes.entity.IdEntity;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;

import java.util.List;

/**
 * @author 54312
 * 返回桌类型的基本信息和此类型的排队情况
 */
public class TableTypeDTO{

    /**
     *桌型名称 A B
     */
    private String tableTypeDescribe;

    private Integer eatMinNumber;
    private Integer eatMaxNumber;

    /**
     * 此桌型下的排队人数
     */
    private Integer queueNumbers;
    /**
     * 到号
     */
    private String arrivingQueueInfo;

    public String getTableTypeDescribe() {
        return tableTypeDescribe;
    }

    public void setTableTypeDescribe(String tableTypeDescribe) {
        this.tableTypeDescribe = tableTypeDescribe;
    }

    public Integer getEatMinNumber() {
        return eatMinNumber;
    }

    public void setEatMinNumber(Integer eatMinNumber) {
        this.eatMinNumber = eatMinNumber;
    }

    public Integer getEatMaxNumber() {
        return eatMaxNumber;
    }

    public void setEatMaxNumber(Integer eatMaxNumber) {
        this.eatMaxNumber = eatMaxNumber;
    }

    public Integer getQueueNumbers() {
        return queueNumbers;
    }

    public void setQueueNumbers(Integer queueNumbers) {
        this.queueNumbers = queueNumbers;
    }

    public String getArrivingQueueInfo() {
        return arrivingQueueInfo;
    }

    public void setArrivingQueueInfo(String arrivingQueueInfo) {
        this.arrivingQueueInfo = arrivingQueueInfo;
    }
}
