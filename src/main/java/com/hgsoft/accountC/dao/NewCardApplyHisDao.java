package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.accountC.entity.NewCardApply;
import com.hgsoft.accountC.entity.NewCardApplyHis;
import com.hgsoft.common.dao.BaseDao;
@Repository
public class NewCardApplyHisDao extends BaseDao{
	public void saveHis(NewCardApplyHis newCardApplyHis,NewCardApply newCardApply) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_NewCard_apply_his( ID,genTime,genReason,"
				+ "ApplyID,reqCount,AppState,Approver,approverNo,approverName,OperID,PlaceID,operNo,operName,placeNo,placeName,HisSeqID,bail,truckbail,appTime,operTime ) "
				+ "select "+newCardApplyHis.getId()+",sysdate,"+newCardApplyHis.getGenReason()
				+ ",ApplyID,reqCount,AppState,Approver,approverNo,approverName,OperID,PlaceID,operNo,operName,placeNo,placeName,HisSeqID,bail,truckbail,appTime,operTime"
				+ " from CSMS_NewCard_apply where id="+newCardApply.getId()+"");
		save(sql.toString());
	}
	public NewCardApplyHis findByHisId(Long hisId){
		String sql ="";
		if(hisId!=null){
			sql = "select * "
					+ "from CSMS_NewCard_apply_his where HisSeqID="+hisId;
		}else{
			sql = "select * "
					+ "from CSMS_NewCard_apply_his where HisSeqID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		NewCardApplyHis newCardApplyHis = null;
		if (!list.isEmpty()) {
			newCardApplyHis = new NewCardApplyHis();
			this.convert2Bean(list.get(0), newCardApplyHis);
		}

		return newCardApplyHis;
	}
	
	public NewCardApplyHis findById(Long id){
		String sql ="select * from CSMS_NewCard_apply_his where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		NewCardApplyHis newCardApplyHis = null;
		if (!list.isEmpty()) {
			newCardApplyHis = new NewCardApplyHis();
			this.convert2Bean(list.get(0), newCardApplyHis);
		}

		return newCardApplyHis;
	}
}
