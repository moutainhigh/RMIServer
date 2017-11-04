package com.hgsoft.clearInterface.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.UserStateInfoSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
@Component
public class UserStateInfoSendDao extends ClearBaseDao {
	
	public void save(UserStateInfoSend userStateInfoSend) {
		StringBuffer sql=new StringBuffer("insert into tb_userstateinfo_send(");
		sql.append(FieldUtil.getFieldMap(UserStateInfoSend.class,userStateInfoSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(UserStateInfoSend.class,userStateInfoSend).get("valueStr")+")");
		save(sql.toString());
	}
}
