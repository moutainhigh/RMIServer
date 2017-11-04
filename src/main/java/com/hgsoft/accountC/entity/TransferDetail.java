package com.hgsoft.accountC.entity;

import java.util.Date;

public class TransferDetail implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -5153674440085734558L;
	private Long id;
	private Long transferId;
	private String cardNo;
	private Long operId;
	private Long placeId;
	private Date operTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	// Constructors

	/** default constructor */
	public TransferDetail() {
	}

	public TransferDetail(Long id, Long transferId, String cardNo, Long operId,
			Long placeId, Date operTime) {
		super();
		this.id = id;
		this.transferId = transferId;
		this.cardNo = cardNo;
		this.operId = operId;
		this.placeId = placeId;
		this.operTime = operTime;
	}

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

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

}