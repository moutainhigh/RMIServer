package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccCardUserInfoSend implements Serializable{

    private static final long serialVersionUID = -1570839079990784697L;
    private Long id;
    private String organ;
    private Integer accountType;
    private Long bankNo;
    private String bankAccount;
    private String bankName;
    private Long spanBankNo;
    private String reconBankNo;
    private String branchBankno;
    private Integer virType;
    private Integer virCount;
    private BigDecimal maxAcr;
    private Integer systemType;
    private Timestamp reqTime;
    private Timestamp checkTime;
    private String reMark;
    private Timestamp updateTime;
    private Long boardListNo;
    private String accName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Long getBankNo() {
        return bankNo;
    }

    public void setBankNo(Long bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getSpanBankNo() {
        return spanBankNo;
    }

    public void setSpanBankNo(Long spanBankNo) {
        this.spanBankNo = spanBankNo;
    }

    public String getReconBankNo() {
        return reconBankNo;
    }

    public void setReconBankNo(String reconBankNo) {
        this.reconBankNo = reconBankNo;
    }

    public String getBranchBankno() {
        return branchBankno;
    }

    public void setBranchBankno(String branchBankno) {
        this.branchBankno = branchBankno;
    }

    public Integer getVirType() {
        return virType;
    }

    public void setVirType(Integer virType) {
        this.virType = virType;
    }

    public Integer getVirCount() {
        return virCount;
    }

    public void setVirCount(Integer virCount) {
        this.virCount = virCount;
    }

    public BigDecimal getMaxAcr() {
        return maxAcr;
    }

    public void setMaxAcr(BigDecimal maxAcr) {
        this.maxAcr = maxAcr;
    }

    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    public Timestamp getReqTime() {
        return reqTime;
    }

    public void setReqTime(Timestamp reqTime) {
        this.reqTime = reqTime;
    }

    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getBoardListNo() {
        return boardListNo;
    }

    public void setBoardListNo(Long boardListNo) {
        this.boardListNo = boardListNo;
    }

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}
}