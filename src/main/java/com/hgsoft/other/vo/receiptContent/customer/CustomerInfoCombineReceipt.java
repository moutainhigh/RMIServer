package com.hgsoft.other.vo.receiptContent.customer;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 客户信息合并回执
 */
public class CustomerInfoCombineReceipt extends BaseReceiptContent {
    /**
     * 客户类型
     */
    private String customerType;
    /**
     * 二级编码
     */
    private String customerSecondNo;
    /**
     * 二级编码名称
     */
    private String customerSecondName;
    /**
     * 联系人
     */
    private String customerLinkMan;
    /**
     * 联系手机
     */
    private String customerMobile;
    /**
     * 短信接收手机
     */
    private String customerShortTel;
    /**
     * 联系电话
     */
    private String customerTel;
    /**
     * 电子邮箱
     */
    private String customerEmail;
    /**
     * 邮政编码
     */
    private String customerZipCode;
    /**
     * 地址
     */
    private String customerAddr;
    /**
     * 数组json格式(卡号)
     */
    private String cardNoJson;
    /**
     * 数组json格式(电子标签号)
     */
    private String tagNoJson;
    /**
     * 数组json格式(车牌号码和颜色)
     */
    private String plateAndColorJson;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerSecondNo() {
        return customerSecondNo;
    }

    public void setCustomerSecondNo(String customerSecondNo) {
        this.customerSecondNo = customerSecondNo;
    }

    public String getCustomerSecondName() {
        return customerSecondName;
    }

    public void setCustomerSecondName(String customerSecondName) {
        this.customerSecondName = customerSecondName;
    }

    public String getCustomerLinkMan() {
        return customerLinkMan;
    }

    public void setCustomerLinkMan(String customerLinkMan) {
        this.customerLinkMan = customerLinkMan;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerShortTel() {
        return customerShortTel;
    }

    public void setCustomerShortTel(String customerShortTel) {
        this.customerShortTel = customerShortTel;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCardNoJson() {
        return cardNoJson;
    }

    public void setCardNoJson(String cardNoJson) {
        this.cardNoJson = cardNoJson;
    }

    public String getTagNoJson() {
        return tagNoJson;
    }

    public void setTagNoJson(String tagNoJson) {
        this.tagNoJson = tagNoJson;
    }

    public String getPlateAndColorJson() {
        return plateAndColorJson;
    }

    public void setPlateAndColorJson(String plateAndColorJson) {
        this.plateAndColorJson = plateAndColorJson;
    }
}
