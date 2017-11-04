package com.hgsoft.system.entity;

import java.util.Date;


public class MessageSendHis implements java.io.Serializable {

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
	private Long messageSendId;
	private Date genTime;
	private String genReason;
	
	public MessageSendHis() {
		
	}

	public MessageSendHis(Long id, String phone, String content, Date sendTime,
			String portNo, String state, String type, Long operateId,
			String operateName, Date operateTime, Long messageSendId,
			Date genTime, String genReason) {
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
		this.messageSendId = messageSendId;
		this.genTime = genTime;
		this.genReason = genReason;
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

	public Long getMessageSendId() {
		return messageSendId;
	}

	public void setMessageSendId(Long messageSendId) {
		this.messageSendId = messageSendId;
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

	public void copyFromMessageSend(MessageSend temp) {
		this.content = temp.getContent();
		this.phone = temp.getPhone();
		this.sendTime = temp.getSendTime();
		this.portNo = temp.getPortNo();
		this.state = temp.getState();
		this.type = temp.getType();
		this.operateId = temp.getOperateId();
		this.operateName = temp.getOperateName();
		this.operateTime = temp.getOperateTime();
		this.messageSendId = temp.getId();
	}
}