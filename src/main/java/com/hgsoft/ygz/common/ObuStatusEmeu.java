package com.hgsoft.ygz.common;

/**
 * OBU_STATE EMEU
 * @author luningyun
 *
 */
public enum ObuStatusEmeu {

	NORMAL(1,"正常"),HADOBU_HANGUP(2,"有签挂起"),NOOBU_HANGUP(3,"无签挂起"),HADOBU_CANCLE(4,"有签注销"),
	NOOBU_CANCLE(5,"无签注销"),OBU_LOSS(6,"签挂失"),HAD_TRANSFER_ACCOUNT(7,"已过户"),MAINTAIN(8,"维修中"),
	BLACK_OBU_STATE_IN(1,"进入黑名单"),BLACK_OBU_STATE_OUT(2,"解除黑名单");
	
	private Integer code;
	private String name;
	
	private ObuStatusEmeu(Integer code,String name) {
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
