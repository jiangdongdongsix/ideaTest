package com.iqes.entity.dto;

import com.iqes.entity.IdEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author 54312
 */
public class MenuDTO extends IdEntity{

    private String menuName;

    private String menuType;

    private Integer menuPrice;

    private Integer memberMenuPrice;

    private String describe;

    private String photoUrl;

    /**
     * 该字段表明菜品是否可售，可能由于售罄或其他问题而导致菜品不可售
     * 0：不可售
     * 1：可售
     */
    private Boolean available;

    private MultipartFile menuPhoto;

    public MultipartFile getMenuPhoto() {
        return menuPhoto;
    }

    public void setMenuPhoto(MultipartFile menuPhoto) {
        this.menuPhoto = menuPhoto;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(Integer menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Integer getMemberMenuPrice() {
        return memberMenuPrice;
    }

    public void setMemberMenuPrice(Integer memberMenuPrice) {
        this.memberMenuPrice = memberMenuPrice;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "menuName='" + menuName + '\'' +
                ", menuType='" + menuType + '\'' +
                ", menuPrice=" + menuPrice +
                ", memberMenuPrice=" + memberMenuPrice +
                ", describe='" + describe + '\'' +
                ", available='" + available + '\'' +
                '}';
    }
}
