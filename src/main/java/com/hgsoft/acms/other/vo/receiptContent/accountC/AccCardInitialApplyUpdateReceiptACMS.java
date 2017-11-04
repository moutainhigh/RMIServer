package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 银行账号修改申请回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardInitialApplyUpdateReceiptACMS extends BaseReceiptContent {

    /**
     * 账户类型
     */
    private String applyAccountType;
    /**
     * 账户联系人
     */
    private String applyLinkman;
    /**
     * 联系手机
     */
    private String applyTel;
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
     * 服务方式
     */
    private String serItem;

    public String getApplyAccountType() {
        return applyAccountType;
    }

    public void setApplyAccountType(String applyAccountType) {
        this.applyAccountType = applyAccountType;
    }

    public String getApplyLinkman() {
        return applyLinkman;
    }

    public void setApplyLinkman(String applyLinkman) {
        this.applyLinkman = applyLinkman;
    }

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
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

    public String getSerItem() {
        return serItem;
    }

    public void setSerItem(String serItem) {
        this.serItem = serItem;
    }
}
