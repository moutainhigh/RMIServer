package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记账卡换卡回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardReplaceNewcardReceiptACMS extends BaseReceiptContent {
    /**
     * 原 卡 号
     */
    private String oldAccCardNo;
    /**
     * 换卡方式
     */
    private String lockType;
    /**
     * 新 卡 号
     */
    private String accCardNo;
    /**
     * 工 本 费
     */
    private String accCardCost;

    public String getOldAccCardNo() {
        return oldAccCardNo;
    }

    public void setOldAccCardNo(String oldAccCardNo) {
        this.oldAccCardNo = oldAccCardNo;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
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
