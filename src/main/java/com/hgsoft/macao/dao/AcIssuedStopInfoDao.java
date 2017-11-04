package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcIssuedStopInfo;
import com.hgsoft.macao.entity.AcStopInfo;

@Repository
public class AcIssuedStopInfoDao extends BaseDao{
	
	public void save(AcIssuedStopInfo acIssuedStopInfo) {
		Map map = FieldUtil.getPreFieldMap(AcIssuedStopInfo.class,acIssuedStopInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_ACISSUEDSTOP_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AcIssuedStopInfo findAcIssuedStopInfo(){
		String sql = "select "+FieldUtil.getFieldMap(AcIssuedStopInfo.class, new AcIssuedStopInfo()).get("nameStr")+" from CSMS_ACISSUEDSTOP_INFO  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		AcIssuedStopInfo acLocssInfo = null;
		if (!list.isEmpty()) {
			acLocssInfo = new AcIssuedStopInfo();
			this.convert2Bean(list.get(0), acLocssInfo);
		}

		return acLocssInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update CSMS_ACISSUEDSTOP_INFO set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from CSMS_ACISSUEDSTOP_INFO  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
	public int[] batchSaveAcIssuedStopInfo(final List<AcIssuedStopInfo> list){
		 String sql = "insert into CSMS_ACISSUEDSTOP_INFO(id,interCode,createTime,Code) values(SEQ_CSMSACISSUEDSTOPINFO_NO.nextval,?,?,?)";
	    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AcIssuedStopInfo acIssuedStopInfo = list.get(i);
					ps.setString(1, acIssuedStopInfo.getInterCode());
					ps.setString(2, acIssuedStopInfo.getCreateTime());
					ps.setString(3, acIssuedStopInfo.getCode());
					
				}
				@Override
				public int getBatchSize() {
					 return list.size();
				}
			});
	}
}
