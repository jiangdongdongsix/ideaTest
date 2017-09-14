package com.iqes.repository.queue;

import com.iqes.entity.QueueInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

public interface QueueManagerDao extends PagingAndSortingRepository<QueueInfo, Long> {

    //获取同桌类型的排队顾客
    @Query("select q from QueueInfo q where q.tableType=(select t.tableType from TableNumber t where t.name=?1) order by q.id")
    List<QueueInfo> getSameTypeNumbers(String tableName);

    //获取有抽号标志的排队顾客
    @Query("select q from QueueInfo q where q.extractFlag='1'")
    List<QueueInfo> getArrivingNumbers();

    //通过id获取queueInfo对象
    QueueInfo getByIdIs(Long id);
}