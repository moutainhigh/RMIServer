package com.hgsoft.accountC.service;

import com.hgsoft.accountC.dao.HandPaymentDao;
import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.accountC.serviceInterface.IHandPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yangzhongji on 17/9/28.
 */
@Service
public class HandPaymentService implements IHandPaymentService {

    @Resource
    private HandPaymentDao handPaymentDao;

    @Override
    public List<HandPayment> listByBaccountAndPayTime(String baccount, Date payTime) {
        return handPaymentDao.listByBaccountAndPayTime(baccount, payTime);
    }
}
