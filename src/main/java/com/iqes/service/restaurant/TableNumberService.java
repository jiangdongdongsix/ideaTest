package com.iqes.service.restaurant;

import com.iqes.entity.QueueInfo;
import com.iqes.entity.RestaurantArea;
import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.entity.dto.TableNumberDTO;
import com.iqes.repository.queue.QueueManagerDao;
import com.iqes.repository.restaurant.RestaurantAreaDao;
import com.iqes.repository.restaurant.TableNumberDao;
import com.iqes.repository.restaurant.TableTypeDao;
import com.iqes.service.ServiceException;
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
 * @author 54312
 *
 */

@Service
@Transactional
public class TableNumberService {

    @Autowired
    private TableNumberDao tableNumberDao;

    @Autowired
    private TableTypeDao tableTypeDao;

    @Autowired
    private RestaurantAreaDao restaurantAreaDao;

    @Autowired
    private QueueManagerDao queueManagerDao;

   public void saveOne(TableNumberDTO tableNumberDTO){

       System.out.println(tableNumberDTO);

       TableType tableType=tableTypeDao.getByDescribe(tableNumberDTO.getTableTypeDescribe());
       if (tableType==null){
           throw new ServiceException("无此桌类型");
       }

       RestaurantArea restaurantArea=restaurantAreaDao.findByAreaName(tableNumberDTO.getArea());
       if (restaurantArea==null){
           throw new ServiceException("无此区域");
       }

       TableNumber tableNumber=new TableNumber();

       if (tableNumberDTO.getId()!=null) {
           tableNumber.setId(tableNumberDTO.getId());
       }

       tableNumber.setName(tableNumberDTO.getTableName());
       tableNumber.setState("0");
       tableNumber.setRestaurantArea(restaurantArea);
       tableNumber.setTableType(tableType);

       tableNumberDao.save(tableNumber);
   }


    public void deleteOne(Long id){
        TableNumber tableNumber=tableNumberDao.findOne(id);
        List<QueueInfo> queueInfoList=queueManagerDao.getByTableNumber(id);
        System.out.println(queueInfoList.size());

        if (tableNumber==null) {
            throw new RuntimeException("删除失败，因为就没有这个桌子呀！");
        }else if(queueInfoList.size()!=0){
            throw new RuntimeException("删除失败，因为有顾客要坐在这个桌子就餐！");
        }
        tableNumberDao.delete(id);
    }

    public TableNumber findById(Long id){
        return tableNumberDao.findOne(id);
    }

    /**
     * 根据桌名去查找
     * @param tablename
     * @return
     */
    public TableNumberDTO findByName(String tablename){

        TableNumber tableNumber=tableNumberDao.findTableNumberByName(tablename);
        if (tableNumber==null){
            throw new ServiceException("查无此桌！！");
        }
        return convertDTO(tableNumber);
    }

    /**
     * 根据桌子的状态查找
     * @param state
     * @return
     */
    public List<TableNumberDTO> findByState(String state){
        List<TableNumber> tableNumberList=tableNumberDao.findByState(state);
        return convertToDTOS(tableNumberList);
    }

    /**
     * 根据区域返回
     * @param areaName
     * @return
     */
    public List<TableNumberDTO> findByArea(String areaName){
        return convertToDTOS(tableNumberDao.findByRestaurantAreaName(areaName));
    }


    /**
     * 根据桌型描述去查桌子
     * 大桌 中桌 小桌
     * @param tableTypeDesribe
     * @return
     */
    public List<TableNumberDTO> findByTableType(String tableTypeDesribe){
        TableType tableType=tableTypeDao.getByDescribe(tableTypeDesribe);
        List<TableNumber> tableNumberList=tableNumberDao.findByTableTypeId(tableType.getId());

        return convertToDTOS(tableNumberList);
    }

    /**
     * 所有的桌子
     * @return
     */
    public List<TableNumberDTO> findAll(){
        List<TableNumber> tableNumberList=tableNumberDao.findAll();
        return convertToDTOS(tableNumberList);
    }

