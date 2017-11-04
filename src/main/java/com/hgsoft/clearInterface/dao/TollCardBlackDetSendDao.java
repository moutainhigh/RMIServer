/*package com.hgsoft.clearInterface.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.TollCardBlackDetSend;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class TollCardBlackDetSendDao extends BaseDao {
	public void save(TollCardBlackDetSend tollCardBlackDetSend) {
		StringBuffer sql=new StringBuffer("insert into csms_tollcardblackdet_send (");
		sql.append(FieldUtil.getFieldMap(TollCardBlackDetSend.class,tollCardBlackDetSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(TollCardBlackDetSend.class,tollCardBlackDetSend).get("valueStr")+")");
		save(sql.toString());
	}
}
*/