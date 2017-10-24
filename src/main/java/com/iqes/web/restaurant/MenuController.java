package com.iqes.web.restaurant;

/**
 * @author huqili
 *
 */
import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.Menu;
import com.iqes.service.restaurant.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/restaurant/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveMenu(@RequestBody Menu menu){

        JSONObject jsonObject=new JSONObject();
        try{
            menuService.saveOne(menu);
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
}
