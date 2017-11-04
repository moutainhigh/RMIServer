package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by FZH on 2017/7/5.
 */
public class TbBankdetail implements Serializable {

    private static final long serialVersionUID = 5214099478747347441L;
    private Long id;
    private Long obaNo;
    private String bankName;
    private Timestamp setTime;
    private String remark;
    private Long operateId;
    private String operateName;
    private Long checkId;
    private String checkName;
    private Date checkTime;
    private Integer checkFlag;
    private Integer operateFlag;
    private Integer interbankFlag;//是否允许跨行划扣
    private Long gencheckPassId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObaNo() {
        return obaNo;
    }

    public void setObaNo(Long obaNo) {
        this.obaNo = obaNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Timestamp getSetTime() {
        return setTime;
    }

    public void setSetTime(Timestamp setTime) {
        this.setTime = setTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(Integer operateFlag) {
        this.operateFlag = operateFlag;
    }

    public Long getGencheckPassId() {
        return gencheckPassId;
    }

    public Integer getInterbankFlag() {
        return interbankFlag;
    }

    public void setInterbankFlag(Integer interbankFlag) {
        this.interbankFlag = interbankFlag;
    }

    public void setGencheckPassId(Long gencheckPassId) {
        this.gencheckPassId = gencheckPassId;
    }
}
