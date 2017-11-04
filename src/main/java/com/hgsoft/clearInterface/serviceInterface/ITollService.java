package com.hgsoft.clearInterface.serviceInterface;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.AcReq;
import com.hgsoft.clearInterface.entity.TollResult;
//@WebService(targetNamespace="http://ws.hgsoft.com/")
public interface ITollService{
	public TollResult searchTollResult(AcReq acReq);
	
}
