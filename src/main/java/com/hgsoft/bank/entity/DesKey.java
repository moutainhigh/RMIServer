package com.hgsoft.bank.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : 吴锡霖
 * file : DesKey.java
 * date : 2017-06-01
 * time : 19:55
 */
public class DesKey implements Serializable {

    private static final long serialVersionUID = -42141156110542128L;

    private Long id;
    private String operNo;
    private String terminalNo;
    private Date updateTime;
    private String bankNo;
    private String desKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }
}
