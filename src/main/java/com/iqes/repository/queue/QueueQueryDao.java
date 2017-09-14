package com.iqes.repository.queue;

import com.iqes.entity.QueueInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 *  排队信息管理Dao层
 * Created by jiangdongdong.tp on 2017/2/6.
 */
public interface QueueQueryDao extends JpaRepository<QueueInfo,Long> {

    @Query(value = "select count(id) from queue_info as q where q.table_type_id = ?2 and q.id<?1",nativeQuery = true)
    long getWaitCountById(Long id,Long tableTypeId);

}
