package com.hgsoft.agentCard.entity;

import java.io.Serializable;
import java.util.Date;

public class CardNoSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3967619663332253098L;
	//序列ID
	private Long id;
	//卡类型
	private String type;
	//银行编码
	private String obaNo;
	//起始号
	private String startCardNo;
	//结束号
	private String endCardNo;
	//创建时间
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObaNo() {
		return obaNo;
	}
	public void setObaNo(String obaNo) {
		this.obaNo = obaNo;
	}
	public String getStartCardNo() {
		return startCardNo;
	}
	public void setStartCardNo(String startCardNo) {
		this.startCardNo = startCardNo;
	}
	public String getEndCardNo() {
		return endCardNo;
	}
	public void setEndCardNo(String endCardNo) {
		this.endCardNo = endCardNo;
	}
	
	

}
