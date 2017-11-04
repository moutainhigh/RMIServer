package com.hgsoft.daysettle.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.daysettle.entity.RegisterDaySetDetail;
import com.hgsoft.daysettle.entity.RegisterDaySetRecord;

@Component
public class RegisterDaySetDetailDao extends BaseDao{

	public int[] savebatchDaySetDetail(final RegisterDaySetRecord registerDaySetRecord,final List<RegisterDaySetDetail> list) {  
        String sql = "insert into CSMS_RegisterDaySet_Detail(ID,MainID,FeeType,HandFee,Memo)"
        		+ " values(SEQ_CSMSReDaySetDetail_NO.nextval,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				RegisterDaySetDetail registerDaySetDetail = list.get(i);
				ps.setLong(1, registerDaySetRecord.getId());
				ps.setString(2, registerDaySetDetail.getFeeType());
				ps.setBigDecimal(3, registerDaySetDetail.getHandFee().multiply(new BigDecimal("100")));
				ps.setString(4, registerDaySetDetail.getMemo());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    } 
}
