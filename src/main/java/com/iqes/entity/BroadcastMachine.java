package com.iqes.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author 54312
 */

@Entity
@Table(name = "broadcast_machine")
public class BroadcastMachine extends IdEntity {

    /** 播报次数*/
    private Integer broadcastCount;
    /** 启动状态，
     * false：不启用
     * true：启用
     * */
    private boolean enablement;

    /**
     * 字体大小
     */
    private String fontSize;

    /**
     * 字体显示位置
     */
    private String fontArea;

    /**
     * 字体颜色
     */
    private String fontColour;

    public Integer getBroadcastCount() {
        return broadcastCount;
    }

    public void setBroadcastCount(Integer broadcastCount) {
        this.broadcastCount = broadcastCount;
    }

    public boolean isEnablement() {
        return enablement;
    }

    public void setEnablement(boolean enablement) {
        this.enablement = enablement;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontArea() {
        return fontArea;
    }

    public void setFontArea(String fontArea) {
        this.fontArea = fontArea;
    }

    public String getFontColour() {
        return fontColour;
    }

    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }
}
