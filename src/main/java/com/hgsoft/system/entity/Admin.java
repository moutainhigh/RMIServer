package com.hgsoft.system.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Admin entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Admin implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1790247174032277090L;
	/**
	 * 
	 */
	// Fields
	private Integer id;
	private Integer userid;
	private String username;
	private String name;
	private String type;
	private String password;
	private String sex;
	private String email;
	private String phone;
	private Date createTime;
	private String lastIp;
	private Date lastTime;
	private String valid;
	private String theme;
	private Set<Role> roles = new HashSet<Role>(0);
	
	public Admin(Integer id){
		this.id = id;
	}
	// Constructors

	/** default constructor */
	public Admin() {
	}

	public Admin(Integer userid, String username, String name, String type, String password,
			String sex, String email, String phone, Date createTime,
			String lastIp, Date lastTime, String valid, String theme,
			Set<Role> roles) {
		super();
		this.userid = userid;
		this.username = username;
		this.name = name;
		this.type = type;
		this.password = password;
		this.sex = sex;
		this.email = email;
		this.phone = phone;
		this.createTime = createTime;
		this.lastIp = lastIp;
		this.lastTime = lastTime;
		this.valid = valid;
		this.theme = theme;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}