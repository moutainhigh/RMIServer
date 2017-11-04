package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;

public class MessageCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6612591631860362419L;
	
	
	private Long id;
	private String code;
	private String name;
	private String remark;
	private String state;
	private Long messageGrade;
	private Long operateId;
	private String operateName;
	private Date operateTime;
	
	public MessageCategory() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "MessageCategory [id=" + id + ", code=" + code + ", name=" + name + ", remark=" + remark + ", state="
				+ state + ", messageGrade=" + messageGrade + ", operateId=" + operateId + ", operateName=" + operateName
				+ ", operateTime=" + operateTime + "]";
	}


	public MessageCategory(Long id, String code, String name, String remark, String state, Long messageGrade,
			Long operateId, String operateName, Date operateTime) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.state = state;
		this.messageGrade = messageGrade;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateTime = operateTime;
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


	public Long getMessageGrade() {
		return messageGrade;
	}


	public void setMessageGrade(Long messageGrade) {
		this.messageGrade = messageGrade;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
