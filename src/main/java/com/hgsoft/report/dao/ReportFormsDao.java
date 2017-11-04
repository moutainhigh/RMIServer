package com.hgsoft.report.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.report.entity.CustomArea;
import com.hgsoft.report.entity.CustomPointType;

@Repository
public class ReportFormsDao extends BaseDao{

	
	@SuppressWarnings("unchecked")
	public List<CustomPointType> findAllTypeIdAndName() {
		String sql = "select ct.id,ct.name from OMS_CUSTOMPOINTTYPE ct where 1=1";
		sql += " order by id ";
		return jdbcUtil.selectForList(sql);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CustomArea> findAllAreaCodeAndName() {
		String sql = "select oa.code,oa.name from oms_Area oa order by id";
		return jdbcUtil.selectForList(sql);
	}
}
