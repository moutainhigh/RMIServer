package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.TagIssuceInfo;
import com.hgsoft.macao.entity.TagReplaceInfo;

@Repository
public class TagReplaceInfoDao extends BaseDao{
	
	public void save(TagReplaceInfo tagReplaceInfo) {
		Map map = FieldUtil.getPreFieldMap(TagReplaceInfo.class,tagReplaceInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_TAGREPLACE_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public TagReplaceInfo findTagReplaceInfo(){
		String sql = "select "+FieldUtil.getFieldMap(TagReplaceInfo.class, new TagReplaceInfo()).get("nameStr")+" from CSMS_TAGREPLACE_INFO  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		TagReplaceInfo tagReplaceInfo = null;
		if (!list.isEmpty()) {
			tagReplaceInfo = new TagReplaceInfo();
			this.convert2Bean(list.get(0), tagReplaceInfo);
		}

		return tagReplaceInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update CSMS_TAGREPLACE_INFO set errorCode=?  where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from CSMS_TAGREPLACE_INFO  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
}
