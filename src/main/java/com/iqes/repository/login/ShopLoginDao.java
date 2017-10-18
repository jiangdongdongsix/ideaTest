package com.iqes.repository.login;


import com.iqes.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShopLoginDao extends PagingAndSortingRepository<User, Long> {

    User findByLoginName(String loginName);

    @Query(value = "select * from my_user where login_name=?1 and password = ?2",nativeQuery = true)
    User validate(String loginName,String password);
}
