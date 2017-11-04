package com.hgsoft.system.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.system.entity.CusPointTreeEnd;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Repository
public class CusPointTreeEndDao extends BaseDao{
	public int[] batchSaveCusPointTreeEnd(final List<CusPointTreeEnd> list) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sql = "insert into OMS_CUSTOMPOINT(id,code,custompointtype,name,terminaltype,city,"
        		+ "area,phone,fax,address,status,business,opentime,remark,usestate,treeend,parent,"
        		+ "createid,createtime,layer,stockplace,stockname,bankCode) "
        		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				CusPointTreeEnd cusPointTreeEnd = list.get(i);
				if(cusPointTreeEnd.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, cusPointTreeEnd.getId());
				if(cusPointTreeEnd.getCode()==null)ps.setNull(2, Types.VARCHAR);else ps.setString(2, cusPointTreeEnd.getCode());
				if((cusPointTreeEnd.getCustomPointType()+"").equals(""))ps.setNull(1, Types.BIGINT);else ps.setLong(3, cusPointTreeEnd.getCustomPointType());
				if(cusPointTreeEnd.getName()==null)ps.setNull(4, Types.VARCHAR);else ps.setString(4, cusPointTreeEnd.getName());
				if(cusPointTreeEnd.getTerminalType()==null)ps.setNull(5, Types.VARCHAR);else ps.setString(5, cusPointTreeEnd.getTerminalType());
				if(cusPointTreeEnd.getCity()==null)ps.setNull(6, Types.VARCHAR);else ps.setString(6, cusPointTreeEnd.getCity());
				if(cusPointTreeEnd.getArea()==null)ps.setNull(7, Types.VARCHAR);else ps.setString(7, cusPointTreeEnd.getArea());
				if(cusPointTreeEnd.getPhone()==null)ps.setNull(8, Types.VARCHAR);else ps.setString(8, cusPointTreeEnd.getPhone());
				if(cusPointTreeEnd.getFax()==null)ps.setNull(9, Types.VARCHAR);else ps.setString(9, cusPointTreeEnd.getFax());
				if(cusPointTreeEnd.getAddress()==null)ps.setNull(10, Types.VARCHAR);else ps.setString(10, cusPointTreeEnd.getAddress());
				if(cusPointTreeEnd.getStatus()==null)ps.setNull(11, Types.VARCHAR);else ps.setString(11, cusPointTreeEnd.getStatus());
				if(cusPointTreeEnd.getBusiness()==null)ps.setNull(12, Types.VARCHAR);else ps.setString(12, cusPointTreeEnd.getBusiness());
				if(cusPointTreeEnd.getOpenTime()==null)ps.setNull(13, Types.VARCHAR);else ps.setString(13, cusPointTreeEnd.getOpenTime());
				if(cusPointTreeEnd.getRemark()==null)ps.setNull(14, Types.VARCHAR);else ps.setString(14, cusPointTreeEnd.getRemark());
				if(cusPointTreeEnd.getUseState()==null)ps.setNull(15, Types.VARCHAR);else ps.setString(15, cusPointTreeEnd.getUseState());
				if(cusPointTreeEnd.getTreeEnd()==null)ps.setNull(16, Types.VARCHAR);else ps.setString(16, cusPointTreeEnd.getTreeEnd());
				if((cusPointTreeEnd.getParent()+"").equals(""))ps.setNull(1, Types.BIGINT);else ps.setLong(17, cusPointTreeEnd.getParent());
				if(cusPointTreeEnd.getCreateID()==null)ps.setNull(18, Types.BIGINT);else ps.setLong(18, cusPointTreeEnd.getCreateID());
				if(cusPointTreeEnd.getCreateTime()==null)ps.setNull(19, Types.DATE);else ps.setTimestamp(19, new java.sql.Timestamp(cusPointTreeEnd.getCreateTime().getTime()));
				if((cusPointTreeEnd.getLayer()+"").equals(""))ps.setNull(1, Types.BIGINT);else ps.setLong(20, cusPointTreeEnd.getLayer());
				if((cusPointTreeEnd.getStockPlace()+"").equals(""))ps.setNull(1, Types.BIGINT);else ps.setLong(21, cusPointTreeEnd.getStockPlace());
				if(cusPointTreeEnd.getStockName()==null)ps.setNull(22, Types.VARCHAR);else ps.setString(22, cusPointTreeEnd.getStockName());
				if(cusPointTreeEnd.getBankCode()==null)ps.setNull(23, Types.BIGINT);else ps.setLong(23, cusPointTreeEnd.getBankCode());
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }

	/**
	 * 找所有网点
	 * @return
	 */
	public List<Map<String, Object>> findCusPointTreeEnds(){
		String sql ="select c.id,c.code,c.CUSTOMPOINTTYPE,c.NAME,c.TERMINALTYPE,c.CITY,c.AREA,c.PHONE,c.FAX,c.ADDRESS,c.STATUS,c.BUSINESS,c.OPENTIME,c.REMARK,c.USESTATE,c.TREEEND,c.PARENT,c.CREATEID,c.CREATETIME,c.LAYER,c.STOCKPLACE,c.STOCKNAME,c.BANKCODE "
				+"from OMS_CUSTOMPOINT c WHERE c.USESTATE='1' start with c.id in"
				+"(select d.id from OMS_CUSTOMPOINT d where d.layer=1) connect by PRIOR c.ID=c.Parent";
		return queryList(sql);
	}

	public List<Map<String, Object>> findCusPointTreeByPointType(String type,String area){
		String sql = "select c.id,c.code,c.CUSTOMPOINTTYPE,c.NAME,c.TERMINALTYPE,c.CITY,c.AREA,c.PHONE,c.FAX,c.ADDRESS,c.STATUS,c.BUSINESS,c.OPENTIME,c.REMARK,c.USESTATE,c.TREEEND,c.PARENT,c.CREATEID,c.CREATETIME,c.LAYER,c.STOCKPLACE,c.STOCKNAME,c.BANKCODE  "
				+"from OMS_CUSTOMPOINT c where c.usestate='1'";
		if(type!=null && type.length()!=0){
			sql=sql+" and c.customPointType="+type;
		}
		if(area!=null && area.length()!=0){
			sql=sql+" and c.area="+"'"+area+"'";
		}
		sql=sql+"start with c.id in (select d.id from OMS_CUSTOMPOINT d where d.layer=1) connect by PRIOR c.ID=c.Parent";
		return queryList(sql);
	}

	public List<Map<String, Object>> findCusPointAreaByPointCode(String code){
		String sql = "select area,customPointType from OMS_CUSTOMPOINT where usestate='1'";
		if(code!=null && code.length()!=0){
			sql=sql+" and code="+"'"+code+"'";
		}
//		sql=sql+"start with c.id in (select d.id from OMS_CUSTOMPOINT d where d.layer=1) connect by PRIOR c.ID=c.Parent";
		return queryList(sql);
	}

