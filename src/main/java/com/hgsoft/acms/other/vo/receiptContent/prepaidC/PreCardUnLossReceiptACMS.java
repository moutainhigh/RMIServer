package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡解挂回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardUnLossReceiptACMS extends BaseReceiptContent {
    /**
     * 原挂失回执编号
     */
    private String lostReceiptNo;
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 特别说明
     */
    private String explanation;

    public String getLostReceiptNo() {
        return lostReceiptNo;
    }

    public void setLostReceiptNo(String lostReceiptNo) {
        this.lostReceiptNo = lostReceiptNo;
    }

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
