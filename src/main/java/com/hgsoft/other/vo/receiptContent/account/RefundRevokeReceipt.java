package com.hgsoft.other.vo.receiptContent.account;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 账户退款申请撤销回执
 */
public class RefundRevokeReceipt extends BaseReceiptContent {
    /**
     * 退款回执编号
     */
    private String refundReceiptNo;
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
     * 撤销金额
     */
    private String revokeBalance;

    public String getRefundReceiptNo() {
        return refundReceiptNo;
    }

    public void setRefundReceiptNo(String refundReceiptNo) {
        this.refundReceiptNo = refundReceiptNo;
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

    public String getRevokeBalance() {
        return revokeBalance;
    }

    public void setRevokeBalance(String revokeBalance) {
        this.revokeBalance = revokeBalance;
    }
}
