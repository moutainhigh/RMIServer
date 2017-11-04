package com.hgsoft.daysettle.entity;

public class WareReportRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2648120672449614629L;

	private Long id;
	
	private Long daySetID;
	
	private String settleDay;
	
	private String productCode;
	
	private String productName;
	
	private Integer wareCount;
	
	private Long operid;
	
	private String operCode;
	
	private String operName;
	
	private Long placeid;
	
	private String placeCode;
	
	private String placeName;
	
	private String productState;
	
	private String productSource;
	
	//系统计算数量
	private Integer sysCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDaySetID() {
		return daySetID;
	}

	public void setDaySetID(Long daySetID) {
		this.daySetID = daySetID;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getWareCount() {
		return wareCount;
	}

	public void setWareCount(Integer wareCount) {
		this.wareCount = wareCount;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Long getPlaceid() {
		return placeid;
	}

	public void setPlaceid(Long placeid) {
		this.placeid = placeid;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	public String getProductSource() {
		return productSource;
	}

	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}

	public Integer getSysCount() {
		return sysCount;
	}

	public void setSysCount(Integer sysCount) {
		this.sysCount = sysCount;
	}
	
	
}
