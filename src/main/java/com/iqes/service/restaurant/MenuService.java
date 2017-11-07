package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.Menu;
import com.iqes.entity.dto.MenuDTO;
import com.iqes.repository.restaurant.MenuDao;
import com.iqes.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public void saveOne(Menu menu){
        if (menu==null){
            return;
        }else {
            menuDao.save(menu);
        }

    }

    public void deleteOne(Long id){
        Menu menu=menuDao.findOne(id);
        if (menu==null){
            throw new ServiceException("查无此桌");
        }else{
            menuDao.delete(id);
        }
    }

    public Menu findOne(Long id){
        Menu menu=menuDao.findOne(id);
        if (menu==null){
            throw new ServiceException("查无此桌");
        }
        return menu;
    }

    public Page<Menu> pageQueryOfMenu(int pageNo, int pageSize){
        /**
         * 获取排序sort
         */
        Sort.Order order=new Sort.Order(Sort.Direction.ASC,"id");
        Sort sort=new Sort(order);

        PageRequest pageable = new PageRequest(pageNo-1, pageSize,sort);
        Page<Menu> page = menuDao.findAll(pageable);

        return page;
    }

    public String updateAvailableState(Long id,boolean availability){
        String msg="更新失败";
        Menu menu=menuDao.findOne(id);

        if (menu!=null){
            menu.setAvailable(availability);
            menuDao.save(menu);
            msg="更新成功";
        }
        return msg;
    }

    public List<MenuDTO> getAllMenu(){
      return convertMenuDTOS(menuDao.findAll());
    }

    public MenuDTO findByMenuName(String menuName){
        Menu menu=menuDao.findByMenuName(menuName);
        if (menu==null){
            throw new ServiceException("查无此菜哟~~");
        }
        return convertMenuDTO(menu);
    }


    private List<MenuDTO> convertMenuDTOS(List<Menu> menuList){
        List<MenuDTO> menuDTOS=new ArrayList<MenuDTO>();
        MenuDTO menuDTO=new MenuDTO();
        for (Menu menu:menuList){
            menuDTO.setAvailable(menu.isAvailable());
            menuDTO.setDescribe(menu.getDescribe());
            menuDTO.setMemberMenuPrice(menuDTO.getMemberMenuPrice());
            menuDTO.setMenuName(menuDTO.getMenuName());
            menuDTO.setMenuPrice(menu.getMenuPrice());
            menuDTO.setMenuType(menu.getMenuType());

            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }

    private MenuDTO convertMenuDTO(Menu menu){

        MenuDTO menuDTO=new MenuDTO();

        menuDTO.setAvailable(menu.isAvailable());
        menuDTO.setDescribe(menu.getDescribe());
        menuDTO.setMemberMenuPrice(menuDTO.getMemberMenuPrice());
        menuDTO.setMenuName(menuDTO.getMenuName());
        menuDTO.setMenuPrice(menu.getMenuPrice());
        menuDTO.setMenuType(menu.getMenuType());

        return menuDTO;
    }
}
