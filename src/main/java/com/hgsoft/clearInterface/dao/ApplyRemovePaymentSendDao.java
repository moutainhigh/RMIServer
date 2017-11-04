package com.hgsoft.clearInterface.dao;

import org.springframework.stereotype.Component;
import com.hgsoft.clearInterface.entity.ApplyRemovePaymentSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class ApplyRemovePaymentSendDao extends ClearBaseDao {
	
	public void save(ApplyRemovePaymentSend applyRemovePaymentSend) {
		StringBuffer sql=new StringBuffer("insert into tb_applyremovepayment_send(");
		sql.append(FieldUtil.getFieldMap(ApplyRemovePaymentSend.class,applyRemovePaymentSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ApplyRemovePaymentSend.class,applyRemovePaymentSend).get("valueStr")+")");
		save(sql.toString());
	}
}
