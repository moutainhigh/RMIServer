package com.hgsoft.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.StringUtil;

public class FieldUtil {
	
	public static String getValue(Field field,Class<?> objClass,Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String methodName = "";
		String name = "";
		Method method = null;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (field != null) {
			name = field.getName();
			methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			method = objClass.getMethod(methodName);
			if (method != null) {
				Object value = method.invoke(obj);
				if (method.getReturnType().toString().equals("class java.lang.Integer")) {
					return (value==null)?"NULL":value+"";
				} else if (method.getReturnType().toString().equals(
						"class java.util.Date")) {
					return (value==null)?"NULL":"to_date('"+format.format((Date) value)+"','YYYY-MM-DD HH24:MI:SS')";
				} else if (method.getReturnType().toString().equals(
						"class java.lang.String")) {
					return (value==null)?"NULL":"'"+value+"'";
				} else if (method.getReturnType().toString().equals(
						"class java.lang.Long")) {
					return (value==null)?"NULL":value+"";
				} else if (method.getReturnType().toString().equals(
						"class java.math.BigDecimal")) {
					return (value==null)?"NULL":value+"";
				} else if (method.getReturnType().toString().equals(
						"class java.lang.Double")) {
					return (value==null)?"NULL":value+"";
				}else if (method.getReturnType().toString().equals(
						"class java.sql.Timestamp")) {
					return (value==null)?"NULL":"to_timestamp('"+value+"','yyyy-mm-dd hh24:mi:ss:ff')";
				}
			}
		}
		return "NULL";
	}
	
	public static Map<String, String> getFieldMap(Class objClass,Object obj){
		Map<String, String> map = new HashMap<String, String>();
		try {
			Field[] fields = objClass.getDeclaredFields();
			StringBuilder nameBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();
			StringBuilder nameNotNullBuilder = new StringBuilder();
			StringBuilder valueNotNullBuilder = new StringBuilder();
			StringBuilder nameAndValueBuilder = new StringBuilder();
			StringBuilder nameAndBalueNotNullBuilder = new StringBuilder();
			StringBuilder nameAndValueNotNullToUpdateBuilder = new StringBuilder();
			String value = "";
			for (int i = 0; i < fields.length; i++) {
				//if (!fields[i].getName().equals("id")&&!fields[i].getName().equals("serialVersionUID")) {
				if (!fields[i].getName().equals("serialVersionUID")) {
					value = getValue(fields[i], objClass, obj);
					nameBuilder.append(",").append(fields[i].getName());
					valueBuilder.append(",").append(value);
					nameAndValueBuilder.append(",").append(fields[i].getName()).append("=").append(value);
					//System.out.println(fields[i].getName()+";"+value+";"+(StringUtils.isNotBlank(""))+";"+(StringUtils.isNotBlank(value)&&(!value.equals("NULL"))));
					if (StringUtils.isNotBlank(value) && (!value.equals("NULL")) && (!value.equals("''"))) {
						nameAndBalueNotNullBuilder.append(" and ").append(fields[i].getName()).append("=").append(value);
						nameAndValueNotNullToUpdateBuilder.append(",").append(fields[i].getName()).append("=").append(value);
						nameNotNullBuilder.append(",").append(fields[i].getName());
						valueNotNullBuilder.append(",").append(value);
					}
				}
			}
			String nameStr = nameBuilder.toString();
			if (StringUtils.isNotBlank(nameStr)) {
				nameStr = nameStr.substring(1);
			}
			String valueStr = valueBuilder.toString();
			if (StringUtils.isNotBlank(valueStr)) {
				valueStr = valueStr.substring(1);
			}
			String nameStrNotNull = nameNotNullBuilder.toString();
			if (StringUtils.isNotBlank(nameStrNotNull)) {
				nameStrNotNull = nameStrNotNull.substring(1);
			}
			String valueStrNotNull = valueNotNullBuilder.toString();
			if (StringUtils.isNotBlank(valueStrNotNull)) {
				valueStrNotNull = valueStrNotNull.substring(1);
			}
			String nameAndValue = nameAndValueBuilder.toString();
			if (StringUtils.isNotBlank(nameAndValue)) {
				nameAndValue = nameAndValue.substring(1);
			}
			String nameAndValueNotNull = nameAndBalueNotNullBuilder.toString();

			String nameAndValueNotNullToUpdate = nameAndValueNotNullToUpdateBuilder.toString();
			if (StringUtils.isNoneBlank(nameAndValueNotNullToUpdate)) {
				nameAndValueNotNullToUpdate = nameAndValueNotNullToUpdate.substring(1);
			}

			map.put("nameStr", nameStr);
			map.put("valueStr", valueStr);
			map.put("nameStrNotNull", nameStrNotNull);
			map.put("valueStrNotNull", valueStrNotNull);
			map.put("nameAndValue", nameAndValue);
			map.put("nameAndValueNotNull", nameAndValueNotNull);
			map.put("nameAndValueNotNullToUpdate", nameAndValueNotNullToUpdate);
		} catch (Exception e) {
			throw new ApplicationException("转换失败", e);
		}
		return map;
	}
	
