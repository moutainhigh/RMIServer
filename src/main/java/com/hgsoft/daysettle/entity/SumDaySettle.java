package com.hgsoft.daysettle.entity;

import java.io.Serializable;
/**
 * 
 * @author gaosiling
 * 2016年11月25日 15:22:33
 */
public class SumDaySettle implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4599308224528220336L;

	//系统现金总额
	private String cash="0";
	//系统POS总额
	private String pos="0";
	//系统转账缴款总额
	private String tranfer="0";
	//系统支付宝缴款总额
	private String apliays="0";
	//系统微信缴款总额
	private String wechat="0";
	
	private String aPaid = "0";
	//昨日未缴存银行现金总额与当日系统现金总额和值
	private String bPaid = "0";
	//现金长短款
	private String cashAllAmt="0";
	//POS长短款
	private String posAllAmt="0";
	//转账长短款
	private String tranferAllAmt="0";
	//支付宝长短款
	private String apliaysAllAmt="0";
	//微信长短款
	private String wechatAllAmt="0";
	//已缴存对公账户长短款-无用
	private String aPaidPos="0";
	//未缴存对公账户长短款-无用
	private String bPaidPos="0";
	//网点已缴存银行对公帐户现金总额
	private String placeToComparePaid="0";
	//网点已缴存待扣账户现金总额
	private String placeToTranferPaid="0";
	//未缴存银行现金总额
	private String placeNoChargeBankFee="0";
	//网点现金收款总额
	private String placeCashAmt="0";
	//网点POS收款总额
	private String placePosAmt="0";
	//网点转账收款总额
	private String placeTranferAmt="0";
	//网点微信收款总额
	private String placeWeChat="0";
	//网点支付宝收款总额
	private String placeAppliy="0";
	//业务总额
	private String serviceAllAmt="0";
		
	public String getServiceAllAmt() {
		return serviceAllAmt;
	}

	public void setServiceAllAmt(String serviceAllAmt) {
		this.serviceAllAmt = serviceAllAmt;
	}

	public String getPlaceToComparePaid() {
		return placeToComparePaid;
	}

	public void setPlaceToComparePaid(String placeToComparePaid) {
		this.placeToComparePaid = placeToComparePaid;
	}

	public String getPlaceToTranferPaid() {
		return placeToTranferPaid;
	}

	public void setPlaceToTranferPaid(String placeToTranferPaid) {
		this.placeToTranferPaid = placeToTranferPaid;
	}

	public String getPlaceNoChargeBankFee() {
		return placeNoChargeBankFee;
	}

	public void setPlaceNoChargeBankFee(String placeNoChargeBankFee) {
		this.placeNoChargeBankFee = placeNoChargeBankFee;
	}

	public String getPlaceWeChat() {
		return placeWeChat;
	}

	public void setPlaceWeChat(String placeWeChat) {
		this.placeWeChat = placeWeChat;
	}

	public String getPlaceAppliy() {
		return placeAppliy;
	}

	public void setPlaceAppliy(String placeAppliy) {
		this.placeAppliy = placeAppliy;
	}

	public String getPlaceCashAmt() {
		return placeCashAmt;
	}

	public void setPlaceCashAmt(String placeCashAmt) {
		this.placeCashAmt = placeCashAmt;
	}

	public String getPlacePosAmt() {
		return placePosAmt;
	}

	public void setPlacePosAmt(String placePosAmt) {
		this.placePosAmt = placePosAmt;
	}

	public String getPlaceTranferAmt() {
		return placeTranferAmt;
	}

	public void setPlaceTranferAmt(String placeTranferAmt) {
		this.placeTranferAmt = placeTranferAmt;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getTranfer() {
		return tranfer;
	}

	public void setTranfer(String tranfer) {
		this.tranfer = tranfer;
	}

	public String getCashAllAmt() {
		return cashAllAmt;
	}

	public void setCashAllAmt(String cashAllAmt) {
		this.cashAllAmt = cashAllAmt;
	}

	public String getPosAllAmt() {
		return posAllAmt;
	}

	public void setPosAllAmt(String posAllAmt) {
		this.posAllAmt = posAllAmt;
	}

	public String getTranferAllAmt() {
		return tranferAllAmt;
	}

	public void setTranferAllAmt(String tranferAllAmt) {
		this.tranferAllAmt = tranferAllAmt;
	}

	public String getaPaidPos() {
		return aPaidPos;
	}

	public void setaPaidPos(String aPaidPos) {
		this.aPaidPos = aPaidPos;
	}

	public String getbPaidPos() {
		return bPaidPos;
	}

	public void setbPaidPos(String bPaidPos) {
		this.bPaidPos = bPaidPos;
	}

	public String getSum(){
		return String.valueOf(Integer.parseInt(cashAllAmt)+
		Integer.parseInt(posAllAmt)+
		Integer.parseInt(tranferAllAmt)+
		Integer.parseInt(apliaysAllAmt)+
		Integer.parseInt(wechatAllAmt)+
		Integer.parseInt(aPaidPos)+
		Integer.parseInt(bPaidPos));
	}
	
	public String getApliays() {
		return apliays;
	}

	public void setApliays(String apliays) {
		this.apliays = apliays;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getApliaysAllAmt() {
		return apliaysAllAmt;
	}

	public void setApliaysAllAmt(String apliaysAllAmt) {
		this.apliaysAllAmt = apliaysAllAmt;
	}

	public String getWechatAllAmt() {
		return wechatAllAmt;
	}

	public void setWechatAllAmt(String wechatAllAmt) {
		this.wechatAllAmt = wechatAllAmt;
	}

	public String getaPaid() {
		return aPaid;
	}

	public void setaPaid(String aPaid) {
		this.aPaid = aPaid;
	}

	public String getbPaid() {
		return bPaid;
	}

	public void setbPaid(String bPaid) {
		this.bPaid = bPaid;
	}

	@Override
	public String toString() {
		return "SumDaySettle [" +
					"系统现金收款总额cash=" + cash
				+ ", 系统POS收款总额pos=" + pos
				+ ", 系统转账收款总额tranfer=" + tranfer
				+ ", 系统支付宝收款总额apliays=" + apliays
				+ ", 系统微信收款总额wechat=" + wechat
				+ ", 系统昨日未缴存银行现金总额与当日系统现金总额和值bPaid=" + bPaid
				+ ", 长短款现金cashAllAmt=" + cashAllAmt
				+ ", 长短款POSposAllAmt=" + posAllAmt
				+ ", 长短款转账tranferAllAmt=" + tranferAllAmt
				+ ", 长短款支付宝apliaysAllAmt=" + apliaysAllAmt
				+ ", 长短款微信wechatAllAmt=" + wechatAllAmt
				+ ", 长短款已缴存（无用）aPaidPos=" + aPaidPos
				+ ", 长短款未缴存（无用）bPaidPos=" + bPaidPos
				+ ", 网点已缴存银行对公帐户现金总额placeToComparePaid=" + placeToComparePaid
				+ ", 网点已缴存待扣账户现金总额placeToTranferPaid=" + placeToTranferPaid
				+ ", 网点未缴存银行现金总额placeNoChargeBankFee=" + placeNoChargeBankFee
				+ ", 网点现金收款总额placeCashAmt=" + placeCashAmt
				+ ", 网点POS收款总额pplacePosAmt=" + placePosAmt
				+ ", 网点转账收款总额pplaceTranferAmt=" + placeTranferAmt
				+ ", 网点微信收款总额placeWeChat=" + placeWeChat
				+ ", 网点支付宝收款总额pplaceAppliy=" + placeAppliy
				+ ", 业务总额serviceAllAmt="+serviceAllAmt+"]";
	}

	
	
}
