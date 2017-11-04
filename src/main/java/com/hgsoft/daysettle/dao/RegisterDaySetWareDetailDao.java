package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.RegisterDaySetWareDetail;
import com.hgsoft.daysettle.entity.RegisterDaySetWareRecord;

@Component
public class RegisterDaySetWareDetailDao extends BaseDao{

	public int[] savebatchDaySetDetail(final List<RegisterDaySetWareDetail> list,final RegisterDaySetWareRecord registerDaySetWareRecord) {  
        String sql = "insert into CSMS_RegisterDaySetWare_Detail(ID,MainID,SettleDay,ProductType,CurrBalanceNum,CurrRecoverNum,serviceType,memo)"
        		+ " values(SEQ_CSMSRDaySetWaredetail_NO.nextval,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				RegisterDaySetWareDetail registerDaySetWareDetail = list.get(i);
				ps.setLong(1, registerDaySetWareRecord.getId());
				ps.setString(2, registerDaySetWareDetail.getSettleDay());
				
				ps.setString(3, registerDaySetWareDetail.getProductType());
				if(registerDaySetWareDetail.getCurrBalanceNum()!=null){
					ps.setInt(4, registerDaySetWareDetail.getCurrBalanceNum());
				}else{
					ps.setInt(4, 0);
				}
				if(registerDaySetWareDetail.getCurrRecoverNum()!=null){
					ps.setInt(5, registerDaySetWareDetail.getCurrRecoverNum());
				}else{
					ps.setInt(5, 0);
				}
				ps.setString(6, registerDaySetWareDetail.getServiceType());
				ps.setString(7, registerDaySetWareDetail.getMemo());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }  
	
	public List<RegisterDaySetWareDetail> findList(RegisterDaySetWareDetail registerDaySetWareDetail) {
		RegisterDaySetWareDetail temp = null;
		List<RegisterDaySetWareDetail> detailList = new ArrayList<RegisterDaySetWareDetail>();
		if (registerDaySetWareDetail != null) {
			StringBuffer sql = new StringBuffer("select ID,MainID,SettleDay,ProductType," +
					"CurrBalanceNum,CurrRecoverNum,serviceType"
					+ " from CSMS_RegisterDaySetWare_Detail where 1=1 ");
			
			Map<String, Object> mapParam = FieldUtil.getPreFieldMap(RegisterDaySetWareDetail.class,registerDaySetWareDetail);
			sql.append(mapParam.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id desc");
			@SuppressWarnings("rawtypes")
			List<Map<String, Object>> list = queryList(sql.toString(),((List) mapParam.get("paramNotNull")).toArray());
			
			for (Map<String, Object> map : list) {
				temp = new RegisterDaySetWareDetail();
				this.convert2Bean(map, temp);
				detailList.add(temp);
			}

		}
		return detailList;
	}
	
}
