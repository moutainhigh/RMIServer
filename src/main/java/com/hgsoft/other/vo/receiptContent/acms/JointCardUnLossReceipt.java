package com.hgsoft.other.vo.receiptContent.acms;

import com.hgsoft.acms.other.vo.receiptContent.BaseReceiptContentACMS;

public class JointCardUnLossReceipt extends BaseReceiptContentACMS {

	private String oldReceiptNo;
	private String cardNo;
	private String name;
	private String linkTel;
	private String mobileNum;
	private String linkMan;
	private String linkAddr;
	
	public String getOldReceiptNo() {
		return oldReceiptNo;
	}
	public void setOldReceiptNo(String oldReceiptNo) {
		this.oldReceiptNo = oldReceiptNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
