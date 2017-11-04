package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcLossInfo;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class AcLossInfoDao extends BaseDao{
	
	public void save(AcLossInfo acLossInfo) {
		Map map = FieldUtil.getPreFieldMap(AcLossInfo.class,acLossInfo);
		StringBuffer sql=new StringBuffer("insert into csms_acloss_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AcLossInfo findAcLossInfo(){
		String sql = "select "+FieldUtil.getFieldMap(AcLossInfo.class, new AcLossInfo()).get("nameStr")+" from csms_acloss_info  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		AcLossInfo acLocssInfo = null;
		if (!list.isEmpty()) {
			acLocssInfo = new AcLossInfo();
			this.convert2Bean(list.get(0), acLocssInfo);
		}

		return acLocssInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update csms_acloss_info set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from csms_acloss_info  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
}
