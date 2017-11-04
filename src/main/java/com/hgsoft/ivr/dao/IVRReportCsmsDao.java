package com.hgsoft.ivr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class IVRReportCsmsDao extends BaseDao{
	
	public List<Map<Object, String>> findCardNoList(String bankno, String userNo,
			String organ) {
		/*
		StringBuffer sql = new StringBuffer("select ai.cardno "
		  +" from CSMS_AccountC_apply aa "
		  +" left join CSMS_SubAccount_Info si on aa.id = si.applyid "
		  +" left join CSMS_AccountC_info ai on ai.accountid = si.id "
		  +" where 1 = 1  "
		  +" union "
		  +" select ai.cardno "
		  +" from csms_customer c "
		  +" left join CSMS_AccountC_info ai on c.id = ai.customerid "
		  +" where 1 = 1  "
		  +" union "
		  +" select p.cardno "
		  +" from csms_customer c "
		  +" left join csms_prepaidc p on c.id = p.customerid "
		  +" where 1 = 1 ");
		  */
		StringBuffer sql1 = new StringBuffer("select ai.cardno "
				  +" from CSMS_AccountC_apply aa "
				  +" left join CSMS_SubAccount_Info si on aa.id = si.applyid "
				  +" left join CSMS_AccountC_info ai on ai.accountid = si.id "
				  +" where 1 = 1  ");
		StringBuffer sql2 = new StringBuffer(" select ai.cardno "
				  +" from csms_customer c "
				  +" left join CSMS_AccountC_info ai on c.id = ai.customerid "
				  +" where 1 = 1  ");
		StringBuffer sql3 = new StringBuffer(" select p.cardno "
				  +" from csms_customer c "
				  +" left join csms_prepaidc p on c.id = p.customerid "
				  +" where 1 = 1 ");
		
		SqlParamer params1=new SqlParamer();
		if(StringUtil.isNotBlank(bankno)){
			params1.eq("aa.bankaccount", bankno);
		} 
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("c.userno", userNo);
		} 
		if(StringUtil.isNotBlank(organ)){
			params.eq("c.organ", organ);
		} 
		sql1=sql1.append(params1.getParam());
		sql2=sql2.append(params.getParam());
		sql3=sql3.append(params.getParam());
		StringBuffer sql = new StringBuffer(sql1+" union "+sql2+" union " +sql3);
		//sql=sql.append(params.getParam());
		List<Map<Object, String>> list=jdbcUtil.selectForList(sql.toString());
 		return list;
	}

}
