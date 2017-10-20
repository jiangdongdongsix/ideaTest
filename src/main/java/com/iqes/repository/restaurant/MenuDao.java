package com.iqes.repository.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuDao extends PagingAndSortingRepository<Menu,Long>,JpaSpecificationExecutor<Menu> {

}
