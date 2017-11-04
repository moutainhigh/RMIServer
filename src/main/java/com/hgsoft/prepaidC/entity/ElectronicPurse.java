package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ElectronicPurse entity. @author MyEclipse Persistence Tools
 */

public class ElectronicPurse implements java.io.Serializable {

	private static final long serialVersionUID = -5868919583541883689L;
	private Long id;
	private BigDecimal balance;
	private Date duration;
	private Date accountdate;
	private BigDecimal credit;
	private BigDecimal lastcredit;
	private Long prepaidc;
	private Long operid;
	private Long placeid;
	private Long hisseqid;
	
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
	public ElectronicPurse() {
	}

	/** minimal constructor */
	public ElectronicPurse(BigDecimal balance) {
		this.balance = balance;
	}

	/** full constructor */
	public ElectronicPurse(BigDecimal balance, Date duration,
			Date accountdate, BigDecimal credit, BigDecimal lastcredit,
			Long prepaidc, Long operid, Long placeid, Long hisseqid) {
		this.balance = balance;
		this.duration = duration;
		this.accountdate = accountdate;
		this.credit = credit;
		this.lastcredit = lastcredit;
		this.prepaidc = prepaidc;
		this.operid = operid;
		this.placeid = placeid;
		this.hisseqid = hisseqid;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getDuration() {
		return this.duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

	public Date getAccountdate() {
		return this.accountdate;
	}

	public void setAccountdate(Date accountdate) {
		this.accountdate = accountdate;
	}

	public BigDecimal getCredit() {
		return this.credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getLastcredit() {
		return this.lastcredit;
	}

	public void setLastcredit(BigDecimal lastcredit) {
		this.lastcredit = lastcredit;
	}

	public Long getPrepaidc() {
		return this.prepaidc;
	}

	public void setPrepaidc(Long prepaidc) {
		this.prepaidc = prepaidc;
	}

	public Long getOperid() {
		return this.operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Long getPlaceid() {
		return this.placeid;
	}

	public void setPlaceid(Long placeid) {
		this.placeid = placeid;
	}

	public Long getHisseqid() {
		return this.hisseqid;
	}

	public void setHisseqid(Long hisseqid) {
		this.hisseqid = hisseqid;
	}

}