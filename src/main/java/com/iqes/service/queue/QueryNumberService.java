package com.iqes.service.queue;

/**
 * 叫号查询的service
 * @author  huqili
 */

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.TableTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    @Autowired
    private TableTypeDao tableTypeDao;

    public QueueInfo queryNumber(){
        List<QueueInfo> queueInfos=queueManagerDao.getArrivingNumbers();
        QueueInfo queueInfo=new QueueInfo();

        for (QueueInfo q:queueInfos){
            //判断叫号次数
            if(q.getCallCount()<3){
                q.setCallCount(q.getCallCount()+1);
                queueManagerDao.save(q);
                queueInfo=q;
                break;
            }
        }
        return queueInfo;
    }

    public  Map<String,List<QueueInfo>> getArrivingNumberByTableType(){

        List<Long> tableTypeIds=tableTypeDao.getAllTableTypeId();
        List<QueueInfo> queueInfoList=null;
        Map<String,List<QueueInfo>> listMap=new HashMap<String, List<QueueInfo>>(10);

        for (Long id:tableTypeIds){
            queueInfoList=queueManagerDao.getByExtractFlagAndAndTableType(id);
            String name="tableType"+"_"+id;
            listMap.put(name,queueInfoList);
        }
        return listMap;
    }
}
