package com.iqes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 54312
 */
@Entity
@Table(name = "config_info")
public class ConfigInfo extends IdEntity {

    /**
     * 保留模式，1：不保留，过号作废；2：按次数保留，次数一过作废；3：按时间保留，时间一过，作废。
     */
    private Integer reservePattern;

    private Integer extractCount;

    private Integer reserveTime;

    /**
     * 邻桌取号，例如8人桌可以取6人桌，4人桌里的排队号
     */
    private Boolean nextTableExtractFlag;

    /**
     * 暂停抽号
     * false:不暂停
     * true:暂停
     */
    private  Boolean pauseQueue;

    public Boolean getPauseQueue() {
        return pauseQueue;
    }

    public void setPauseQueue(Boolean pauseQueue) {
        this.pauseQueue = pauseQueue;
    }

    public Boolean getNextTableExtractFlag() {
        return nextTableExtractFlag;
    }

    public void setNextTableExtractFlag(Boolean nextTableExtractFlag) {
        this.nextTableExtractFlag = nextTableExtractFlag;
    }

    public Integer getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Integer reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Integer getReservePattern() {
        return reservePattern;
    }

    public void setReservePattern(Integer reservePattern) {
        this.reservePattern = reservePattern;
    }

    public Integer getExtractCount() {
        return extractCount;
    }

    public void setExtractCount(Integer extractCount) {
        this.extractCount = extractCount;
    }

    @Override
    public String toString() {
        return "ConfigInfo{" +
                "reservePattern=" + reservePattern +
                ", extractCount=" + extractCount +
                ", reserveTime=" + reserveTime +
                ", id=" + id +
                '}';
    }
}
