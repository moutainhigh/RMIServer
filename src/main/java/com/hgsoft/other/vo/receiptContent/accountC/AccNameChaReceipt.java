package com.hgsoft.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 银行账户名称变更申请回执
 * Created by wiki on 2017/9/11.
 */
public class AccNameChaReceipt extends BaseReceiptContent {
    /**
     * 开户银行账号
     */
    private String applyBankAccount;
    /**
     * 开户银行名称
     */
    private String applyBankName;
    /**
     * 开户账户户名(原)
     */
    private String oldApplyAccName;
    /**
     * 开户账户户名(新)
     */
    private String applyAccName;

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

    public String getOldApplyAccName() {
        return oldApplyAccName;
    }

    public void setOldApplyAccName(String oldApplyAccName) {
        this.oldApplyAccName = oldApplyAccName;
    }

    public String getApplyAccName() {
        return applyAccName;
    }

    public void setApplyAccName(String applyAccName) {
        this.applyAccName = applyAccName;
    }
}
