package com.hgsoft.daysettle.entity;

public class CashDepositDaySet implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373924101946651490L;
	
	private Long id;
	
	private Long daySetID;
	
	private String settleDay;
	
	private Double publicAcc;
	
	private Double withholdAcc;
	
	private Double outstandingAmount;
	
	private Long operid;
	
	private String operCode;
	
	private String operName;
	
	private Long placeid;
	
	private String placeCode;
	
	private String placeName;
	//上一日未缴存金额
	private Double preDayOutstandingAmount;
	//短款修正金额
	private Double correctCash;
	//现金收款金额
	private Double toDayCash;

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

	public Double getPublicAcc() {
		return publicAcc;
	}

	public void setPublicAcc(Double publicAcc) {
		this.publicAcc = publicAcc;
	}

	public Double getWithholdAcc() {
		return withholdAcc;
	}

	public void setWithholdAcc(Double withholdAcc) {
		this.withholdAcc = withholdAcc;
	}

	public Double getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
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

	public Double getPreDayOutstandingAmount() {
		return preDayOutstandingAmount;
	}

	public void setPreDayOutstandingAmount(Double preDayOutstandingAmount) {
		this.preDayOutstandingAmount = preDayOutstandingAmount;
	}

	public Double getCorrectCash() {
		return correctCash;
	}

	public void setCorrectCash(Double correctCash) {
		this.correctCash = correctCash;
	}

	public Double getToDayCash() {
		return toDayCash;
	}

	public void setToDayCash(Double toDayCash) {
		this.toDayCash = toDayCash;
	}


	
}
