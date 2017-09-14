package com.iqes.repository.queue;

import com.iqes.entity.QueueInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *  排队信息管理Dao层
 * Created by jiangdongdong.tp on 2017/2/6.
 */
public interface QueueQueryDao extends JpaRepository<QueueInfo,Long> {

    //确认排队，更新排队状态和手机号
    @Modifying
    @Query(value = "update queue_info set queue_state = ?3,customer_tel=?2 where id = ?1",nativeQuery = true)
    @Transactional
    void updateStateAndTel(long id,String tel,String state);

    //根据排队id和桌型id来获取排队人数
    @Query(value = "select count(id) from queue_info as q where q.table_type_id = ?2 and q.id<?1",nativeQuery = true)
    long getWaitCountById(Long id,Long tableTypeId);

    //根据排队id和桌型id来获取排队人数（整个队形）
    @Query(value = "select count(id) from queue_info as q where q.table_type_id = ?1",nativeQuery = true)
    long getWaitCount(Long tableTypeId);

    //根据桌型id来获取选坐排队人数（整个队形）
    @Query(value = "select count(id) from queue_info as q where q.table_type_id = ?1 and q.seat_flag = true ",nativeQuery = true)
    long chooseSeatCountByTableTypeId(Long tableTypeId);

    //根据排队id和桌型id来获取选坐排队人数
    @Query(value = "select count(id) from queue_info as q where q.table_type_id = ?2 and q.id<?1 and q.seat_flag = true ",nativeQuery = true)
    long chooseSeatCountById(Long id,Long tableTypeId);

    //未选坐的排队人数
    @Query(value = "select count(id) from queue_info as q where q.table_type_id = ?2 and q.id<?1 and q.seat_flag = false",nativeQuery = true)
    long nochooseCountById(Long id,Long tableTypeId);


}
