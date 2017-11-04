package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 保证金收款回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardBailAddReceiptACMS extends BaseReceiptContent {

    /**
     * 记账卡号
     */
    private String accCardNo;
    /**
     * 开户银行账号
     */
    private String applyBankAccount;
    /**
     * 缴纳保证金金额
     */
    private String bailFee;
    /**
     * 缴纳后保证金金额
     */
    private String bussBailFee;
    /**
     * 交易类型
     */
    private String bailTradingType;
    /**
     * 特别说明
     */
    private String explanation;

    public String getAccCardNo() {
        return accCardNo;
    }

    public void setAccCardNo(String accCardNo) {
        this.accCardNo = accCardNo;
    }

    public String getApplyBankAccount() {
        return applyBankAccount;
    }

    public void setApplyBankAccount(String applyBankAccount) {
        this.applyBankAccount = applyBankAccount;
    }

    public String getBailFee() {
        return bailFee;
    }

    public void setBailFee(String bailFee) {
        this.bailFee = bailFee;
    }

    public String getBussBailFee() {
        return bussBailFee;
    }

    public void setBussBailFee(String bussBailFee) {
        this.bussBailFee = bussBailFee;
    }

    public String getBailTradingType() {
        return bailTradingType;
    }

    public void setBailTradingType(String bailTradingType) {
        this.bailTradingType = bailTradingType;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
