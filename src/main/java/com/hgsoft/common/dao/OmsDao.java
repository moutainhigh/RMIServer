package com.hgsoft.common.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.OmsJdbcUtil;
import com.hgsoft.utils.Pager;

import oracle.jdbc.internal.OracleTypes;

public class OmsDao {
	@Resource
	protected OmsJdbcUtil omsJdbcUtil;
	private static Logger logger = Logger.getLogger(OmsDao.class);
	
	public Integer test() {
		@SuppressWarnings("unchecked")
		Integer param2Value = (Integer) omsJdbcUtil.getJdbcTemplate().execute(
		new CallableStatementCreator() {
			public CallableStatement createCallableStatement(
					Connection con) throws SQLException {
				String storedProc = "{call CANCLEISOK (?,?)}";// 调用的sql
				CallableStatement cs = con.prepareCall(storedProc);
				cs.setInt(1, 1);// 设置输入参数的值
				cs.registerOutParameter(2, OracleTypes.INTEGER);// 注册输出参数的类型
				return cs;
			}
		}, new CallableStatementCallback() { 
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				return cs.getInt(2);// 获取输出参数的值
			} });
		
		return param2Value;
	}
	
	public int count(String sql, Object... args) {
		/*System.out.println("sql："+sql);
		System.out.println("参数："+Arrays.toString(args));*/
		return omsJdbcUtil.getJdbcTemplate().queryForObject(sql, args,
				Integer.class);
	}
	
	public int queryCount(String sql, Object... args){
		/*System.out.println("sql："+sql);
		System.out.println("参数："+Arrays.toString(args));*/
		return omsJdbcUtil.getJdbcTemplate().queryForInt(sql,args);
	}

	public List<Map<String, Object>> queryList(String sql, Object... args) {
		/*System.out.println("sql:"+sql);
		System.out.println("参数:"+Arrays.toString(args));*/
		return omsJdbcUtil.getJdbcTemplate().queryForList(sql, args);
	}

	public Object queryObject(String sql, Class<Object> c) {
		return omsJdbcUtil.getJdbcTemplate().queryForObject(sql, c);
	}

	public <T> T queryObject(String sql, Class<T> c, Object... args) {
		RowMapper<T> rm = ParameterizedBeanPropertyRowMapper.newInstance(c);
		return omsJdbcUtil.getJdbcTemplate().queryForObject(sql, rm, args);
	}

	public void save(String sql) {
		/*System.out.println("sql:"+sql);
		System.out.println("参数:[]");*/
		omsJdbcUtil.save(sql);
	}

	public void save(String sql, Object... args) {
		/*System.out.println("sql:"+sql);
		System.out.println("参数:[]");*/
		omsJdbcUtil.getJdbcTemplate().update(sql, args);
	}
	
	public int saveOrUpdate(String sql,List objects,Object... args) {
		for (int i = 1; i <= args.length; i++) {
			objects.add(args[i-1]);
		}
		/*System.out.println("sql:"+sql);
		System.out.println("参数："+objects);*/
		return omsJdbcUtil.saveOrUpdate(sql, objects.toArray());
	}
	
	public void saveOrUpdate(String sql,Object... args) {
		/*System.out.println("sql："+sql);
		System.out.println("参数："+Arrays.toString(args));*/
		omsJdbcUtil.saveOrUpdate(sql, args);
	}
	
	public void delete(String sql,Object... args) {
		/*System.out.println("sql："+sql);
		System.out.println("参数："+Arrays.toString(args));*/
		omsJdbcUtil.saveOrUpdate(sql, args);
	}

	public void update(String sql) {
		/*System.out.println("sql："+sql);
		System.out.println("参数:[]");*/
		omsJdbcUtil.update(sql);
	}

	public void update(String sql,Object... args) {
		omsJdbcUtil.getJdbcTemplate().update(sql, args);
	}

	public void delete(String sql) {
		/*System.out.println("sql："+sql);
		System.out.println("参数:[]");*/
		omsJdbcUtil.delete(sql);
	}

	public void commit() {
		omsJdbcUtil.getJdbcTemplate().execute("commit");
	}
	
	public int[] batchUpdate(String sql,BatchPreparedStatementSetter pss){
//		System.out.println("sql："+sql);
		return omsJdbcUtil.getJdbcTemplate().batchUpdate(sql,pss);
	}

	/**
	 * 说明：对象类型转换
	 * @param map集合
	 * @param object传入的类型
	 * @return 以传入的类型对象
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 */
	public Object convert2Bean(Map<String, Object> map, Object object) {
		try {
			Class<?> classType = object.getClass();
			Field[] fields = classType.getDeclaredFields();
			for (Field field : fields) {
				//  4.遍历数组获取各个成员变量名字
				String name = field.getName();
				if (name.toUpperCase().equals("SerialVersionUID".toUpperCase())) {
					continue;
				}
				//  5.操作字符串获取成员变量的set和get方法名字；
				String firstLetter = name.substring(0, 1).toUpperCase();
				// String getMethodName = "get"+firstLetter+name.substring(1);
				String setMethodName = "set" + firstLetter + name.substring(1);
				// Method getMethod = classType.getMethod(getMethodName,new
				// Class<?>[] {});
				Method setMethod = classType.getMethod(setMethodName,
						new Class<?>[] { field.getType() });
				// Object value = getMethod.invoke(copyObj, new Object[] {});
				// seMethod.invoke(copyObj, new Object[] { value });
				// Object value = getMethod.invoke(object, new Object[]{});
				Object value = map.get(name.toUpperCase());
				if (value == null) {
					setMethod.invoke(object, new Object[] { value });
				} else if (setMethod.getParameterTypes()[0].getName().equals(
						"java.lang.Integer")) {
					Integer intVal = null;
					intVal = Integer.parseInt(value.toString());
					setMethod.invoke(object, new Object[] { intVal });
				} else if (setMethod.getParameterTypes()[0].getName().equals(
						"java.util.Date")) {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = null;
					// date=format.parse(value.toString().substring(0,19));
					date = format.parse(value.toString());
					setMethod.invoke(object, new Object[] { date });
				} else if (setMethod.getParameterTypes()[0].getName().equals(
						"java.lang.String")) {
					setMethod.invoke(object, new Object[] { value.toString() });
				} else if (setMethod.getParameterTypes()[0].getName().equals(
						"java.lang.Long")) {
					Long intVal = null;
					intVal = Long.parseLong(value.toString());
					setMethod.invoke(object, new Object[] { intVal });
				} else if (setMethod.getParameterTypes()[0].getName().equals(
						"java.math.BigDecimal")) {
					BigDecimal intVal = null;
					intVal = new BigDecimal(value.toString());
					setMethod.invoke(object, new Object[] { intVal });
				} else if (setMethod.getParameterTypes()[0].getName().equals(
						"java.lang.Double")) {
					Double intVal = 0.0;
					intVal = Double.parseDouble(value.toString());
					setMethod.invoke(object, new Object[] { intVal });
				}

			}
			return object;
		} catch (Exception e) {
			String message = "转换异常";
			logger.error(message, e);
			throw new ApplicationException("convert2Bean error", message);
		}
	}
	
	static Object[] concat(Object[] a, Object[] b) {  
		Object[] c= new Object[a.length+b.length];  
	   System.arraycopy(a, 0, c, 0, a.length);  
	   System.arraycopy(b, 0, c, a.length, b.length);  
	   return c;  
	} 

	/**
	 * 说明：分页SQL
	 * @param sql传入的查询sql
	 * @param currentPage 当前页码
	 * @param pageSize 每页显示数量
	 * @return 返回分页参数
	 * @author gaosiling
	 */
	public Pager findByPages(String sql, Pager pager,Object[] Objects ){
		if(omsJdbcUtil.getJdbcTemplate() == null){
			throw new IllegalArgumentException("Pager.jTemplate is null");
		}else if(sql == null || sql.equals("")){
			throw new IllegalArgumentException("Pager.sql is empty");
		}	
		//计算总记录数
		StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) totalTable ");
		
		//总记录数
		pager.setTotalSize(omsJdbcUtil.getJdbcTemplate().queryForInt(totalSQL.toString(),Objects));
		StringBuffer paginationSQL = new StringBuffer();
		paginationSQL.append("SELECT   rownum num,  page.* FROM(");
		paginationSQL.append(sql);
		
		paginationSQL.append(" ) Page WHERE rownum < "+pager.getEnd()+"  MINUS  SELECT rownum, page.* FROM (  ");
		
		paginationSQL.append(sql);
		paginationSQL.append(") Page WHERE rownum < " + pager.getBegin());
		/*paginationSQL.append(") page where num >= " + pager.getBegin() + " and num <" + pager.getEnd());*/
		/*System.out.println("sql："+sql);
		System.out.println("参数："+Arrays.toString(Objects));*/
		//装入结果集
		if(Objects==null)pager.setResultList(omsJdbcUtil.getJdbcTemplate().queryForList(paginationSQL.toString(),Objects));
		else pager.setResultList(omsJdbcUtil.getJdbcTemplate().queryForList(paginationSQL.toString(),concat(Objects,Objects)));
		return pager;
	}
}
