package com.iqes.repository;

import com.iqes.entity.TableType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface TableDao extends PagingAndSortingRepository<TableType, Long>, JpaSpecificationExecutor<TableType> {

      @Modifying
      @Query(value = "SELECT * FROM table_type as ty where ty.eat_min_number<?1 and ?1<=ty.eat_max_number",nativeQuery = true)
      List<TableType> getTableType(Integer eatNum);
}
