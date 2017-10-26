package com.iqes.service.restaurant;

import com.iqes.entity.SeatingChart;
import com.iqes.repository.restaurant.SeatingChartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author 54312
 */
@Service
@Transactional
public class SeatingChartService {

    @Autowired
    private SeatingChartDao seatingChartDao;

    public void saveOne(MultipartFile file, HttpServletRequest request){
        String localPath = request.getSession().getServletContext().getRealPath("/seating");
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File dir = new File(localPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        try {
            file.transferTo(new File(localPath+"\\"+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SeatingChart chart=new SeatingChart();
        chart.setUrl(request.getServletContext().getContextPath()+"/seating/"+fileName);
        seatingChartDao.save(chart);
    }

    public SeatingChart find(){
        return seatingChartDao.findOne((long)1);
    }
}
