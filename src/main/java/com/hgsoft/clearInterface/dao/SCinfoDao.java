/*package com.hgsoft.clearInterface.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.clearInterface.entity.SCinfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class SCinfoDao extends BaseDao {
	
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	
	public void save(SCinfo scInfo,Long customerId) {
		
		//SubAccountInfo subAccountInfo = subAccountInfoDao.findByCustomerIdAndType(customerId, "1");
		//scInfo.setUserNo(subAccountInfo.getSubAccountNo());
		
		
		StringBuffer sql=new StringBuffer("insert into CSMS_SCinfo(");
		sql.append(FieldUtil.getFieldMap(SCinfo.class,scInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(SCinfo.class,scInfo).get("valueStr")+")");
		save(sql.toString());
	}
}
*/