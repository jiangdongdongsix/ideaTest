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

//    //根据桌名获取同桌类型的排队顾客
//    @Query("select q from QueueInfo q where q.tableType=(select t.tableType from TableNumber t where t.name=?1) and q.queueState='1' order by q.id")
//    List<QueueInfo> getSameTypeNumbers(String tableName);

    //根据桌类型id获取同桌类型的排队顾客

    @Query(value = "select * from queue_info as q where q.table_type_id = ?1 and q.queue_state='1'and q.extract_flag='0' ORDER BY q.id",nativeQuery = true )
    List<QueueInfo> getSameTypeNumbersByTableTypeId(Long id);


    //获取有抽号标志的排队顾客
    @Query("select q from QueueInfo q where q.extractFlag='1'")
    List<QueueInfo> getArrivingNumbers();

    //根据抽号标志和桌类型返回
    @Query("select q from QueueInfo q where q.extractFlag='1' and q.tableType.id=?1")
    List<QueueInfo> getByExtractFlagAndAndTableType(Long tableTypeId);

    //通过id获取queueInfo对象
    QueueInfo getById(Long id);
}