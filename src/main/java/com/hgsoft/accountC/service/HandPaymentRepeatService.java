package com.hgsoft.accountC.service;

import com.hgsoft.accountC.dao.HandPaymentRepeatDao;
import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.accountC.serviceInterface.IHandPaymentRepeatService;
import com.hgsoft.settlement.entity.AccBankListReturn;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangzhongji on 17/9/28.
 */
@Service
public class HandPaymentRepeatService implements IHandPaymentRepeatService {

    @Resource
    private HandPaymentRepeatDao handPaymentRepeatDao;

    @Override
    public int insert(HandPayment handPayment, AccBankListReturn accBankListReturn) {
        return handPaymentRepeatDao.insert(handPayment, accBankListReturn);
    }
}
