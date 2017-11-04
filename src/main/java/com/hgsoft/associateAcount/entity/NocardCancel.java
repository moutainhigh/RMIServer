package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class NocardCancel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8683969076279593712L;
	private Long id;
	private Long customerId;
	private String cardNo;
	private Date serviceTime;
	private Long operId;
	private Long placeId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Date getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
	
}

