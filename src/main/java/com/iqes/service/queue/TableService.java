package com.iqes.service.queue;

import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;

import com.iqes.repository.restaurant.TableNumberDao;
import com.iqes.repository.restaurant.TableTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableTypeDao tableTypeDao;
    @Autowired
    private TableNumberDao tableNumberDao;


    public List<TableType> getTableTypeByEatNum(Integer eatNum) throws Exception{
        return tableTypeDao.getTableType(eatNum);
    }

    public Integer getTableCountByType(long tableTypeId) throws Exception{
        return tableTypeDao.getTableCountByType(tableTypeId);
    }


    public TableNumber getByTableName(String tablename) throws Exception{
        return tableNumberDao.getByTableName(tablename);
    }

    //根据桌型id获得
    public List<TableNumber> findByTableTypeId(long id) throws Exception{
        return tableNumberDao.findByTableTypeId(id);
    }

    //根据桌型id获得该桌型的没桌用餐时间
    public long findEatTimeById(long id){
        return tableTypeDao.findEatTimeById(id);
    }

    //获取所有桌型
    public List<TableType> findAll(){
        return tableTypeDao.findAll();
    }



}
