package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.TableType;
import com.iqes.repository.restaurant.TableNumberDao;
import com.iqes.repository.restaurant.TableTypeDao;
import com.iqes.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableTypeService {

    @Autowired
    private TableTypeDao tableTypeDao;

    @Autowired
    private TableNumberDao tableNumberDao;

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