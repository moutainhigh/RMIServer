package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountNCApply;
import com.hgsoft.accountC.entity.AccountNCApplyHis;
import com.hgsoft.common.dao.BaseDao;

@Repository
public class AccountNCApplyHisDao extends BaseDao{

	public void saveHis(AccountNCApplyHis accountNCApplyHis,AccountNCApply accountNCApply) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_AccountNC_apply_his( ID,genTime,genReason,AccountID,OldAccName,NewAccName,Remark,AppState,Approver,AppTime,OperID,PlaceID,operNo,operName,placeNo,placeName,OperTime,HisSeqID,ApproverNo,ApproverName )  "
				+ "SELECT "+accountNCApplyHis.getId()+",sysdate,"+accountNCApplyHis.getGenReason()
				+",AccountID,OldAccName,NewAccName,Remark,AppState,Approver,AppTime,OperID,PlaceID,operNo,operName,placeNo,placeName,OperTime,HisSeqID,ApproverNo,ApproverName"
				+ " FROM CSMS_AccountNC_apply WHERE ID="+accountNCApply.getId()+"");
		save(sql.toString());
	}
	
	public AccountNCApplyHis findByHisId(Long hisId) {
		String sql = "select * from CSMS_AccountNC_apply_his where hisSeqId=?";
		List<Map<String, Object>> list = queryList(sql,hisId);
		AccountNCApplyHis accountNCApply = null;
		if (!list.isEmpty()) {
			accountNCApply = new AccountNCApplyHis();
			this.convert2Bean(list.get(0), accountNCApply);
		}
		return accountNCApply;
	}
}
