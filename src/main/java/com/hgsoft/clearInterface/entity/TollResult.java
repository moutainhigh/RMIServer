package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 通行费接口查询结果
 * @author FDF
 */
public class TollResult implements Serializable{
	private int result;//0-成功 1-失败
	private String message;
	private TollCollection tollCollection;
	private List<TollDetail> tollDetails;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TollCollection getTollCollection() {
		return tollCollection;
	}
	public void setTollCollection(TollCollection tollCollection) {
		this.tollCollection = tollCollection;
	}
	public List<TollDetail> getTollDetails() {
		return tollDetails;
	}
	public void setTollDetails(List<TollDetail> tollDetails) {
		this.tollDetails = tollDetails;
	}
}
