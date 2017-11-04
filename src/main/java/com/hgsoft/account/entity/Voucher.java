package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 缴款单凭证
 * @author gsf
 * @date 2017年6月19日
 */
public class Voucher implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015230972810589246L;
	
	
	private Long id;
	private String voucherNo;//凭证编号
	private String vouType;//凭证类型
	private Date getTime;//收款时间
	private BigDecimal getFee;//收款金额
	private BigDecimal balance;//余额
	private String payType;//收款方式
	private String payAccount;//支付账号
	private String payMan;//缴款人
	private String organ;//客户名称
	private String idCode;//身份证号码
	private String getOpno;//收款人
	private String getPlaceNo;//收款网点
	private String memo;//备注
	private BigDecimal frozenBalance;//冻结金额
	
	private Date operTime;
	private Long operId;
	private String operNo;
	private String operName;
	private Long placeId;
	private String placeNo;
	private String placeName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getVouType() {
		return vouType;
	}
	public void setVouType(String vouType) {
		this.vouType = vouType;
	}
	public Date getGetTime() {
		return getTime;
	}
	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
	public BigDecimal getGetFee() {
		return getFee;
	}
	public void setGetFee(BigDecimal getFee) {
		this.getFee = getFee;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	public String getPayMan() {
		return payMan;
	}
	public void setPayMan(String payMan) {
		this.payMan = payMan;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getGetOpno() {
		return getOpno;
	}
	public void setGetOpno(String getOpno) {
		this.getOpno = getOpno;
	}
	public String getGetPlaceNo() {
		return getPlaceNo;
	}
	public void setGetPlaceNo(String getPlaceNo) {
		this.getPlaceNo = getPlaceNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public BigDecimal getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(BigDecimal frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
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
	
	
	
	
}
