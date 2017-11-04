package com.hgsoft.httpInterface.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.ServiceParamSetNew;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.utils.StringUtil;

@Repository
public class OmsParamDao extends BaseDao{
	public OMSParam findById(Long id) {
		String sql = "select * from csms_oms_param where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		OMSParam omsParam = null;
		if(list.size()>0){
			omsParam = new OMSParam();
			convert2Bean(list.get(0), omsParam);
		}
		return omsParam;
	}
	
	public List<OMSParam> findFirstLevel(String omsType,String type) {
		String sql = "select * from csms_oms_param where omsType=? and type=? and state='1'";
		List<Map<String,Object>> listMap = queryList(sql,omsType,type);
		
		OMSParam temp = null;
		List<OMSParam> list = null;
		if(listMap.size()>0){
			list = new ArrayList<OMSParam>();
			for(int i=0;i<listMap.size();i++){
				temp = new OMSParam();
				this.convert2Bean(listMap.get(i), temp);
				list.add(temp);
			}
		}
		return list;
	}
	
	public OMSParam findByParamValue(String id) {
		String sql = "select * from csms_oms_param where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		OMSParam omsParam = null;
		if(list.size()>0){
			omsParam = new OMSParam();
			convert2Bean(list.get(0), omsParam);
		}
		return omsParam;
	}
	
	public List<OMSParam> findSecondLevel(Long omsId,String omsType,String type) {
		String sql = "select * from csms_oms_param where paramId=? and omsType=? and type=? and state='1'";
		List<Map<String,Object>> listMap = queryList(sql,omsId,omsType,type);
		
		OMSParam temp = null;
		List<OMSParam> list = null;
		if(listMap.size()>0){
			list = new ArrayList<OMSParam>();
			for(int i=0;i<listMap.size();i++){
				temp = new OMSParam();
				this.convert2Bean(listMap.get(i), temp);
				list.add(temp);
			}
		}
		return list;
	}
	
	public void add(OMSParam omsParam){
		Map map = FieldUtil.getPreFieldMap(OMSParam.class,omsParam);
		StringBuffer sql=new StringBuffer("insert into CSMS_OMS_PARAM");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	public void delete(Long omsId,String omsType){
		String sql = "delete from csms_oms_param where omsId=? and omsType=?";
		delete(sql,omsId,omsType);
	}
	public void update(Long omsId,String omsType,Long paramId,String paramValue,String type,String state,Long operId,String operNo,String operName,String operTime,String memo){
		StringBuffer sql = new StringBuffer("update csms_oms_param op set op.operId=?,op.operNo=?,op.operName=?,op.operTime=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
		
		List<String> list=new ArrayList<String>();
		list.add(String.valueOf(operId));
		list.add(operNo);
		list.add(operName);
		list.add(operTime);
		if(paramId!=null){
			sql.append(",op.paramId=?");
			list.add(String.valueOf(paramId));
		}
		if(StringUtil.isNotBlank(paramValue)){
			sql.append(",op.paramValue=?");
			list.add(paramValue);
		}
		if(StringUtil.isNotBlank(type)){
			sql.append(",op.type=?");
			list.add(type);
		}
		if(StringUtil.isNotBlank(state)){
			sql.append(",op.state=?");
			list.add(state);
		}
		if(StringUtil.isNotBlank(memo)){
			sql.append(",op.memo=?");
			list.add(memo);
		}
		sql=sql.append(" where op.omsId=? and op.omsType=?");
		list.add(String.valueOf(omsId));
		list.add(omsType);
		saveOrUpdate(sql.toString(),list.toArray());
		
	}
	
	public List<OMSParam> findStopReason(String omsType,String type) {
		String sql = "select * from csms_oms_param where state ='1' and omsType=? and type=? or type = 3 ";
		List<Map<String,Object>> listMap = queryList(sql,omsType,type);
		
		OMSParam temp = null;
		List<OMSParam> list = null;
		if(listMap.size()>0){
			list = new ArrayList<OMSParam>();
			for(int i=0;i<listMap.size();i++){
				temp = new OMSParam();
				this.convert2Bean(listMap.get(i), temp);
				list.add(temp);
			}
		}
		return list;
	}
	
	public ServiceParamSetNew findOmsParam(String key) {
		String sql = "select * from OMS_SERVICEPARAMSET_NEW where key=? and status='1' ";
		List<Map<String, Object>> list = queryList(sql,key);
		ServiceParamSetNew serviceParamSetNew = null;
		if (!list.isEmpty()) {
			serviceParamSetNew = new ServiceParamSetNew();
			this.convert2Bean(list.get(0), serviceParamSetNew);
		}
		return serviceParamSetNew;
	}
}
