package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.util.Date;

public class MonthlyReg implements Serializable {

    /**
     * 未审核
     */
    public static final Integer CHECKFLAG_NOTCHECK = 0;
    /**
     * 已审核
     */
    public static final Integer CHECKFLAG_CHECKED = 1;
    /**
     * 未生成账单
     */
    public static final Integer GENBILLFLAG_NOT = 0;
    /**
     * 已生成账单
     */
    public static final Integer GENBILLFLAG_GEN = 1;


    private static final long serialVersionUID = -6356720389359278219L;

    private Long id;
    private Date startDispartTime;
    private Date endDispartTime;
    private Integer settleMonth;
    private Integer checkResult;
    private String checkOpNo;
    private Date checkTime;
    private Date startGenBillTime;
    private Integer genBillFlag;
    private Date genBillTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDispartTime() {
        return startDispartTime;
    }

    public void setStartDispartTime(Date startDispartTime) {
        this.startDispartTime = startDispartTime;
    }

    public Date getEndDispartTime() {
        return endDispartTime;
    }

    public void setEndDispartTime(Date endDispartTime) {
        this.endDispartTime = endDispartTime;
    }

    public Integer getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(Integer settleMonth) {
        this.settleMonth = settleMonth;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckOpNo() {
        return checkOpNo;
    }

    public void setCheckOpNo(String checkOpNo) {
        this.checkOpNo = checkOpNo;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getStartGenBillTime() {
        return startGenBillTime;
    }

    public void setStartGenBillTime(Date startGenBillTime) {
        this.startGenBillTime = startGenBillTime;
    }

    public Integer getGenBillFlag() {
        return genBillFlag;
    }

    public void setGenBillFlag(Integer genBillFlag) {
        this.genBillFlag = genBillFlag;
    }

    public Date getGenBillTime() {
        return genBillTime;
    }

    public void setGenBillTime(Date genBillTime) {
        this.genBillTime = genBillTime;
    }
}
