package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.DaySetDetail;
import com.hgsoft.daysettle.entity.DaySetRecord;

@Component
public class DaySetDetailDao extends BaseDao{

	public int[] batchSaveDaySetDetail(final List<DaySetDetail> list,final DaySetRecord daySetRecord) {  
        String sql = "insert into CSMS_DaySet_Detail(ID,MainID,settleDay,FeeType,SystemFee,HandFee,LSadjustFee,DifferenceFee,DifferenceFlag)"
        		+ " values(SEQ_CSMSDaySetDetail_NO.nextval,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				DaySetDetail daySetDetail = list.get(i);
				ps.setLong(1, daySetRecord.getId());
				ps.setString(2, daySetRecord.getSettleDay());
				ps.setString(3, daySetDetail.getFeeType());
				ps.setBigDecimal(4, daySetDetail.getSystemFee());
				ps.setBigDecimal(5, daySetDetail.getHandFee());
				ps.setBigDecimal(6, daySetDetail.getLsadjustFee());
				ps.setBigDecimal(7, daySetDetail.getDifferenceFee());
				ps.setString(8, daySetDetail.getDifferenceFlag());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }  
	
	public List<DaySetDetail> findList(DaySetDetail daySetDetail) {
		DaySetDetail temp = null;
		List<DaySetDetail> detailList = new ArrayList<DaySetDetail>();
		if (daySetDetail != null) {
			StringBuffer sql = new StringBuffer("select ID,MainID,settleDay,FeeType,"
					+ "SystemFee,HandFee,LSadjustFee,DifferenceFee,DifferenceFlag from CSMS_DaySet_Detail where 1=1 and FeeType!=1 and FeeType != 2 and FeeType !=3  and FeeType !=4 ");
			
			/*sql.append(FieldUtil.getFieldMap(DaySetDetail.class,daySetDetail).get("nameAndValueNotNull"));
			sql.append(" order by id");*/
			
			
			
			
			Map mapParam = FieldUtil.getPreFieldMap(DaySetDetail.class,daySetDetail);
			sql.append(mapParam.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) mapParam.get("paramNotNull")).toArray());
			for (Map<String, Object> map : list) {
				temp = new DaySetDetail();
				this.convert2Bean(map, temp);
				detailList.add(temp);
			}
		}
		return detailList;
	}
	
	public List<DaySetDetail> findDetailList(DaySetDetail daySetDetail) {
		DaySetDetail temp = null;
		List<DaySetDetail> detailList = new ArrayList<DaySetDetail>();
		if (daySetDetail != null) {
			StringBuffer sql = new StringBuffer("select ID,MainID,settleDay,FeeType,"
					+ "SystemFee,HandFee,LSadjustFee,DifferenceFee,DifferenceFlag from CSMS_DaySet_Detail where 1=1 ");
			
			/*sql.append(FieldUtil.getFieldMap(DaySetDetail.class,daySetDetail).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			Map mapParam = FieldUtil.getPreFieldMap(DaySetDetail.class,daySetDetail);
			sql.append(mapParam.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) mapParam.get("paramNotNull")).toArray());
			
			for (Map<String, Object> map : list) {
				temp = new DaySetDetail();
				this.convert2Bean(map, temp);
				detailList.add(temp);
			}
		}
		return detailList;
	}
	
	public DaySetDetail findById(Long id) {
		DaySetDetail temp = null;
			
		StringBuffer sql = new StringBuffer("select ID,MainID,settleDay,FeeType,"
				+ "SystemFee,HandFee,LSadjustFee,DifferenceFee,DifferenceFlag from CSMS_DaySet_Detail where id=? ");
		List<Map<String, Object>> list = queryList(sql.toString(), id);
		
		if (!list.isEmpty()) {
			temp = new DaySetDetail();
			this.convert2Bean(list.get(0), temp);
		}
		return temp;
	}
	
	public void update(DaySetDetail daySetDetail){
		StringBuffer sql = new StringBuffer("update CSMS_DaySet_Detail set DifferenceFee=DifferenceFee-?, DifferenceFlag = ? where id = ? ");
		saveOrUpdate(sql.toString(),daySetDetail.getDifferenceFee(),daySetDetail.getDifferenceFlag(),daySetDetail.getId());
	}
	
}
