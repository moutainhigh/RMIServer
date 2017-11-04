package com.hgsoft.accountC.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AcctollcollectRecord;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;


@Repository
public class AcctollcollectRecordDao extends BaseDao{

	public void save(AcctollcollectRecord acctollcollectRecord) {
		Map map = FieldUtil.getPreFieldMap(AcctollcollectRecord.class,acctollcollectRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_AccTollCollect_RECORD");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
