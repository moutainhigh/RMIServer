package com.hgsoft.system.entity;

import java.io.Serializable;

import com.hgsoft.customer.entity.Customer;


/**
 * Module entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Module implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9098255604264102217L;
	// Fields
	private Integer id;
	private Integer parent;
	private String name;
	private String url;
	private String functions;
	private Integer priority;
	private Integer display;
	private Integer level1;
	private String remark;
    private String icon;

    // Constructors

	/** default constructor */
	public Module() {
	}

	/** full constructor */
	public Module(Integer parent, String name, String url, String functions,
			Integer priority, Integer display, Integer level1, String subsystem, String remark, String icon) {
		this.parent = parent;
		this.name = name;
		this.url = url;
		this.functions = functions;
		this.priority = priority;
		this.display = display;
		this.level1 = level1;
		this.remark = remark;
        this.icon = icon;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFunctions() {
		return this.functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getDisplay() {
		return this.display;
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
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", parent=" + parent + ", name=" + name
				+ ", url=" + url + ", functions=" + functions + ", priority="
				+ priority + ", display=" + display + ", level1=" + level1
				+ ", remark=" + remark + ", icon=" + icon + "]";
	}

	
	
}