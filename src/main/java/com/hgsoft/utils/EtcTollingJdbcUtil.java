package com.hgsoft.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EtcTollingJdbcUtil {
	
	private JdbcTemplate jdbcTemplate;
	

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	//qinzuohai
	public void delete(String sql) {
		 jdbcTemplate.execute(sql);
	}
	
	//qinzuohai
	public void save(String sql) {
		 jdbcTemplate.execute(sql);
	}
	
	public int update(String sql) {
		return jdbcTemplate.update(sql);
	}
	
	public int saveOrUpdate(String sql,Object[] objects) {
		return jdbcTemplate.update(sql, objects);
	}
	
	public int select(String sql) {
		try {
			return jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			return 0;

		}

		
	}
	public String selectForString(String sql){
		try{
			return (String)jdbcTemplate.queryForObject(sql, String.class);
		}catch(IncorrectResultSizeDataAccessException e){
			return "";
			
		}
	}
	@SuppressWarnings("unchecked")
	public List selectForList(String sql) {
		List list = new ArrayList();
		try {
			list = jdbcTemplate.queryForList(sql);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int[] batchUpdate(String[] sql) throws Exception{
		return jdbcTemplate.batchUpdate(sql);
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	

}
