package com.hgsoft.clearInterface.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.ManualRemoveBlackListSend;
import com.hgsoft.clearInterface.entity.RelieveStopPayDetail;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class ManualRemoveBlackListSendDao extends ClearBaseDao {
	
	public void save(ManualRemoveBlackListSend manualRemoveBlackListSend) {
		StringBuffer sql=new StringBuffer("insert into TB_MANUALREMOVEBLACKLIST_SEND(");
		sql.append(FieldUtil.getFieldMap(ManualRemoveBlackListSend.class,manualRemoveBlackListSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ManualRemoveBlackListSend.class,manualRemoveBlackListSend).get("valueStr")+")");
		save(sql.toString());
	}
	
	
	public int[] batchSend(final List<ManualRemoveBlackListSend> manualListSends) {
        String sql = "insert into TB_MANUALREMOVEBLACKLIST_SEND(id,bacCount,payAccount,payFlag,cardNo,handPayTime,"
        		+ "handPayFee,updateTime,etcMoney,lateFee,remark,boardListNo) "
        		+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ManualRemoveBlackListSend manualRemoveBlackListSend = manualListSends.get(i);
				if(manualRemoveBlackListSend.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, manualRemoveBlackListSend.getId());
				if(manualRemoveBlackListSend.getBaccount() ==null)ps.setNull(2, Types.LONGVARCHAR);else ps.setString(2, manualRemoveBlackListSend.getBaccount());
				if(manualRemoveBlackListSend.getPayAccount() ==null)ps.setNull(3, Types.LONGVARCHAR);else ps.setString(3, manualRemoveBlackListSend.getPayAccount());
				if(manualRemoveBlackListSend.getPayFlag() ==null)ps.setNull(4, Types.VARCHAR);else ps.setString(4, manualRemoveBlackListSend.getPayFlag());
				if(manualRemoveBlackListSend.getCardNo()==null)ps.setNull(5, Types.VARCHAR);else ps.setString(5, manualRemoveBlackListSend.getCardNo());
				if(manualRemoveBlackListSend.getHandpaytime()==null)ps.setNull(6, Types.DATE);else ps.setTimestamp(6, new java.sql.Timestamp(manualRemoveBlackListSend.getHandpaytime().getTime()));
				if(manualRemoveBlackListSend.getHandpayfee()==null)ps.setNull(7, Types.DECIMAL);else ps.setBigDecimal(7, manualRemoveBlackListSend.getHandpayfee());
				if(manualRemoveBlackListSend.getUpdateTime()==null)ps.setNull(8, Types.DATE);else ps.setTimestamp(8, new java.sql.Timestamp(manualRemoveBlackListSend.getUpdateTime().getTime()));
				if(manualRemoveBlackListSend.getEtcmoney()==null)ps.setNull(8, Types.DECIMAL);else ps.setBigDecimal(9, manualRemoveBlackListSend.getEtcmoney());
				if(manualRemoveBlackListSend.getLatefee()==null)ps.setNull(10, Types.DECIMAL);else ps.setBigDecimal(10, manualRemoveBlackListSend.getLatefee());
				if(manualRemoveBlackListSend.getRemark()==null)ps.setNull(11, Types.VARCHAR);else ps.setString(11, manualRemoveBlackListSend.getRemark());
				if(manualRemoveBlackListSend.getBoardListNo()==null)ps.setNull(12, Types.BIGINT);else ps.setLong(12, manualRemoveBlackListSend.getBoardListNo());
				
			}
			@Override
			public int getBatchSize() {
				 return manualListSends.size();
			}
		});
    }
}
