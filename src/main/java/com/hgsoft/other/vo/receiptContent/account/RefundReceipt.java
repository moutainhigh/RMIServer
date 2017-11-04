package com.hgsoft.other.vo.receiptContent.account;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 账户退款申请回执
 */
public class RefundReceipt extends BaseReceiptContent {
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
     * 银行账号
     */
    private String bankNo;
    /**
     * 银行账号名称
     */
    private String bankMember;
    /**
     * 银行开户网点
     */
    private String bankOpenBranches;
    /**
     * 退款金额
     */
    private String currentRefundBalance;

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

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankMember() {
        return bankMember;
    }

    public void setBankMember(String bankMember) {
        this.bankMember = bankMember;
    }

    public String getBankOpenBranches() {
        return bankOpenBranches;
    }

    public void setBankOpenBranches(String bankOpenBranches) {
        this.bankOpenBranches = bankOpenBranches;
    }

    public String getCurrentRefundBalance() {
        return currentRefundBalance;
    }

    public void setCurrentRefundBalance(String currentRefundBalance) {
        this.currentRefundBalance = currentRefundBalance;
    }
}
