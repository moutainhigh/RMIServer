package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcCancelInfo;
import com.hgsoft.macao.entity.VechileChangeInfo;

@Repository
public class VechileChangeInfoDao extends BaseDao{
	
	public void save(VechileChangeInfo vechileChangeInfo) {
		Map map = FieldUtil.getPreFieldMap(VechileChangeInfo.class,vechileChangeInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_VECHILECHANGE_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public VechileChangeInfo findVechileChangeInfo(){
		String sql = "select "+FieldUtil.getFieldMap(VechileChangeInfo.class, new VechileChangeInfo()).get("nameStr")+" from CSMS_VECHILECHANGE_INFO  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		VechileChangeInfo vechileChangeInfo = null;
		if (!list.isEmpty()) {
			vechileChangeInfo = new VechileChangeInfo();
			this.convert2Bean(list.get(0), vechileChangeInfo);
		}

		return vechileChangeInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update CSMS_VECHILECHANGE_INFO set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from CSMS_VECHILECHANGE_INFO  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
}
