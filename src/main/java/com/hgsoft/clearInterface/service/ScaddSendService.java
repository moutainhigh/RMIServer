package com.hgsoft.clearInterface.service;

import com.hgsoft.clearInterface.dao.ScaddSendDao;
import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.clearInterface.serviceInterface.IScaddSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : 吴锡霖
 * file : ScaddSendService.java
 * date : 2017-06-28
 * time : 9:14
 */
@Service
public class ScaddSendService implements IScaddSendService {
    
    @Resource
    private ScaddSendDao scaddSendDao;


    @Override
    public void save(ScaddSend scaddSend) {
        scaddSendDao.save(scaddSend);
    }
}
