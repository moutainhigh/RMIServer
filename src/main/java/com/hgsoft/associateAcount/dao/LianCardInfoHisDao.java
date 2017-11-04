package com.hgsoft.associateAcount.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.LianCardInfoHis;
import com.hgsoft.common.dao.BaseDao;

@Repository
public class LianCardInfoHisDao extends BaseDao {

	
	public void save(LianCardInfo lianCardInfo,LianCardInfoHis lianCardInfoHis){
		String sql="insert into CSMS_LIAN_CARD_INFOHIS("
				+ " ID,CustomerID,cardNo,nFlag,emFlag,VehicleType,name,idCode,idType,sex,begTime,endTime,preKeep,opNo,setTime,placeNo,memo,HisSeqID,CreateDate,CreateReason)"
				+ " select "+lianCardInfoHis.getId()+",CustomerID,cardNo,nFlag,emFlag,VehicleType,name,idCode,idType,sex,begTime,endTime,preKeep,opNo,setTime,placeNo,"
				+ "'"+lianCardInfoHis.getMemo()+"',HisSeqID,sysdate,'"+lianCardInfoHis.getCreateReason()+"' "
				+ " from CSMS_LIAN_CARD_INFO where id="+lianCardInfo.getId();
		save(sql);
	}
}
