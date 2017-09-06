package com.iqes.entity.co;

/**
 * 传如时间算法的参数
 */
public class CalculateTimeVO {

    /**
     * 排队人数
     */
    private long eatCountById;
    /**
     * 桌型id
     */
    private long tableTypeId;
    /**
     * 座位号
     */
    private String seatNum;
    /**
     * 最后一桌的用餐时间
     */
    private String lastTableTime;

    public long getEatCountById() {
        return eatCountById;
    }

    public void setEatCountById(long eatCountById) {
        this.eatCountById = eatCountById;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getLastTableTime() {
        return lastTableTime;
    }

    public void setLastTableTime(String lastTableTime) {
        this.lastTableTime = lastTableTime;
    }

    public long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }
}
