package com.iqes.repository.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MenuDao extends PagingAndSortingRepository<Menu,Long>,JpaSpecificationExecutor<Menu> {

    @Override
    @Query(value = "select Menu from Menu")
    List<Menu> findAll();

    Menu findByMenuName(String menuName);
}
