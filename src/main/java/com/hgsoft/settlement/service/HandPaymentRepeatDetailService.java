package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.HandPaymentRepeatDetailDao;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import com.hgsoft.settlement.serviceinterface.IHandPaymentRepeatDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangzhongji on 17/9/28.
 */
@Service
public class HandPaymentRepeatDetailService implements IHandPaymentRepeatDetailService {

    @Resource
    private HandPaymentRepeatDetailDao handPaymentRepeatDetailDao;

    @Override
    public int insertByAcTradeDetail(AcTradeDetailInfo acTradeDetailInfo) {
        return handPaymentRepeatDetailDao.insertByAcTradeDetail(acTradeDetailInfo);
    }
}
