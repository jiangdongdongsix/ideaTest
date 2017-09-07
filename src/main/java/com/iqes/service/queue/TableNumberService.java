package com.iqes.service.queue;

import com.iqes.entity.TableNumber;
import com.iqes.repository.queue.TableNumberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableNumberService {

    @Autowired
    private TableNumberDao tableNumberDao;

    public void addTableNumber(TableNumber tableNumber){
        tableNumberDao.save(tableNumber);
    }

    public void deleteTableNumber(Long id){
        tableNumberDao.delete(id);
    }

    public TableNumber findOne(Long id){
        return  tableNumberDao.findOne(id);
    }

    public List<TableNumber> findAll(){
        return (List<TableNumber>) tableNumberDao.findAll();
    }
}
