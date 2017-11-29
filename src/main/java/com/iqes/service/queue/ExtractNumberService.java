package com.iqes.service.queue;



import com.iqes.entity.*;
import com.iqes.entity.dto.ShareTableDTO;
import com.iqes.repository.queue.QueueHistoryDao;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.ConfigInfoDao;
import com.iqes.repository.restaurant.TableNumberDao;
import com.iqes.repository.restaurant.TableTypeDao;
import com.iqes.service.ServiceException;
import com.iqes.utils.TimeFormatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 抽号service
 * @author huqili.tp
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExtractNumberService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    @Autowired
    private TableNumberDao tableNumberDao;

    @Autowired
    private ConfigInfoDao configInfoDao;

    @Autowired
    private QueueHistoryService queueHistoryService;

    @Autowired
    private TableTypeDao tableTypeDao;

    /**
     * 未抽号状态
     */
    private static final String NO_EXTRACT="0";

    private static final Integer PATTERN_ONE=1;

    private static final Integer PATTERN_TWO=2;

    private static final Integer PATTERN_THREE=3;
    /**
     * @param tablename
     * @return 排队号
     * 抽号的时候主要分为三种模式抽号
     * 模式1.过号即删，不做保留；
     * 模式2.按次数保留，每次叫号都会保留次数，当次数超过某一值时，删除；
     * 模式3.按时间保留，第一次叫号的时候会记录一个时间，以后再叫号的时候会和这个时间做比较，大于某一值时，删除；
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
        List<QueueInfo> queueInfos = queueManagerDao.getSameTypeNumbersByTableTypeId(tNumber.getTableType().getId());
        //获取一个传递变量
        QueueInfo queueInfo = new QueueInfo();

        //判断是否邻桌取号
        if (configInfo.getNextTableExtractFlag()){
            if (queueInfos.size()==0){
                List<Long>  talbeTypeIds=tableTypeDao.getByEatMaxNumberLessThan(tNumber.getTableType().getEatMaxNumber());
                for (Long id:talbeTypeIds){
                    List<QueueInfo> queueInfos1=queueManagerDao.getSameTypeNumbersByTableTypeId(id);
                    if (queueInfos1.size()>0){
                        queueInfos=queueInfos1;
                        break;
                    }
                }
            }
        }

        //按模式进行抽号
        if (PATTERN_ONE.equals(configInfo.getReservePattern())) {
            queueInfo=patternOne(queueInfos,tNumber,queueInfo);
        }else if (PATTERN_TWO.equals(configInfo.getReservePattern())){
            queueInfo=patternTwo(queueInfos,configInfo,tNumber,queueInfo);
        }else if (PATTERN_THREE.equals(configInfo.getReservePattern())){
            queueInfo=patternThree(queueInfos,configInfo,tNumber,queueInfo);
        }

        return queueInfo;
    }

    /**
     *
     * @param queueInfos
     * @param configInfo
     * @param tNumber
     * @param queueInfo
     * @return
     * @throws Exception
     */
    private QueueInfo patternThree(List<QueueInfo> queueInfos, ConfigInfo configInfo, TableNumber tNumber,QueueInfo queueInfo) throws Exception {

        for (QueueInfo q : queueInfos) {
            if ((!q.getSeatFlag()) || (tNumber.equals(q.getTableNumber()))) {
                if ("0".equals(q.getExtractFlag())) {
                    queueInfo=extracting(q,tNumber);
                    break;
                } else {
                    Long diffTime = TimeFormatTool.diffTime(q.getFirstExtractTime());
                    System.out.println(diffTime);
                    if (configInfo.getReserveTime() < diffTime / 60000) {
                        deleteNumber(q);
                    } else {
                        q.setExtractFlag("0");
                        q.setTableNumber(null);
                        queueManagerDao.save(q);
                    }
                }
            }
        }
        return queueInfo;
    }

    /**
     *
     * @param queueInfos
     * @param configInfo
     * @param tNumber
     * @param queueInfo
     * @return
     */
    private QueueInfo patternTwo(List<QueueInfo> queueInfos, ConfigInfo configInfo, TableNumber tNumber,QueueInfo queueInfo) {
        for (QueueInfo q : queueInfos) {
            if ((!q.getSeatFlag()) || (tNumber.equals(q.getTableNumber()))) {
                if (q.getExtractCount() < configInfo.getExtractCount()) {
                    if (NO_EXTRACT.equals(q.getExtractFlag())) {
                        queueInfo=extracting(q,tNumber);
                        break;
                    } else {
                        q.setExtractFlag("0");
                        q.setTableNumber(null);
                        queueManagerDao.save(q);
                    }
                } else {
                    deleteNumber(q);
                }
            }
        }
        return queueInfo;
    }

    /**
     *
     * @param queueInfos
     * @param tNumber
     * @param queueInfo
     * @return
     */
    private QueueInfo patternOne(List<QueueInfo> queueInfos,TableNumber tNumber,QueueInfo queueInfo) {
        for (QueueInfo q : queueInfos) {
            if ((!q.getSeatFlag()) || (tNumber.equals(q.getTableNumber()))) {
                    if ("0".equals(q.getExtractFlag())) {
                        queueInfo=extracting(q,tNumber);
                        break;
                    } else {
                        deleteNumber(q);
                    }
                }
            }
        return queueInfo;
    }

    /**
     * 抽号
     * @param q
     * @param tNumber
     * @return
     */
    private QueueInfo extracting(QueueInfo q,TableNumber tNumber){
            q.setExtractFlag("1");
            q.setExFlag(true);
            q.setExtractCount(q.getExtractCount() + 1);
            if (q.getExtractCount() == 1) {
                q.setFirstExtractTime(TimeFormatTool.getCurrentTime());
            }
            if (q.getTableNumber() == null) {
                q.setTableNumber(tNumber);
            }
            queueManagerDao.save(q);
            return q;
    }

    /**
     *
     * @param q
     */
    private void deleteNumber(QueueInfo q){
        queueHistoryService.saveAndAddToCloud(q);
        System.out.println("已删除！！");
    }


    /**
     *删除排队号，如果有桌子，就置为就餐中
     * @param queueInfoid
     * @return
     */
    public String deleteNumberById(Long queueInfoid){
        String res = "该号码已删除";
        QueueInfo queueInfo=queueManagerDao.getById(queueInfoid);
        if(null!= queueInfo) {
            if (queueInfo.getTableNumber()!=null){
                queueInfo.getTableNumber().setState("1");
                tableNumberDao.save(queueInfo.getTableNumber());
            }
            deleteNumber(queueInfo);
            res="删除成功";
        }
        return res;
    }


    /**
     *
     * @param pageNo
     * @param pageSize
     * @param tableTypeDescribe
     * @return
     */
    public Page<QueueInfo> pageQuery(int pageNo, int pageSize, final String tableTypeDescribe){

        Sort.Order order=new Sort.Order(Sort.Direction.ASC,"id");

        Sort sort=new Sort(order);

        //默认是存0页开始的
        pageNo=pageNo-1;
        PageRequest pageable = new PageRequest(pageNo, pageSize,sort);

        //通常使用 Specification 的匿名内部类
        Specification<QueueInfo> specification = new Specification<QueueInfo>() {
            @Override
            public Predicate toPredicate(Root<QueueInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<QueueInfo,TableType> join=root.join("tableType",JoinType.INNER);
                Path<String> exp4=join.get("describe");
                return cb.like(exp4,tableTypeDescribe);

            }
        };

        Page<QueueInfo> page = queueManagerDao.findAll(specification, pageable);

        return page;
    }

    /**
     * 根据桌描述获取所有排队号
     * @param describe
     * @return
     */
    public List<QueueInfo> getQueueInfosByTableTypeDescribe(String describe){
        TableType tableType=tableTypeDao.getByDescribe(describe);
        List<QueueInfo> queueInfoList;
        if (tableType!=null){
            queueInfoList=queueManagerDao.getByTableType(tableType.getId());
        }else {
            throw new ServiceException("无此桌型");
        }
        return queueInfoList;
    }


    /**
     * 拼桌
     * @param tables
     * @param queueInfos
     * @return
     */
    public ShareTableDTO shareTable(String tables,String queueInfos){

        String[] tableStr=tables.split(",");
        String[] queueStr=queueInfos.split(",");

        TableNumber tableNumber=null;
        QueueInfo queueInfo=null;

        for (int j=0;j<queueStr.length;j++){
            StringBuilder sb=new StringBuilder();
            queueInfo=queueManagerDao.getByQueueId(queueStr[j]);

            if (queueInfo==null){
                throw new ServiceException("没有"+queueStr[j]+"这个号呀！");
                }
            for (int i=0;i<tableStr.length;i++){
                tableNumber=tableNumberDao.getByTableName(tableStr[i]);
                if (tableNumber==null){
                    throw new ServiceException("没有"+tableStr[i]+"这个桌子呀！");
                }
                sb.append(tableNumber.getId());
                if ((i + 1)<tableStr.length) {
                    sb.append(",");
                }
            }

            queueInfo.setShareTalbeState("1");
            queueInfo.setExtractFlag("1");
            queueInfo.setTables(sb.toString());
        }

        ShareTableDTO shareTableDTO=new ShareTableDTO();
        shareTableDTO.setQueueInfos(queueInfos);
        shareTableDTO.setTables(tables);

        return shareTableDTO;
    }
}


