package com.hgsoft.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签过户回执
 * Created by wiki on 2017/8/22.
 */
public class TagTransferReceipt extends BaseReceiptContent {
    /**
     * 标 签 号
     */
    private String tagNo;
    /**
     * 新客户号
     */
    private String newCustomerNo;
    /**
     * 新证件类型
     */
    private String newCustomerName;
    /**
     * 新证件号码
     */
    private String newCustomerIdType;
    /**
     * 新客户名称
     */
    private String newCustomerIdCode;

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getNewCustomerNo() {
        return newCustomerNo;
    }

    public void setNewCustomerNo(String newCustomerNo) {
        this.newCustomerNo = newCustomerNo;
    }

    public String getNewCustomerName() {
        return newCustomerName;
    }

    public void setNewCustomerName(String newCustomerName) {
        this.newCustomerName = newCustomerName;
    }

    public String getNewCustomerIdType() {
        return newCustomerIdType;
    }

    public void setNewCustomerIdType(String newCustomerIdType) {
        this.newCustomerIdType = newCustomerIdType;
    }

    public String getNewCustomerIdCode() {
        return newCustomerIdCode;
    }

    public void setNewCustomerIdCode(String newCustomerIdCode) {
        this.newCustomerIdCode = newCustomerIdCode;
    }
}
