package com.hgsoft.clearInterface.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.ScreturnSend;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.dao.ReturnFeeDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.utils.SequenceUtil;

@Component
public class ScreturnSendDao extends BaseDao  {
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	ReturnFeeDao returnFeeDao;
	public void save(ScreturnSend screturnSend) {
		if(screturnSend.getId()==null){
			screturnSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSscreturnsend_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_screturn_send(");
		sql.append(FieldUtil.getFieldMap(ScreturnSend.class,screturnSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ScreturnSend.class,screturnSend).get("valueStr")+")");
		save(sql.toString());
	}
   public void save(ScreturnSend screturnSend,PrepaidCBussiness prepaidCBussiness,String feeType,String state) {
	   List<ReturnFee> m=returnFeeDao.findByBussinessID(prepaidCBussiness.getId());
	   ReturnFee returnFee=null;
	   for(int i=0;i<m.size();i++){
		   returnFee=m.get(i);
		   if(returnFee.getFeeType().equals(feeType)){
		   screturnSend=new ScreturnSend();
		   screturnSend.setReturnFee(returnFee.getReturnFee());
		   screturnSend.setCardNo(prepaidCBussiness.getCardno());
		   screturnSend.setBussinessID(prepaidCBussiness.getId());
		   screturnSend.setReturnTime(returnFee.getReturnTime());
		   screturnSend.setFeeType(feeType);
		   screturnSend.setState(state);
		   screturnSend.setPlaceID(prepaidCBussiness.getPlaceid());
		   save(screturnSend); 
		   }
	   }
   }

}
