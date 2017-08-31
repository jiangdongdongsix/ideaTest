package com.iqes.service.queue;

import com.iqes.entity.QueueInfo;
import com.iqes.repository.queue.QueueQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QueueQueryService {

    @Autowired
    private QueueQueryDao queueQueryDao;

    public long save(QueueInfo queueInfo) throws  Exception{
        QueueInfo queueInfoVo = queueQueryDao.save(queueInfo);
        return queueInfoVo.getId();
    }


}
