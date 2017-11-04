package com.hgsoft.system.entity;

/**
 * 系统接口授权控制表的实体
 * @author gsf
 *
 */
public class InterfaceControl implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 309331496984290785L;
	
	private Long id;
	private String code;
	private Long state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	
	
	
}
