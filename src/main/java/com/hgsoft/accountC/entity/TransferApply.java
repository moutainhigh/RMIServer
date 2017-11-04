package com.hgsoft.accountC.entity;

import java.util.Date;


public class TransferApply implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1884581219346973757L;
	private Long id;
	private Long oldaccountId;
	private Long newaccountId;
	private String flag;
	private String appState;
	private Date effectTime;
	private Long approver;
	private Date apptime;
	private Long operId;
	private Long placeId;
	private Date operTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String approverNo;
	private String approverName;
	
	public String getApproverNo() {
		return approverNo;
	}
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
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
	
	// Constructors

	/** default constructor */
	public TransferApply() {
	}

	public TransferApply(Long id, Long oldaccountId, Long newaccountId,
			String flag, String appState, Date effectTime, Long approver,
			Date apptime, Long operId, Long placeId, Date operTime) {
		super();
		this.id = id;
		this.oldaccountId = oldaccountId;
		this.newaccountId = newaccountId;
		this.flag = flag;
		this.appState = appState;
		this.effectTime = effectTime;
		this.approver = approver;
		this.apptime = apptime;
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

	public Long getOldaccountId() {
		return oldaccountId;
	}

	public void setOldaccountId(Long oldaccountId) {
		this.oldaccountId = oldaccountId;
	}

	public Long getNewaccountId() {
		return newaccountId;
	}

	public void setNewaccountId(Long newaccountId) {
		this.newaccountId = newaccountId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Long getApprover() {
		return approver;
	}

	public void setApprover(Long approver) {
		this.approver = approver;
	}

	public Date getApptime() {
		return apptime;
	}

	public void setApptime(Date apptime) {
		this.apptime = apptime;
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