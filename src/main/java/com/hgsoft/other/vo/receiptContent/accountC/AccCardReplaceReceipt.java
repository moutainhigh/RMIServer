package com.hgsoft.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记账卡挂失补领回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardReplaceReceipt extends BaseReceiptContent {
    /**
     * 原挂失回执编号
     */
    private String lostReceiptNo;
    /**
     * 原卡号
     */
    private String oldAccCardNo;
    /**
     * 新卡号
     */
    private String accCardNo;
    /**
     * 工本费
     */
    private String accCardCost;

    public String getLostReceiptNo() {
        return lostReceiptNo;
    }

    public void setLostReceiptNo(String lostReceiptNo) {
        this.lostReceiptNo = lostReceiptNo;
    }

    public String getOldAccCardNo() {
        return oldAccCardNo;
    }

    public void setOldAccCardNo(String oldAccCardNo) {
        this.oldAccCardNo = oldAccCardNo;
    }

    public String getAccCardNo() {
        return accCardNo;
    }

    public void setAccCardNo(String accCardNo) {
        this.accCardNo = accCardNo;
    }

    public String getAccCardCost() {
        return accCardCost;
    }

    public void setAccCardCost(String accCardCost) {
        this.accCardCost = accCardCost;
    }
}
