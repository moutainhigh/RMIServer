package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.DaySetDetail;
import com.hgsoft.daysettle.entity.DaySetWareDetail;
import com.hgsoft.daysettle.entity.DaySetWareRecord;

@Component
public class DaySetWareDetailDao extends BaseDao{

	public int[] savebatchDaySetDetail(final List<DaySetWareDetail> list,final DaySetWareRecord daySetRecord) {  
        String sql = "insert into CSMS_DaySetWare_Detail(ID,MainID,SettleDay,ProductType,CurrBalanceNum,CurrRecoverNum,SysCurrBalanceNum,SysCurrRecoverNum,BalanceDiffNum,RecoverDiffNum,DifferenceFlag,serviceType,LSadjustNum,InStockNum,outStockNum)"
        		+ " values(SEQ_CSMSDaySetWareDetail_NO.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				DaySetWareDetail daySetWareDetail = list.get(i);
				ps.setLong(1, daySetRecord.getId());
				ps.setString(2, daySetRecord.getSettleDay());
				
				ps.setString(3, daySetWareDetail.getProductType());
				if(daySetWareDetail.getCurrBalanceNum()!=null){
					ps.setInt(4, daySetWareDetail.getCurrBalanceNum());
				}else{
					ps.setInt(4, 0);
				}
				if(daySetWareDetail.getCurrRecoverNum()!=null){
					ps.setInt(5, daySetWareDetail.getCurrRecoverNum());
				}else{
					ps.setInt(5, 0);
				}
				if(daySetWareDetail.getSysCurrBalanceNum()!=null){
					ps.setInt(6, daySetWareDetail.getSysCurrBalanceNum());
				}else{
					ps.setInt(6, 0);
				}
				if(daySetWareDetail.getSysCurrRecoverNum()!=null){
					ps.setInt(7, daySetWareDetail.getSysCurrRecoverNum());
				}else{
					ps.setInt(7, 0);
				}
				if(daySetWareDetail.getBalanceDiffNum()!=null){
					ps.setInt(8, daySetWareDetail.getBalanceDiffNum());
				}else{
					ps.setInt(8, 0);
				}
				if(daySetWareDetail.getRecoverDiffNum()!=null){
					ps.setInt(9, daySetWareDetail.getRecoverDiffNum());
				}else{
					ps.setInt(9, 0);
				}
				ps.setString(10, daySetWareDetail.getDifferenceFlag());
				ps.setString(11, daySetWareDetail.getServiceType());
				ps.setInt(12, daySetWareDetail.getLsadjustNum());
				ps.setInt(13, daySetWareDetail.getInStockNum());
				ps.setInt(14, daySetWareDetail.getOutStockNum());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }  
	
	public List<DaySetWareDetail> findList(DaySetWareDetail daySetWareDetail) {
		DaySetWareDetail temp = null;
		List<DaySetWareDetail> detailList = new ArrayList<DaySetWareDetail>();
		if (daySetWareDetail != null) {
			StringBuffer sql = new StringBuffer("select ID,MainID,SettleDay,ProductType,CurrBalanceNum,CurrRecoverNum,SysCurrBalanceNum,SysCurrRecoverNum,BalanceDiffNum,RecoverDiffNum,DifferenceFlag,serviceType,LSadjustNum"
					+ " from CSMS_DaySetWare_Detail where 1=1 ");
			
			/*sql.append(FieldUtil.getFieldMap(DaySetWareDetail.class,daySetWareDetail).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			Map mapParam = FieldUtil.getPreFieldMap(DaySetWareDetail.class,daySetWareDetail);
			sql.append(mapParam.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) mapParam.get("paramNotNull")).toArray());
			
			for (Map<String, Object> map : list) {
				temp = new DaySetWareDetail();
				this.convert2Bean(map, temp);
				detailList.add(temp);
			}

		}
		return detailList;
	}
	
	public DaySetWareDetail findById(Long id) {
		DaySetWareDetail temp = null;
			
		StringBuffer sql = new StringBuffer("select ID,MainID,SettleDay,ProductType,CurrBalanceNum,CurrRecoverNum,SysCurrBalanceNum,SysCurrRecoverNum,BalanceDiffNum,RecoverDiffNum,DifferenceFlag,serviceType,LSadjustNum"
				+ " from CSMS_DaySetWare_Detail where id=? ");
		List<Map<String, Object>> list = queryList(sql.toString(), id);
		
		if (!list.isEmpty()) {
			temp = new DaySetWareDetail();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	public void update(DaySetWareDetail daySetWareDetail){
		StringBuffer sql = new StringBuffer("update CSMS_DaySetWare_Detail  set BalanceDiffNum=BalanceDiffNum-?,RecoverDiffNum=RecoverDiffNum-?, DifferenceFlag = ? where id = ?");
		saveOrUpdate(sql.toString(),daySetWareDetail.getBalanceDiffNum(),daySetWareDetail.getRecoverDiffNum(),daySetWareDetail.getDifferenceFlag(),daySetWareDetail.getId());
	}
}
