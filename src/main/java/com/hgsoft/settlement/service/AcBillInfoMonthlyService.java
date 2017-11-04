package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.AcBillInfoMonthlyDao;
import com.hgsoft.settlement.entity.AcBillInfoMonthly;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import com.hgsoft.settlement.serviceinterface.IAcBillInfoMonthlyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AcBillInfoMonthlyService implements IAcBillInfoMonthlyService {

    private static Logger logger = Logger.getLogger(AcBillInfoMonthlyService.class);

    @Resource
    private AcBillInfoMonthlyDao acBillInfoMonthlyDao;

    @Override
    public void batchSave(Integer settleMonth, Date startDispartTime, Date endDispartTime) {
        acBillInfoMonthlyDao.batchSave(settleMonth, startDispartTime, endDispartTime);
    }

    @Override
    public List<AcBillInfoMonthly> findUserBillByCardNoAndSettleMonth(String cardNo, String settleMonth) {
        return acBillInfoMonthlyDao.findByCardNoSettleMonthDealStatus(cardNo, settleMonth, AcTradeDetailInfo.DealStatusEnum.NORMAL_TRADE.getValue());
    }

    @Override
    public List<AcBillInfoMonthly> findUserBillByBankAccSettleMonth(String bankAccount, String settleMonth) {
        return acBillInfoMonthlyDao.findByBankAccountSettleMonth(bankAccount, settleMonth, AcTradeDetailInfo.DealStatusEnum.NORMAL_TRADE.getValue());
    }
}
