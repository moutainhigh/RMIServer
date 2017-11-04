package com.hgsoft.clearInterface.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.HandleRelieveBlackList;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class HandleRelieveBlackListDao extends ClearBaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	
	public void saveHandleRelieveBlackList(HandleRelieveBlackList handleRelieveBlackList){
		handleRelieveBlackList.setId(sequenceUtil.getSequenceLong("SEQ_TBMANUALREMOVEBLACKLIST_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_MANUALREMOVEBLACKLIST_SEND(");
		sql.append(FieldUtil.getFieldMap(HandleRelieveBlackList.class, handleRelieveBlackList).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(HandleRelieveBlackList.class, handleRelieveBlackList).get("valueStr")
				+ ")");
		save(sql.toString());
	}

}
