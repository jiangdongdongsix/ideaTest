package com.iqes.service.login;

import com.iqes.entity.User;
import com.iqes.exception.ValidteFailException;
import com.iqes.repository.login.ShopLoginDao;
import com.iqes.utils.RSAHelper;
import com.iqes.utils.RSAUtil;
import com.iqes.utils.YanHuaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
import com.alibaba.fastjson.JSONObject;

@Service
public class ShopLoginService {

    @Autowired
    private ShopLoginDao shopLoginDao;


    @Transactional
    public User login(String logiName,String password) throws Exception{
        JSONObject jsonObject = new JSONObject();
        User userBack = null;

        User user = shopLoginDao.findByLoginName(logiName);
        String pwd = RSAUtil.decryptByPrivateKeyBase64(password,RSAHelper.getPrivateKey(YanHuaConstants.PRIVATE_KEY));

        //先根据用户名去查询用户对应的salt编码
//        LoginMobile loginUser =loginMobileService.findUserByName(loginMobileCommand.getLogin_name());
        if(user!=null){
            //对salt进行编码
            byte[] salt = Encodes.decodeHex(user.getSalt());
            byte[] hashPassword = Digests.sha1(pwd.getBytes(), salt, 1024);
            //编码后的密码传入匹配
            String encodePassword = (Encodes.encodeHex(hashPassword));
            userBack=shopLoginDao.validate(logiName,encodePassword);
        }else{
           throw  new ValidteFailException("该用户未注册");
        }
        if(userBack != null){
            return userBack;
        }else {
            throw new ValidteFailException("用户名或密码错误");
        }
    }
}
