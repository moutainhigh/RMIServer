package com.hgsoft.system.entity;

import java.util.Date;


public class MessageSend implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2978316409407781687L;
	/**
	 * 
	 */
	// Fields
	private Long id;
	private String phone; //手机号码
	private String content; //短信内容
	private Date sendTime; //待发送时间
	private String portNo; //端口号
	private String state; //状态
	private String type; //短信业务类型
	private Long operateId; //录入员ID
	private String operateName; 
	private Date operateTime;
	
	public MessageSend() {
		
	}
	
	public MessageSend(Long id, String phone, String content, Date sendTime,
			String portNo, String state, String type, Long operateId,
			String operateName, Date operateTime) {
		super();
		this.id = id;
		this.phone = phone;
		this.content = content;
		this.sendTime = sendTime;
		this.portNo = portNo;
		this.state = state;
		this.type = type;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
}