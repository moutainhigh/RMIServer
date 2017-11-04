package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.ScBillInfoMonthlyDao;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import com.hgsoft.settlement.entity.ScBillInfoMonthly;
import com.hgsoft.settlement.entity.ScTradeDetailInfo;
import com.hgsoft.settlement.serviceinterface.IScBillInfoMonthlyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ScBillInfoMonthlyService implements IScBillInfoMonthlyService {

    private static Logger logger = Logger.getLogger(ScBillInfoMonthlyService.class);

    @Resource
    private ScBillInfoMonthlyDao scBillInfoMonthlyDao;

    @Override
    public void batchSave(Integer settleMonth, Date startDispartTime, Date endDispartTime) {
        scBillInfoMonthlyDao.batchSave(settleMonth, startDispartTime, endDispartTime);
    }

    @Override
    public List<ScBillInfoMonthly> findUserBillByCardNoAndSettleMonth(String cardNo, String settleMonth) {
        return scBillInfoMonthlyDao.findByCardNoSettleMonthDealStatus(cardNo, settleMonth, AcTradeDetailInfo.DealStatusEnum.NORMAL_TRADE.getValue());
    }
}
