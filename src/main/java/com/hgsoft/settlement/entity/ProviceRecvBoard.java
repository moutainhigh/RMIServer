package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 接收公告信息表
 */
public class ProviceRecvBoard implements Serializable {


    private static final long serialVersionUID = 5425258023276283809L;

    /**
     * 省内消费流水
     */
    public static final String TABLECODE_CARDINSETTLEDETAIL = "8101";
    /**
     * 省外消费流水
     */
    public static final String TABLECODE_CARDOUTSETTLEDETAIL = "8102";

    /**
     * 初始接收
     */
    public static final int UPDAETFLAG_INIT = 0;
    /**
     * 已获取（更新清分库记录）
     */
    public static final int UPDAETFLAG_TAKE = 1;
    /**
     * 验证通过
     */
    public static final int UPDAETFLAG_VERIFIED = 2;
    /**
     * 验证失败
     */
    public static final int UPDAETFLAG_VERIFYFAIL = 3;
    /**
     * 处理完成
     */
    public static final int UPDAETFLAG_DONE = 4;
    /**
     * 储值卡处理完成
     */
    public static final int GENDETAILFLAG_SC_DONE = 1;

    /**
     * 记帐卡处理完成
     */
    public static final int GENDETAILFLAG_AC_DONE = 2;


    private Long listNo;
    private String tableCode;
    private String tableName;
    private String sendCode;
    private Date updateTime;
    private Long updateFlag;
    private Integer cnt;

    public Long getListNo() {
        return listNo;
    }

    public void setListNo(Long listNo) {
        this.listNo = listNo;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Long updateFlag) {
        this.updateFlag = updateFlag;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
