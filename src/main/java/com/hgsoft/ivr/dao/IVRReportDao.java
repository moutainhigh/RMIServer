package com.hgsoft.ivr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.OmsDao;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Repository
public class IVRReportDao extends OmsDao {

	public List findAllCustomPointType() {
		 
		StringBuffer sql = new StringBuffer("select t.*  from OMS_CUSTOMPOINTTYPE t where 1=1 ");
		return queryList(sql.toString());
	}

	public List findAllCustomPointArea() {
		StringBuffer sql = new StringBuffer("select t.*  from oms_Area t where 1=1");
		return queryList(sql.toString());
	}

	public List findAllOperator() {
		String sql="select * from OMS_CLIENTADMIN where 1 = 1 order by id ";
		List<Object> list=omsJdbcUtil.selectForList(sql);
		return list;
	}

	public List findRoadDiscountList() {
		String sql = "select DISTINCT t.roadno,r.roadname from OMS_sectionOneDiscount t left join oms_road r ON t.roadno = r.roadno where t.checkflag = 2";
		List<Object> list=omsJdbcUtil.selectForList(sql);
		return list;
	}

	public List findPlaceNameList(String area,String placeType) {
	 
		StringBuffer sql = new StringBuffer("select cp.* from OMS_customPoint cp where  1=1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(area)){
			sqlp.eq("cp.area", area);
		}
		if(StringUtil.isNotBlank(placeType)){
			sqlp.eq("cp.customPointType", placeType);
		}
		sql=sql.append(sqlp.getParam());
		return queryList(sql.toString(),sqlp.getList().toArray());
	}

	public List<Map<String, Object>> findCustomerList(String organ,
			String idCode, String idType) {
		StringBuffer sql = new StringBuffer("select "
				   +" c.userno userno, "
				   +" c.organ organ, "
				   +" (case when c.idtype ='0' then '军官证' "
				   +" when c.idtype ='1' then '身份证' "
				   +" when c.idtype ='2' then '营业执照' "
				   +" when c.idtype ='3' then '其他' "
				   +" when c.idtype ='4' then '临时身份证' "
				   +" when c.idtype ='5' then '入境证' "
				   +" when c.idtype ='6' then '驾驶证' "
				   +" when c.idtype ='7' then '组织机构代码证' "
				   +" when c.idtype ='8' then '护照' "
				   +" when c.idtype ='9' then '信用代码证'  "
				   +" when c.idtype ='10' then '港澳居民来往内地通行证'  "
				   +" when c.idtype ='11' then '台湾居民来往大陆通行证' "
				   +" when c.idtype ='12' then '武警警官证件' end ) idtype, "
				   +" c.idcode idcode, "
				   +" c.secondno secondno, "
				   +" c.secondname secondname "
				  +" from csms_customer c "
				  +" where 1 = 1 ");
				SqlParamer sqlp=new SqlParamer();
				if(StringUtil.isNotBlank(organ) && StringUtil.isNotBlank(idCode) && StringUtil.isNotBlank(idType)){
					sqlp.eq("c.organ", organ);
					sqlp.eq("c.idcode", idCode);
					sqlp.eq("c.idtype", idType);
				}
				sql=sql.append(sqlp.getParam());
				return queryList(sql.toString(),sqlp.getList().toArray());
	}

	public String findBankNoBycardNo(String cardNo) {
		StringBuffer sql = new StringBuffer("select aa.bankaccount  bankaccount from"
			+" csms_accountc_info ai" 
			+" join csms_subaccount_info si"
			+" on si.id = ai.accountid"
			+" join csms_accountc_apply aa"
			+" on si.applyid = aa.id"
			+" where 1 = 1");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("ai.cardno", cardNo);
		}
		sql=sql.append(sqlp.getParam());
		List<Map<String, Object>> bankNoList = queryList(sql.toString(),sqlp.getList().toArray());
		return  bankNoList.size() > 0?bankNoList.get(0).get("bankaccount").toString():null;
	}

	public String findOrganByBankNo(String bankNo) {
		StringBuffer sql = new StringBuffer("select c.organ organ from CSMS_AccountC_apply aa"
				+" join csms_customer c"
				+" on aa.customerid = c.id"
				+" where  1 = 1");
			SqlParamer sqlp=new SqlParamer();
			if(StringUtil.isNotBlank(bankNo)){
				sqlp.eq("aa.bankAccount", bankNo);
			}
			sql=sql.append(sqlp.getParam());
			List<Map<String, Object>> organList = queryList(sql.toString(),sqlp.getList().toArray());
			return  organList.size() > 0?organList.get(0).get("organ").toString():null;
	}

}
