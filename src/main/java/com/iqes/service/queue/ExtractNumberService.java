package com.iqes.service.queue;



import com.iqes.entity.*;
import com.iqes.repository.queue.QueueHistoryDao;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.ConfigInfoDao;
import com.iqes.repository.restaurant.TableNumberDao;
import com.iqes.utils.TimeFormatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * 抽号service
 * @author huqili.tp
 *
 */
@Service
@Transactional
public class ExtractNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    @Autowired
    private TableNumberDao tableNumberDao;

    @Autowired
    private ConfigInfoDao configInfoDao;

    @Autowired
    private QueueHistoryDao queueHistoryDao;

    /**
     * @param tablename
     * @return 排队号
     * 抽号的时候主要分为三种模式抽号
     * 1.过号即删，不做保留；
     * 2.按次数保留，每次叫号都会保留次数，当次数超过某一值时，删除；
     * 3.按时间保留，第一次叫号的时候会记录一个时间，以后再叫号的时候会和这个时间做比较，大于某一值时，删除；
     */

    public QueueInfo extractNumber(String tablename) throws Exception {

        //获取桌子信息
        TableNumber tNumber = tableNumberDao.findTableNumberByName(tablename);
        if (tNumber == null) {
            return null;
        }
        //获取配置信息
        ConfigInfo configInfo = configInfoDao.findOne((long) 1);
        //获取同类型的排队号
        List<QueueInfo> queueInfos = queueManagerDao.getSameTypeNumbers(tablename);
        //获取一个传递变量
        QueueInfo queueInfo = new QueueInfo();

        //按模式进行抽号
        if (configInfo.getReservePattern()==1) {
            queueInfo=patternOne(queueInfos,configInfo,tNumber,queueInfo);
        }else if (configInfo.getReservePattern()==2){
            queueInfo=patternTwo(queueInfos,configInfo,tNumber,queueInfo);
        }else {
            queueInfo=patternThree(queueInfos,configInfo,tNumber,queueInfo);
        }
        return queueInfo;
    }

    //按时间保留的模式
    private QueueInfo patternThree(List<QueueInfo> queueInfos, ConfigInfo configInfo, TableNumber tNumber,QueueInfo queueInfo) throws Exception {

        for (QueueInfo q : queueInfos) {
            if ((!q.getSeatFlag()) || (tNumber.equals(q.getTableNumber()))) {
                if ("0".equals(q.getExtractFlag())) {
                    q.setExtractFlag("1");
                    q.setExtractCount(q.getExtractCount() + 1);
                    if (q.getExtractCount() == 1) {
                        q.setFirstExtractTime(TimeFormatTool.getCurrentTime());
                    }
                    if (q.getTableNumber() == null) {
                        q.setTableNumber(tNumber);
                    }
                    queueInfo = q;
                    break;
                } else {
                    Long diffTime = TimeFormatTool.diffTime(q.getFirstExtractTime());
                    if (configInfo.getReserveTime() < diffTime / 60000) {
                        deleteNumber(q);
                    } else {
                        q.setExtractFlag("0");
                        queueManagerDao.save(q);
                    }
                }
            }
        }
        return queueInfo;
    }

    //按次数保留的模式
    private QueueInfo patternTwo(List<QueueInfo> queueInfos, ConfigInfo configInfo, TableNumber tNumber,QueueInfo queueInfo) {
      // int exchangeFlag=0;
        for (QueueInfo q : queueInfos) {
            if ((!q.getSeatFlag()) || (tNumber.equals(q.getTableNumber()))) {
                if (q.getExtractCount() < configInfo.getExtractCount()) {
                    //判断抽号标志
                    if ("0".equals(q.getExtractFlag())) {
                        q.setExtractFlag("1");
                        q.setExtractCount(q.getExtractCount() + 1);
                        if (q.getTableNumber() == null) {
                            q.setTableNumber(tNumber);
                        }
                        queueManagerDao.save(q);
                        queueInfo = q;
                        break;
                    } else {
                        q.setExtractFlag("0");
                        queueManagerDao.save(q);
                    }
                } else {
                    deleteNumber(q);
                }
            }
        }
        return queueInfo;
    }

    //过号即删的模式
    private QueueInfo patternOne(List<QueueInfo> queueInfos,ConfigInfo configInfo,TableNumber tNumber,QueueInfo queueInfo) {
        for (QueueInfo q : queueInfos) {
            if ((!q.getSeatFlag()) || (tNumber.equals(q.getTableNumber()))) {
                    if ("0".equals(q.getExtractFlag())) {
                        q.setExtractFlag("1");
                        q.setExtractCount(q.getExtractCount() + 1);
                        if (q.getTableNumber() == null) {
                            q.setTableNumber(tNumber);
                        }
                        queueInfo=q;
                        break;
                    } else {
                        deleteNumber(q);
                    }
                }
            }
        return queueInfo;
    }

    //删除排队号的方法，需更新endTime,存入历史记录表，排队表中删除
    public void deleteNumber(QueueInfo q){
        q.setQueueEndTime(TimeFormatTool.getCurrentTime());
        q.setQueueState("3");
        QueueHistory qH=new QueueHistory(q);
        queueHistoryDao.save(qH);
        queueManagerDao.delete(q);
    }

    //排队号的交换
//
//    public void exchangeNumbers(QueueInfo q){
//
//        QueueInfo temp=queueManagerDao.getById(q.getId()-1);
//        temp.setExtractFlag("0");
//        temp.setCallCount(0);
//
//
//        Long id=temp.getId();
//        temp.setId(q.getId());
//        q.setId(id);
//
//        queueManagerDao.save(temp);
//        queueManagerDao.save(q);
//    }

    //分页查询
    public Page<QueueInfo> pageQuery(int pageNo, int pageSize, final String tableTypeName){

        Sort.Order order=new Sort.Order(Sort.Direction.ASC,"id");

        Sort sort=new Sort(order);

        PageRequest pageable = new PageRequest(pageNo, pageSize,sort);

        //通常使用 Specification 的匿名内部类
        Specification<QueueInfo> specification = new Specification<QueueInfo>() {
            @Override
            public Predicate toPredicate(Root<QueueInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<QueueInfo,TableType> join=root.join("tableType",JoinType.INNER);
                Path<String> exp4=join.get("tableTypeName");
                return cb.like(exp4,tableTypeName);

            }
        };

        Page<QueueInfo> page = queueManagerDao.findAll(specification, pageable);

        return page;
    }


}
