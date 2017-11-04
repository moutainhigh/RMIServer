package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by FZH on 2017/7/5.
 */
public class OmsMinusBank implements Serializable {

    private static final long serialVersionUID = -6835180466518970860L;
    private Long id;
    private String clearingBankCode;
    private String focusHandleCode;
    private String focusHandleArea;
    private String bankName;
    private String remark;
    private Integer checkFlag;
    private Long operateId;
    private String operateName;
    private Date operateTime;
    private Long setPoint;
    private String setPointName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClearingBankCode() {
        return clearingBankCode;
    }

    public void setClearingBankCode(String clearingBankCode) {
        this.clearingBankCode = clearingBankCode;
    }

    public String getFocusHandleCode() {
        return focusHandleCode;
    }

    public void setFocusHandleCode(String focusHandleCode) {
        this.focusHandleCode = focusHandleCode;
    }

    public String getFocusHandleArea() {
        return focusHandleArea;
    }

    public void setFocusHandleArea(String focusHandleArea) {
        this.focusHandleArea = focusHandleArea;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Long getSetPoint() {
        return setPoint;
    }

    public void setSetPoint(Long setPoint) {
        this.setPoint = setPoint;
    }

    public String getSetPointName() {
        return setPointName;
    }

    public void setSetPointName(String setPointName) {
        this.setPointName = setPointName;
    }
}
