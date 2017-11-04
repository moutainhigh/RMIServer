package com.hgsoft.clearInterface.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.AccCardTransferSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
@Component
public class AccCardTransferResultRecvDao extends ClearBaseDao {
	
	//2017年10月21日10:15:45 修改查询的表  hzw
	public List<Map<String,Object>> findByResult(Long result){
		String sql = "select * from CSMS_ACCCARDTRANSFERRESULT where result=? order by REQTIME desc ";
		return queryList(sql,result);
	}
	
	public void save(AccCardTransferSend accCardTransferSend) {
		StringBuffer sql=new StringBuffer("insert into TB_ACCCARDTRANSFERRESULT_RECV(");
		sql.append(FieldUtil.getFieldMap(AccCardTransferSend.class,accCardTransferSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccCardTransferSend.class,accCardTransferSend).get("valueStr")+")");
		save(sql.toString());
	}
}
