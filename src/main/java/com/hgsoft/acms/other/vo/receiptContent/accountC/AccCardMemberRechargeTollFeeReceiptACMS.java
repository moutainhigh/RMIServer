package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记帐卡手工缴纳通行费回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardMemberRechargeTollFeeReceiptACMS extends BaseReceiptContent {

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
     * 通行费金额
     */
    private String relieveETCFee;

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

    public String getRelieveETCFee() {
        return relieveETCFee;
    }

    public void setRelieveETCFee(String relieveETCFee) {
        this.relieveETCFee = relieveETCFee;
    }
}
