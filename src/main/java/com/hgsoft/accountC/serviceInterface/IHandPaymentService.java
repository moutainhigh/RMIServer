package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.HandPayment;

import java.util.Date;
import java.util.List;

/**
 * Created by yangzhongji on 17/9/28.
 */
public interface IHandPaymentService {

    List<HandPayment> listByBaccountAndPayTime(String baccount, Date payTime);
}
