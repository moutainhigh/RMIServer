package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;

public class MessageGrade implements Serializable{

	/**
	 * 短信内容级别
	 */
	private static final long serialVersionUID = 5540123555462620204L;
	
	private Long id;
	private String code;
	private String name;
	private String remark;
	private String state;
	private Long operateId;
	private String operateName;
	private Date operateTime;
	
	public MessageGrade() {
		// TODO Auto-generated constructor stub
	}

	
	public MessageGrade(Long id, String code, String name, String remark, String state, Long operateId,
			String operateName, Date operateTime) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.state = state;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "MessageGrade [id=" + id + ", code=" + code + ", name=" + name + ", remark=" + remark + ", state="
				+ state + ", operateId=" + operateId + ", operateName=" + operateName + ", operateTime=" + operateTime
				+ "]";
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
