package com.iqes.service.restaurant;

import com.iqes.entity.ConfigInfo;
import com.iqes.repository.restaurant.ConfigInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigInfoService {

    @Autowired
    private ConfigInfoDao configInfoDao;

    public ConfigInfo findOne(){
        return  configInfoDao.findOne((long) 1);
    }

    public void saveOne(ConfigInfo configInfo){

        configInfoDao.save(configInfo);
    }

    public void upadtePause(boolean pause){
        ConfigInfo configInfo=configInfoDao.findOne((long)1);
        configInfo.setPauseQueue(pause);
    }

    public boolean findPauseQueue(){
        return configInfoDao.findOne((long)1).getPauseQueue();
    }

    /**
     * 避免传入数据库的对象有空字段
     * @param configInfo
     * @return
     */

}
