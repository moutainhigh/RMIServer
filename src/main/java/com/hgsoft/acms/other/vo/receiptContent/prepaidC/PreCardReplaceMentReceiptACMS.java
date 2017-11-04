package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡挂失补领回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardReplaceMentReceiptACMS extends BaseReceiptContent {

    /**
     * 原挂失回执编号
     */
    private String lostReceiptNo;
    /**
     * 原储值卡号
     */
    private String oldPreCardNo;
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 工本费
     */
    private String preCardCost;
    /**
     * 特别说明1
     */
    private String explanation1;
    /**
     * 特别说明2
     */
    private String explanation2;

    public String getLostReceiptNo() {
        return lostReceiptNo;
    }

    public void setLostReceiptNo(String lostReceiptNo) {
        this.lostReceiptNo = lostReceiptNo;
    }

    public String getOldPreCardNo() {
        return oldPreCardNo;
    }

    public void setOldPreCardNo(String oldPreCardNo) {
        this.oldPreCardNo = oldPreCardNo;
    }

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getPreCardCost() {
        return preCardCost;
    }

    public void setPreCardCost(String preCardCost) {
        this.preCardCost = preCardCost;
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
