package com.iqes.repository.queue;

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface QueueManagerDao extends PagingAndSortingRepository<QueueInfo, Long>,JpaSpecificationExecutor<QueueInfo> {


    //根据桌类型id获取同桌类型的排队顾客
    @Query(value = "select * from queue_info as q where q.table_type_id = ?1 and q.queue_state='1'ORDER BY q.id",nativeQuery = true )
    List<QueueInfo> getSameTypeNumbersByTableTypeId(Long id);


    //获取有抽号标志的排队顾客
    @Query("select q from QueueInfo q where q.extractFlag='1'")
    List<QueueInfo> getArrivingNumbers();

    //根据抽号标志和桌类型返回
    @Query("select q from QueueInfo q where q.extractFlag='1' and q.tableType.id=?1")
    List<QueueInfo> getByExtractFlagAndAndTableType(Long tableTypeId);

    /**
     *
     * @param id
     * @return
     */
    QueueInfo getById(Long id);

    @Query(value = "select count(id) from queue_info AS q  where q.table_type_id=?1 AND q.queue_state='1'",nativeQuery = true)
    Integer getNumbersByTableTypeId(Long id);

    @Query("select q from QueueInfo q where q.tableType.id=?1 and q.queueState='1'")
    List<QueueInfo> getByTableType(Long tableTypeId);

    @Query("select q from QueueInfo q where q.tableNumber.id=?1")
    List<QueueInfo> getByTableNumber(Long tableNumberId);

    QueueInfo getByQueueId(String queueId);

    List<QueueInfo> getByTables(String tables);

    List<QueueInfo> getAllByQueueState(String queueState);
}