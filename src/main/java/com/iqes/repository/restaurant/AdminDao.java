package com.iqes.repository.restaurant;

import com.iqes.entity.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminDao extends CrudRepository<Admin,Long>{

    Admin findAdminByAccountAndPassword(String account,String password);

}
