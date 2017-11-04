package com.hgsoft.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 保证金退还回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardBailBackReceipt extends BaseReceiptContent {

    /**
     * 记账卡号
     */
    private String accCardNo;
    /**
     * 开户银行账号
     */
    private String applyBankAccount;
    /**
     * 银行账号
     */
    private String bailBankNo;
    /**
     * 银行账号名称
     */
    private String bailBankMember;
    /**
     * 银行开户网点
     */
    private String bailBankOpenBranches;

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

    public String getBailBankNo() {
        return bailBankNo;
    }

    public void setBailBankNo(String bailBankNo) {
        this.bailBankNo = bailBankNo;
    }

    public String getBailBankMember() {
        return bailBankMember;
    }

    public void setBailBankMember(String bailBankMember) {
        this.bailBankMember = bailBankMember;
    }

    public String getBailBankOpenBranches() {
        return bailBankOpenBranches;
    }

    public void setBailBankOpenBranches(String bailBankOpenBranches) {
        this.bailBankOpenBranches = bailBankOpenBranches;
    }

}
