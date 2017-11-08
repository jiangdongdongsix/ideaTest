package com.iqes.service.queue;

/**
 * 叫号查询的service
 * @author  huqili
 */

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;
import com.iqes.entity.dto.ShareTableDTO;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.TableTypeDao;
import com.iqes.service.ServiceException;
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

    public ShareTableDTO queryNumber(){
        List<QueueInfo> queueInfos=queueManagerDao.getArrivingNumbers();
        final String shareTalbeFlag="1";
        if (queueInfos.size()==0){
            throw new ServiceException("当前无需要被叫号的！");
        }

        ShareTableDTO shareTableDTO=new ShareTableDTO();

        for (QueueInfo q:queueInfos){
            /** 判断是否有拼桌的*/
            if(shareTalbeFlag.equals(q.getShareTalbeState())){
                List<QueueInfo> shareTableQueues=queueManagerDao.getByTables(q.getTables());
                StringBuilder stringBuilder=new StringBuilder();

                for (QueueInfo queueInfo1:shareTableQueues){
                    stringBuilder.append(queueInfo1.getQueueId());
                    stringBuilder.append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                shareTableDTO.setTables(q.getTables());
                shareTableDTO.setQueueInfos(stringBuilder.toString());
                break;
            }else {
                shareTableDTO.setQueueInfos(q.getQueueId());
                shareTableDTO.setTables(q.getTableNumber().getName());
                break;
            }
            }
            return shareTableDTO;
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
