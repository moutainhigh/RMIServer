package com.hgsoft.common.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import com.hgsoft.utils.EtcTollingJdbcUtil;
import com.hgsoft.utils.Pager;

public class EtcTollingBaseDao {
	@Resource
	protected EtcTollingJdbcUtil etcTollingJdbcUtil;
	
	
	public int count(String sql, Object... args) {
		return etcTollingJdbcUtil.getJdbcTemplate().queryForObject(sql, args,
				Integer.class);
	}

	public List<Map<String, Object>> queryList(String sql, Object... args) {
		return etcTollingJdbcUtil.getJdbcTemplate().queryForList(sql, args);
	}

	public Object queryObject(String sql, Class<Object> c) {
		return etcTollingJdbcUtil.getJdbcTemplate().queryForObject(sql, c);
	}

	public void save(String sql) {
		etcTollingJdbcUtil.save(sql);
	}
	public void saveByBatchUpdate(String sql, List<Object[]> batchArgs) {
		etcTollingJdbcUtil.getJdbcTemplate().batchUpdate(sql, batchArgs);
	}
	
	public void saveOrUpdate(String sql,List objects,Object... args) {
		for (int i = 1; i <= args.length; i++) {
			objects.add(args[i-1]);
		}
		etcTollingJdbcUtil.saveOrUpdate(sql, objects.toArray());
	}
	
	public void saveOrUpdate(String sql,Object... args) {
		etcTollingJdbcUtil.saveOrUpdate(sql, args);
	}
	
	public void delete(String sql,Object... args) {
		etcTollingJdbcUtil.saveOrUpdate(sql, args);
	}

	public void update(String sql) {
		etcTollingJdbcUtil.update(sql);
	}

	public void delete(String sql) {
		etcTollingJdbcUtil.delete(sql);
	}

	public void commit() {
		etcTollingJdbcUtil.getJdbcTemplate().execute("commit");
	}
	
	public int[] batchUpdate(String sql,BatchPreparedStatementSetter pss){
		return 		etcTollingJdbcUtil.getJdbcTemplate().batchUpdate(sql,pss);
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
	public Object convert2Bean(Map<String, Object> map, Object object)
			throws IllegalAccessException, IllegalArgumentException,
			NoSuchMethodException, SecurityException, InvocationTargetException {
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
				try {
					intVal = Integer.parseInt(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				setMethod.invoke(object, new Object[] { intVal });
			} else if (setMethod.getParameterTypes()[0].getName().equals(
					"java.util.Date")) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = null;
				try {
					// date=format.parse(value.toString().substring(0,19));
					date = format.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				setMethod.invoke(object, new Object[] { date });
			} else if (setMethod.getParameterTypes()[0].getName().equals(
					"java.lang.String")) {
				setMethod.invoke(object, new Object[] { value });
			} else if (setMethod.getParameterTypes()[0].getName().equals(
					"java.lang.Long")) {
				Long intVal = null;
				try {
					intVal = Long.parseLong(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				setMethod.invoke(object, new Object[] { intVal });
			} else if (setMethod.getParameterTypes()[0].getName().equals(
					"java.math.BigDecimal")) {
				BigDecimal intVal = null;
				try {
					intVal = new BigDecimal(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				setMethod.invoke(object, new Object[] { intVal });
			} else if (setMethod.getParameterTypes()[0].getName().equals(
					"java.lang.Double")) {
				Double intVal = 0.0;
				try {
					intVal = Double.parseDouble(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				setMethod.invoke(object, new Object[] { intVal });
			}

		}
		return object;
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
		if(		etcTollingJdbcUtil.getJdbcTemplate() == null){
			throw new IllegalArgumentException("Pager.jTemplate is null");
		}else if(sql == null || sql.equals("")){
			throw new IllegalArgumentException("Pager.sql is empty");
		}	
		//计算总记录数
		StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) totalTable ");
		
		//总记录数
		pager.setTotalSize(	etcTollingJdbcUtil.getJdbcTemplate().queryForInt(totalSQL.toString(),Objects));
		StringBuffer paginationSQL = new StringBuffer();
		paginationSQL.append("SELECT   rownum num,  page.* FROM(");
		paginationSQL.append(sql);
		
		paginationSQL.append(" ) Page WHERE rownum < "+pager.getEnd()+"  MINUS  SELECT rownum, page.* FROM (  ");
		
		paginationSQL.append(sql);
		paginationSQL.append(") Page WHERE rownum < " + pager.getBegin());
		/*paginationSQL.append(") page where num >= " + pager.getBegin() + " and num <" + pager.getEnd());*/
		System.out.println(paginationSQL);
		//装入结果集
		if(Objects==null)pager.setResultList(etcTollingJdbcUtil.getJdbcTemplate().queryForList(paginationSQL.toString(),Objects));
		else pager.setResultList(etcTollingJdbcUtil.getJdbcTemplate().queryForList(paginationSQL.toString(),concat(Objects,Objects)));
		return pager;
	}
}
