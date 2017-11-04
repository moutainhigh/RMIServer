package com.hgsoft.httpInterface.dao;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.SpecialCostSubclass;
import com.hgsoft.utils.StringUtil;

@Repository
public class SpecialCostSubclassDao extends BaseDao{
	public void save(SpecialCostSubclass specialCostSubclass){
		Map map = FieldUtil.getPreFieldMap(SpecialCostSubclass.class,specialCostSubclass);
		StringBuffer sql=new StringBuffer("insert into CSMS_specialCostSubclass");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	public void update(Long omsId,Long specialCostType, String code,String categoryName, BigDecimal charge, String remark, String state,String flag){
		String separator = ",";
		StringBuffer sql =new StringBuffer("update CSMS_specialCostSubclass set ");
		List<Object> list = new ArrayList<Object>();
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
		if(charge!=null){
			if(list.size()>0)
				sql.append(separator);
			sql.append("charge=?");
			list.add(charge);
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
		if(specialCostType!=null){
			if(list.size()>0)
				sql.append(separator);
			sql.append("specialCostType=? ");
			list.add(specialCostType);
		}
		sql.append("where omsId=?");
		saveOrUpdate(sql.toString(),list,omsId);
		
	}
	public void delete(Long specialCostType){
		String sql = "delete from CSMS_specialCostSubclass where omsId=?";
		delete(sql,specialCostType);
	}
}
