package com.hgsoft.bank.dao;

import com.hgsoft.bank.entity.AuthInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AuthInfoDao extends BaseDao {
	public List<Map<String, Object>> findByList(AuthInfo authInfo) {
		StringBuffer sql = new StringBuffer(
				"select id,operno,password,terminalno,state,logindate,loginip from CSMS_AUTH_INFO ");
		List<Map<String, Object>> list = queryList(sql.toString());
		return list;
	}

	public AuthInfo findById(Long id) {
		StringBuffer sql = new StringBuffer(
				"select id,operno,password,terminalno,state,logindate,loginip from CSMS_AUTH_INFO where id=?");
		List<Map<String, Object>> list = queryList(sql.toString(), id);
		AuthInfo authInfo = null;
		if (!list.isEmpty()) {
			authInfo = new AuthInfo();
			this.convert2Bean(list.get(0), authInfo);
		}

		return authInfo;
	}

	public AuthInfo find(AuthInfo authInfo) {
		AuthInfo temp = null;
		if (authInfo != null) {
			StringBuffer sql = new StringBuffer(
					"select id,operno,password,terminalno,state,logindate,loginip from CSMS_AUTH_INFO ");
			Map map = FieldUtil.getPreFieldMap(AuthInfo.class, authInfo);
			if (authInfo != null) {
				sql.append(map.get("selectNameStrNotNull"));
			}
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString(),
					((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new AuthInfo();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

	public void save(AuthInfo authInfo) {
		Map map = FieldUtil.getPreFieldMap(AuthInfo.class, authInfo);
		StringBuffer sql = new StringBuffer("insert into CSMS_AUTH_INFO");
		sql.append(map.get("insertNameStrNotNull"));
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"));
	}
	
	public void delete(AuthInfo authInfo){
		String sql = "delete from CSMS_AUTH_INFO where OperNo = ? and BankNo=? and TerminalNo=?";
		delete(sql,authInfo.getOperNo(),authInfo.getBankNo(),authInfo.getTerminalNo());
	}

	public Pager findByPage(Pager pager, AuthInfo authInfo) {
		StringBuffer sql = new StringBuffer(
				"select id,operno,password,terminalno,state,logindate,loginip from CSMS_AUTH_INFO where 1=1 ");
		List list = new ArrayList();
		if (authInfo != null) {
			Map map = FieldUtil.getPreFieldMap(AuthInfo.class, authInfo);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list = ((List) map.get("paramNotNull"));
		}
		sql.append(" order by  ID desc ");
		return this.findByPages(sql.toString(), pager, list.toArray());
	}

	public void update(AuthInfo authInfo) {
		if (authInfo != null) {
			StringBuffer sql = new StringBuffer("update CSMS_AUTH_INFO set ");
			String param = FieldUtil.getFieldMap(AuthInfo.class, authInfo).get(
					"nameAndValueNotNullToUpdate");
//			param = param.substring(1);
			if (StringUtil.isNotBlank(param)) {
				sql.append(param + " where id=" + authInfo.getId());
			}
			update(sql.toString());
		}
	}

	public void deleteById(Long id) {
		String sql = "delete from CSMS_AUTH_INFO where id=?";
		update(sql, id);
	}
	
	/*public Map<String,Object> getLicenseInfo(String vehiclePlate,String vehicleColor){
		String PerOrAccSQL = "select ci.prepaidcid,ci.AccountCID from CSMS_Vehicle_Info vi join CSMS_CarObuCard_info ci on vi.id=ci.VehicleID where vi.vehiclePlate=? and vi.vehicleColor=?";
		List<Map<String, Object>> PerOrAccList = this.queryList(PerOrAccSQL,vehiclePlate,vehicleColor);
		Map<String,Object> PerOrAccMap = null;
		String sql = "";
		if(PerOrAccList.size()>0){
			PerOrAccMap = PerOrAccList.get(0);
		}else{
			return null;
		}
		if(PerOrAccMap.get("PREPAIDCID")!=null){
			sql = "select vi.vehiclePlate,vi.vehicleColor,'' lockFlag,'' lockUnit,c.CardNo,vi.vehicleSpecificInformation from csms_vehicle_info vi join CSMS_CarObuCard_info ci on vi.id = ci.VehicleID join CSMS_PrePaidC c on ci.prepaidcid=c.id where vi.vehiclePlate=? and vi.vehicleColor=?";
		}else if(PerOrAccMap.get("ACCOUNTCID")!=null){
			sql = "select vi.vehiclePlate,vi.vehicleColor,'' lockFlag,'' lockUnit,ai.CardNo,vi.vehicleSpecificInformation from csms_vehicle_info vi join CSMS_CarObuCard_info ci on vi.id = ci.VehicleID join CSMS_AccountC_info ai on ci.AccountCID=ai.id where vi.vehiclePlate=? and vi.vehicleColor=? ";
		}else{
			return null;
		}
		List<Map<String, Object>> list = this.queryList(sql,vehiclePlate,vehicleColor);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}*/
	

}
