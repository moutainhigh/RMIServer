package com.hgsoft.daysettle.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.daysettle.entity.AfterDaySetFee;
import com.hgsoft.daysettle.entity.DaySetDetail;
import com.hgsoft.daysettle.entity.DaySetRecord;

@Component
public class AfterDaySetFeeDao extends BaseDao{
	public int[] batchSaveAfterDaySetFee(final List<DaySetDetail> daySetDetailList,DaySetRecord daySetRecord){
		  String sql = "insert into CSMS_AfterDaySetFee(ID,DaySetID,FeeType,DifferenceFee,OperID,OperTime,OperPlaceID)"
	        		+ " values(SEQ_CSMSAfterDaySetFee_NO.nextval,?,?,?,"+daySetRecord.getOperID()+",sysdate,"+daySetRecord.getOperPlaceID()+")";
		  return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
					DaySetDetail daySetDetail = daySetDetailList.get(i);
					ps.setLong(1,daySetDetail.getMainID());
					ps.setString(2, daySetDetail.getFeeType());
					ps.setBigDecimal(3,daySetDetail.getDifferenceFee());
				}
				
				@Override
				public int getBatchSize() {
					 return daySetDetailList.size();
				}
			});
		
		
	}
	
	public void save(AfterDaySetFee afterDaySetFee) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_AfterDaySetFee(");
		sql.append(FieldUtil.getFieldMap(AfterDaySetFee.class,afterDaySetFee).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AfterDaySetFee.class,afterDaySetFee).get("valueStr")+")");
		save(sql.toString());*/
		afterDaySetFee.setHisSeqID(-afterDaySetFee.getId());
		Map map = FieldUtil.getPreFieldMap(AfterDaySetFee.class,afterDaySetFee);
		StringBuffer sql=new StringBuffer("insert into CSMS_AfterDaySetFee");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
