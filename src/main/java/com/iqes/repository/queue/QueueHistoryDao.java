package com.iqes.repository.queue;

import com.iqes.entity.QueueHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueueHistoryDao extends JpaRepository<QueueHistory,Long>{

    @Query(value = "SELECT MAX(create_time) from queue_history",nativeQuery = true)
    String getLastTime();
}
