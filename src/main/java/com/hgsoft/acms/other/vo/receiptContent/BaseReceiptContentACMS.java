package com.hgsoft.acms.other.vo.receiptContent;

/**
 * Created by wiki on 2017/8/17.
 */
public abstract class BaseReceiptContentACMS {
    /**
     * 回执标题
     */
    private String title;
    /**
     * 办理方式
     */
    private String handleWay;
    /**
     * 客户编号
     */
    private String customerNo;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 证件类型
     */
    private String customerIdType;
    /**
     * 证件号码
     */
    private String customerIdCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHandleWay() {
        return handleWay;
    }

    public void setHandleWay(String handleWay) {
        this.handleWay = handleWay;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIdType() {
        return customerIdType;
    }

    public void setCustomerIdType(String customerIdType) {
        this.customerIdType = customerIdType;
    }

    public String getCustomerIdCode() {
        return customerIdCode;
    }

    public void setCustomerIdCode(String customerIdCode) {
        this.customerIdCode = customerIdCode;
    }

}