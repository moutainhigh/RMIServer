package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.util.Date;

public class Road implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1644279916365278948L;
    private Long id;
    private Long provinceNo;//省区代码
    private Long areaNo;//区域代码
    private String areaName;//区域名称
    //新增外键关联字段
    private Long areaId;

    private Long roadNo;//路段编码
    private String roadName;//路段名称
    private String roadFullName;//路段全称
    private String roadServerName;//路段服务器名
    private String roadServerIp;//路段服务器IP
    private String roaddoMain;//路段域名
    private String roaddbName;//路段数据库名
    private String roaddbUserName;//路段数据库用户
    private String roaddbPassword;//路段数据库用户密码
    private Integer freePosition;//免费位置,数据库是number类型
    private String ownerCode;//业主代码
    private Date genTime;//生成日期时间
    private Integer status;//使用标志0：使用中；1：删除；数据库是number类型
    private Long operateId;//操作员代码
    private String operateName;//操作员名字
    private Long checkId;
    private String checkName;
    private Date checkTime;
    private String checkFlag;
    private String operateFlag;//操作标志1添加2修改3正常使用中
    private Long gencheckPassId;
    private String tradeType;


    public Road() {
        super();
    }

    public Road(Road road) {
        this.id = road.getGencheckPassId();
        this.areaNo = road.getAreaNo();
        this.areaName = road.getAreaName();
        this.roadNo = road.getRoadNo();
        this.roadName = road.getRoadName();
        this.provinceNo = road.getProvinceNo();
        this.areaNo = road.getAreaNo();
        this.roadFullName = road.getRoadFullName();
        this.roadServerName = road.getRoadServerName();
        this.roadServerIp = road.getRoadServerIp();
        this.roaddoMain = road.getRoaddoMain();
        this.roaddbName = road.getRoaddbName();
        this.roaddbUserName = road.getRoaddbUserName();
        this.roaddbPassword = road.getRoaddbPassword();
        this.freePosition = road.getFreePosition();
        this.ownerCode = road.getOwnerCode();
        this.operateId = road.getOperateId();
        this.operateName = road.getOperateName();
        this.genTime = road.getGenTime();
        this.status = road.getStatus();
        this.checkId = road.getCheckId();
        this.checkName = road.getCheckName();
        this.checkFlag = road.getCheckFlag();
        this.checkTime = road.getCheckTime();
        this.gencheckPassId = null;


    }

    @Override
    public String toString() {
        return "Road [id=" + id + ", roadNo=" + roadNo + ", roadName=" + roadName + ", provinceNo=" + provinceNo
                + ", areaNo=" + areaNo + ", areaName=" + areaName + ", roadFullName=" + roadFullName
                + ", roadServerName=" + roadServerName + ", roadServerIp=" + roadServerIp + ", roaddoMain=" + roaddoMain
                + ", roaddbName=" + roaddbName + ", roaddbUserName=" + roaddbUserName + ", roaddbPassword="
                + roaddbPassword + ", freePosition=" + freePosition + ", ownerCode=" + ownerCode + ", operateId="
                + operateId + ", operateName=" + operateName + ", operateFlag=" + operateFlag + ", genTime=" + genTime
                + ", status=" + status + ", checkId=" + checkId + ", checkTime=" + checkTime + ", checkFlag="
                + checkFlag + ", checkName=" + checkName + ", gencheckPassId=" + gencheckPassId + ", areaId=" + areaId
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(Long roadNo) {
        this.roadNo = roadNo;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Long getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(Long provinceNo) {
        this.provinceNo = provinceNo;
    }

    public Long getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(Long areaNo) {
        this.areaNo = areaNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoadFullName() {
        return roadFullName;
    }

    public void setRoadFullName(String roadFullName) {
        this.roadFullName = roadFullName;
    }

    public String getRoadServerName() {
        return roadServerName;
    }

    public void setRoadServerName(String roadServerName) {
        this.roadServerName = roadServerName;
    }

    public String getRoadServerIp() {
        return roadServerIp;
    }

    public void setRoadServerIp(String roadServerIp) {
        this.roadServerIp = roadServerIp;
    }

    public String getRoaddoMain() {
        return roaddoMain;
    }

    public void setRoaddoMain(String roaddoMain) {
        this.roaddoMain = roaddoMain;
    }

    public String getRoaddbName() {
        return roaddbName;
    }

    public void setRoaddbName(String roaddbName) {
        this.roaddbName = roaddbName;
    }

    public String getRoaddbUserName() {
        return roaddbUserName;
    }

    public void setRoaddbUserName(String roaddbUserName) {
        this.roaddbUserName = roaddbUserName;
    }

    public String getRoaddbPassword() {
        return roaddbPassword;
    }

    public void setRoaddbPassword(String roaddbPassword) {
        this.roaddbPassword = roaddbPassword;
    }

    public Integer getFreePosition() {
        return freePosition;
    }

    public void setFreePosition(Integer freePosition) {
        this.freePosition = freePosition;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }


    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(String operateFlag) {
        this.operateFlag = operateFlag;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Long getGencheckPassId() {
        return gencheckPassId;
    }

    public void setGencheckPassId(Long gencheckPassId) {
        this.gencheckPassId = gencheckPassId;
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
