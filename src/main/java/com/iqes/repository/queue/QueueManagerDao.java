package com.iqes.repository.queue;

import com.iqes.entity.QueueInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueueManagerDao extends CrudRepository<QueueInfo, Integer> {

    @Query("select q from QueueInfo q where q.tableType=(select t.tableType from TableNumber t where t.name=?1)")
    List<QueueInfo> getSameTypeNumbers(String tableName);

    @Query("select q from QueueInfo q where q.extractFlag='1'")
    List<QueueInfo> getArrivingNumbers();
}