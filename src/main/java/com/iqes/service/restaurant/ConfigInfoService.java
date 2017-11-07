package com.iqes.service.restaurant;

import com.iqes.entity.ConfigInfo;
import com.iqes.repository.restaurant.ConfigInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 54312
 * 配置信息
 */
@Service
@Transactional
public class ConfigInfoService {

    @Autowired
    private ConfigInfoDao configInfoDao;

    public ConfigInfo findOne(){
        return  configInfoDao.findOne((long) 1);
    }

    public void saveOne(ConfigInfo configInfo){
        ConfigInfo c=configInfoDao.findOne(configInfo.getId());

        if (configInfo.getNextTableExtractFlag()==null){
            if (c.getNextTableExtractFlag()!=null){
                configInfo.setNextTableExtractFlag(c.getNextTableExtractFlag());
            }else{
                configInfo.setNextTableExtractFlag(false);
            }
        }

        if (configInfo.getPauseQueue()==null){
            if (c.getPauseQueue()!=null){
                configInfo.setPauseQueue(c.getPauseQueue());
            }else{
                configInfo.setPauseQueue(false);
            }
        }
        configInfoDao.save(configInfo);
    }

    public void upadtePause(boolean pause){
        ConfigInfo configInfo=configInfoDao.findOne((long)1);
        configInfo.setPauseQueue(pause);
    }

    public boolean findPauseQueue(){
        return configInfoDao.findOne((long)1).getPauseQueue();
    }

}
