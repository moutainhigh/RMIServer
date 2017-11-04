package com.hgsoft.other.vo.receiptContent.account;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 账户缴款回执
 */
public class RechargeReceipt extends BaseReceiptContent {
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
     * 缴款金额（元）
     */
    private String rechargeTakeBalance;
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
     * 缴款前账户可用余额（元）
     */
    private String beforeAvailableBalance;
    /**
     * 缴款后账户可用余额（元）
     */
    private String afterAvailableBalance;
    /**
     * 备注
     */
    private String memo;
    /**
     * 银行账户名称（转账）
     */
    private String bankPayName;
    /**
     * 银行账号（转账）
     */
    private String bankNo;
    /**
     * 到账金额（转账）
     */
    private String bankTransferBalance;
    /**
     * 到账时间（转账）
     */
    private String bankArrivalTime;

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

    public String getRechargeTakeBalance() {
        return rechargeTakeBalance;
    }

    public void setRechargeTakeBalance(String rechargeTakeBalance) {
        this.rechargeTakeBalance = rechargeTakeBalance;
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

    public String getBeforeAvailableBalance() {
        return beforeAvailableBalance;
    }

    public void setBeforeAvailableBalance(String beforeAvailableBalance) {
        this.beforeAvailableBalance = beforeAvailableBalance;
    }

    public String getAfterAvailableBalance() {
        return afterAvailableBalance;
    }

    public void setAfterAvailableBalance(String afterAvailableBalance) {
        this.afterAvailableBalance = afterAvailableBalance;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBankPayName() {
        return bankPayName;
    }

    public void setBankPayName(String bankPayName) {
        this.bankPayName = bankPayName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankTransferBalance() {
        return bankTransferBalance;
    }

    public void setBankTransferBalance(String bankTransferBalance) {
        this.bankTransferBalance = bankTransferBalance;
    }

    public String getBankArrivalTime() {
        return bankArrivalTime;
    }

    public void setBankArrivalTime(String bankArrivalTime) {
        this.bankArrivalTime = bankArrivalTime;
    }
}
