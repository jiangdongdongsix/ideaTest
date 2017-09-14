package com.iqes.service.queue;

import com.iqes.entity.TableType;
import com.iqes.repository.restaurant.TableTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableTypeDao tableTypeDao;


    public List<TableType> getTableTypeByEatNum(Integer eatNum){
      return tableTypeDao.getTableType(eatNum);
    }

    public Integer getTableCountByType(long tableTypeId){
        return tableTypeDao.getTableCountByType(tableTypeId);
    }


}
