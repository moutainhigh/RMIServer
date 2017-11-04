package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 通行费明细
 * @author FDF
 */
public class TollDetail implements Serializable{
	private String detailNo;//车道序列号
	private String enNetNo;//入口路网号
	private Integer enRoadId;//入口路段编码
	private Integer enStationId;//入口收费站编码
	private String enStationName;//入口收费站名
	private Date enTime;//入口日期时间
	private String exNetNo;//出口路网号
	private Integer exRoadId;//出口路段编码
	private Integer exStationId;//出口收费站编码
	private String exStationName;//出口收费站名
	private Date exTime;//出口日期时间
	private Long etcMoney;//收费金额
	private Long disToll;//优惠金额
	private Long money;//后台收费金额
	
	public String getDetailNo() {
		return detailNo;
	}
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}
	public String getEnNetNo() {
		return enNetNo;
	}
	public void setEnNetNo(String enNetNo) {
		this.enNetNo = enNetNo;
	}
	public Integer getEnRoadId() {
		return enRoadId;
	}
	public void setEnRoadId(Integer enRoadId) {
		this.enRoadId = enRoadId;
	}
	public Integer getEnStationId() {
		return enStationId;
	}
	public void setEnStationId(Integer enStationId) {
		this.enStationId = enStationId;
	}
	public String getEnStationName() {
		return enStationName;
	}
	public void setEnStationName(String enStationName) {
		this.enStationName = enStationName;
	}
	public Date getEnTime() {
		return enTime;
	}
	public void setEnTime(Date enTime) {
		this.enTime = enTime;
	}
	public String getExNetNo() {
		return exNetNo;
	}
	public void setExNetNo(String exNetNo) {
		this.exNetNo = exNetNo;
	}
	public Integer getExRoadId() {
		return exRoadId;
	}
	public void setExRoadId(Integer exRoadId) {
		this.exRoadId = exRoadId;
	}
	public Integer getExStationId() {
		return exStationId;
	}
	public void setExStationId(Integer exStationId) {
		this.exStationId = exStationId;
	}
	public String getExStationName() {
		return exStationName;
	}
	public void setExStationName(String exStationName) {
		this.exStationName = exStationName;
	}
	public Date getExTime() {
		return exTime;
	}
	public void setExTime(Date exTime) {
		this.exTime = exTime;
	}
	public Long getEtcMoney() {
		return etcMoney;
	}
	public void setEtcMoney(Long etcMoney) {
		this.etcMoney = etcMoney;
	}
	public Long getDisToll() {
		return disToll;
	}
	public void setDisToll(Long disToll) {
		this.disToll = disToll;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
}
