package com.hgsoft.system.serviceInterface;

import java.util.List;
import java.util.Map;

public interface IBusinessAccreditService {

	public List<Map<String,Object>> findBusinessAccreditByURL(String url);
	public List<Map<String,Object>> findBusinessAccreditByURL(String url, String subuumstemcode);
	public List<Map<String,Object>> findBusinessAccreditAdminByStaffNo(String staffNo,String state,String url, String subuumstemcode);
}
