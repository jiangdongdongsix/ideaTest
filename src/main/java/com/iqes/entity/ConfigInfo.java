package com.iqes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "config_info")
public class ConfigInfo extends IdEntity {

    // 保留模式，0：不保留，过号作废；1：按次数保留，次数一过作废；2：按时间保留，时间一过，作废。
    private Integer reservePattern;

    private Integer extractCount;

    private Integer reserveTime;

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
}
