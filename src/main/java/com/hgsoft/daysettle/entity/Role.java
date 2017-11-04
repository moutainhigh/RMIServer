package com.hgsoft.daysettle.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Module entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Role  implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 145865595932927454L;
	// Fields    
    private Long id;
    private String name;
    private String remark;
    private String useState; //使用状态（1.正常 2.停用）
    private String subuumstem; //应用系统，uums_ subuumstem.code
    private Long propertyType; //角色类型 uums_ propertyType.id
    private Set modules = new HashSet(0);


    // Constructors

    /** default constructor */
    public Role() {
    }
    /** default constructor */
    public Role(Long id) {
    	this.id = id;
    }
    /** full constructor */
    
    public Role(Long id, String name, String remark, String useState,
			String subuumstem, Long propertyType, Set modules) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.useState = useState;
		this.subuumstem = subuumstem;
		this.propertyType = propertyType;
		this.modules = modules;
	}

    @Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", remark=" + remark + ", useState=" + useState + ", subuumstem="
				+ subuumstem + ", propertyType=" + propertyType + ", modules=" + modules + "]";
	}
	// Property accessors
    /**
     * 重写方法，如果id相等则说明相等
     */
    @Override
	public boolean equals(Object obj) {
    	if(obj==null) return false;
    	if(!(obj instanceof Role))
    		return false;
    	if(obj == this)
    		return true;
    	return this.id.toString().equals(((Role)obj).id.toString());
	}
    
	@Override
	public int hashCode() {
		return new Integer(id.intValue()).hashCode();
	}
	public String getName() {
        return this.name;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	public String getSubuumstem() {
		return subuumstem;
	}

	public void setSubuumstem(String subuumstem) {
		this.subuumstem = subuumstem;
	}

	public Long getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(Long propertyType) {
		this.propertyType = propertyType;
	}

	public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Set getModules() {
		return modules;
	}

	public void setModules(Set modules) {
		this.modules = modules;
	}
}