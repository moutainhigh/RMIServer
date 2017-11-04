package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡退款申请回执
 */
public class PreCardStopRefundReceipt extends BaseReceiptContent{

    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 卡内余额
     */
    private String balance;
    /**
     * 银行账号
     */
    private String refundBankNo;
    /**
     * 银行账号名称
     */
    private String refundBankMember;
    /**
     * 银行开户网点
     */
    private String refundBankOpenBranches;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRefundBankNo() {
        return refundBankNo;
    }

    public void setRefundBankNo(String refundBankNo) {
        this.refundBankNo = refundBankNo;
    }

    public String getRefundBankMember() {
        return refundBankMember;
    }

    public void setRefundBankMember(String refundBankMember) {
        this.refundBankMember = refundBankMember;
    }

    public String getRefundBankOpenBranches() {
        return refundBankOpenBranches;
    }

    public void setRefundBankOpenBranches(String refundBankOpenBranches) {
        this.refundBankOpenBranches = refundBankOpenBranches;
    }
}
