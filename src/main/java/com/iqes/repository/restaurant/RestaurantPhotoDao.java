package com.iqes.repository.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.RestaurantPhoto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantPhotoDao extends CrudRepository<RestaurantPhoto,Long> {

    /**
     * 查询所有
     * @return
     */
    @Override
    @Query("select rp from RestaurantPhoto rp")
    List<RestaurantPhoto> findAll();

    /**
     * 根据显示区域返回图片对象
     * @param displayArea
     * @return
     */
    List<RestaurantPhoto> getByDisplayArea(String displayArea);
}
