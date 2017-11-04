package com.hgsoft.system.entity;

import java.util.Date;


public class MessageSendOut implements java.io.Serializable {

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
	private Date tobeSendTime;//待发送时间
	private Date sendTime; //发送时间
	private String portNo; //端口号
	private String state; //状态
	private String type; //短信业务类型
	private String sendMode;//发送方式
	private Long operateId; //录入员ID
	private String operateName; 
	private Date operateTime;
	private Long messageImportID;
	public MessageSendOut(){
		
	}
	public MessageSendOut(MessageSend messageSend) {
		this.phone=messageSend.getPhone();
		this.content=messageSend.getContent();
		this.tobeSendTime=messageSend.getSendTime();
		this.portNo=messageSend.getPortNo();
		this.state=messageSend.getState();
		this.type="7";
		this.operateId=messageSend.getOperateId();
		this.operateName=messageSend.getOperateName();
		this.operateTime=messageSend.getOperateTime();
		this.messageImportID=messageSend.getId();
		this.sendMode="2";
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

	public Date getTobeSendTime() {
		return tobeSendTime;
	}

	public void setTobeSendTime(Date tobeSendTime) {
		this.tobeSendTime = tobeSendTime;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
	public Long getMessageImportID() {
		return messageImportID;
	}
	public void setMessageImportID(Long messageImportID) {
		this.messageImportID = messageImportID;
	}
	
}