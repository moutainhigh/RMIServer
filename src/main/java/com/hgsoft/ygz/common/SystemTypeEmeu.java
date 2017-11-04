package com.hgsoft.ygz.common;

/**
 * @author wangjinhao
 * @ClassName：SystemTypeEmeu
 * @Description：系统类型枚举
 * @date 2017/10/19 17:17:56
 */
public enum SystemTypeEmeu {

	CSMSCLIENT(0,"自营"),AMMSCLIENT(1,"代理"),MACAOCLIENT(2,"澳门通"),ACMSCLIENT(3,"香港联营"),
	IVRCLIENT(4,"呼叫中心");

	private SystemTypeEmeu(Integer code,String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;
	private String name;

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
