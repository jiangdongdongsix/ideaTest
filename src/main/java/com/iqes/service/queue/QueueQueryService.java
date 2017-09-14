package com.iqes.service.queue;

import com.iqes.entity.QueueInfo;
import com.iqes.repository.queue.QueueQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QueueQueryService {

    @Autowired
    private QueueQueryDao queueQueryDao;

    public long save(QueueInfo queueInfo) throws Exception {
        QueueInfo queueInfoVo = queueQueryDao.save(queueInfo);
        return queueInfoVo.getId();
    }

    public void update(QueueInfo queueInfo) throws Exception {
        QueueInfo queueInfoVo = queueQueryDao.saveAndFlush(queueInfo);
    }

    public long getWaitCountById(long id,long tableTypeId) throws Exception{
        return  queueQueryDao.getWaitCountById(id,tableTypeId);
    }

    public void updateStateAndTel(long id,String tel,String state){
        queueQueryDao.updateStateAndTel(id,tel,state);
    }

    public QueueInfo findById(long id) throws Exception{
        return  queueQueryDao.findOne(id);
    }

    public void delete(long id) throws Exception{
        queueQueryDao.delete(id);
    }
    //根据排队id和桌型id来获取选坐排队人数
    public long chooseSeatCountById(Long id,Long tableTypeId){
        return  queueQueryDao.chooseSeatCountById(id,tableTypeId);
    }

    //未选坐的排队人数
    public long nochooseCountById(Long id,Long tableTypeId){
        return queueQueryDao.nochooseCountById(id,tableTypeId);
    }

    //根据排队id和桌型id来获取排队人数（整个队形）
    public long getWaitCount(Long tableTypeId){
        return queueQueryDao.getWaitCount(tableTypeId);
    }

    //根据桌型id来获取选坐排队人数（整个队形）
    public long chooseSeatCountByTableTypeId(Long tableTypeId){
        return queueQueryDao.chooseSeatCountByTableTypeId(tableTypeId);
    }


}
