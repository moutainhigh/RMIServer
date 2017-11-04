package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcCancelInfo;

@Repository
public class AcCancelInfoDao extends BaseDao{
	
	public void save(AcCancelInfo acCancelInfo) {
		Map map = FieldUtil.getPreFieldMap(AcCancelInfo.class,acCancelInfo);
		StringBuffer sql=new StringBuffer("insert into csms_accannel_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AcCancelInfo findAcCancelInfoInfo(){
		String sql = "select "+FieldUtil.getFieldMap(AcCancelInfo.class, new AcCancelInfo()).get("nameStr")+" from csms_accannel_info  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		AcCancelInfo acCancelInfo = null;
		if (!list.isEmpty()) {
			acCancelInfo = new AcCancelInfo();
			this.convert2Bean(list.get(0), acCancelInfo);
		}

		return acCancelInfo;
	}
	
	public void update(String errorCode,String flowNo,Long id){
		StringBuffer sql=new StringBuffer("update csms_accannel_info set errorCode=?,serviceFlowNo=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,flowNo,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from csms_accannel_info  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
}
