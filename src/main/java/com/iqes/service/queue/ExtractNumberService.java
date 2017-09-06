package com.iqes.service.queue;

/**
 * 抽号service
 */

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.repository.queue.QueueManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExtractNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    //从dao层获取同桌类型的排队顾客
    public List<QueueInfo> getSameTypeNumber(String tName){
        return queueManagerDao.getSameTypeNumbers(tName);
    }

    //排队号的交换
    public void exchangeNumbers(QueueInfo a){

        QueueInfo b=queueManagerDao.getByIdIs(a.getId()-1);
        b.setExtractFlag("0");
        b.setCallCount(0);

        Long id=b.getId();
        b.setId(a.getId());
        a.setId(id);

        queueManagerDao.save(a);
        queueManagerDao.save(b);
    }

    public QueueInfo ExtractNumber(String tableName){
        //用一个list接
         List<QueueInfo> queueInfoList= getSameTypeNumber(tableName);
        TableNumber tableNumber=new TableNumber();

         QueueInfo queueInfo=new QueueInfo();
         QueueInfo qInfo=new QueueInfo();

         //需要交换的标志
        int ExchanggeFlag=0;

         for(QueueInfo q:queueInfoList){
               if (q.getTableNumber()==tableNumber||q.getTableNumber().equals(null))
                //判断抽号次数
                if (q.getExtractCount()<3){
                    //判断抽号标志
                    if ("0".equals(q.getExtractFlag())){
                        q.setExtractFlag("1");
                        q.setExtractCount(q.getExtractCount()+1);
                        queueInfo = q;

                        if (ExchanggeFlag==1){
                            exchangeNumbers(q);
                        }else{
                            queueManagerDao.save(q);
                        }
                        break;
                    }else {
                        //q.setExtractFlag("0");
                        //q.setCallCount(0);
                        ExchanggeFlag=1;
                    }
                }else {
                    queueManagerDao.delete(q);
                }
         }
        return queueInfo;
    }

}
