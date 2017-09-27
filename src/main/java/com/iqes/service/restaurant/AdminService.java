package com.iqes.service.restaurant;

import com.iqes.entity.Admin;
import com.iqes.repository.restaurant.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin checkAccount(String account,String password){

        Map<String,Integer>  ddd = new HashMap<String, Integer>();

        ddd.put("",1);
        return adminDao.findAdminByAccountAndPassword(account,password);

    }
}
