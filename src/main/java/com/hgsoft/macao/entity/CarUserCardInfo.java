package com.hgsoft.macao.entity;

public class CarUserCardInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 35225940524512114L;
	
	private Long macaoCardCustomerId;
	private Long accountCId;
	private Long vehicleId;
	private Long tagId;
	
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public Long getMacaoCardCustomerId() {
		return macaoCardCustomerId;
	}
	public void setMacaoCardCustomerId(Long macaoCardCustomerId) {
		this.macaoCardCustomerId = macaoCardCustomerId;
	}
	public Long getAccountCId() {
		return accountCId;
	}
	public void setAccountCId(Long accountCId) {
		this.accountCId = accountCId;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
}
