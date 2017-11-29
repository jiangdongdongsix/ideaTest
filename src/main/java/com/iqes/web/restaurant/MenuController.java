package com.iqes.web.restaurant;

/**
 * @author huqili
 *
 */
import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.Menu;
import com.iqes.entity.dto.MenuDTO;
import com.iqes.rabbitmq.RPCClient;
import com.iqes.service.ServiceException;
import com.iqes.service.restaurant.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping(value = "/restaurant/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveMenu(@RequestParam(value = "menuName")String menuName,
                             @RequestParam(value = "menuType",defaultValue = "nothing")String menuType,
                             @RequestParam(value = "menuPrice")Integer menuPrice,
                             @RequestParam(value = "menuMemberPrice")Integer menuMemberPrice,
                             @RequestParam(value = "menuDescribe",defaultValue = "nothing")String menuDescribe,
                             @RequestParam(value = "available",defaultValue = "false")boolean available,
                             @RequestParam(value = "menuPhoto", required = false) MultipartFile menuPhoto,
                             HttpServletRequest request){

        JSONObject jsonObject=new JSONObject();
        Menu menu=new Menu();

        try{
            menu.setMenuType(menuType);
            menu.setMenuPrice(menuPrice);
            menu.setMemberMenuPrice(menuMemberPrice);
            menu.setDescribe(menuDescribe);
            menu.setMenuName(menuName);
            menu.setAvailable(available);

            if (menuPhoto!=null){
                String localPath = request.getSession().getServletContext().getRealPath("/menu");
                String fileName = System.currentTimeMillis() + "_" + menuPhoto.getOriginalFilename();

                File dir = new File(localPath);
                if(!dir.exists()) {
                    dir.mkdir();
                }
                try {
                    menuPhoto.transferTo(new File(localPath+"\\"+fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String photoUrl=request.getServletContext().getContextPath()+"/menu/"+fileName;
                menu.setPhotoUrl(photoUrl);
            }

            menuService.saveOne(menu);

            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteMenu(@RequestParam(value = "id")Long id){

        JSONObject jsonObject=new JSONObject();

        try{
            menuService.deleteOne(id);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/testSave",method = RequestMethod.POST)
    public String deleteMenu(@RequestBody MenuDTO menuDTO,HttpServletRequest request){

        JSONObject jsonObject=new JSONObject();
        Menu menu=new Menu(menuDTO);
        try{
            if (menuDTO.getMenuPhoto()!=null){
                String localPath = request.getSession().getServletContext().getRealPath("/menu");
                String fileName = System.currentTimeMillis() + "_" + menuDTO.getMenuPhoto().getOriginalFilename();

                File dir = new File(localPath);
                if(!dir.exists()) {
                    dir.mkdir();
                }
                try {
                    menuDTO.getMenuPhoto().transferTo(new File(localPath+"\\"+fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String photoUrl=request.getServletContext().getContextPath()+"/menu/"+fileName;
                menu.setPhotoUrl(photoUrl);
                System.out.println("菜单图片上传成功！");
            }
            menuService.saveOne(menu);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String findMenu(@RequestParam(value = "id")Long id){

        JSONObject jsonObject=new JSONObject();
        Menu menu;
        try{
            menu=menuService.findOne(id);
            jsonObject.put("menu",menu);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String findPageOfMenu(@RequestParam(value = "pageNo")Integer pageNo,@RequestParam(value = "pageSize")Integer pageSize){

        JSONObject jsonObject=new JSONObject();
        Page<Menu> menuPage;

        try{
            menuPage=menuService.pageQueryOfMenu(pageNo,pageSize);
            jsonObject.put("menuPage",menuPage);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/availability",method = RequestMethod.POST)
    public String updateMenuAvailable(@RequestParam(value = "id")Long id,@RequestParam(value = "availability")boolean availability){

        JSONObject jsonObject=new JSONObject();
        try{
            String msg=menuService.updateAvailableState(id,availability);
            jsonObject.put("msg",msg);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();

    }

    @ResponseBody
    @RequestMapping(value = "/menus",method = RequestMethod.GET)
    public String findMenu(){

        JSONObject jsonObject=new JSONObject();
        List<MenuDTO> menus=null;
        try{
            menus=menuService.getAllMenu();
            jsonObject.put("menus",menus);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/menuName",method = RequestMethod.GET)
    public String findMenuByName(@RequestParam(value = "menuName")String menuName){

        JSONObject jsonObject=new JSONObject();
        MenuDTO menuDTO=null;
        try{
            menuDTO=menuService.findByMenuName(menuName);
            jsonObject.put("menu",menuDTO);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (ServiceException se){
            se.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",se.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/testRPC",method = RequestMethod.GET)
    public String testRPC() throws IOException, TimeoutException {
        JSONObject jsonObject=new JSONObject();
        String response=null;
        try{
            response=menuService.testRPCsend();
            jsonObject.put("response",response);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (ServiceException se){
            se.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",se.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

}
