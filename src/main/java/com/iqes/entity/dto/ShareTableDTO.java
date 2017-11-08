package com.iqes.entity.dto;


/**
 * @author 54312
 * 拼桌后返回的实体类
 */
public class ShareTableDTO {

    private String tables;
    private String queueInfos;

    public String getQueueInfos() {
        return queueInfos;
    }

    public void setQueueInfos(String queueInfos) {
        this.queueInfos = queueInfos;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }
}
