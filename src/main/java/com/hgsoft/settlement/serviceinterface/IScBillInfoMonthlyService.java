package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.ScBillInfoMonthly;

import java.util.Date;
import java.util.List;

public interface IScBillInfoMonthlyService {

    void batchSave(Integer settleMonth, Date startDispartTime, Date endDispartTime);

    List<ScBillInfoMonthly> findUserBillByCardNoAndSettleMonth(String cardNo, String settleMonth);
}
