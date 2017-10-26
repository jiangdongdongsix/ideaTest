package com.iqes.service.restaurant;

import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.repository.restaurant.TableNumberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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
}
