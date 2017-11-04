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
import com.hgsoft.macao.entity.AcStopInfo;
import com.hgsoft.macao.entity.StopCardBlackList;

@Repository
public class AcStopInfoDao extends BaseDao{
	
	public void save(AcStopInfo acStopInfo) {
		Map map = FieldUtil.getPreFieldMap(AcStopInfo.class,acStopInfo);
		StringBuffer sql=new StringBuffer("insert into csms_acstop_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AcStopInfo findAcStopInfo(){
		String sql = "select "+FieldUtil.getFieldMap(AcStopInfo.class, new AcStopInfo()).get("nameStr")+" from csms_acstop_info  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		AcStopInfo acStopInfo = null;
		if (!list.isEmpty()) {
			acStopInfo = new AcStopInfo();
			this.convert2Bean(list.get(0), acStopInfo);
		}

		return acStopInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update csms_acstop_info set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from csms_acstop_info  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
	
	public int[] batchSaveAcStopInfo(final List<AcStopInfo> list){
		 String sql = "insert into csms_acstop_info(id,interCode,createTime,Code,bankNo,placeNo,operNo) values(SEQ_CSMSACSTOPINFO_NO.nextval,?,?,?,?,?,?)";
	    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AcStopInfo acStopInfo = list.get(i);
					ps.setString(1, acStopInfo.getInterCode());
					ps.setString(2, acStopInfo.getCreateTime());
					ps.setString(3, acStopInfo.getCode());
					ps.setString(4, acStopInfo.getBankNo());
					ps.setString(5, acStopInfo.getPlaceNo());
					ps.setString(6, acStopInfo.getOperNo());
					
				}
				@Override
				public int getBatchSize() {
					 return list.size();
				}
			});
	}
}
