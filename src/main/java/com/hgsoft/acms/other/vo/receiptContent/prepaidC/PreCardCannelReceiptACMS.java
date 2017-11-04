package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡终止使用回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardCannelReceiptACMS extends BaseReceiptContent {

    /**
     * 储值卡卡号
     */
    private String preCardNo;
    /**
     * 终止使用方式
     */
    private String cannelWay;
    /**
     * 卡内余额
     */
    private String preCardBalance;
    /**
     * 银行账号
     */
    private String rechargeBankNo;
    /**
     * 银行账号名称
     */
    private String rechargeBankMember;
    /**
     * 银行开户网点
     */
    private String rechargeBankOpenBranches;
    /**
     * 特别说明
     */
    private String explanation;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getCannelWay() {
        return cannelWay;
    }

    public void setCannelWay(String cannelWay) {
        this.cannelWay = cannelWay;
    }

    public String getPreCardBalance() {
        return preCardBalance;
    }

    public void setPreCardBalance(String preCardBalance) {
        this.preCardBalance = preCardBalance;
    }

    public String getRechargeBankNo() {
        return rechargeBankNo;
    }

    public void setRechargeBankNo(String rechargeBankNo) {
        this.rechargeBankNo = rechargeBankNo;
    }

    public String getRechargeBankMember() {
        return rechargeBankMember;
    }

    public void setRechargeBankMember(String rechargeBankMember) {
        this.rechargeBankMember = rechargeBankMember;
    }

    public String getRechargeBankOpenBranches() {
        return rechargeBankOpenBranches;
    }

    public void setRechargeBankOpenBranches(String rechargeBankOpenBranches) {
        this.rechargeBankOpenBranches = rechargeBankOpenBranches;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
