package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 保证金退还回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardBailBackReceiptACMS extends BaseReceiptContent {

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
    /**
     * 特别说明1
     */
    private String explanation1;
    /**
     * 特别说明2
     */
    private String explanation2;

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

    public String getExplanation1() {
        return explanation1;
    }

    public void setExplanation1(String explanation1) {
        this.explanation1 = explanation1;
    }

    public String getExplanation2() {
        return explanation2;
    }

    public void setExplanation2(String explanation2) {
        this.explanation2 = explanation2;
    }
}
