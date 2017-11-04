package com.hgsoft.app.dao;

import com.hgsoft.app.entity.VehplateAuthInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.RegularUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * App接口Dao
 * @author sxw
 * 2017-07-05
 */
@Repository
public class AppDataDao extends BaseDao{


    /**
	 * 查询车牌认证信息
	 * @param vehiclePlate
	 * @param vehicleColor
	 * @return
     */
	public Map<String, Object> findVehAuthInfo(String vehiclePlate,String vehicleColor){
		StringBuffer sql = new StringBuffer(
				"select * from CSMS_VEHPLATEAUTHINFO where 1=1 "
				);
		
		SqlParamer params=new SqlParamer();
		

		if(StringUtil.isNotBlank(vehiclePlate)){
			params.eq("VEHICLEPLATE", vehiclePlate);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			params.eq("VEHICLECOLOR", vehicleColor);
		}
		
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		List<Map<String, Object>> vehAuthInfoList = queryList(sql.toString(),Objects);
		
		if(!vehAuthInfoList.isEmpty()&&vehAuthInfoList.size()>0){
			return vehAuthInfoList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 保存车牌认证信息
	 * @param vehplateAuthInfo
     */
	public void save(VehplateAuthInfo vehplateAuthInfo) {
		Map map = FieldUtil.getPreFieldMap(VehplateAuthInfo.class, vehplateAuthInfo);
		StringBuffer sql = new StringBuffer("insert into CSMS_VEHPLATEAUTHINFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	/***
	 * 储值卡信息查询（用于验证网商贷证件类型）
	 * @param cardNo
	 * @return
     */
	public Map<String, Object> queryPrepaidCardInfo(String cardNo) {
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> mapList = null;
		if(StringUtil.isNotBlank(cardNo)){
			sql.append("select p.cardNo \"cardNo\",c.organ \"organ\",c.idType \"idType\",c.idCode \"idCode\" from csms_customer c INNER JOIN CSMS_PREPAIDC p" +
					" on p.CUSTOMERID = c.id where p.cardno = ?");
		}
		mapList = queryList(sql.toString(), cardNo);
		if(!mapList.isEmpty()){
			return mapList.get(0);
		}else{
			return null;
		}
	}

	public List<Map<String,Object>> queryCardNoByMobile(String mobile) {
		StringBuffer sql = new StringBuffer(
				"SELECT nvl(p.CARDNO, a.CARDNO) \"cardNo\" FROM csms_customer c INNER JOIN CSMS_PREPAIDC p ON c.id = p.customerid INNER JOIN CSMS_ACCOUNTC_INFO a ON c.id = a.customerid WHERE 1=1 "
		);

		SqlParamer params=new SqlParamer();

		if(StringUtil.isNotBlank(mobile)){
			params.eq("c.MOBILE", mobile);
		}

		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();

		List<Map<String, Object>> cardInfoList = queryList(sql.toString(),Objects);

		if(!cardInfoList.isEmpty()&&cardInfoList.size()>0){
			return cardInfoList;
		}else{
			return null;
		}
	}


	//sql未实现
	public Map<String,Object> queryOutsideCarInfo(String vehiclePlate, String vehicleColor) {
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> mapList = null;
		if(StringUtil.isNotBlank(vehiclePlate)&&StringUtil.isNotBlank(vehicleColor)){
			sql.append("select p.cardNo \"cardNo\",c.organ \"organ\",c.idType \"idType\",c.idCode \"idCode\" from csms_customer c INNER JOIN CSMS_PREPAIDC p" +
					" on p.CUSTOMERID = c.id where p.cardno = ?");
		}
		mapList = queryList(sql.toString(), vehiclePlate,vehicleColor);
		if(!mapList.isEmpty()){
			return mapList.get(0);
		}else{
			return null;
		}
	}

	public Map<String,Object> queryVehplateBindingInfo(String vehiclePlate, String vehicleColor) {
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> mapList = null;
		if(StringUtil.isNotBlank(vehiclePlate)&&StringUtil.isNotBlank(vehicleColor)){
			sql.append("SELECT nvl(P.cardNo, a.cardno) \"cardNo\", tag.tagno \"obuNo\" FROM csms_vehicle_info v " +
					"JOIN csms_carobucard_info coc ON coc.vehicleid = v.ID LEFT JOIN Csms_tag_info tag ON coc.tagid = tag.id " +
					"LEFT JOIN csms_prepaidc P ON P.ID = coc.prepaidcid LEFT JOIN CSMS_ACCOUNTC_INFO a ON a.ID = coc.accountcid " +
					"WHERE v.VEHICLEPLATE = ? AND v.VEHICLECOLOR = ?");
		}
		mapList = queryList(sql.toString(), vehiclePlate,vehicleColor);
		if(!mapList.isEmpty()){
			return mapList.get(0);
		}else{
			return null;
		}
	}

	public Map<String,Object> queryCardBindingInfo(String cardNo) {
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> mapList = null;
		if(StringUtil.isNotBlank(cardNo)){
			if(RegularUtil.isPrePaidCard(cardNo)){
				sql.append("SELECT c.mobile \"mobile\", tag.tagno \"obuNo\", v.VEHICLEPLATE \"vehiclePlate\" ," +
						" v.VEHICLECOLOR \"vehicleColor\", c.shortTel \"shortTel\", v.VEHICLEENGINENO \"vehicleEngineNo\"," +
						" v.IDENTIFICATIONCODE \"identificationCode\", b.CARDBALANCE \"balance\" FROM csms_prepaidc p " +
						"LEFT JOIN CSMS_CUSTOMER c ON c.id = p.CUSTOMERID " +
						"LEFT JOIN csms_carobucard_info coc ON coc.prepaidcid = p.ID " +
						"LEFT JOIN csms_vehicle_info v ON coc.vehicleid = v.ID " +
						"LEFT JOIN Csms_tag_info tag ON coc.tagid = tag.id " +
						"LEFT JOIN CSMS_CARDBALANCEDATA b ON p.cardno = b.cardcode AND b.BALANCETIME = " +
						"( SELECT MAX(BALANCETIME) FROM CSMS_CARDBALANCEDATA WHERE cardno = ? ) WHERE p.cardno = ?");
				mapList = queryList(sql.toString(), cardNo,cardNo);
			}else{
				sql.append("SELECT c.mobile \"mobile\", tag.tagno \"obuNo\", v.VEHICLEPLATE \"vehiclePlate\", " +
						"v.VEHICLECOLOR \"vehicleColor\", c.shortTel \"shortTel\", v.VEHICLEENGINENO \"vehicleEngineNo\", " +
						"v.IDENTIFICATIONCODE \"identificationCode\", NULL \"balance\" FROM CSMS_ACCOUNTC_INFO p " +
						"LEFT JOIN CSMS_CUSTOMER c ON c.id = p.CUSTOMERID " +
						"LEFT JOIN csms_carobucard_info coc ON coc.ACCOUNTCID = p.ID " +
						"LEFT JOIN csms_vehicle_info v ON coc.vehicleid = v.ID " +
						"LEFT JOIN Csms_tag_info tag ON coc.tagid = tag.id WHERE p.cardno = ?");
				mapList = queryList(sql.toString(), cardNo);
			}

		}
		if(!mapList.isEmpty()){
			return mapList.get(0);
		}else{
			return null;
		}
	}
}
