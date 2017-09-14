package com.iqes.repository.restaurant;

/**
 * 桌子的dao层
 */

import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TableNumberDao extends CrudRepository<TableNumber, Long> {

    @Query("select t from TableNumber t where t.name=?1")
    TableNumber getByTableName(String tablename);

    TableNumber findTableNumberByName(String name);

    List<TableNumber> findTableNumbersByTableType(TableType tableType);

    @Query("select t from TableNumber t")
    List<TableNumber> findAll();

    //根据桌型id获得
    List<TableNumber> findByTableTypeId(long id);
}
