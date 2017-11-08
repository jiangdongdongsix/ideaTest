package com.iqes.service.restaurant;

/**
 * @author huqili
 */

import com.iqes.entity.RestaurantPhoto;
import com.iqes.repository.restaurant.RestaurantPhotoDao;
import com.iqes.service.ServiceException;
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

    public void saveOne(MultipartFile file, HttpServletRequest request,String displayArea){

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
        photo.setUrl(request.getServletContext().getContextPath()+"/upload/"+fileName);
        System.out.println(photo.getUrl());
        photo.setDisplayArea(displayArea);
        restaurantPhotoDao.save(photo);
    }

    public List<RestaurantPhoto> findAll(){
        return restaurantPhotoDao.findAll();
    }

    public void deleteOne(Long id){
//        String photoName=restaurantPhotoDao.findOne(id).getUrl();
//        File file=new File(photoName);
//        if (!file.exists()){
//            throw new ServiceException("文件不存在");
//        }else{
//            file.delete();
//        }
        restaurantPhotoDao.delete(id);
    }

    public List<RestaurantPhoto> getPhotosByArea(String displayArea){
        final String allArea="0";
        List<RestaurantPhoto> restaurantPhotoList;
        if (allArea.equals(displayArea)){
            restaurantPhotoList = restaurantPhotoDao.findAll();
        }else {
            restaurantPhotoList = restaurantPhotoDao.getByDisplayArea(displayArea);
        }
        return  restaurantPhotoList;
    }
}
