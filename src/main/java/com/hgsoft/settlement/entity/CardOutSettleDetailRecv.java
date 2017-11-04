package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;


public class CardOutSettleDetailRecv implements Serializable {


    public static final String TABLE_FIELD_NAMES = "ID,boardlistno,serviceproviderid,messageid,transid, cardcode,cardtype,ennetno,enroadid,enstationid, enlaneid,enstationname,enlanetype,entime,exnetno, exroadid,exstationid,exlaneid,exstationname,exlanetype, extime,postbalance,transtype,trseq,termcode, termseq,tac,squaddate,toll,realtoll, reckonstatus,balancetime,settlemonth,recevicetime,filename";
    public static final int[] TABLE_FIELD_TYPES = {
            Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.BIGINT, Types.BIGINT,
            Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BIGINT, Types.BIGINT,
            Types.BIGINT, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER,
            Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.INTEGER,
            Types.TIMESTAMP, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT,
            Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR
    };

    private static final long serialVersionUID = -1326780296240755513L;

    private Long id;
    private Long tableId;
    private String serviceProviderId;
    private Long mesageId;
    private Long transId;
    private String cardCode;
    private Integer cardType;
    private Integer enNetNo;
    private Long enRoadId;
    private Long enStationId;
    private Long enLaneId;
    private String enStationName;
    private Integer enLaneType;
    private Date enTime;
    private Integer exNetNo;
    private Long exRoadId;
    private Long exStationId;
    private Long exLaneId;
    private String exStationName;
    private Integer exLaneType;
    private Date exTime;
    private Long postBalance;
    private String transType;
    private String trseq;
    private String termCode;
    private String termSeq;
    private String tac;
    private Date squadDate;
    private Long toll;
    private Long realToll;
    private Integer reckonStatus;
    private Date balanceTime;
    private Integer settleMonth;
    private Date receviceTime;
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Long getMesageId() {
        return mesageId;
    }

    public void setMesageId(Long mesageId) {
        this.mesageId = mesageId;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getEnNetNo() {
        return enNetNo;
    }

    public void setEnNetNo(Integer enNetNo) {
        this.enNetNo = enNetNo;
    }

    public Long getEnRoadId() {
        return enRoadId;
    }

    public void setEnRoadId(Long enRoadId) {
        this.enRoadId = enRoadId;
    }

    public Long getEnStationId() {
        return enStationId;
    }

    public void setEnStationId(Long enStationId) {
        this.enStationId = enStationId;
    }

    public Long getEnLaneId() {
        return enLaneId;
    }

    public void setEnLaneId(Long enLaneId) {
        this.enLaneId = enLaneId;
    }

    public String getEnStationName() {
        return enStationName;
    }

    public void setEnStationName(String enStationName) {
        this.enStationName = enStationName;
    }

    public Integer getEnLaneType() {
        return enLaneType;
    }

    public void setEnLaneType(Integer enLaneType) {
        this.enLaneType = enLaneType;
    }

    public Date getEnTime() {
        return enTime;
    }

    public void setEnTime(Date enTime) {
        this.enTime = enTime;
    }

    public Integer getExNetNo() {
        return exNetNo;
    }

    public void setExNetNo(Integer exNetNo) {
        this.exNetNo = exNetNo;
    }

    public Long getExRoadId() {
        return exRoadId;
    }

    public void setExRoadId(Long exRoadId) {
        this.exRoadId = exRoadId;
    }

    public Long getExStationId() {
        return exStationId;
    }

    public void setExStationId(Long exStationId) {
        this.exStationId = exStationId;
    }

    public Long getExLaneId() {
        return exLaneId;
    }

    public void setExLaneId(Long exLaneId) {
        this.exLaneId = exLaneId;
    }

    public String getExStationName() {
        return exStationName;
    }

    public void setExStationName(String exStationName) {
        this.exStationName = exStationName;
    }

    public Integer getExLaneType() {
        return exLaneType;
    }

    public void setExLaneType(Integer exLaneType) {
        this.exLaneType = exLaneType;
    }

    public Date getExTime() {
        return exTime;
    }

    public void setExTime(Date exTime) {
        this.exTime = exTime;
    }

    public Long getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(Long postBalance) {
        this.postBalance = postBalance;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTrseq() {
        return trseq;
    }

    public void setTrseq(String trseq) {
        this.trseq = trseq;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTermSeq() {
        return termSeq;
    }

    public void setTermSeq(String termSeq) {
        this.termSeq = termSeq;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public Date getSquadDate() {
        return squadDate;
    }

    public void setSquadDate(Date squadDate) {
        this.squadDate = squadDate;
    }

    public Long getToll() {
        return toll;
    }

    public void setToll(Long toll) {
        this.toll = toll;
    }

    public Long getRealToll() {
        return realToll;
    }

    public void setRealToll(Long realToll) {
        this.realToll = realToll;
    }

    public Integer getReckonStatus() {
        return reckonStatus;
    }

    public void setReckonStatus(Integer reckonStatus) {
        this.reckonStatus = reckonStatus;
    }

    public Date getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Date balanceTime) {
        this.balanceTime = balanceTime;
    }

    public Integer getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(Integer settleMonth) {
        this.settleMonth = settleMonth;
    }

    public Date getReceviceTime() {
        return receviceTime;
    }

    public void setReceviceTime(Date receviceTime) {
        this.receviceTime = receviceTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
