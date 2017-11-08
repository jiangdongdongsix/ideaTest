package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;
import com.iqes.entity.dto.TableTypeDTO;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.TableNumberDao;
import com.iqes.repository.restaurant.TableTypeDao;
import com.iqes.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TableTypeService {

    @Autowired
    private TableTypeDao tableTypeDao;

    @Autowired
    private TableNumberDao tableNumberDao;

    @Autowired
    private QueueManagerDao queueManagerDao;

    public void saveOne(TableType tableType){
        tableTypeDao.save(tableType);
    }

    public void deleteOne(Long id){

        TableType tableType=tableTypeDao.findOne(id);

        if (tableType==null){
            throw new ServiceException("没有此桌类型。");
        }else{
            Integer tableNumbers=tableNumberDao.getNumbersByTableType(id);
            System.out.println(tableNumbers);
            if (tableNumbers!=0){
                throw new ServiceException("有与此桌型绑定的桌子");
            }else{
                tableTypeDao.delete(id);
            }

        }
    }

    public TableType findById(Long id){
        return tableTypeDao.findOne(id);
    }

    public List<TableType> findAll(){
        List<TableType> tableTypes=tableTypeDao.findAll();
        Collections.sort(tableTypes);

        return tableTypes;
    }

    public String updateEatTime(Long id,Integer eatTime){
        String msg="更新失败";
        TableType tableType=tableTypeDao.findOne(id);

        if (tableType!=null) {
            tableType.setEatTime(eatTime);
            tableTypeDao.save(tableType);
            msg="更新用餐时间成功";
        }
        return msg;
    }

    public List<TableTypeDTO> getSomethingAboutTableType(){

        List<TableTypeDTO> tableTypeDTOS=new ArrayList<TableTypeDTO>();
        Integer numbers;
        List<QueueInfo> queueInfos;

        List<TableType> tableTypes=findAll();
        for (TableType tableType:tableTypes){
            TableTypeDTO tableTypeDTO=new TableTypeDTO();
            System.out.println(tableType);
            tableTypeDTO.setTableTypeDescribe(tableType.getDescribe());
            tableTypeDTO.setEatMinNumber(tableType.getEatMinNumber());
            tableTypeDTO.setEatMaxNumber(tableType.getEatMaxNumber());


            numbers=queueManagerDao.getNumbersByTableTypeId(tableType.getId());
            tableTypeDTO.setQueueNumbers(numbers);

            queueInfos=queueManagerDao.getSameTypeNumbersByTableTypeId(tableType.getId());
            String arrivingQueueId=null;
            if (queueInfos!=null) {
                for (QueueInfo q:queueInfos){
                   arrivingQueueId=q.getQueueId();
                    break;
                }
                tableTypeDTO.setArrivingQueueInfo(arrivingQueueId);
            }
            tableTypeDTOS.add(tableTypeDTO);
        }

        return tableTypeDTOS;

    }
}