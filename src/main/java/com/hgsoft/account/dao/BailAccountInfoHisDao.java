package com.hgsoft.account.dao;

import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.entity.BailAccountInfoHis;
import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Component;

@Component("bailAccountInfoHisDao")
public class BailAccountInfoHisDao extends BaseDao {

	
	public void saveHis(BailAccountInfoHis bailAccountInfoHis,BailAccountInfo bailAccountInfo) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_BailAccount_InfoHis("
				+ "ID,MainID,BailFee,BailFrozenBalance,OperID,PlaceID,OperTime,HisSeqID,CreateDate,CreateReason )"
				+ "SELECT "+bailAccountInfoHis.getId()+",MainID,BailFee,BailFrozenBalance,OperID,PlaceID,OperTime,HisSeqID,sysdate,"
				+ "'"+bailAccountInfoHis.getCreateReason()+"' FROM CSMS_BailAccount_Info WHERE ID="+bailAccountInfo.getId()+"");
		save(sql.toString());
	}
}
