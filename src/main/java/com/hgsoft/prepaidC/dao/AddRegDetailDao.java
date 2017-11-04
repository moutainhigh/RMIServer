package com.hgsoft.prepaidC.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class AddRegDetailDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;
	
	public void deleteByAddRegId(Long addRegId){
		String sql = "delete from csms_add_reg_detail where addRegId=?";
		delete(sql, addRegId);
	}
	
	public void save(AddRegDetail addRegDetail) {
		/*StringBuffer sql=new StringBuffer("insert into csms_add_reg_detail(");
		sql.append(FieldUtil.getFieldMap(AddRegDetail.class,addRegDetail).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AddRegDetail.class,addRegDetail).get("valueStr")+")");
		save(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(AddRegDetail.class,addRegDetail);
		StringBuffer sql=new StringBuffer("insert into csms_add_reg_detail");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AddRegDetail findByCardNo(String cardNo,String flag) {
		String sql = "select * from csms_ADD_REG_DETAIL where cardNo=? and flag=? order by id";
		List<Map<String, Object>> list = queryList(sql, cardNo, flag);
		AddRegDetail addRegDetail = null;
		if (!list.isEmpty()) {
			addRegDetail = new AddRegDetail();
			this.convert2Bean(list.get(0), addRegDetail);
		}

		return addRegDetail;
	}
	
	public Integer getAddRegCount(String cardNo,String flag) {
		String sql = "select COUNT(1) from CSMS_ADD_REG_DETAIL where cardNo=? and flag=?";
		int count = this.count(sql, cardNo,flag);
		return count;
	}
	
	public AddRegDetail findById(Long id) {
		String sql = "select * from CSMS_Add_Reg_detail where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		AddRegDetail addRegDetail = null;
		if (!list.isEmpty()) {
			addRegDetail = new AddRegDetail();
			this.convert2Bean(list.get(0), addRegDetail);
		}

		return addRegDetail;
	}
	
	public int update(AddRegDetail addRegDetail) {
		/*StringBuffer sql=new StringBuffer("update CSMS_ADD_REG_detail set ");
		sql.append(FieldUtil.getFieldMap(AddRegDetail.class,addRegDetail).get("nameAndValue")+" where id="+addRegDetail.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(AddRegDetail.class, addRegDetail);
		StringBuffer sql=new StringBuffer("update CSMS_ADD_REG_detail set ");
		sql.append(map.get("updateNameStr") +" where id = ? and flag='0'");
		return saveOrUpdate(sql.toString(), (List) map.get("param"),addRegDetail.getId());
	}
	public void updateByBussinessID(String flag,Long bussinessID){
		StringBuffer sql = new StringBuffer("update CSMS_ADD_REG_detail set flag=? ");
		if("0".equals(flag)){
			sql.append(",addtime=null ");
		}
		sql.append(" where bussinessID=?");
		saveOrUpdate(sql.toString(),flag,bussinessID);
	}
	
	public Pager findDetailByAddRegID(Pager pager, Long addRegID) {
		StringBuffer sql = new StringBuffer(
				"SELECT de.addRegId,de.id,de.cardNo,de.fee,de.flag,ar.RegistrationTime,de.opername,de.operTime,de.addtime,ROWNUM AS num FROM CSMS_ADD_REG_DETAIL de join csms_add_reg ar on de.addregid=ar.id WHERE de.addRegID =?");
		sql.append("  order by de.id");
		return this.findByPages(sql.toString(), pager, new Object[] {addRegID});
	}
	
	public List<AddRegDetail> findDetailByRegID(Long addRegID) {
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM CSMS_ADD_REG_DETAIL WHERE addRegID =?");
		sql.append(" and flag='0' order by id");
		List<Map<String, Object>> list = this.queryList(sql.toString(), addRegID);
		List<AddRegDetail> list1 = null;
		if (!list.isEmpty()) {
			list1 = new ArrayList<AddRegDetail>();
			for (int i = 0; i < list.size(); i++) {
				AddRegDetail addRegDetail = new AddRegDetail();
				this.convert2Bean(list.get(i), addRegDetail);
				list1.add(addRegDetail);
			}

		}

		return list1;
	}


	public int updateFlag(AddRegDetail addRegDetail, String oldFlag) {
		Long id = addRegDetail.getId();
		addRegDetail.setId(null);

		Map map = FieldUtil.getPreFieldMap(AddRegDetail.class, addRegDetail);
		StringBuffer sql=new StringBuffer("update CSMS_ADD_REG_detail set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ? and flag=?");
		return saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"), id, oldFlag);
	}

	public int updateFlagRechargeSuccessByBusinessId(String newFlag, String oldFlag, Long businessId) {
		String sql= "update CSMS_ADD_REG_detail set flag=? where flag=? and bussinessid = ?";
		return update(sql, newFlag, oldFlag, businessId);
	}

	public int updateFlagNotRechargeByBusinessId(String newFlag, String oldFlag, Long businessId) {
		String sql= "update CSMS_ADD_REG_detail set flag=?,bussinessid=null,addtime=null,addplaceid=null,addoperid=null,operno=null,opername=null,placeno=null,placename=null where flag=? and bussinessid = ?";
		return update(sql, newFlag, oldFlag, businessId);
	}
	
	public List<Map<String,Object>> findDepositCountByAddRegId(Long addRegId){
		String sql = "select ard.* from csms_add_reg_detail ard where ard.flag in ('2','3') and ard.addRegId=?";
		return queryList(sql,addRegId);
	}
	
	public List<AddRegDetail> findAllByAddRegId(Long addRegId){
		String sql = "select * from csms_add_reg_detail where addRegId=?";
		List<Map<String,Object>> list = queryList(sql,addRegId);
		
		List<AddRegDetail> resultList = null;
		AddRegDetail addRegDetail = null;
		if (!list.isEmpty()) {
			resultList = new ArrayList<AddRegDetail>();
			for (int i = 0; i < list.size(); i++) {
				addRegDetail = new AddRegDetail();
				this.convert2Bean(list.get(i), addRegDetail);
				resultList.add(addRegDetail);
			}

		}
		return resultList;
	}
	/**
	 * 获取充值登记记录的充值总额：排除已退款的
	 * @param addRegId
	 * @return BigDecimal
	 */
	public BigDecimal findTotalFeeByAddRegId(Long addRegId){
		String sql = "select sum(fee) TOTALFEE from CSMS_add_reg_detail where addRegID=? and Flag in('0','2','3') ";
		List<Map<String, Object>> list = queryList(sql, addRegId);
		BigDecimal totalFee = null;
		if(list.size() > 0){
			totalFee = (BigDecimal) list.get(0).get("TOTALFEE");
			//当总和为0时，得到的结果会为null
			if (totalFee == null){
				totalFee = BigDecimal.ZERO;
			}
		}else{
			totalFee = BigDecimal.ZERO;
		}
		return totalFee;
	}
	
}
