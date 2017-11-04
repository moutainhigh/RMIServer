package com.hgsoft.associateAcount.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.associateAcount.entity.ReqInfoHis;
import com.hgsoft.common.dao.BaseDao;

@Repository
public class ReqInfoHisDao extends BaseDao{

	public BigDecimal findCountByCardNo(String cardNo){
		String sql = "select count(1) as count from CSMS_REQ_INFOHIS where accode="+cardNo +" and useno is not null ";
		List<Map<String, Object>> list = queryList(sql);
		BigDecimal count = null;
		if (!list.isEmpty()&&list.size()==1) {
			count = (BigDecimal) list.get(0).get("count");
		}
		return count;
	}
	
	public void save(ReqInfo reqInfo,ReqInfoHis reqInfoHis){
		String sql="insert into CSMS_REQ_INFOHIS("
				+ " ID,CustomerID,AcCode,OperId ,ReqTime,SetTime,PlaceId,CancelTime ,FileName ,UseNo ,SerNo,Memo,HisSeqID ,CreateDate ,CreateReason)"
				+ " select "+reqInfoHis.getId()+",CustomerID,AcCode,OperId,"
				+ "ReqTime,SetTime,PlaceId,CancelTime ,FileName ,UseNo,SerNo,'"+reqInfoHis.getMemo()+"',HisSeqID,sysdate,'"+reqInfoHis.getCreateReason()+"' "
				+ "from CSMS_REQ_INFO where id="+reqInfo.getId();
		save(sql);
	}
}
