package com.hgsoft.prepaidC.serviceInterface;

import java.util.Map;

import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;

public interface IPrepaidHalfPayService {
	public Pager findHalfPayByPage(Pager pager,PrepaidCBussiness prepaidCBussiness,Integer type);
	public Map<String,Object> saveHalfTrue(PrepaidCBussiness prepaidCBussiness);
	public Map<String,Object> saveHalfFalse(PrepaidCBussiness prepaidCBussiness);
	public Map<String,Object> saveHalfTrueIm(PrepaidCBussiness prepaidCBussiness);
	public Map<String,Object> saveHalfFalseIm(PrepaidCBussiness prepaidCBussiness);
	public int findHalf(String cardno);
	public int findNewHalf(PrepaidCBussiness prepaidCBussiness);
	public int findImHalf(String cardno);
}
