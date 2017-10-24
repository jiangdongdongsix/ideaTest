package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.TableType;
import com.iqes.repository.restaurant.TableTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableTypeService {

    @Autowired
    private TableTypeDao tableTypeDao;

    public void saveOne(TableType tableType){
        tableTypeDao.save(tableType);
    }

    public String deleteOne(Long id){

        TableType tableType=tableTypeDao.findOne(id);
        String msg="删除成功";

        if (tableType!=null){
            tableTypeDao.delete(id);
        }else{
            msg= "删除失败!";
        }
        return msg;
    }

    public TableType findById(Long id){
        return tableTypeDao.findOne(id);
    }

    public List<TableType> findAll(){
      return tableTypeDao.findAll();
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
}