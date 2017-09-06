package com.iqes.repository.queue;

/**
 * 桌子的dao层
 */

import com.iqes.entity.TableNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TableNumberDao extends CrudRepository<TableNumber, Integer> {

    @Query("select t from TableNumber t where t.name=?1")
    TableNumber getByTableName(String tablename);
}
