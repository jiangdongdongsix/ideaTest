package com.iqes.repository.restaurant;

/**
 * 桌子的dao层
 */
import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TableNumberDao extends PagingAndSortingRepository<TableNumber, Long>, JpaSpecificationExecutor<TableNumber> {

    @Query("select t from TableNumber t where t.name=?1")
    TableNumber getByTableName(String tablename);

    /**
     * 根据桌区域名查桌子
     * @param areaName
     * @return
     */
    @Query("select t from TableNumber t where t.restaurantArea.areaName=?1")
    List<TableNumber> findByRestaurantAreaName(String areaName);

    /**
     * 根据状态查
     * @param state
     * @return
     */
    List<TableNumber> findByState(String state);

    TableNumber findTableNumberByName(String name);

    List<TableNumber> findTableNumbersByTableType(TableType tableType);

    @Override
    @Query("select t from TableNumber t  order by t.id DESC ")
    List<TableNumber> findAll();

    /**
     *
     * @param id
     * @return
     */
    List<TableNumber> findByTableTypeId(long id);

    @Query(value = "select count(id) FROM table_number as t WHERE t.state='0' AND t.area_id=?1",nativeQuery = true)
    Integer getByStateAndRestaurantArea(Long areaId);

    /**
     * 根据卓类型id获取匹配的桌子数量
     * @param id
     * @return
     */
    @Query(value = "select count(id) from table_number as t where t.table_type_id=?1",nativeQuery = true)
    Integer getNumbersByTableType(Long id);

}
