package com.iqes.service.queue;

/**
 * 抽号service
 */

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.queue.TableNumberDao;
import com.iqes.repository.queue.TempHotelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TempExtractNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    @Autowired
    private TableNumberDao tableNumberDao;

    @Autowired
    private TempHotelDao tempHotelDao;

    //从dao层获取同桌类型的排队顾客
    public List<QueueInfo> getSameTypeNumber(String tName){ return queueManagerDao.getSameTypeNumbers(tName);
    }

    //获取一个桌子的信息
    public TableNumber getTableNumber(String tableName){return tableNumberDao.getByTableName(tableName);
    }

    //获取是否保留号码的标志
    public  Boolean getReserveFlag(){ return tempHotelDao.getReserveFlag();
    }

    //获取保留次数
    public Integer getExtractCount(){return tempHotelDao.getExtractCount();
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

        List<QueueInfo> queueInfoList= getSameTypeNumber(tableName);
        TableNumber tableNumber=getTableNumber(tableName);
        Boolean reserveFlag=getReserveFlag();
        Integer extractCount=getExtractCount();

        QueueInfo queueInfo=new QueueInfo();


        //需要交换的标志
        int ExchangeFlag=0;

        for(QueueInfo q:queueInfoList) {
            //先判断桌号符合不符合,可以是桌号相符的，或者桌号不确定的
            if ((!q.getSeatFlag())||(tableNumber.getId()==(q.getTableNumber().getId()))){
                //判断抽号次数
                if (q.getExtractCount() < extractCount) {
                    //判断抽号标志
                    if ("0".equals(q.getExtractFlag())) {
                        q.setExtractFlag("1");
                        q.setExtractCount(q.getExtractCount() + 1);
                        if (q.getTableNumber()==null) {
                            q.setTableNumber(tableNumber);
                        }
                        queueInfo = q;
                        if (ExchangeFlag == 1) {
                            exchangeNumbers(q);
                        } else {
                            queueManagerDao.save(q);
                        }
                        break;
                    } else {
                        if (reserveFlag){
                            ExchangeFlag = 1;
                        }else {
                            queueManagerDao.delete(q);
                        }
                    }
                } else {
                    queueManagerDao.delete(q);
                }
            }
        }
        return queueInfo;
    }
}
