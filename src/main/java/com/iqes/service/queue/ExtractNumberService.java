package com.iqes.service.queue;

import com.iqes.entity.QueueInfo;
import com.iqes.repository.queue.QueueManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ExtractNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    public List<QueueInfo> getSameTypeNumber(String tName){
        return queueManagerDao.getSameTypeNumbers(tName);
    }

    public QueueInfo ExtractNumber(String tableName){
         List<QueueInfo> queueInfoList=getSameTypeNumber(tableName);
         QueueInfo queueInfo=new QueueInfo();
         for(QueueInfo q:queueInfoList){
                if (q.getExtractCount()<3){
                    if ("0".equals(q.getExtractFlag())){
                        q.setExtractFlag("1");
                        queueManagerDao.save(q);
                        queueInfo = q;
                        break;
                    }else {
                        q.setExtractFlag("0");
                        continue;
                    }
                }else {
                    queueManagerDao.delete(q);
                }
         }
        return queueInfo;

    }



}
