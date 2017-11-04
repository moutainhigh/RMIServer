package com.hgsoft.timerTask.serviceinterface;

import java.io.Serializable;

/**
 * Created by apple on 2017/1/12.
 */
public interface IReportTimerTask extends Serializable {
    public void addTagsellIssueTotal();
    public void addEtcCardSaleCount();

    public void electronicTagSellIssueDetail();
    public void paymentBalanceReport();
    public void serviceMoneyWaterReport();
    
    public void generateAccountBalanceReportData();
    public void generateBankTransferBalanceReportData();
    public void generateOBUPickUPBalanceReportData();
    public void insertPrepaidSum();
    public void insertPrePayTotal();
    public void insertStockGBCardDetail();
    public void insertCancelCardDetail();
}
