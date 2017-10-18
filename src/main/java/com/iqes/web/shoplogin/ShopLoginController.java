package com.iqes.web.shoplogin;


import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.User;
import com.iqes.entity.dto.ConveyModelDTO;
import com.iqes.entity.vo.AccountVO;
import com.iqes.exception.ValidteFailException;
import com.iqes.service.login.ShopLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/shop")
public class ShopLoginController {

    @Autowired
    private ShopLoginService shopLoginService;


    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ConveyModelDTO login(@RequestBody AccountVO accountVO){
        System.out.println(accountVO.toString());
        ConveyModelDTO conveyModelDTO = new ConveyModelDTO();
        try{
            User user = shopLoginService.login(accountVO.getLoginName(),accountVO.getPassword());
            conveyModelDTO.setVersion("1.0");
            conveyModelDTO.setErrorCode("0");
            conveyModelDTO.setData(user);
        }catch (ValidteFailException v){
            v.printStackTrace();
            conveyModelDTO.setVersion("1.0");
            conveyModelDTO.setErrorCode("001");
            conveyModelDTO.setErrorMessage(v.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            conveyModelDTO.setVersion("1.0");
            conveyModelDTO.setErrorCode("001");
            conveyModelDTO.setErrorMessage(e.getMessage());
        }

        return conveyModelDTO;
    }



}
