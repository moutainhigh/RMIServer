
package com.hgsoft.customer.entity;

import java.util.Date;

public class InvoiceHis  implements java.io.Serializable {

	private Long id;
    
	private Long mainId;
	
	private Date genTime;
    
	private String genReason;
    
	private String invoiceTitle;
    
	private String isdefault;
    
	private Long operId;
    
	private Long placeId;
    
	private Date up_Date;
    
	private Long hisSeqId;
    
	private String operNo;
	
	private String operName;
	
	private String placeNo;
	
	private String placeName;

	public InvoiceHis() {
		super();
	}

	public InvoiceHis(Long id, Long mainId, Date genTime, String genReason, String invoiceTitle, String isdefault,
			Long operId, Long placeId, Date up_Date, Long hisSeqId, String operNo, String operName, String placeNo,
			String placeName) {
		super();
		this.id = id;
		this.mainId = mainId;
		this.genTime = genTime;
		this.genReason = genReason;
		this.invoiceTitle = invoiceTitle;
		this.isdefault = isdefault;
		this.operId = operId;
		this.placeId = placeId;
		this.up_Date = up_Date;
		this.hisSeqId = hisSeqId;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
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

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
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

	public Date getUp_Date() {
		return up_Date;
	}

	public void setUp_Date(Date up_Date) {
		this.up_Date = up_Date;
	}

	public Long getHisSeqId() {
		return hisSeqId;
	}

	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}

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
	

}