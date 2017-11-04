package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.PreDaySetDetail;
import com.hgsoft.daysettle.entity.PreDaySetRecord;
import com.hgsoft.obu.entity.OBUActDetail;

@Component
public class PreDaySetDetailDao extends BaseDao{

	
	
	public int[] batchSaveDaySetDetail(final List<PreDaySetDetail> list,PreDaySetRecord preDaySetRecord) {  
        String sql = "insert into CSMS_Pre_DaySet_Detail(ID,MainID,PreSettleDay,FeeType,SystemFee,HandFee,LSadjustFee,DifferenceFee)"
        		+ " values(SEQ_CSMSPreDaySetDetail_NO.nextval,"+preDaySetRecord.getId()+","+preDaySetRecord.getPreSettleDay()+",?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				PreDaySetDetail preDaySetDetail = list.get(i);
				ps.setString(1, preDaySetDetail.getFeeType());
				ps.setBigDecimal(2, preDaySetDetail.getSystemFee());
				ps.setBigDecimal(3, preDaySetDetail.getHandFee());
				ps.setBigDecimal(4, preDaySetDetail.getLsadjustFee());
				ps.setBigDecimal(5, preDaySetDetail.getDifferenceFee());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }  
	
	public List<PreDaySetDetail> findList(PreDaySetDetail preDaySetDetail) {
		PreDaySetDetail temp = null;
		List<PreDaySetDetail> detailList = new ArrayList<PreDaySetDetail>();
		if (preDaySetDetail != null) {
			StringBuffer sql = new StringBuffer("select ID,MainID,PreSettleDay,FeeType,"
					+ "SystemFee,HandFee,LSadjustFee,DifferenceFee from Csms_Pre_Dayset_Detail where 1=1 ");
			
			sql.append(FieldUtil.getFieldMap(PreDaySetDetail.class,preDaySetDetail).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());
			for (Map<String, Object> map : list) {
				temp = new PreDaySetDetail();
				this.convert2Bean(map, temp);
				detailList.add(temp);
			}

		}
		return detailList;
	}
}
