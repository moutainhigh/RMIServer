package com.hgsoft.httpInterface.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.SpecialCostType;
import com.hgsoft.utils.StringUtil;

@Repository
public class SpecialCostTypeDao extends BaseDao{
	public void save(SpecialCostType specialCostType){
		Map map = FieldUtil.getPreFieldMap(SpecialCostType.class,specialCostType);
		StringBuffer sql=new StringBuffer("insert into CSMS_specialCostType");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	public void update(Long omsId,String code,String categoryName,String remark,String state,String flag){
		String separator = ",";
		StringBuffer sql =new StringBuffer("update CSMS_specialCostType set ");
		List<String> list = new ArrayList<String>();
		if(StringUtil.isNotBlank(code)){
			sql.append("code=?");
			list.add(code);
		}
		if(StringUtil.isNotBlank(categoryName)){
			if(list.size()>0)
				sql.append(separator);
			sql.append("categoryName=?");
			list.add(categoryName);
		}
		if(StringUtil.isNotBlank(remark)){
			if(list.size()>0)
				sql.append(separator);
			sql.append("remark=?");
			list.add(remark);
		}
		if(StringUtil.isNotBlank(state)){
			if(list.size()>0)
				sql.append(separator);
			sql.append("state=? ");
			list.add(state);
		}
		if(StringUtil.isNotBlank(flag)){
			if(list.size()>0)
				sql.append(separator);
			sql.append("flag=? ");
			list.add(flag);
		}
		sql.append("where omsId=?");
		saveOrUpdate(sql.toString(),list,omsId);
	}
	public void delete(Long omsId){
		String sql = "delete from CSMS_specialCostType where omsId=?";
		delete(sql,omsId);
	}
}
