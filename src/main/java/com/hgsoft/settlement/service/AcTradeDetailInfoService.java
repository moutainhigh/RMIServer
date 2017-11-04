package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.AcTradeDetailInfoDao;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import com.hgsoft.settlement.serviceinterface.IAcTradeDetailInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AcTradeDetailInfoService implements IAcTradeDetailInfoService {

    @Resource
    private AcTradeDetailInfoDao acTradeDetailInfoDao;

    @Override
    public void batchSaveFromCardInSettleDetail() {
        acTradeDetailInfoDao.batchSaveFromCardInSettleDetail();
    }

    @Override
    public void batchSaveFromCardOutSettleDetail() {
        acTradeDetailInfoDao.batchSaveFromCardOutSettleDetail();
    }

    @Override
    public List<AcTradeDetailInfo> findByDetailNoAndStatus(String detailNo, Integer dealStatus) {
        return acTradeDetailInfoDao.findByDetailNoAndStatus(detailNo, dealStatus);
    }

    @Override
    public int updatePayInfo(AcTradeDetailInfo acTradeDetailInfo, Integer oldPayFlag) {
        return acTradeDetailInfoDao.updatePayInfo(acTradeDetailInfo, oldPayFlag);
    }

}
