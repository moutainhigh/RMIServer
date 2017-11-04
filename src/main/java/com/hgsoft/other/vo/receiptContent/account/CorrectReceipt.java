package com.hgsoft.other.vo.receiptContent.account;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 账户缴款冲正回执
 */
public class CorrectReceipt extends BaseReceiptContent {
    /**
     * 原充值回执编号
     */
    private String rechargeReciptNo;
    /**
     * 客户类型
     */
    private String customerType;
    /**
     * 二级编码
     */
    private String customerSecondNo;
    /**
     * 二级编码名称
     */
    private String customerSecondName;
    /**
     * 缴款方式Code
     */
    private String rechargePayMentTypeCode;
    /**
     * 缴款方式
     */
    private String rechargePayMentType;
    /**
     * 银行账号（POS）
     */
    private String rechargePayMentNo;
    /**
     * POS缴款流水号（POS）
     */
    private String rechargePosId;
    /**
     * 交款单号（缴款单）
     */
    private String rechargeVoucherNo;
    /**
     * 冲正金额
     */
    private String correctMoney;
    /**
     * 冲正前账户可用余额
     */
    private String beforeCorrectMoney;
    /**
     * 冲正后账户可用余额
     */
    private String afterCorrectMoney;
    /**
     * 备注
     */
    private String memo;
    /**
     * 银行账号（转账）
     */
    private String bankNo;
    /**
     * 银行账户名称（转账）
     */
    private String bankPayName;

    public String getRechargeReciptNo() {
        return rechargeReciptNo;
    }

    public void setRechargeReciptNo(String rechargeReciptNo) {
        this.rechargeReciptNo = rechargeReciptNo;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerSecondNo() {
        return customerSecondNo;
    }

    public void setCustomerSecondNo(String customerSecondNo) {
        this.customerSecondNo = customerSecondNo;
    }

    public String getCustomerSecondName() {
        return customerSecondName;
    }

    public void setCustomerSecondName(String customerSecondName) {
        this.customerSecondName = customerSecondName;
    }

    public String getRechargePayMentTypeCode() {
        return rechargePayMentTypeCode;
    }

    public void setRechargePayMentTypeCode(String rechargePayMentTypeCode) {
        this.rechargePayMentTypeCode = rechargePayMentTypeCode;
    }

    public String getRechargePayMentType() {
        return rechargePayMentType;
    }

    public void setRechargePayMentType(String rechargePayMentType) {
        this.rechargePayMentType = rechargePayMentType;
    }

    public String getRechargePayMentNo() {
        return rechargePayMentNo;
    }

    public void setRechargePayMentNo(String rechargePayMentNo) {
        this.rechargePayMentNo = rechargePayMentNo;
    }

    public String getRechargePosId() {
        return rechargePosId;
    }

    public void setRechargePosId(String rechargePosId) {
        this.rechargePosId = rechargePosId;
    }

    public String getRechargeVoucherNo() {
        return rechargeVoucherNo;
    }

    public void setRechargeVoucherNo(String rechargeVoucherNo) {
        this.rechargeVoucherNo = rechargeVoucherNo;
    }

    public String getCorrectMoney() {
        return correctMoney;
    }

    public void setCorrectMoney(String correctMoney) {
        this.correctMoney = correctMoney;
    }

    public String getBeforeCorrectMoney() {
        return beforeCorrectMoney;
    }

    public void setBeforeCorrectMoney(String beforeCorrectMoney) {
        this.beforeCorrectMoney = beforeCorrectMoney;
    }

    public String getAfterCorrectMoney() {
        return afterCorrectMoney;
    }

    public void setAfterCorrectMoney(String afterCorrectMoney) {
        this.afterCorrectMoney = afterCorrectMoney;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankPayName() {
        return bankPayName;
    }

    public void setBankPayName(String bankPayName) {
        this.bankPayName = bankPayName;
    }
}
