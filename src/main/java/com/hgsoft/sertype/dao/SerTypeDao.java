package com.hgsoft.sertype.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;

/**
 * Created by wiki on 2017/6/14.
 */
@Repository
public class SerTypeDao extends BaseDao {

    public List<Map<String, Object>> findAll(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        StringBuffer sql = new StringBuffer("select serType as \"value\",serName as \"name\" from csms_sertype");
        list = queryList(sql.toString());
        return list;
    }
    
    public List<Map<String, Object>> findAllByIn(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //StringBuffer sql = new StringBuffer("select serType as \"value\",serName as \"name\" from csms_sertype");
        StringBuffer sql = new StringBuffer("SELECT serType AS value,serName AS name FROM csms_sertype WHERE serType in ('201', '206', '208', '215', '216',  '222', '223',  '301', '303', '312', '406', '407', '501', '502', '504', '509', '511', '519', '520')");
        list = queryList(sql.toString());
        return list;
    }

	public List<Map<String, Object>> findProductTypes() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        StringBuffer sql = new StringBuffer("select id,code as value , name  from OMS_productType where layer = '2' ORDER BY VALUE");
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findProductName() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        StringBuffer sql = new StringBuffer("SELECT id,code as value , name from OMS_productInfo order by id ");
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findSupplier() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        StringBuffer sql = new StringBuffer("SELECT id as value , name from OMS_supplier order by id");
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findProductNameFind(String type) {
		String sql = "SELECT code as value ,name  from OMS_productInfo a where a.producttype = (SELECT id from OMS_productType where code = ? ) order by id   " ;
		if (type.equals("all")){
			return findProductName();
		}
		return queryList(sql,type);
	}

	/**
	 * 获取产品类型
	 * @param type id/code
	 * @return
	 */
	public List<Map<String,Object>> findProductType(){
    	return queryList("select id,code,name from OMS_productType where layer = '2'");
	}

	/**
	 * 获取产品名称
	 * @param id 产品类型id
	 * @return
	 */
	public List<Map<String,Object>> findProductinfoByTypeId(String id){
		return queryList("select id,code,name,productType from oms_productinfo where usestate = '1' and productType = ? ",id);
	}

