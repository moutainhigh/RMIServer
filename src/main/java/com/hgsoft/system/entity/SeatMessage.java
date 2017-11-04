package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;

public class SeatMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 46592049153338998L;
	
	private Long id;
	private String content;
	private String remark;
	private Long messageCategory;
	private Long operateId;
	private String operateName;
	private Date operateTime;
	
	public SeatMessage() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "SeatMessage [id=" + id + ", content=" + content + ", remark=" + remark + ", messageCategory="
				+ messageCategory + ", operateId=" + operateId + ", operateName=" + operateName + ", operateTime="
				+ operateTime + "]";
	}

	public SeatMessage(Long id, String content, String remark, Long messageCategory, Long operateId, String operateName,
			Date operateTime) {
		super();
		this.id = id;
		this.content = content;
		this.remark = remark;
		this.messageCategory = messageCategory;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getMessageCategory() {
		return messageCategory;
	}

	public void setMessageCategory(Long messageCategory) {
		this.messageCategory = messageCategory;
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
