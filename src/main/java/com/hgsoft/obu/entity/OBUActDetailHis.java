package com.hgsoft.obu.entity;

public class OBUActDetailHis implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6403172801025390189L;
	private Long id;
	private Long mainID;
	private String vehiclePlate;
	private String tagNo;
	private String importState;
	private String memo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainID() {
		return mainID;
	}
	public void setMainID(Long mainID) {
		this.mainID = mainID;
	}
	public String getVehiclePlate() {
		return vehiclePlate;
	}
	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public String getImportState() {
		return importState;
	}
	public void setImportState(String importState) {
		this.importState = importState;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
