package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.AcBillInfoMonthly;

import java.util.Date;
import java.util.List;

public interface IAcBillInfoMonthlyService {

    void batchSave(Integer settleMonth, Date startDispartTime, Date endDispartTime);

    List<AcBillInfoMonthly> findUserBillByCardNoAndSettleMonth(String cardNo, String settleMonth);

    List<AcBillInfoMonthly> findUserBillByBankAccSettleMonth(String bankAccount, String settleMonth);
}
