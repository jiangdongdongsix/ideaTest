package com.iqes.service.queue;

import com.iqes.entity.TableType;
import com.iqes.repository.TableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableDao tableDao;


    public List<TableType> getTableTypeByEatNum(Integer eatNum){
      return tableDao.getTableType(eatNum);
    }


}
