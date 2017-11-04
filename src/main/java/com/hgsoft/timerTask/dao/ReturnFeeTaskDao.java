package com.hgsoft.timerTask.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.prepaidC.entity.ReturnFee;
@Component
public class ReturnFeeTaskDao extends ClearBaseDao{
	@Resource
	ReturnFeeInsertDao feeInsert;
	
	public void saveReturnFee() { 
	   String sql = "select * from tb_screturn_send where 1=1";
	   List<Map<String, Object>> list = this.queryList(sql);
	  
	   List<ReturnFee> returnFeeList = null;
		if (!list.isEmpty()) {
			returnFeeList = new ArrayList<ReturnFee>();
			for (Map<String,Object> map : list) {
				ReturnFee returnFee = new ReturnFee();
				this.convert2Bean(map, returnFee);
				returnFee.setCardNo((String)map.get("CARDCODE"));
				returnFeeList.add(returnFee);
			}
			feeInsert.batchSaveReturnFee(returnFeeList);
		}

	}
	
	public void deleteReturnFeeFromClear() { 
		   String sql = "delete from tb_screturn_send";
		   delete(sql);
	}
		
}
