package com.iqes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu extends IdEntity{

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
    private String available;

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
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
}
