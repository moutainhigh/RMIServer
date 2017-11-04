package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.OmsRoadDao;
import com.hgsoft.settlement.dao.RoadDao;
import com.hgsoft.settlement.serviceinterface.IRoadService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoadService implements IRoadService {

    private static Logger logger = Logger.getLogger(RoadService.class);

    @Resource
    private RoadDao roadDao;
    @Resource
    private OmsRoadDao omsRoadDao;


    @Override
    public void deleteAll() {
        roadDao.deleteAll();
    }

    @Override
    public List<Object[]> findAllObjsFromOms() {
        return omsRoadDao.findAllObjs();
    }

    @Override
    public void batchSave(List<Object[]> objs) {
        roadDao.batchSave(objs);
    }

}
