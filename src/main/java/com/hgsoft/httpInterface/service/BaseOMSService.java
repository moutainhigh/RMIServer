package com.hgsoft.httpInterface.service;

import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.PropertiesUtil;
import net.sf.json.JSONObject;

import java.io.IOException;

public abstract class BaseOMSService {

	public BaseOMSService() throws IOException{
		JSONObject json = new JSONObject();
		json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
		json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
		json.accumulate("timer", System.currentTimeMillis());
		this.token = DesEncrypt.getInstance().encrypt(json.toString());
		
	}
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
