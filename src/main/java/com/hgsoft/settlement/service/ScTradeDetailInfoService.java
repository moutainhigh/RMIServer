package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.ScTradeDetailInfoDao;
import com.hgsoft.settlement.serviceinterface.IScTradeDetailInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScTradeDetailInfoService implements IScTradeDetailInfoService {

    private static Logger logger = Logger.getLogger(ScTradeDetailInfoService.class);

    @Resource
    private ScTradeDetailInfoDao scTradeDetailInfoDao;

    @Override
    public void batchSaveFromScAddSure() {
        scTradeDetailInfoDao.batchSaveFromScAddSure();
    }

    @Override
    public void batchSaveFromCardInSettleDetail() {
        scTradeDetailInfoDao.batchSaveFromCardInSettleDetail();
    }

    @Override
    public void batchSaveFromCardOutSettleDetail() {
        scTradeDetailInfoDao.batchSaveFromCardOutSettleDetail();
    }
}
