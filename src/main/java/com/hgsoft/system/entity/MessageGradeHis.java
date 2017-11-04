package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;


public class MessageGradeHis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567295331536704950L;
	
	private Long id;
	private String code;
	private String name;
	private String remark;
	private String state;
	private Long operateId;
	private String operateName;
	private Date operateTime;
	private Long messageGradeId;
	private Date genTime;
	private String genReason;
	
	
	public MessageGradeHis() {
		// TODO Auto-generated constructor stub
	}
	
	public MessageGradeHis(MessageGrade messageGrade) {
		// TODO Auto-generated constructor stub
		this.code = messageGrade.getCode();
		this.name = messageGrade.getName();
		this.remark = messageGrade.getRemark();
		this.state = messageGrade.getState();
		this.operateId = messageGrade.getOperateId();
		this.operateName = messageGrade.getOperateName();
		this.messageGradeId = messageGrade.getId();
	}

	public MessageGradeHis(Long id, String code, String name, String remark, String state, Long operateId,
			String operateName, Date operateTime, Long messageGradeId, Date genTime, String genReason) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.state = state;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateTime = operateTime;
		this.messageGradeId = messageGradeId;
		this.genTime = genTime;
		this.genReason = genReason;
	}

	@Override
	public String toString() {
		return "MessageGradeHis [id=" + id + ", code=" + code + ", name=" + name + ", remark=" + remark + ", state="
				+ state + ", operateId=" + operateId + ", operateName=" + operateName + ", operateTime=" + operateTime
				+ ", messageGradeId=" + messageGradeId + ", genTime=" + genTime + ", genReason=" + genReason + "]";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public Long getMessageGradeId() {
		return messageGradeId;
	}

	public void setMessageGradeId(Long messageGradeId) {
		this.messageGradeId = messageGradeId;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getGenReason() {
		return genReason;
	}

	public void setGenReason(String genReason) {
		this.genReason = genReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
