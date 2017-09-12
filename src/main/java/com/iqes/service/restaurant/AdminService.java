package com.iqes.service.restaurant;

import com.iqes.entity.Admin;
import com.iqes.repository.restaurant.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin checkAccount(String account,String password){
        return adminDao.findAdminByAccountAndPassword(account,password);
    }
}
