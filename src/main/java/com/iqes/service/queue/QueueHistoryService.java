package com.iqes.service.queue;

import com.iqes.repository.queue.QueueHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueHistoryService {

    @Autowired
    private QueueHistoryDao queueHistoryDao;


    public String getLastTime() throws Exception{
        return queueHistoryDao.getLastTime();
    }

}
