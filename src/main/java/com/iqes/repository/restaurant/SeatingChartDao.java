package com.iqes.repository.restaurant;

import com.iqes.entity.SeatingChart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 54312
 */
public interface SeatingChartDao extends CrudRepository<SeatingChart,Long> {

    @Override
    @Query(value = "select SeatingChart from SeatingChart ")
    List<SeatingChart> findAll();
}
