package com.iqes.service.restaurant;

import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.repository.restaurant.TableNumberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableNumberService {

    private TableNumberDao tableNumberDao;

    public void saveOne(TableNumber tableNumber){
        tableNumberDao.save(tableNumber);
    }

    public void deleteOne(Long id){
        tableNumberDao.delete(id);
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
