package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.*;

import java.util.Date;
import java.util.List;

public interface ISettlementService {

    void batchSaveScTradeDetailFromScAddSure();

    void batchSaveScTradeDetailFromCardInSettleDetail();

    void batchSaveScTradeDetailFromCardOutSettleDetail();

    void batchSaveAcTradeDetailFromCardInSettleDetail();

    void batchSaveAcTradeDetailFromCardOutSettleDetail();

    List<MonthlyReg> findAllCheckedAndNotGenOfMonthlyRegs();

    void saveMonthlyBill(MonthlyReg monthlyReg);

    List<CommandInfo> findTollFeeCommand();

    void updateHandPayment(List<CommandInfo> commandInfos);

    public List<CardFeeData> listCardFeeData();

    void saveReturnFeeData(CardFeeData cardFeeData);
}
