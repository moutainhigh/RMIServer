package com.hgsoft.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
@Repository
public class OmsUtilDao extends BaseDao{
	/**
	 * 根据标签号段，获取产品来源
	 * @param startCode 
	 * @param endCode
	 * @return String 若不同来源返回"",若返回
	 */
	public String getSourceType(String startCode,String endCode){
		String sql4Exist = "select sourceType from oms_productdetail  "
				+ "where productcode>=? and productcode<=? ";
		List<Map<String, Object>> list4Exist = queryList(sql4Exist, startCode,endCode);
		if(list4Exist.isEmpty()){
			//若不在库，返回0表示不在库
			return "0";
		}
		
		String sql4SourceType = "select sourceType from oms_productdetail  "
				+ "where productcode>=? and productcode<=? group by sourceType";
		List<Map<String, Object>> list4SourceType = queryList(sql4SourceType, startCode,endCode);
		if(list4SourceType != null && list4SourceType.size() == 1){
			//返回具体产品来源flag
			return (String)list4SourceType.get(0).get("SOURCETYPE");
		}else{
			return "";
		}
	}

}
