package com.iqes.entity.dto;

import com.iqes.entity.Menu;

import java.util.List;

/**
 * @author 54312
 */
public class MenuWithTypeDTO {
    private String menuType;
    private List<Menu> menuList;

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "MenuWithTypeDTO{" +
                "menuType='" + menuType + '\'' +
                ", menuList=" + menuList +
                '}';
    }
}
