package com.hgsoft.acms.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签过户回执
 * Created by wiki on 2017/8/22.
 */
public class TagTransferReceiptACMS extends BaseReceiptContent {
    /**
     * 标 签 号
     */
    private String tagNo;
    /**
     * 原客户号
     */
    private String oldCustomerNo;
    /**
     * 原证件类型
     */
    private String oldCustomerName;
    /**
     * 原证件号码
     */
    private String oldCustomerIdType;
    /**
     * 原客户名称
     */
    private String oldCustomerIdCode;

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getOldCustomerNo() {
        return oldCustomerNo;
    }

    public void setOldCustomerNo(String oldCustomerNo) {
        this.oldCustomerNo = oldCustomerNo;
    }

    public String getOldCustomerName() {
        return oldCustomerName;
    }

    public void setOldCustomerName(String oldCustomerName) {
        this.oldCustomerName = oldCustomerName;
    }

    public String getOldCustomerIdType() {
        return oldCustomerIdType;
    }

    public void setOldCustomerIdType(String oldCustomerIdType) {
        this.oldCustomerIdType = oldCustomerIdType;
    }

    public String getOldCustomerIdCode() {
        return oldCustomerIdCode;
    }

    public void setOldCustomerIdCode(String oldCustomerIdCode) {
        this.oldCustomerIdCode = oldCustomerIdCode;
    }
}
