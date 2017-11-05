package com.iqes.repository.restaurant;

import com.iqes.entity.TableType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface TableTypeDao extends PagingAndSortingRepository<TableType, Long>, JpaSpecificationExecutor<TableType> {

      @Query(value = "SELECT * FROM table_type as ty where ty.eat_min_number<?1 and ?1<=ty.eat_max_number",nativeQuery = true)
      List<TableType> getTableType(Integer eatNum);

      @Query(value = "SELECT count(*) FROM table_number as tn where tn.table_type_id = ?1",nativeQuery = true)
      Integer getTableCountByType(long tableTypeId);

      @Override
      @Query(value = "select t from TableType t")
      List<TableType> findAll();


      @Query(value = "select eat_time from table_type where id = ?1",nativeQuery = true)
      long findEatTimeById(long id);

      @Query(value = "select id from TableType t where t.eatMaxNumber<?1 order by id desc ")
      List<Long> getByEatMaxNumberLessThan(Integer eatMaxNumber );

      @Query(value = "select id from TableType")
      List<Long> getAllTableTypeId();

      TableType getByDescribe(String describe);
}
