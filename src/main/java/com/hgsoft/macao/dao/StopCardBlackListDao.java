package com.hgsoft.macao.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.StopCardBlackList;
@Repository
public class StopCardBlackListDao extends BaseDao{
	/**
	 * 查找最近时间黑名单流水记录
	 */
	public StopCardBlackList getLast(){
		String sql = "select "+FieldUtil.getFieldMap(StopCardBlackList.class,new StopCardBlackList()).get("nameStr")+" from CSMS_STOPCARDBLACKLIST where "
				+ " insertTime is not null and (GenCau='1' or GenCau='4') order by insertTime desc";
		List<Map<String,Object>> list = queryList(sql);
		
		StopCardBlackList stopCardBlackList = null;
		try {
			if(list.size()>0){
				stopCardBlackList = new StopCardBlackList();
				convert2Bean(list.get(0), stopCardBlackList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stopCardBlackList;
	}
	
	
	public int[] batchSaveStopCardBlackList(final List<StopCardBlackList> list){
		 String sql = "insert into CSMS_STOPCARDBLACKLIST(id,recordId,cardCode,genCau,genTime,insertTime) values(SEQ_CSMSSTOPCARDBLACKLIST_NO.nextval,?,?,?,?,?)";
	    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					StopCardBlackList stopCardBlackList = list.get(i);
					ps.setBigDecimal(1, stopCardBlackList.getRecordId());
					ps.setString(2, stopCardBlackList.getCardCode());
					ps.setString(3, stopCardBlackList.getGenCau());
					ps.setTimestamp(4, new java.sql.Timestamp(stopCardBlackList.getGenTime().getTime()));
					ps.setTimestamp(5, new java.sql.Timestamp(stopCardBlackList.getInsertTime().getTime()));
					
				}
				@Override
				public int getBatchSize() {
					 return list.size();
				}
			});
	}
}
