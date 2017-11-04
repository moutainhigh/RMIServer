package com.hgsoft.utils;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class OmsJdbcUtil {
	@Resource
	private JdbcTemplate omsJdbcTemplate;

	public void delete(String sql) {
		getJdbcTemplate().execute(sql);
	}
	
	public void save(String sql) {
		getJdbcTemplate().execute(sql);
	}
	
	public int update(String sql) {
		return getJdbcTemplate().update(sql);
	}
	
	public int saveOrUpdate(String sql,Object[] objects) {
		return getJdbcTemplate().update(sql, objects);
	}
	
	public int select(String sql) {
		try {
			return getJdbcTemplate().queryForInt(sql);
		} catch (Exception e) {
			return 0;

		}
	}

	public String selectForString(String sql){
		try{
			return (String)getJdbcTemplate().queryForObject(sql, String.class);
		}catch(IncorrectResultSizeDataAccessException e){
			return "";
			
		}
	}
	@SuppressWarnings("unchecked")
	public List selectForList(String sql) {
		List list = new ArrayList();
		try {
			list = getJdbcTemplate().queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int[] batchUpdate(String[] sql) throws Exception{
		return getJdbcTemplate().batchUpdate(sql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return omsJdbcTemplate;
	}

}
