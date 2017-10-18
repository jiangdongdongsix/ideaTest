package com.iqes.service.restaurant;

import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.repository.restaurant.TableNumberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableNumberService {

    @Autowired
    private TableNumberDao tableNumberDao;

    public void saveOne(TableNumber tableNumber){
        tableNumberDao.save(tableNumber);
    }

    public String deleteOne(Long id){
        TableNumber tableNumber=tableNumberDao.findOne(id);
        String msg="删除成功";

        if (tableNumber!=null) {
            tableNumberDao.delete(id);
        }else{
            msg="删除失败";
        }
        return msg;
    }

    public TableNumber findById(Long id){
        return tableNumberDao.findOne(id);
    }

    public TableNumber findByName(String tablename){
        return tableNumberDao.findTableNumberByName(tablename);
    }

    public List<TableNumber> findByTableType(TableType tableType){
        return  tableNumberDao.findTableNumbersByTableType(tableType);
    }

    public List<TableNumber> findAll(){
        return tableNumberDao.findAll();
    }
}
