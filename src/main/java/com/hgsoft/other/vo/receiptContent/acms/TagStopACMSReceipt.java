package com.hgsoft.other.vo.receiptContent.acms;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签挂起回执
 * Created by wiki on 2017/8/22.
 */
public class TagStopACMSReceipt extends BaseReceiptContent {
    /**
     * 标 签 号
     */
    private String tagNo;

    /**
     * 客户名称
     */
 	private String name;
// 	/**
// 	 * 证件类型
// 	 */
// 	private String idType;
// 	/**
// 	 * 证件号码
// 	 */
// 	private String idCode;
 	/**
 	 * 联系电话
 	 */
 	private String linkTel;
 	/**
 	 * 手机号
 	 */
 	private String mobileNum;
 	/**
 	 * 联系人
 	 */
 	private String linkMan;
 	/**
 	 * 联系地址
 	 */
 	private String linkAddr;
	public String getTagNo() {
		return tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
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
