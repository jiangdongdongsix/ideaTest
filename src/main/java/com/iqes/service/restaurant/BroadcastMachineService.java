package com.iqes.service.restaurant;

import com.iqes.entity.BroadcastMachine;
import com.iqes.repository.restaurant.BroadcastMachineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 54312
 */
@Service
@Transactional
public class BroadcastMachineService {

    @Autowired
    private BroadcastMachineDao broadcastMachineDao;

    public void save(BroadcastMachine broadcastMachine){
        broadcastMachineDao.save(broadcastMachine);
    }

    public BroadcastMachine find(){
        return broadcastMachineDao.findOne((long)1);
    }
}
