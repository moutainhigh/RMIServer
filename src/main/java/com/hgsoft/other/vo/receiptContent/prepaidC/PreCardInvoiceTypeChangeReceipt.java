package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡发票类型变更申请回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardInvoiceTypeChangeReceipt extends BaseReceiptContent {
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 原发票开具方式
     */
    private String oldPreCardInvoiceprint;
    /**
     * 新发票开具方式
     */
    private String preCardInvoiceprint;
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

    public String getOldPreCardInvoiceprint() {
        return oldPreCardInvoiceprint;
    }

    public void setOldPreCardInvoiceprint(String oldPreCardInvoiceprint) {
        this.oldPreCardInvoiceprint = oldPreCardInvoiceprint;
    }

    public String getPreCardInvoiceprint() {
        return preCardInvoiceprint;
    }

    public void setPreCardInvoiceprint(String preCardInvoiceprint) {
        this.preCardInvoiceprint = preCardInvoiceprint;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
