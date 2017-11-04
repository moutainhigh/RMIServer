package com.hgsoft.timerTask.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.prepaidC.entity.ReturnFee;
@Component
public class ReturnFeeInsertDao extends BaseDao{
	
	public int[] batchSaveReturnFee(final List<ReturnFee> list) {
        String sql = "insert into CSMS_ReturnFee(id,cardno,feetype,returnfee,state,returntime) values(SEQ_CSMS_RETURNFEE_NO.nextval,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ReturnFee returnFee = list.get(i);
				ps.setString(1, returnFee.getCardNo());
				ps.setString(2, returnFee.getFeeType());
				ps.setBigDecimal(3, returnFee.getReturnFee());
				ps.setString(4, returnFee.getState());
				ps.setDate(5, new java.sql.Date(returnFee.getReturnTime().getTime()));
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
		
}
