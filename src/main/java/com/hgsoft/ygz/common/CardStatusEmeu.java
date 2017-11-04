package com.hgsoft.ygz.common;

/**
 * 用户卡状态
 * @author saint-yeb
 *
 */
public enum CardStatusEmeu {

	NORMAL(1,"正常"),HADCARD_HANGUP(2,"有卡挂起"),NOCARD_HANGUP(3,"无卡挂起"),HADCARD_CANCLE(4,"有卡注销"),
	NOCARD_CANCLE(5,"无卡注销"),CARD_LOSS(6,"卡挂失");
	
	private Integer code;
	private String name;
	
	private CardStatusEmeu(Integer code,String name) {
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
