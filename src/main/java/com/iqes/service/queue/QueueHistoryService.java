package com.iqes.service.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueHistory;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.dto.QueueInfoDTO;
import com.iqes.rabbitmq.EmitLog;
import com.iqes.rabbitmq.RabbitCarrier;
import com.iqes.repository.queue.QueueHistoryDao;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.utils.TimeFormatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 54312
 */
@Service
public class QueueHistoryService {

    @Autowired
    private QueueHistoryDao queueHistoryDao;

    @Autowired
    private QueueManagerDao queueManagerDao;


    public String getLastTime(long id) throws Exception{
        return queueHistoryDao.getLastTime(id);
    }

    public void saveAndAddToCloud(QueueInfo q){
        /**
         * 从正在排队的表里删除
         */
        queueManagerDao.delete(q);

        q.setQueueEndTime(TimeFormatTool.getCurrentTime());
        q.setQueueState("3");
        q.setId(null);

        /**
         * 转换格式保存在本地的历史纪律表里
         */
        QueueHistory qH=new QueueHistory(q);
        queueHistoryDao.save(qH);

        /**
         * 再转换一次格式，将桌位和桌型转换后为id,存储到云端
         */
        QueueInfoDTO queueInfoDTO=new QueueInfoDTO(qH);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("queueInfoDTO",queueInfoDTO);

        RabbitCarrier rabbitCarrier=new RabbitCarrier();
        rabbitCarrier.setServiceName("QueueService");
        rabbitCarrier.setMethodName("saveQueueHistory");
        rabbitCarrier.setParameter(JSONObject.toJSONString(jsonObject));

        try {
            new EmitLog().send(JSONObject.toJSONString(rabbitCarrier));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
