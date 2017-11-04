package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.MonthlyReg;

import java.util.List;

public interface IMonthlyRegService {

    List<MonthlyReg> findAllCheckedAndNotGen();

    void updateGenBillFlag(Long id, int genBillFlag);

    /**
     * 根据结算月获取已审核通过的对账周期登记记录
     * @param settleMonth
     * @return
     */
    MonthlyReg findBySettleMonth(Integer settleMonth);
}
