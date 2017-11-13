package com.iqes.service.queue;

/**
 * 叫号查询的service
 * @author  huqili
 */

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.entity.dto.ShareTableDTO;
import com.iqes.entity.dto.TableNumberDTO;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.TableNumberDao;
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

    @Autowired
    private TableNumberDao tableNumberDao;

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
                StringBuilder sb=new StringBuilder();

                for (QueueInfo queueInfo1:shareTableQueues){
                    stringBuilder.append(queueInfo1.getQueueId());
                    stringBuilder.append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);


                String[] tableStr=q.getTables().split(",");
                for (int i=0;i<tableStr.length;i++){
                    TableNumber tableNumber=tableNumberDao.findOne(Long.valueOf(tableStr[i]));
                    sb.append(tableNumber.getName());
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length()-1);

                /** 保存*/
                shareTableDTO.setQueueInfos(stringBuilder.toString());
                shareTableDTO.setTables(sb.toString());

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

    public List<QueueInfo> findAllQueueInfos(){
        List<QueueInfo> queueInfos=queueManagerDao.getAllByQueueState("1");
        if (queueInfos.size()==0){
            throw new ServiceException("是不是没有人排队呀~");
        }
        return queueInfos;
    }
}
