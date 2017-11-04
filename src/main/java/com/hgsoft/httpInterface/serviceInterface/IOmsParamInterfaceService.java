package com.hgsoft.httpInterface.serviceInterface;

import java.util.Date;
import java.util.Map;

public interface IOmsParamInterfaceService {
	public void add(Long omsId,String omsType,Long paramId,String paramValue,String type,String state,Long operId,String operNo,String operName,Date operTime,String memo);
	public void delete(Long omsId,String omsType);
	public void update(Long omsId,String omsType,Long paramId,String paramValue,String type,String state,Long operId,String operNo,String operName,Date operTime,String memo);
	public Map<String,Object> findOmsParam(String key);
}