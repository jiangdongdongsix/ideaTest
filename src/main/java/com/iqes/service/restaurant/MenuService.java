package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.Menu;
import com.iqes.repository.restaurant.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
