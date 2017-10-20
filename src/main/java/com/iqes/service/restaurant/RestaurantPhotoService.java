package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.RestaurantPhoto;
import com.iqes.repository.restaurant.RestaurantPhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Service
@Transactional
public class RestaurantPhotoService {

    @Autowired
    private RestaurantPhotoDao restaurantPhotoDao;

    public void saveOne(MultipartFile file, HttpServletRequest request){
        String localPath = request.getSession().getServletContext().getRealPath("/upload");
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File dir = new File(localPath);
        if(!dir.exists()) {
            // 如果文件夹不存在，就创建文件夹。
            dir.mkdir();
        }
        //保存
        try {
            file.transferTo(new File(localPath+"\\"+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RestaurantPhoto photo=new RestaurantPhoto();
        photo.setUrl("/iqes/upload/"+fileName);
        restaurantPhotoDao.save(photo);
    }

    public List<RestaurantPhoto> findAll(){
        return restaurantPhotoDao.findAll();
    }

    public void deleteOne(Long id){
        restaurantPhotoDao.delete(id);
    }
}
