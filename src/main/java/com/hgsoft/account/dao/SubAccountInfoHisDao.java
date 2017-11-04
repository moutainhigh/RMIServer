package com.hgsoft.account.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
@Repository
public class SubAccountInfoHisDao extends BaseDao{
	public void saveSubAccountInfoHis(Long subAccountInfoHisId,String createReason,Long subAccountInfoId) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_SubAccount_InfoHis("
				+ "ID,MainID,subAccountNo,subAccountType,ApplyID,OperID,PlaceID,OperTime,agentsmay,HisSeqID,"
				+ "operno,opername,placeno,placename,SubServicePwd,BailBalance,BailFee,BailFrozenBalance,CreateDate,CreateReason )"
				+ "SELECT "+subAccountInfoHisId+",MainID,subAccountNo,subAccountType,ApplyID,OperID,PlaceID,OperTime,agentsmay,HisSeqID,"
				+ "operno,opername,placeno,placename,SubServicePwd,BailBalance,BailFee,BailFrozenBalance,sysdate,'"+createReason+"' "
				+ " FROM CSMS_SubAccount_Info WHERE ID="+subAccountInfoId+"");
		save(sql.toString());
	}
}
