package com.hgsoft.jointCard.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.entity.CardHolderHis;

@Repository
public class CardHolderHisDao extends BaseDao {

	public void save(CardHolder cardHolder, CardHolderHis cardHolderHis){
		String sql="insert into CSMS_HK_CARDHOLDER_HIS("
				+ "ID,Name,IdType,IdCode,invoiceTitle,Remark,cardno,accountcID,hisSeqId,genTime,GenReason,OperID,operCode,operName,placeID,Placecode,placeName,linkMan,phoneNum,mobileNum,linkAddr)"
				+ " select " + cardHolderHis.getId() + ",Name,IdType,IdCode,invoiceTitle,"
				+ "'" + cardHolderHis.getRemark() + "',cardno,accountcID,'" + cardHolder.getHisSeqId() + "',sysdate,'" + cardHolderHis.getGenReason() + "',OperID,operCode,operName,placeID,Placecode,placeName,linkMan,phoneNum,mobileNum,linkAddr"
				+ " from CSMS_HK_Cardholder where id=" + cardHolder.getId();
		save(sql);
	}
	
}