package com.hgsoft.prepaidC.entity;

import java.io.Serializable;

public class PrepaidCTransferDetail implements Serializable {

	private static final long serialVersionUID = -9119835751565451262L;
	private Long id;
	private Long transferId;
	private String cardNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
