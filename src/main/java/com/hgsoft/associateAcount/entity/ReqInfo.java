package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class ReqInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7424291607474629162L;
	private Long id;
	private Long customerId;
	private String accode;
	private Long operid;
	private Date reqTime;
	private Date setTime;
	private Date cancelTime;
	private Long placeId;
	private String fileName;
	private String useNo;
	private String serNo;
	private String memo;
	private Long hisseqId;
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
	public String getAccode() {
		return accode;
	}
	public void setAccode(String accode) {
		this.accode = accode;
	}
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public Date getSetTime() {
		return setTime;
	}
	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUseNo() {
		return useNo;
	}
	public void setUseNo(String useNo) {
		this.useNo = useNo;
	}
	public String getSerNo() {
		return serNo;
	}
	public void setSerNo(String serNo) {
		this.serNo = serNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getHisseqId() {
		return hisseqId;
	}
	public void setHisseqId(Long hisseqId) {
		this.hisseqId = hisseqId;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	
	
	
}

