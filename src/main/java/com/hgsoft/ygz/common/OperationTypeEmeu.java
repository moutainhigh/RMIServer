package com.hgsoft.ygz.common;

/**
 * 操作类型
 * @author saint-yeb
 *
 */
public enum OperationTypeEmeu {

	TRANSFER(0,"同步"),ADD(1,"新增"),UPDATE(2,"修改"),DEL(3,"删除"),
	EN_BLACK(1,"进入黑名单"),EX_BLACK(2,"解除黑名单"),
	BLACK_OBU_TYPE_LOSS(1,"标签挂失"),BLACK_OBU_TYPE_NOOBU_HANGUP(2,"无签挂起"),
	BLACK_OBU_TYPE_NOOBU_CANCLE(3,"无签注销"),BLACK_OBU_TYPE_VEHICLETYPE_NOTMATCH(4,"车型不符");
	
	private Integer code;
	private String name;
	
	private OperationTypeEmeu(Integer code,String name) {
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