//	public List<Map<String, Object>> findCusPointTreeByPointType(String type,String area){
//		String sql = "select code,name from OMS_CUSTOMPOINT where usestate='1'";
//		if(type!=null && type.length()!=0){
//			sql=sql+" and customPointType="+type;
//		}
//		if(area!=null && area.length()!=0){
//			sql=sql+" and area="+"'"+area+"'";
//		}
//		return queryList(sql);
//	}
//
//
//	public List<Map<String, Object>> findCusPointAreaByPointCode(String code){
//		String sql = "select area,customPointType from OMS_CUSTOMPOINT where usestate='1'";
//		if(code!=null && code.length()!=0){
//			sql=sql+" and code="+"'"+code+"'";
//		}
//		return queryList(sql);
//	}
	public void deleteAll(){
		String sql = "delete from OMS_CUSTOMPOINT";
		delete(sql);
	}
	/**
	 * 找登陆用户所在网点
	 * @return
	 */
	public Map<String, Object> findCusPointTreeEnds(String id){
		String sql = "select * from OMS_CUSTOMPOINT where usestate='1' and id=?";
		List list = queryList(sql,id);
		try {
			if(list.size()==1)
			return (Map<String, Object>) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
/**
 * 查找所有自营下的网点
 * @return
 */

	public List<Map<String, Object>> findCusPointTreeByIdOne() {
		String sql ="select c.id,c.code,c.CUSTOMPOINTTYPE,c.NAME,c.TERMINALTYPE,c.CITY,c.AREA,c.PHONE,c.FAX,c.ADDRESS,c.STATUS,c.BUSINESS,c.OPENTIME,c.REMARK,c.USESTATE,c.TREEEND,c.PARENT,c.CREATEID,c.CREATETIME,c.LAYER,c.STOCKPLACE,c.STOCKNAME,c.BANKCODE "
				+"from OMS_CUSTOMPOINT c WHERE c.USESTATE='1' start with c.id = 1 "
				+" connect by PRIOR c.ID=c.Parent";
		return queryList(sql);
	}

	/**
	 * 查找自营网点
	 * @param type
	 * @param area
	 * @return
	 */
public List<Map<String, Object>> pointTypeChangeByIdOne(String type, String area) {
	String sql = "select c.id,c.code,c.CUSTOMPOINTTYPE,c.NAME,c.TERMINALTYPE,c.CITY,c.AREA,c.PHONE,c.FAX,c.ADDRESS,c.STATUS,c.BUSINESS,c.OPENTIME,c.REMARK,c.USESTATE,c.TREEEND,c.PARENT,c.CREATEID,c.CREATETIME,c.LAYER,c.STOCKPLACE,c.STOCKNAME,c.BANKCODE  "
			+"from OMS_CUSTOMPOINT c where c.usestate='1'";
	if(type!=null && type.length()!=0){
		sql=sql+" and c.customPointType="+type;
	}
	if(area!=null && area.length()!=0){
		sql=sql+" and c.area="+"'"+area+"'";
	}
	sql=sql+"start with c.id = 1 connect by PRIOR c.ID=c.Parent";
	return queryList(sql);
}

	/**
	 *
	 * @param treeEnd 1层级、2网点、null为全部
	 * @param useState 1正常、2停用、null为全部
	 * @param customPointType 1自营、2代理、null为全部
	 * @return List<CustomPoint>
	 */
	/*@SuppressWarnings("unchecked")
	public List<CustomPoint> findAllByTreeEnd(String treeEnd, String useState,String customPointType) {
		StringBuffer sql = new StringBuffer("select * from OMS_CUSTOMPOINT c where 1=1");
		if (StringUtil.isNotBlank(treeEnd)) {
			sql.append("and c.treeend ='"+treeEnd+"'");
		}
		if (StringUtil.isNotBlank(useState)) {
			sql.append("and c.usestate ='"+useState+"'");
		}
		if (!"1".equals(treeEnd)) { //查网点时，不需要这个
			sql.append("start with c.id in(select d.id from OMS_CUSTOMPOINT d where d.layer=1");
			if(StringUtil.isNotBlank(customPointType)){
				String code ="1".equals(customPointType)?"A00000001":"B00000001";
				sql.append("and d.code='"+code+"'");
			}
			sql.append(") connect by prior c.id=c.parent");
		}
		List<Object> list = jdbcUtil.selectForList(sql.toString());
		List<CustomPoint> customPointList = new ArrayList<CustomPoint>();
		for (int i = 0; i < list.size(); i++) {
			CustomPoint customPoint = null;
			try {
				customPoint = (CustomPoint) convert2Bean((Map<String, Object>) list.get(i),new CustomPoint());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			customPointList.add(customPoint);
		}
		return customPointList;
	}*/

}
