package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 扣款账户变更申请回执
 * Created by wiki on 2017/8/21.
 */
public class AccNameChaReceiptACMS extends BaseReceiptContent {
    /**
     * 原开户账户户名
     */
    private String oldApplyAccName;
    /**
     * 原开户银行账号
     */
    private String oldApplyBankAccount;
    /**
     * 原开户银行名称
     */
    private String oldApplyBankName;
    /**
     * 开户账户户名
     */
    private String applyAccName;
    /**
     * 开户银行账号
     */
    private String applyBankAccount;
    /**
     * 开户银行名称
     */
    private String applyBankName;
    /**
     * 记帐卡数量
     */
    private String accCardNumber;
    /**
     * 记帐卡卡号
     */
    private String accCardNos;

    public String getOldApplyAccName() {
        return oldApplyAccName;
    }

    public void setOldApplyAccName(String oldApplyAccName) {
        this.oldApplyAccName = oldApplyAccName;
    }

    public String getOldApplyBankAccount() {
        return oldApplyBankAccount;
    }

    public void setOldApplyBankAccount(String oldApplyBankAccount) {
        this.oldApplyBankAccount = oldApplyBankAccount;
    }

    public String getOldApplyBankName() {
        return oldApplyBankName;
    }

    public void setOldApplyBankName(String oldApplyBankName) {
        this.oldApplyBankName = oldApplyBankName;
    }

    public String getApplyAccName() {
        return applyAccName;
    }

    public void setApplyAccName(String applyAccName) {
        this.applyAccName = applyAccName;
    }

    public String getApplyBankAccount() {
        return applyBankAccount;
    }

    public void setApplyBankAccount(String applyBankAccount) {
        this.applyBankAccount = applyBankAccount;
    }

    public String getApplyBankName() {
        return applyBankName;
    }

    public void setApplyBankName(String applyBankName) {
        this.applyBankName = applyBankName;
    }

    public String getAccCardNumber() {
        return accCardNumber;
    }

    public void setAccCardNumber(String accCardNumber) {
        this.accCardNumber = accCardNumber;
    }

    public String getAccCardNos() {
        return accCardNos;
    }

    public void setAccCardNos(String accCardNos) {
        this.accCardNos = accCardNos;
    }
}
