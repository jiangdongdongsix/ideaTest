package com.iqes.entity.dto;

import com.iqes.entity.BroadcastMachine;

import java.util.List;

/**
 * @author 54312
 * 直立机首页所需数据
 */
public class BroadcastHomePageDTO {

  private BroadcastMachine broadcastMachine;

    public BroadcastMachine getBroadcastMachine() {
        return broadcastMachine;
    }

    public void setBroadcastMachine(BroadcastMachine broadcastMachine) {
        this.broadcastMachine = broadcastMachine;
    }

    /**
     * 图片路径
     */
    private List<String> photoUrls;

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
