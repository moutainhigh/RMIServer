package com.hgsoft.other.vo.receiptContent.acms;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 联营卡终止使用回执
 * @author huanghaoyu
 *
 */
public class JointCardCancelReceipt extends BaseReceiptContent {

	private String cardNo;
	private String stopType;
	private String name;
	private String linkTel;
	private String mobileNum;
	private String linkMan;
	private String linkAddr;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getStopType() {
		return stopType;
	}
	public void setStopType(String stopType) {
		this.stopType = stopType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkAddr() {
		return linkAddr;
	}
	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}
	
}
