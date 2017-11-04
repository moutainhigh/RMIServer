package com.hgsoft.system.dao;

import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BusinessAccreditDao extends BaseDao{
	
	public List<Map<String, Object>> findBusinessAccreditByURL(String url){
		String sql="select * from csms_businessaccredit bt where url=? and bt.usestate='2'";
		List<Map<String, Object>> list = queryList(sql, url);
		return list;
	}
	
	public List<Map<String, Object>> findBusinessAccreditByURL(String url, String subuumstemcode){
		String sql="select * from csms_businessaccredit bt where url=? and bt.usestate='2' and bt.subuumstemcode = ?";
		List<Map<String, Object>> list = queryList(sql, url, subuumstemcode);
		return list;
	}

	public List<Map<String, Object>> BusinessAccreditAdminByName(String staffno,String state,String url, String subuumstemcode) {
		String sql="select * from csms_businessaccredit_admin ba join (select b.ID, b.LOGINNAME, b.USERNAME, b.PASSWORD, b.SEX, b.STAFFNO, b.EMAIL, b.PHONE, b.USERSTATE, b.USESTATE, b.IPLIMIT, b.LOGINTIME, b.LOGINIP, b.LASTLOGINTIME, b.LASTLOGINIP, b.REMARK, b.COMPANY, b.DEPARTMENT, b.DENAME, b.CREATEID, b.UPDATEID, b.CREATETIME, b.UPDATETIME, b.CUSTOMPOINT, b.POINTNAME, null as ADMINGROUP, b.CUSTOMPOINTTYPE, b.POINTCODE, a.subuumstem as subuumstemCode from uums_admin_subuumstem a  join  uums_admin  b on  a.admin = b.id ) a on ba.admin=a.id join CSMS_BUSINESSACCREDIT b on BA.BUSINESSACCREDIT=b.id where a.staffno=? and b.businessstate=? and b.url=? and b.subuumstemcode = ?";
		List<Map<String, Object>> list = queryList(sql, staffno, state, url, subuumstemcode);
		return list;
	}
}
