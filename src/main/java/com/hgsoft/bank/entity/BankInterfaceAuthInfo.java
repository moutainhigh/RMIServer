package com.hgsoft.bank.entity;

import java.io.Serializable;

/**
 * Created by 孙晓伟
 * file : BankInterfaceAuthInfo.java
 * date : 2017/7/27
 * time : 9:24
 */
public class BankInterfaceAuthInfo implements Serializable {

    private static final long serialVersionUID = -421411561105342128L;

    private Long id;

    private String bankCode;

    private String transactionCode;

    private String businessCode;

    private String businessType;

    private String enableFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}



