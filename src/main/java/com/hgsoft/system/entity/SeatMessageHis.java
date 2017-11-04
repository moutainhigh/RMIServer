package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;


public class SeatMessageHis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6731210167397740675L;
	
	private Long id;
	private String content;
	private String remark;
	private Long messageCategory;//OMS_messageCategory.id
	private Long operateId;
	private String operateName;
	private Date operateTime;
	private Long seatMessageId;
	private Date genTime;
	private String genReason;
	
	public SeatMessageHis() {
		// TODO Auto-generated constructor stub
	}
	
	public SeatMessageHis(SeatMessage seatMessage){
		this.content = seatMessage.getContent();
		this.remark = seatMessage.getRemark();
		this.messageCategory = seatMessage.getMessageCategory();
		this.operateId = seatMessage.getOperateId();
		this.operateTime = seatMessage.getOperateTime();
		this.operateName = seatMessage.getOperateName();
		this.seatMessageId = seatMessage.getId();
	}
	
	
	public SeatMessageHis(Long id, String content, String remark, Long messageCategory, Long operateId,
			String operateName, Date operateTime, Long seatMessageId, Date genTime, String genReason) {
		super();
		this.id = id;
		this.content = content;
		this.remark = remark;
		this.messageCategory = messageCategory;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateTime = operateTime;
		this.seatMessageId = seatMessageId;
		this.genTime = genTime;
		this.genReason = genReason;
	}
	
	@Override
	public String toString() {
		return "SeatMessageHis [id=" + id + ", content=" + content + ", remark=" + remark + ", messageCategory="
				+ messageCategory + ", operateId=" + operateId + ", operateName=" + operateName + ", operateTime="
				+ operateTime + ", seatMessageId=" + seatMessageId + ", genTime=" + genTime + ", genReason=" + genReason
				+ "]";
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


	public Long getSeatMessageId() {
		return seatMessageId;
	}


	public void setSeatMessageId(Long seatMessageId) {
		this.seatMessageId = seatMessageId;
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