	public static Object getValueClass(Field field,Class<?> objClass,Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String methodName = "";
		String name = "";
		Method method = null;
		/*SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
		if (field != null) {
			name = field.getName();
			methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			method = objClass.getMethod(methodName);
			if (method != null) {
				Object value = method.invoke(obj);
				if (method.getReturnType().toString().equals("class java.lang.Integer")) {
					return (value==null)?null:Integer.parseInt(value.toString())+"";
				} else if (method.getReturnType().toString().equals(
						"class java.util.Date")) {
					return (value==null)?null:(Date) value;
				} else if (method.getReturnType().toString().equals(
						"class java.lang.String")) {
					return (value==null || "".equals(value))?null:value.toString();
				} else if (method.getReturnType().toString().equals(
						"class java.lang.Long")) {
					return (value==null)?null:Long.parseLong(value.toString())+"";
				} else if (method.getReturnType().toString().equals(
						"class java.math.BigDecimal")) {
					return (value==null)?null:new BigDecimal(value.toString())+"";
				} else if (method.getReturnType().toString().equals(
						"class java.lang.Double")) {
					return (value==null)?null:Double.parseDouble(value.toString())+"";
				}
			}
		}
		return null;
	}
	
	/**
	 * 区别于getValueClass，此方法获取String类型的字段不为null的value，可以返回""
	 * @param field
	 * @param objClass
	 * @param obj
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @return Object
	 */
	public static Object getValueClass4NotNullStr(Field field,Class<?> objClass,Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String methodName = "";
		String name = "";
		Method method = null;
		/*SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
		if (field != null) {
			name = field.getName();
			methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			method = objClass.getMethod(methodName);
			if (method != null) {
				Object value = method.invoke(obj);
				if (method.getReturnType().toString().equals("class java.lang.Integer")) {
					return (value==null)?null:Integer.parseInt(value.toString())+"";
				} else if (method.getReturnType().toString().equals(
						"class java.util.Date")) {
					return (value==null)?null:(Date) value;
				} else if (method.getReturnType().toString().equals(
						"class java.lang.String")) {
					return (value==null)?null:value.toString();
				} else if (method.getReturnType().toString().equals(
						"class java.lang.Long")) {
					return (value==null)?null:Long.parseLong(value.toString())+"";
				} else if (method.getReturnType().toString().equals(
						"class java.math.BigDecimal")) {
					return (value==null)?null:new BigDecimal(value.toString())+"";
				} else if (method.getReturnType().toString().equals(
						"class java.lang.Double")) {
					return (value==null)?null:Double.parseDouble(value.toString())+"";
				}
			}
		}
		return null;
	}
	
	public static Map<String, Object> getPreFieldMap4UpdateNotNull(Class objClass,Object obj){
		Boolean hasWhere = false;
		Map<String, Object> map = new HashMap<String, Object>();
		if(obj==null){
			map.put("nameStr","");
			map.put("selectNameStrNotNull","");
			map.put("selectNameStrNotNullAndWhere","");
			map.put("insertNameStr", "");
			map.put("insertNameStrNotNull", "");
			map.put("updateNameStr", "");
			map.put("updateNameStrNotNull","");
			map.put("paramNotNull", "");
			map.put("param", "");
			return map;
		}
		try {
			Field[] fields = objClass.getDeclaredFields();
			Object value = "";
			StringBuilder selectNameStrNotNull = new StringBuilder();
			StringBuilder selectNameStrNotWhere = new StringBuilder();

			StringBuilder nameStr =  new StringBuilder();
			StringBuilder valueStr =  new StringBuilder();

			StringBuilder nameStrNotNull =  new StringBuilder();
			StringBuilder valueStrNotNull =  new StringBuilder();

			StringBuilder updateNameStr =  new StringBuilder();
			StringBuilder updateNameStrNotNull =  new StringBuilder();
			
			List<Object> paramNotNull = new ArrayList<Object>();
			List<Object> param = new ArrayList<Object>();
			for (int i = 0; i < fields.length; i++) {
				
				if (!fields[i].getName().equals("serialVersionUID")) {

					value = getValueClass4NotNullStr(fields[i], objClass, obj);
					nameStr.append(",").append(fields[i].getName());
					valueStr.append(",?");
					updateNameStr.append(",").append(fields[i].getName()).append("=?");
					param.add(value);
					
					if (value!=null) {
						hasWhere = hasWhere(selectNameStrNotNull,hasWhere);
						selectNameStrNotNull.append(fields[i].getName()).append("=?");
						
						selectNameStrNotWhere.append(" and ").append(fields[i].getName()).append("=?");
						
						nameStrNotNull.append(",").append(fields[i].getName());
						valueStrNotNull.append(",?");
						updateNameStrNotNull.append(",").append(fields[i].getName()).append("=?");

						paramNotNull.add(value);
					}
				}
				
			}
			
			if (StringUtil.isNotBlank(nameStr.toString())) {
				nameStr = new StringBuilder(nameStr.substring(1));
			}
			if (StringUtil.isNotBlank(valueStr.toString())) {
				valueStr = new StringBuilder(valueStr.substring(1));
			}
			if (StringUtil.isNotBlank(nameStrNotNull.toString())) {
				nameStrNotNull = new StringBuilder(nameStrNotNull.substring(1));
			}
			if (StringUtil.isNotBlank(valueStrNotNull.toString())) {
				valueStrNotNull = new StringBuilder(valueStrNotNull.substring(1));
			}
			if (StringUtil.isNotBlank(updateNameStr.toString())) {
				updateNameStr = new StringBuilder(updateNameStr.substring(1));
			}
			if (StringUtil.isNotBlank(updateNameStrNotNull.toString())) {
				updateNameStrNotNull = new StringBuilder(updateNameStrNotNull.substring(1));
			}
			map.put("nameStr", nameStr.toString());

			nameStr.append(") values(").append(valueStr).append(")");
			nameStrNotNull.append(") values(").append(valueStrNotNull).append(")");

			map.put("selectNameStrNotNull", selectNameStrNotNull.toString());
			map.put("selectNameStrNotNullAndWhere", selectNameStrNotWhere.toString());
			map.put("insertNameStr", "("+nameStr.toString());
			map.put("insertNameStrNotNull", "("+nameStrNotNull.toString());
			map.put("updateNameStr", updateNameStr.toString());
			map.put("updateNameStrNotNull", updateNameStrNotNull.toString());
			map.put("paramNotNull", paramNotNull);
			map.put("param", param);
		} catch (Exception e) {
			throw new ApplicationException("对象转换失败", e);
		}
		return map;
	}
	
	
	public static Map<String, Object> getPreFieldMap(Class objClass,Object obj){
		Boolean hasWhere = false;
		Map<String, Object> map = new HashMap<String, Object>();
		if(obj==null){
			map.put("nameStr","");
			map.put("selectNameStrNotNull","");
			map.put("selectNameStrNotNullAndWhere","");
			map.put("insertNameStr", "");
			map.put("insertNameStrNotNull", "");
			map.put("updateNameStr", "");
			map.put("updateNameStrNotNull","");
			map.put("paramNotNull", "");
			map.put("param", "");
			return map;
		}
		try {
			Field[] fields = objClass.getDeclaredFields();
			Object value = "";
			StringBuilder selectNameStrNotNull = new StringBuilder();
			StringBuilder selectNameStrNotWhere = new StringBuilder();

			StringBuilder nameStr =  new StringBuilder();
			StringBuilder valueStr =  new StringBuilder();

			StringBuilder nameStrNotNull =  new StringBuilder();
			StringBuilder valueStrNotNull =  new StringBuilder();

			StringBuilder updateNameStr =  new StringBuilder();
			StringBuilder updateNameStrNotNull =  new StringBuilder();
			
			List<Object> paramNotNull = new ArrayList<Object>();
			List<Object> param = new ArrayList<Object>();
			for (int i = 0; i < fields.length; i++) {
				
				if (!fields[i].getName().equals("serialVersionUID")) {

					value = getValueClass(fields[i], objClass, obj);
					nameStr.append(",").append(fields[i].getName());
					valueStr.append(",?");
					updateNameStr.append(",").append(fields[i].getName()).append("=?");
					param.add(value);
					
					if (value!=null) {
						hasWhere = hasWhere(selectNameStrNotNull,hasWhere);
						selectNameStrNotNull.append(fields[i].getName()).append("=?");
						
						selectNameStrNotWhere.append(" and ").append(fields[i].getName()).append("=?");
						
						nameStrNotNull.append(",").append(fields[i].getName());
						valueStrNotNull.append(",?");
						updateNameStrNotNull.append(",").append(fields[i].getName()).append("=?");

						paramNotNull.add(value);
					}
				}
				
			}
			
			if (StringUtil.isNotBlank(nameStr.toString())) {
				nameStr = new StringBuilder(nameStr.substring(1));
			}
			if (StringUtil.isNotBlank(valueStr.toString())) {
				valueStr = new StringBuilder(valueStr.substring(1));
			}
			if (StringUtil.isNotBlank(nameStrNotNull.toString())) {
				nameStrNotNull = new StringBuilder(nameStrNotNull.substring(1));
			}
			if (StringUtil.isNotBlank(valueStrNotNull.toString())) {
				valueStrNotNull = new StringBuilder(valueStrNotNull.substring(1));
			}
			if (StringUtil.isNotBlank(updateNameStr.toString())) {
				updateNameStr = new StringBuilder(updateNameStr.substring(1));
			}
			if (StringUtil.isNotBlank(updateNameStrNotNull.toString())) {
				updateNameStrNotNull = new StringBuilder(updateNameStrNotNull.substring(1));
			}
			map.put("nameStr", nameStr.toString());

			nameStr.append(") values(").append(valueStr).append(")");
			nameStrNotNull.append(") values(").append(valueStrNotNull).append(")");

			map.put("selectNameStrNotNull", selectNameStrNotNull.toString());
			map.put("selectNameStrNotNullAndWhere", selectNameStrNotWhere.toString());
			map.put("insertNameStr", "("+nameStr.toString());
			map.put("insertNameStrNotNull", "("+nameStrNotNull.toString());
			map.put("updateNameStr", updateNameStr.toString());
			map.put("updateNameStrNotNull", updateNameStrNotNull.toString());
			map.put("paramNotNull", paramNotNull);
			map.put("param", param);
		} catch (Exception e) {
			throw new ApplicationException("对象转换失败", e);
		}
		return map;
	}
	
	/**
	 * 暂时只是给his查出其主对象用
	 * @param objClass
	 * @param args
	 * @return Map<String,String>
	 */

	/*public static Map<String, String> getFieldMapWithOutFields(Class objClass,Object obj,String ...withOutFields){
		Map<String, String> map = new HashMap<String, String>();
		try {
			Field[] fields = objClass.getDeclaredFields();
			String nameStr = "";
			for (int i = 0; i < fields.length; i++) {
				boolean match = false;
				for(String withOutFile:withOutFields){
					if(withOutFile.equals(fields[i].getName())){
						match = true;
					}
				}
				if (!fields[i].getName().equals("serialVersionUID")&&!match) {
					nameStr+=","+fields[i].getName();
					//System.out.println(fields[i].getName()+";"+value+";"+(StringUtils.isNotBlank(""))+";"+(StringUtils.isNotBlank(value)&&(!value.equals("NULL"))));
				}
				match = false;
			}
			if (StringUtils.isNotBlank(nameStr)) {
				nameStr = nameStr.substring(1);
			}
			map.put("nameStr", nameStr);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return map;
	}*/
	
	public static void main(String[] args) {
		Customer customer = new Customer();
		customer.setFirRunTime(new Date());
		customer.setId(1l);
		customer.setAddr("addr");
		customer.setUserNo("");
		customer.setPlaceId(12l);
		//System.out.println(getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));
		
		VehicleInfo vehicle = new VehicleInfo();
		vehicle.setId((long)12);
		vehicle.setVehicleHeight(3L);
		vehicle.setVehicleWeightLimits(2L);
		vehicle.setVehiclePlate("");
		
		System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("nameStr"));
		System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("valueStr"));

		System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("nameStrNotNull"));
		System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("valueStrNotNull"));
		
		
		System.out.println(getPreFieldMap4UpdateNotNull(VehicleInfo.class,vehicle).get("updateNameStrNotNull"));
		System.out.println(getPreFieldMap4UpdateNotNull(VehicleInfo.class,vehicle).get("paramNotNull"));
		
		//System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("nameAndValue"));
	//System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("nameAndValueNotNull"));
		//System.out.println(getFieldMap(VehicleInfo.class,vehicle).get("nameAndValueNotNullToUpdate"));
		getActionTime(new Date());
	}
	
	public static Boolean hasWhere(StringBuilder sb,Boolean flag){
		if(!flag){
			sb.append(" where ");
			flag =  true;
		}else{
			sb.append(" and ");
		}
		return flag;
	}
	//储值卡和记帐卡生效时间
    public static Date getActionTime(Date date){
    	
    	 String nowTime = new SimpleDateFormat("HH:MM").format(date); 
    	 int i=0;
         if(DateCompare(nowTime,"16:00","HH:mm")){
        	 i=24;
         }else{
        	 i=24;
         }
         Calendar c = Calendar.getInstance();
         c.setTime(date);
         c.add(Calendar.MINUTE, i*60);
         return c.getTime(); 
    }
	public static boolean DateCompare(String source, String traget, String type) { 

        SimpleDateFormat format = new SimpleDateFormat(type); 

        Date sourcedate=null;
        Date tragetdate=null;
		try {
			sourcedate = format.parse(source);
			tragetdate =format.parse(traget);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return tragetdate.before(sourcedate);

    } 


}
