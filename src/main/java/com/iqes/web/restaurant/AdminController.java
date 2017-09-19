package com.iqes.web.restaurant;

import com.iqes.entity.Admin;
import com.iqes.service.restaurant.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "restaurant/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password){

        Admin admin=adminService.checkAccount(account,password);

        //没有写完，暂时不确定错误信息该如何返回
        return null;
    }
}
