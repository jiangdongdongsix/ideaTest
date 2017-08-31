package com.iqes.repository.queue;


/**
 * 抽号场景和叫号查询场景的Dao层
 */

import com.iqes.entity.QueueInfo;
import org.springframework.data.repository.CrudRepository;

public interface QueueManagerDao extends CrudRepository<QueueInfo,Integer> {

}
