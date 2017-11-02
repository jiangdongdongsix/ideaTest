package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.RestaurantPhoto;
import com.iqes.service.restaurant.RestaurantPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 54312
 */

@Controller
@RequestMapping(value = "/restaurant/restaurantPhoto")
public class RestaurantPhotoController {

    @Autowired
    private RestaurantPhotoService restaurantPhotoService;

    @ResponseBody
    @RequestMapping(value = "/photos",method = RequestMethod.GET)
    public String findAll(){

        JSONObject jsonObject=new JSONObject();

        List<RestaurantPhoto> restaurantPhotoList=restaurantPhotoService.findAll();

        jsonObject.put("restaurantPhotoList",restaurantPhotoList);

        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam("id") Long id){

        JSONObject jsonObject=new JSONObject();
        try{
            restaurantPhotoService.deleteOne(id);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e) {
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,@RequestParam("displayArea") String displayArea){

        System.out.println("门店图片上传！！！");

        JSONObject jsonObject=new JSONObject();

        try{
            restaurantPhotoService.saveOne(file,request,displayArea);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e) {
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }


//      多图上传
//    @RequestMapping(value = "/testUploadPhoto2",method = RequestMethod.POST)
//    public String upload2(HttpServletRequest request) throws IOException {
//
//        System.out.println("photo2");
//
//        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
//                request.getSession().getServletContext());
//        String newName="";
//        //检查form中是否有enctype="multipart/form-data"
//        if(multipartResolver.isMultipart(request)) {
//            //将request变成多部分request
//            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
//            //获取multiRequest 中所有的文件名
//            Iterator iter=multiRequest.getFileNames();
//
//            while(iter.hasNext())
//            {
//                //一次遍历所有文件
//                MultipartFile file=multiRequest.getFile(iter.next().toString());
//
//                if(file!=null)
//                {
//                    String path=request.getSession().getServletContext().getRealPath("/upload");
//                    newName=System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//                    System.out.println(path);
//                    System.out.println(newName);
//                    //上传
//                    file.transferTo(new File(path+"\\"+newName));
//                }
//
//            }
//
//        }
//
//        RestaurantPhoto restaurantPhoto=new RestaurantPhoto();
//        restaurantPhoto.setUrl(newName);
//        restaurantPhotoService.saveOne(restaurantPhoto);
//        return "redirect:/restaurant/restaurantPhoto/testFind";
//    }

    @ResponseBody
    @RequestMapping(value = "/displayArea")
    public String getPhotoByDisplayArea(@RequestParam(value = "displayArea")String displayArea){
        JSONObject jsonObject=new JSONObject();
        List<RestaurantPhoto> restaurantPhotoList;
        try {
            restaurantPhotoList=restaurantPhotoService.getPhotosByArea(displayArea);
            jsonObject.put("restaurantPhotos",restaurantPhotoList);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }


//    @RequestMapping(value = "/testUpload",method = RequestMethod.POST)
//    public String uploadaa(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,@RequestParam("displayArea") String displayArea){
//
//        System.out.println("门店图片上传！！！");
//        System.out.println(request.getServletContext().getContextPath());
//
//        JSONObject jsonObject=new JSONObject();
//        Model model = null;
//
//
//        try{
//            restaurantPhotoService.saveOne(file,request,displayArea);
//            List<RestaurantPhoto> restaurantPhotoList=restaurantPhotoService.findAll();
//
//            model.addAttribute(restaurantPhotoList);
//            jsonObject.put("Version", "1.0");
//            jsonObject.put("ErrorCode", "0");
//            jsonObject.put("ErrorMessage", "");
//        }catch (Exception e) {
//            jsonObject.put("Version", "1.0");
//            jsonObject.put("ErrorCode", "1");
//            jsonObject.put("ErrorMessage", e.getMessage());
//            e.printStackTrace();
//        }
//
//        return "redirect:checkView";
//    }
}
