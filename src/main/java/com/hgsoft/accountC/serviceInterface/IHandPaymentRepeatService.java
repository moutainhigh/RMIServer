package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.settlement.entity.AccBankListReturn;

/**
 * Created by yangzhongji on 17/9/28.
 */
public interface IHandPaymentRepeatService {

    int insert(HandPayment handPayment, AccBankListReturn accBankListReturn);
}