    /**
     * 返回所有桌子的分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<TableNumber> pageQuery(int pageNo, int pageSize){

        /**
         * 获取排序sort
         */
        Sort.Order order=new Sort.Order(Sort.Direction.ASC,"id");
        Sort sort=new Sort(order);
        /**
         * 默认第一页是存0页开始，故减1
         */
        PageRequest pageable = new PageRequest(pageNo-1, pageSize,sort);

        Page<TableNumber> page = tableNumberDao.findAll(pageable);
        return page;
    }

    /**
     * 根据桌所在的区域返回page对象
     * @param pageNo
     * @param pageSize
     * @param area
     * @return
     */
    public Page<TableNumber> pageQueryByArea(int pageNo, int pageSize, final String area){

        Sort.Order order=new Sort.Order(Sort.Direction.ASC,"id");
        Sort sort=new Sort(order);

        PageRequest pageable = new PageRequest(pageNo-1, pageSize,sort);

        //通常使用 Specification 的匿名内部类
        Specification<TableNumber> specification = new Specification<TableNumber>() {
            @Override
            public Predicate toPredicate(Root<TableNumber> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<String> _area = root.get("area");
                Predicate _key = criteriaBuilder.like(_area, "%" + area + "%");
                return criteriaBuilder.and(_key);
            }
        };

        Page<TableNumber> page = tableNumberDao.findAll(specification, pageable);
        return page;
    }

    /**
     * 根据桌类型返回page对象
     * @param pageNo
     * @param pageSize
     * @param tableTypeName
     * @return
     */
    public Page<TableNumber> pageQueryByTableType(int pageNo, int pageSize, final String tableTypeName){

        Sort.Order order=new Sort.Order(Sort.Direction.ASC,"id");
        Sort sort=new Sort(order);

        PageRequest pageable = new PageRequest(pageNo-1, pageSize,sort);

        Specification<TableNumber> specification = new Specification<TableNumber>() {
            @Override
            public Predicate toPredicate(Root<TableNumber> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<TableNumber,TableType> join=root.join("tableType",JoinType.INNER);
                Path<String> exp4=join.get("tableTypeName");
                return cb.like(exp4,tableTypeName);
            }
        };
        Page<TableNumber> page = tableNumberDao.findAll(specification, pageable);
        return page;
    }

    public String updateTbleState(Long id,String state){
        String msg="查无此桌";
        TableNumber tableNumber=tableNumberDao.findOne(id);
        if (tableNumber!=null){
            tableNumber.setState(state);
            msg="修改成功";
        }
        return msg;
    }

    public Integer findTableNumbersByArea(RestaurantArea area){
        return tableNumberDao.getByStateAndRestaurantArea(area.getId());
    }

    /**
     * 把查询出来的list数据封装到DTOs
     * @param tableNumberList
     * @return
     */

    private List<TableNumberDTO> convertToDTOS(List<TableNumber> tableNumberList){
        List<TableNumberDTO> tableNumberDTOS=new ArrayList<TableNumberDTO>();

        for (TableNumber t:tableNumberList){
            TableNumberDTO tableNumberDTO=new TableNumberDTO();

            tableNumberDTO.setArea(t.getRestaurantArea().getAreaName());
            tableNumberDTO.setEatMaxNumber(t.getTableType().getEatMaxNumber());
            tableNumberDTO.setTableName(t.getName());
            tableNumberDTO.setTableTypeDescribe(t.getTableType().getDescribe());
            tableNumberDTO.setState(t.getState());
            tableNumberDTO.setId(t.getId());

            tableNumberDTOS.add(tableNumberDTO);
        }
        return tableNumberDTOS;
    }

    private TableNumberDTO convertDTO(TableNumber t){
        TableNumberDTO tableNumberDTO=new TableNumberDTO();

        tableNumberDTO.setArea(t.getRestaurantArea().getAreaName());
        tableNumberDTO.setEatMaxNumber(t.getTableType().getEatMaxNumber());
        tableNumberDTO.setTableName(t.getName());
        tableNumberDTO.setTableTypeDescribe(t.getTableType().getDescribe());
        tableNumberDTO.setState(t.getState());
        tableNumberDTO.setId(t.getId());

        return tableNumberDTO;
    }
}
