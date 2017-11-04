package com.hgsoft.ygz.common;

/**
 * 业务类型
 * @author saint-yeb
 *
 */
public enum BusinessTypeEmeu {
	
	ISSUERUPLOAD("ISSUERUPLOAD","发行方信息映射"),AGENCYUPLOAD("AGENCYUPLOAD","客服合作机构信息映射"),
	HALLUPLOAD("HALLUPLOAD","服务网点信息映射"),MOBILESERVICEUPLOAD("MOBILESERVICEUPLOAD","流动服务网点信息映射"),
	TERMINALUPLOAD ("TERMINALUPLOAD","自助服务终端信息映射"),ONLINEUNLOAD("ONLINEUNLOAD","线上服务渠道信息映射"),
	USERUPLOAD("CUSTOMERUPLOAD","客户信息映射"),VEHICLEUPLOAD("VEHICLEUPLOAD","客户车辆信息映射 "),
	CARDUPLOAD("CARDUPLOAD","记账卡信息映射"),PRECARDUPLOAD("CARDUPLOAD","储值卡信息映射"),
	OBUUPLOAD("OBUUPLOAD","OBU信息映射"),CARDBLACKLISTUPLOAD("CARDBLACKLISTUPLOAD","用户卡黑名单"),
	OBUBLACKLISTUPLOAD("OBUBLACKLISTUPLOAD","OBU黑名单"),RECHARGEUPLOAD("RECHARGEUPLOAD","充值交易"),
	REVERSALUPLOAD("REVERSALUPLOAD","冲正交易映射"),REIMBUSREUPLOAD("REIMBUSREUPLOAD","退款交易映射"),
	BALANCEUPLOAD("BALANCEUPLOAD","卡账余额映射");
	
	private String code;
	private String name;
	
	private BusinessTypeEmeu(String code,String name) {
		this.code = code;
		this.name=name;
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
