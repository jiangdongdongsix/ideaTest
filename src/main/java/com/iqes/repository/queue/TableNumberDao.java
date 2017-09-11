package com.iqes.repository.queue;

/**
 * 桌子的dao层
 */

import com.iqes.entity.TableNumber;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TableNumberDao extends CrudRepository<TableNumber, Integer> {


    @Query("select t from TableNumber t where t.name=?1")
    TableNumber getByTableName(String tablename);

    //根据桌型id获得
    List<TableNumber> findByTableTypeId(long id);

}
