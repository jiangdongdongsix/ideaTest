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

    private Integer broadcastCount;

    private String enablement;

    private String fontSize;

    private String fontArea;

    private String fontColour;

    public Integer getBroadcastCount() {
        return broadcastCount;
    }

    public void setBroadcastCount(Integer broadcastCount) {
        this.broadcastCount = broadcastCount;
    }

    public String getEnablement() {
        return enablement;
    }

    public void setEnablement(String enablement) {
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
