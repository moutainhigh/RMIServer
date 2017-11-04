package com.hgsoft.httpInterface.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.httpInterface.entity.CustomerModifyApply;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class CustomerModifyApplyDao extends BaseDao{
	public CustomerModifyApply findById(Long id) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_customerModify_apply where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		CustomerModifyApply customerModifyApply = null;
		if(list.size()>0){
			customerModifyApply = new CustomerModifyApply();
			convert2Bean(list.get(0), customerModifyApply);
		}
		return customerModifyApply;
	}
	
	public void save(Long id,Customer oldCustomer, Customer newCustomer,String oldpath,String newpath,Date createTime){
		String sql = "insert into csms_customermodify_apply(id,customerId,oldOrgan,newOrgan,oldIdType,newIdType,oldIdCode,newIdCode,oldPicAddr,newPicAddr,appState,createTime) values(?,?,?,?,?,?,?,?,?,?,'1',?)";
		saveOrUpdate(sql, id,newCustomer.getId(),oldCustomer.getOrgan(),newCustomer.getOrgan(),oldCustomer.getIdType(),newCustomer.getIdType(),oldCustomer.getIdCode(),newCustomer.getIdCode(),oldpath,newpath,createTime);
	}
	/**
	 * 获取未审核记录
	 * @param id
	 * @return void
	 * @author lgm
	 * @date 2017年3月16日
	 */
	public boolean hasApproval(Long id){
		String sql = "select * from csms_customerModify_apply where appState='1' and customerId=?";
		List<Map<String,Object>> list = queryList(sql,id);
		return list.size()>0;
	}
	
	public Pager list(Pager pager, String oldOrgan, String newOrgan,String oldIdType, String newIdType, String oldIdCode,String newIdCode,String appState,String approverName,String appTime,String createTime){
		StringBuffer sql = new StringBuffer("select id,oldOrgan, newOrgan, oldIdType, newIdType, oldIdCode, newIdCode, appState, approverName, to_char(appTime,'yyyy-MM-dd hh24:mi:ss') appTime ,to_char(createTime,'yyyy-MM-dd hh24:mi:ss') createTime from csms_customermodify_apply where 1=1");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(oldOrgan)){
			sqlp.eq("oldOrgan", oldOrgan);
		}
		if(StringUtil.isNotBlank(newOrgan)){
			sqlp.eq("newOrgan", newOrgan);
		}
		if(StringUtil.isNotBlank(oldIdType)){
			sqlp.eq("oldIdType", oldIdType);
		}
		if(StringUtil.isNotBlank(newIdType)){
			sqlp.eq("newIdType", newIdType);
		}
		if(StringUtil.isNotBlank(oldIdCode)){
			sqlp.eq("oldIdCode", oldIdCode);
		}
		if(StringUtil.isNotBlank(newIdCode)){
			sqlp.eq("newIdCode", newIdCode);
		}
		if(StringUtil.isNotBlank(appState)){
			sqlp.eq("appState", appState);
		}
		if(StringUtil.isNotBlank(approverName)){
			sqlp.eq("approverName", approverName);
		}
		if(StringUtil.isNotBlank(appTime)){
			sqlp.eq("to_char(appTime,'yyyy-MM-dd hh24:mi:ss')", appTime);
		}
		if(StringUtil.isNotBlank(createTime)){
			sqlp.eq("to_char(createTime,'yyyy-MM-dd hh24:mi:ss')", createTime);
		}
		sql=sql.append(sqlp.getParam());
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		
	}
	public Map<String,Object> detailList(Long id,String rootPath){
		String sql ="select '"+rootPath +"' rootPath,";
		sql = sql + "id,customerId,oldOrgan,newOrgan,oldIdType,newIdType,oldIdCode,newIdCode,oldPicAddr,newPicAddr,appState,approverId,approverNo,approverName,to_char(appTime,'yyyy-mm-dd hh24:mi:ss') appTime,to_char(createTime,'yyyy-mm-dd hh24:mi:ss') createTime from csms_customermodify_apply where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		if(list.size()>0)
			return list.get(0);
		return null;
		
	}
	public void approval(Long id,String appState,Long approverId,String approverNo,String approverName,Date appTime){
		String sql = "update csms_customermodify_apply set appstate=?,approverId=?,approverNo=?,approverName=?,appTime=? where id=?";
		saveOrUpdate(sql, appState,approverId,approverNo,approverName,appTime,id);
		
	}
	
	/**
	 * 判断是否存在新证件类型+证件号码待审批
	 * @param idCode
	 * @param idType
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @return CustomerModifyApply
	 */
	public CustomerModifyApply findByTypeCode(String idCode,String idType) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select "+FieldUtil.getFieldMap(CustomerModifyApply.class,new CustomerModifyApply()).get("nameStr")+" from csms_customerModify_apply where newIdCode=? and newIdtype=? and AppState='1' ";
		List<Map<String,Object>> list = queryList(sql,idCode,idType);
		CustomerModifyApply customerModifyApply = null;
		if(list.size()>0){
			customerModifyApply = new CustomerModifyApply();
			convert2Bean(list.get(0), customerModifyApply);
		}
		return customerModifyApply;
	}
	
	public void saveCustomerModifyApply(CustomerModifyApply customerModifyApply) {
		Map map = FieldUtil.getPreFieldMap(CustomerModifyApply.class, customerModifyApply);
		StringBuffer sql = new StringBuffer("insert into csms_customerModify_apply");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
