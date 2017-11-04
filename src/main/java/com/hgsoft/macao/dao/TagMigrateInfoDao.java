package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.TagMigrateInfo;
import com.hgsoft.macao.entity.TagReplaceInfo;

@Repository
public class TagMigrateInfoDao extends BaseDao{
	
	public void save(TagMigrateInfo tagMigrateInfo) {
		Map map = FieldUtil.getPreFieldMap(TagMigrateInfo.class,tagMigrateInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_TAGMIGRATE_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public TagMigrateInfo findTagMigrateInfo(){
		String sql = "select "+FieldUtil.getFieldMap(TagMigrateInfo.class, new TagMigrateInfo()).get("nameStr")+" from CSMS_TAGMIGRATE_INFO  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		TagMigrateInfo tagMigrateInfo = null;
		if (!list.isEmpty()) {
			tagMigrateInfo = new TagMigrateInfo();
			this.convert2Bean(list.get(0), tagMigrateInfo);
		}

		return tagMigrateInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update CSMS_TAGMIGRATE_INFO set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from CSMS_TAGMIGRATE_INFO  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
}
