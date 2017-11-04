package com.hgsoft.clearInterface.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.HandlePayToll;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;
@Repository
public class HandlePayTollDao extends ClearBaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	
	public void saveHandlePayToll(HandlePayToll handlePayToll){
		handlePayToll.setId(sequenceUtil.getSequenceLong("SEQ_TBPAYTOLLBYHANDSEND_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_PAYTOLLBYHAND_SEND(");
		sql.append(FieldUtil.getFieldMap(HandlePayToll.class, handlePayToll).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(HandlePayToll.class, handlePayToll).get("valueStr")
				+ ")");
		save(sql.toString());
	}

}
