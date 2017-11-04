package com.hgsoft.app.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.app.entity.AppPointCtr;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class AppPointCtrDao extends BaseDao{
	public AppPointCtr findByPlaceNo(String placeNo){
		StringBuffer sql = new StringBuffer("select * from csms_appPointCtr where 1=1 ");
		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(placeNo)){
			params.eq("placeNo", placeNo);
		}
		
		sql.append(params.getParam());
		List<Map<String, Object>> list = queryList(sql.toString(),params.getList().toArray());
		AppPointCtr appPointCtr = null;
		if(list.size()>0){
			appPointCtr = new AppPointCtr();
			convert2Bean(list.get(0),appPointCtr);
		}
		return appPointCtr;
	}
}
