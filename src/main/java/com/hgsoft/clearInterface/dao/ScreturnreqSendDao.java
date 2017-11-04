package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.ScreturnreqSend;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;

@Component
public class ScreturnreqSendDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	
	public void save(ScreturnreqSend screturnreqSend) {
		StringBuffer sql=new StringBuffer("insert into CSMS_Screturnreq_send(");
		sql.append(FieldUtil.getFieldMap(ScreturnreqSend.class,screturnreqSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScreturnreqSend.class,screturnreqSend).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void saveScreturnreqSend(String oldCardNo,String newCardNo){
		ScreturnreqSend screturnreqSend=new ScreturnreqSend();
		screturnreqSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSscreturnreqSend_NO"));
		screturnreqSend.setNewCardNo(newCardNo);
		screturnreqSend.setOldCardNo(oldCardNo);
		screturnreqSend.setOptime(new Date());
		save(screturnreqSend);
	}
}
