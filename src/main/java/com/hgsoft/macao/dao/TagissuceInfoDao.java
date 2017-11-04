package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.ServerChangeInfo;
import com.hgsoft.macao.entity.TagIssuceInfo;

@Repository
public class TagissuceInfoDao extends BaseDao{
	
	public void save(TagIssuceInfo tagIssuceInfo) {
		Map map = FieldUtil.getPreFieldMap(TagIssuceInfo.class,tagIssuceInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_TAGISSUCE_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public TagIssuceInfo findTagIssuceInfo(){
		String sql = "select "+FieldUtil.getFieldMap(TagIssuceInfo.class, new TagIssuceInfo()).get("nameStr")+" from CSMS_TAGISSUCE_INFO  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		TagIssuceInfo tagIssuceInfo = null;
		if (!list.isEmpty()) {
			tagIssuceInfo = new TagIssuceInfo();
			this.convert2Bean(list.get(0), tagIssuceInfo);
		}

		return tagIssuceInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update CSMS_TAGISSUCE_INFO set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from CSMS_TAGISSUCE_INFO  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
}
