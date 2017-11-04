package com.hgsoft.system.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.system.entity.CusPointTreeEnd;

public interface ICusPointTreeEndService {
	public List<Map<String, Object>> findCusPointTreeEndList();
	public Map<String, Object> findCusPointTreeEndList(String id);
	public Map<String, String> saveCusPointTreeEndList(List<CusPointTreeEnd> cusPointTreeEnds);
	public List<Map<String, Object>> findCusPointTreeByPointType(String type,String area);
	public List<Map<String, Object>> findCusPointAreaByPointCode(String str);
	public List<Map<String, Object>> findCusPointTreeByIdOne();
	public List<Map<String, Object>> pointTypeChangeByIdOne(String type,String area);
}
