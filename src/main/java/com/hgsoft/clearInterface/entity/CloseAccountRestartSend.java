package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 孙晓伟
 * file : CloseAccountRestartSend.java  TB_CLOSEDACCOUNTRESTART_SEND
 * date : 2017/8/11
 * time : 19:31
 */

public class CloseAccountRestartSend implements Serializable{
    private static final long serialVersionUID = -5094051937556481082L;

    private String accountId;

    private String cardCode;

    private Timestamp reqTime;

    private String remark;

    private Timestamp updateTime;

    private Long boardListNo;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Timestamp getReqTime() {
        return reqTime;
    }

    public void setReqTime(Timestamp reqTime) {
        this.reqTime = reqTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
}
