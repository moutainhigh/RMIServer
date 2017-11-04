package com.hgsoft.ygz.common;

/**
 * 用户卡黑名单类型
 * @author saint-yeb
 *
 */
public enum CardBlackTypeEmeu {

	CARDLOCK(1,"卡挂失"),NOCARD_HANGUP(2,"无卡挂起"),NOCARD_CANCLE(3,"无卡注销"),ACCOUNT_OVERDRAFT(4,"账户透支"),
	COOPERTATION_BLACK(5,"合作机构黑名单"),VEH_TYPE_INCONFORMITY(6,"车型不符");
	
	private Integer code;
	private String name;
	
	private CardBlackTypeEmeu(Integer code,String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
