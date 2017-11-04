package com.hgsoft.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SqlParamer {

	private List list = new ArrayList();
	private StringBuffer param = new StringBuffer("");
	private StringBuffer oderParam = new StringBuffer("");
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private SimpleDateFormat formatEnd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
	
	
	public SimpleDateFormat getFormat() {
		return format;
	}
	
	public SimpleDateFormat getFormatEnd() {
		return formatEnd;
	}
	
	public SqlParamer(){
	}
	
	public void eq(String name, Object object) {
		param.append(" and ").append(name).append(" = ? ");
		list.add(object);
	}
	
	/**
	 * 判断年月相等
	 * @param name  
	 * @param object  YYYYMM
	 * @return void
	 */
	public void eqYearMonth(String name, Object object) {
		param.append(" and ").append("to_char(").append(name).append(",'yyyymm') +  = ? ");
		list.add(object);
	}
	
	/**
	 * 判断年月日相等
	 * @param name  
	 * @param object  YYYYMMDD
	 * @return void
	 */
	public void eqYearMonthDay(String name, Object object) {
		param.append(" and ").append("to_char(")
				.append(name).append(",'yyyymmdd') = ? ");
		list.add(object);
	}

	public void ne(String name, Object object) {
		param.append(" and ").append(name).append(" != ? ");
		list.add(object);
	}

	public void ge(String name, Object object) {
		param.append(" and ").append(name).append(" >= ? ");
		list.add(object);
	}
	
	public void geDate(String name, Object object) {
		param.append(" and ").append(name).append(" >= to_date(?,'YYYY-MM-DD HH24:MI:SS') ");
		list.add(object);
	}

	public void gt(String name, Object object) {
		param.append(" and ").append(name).append(" > ? ");
		list.add(object);
	}

	public void lt(String name, Object object) {
		param.append(" and ").append(name).append(" < ? ");
		list.add(object);
	}

	public void le(String name, Object object) {
		param.append(" and ").append(name).append(" <= ? ");
		list.add(object);
	}
	
	public void leDate(String name, Object object) {
		param.append(" and ").append(name).append(" <= to_date(?,'YYYY-MM-DD HH24:MI:SS') ");
		list.add(object);
	}
	
	public void like(String name,Object object){
		param.append(" and ").append(name).append(" like ? ");
		//模糊查询需要添加%
		list.add("%"+object+"%");
	}
	
	public void in(String name,List ins){
		param.append(" and ").append(name).append(" in ( ? ");
		
		for(int i=1;i<ins.size();i++){
			param.append(" , ? ");
		}
		param.append(" ) ");
		list.addAll(ins);
	}
	
	public void notIn(String name,List ins){
		param.append(" and ").append(name).append(" not in ( ? ");
		for(int i=1;i<ins.size();i++){
			param.append(" , ? ");
		}

		param.append(" ) ");
		list.addAll(ins);
	}
	
	public void oder(String name,String oder){
		oderParam.append(", ").append(name).append(" " + oder);
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getParam() {
		return param.toString();
	}

	public void setParam(String param) {
		this.param = new StringBuffer(param);
	}
	public String getOderParam() {
		return oderParam.toString();
	}
	public void setOderParam(String oderParam) {
		this.oderParam = new StringBuffer(oderParam);
	}
	
}
