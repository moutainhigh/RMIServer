package com.hgsoft.agentCard.entity;

import java.io.Serializable;
import java.util.Date;

public class JoinCardNoSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4409203821937825436L;
	
    private Long id;//
    private String code;//卡号
    private String endCode;//结束卡号
    private String bankNo;//所属银行编码,对应tb_bankDetail维护表
    private String bankName;//所属银行名称
    private String compoundFlag;//复合卡标识
    private String remark;//备注
    private String cardType;//卡片类型
    private String checkFlag;//审核标志
    private Long operateId;//操作员ID
    private String operateName;//操作员登录名
    private Date operateTime;//受理时间
    private String customPoint;//受理网点
    private String issFlag;//保有量计算标识
    private Date checkTime;//审核时间
    private Long checkId;//审核员ID
    private String checkName;//审核员名称
    private Date issueTime;//发行时间
    private String workFlag;//有效标志
    private Date workTime;//有效时间
    
	@Override
	public String toString() {
		return "JoinCardNoSection [id=" + id + ", code=" + code + ", endCode=" + endCode + ", bankNo=" + bankNo
				+ ", bankName=" + bankName + ", compoundFlag=" + compoundFlag + ", remark=" + remark + ", cardType="
				+ cardType + ", checkFlag=" + checkFlag + ", operateId=" + operateId + ", operateName=" + operateName
				+ ", operateTime=" + operateTime + ", customPoint=" + customPoint + ", issFlag=" + issFlag
				+ ", checkTime=" + checkTime + ", checkId=" + checkId + ", checkName=" + checkName + ", issueTime="
				+ issueTime + ", workFlag=" + workFlag + ", workTime=" + workTime + "]";
	}

	public JoinCardNoSection(Long id, String code, String endCode, String bankNo, String bankName, String compoundFlag,
			String remark, String cardType, String checkFlag, Long operateId, String operateName, Date operateTime,
			String customPoint, String issFlag, Date checkTime, Long checkId, String checkName, Date issueTime,
			String workFlag, Date workTime) {
		super();
		this.id = id;
		this.code = code;
		this.endCode = endCode;
		this.bankNo = bankNo;
		this.bankName = bankName;
		this.compoundFlag = compoundFlag;
		this.remark = remark;
		this.cardType = cardType;
		this.checkFlag = checkFlag;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateTime = operateTime;
		this.customPoint = customPoint;
		this.issFlag = issFlag;
		this.checkTime = checkTime;
		this.checkId = checkId;
		this.checkName = checkName;
		this.issueTime = issueTime;
		this.workFlag = workFlag;
		this.workTime = workTime;
	}

	public JoinCardNoSection() {
		super();
	}

	public JoinCardNoSection(JoinCardNoSection joinCardNoSection) {
		this.id = joinCardNoSection.getId();
		this.code = joinCardNoSection.getCode();
		this.endCode = joinCardNoSection.getEndCode();
		this.bankNo = joinCardNoSection.getBankNo();
		this.bankName = joinCardNoSection.getBankName();
		this.compoundFlag = joinCardNoSection.getCompoundFlag();
		this.remark = joinCardNoSection.getRemark();
		this.cardType = joinCardNoSection.getCardType();
		this.checkFlag = joinCardNoSection.getCheckFlag();
		this.operateId = joinCardNoSection.getOperateId();
		this.operateName = joinCardNoSection.getOperateName();
		this.operateTime = joinCardNoSection.getOperateTime();
		this.customPoint = joinCardNoSection.getCustomPoint();
		this.issFlag = joinCardNoSection.getIssFlag();
		this.checkTime = joinCardNoSection.getCheckTime();
		this.checkId = joinCardNoSection.getCheckId();
		this.checkName = joinCardNoSection.getCheckName();
		this.issueTime = joinCardNoSection.getIssueTime();
		this.workFlag = joinCardNoSection.getWorkFlag();
		this.workTime = joinCardNoSection.getWorkTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCompoundFlag() {
		return compoundFlag;
	}

	public void setCompoundFlag(String compoundFlag) {
		this.compoundFlag = compoundFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
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

	public String getCustomPoint() {
		return customPoint;
	}

	public void setCustomPoint(String customPoint) {
		this.customPoint = customPoint;
	}

	public String getIssFlag() {
		return issFlag;
	}

	public void setIssFlag(String issFlag) {
		this.issFlag = issFlag;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public String getWorkFlag() {
		return workFlag;
	}

	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}

	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
    
    
}
