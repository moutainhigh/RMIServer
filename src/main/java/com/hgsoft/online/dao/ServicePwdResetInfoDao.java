package com.hgsoft.online.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.online.entity.ServicePwdResetInfo;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ServicePwdResetInfoDao extends BaseDao {
	
	public void save(ServicePwdResetInfo servicePwdResetInfo) {
		StringBuffer sql=new StringBuffer("insert into CSMS_SERVICEPWDRESETINFO(");
		sql.append(FieldUtil.getFieldMap(ServicePwdResetInfo.class,servicePwdResetInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ServicePwdResetInfo.class,servicePwdResetInfo).get("valueStr")+")");
		save(sql.toString());
	}

	public ServicePwdResetInfo findByUserNoTelNumCheckCode(String userNo, String telNum, String checkCode){
		ServicePwdResetInfo temp = null;
		StringBuffer sql = new StringBuffer(
				"select * from CSMS_SERVICEPWDRESETINFO WHERE 1 = 1 and confirmFlag = '0' and SYSDATE BETWEEN applytime and effectivetime "
		);
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("userNo", userNo);
		}if(StringUtil.isNotBlank(telNum)){
			params.eq("telNum", telNum);
		}
		if(StringUtil.isNotBlank(checkCode)){
			params.eq("checkCode", checkCode);
		}
		sql.append(params.getParam());
		sql.append(" order BY id desc");

		List list = params.getList();
		Object[] Objects= list.toArray();

		List<Map<String, Object>> listMap =  queryList(sql.toString(),Objects);
		if (!listMap.isEmpty() && listMap.size() >= 1) {
			temp = new ServicePwdResetInfo();
			this.convert2Bean(listMap.get(0), temp);
		}
		return temp;
	}

	public void updateConfirmFlag(Long id){
		String sql = "update CSMS_SERVICEPWDRESETINFO set confirmFlag='1' where id=?";
		saveOrUpdate(sql, id);
	}


}
