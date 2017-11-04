package com.hgsoft.system.entity;

import java.io.Serializable;

public class SysModule  implements Serializable{

	/*
	 * "describe":"","display":"1","functions":"#;","id":245,"image":"kehu","level1"
	 * :
	 * 1,"name":"客户信息管理","parent":0,"priority":1,"remark":"","subuumstem":"11111111"
	 * ,"url":"#;"
	 */
		
	private Integer id;
	private Integer parent;
	private String name;
	private String url;
	private String functions;
	private Integer priority;
	private Integer display;
	private Integer level1;
	private String remark;
    private String image;
    private String describe;
    private String subuumstem;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Integer getLevel1() {
		return level1;
	}
	public void setLevel1(Integer level1) {
		this.level1 = level1;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getSubuumstem() {
		return subuumstem;
	}
	public void setSubuumstem(String subuumstem) {
		this.subuumstem = subuumstem;
	}
    
    
}
