package com.hgsoft.prepaidC.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.prepaidC.entity.AddReg;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class AddRegDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;
	
	public void deleteById(Long id){
		String sql = "delete from csms_add_reg where id=?";
		delete(sql, id);
	}
	
	public Pager findAddRegByCustomer(Pager pager, Customer customer) {
		StringBuffer sql = new StringBuffer(
				"SELECT A.id,A.RegistrationTime,A.TOTALFEE,A.addStyle,c.ORGAN,c.IDTYPE,"
				+ "c.IDCODE,ROWNUM AS num FROM CSMS_ADD_REG A,CSMS_CUSTOMER c WHERE 1 = 1 AND c. ID = A .USERID and c.id =?");
//		if(customer!=null){
//			sql.append(FieldUtil.getFieldMap(Customer.class, customer).get("nameAndValueNotNull"));
//		}
		sql.append(" order by A.RegistrationTime desc");
		return this.findByPages(sql.toString(), pager, new Object[]{customer.getId()});
	}

	/*public void saveAddRegs(Map<String, String> addRegPair, Customer customer) {
		for(Map.Entry<String, String> entry : addRegPair.entrySet()){
			AddReg addReg = new AddReg();
			addReg.setCardno(entry.getKey());
			addReg.setFee(BigDecimal.valueOf(Double.valueOf(entry.getValue())));
			addReg.setFlag("1");//充值标志：正常
			saveAddReg(addReg, customer);
		}
	}
	
	public void saveAddReg(AddReg addReg,Customer customer){
		StringBuffer sql=new StringBuffer("insert into csms_add_Reg(");
		BigDecimal PrePaidC_NO = sequenceUtil.getSequence("SEQ_CSMS_add_reg_NO");
		addReg.setId(Long.valueOf(PrePaidC_NO.toString()));
		addReg.setUserid(customer.getId());
		addReg.setRegistrationtime(new Date());
		sql.append(FieldUtil.getFieldMap(AddReg.class,addReg).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AddReg.class,addReg).get("valueStr")+")");
		save(sql.toString());
	}*/
	
	public AddReg findById(Long id) {
		String sql = "select * from CSMS_Add_Reg where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		AddReg addReg = null;
		if (!list.isEmpty()) {
			addReg = new AddReg();
			this.convert2Bean(list.get(0), addReg);
		}

		return addReg;
	}
	
	public void update(AddReg addReg) {
		/*StringBuffer sql=new StringBuffer("update CSMS_ADD_REG set ");
		sql.append(FieldUtil.getFieldMap(AddReg.class,addReg).get("nameAndValue")+" where id="+addReg.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(AddReg.class,addReg);
		StringBuffer sql=new StringBuffer("update CSMS_ADD_REG set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),addReg.getId());
	}
	
	public void save(AddReg addReg) {
		/*StringBuffer sql=new StringBuffer("insert into csms_add_reg(");
		sql.append(FieldUtil.getFieldMap(AddReg.class,addReg).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AddReg.class,addReg).get("valueStr")+")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(AddReg.class,addReg);
		StringBuffer sql=new StringBuffer("insert into csms_add_reg");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AddReg findByIdAndCustomerId(Long id, Long customerId) {
		String sql = "select * from CSMS_Add_Reg where id=? and userId=?";
		List<Map<String, Object>> list = queryList(sql,id,customerId);
		AddReg addReg = null;
		if (!list.isEmpty()) {
			addReg = new AddReg();
			this.convert2Bean(list.get(0), addReg);
		}

		return addReg;
	}
	
}