	public List<Map<String, Object>> findAllStockPlaceList() {
		
		String sql="select id,name from OMS_STOCKPLACE c where 1=1 ";
	    sql += " start with c.id in(select c.id from OMS_STOCKPLACE c where c.layer=1) connect by prior c.id=c.parent";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findAllProductTypeList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select id,name from oms_productType c start with c.id in(select c.id from oms_productType c where c.layer=1) connect by prior c.id=c.parent";
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findAllProductInfoList() {
		String sql = "select id,name from OMS_PRODUCTINFO order by id ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findAllByState(String state) {
		String sql = "";
		if (!"0".equals(state)) {// "0"时查全称，1正常，2停用
			sql = "select * from OMS_REFUNDWAY where state =" + state + " order by id ";
		} else {
			sql = "select * from OMS_REFUNDWAY order by id ";
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = queryList(sql.toString());
        return list;
	}

	public List<Map<String, Object>> findAllByPlacenoCode(String placeno) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//String sql = "SELECT c. ID,c.code,c. NAME FROM OMS_CUSTOMPOINT c WHERE c.USESTATE = '1' START WITH c. ID = ? CONNECT BY PRIOR c. ID = c. PARENT";
		String sql = " select max(sys_connect_by_path(id,',')) as IDSTR from ( SELECT rownum ro,c. ID,c.code,	c. NAME FROM OMS_CUSTOMPOINT c WHERE c.USESTATE = '1' START WITH c. code = ? CONNECT BY PRIOR c. ID = c. PARENT ) newtab start with newtab.ro =1 connect by prior newtab.ro = newtab.ro - 1 ";
		list = queryList(sql.toString(),placeno);
		return list;
	}

	public Map<String, Object> findPointById(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//String sql = "SELECT c. ID,c.code,c. NAME FROM OMS_CUSTOMPOINT c WHERE c.USESTATE = '1' START WITH c. ID = ? CONNECT BY PRIOR c. ID = c. PARENT";
		String sql = " SELECT a.id pointid,a.code pointcode,a.name pointname,b.id typeid,b.name typename,c.code  areacode,c.name  areaname from OMS_customPoint a left JOIN  oms_customPointType b on a.customPointType = b.id left join OMS_Area c on a.Area = c.code where a.id = ? ";
		list = queryList(sql.toString(),id);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	public List<Map<String, Object>> findCusPointTreeByIdTwo() {
			String sql ="select c.id,c.code,c.CUSTOMPOINTTYPE,c.NAME,c.TERMINALTYPE,c.CITY,c.AREA,c.PHONE,c.FAX,c.ADDRESS,c.STATUS,c.BUSINESS,c.OPENTIME,c.REMARK,c.USESTATE,c.TREEEND,c.PARENT,c.CREATEID,c.CREATETIME,c.LAYER,c.STOCKPLACE,c.STOCKNAME,c.BANKCODE "
					+"from OMS_CUSTOMPOINT c WHERE c.USESTATE='1' start with c.id = 2 "
					+" connect by PRIOR c.ID=c.Parent";
			return queryList(sql);
		}

	public List<Map<String, Object>> findSysAdminListByPointID(String string) {
		String sql = "SELECT id,staffno,username from uums_ADMIN where usestate='1' and customPointType = '1'  and custompoint = (SELECT parent from oms_custompoint where id = ?)";
		return queryList(sql,string);
	}

	public List<Map<String, Object>> findProductNameFindByID(String type) {
		String sql = "SELECT id,code as value ,name  from OMS_productInfo a where a.producttype =  ?  order by id   " ;
		if (type.equals("all")){
			return findProductName();
		}
		return queryList(sql,type);
	}


	public List<Map<String, Object>> findStockNameList(String string) {
		String sql = "SELECT id,stockplace,stockname from oms_custompoint where id=?";
		return queryList(sql,string);
	}

	/**
	 * 根据网点id 找营业部下所有网点
	 */
	public List<Map<String, Object>> setFindAllPointByPlaceID(String string) {
		String sql = " SELECT A . ID pointid, A .code pointcode, A . NAME pointname, b. ID typeid,b. NAME typename,c.code areacode,c. NAME areaname FROM OMS_customPoint A LEFT JOIN oms_customPointType b ON A .customPointType = b. ID LEFT JOIN OMS_Area c ON A .Area = c.code WHERE A . parent = ( SELECT parent from OMS_customPoint where id = ? ) ";
		return queryList(sql.toString(),string);
	}

	public List<Map<String, Object>> setFindCusPointByPlaceID(String string) {
		String sql = " SELECT A . ID pointid, A .code pointcode, A . NAME pointname FROM OMS_customPoint A  WHERE A . id = ( SELECT parent from OMS_customPoint where id = ? ) ";
		return queryList(sql.toString(),string);
	}

	public List<Map<String, Object>> findPointListByTypeIdAndAreaCode(
			String pointId, String type, String area) {
		String sql = "SELECT A . ID pointid, A .code pointcode, A . NAME pointname, b. ID typeid,b. NAME typename,c.code areacode,c. NAME areaname FROM OMS_customPoint A LEFT JOIN oms_customPointType b ON A .customPointType = b. ID LEFT JOIN OMS_Area c ON A .Area = c.code WHERE A . parent = ( SELECT parent from OMS_customPoint where id = ? ) ";
		if(type!=null && type.length()!=0){
			sql=sql+" and b. ID ="+type;
		}
		if(area!=null && area.length()!=0){
			sql=sql+" and c.code="+"'"+area+"'";
		}
		
		return queryList(sql.toString(),pointId);
	}

	public List<Map<String, Object>> findAgentPointList(String id, String layer) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(""!=layer && layer !=null ){
			String sql = " SELECT c. ID,	c.code,	c.CUSTOMPOINTTYPE,	c. NAME,	c.CITY,	c.AREA,	c.USESTATE,c. PARENT,	c. LAYER FROM OMS_CUSTOMPOINT c WHERE c.layer  = ?  AND c.USESTATE = '1' START WITH c. ID = ? CONNECT BY PRIOR c. ID = c. PARENT  ";
			if(id !=null && ""!=id){
				list = queryList(sql.toString(),layer,id);
				return list;
			}else{
				list = queryList(sql.toString(),layer,"2");
				return list;
			}
		}
		return null;
	}
	
	public List<Map<String, Object>> findPointByPointIDAndLayer(String layer, String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(id !=null && ""!=id){
			String sql = " SELECT c. ID,c.code,	c.CUSTOMPOINTTYPE,c. NAME,c.CITY,	c.AREA,	c.USESTATE,c. PARENT,	c. LAYER  from oms_custompoint c where 1=1 ";
			if(layer!=null && layer.length()!=0){
				sql = sql + " and layer ="+layer;
			}
			sql = sql+ " START with id =? connect by  c.id = PRIOR c. parent ORDER BY layer";

			return queryList(sql.toString(),id);
		}else {
			return null;
		}
	}

    public List<Map<String,Object>> findSysAdminList() {
        String sql = "select * from UUMS_ADMIN where usestate='1'";
        return queryList(sql);
    }

	public int findLayerByPointId(String pointId) {
		 String sql = "select layer from oms_custompoint where id = ?";
		 int queryCount = queryCount(sql, pointId);
		 return queryCount;
	}

	public List<Map<String, Object>> findLessLayerByLayerAndPointID(int layer,
			String pointId) {
		String sql = "SELECT c. ID,	c.code,c.CUSTOMPOINTTYPE,c. NAME,c.CITY,c.AREA,c.USESTATE,c. PARENT,c. LAYER FROM oms_custompoint c WHERE 1 = 1 and UseState =1 and layer <=? START with id =? connect by  c.id = PRIOR c. parent ORDER BY layer";
        return queryList(sql,String.valueOf(layer),pointId);
	}

	public Map<String, Object> findPointByPointIDAndLayerTO(String layer,
			String pointId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = " SELECT c. ID,c.code,	c.CUSTOMPOINTTYPE,c. NAME,c.CITY,	c.AREA,	c.USESTATE,c. PARENT,	c. LAYER  from oms_custompoint c where 1=1  and layer =? START with id =? connect by  c.id = PRIOR c. parent ORDER BY layer";
		list = queryList(sql.toString(),layer,pointId);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<Map<String, Object>> setFindCusPointAndParentByPlaceID(
			String string) {
		//SELECT  A . ID pointid, A .code pointcode, A . NAME pointname,a.layer  FROM OMS_customPoint A START with id =(SELECT parent from OMS_customPoint where id = '284') CONNECT by PRIOR a.id =  a.parent
		String sql = "SELECT  A . ID pointid, A .code pointcode, A . NAME pointname,a.layer  FROM OMS_customPoint A START with id =(SELECT parent from OMS_customPoint where id = ?) CONNECT by PRIOR a.id =  a.parent";
        return queryList(sql,string);
	}
}

