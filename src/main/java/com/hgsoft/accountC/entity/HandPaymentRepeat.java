package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangzhongji on 17/9/28.
 */
public class HandPaymentRepeat implements Serializable {

    private static final long serialVersionUID = -4521756457515666072L;

    private Long id;
    private String baccount;
    private Date payTime;
    private Date startTime;
    private Date endTime;
    private BigDecimal etcFee;
    private String flag;
    private Long operId;
    private String operNo;
    private String operName;
    private Long placeId;
    private String placeNo;
    private String placeName;
    private Date genTime;
    private String acbAccount;
    private Date cmdGenTime;
    private Date hdlDatetime;
    private BigDecimal income;
    private Date updateTime;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaccount() {
        return baccount;
    }

    public void setBaccount(String baccount) {
        this.baccount = baccount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getEtcFee() {
        return etcFee;
    }

    public void setEtcFee(BigDecimal etcFee) {
        this.etcFee = etcFee;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(String placeNo) {
        this.placeNo = placeNo;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public String getAcbAccount() {
        return acbAccount;
    }

    public void setAcbAccount(String acbAccount) {
        this.acbAccount = acbAccount;
    }

    public Date getCmdGenTime() {
        return cmdGenTime;
    }

    public void setCmdGenTime(Date cmdGenTime) {
        this.cmdGenTime = cmdGenTime;
    }

    public Date getHdlDatetime() {
        return hdlDatetime;
    }

    public void setHdlDatetime(Date hdlDatetime) {
        this.hdlDatetime = hdlDatetime;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
