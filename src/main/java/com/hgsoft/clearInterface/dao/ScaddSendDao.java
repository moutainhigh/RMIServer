package com.hgsoft.clearInterface.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;

@Component
public class ScaddSendDao extends ClearBaseDao  {
	
	@Resource
	SequenceUtil sequenceUtil;
	public void save(ScaddSend scaddSend) {
		if(scaddSend.getId()==null){
			scaddSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSscaddsend_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_scadd_send(");
		sql.append(FieldUtil.getFieldMap(ScaddSend.class,scaddSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScaddSend.class,scaddSend).get("valueStr")+")");
		save(sql.toString());
	}
	
	/*public void save(ScaddSend scaddSend,PrepaidCBussiness prepaidCBussiness,String state){
		scaddSend.setCardNo(prepaidCBussiness.getCardno());
		scaddSend.setBeforeAddBalance(prepaidCBussiness.getBeforebalance());
		scaddSend.setMoney(prepaidCBussiness.getRealprice());
		scaddSend.setReturnMoney(prepaidCBussiness.getReturnMoney());
		scaddSend.setTransferSum(prepaidCBussiness.getTransferSum());
		scaddSend.setBussinessID(prepaidCBussiness.getId());
		scaddSend.setPlaceID(prepaidCBussiness.getPlaceNo());
		scaddSend.setTermCode(prepaidCBussiness.getTermcode());
		scaddSend.setOfflineTradeNo(prepaidCBussiness.getOfflinetradeno());
		scaddSend.setOnlineTradeNo(prepaidCBussiness.getOnlinetradeno());
		scaddSend.setCheckCode(prepaidCBussiness.getCheckcode());
		scaddSend.setMac(prepaidCBussiness.getMac());
		scaddSend.setTac(prepaidCBussiness.getTac());
		scaddSend.setState(state);
		save(scaddSend);
		
	}*/
}
