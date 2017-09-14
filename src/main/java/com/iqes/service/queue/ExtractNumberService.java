package com.iqes.service.queue;

/**
 * 抽号service
 */

import com.iqes.entity.ConfigInfo;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.ConfigInfoDao;
import com.iqes.repository.restaurant.TableNumberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExtractNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    @Autowired
    private TableNumberDao tableNumberDao;

    @Autowired
    private ConfigInfoDao configInfoDao;

    public QueueInfo extractNumber(String tablename){

        TableNumber tNumber=tableNumberDao.findByName(tablename);

        if (tNumber==null){
            return null;
        }

        ConfigInfo configInfo=configInfoDao.findOne((long)1);
        List<QueueInfo> queueInfos=queueManagerDao.getSameTypeNumbers(tablename);
        QueueInfo queueInfo=new QueueInfo();
        int exchangeFlag=0;

        for (QueueInfo q:queueInfos){

            if ((!q.getSeatFlag())||(tNumber.getId()==(q.getTableNumber().getId()))){
              if (configInfo.getReservePattern()==0) {
                  if ("0".equals(q.getExtractFlag())) {
                      q.setExtractFlag("1");
                      q.setExtractCount(q.getExtractCount() + 1);
                      if (q.getTableNumber() == null) {
                          q.setTableNumber(tNumber);
                      }
                      break;
                  } else {
                      queueManagerDao.delete(q);
                  }
              }else if (configInfo.getReservePattern()==1){
                  if (q.getExtractCount() < configInfo.getExtractCount()) {
                      //判断抽号标志
                      if ("0".equals(q.getExtractFlag())) {
                          q.setExtractFlag("1");
                          q.setExtractCount(q.getExtractCount() + 1);
                          if (q.getTableNumber()==null) {
                              q.setTableNumber(tNumber);
                          }
                          queueInfo = q;
                          if (exchangeFlag == 1) {
                              exchangeNumbers(q);
                          } else {
                              queueManagerDao.save(q);
                          }
                          break;
                      } else {
                              exchangeFlag = 1;
                      }
                  } else {
                      queueManagerDao.delete(q);
                  }
              }else{
                  //这里是按时长保留的方法块
              }
            }
        }


        return queueInfo;
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

}
