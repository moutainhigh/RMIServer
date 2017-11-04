package com.hgsoft.daysettle.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.AfterDaySetFee;
import com.hgsoft.daysettle.entity.AfterDaySetWare;

@Component
public class AfterDaySetWareDao extends BaseDao{

	public int[] batchSaveAfterDaySetWare(final List<AfterDaySetWare> list,Long reportOperID,Long reportPlaceID) {  
        String sql = "insert into CSMS_AfterDaySetWare(ID,DatSetWareID,productType,BalanceDiffNum,RecoverDiffNum,ReportOperID,ReportTime,ReportPlaceID)"
        		+ " values(SEQ_CSMSAfterDaySetWare_NO.nextval,?,?,?,?,"+reportOperID+",sysdate,"+reportPlaceID+")";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			int a=0;
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				AfterDaySetWare afterDaySetWare = list.get(i);
				if(afterDaySetWare!=null){
					ps.setLong(1, afterDaySetWare.getDatSetWareID());
					ps.setString(2, afterDaySetWare.getProductType());
					ps.setInt(3, afterDaySetWare.getBalanceDiffNum());
					ps.setInt(4, afterDaySetWare.getRecoverDiffNum());
					a++;
				}
			}
			
			@Override
			public int getBatchSize() {
				 return a;
			}
		});
    }  
	
	public void save(AfterDaySetWare afterDaySetWare) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_AfterDaySetWare(");
		sql.append(FieldUtil.getFieldMap(AfterDaySetWare.class,afterDaySetWare).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AfterDaySetWare.class,afterDaySetWare).get("valueStr")+")");
		save(sql.toString());*/
		afterDaySetWare.setHisSeqID(-afterDaySetWare.getId());
		Map map = FieldUtil.getPreFieldMap(AfterDaySetWare.class,afterDaySetWare);
		StringBuffer sql=new StringBuffer("insert into CSMS_AfterDaySetWare");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
