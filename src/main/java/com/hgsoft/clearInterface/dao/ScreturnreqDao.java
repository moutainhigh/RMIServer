package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.Screturnreq;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;
@Component
public class ScreturnreqDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	
	public void save(Screturnreq screturnreq) {
		StringBuffer sql=new StringBuffer("insert into CSMS_Screturnreq(");
		sql.append(FieldUtil.getFieldMap(Screturnreq.class,screturnreq).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Screturnreq.class,screturnreq).get("valueStr")+")");
		save(sql.toString());
	}
	public void saveScreturnreq(String oldCardNo,String newCardNo){
		Screturnreq screturnreq=new Screturnreq();
		screturnreq.setId(sequenceUtil.getSequenceLong("SEQ_CSMSscreturnreq_NO"));
		screturnreq.setNewCardNo(newCardNo);
		screturnreq.setOldCardNo(oldCardNo);
		screturnreq.setOptime(new Date());
		save(screturnreq);
	}
}
