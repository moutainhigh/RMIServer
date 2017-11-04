package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6220491211253836220L;
	private Long id;
	private Long customerId;
	private String userNo;
	private String accode;
	private Long operId;
	private Date reqtime;
	private Long placeId;
	private String memo;
	public void setId(Long id){
		this.id=id;
	}
	public Long getId(){
		return id;
	}

	public String getAccode() {
		return accode;
	}
	public void setAccode(String accode) {
		this.accode = accode;
	}
	public void setReqtime(Date reqtime){
		this.reqtime=reqtime;
	}
	public Date getReqtime(){
		return reqtime;
	}
	public void setMemo(String memo){
		this.memo=memo;
	}
	public String getMemo(){
		return memo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
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

