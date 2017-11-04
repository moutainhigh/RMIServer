package com.hgsoft.acms.obu.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.obu.entity.TagReplaceFlow;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TagReplaceFlowDaoACMS extends BaseDao {

	
	public TagReplaceFlow findByTagNo(String oldTagNo){
		String sql="select * from CSMS_TagReplace_Flow where newTagno = ? order by id fetch first 1 rows only ";
		List<TagReplaceFlow> tagReplaceFlows = super.queryObjectList(sql, TagReplaceFlow.class, oldTagNo);
		if (tagReplaceFlows == null || tagReplaceFlows.isEmpty()) {
			return null;
		}
		return tagReplaceFlows.get(0);
	}
	
	public void save(TagReplaceFlow tagInfo){
		tagInfo.setReplaceTime(new Date());
		Map map = FieldUtil.getPreFieldMap(TagReplaceFlow.class,tagInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_TagReplace_Flow");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	
	
}
