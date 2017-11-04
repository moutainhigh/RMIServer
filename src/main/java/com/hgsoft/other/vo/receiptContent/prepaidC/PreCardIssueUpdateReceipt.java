package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡服务方式修改回执
 * Created by wiki on 2017/9/12.
 */
public class PreCardIssueUpdateReceipt extends BaseReceiptContent {
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 原服务方式
     */
    private String oldSerItem;
    /**
     * 新服务方式
     */
    private String newSerItem;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getOldSerItem() {
        return oldSerItem;
    }

    public void setOldSerItem(String oldSerItem) {
        this.oldSerItem = oldSerItem;
    }

    public String getNewSerItem() {
        return newSerItem;
    }

    public void setNewSerItem(String newSerItem) {
        this.newSerItem = newSerItem;
    }
}
