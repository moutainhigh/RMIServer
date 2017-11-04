package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.MonthlyRegDao;
import com.hgsoft.settlement.entity.MonthlyReg;
import com.hgsoft.settlement.serviceinterface.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangzhongji on 17/6/13.
 */
@Service
public class MonthlyRegService implements IMonthlyRegService {

    @Resource
    private MonthlyRegDao monthlyRegDao;

    @Override
    public List<MonthlyReg> findAllCheckedAndNotGen() {
        return monthlyRegDao.findAllCheckedAndNotGen();
    }

    @Override
    public void updateGenBillFlag(Long id, int genBillFlag) {
        monthlyRegDao.updateGenBillFlag(id, genBillFlag);
    }

    /**
     * 根据结算月获取已审核通过的对账周期登记记录
     * @param settleMonth
     * @return
     */
    @Override
    public MonthlyReg findBySettleMonth(Integer settleMonth) {
        return monthlyRegDao.findBySettleMonth(settleMonth);
    }
}
