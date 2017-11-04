package com.hgsoft.account.dao;

import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.dao.ReceiptDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class AccountBussinessDao extends BaseDao{
	@Resource
	private ReceiptDao receiptDao;
	public void save(AccountBussiness accountBussiness) {
		StringBuffer sql=new StringBuffer("insert into CSMS_account_bussiness(");
		Map<String, String> fieldMap = FieldUtil.getFieldMap(AccountBussiness.class, accountBussiness);
		sql.append(fieldMap.get("nameStr")).append(") values(");
		sql.append(fieldMap.get("valueStr")).append(")");
		save(sql.toString());
		//receiptDao.saveByBussiness(null, accountBussiness, null, null, null);
	}
	public void saveRecharge(AccountBussiness accountBussiness) {
		StringBuffer sql=new StringBuffer("insert into CSMS_account_bussiness(");
		Map<String, String> fieldMap = FieldUtil.getFieldMap(AccountBussiness.class, accountBussiness);
		sql.append(fieldMap.get("nameStr")).append(") values(");
		sql.append(fieldMap.get("valueStr")).append(")");
		save(sql.toString());
		//receiptDao.saveByBussiness(null, accountBussiness, null, null, null);
	}
	
	
	public AccountBussiness findById(Long id){
		String sql="select * from CSMS_account_bussiness where id=?";
		List<AccountBussiness> accountBussinesses = super.queryObjectList(sql, AccountBussiness.class, id);
		if (accountBussinesses == null || accountBussinesses.isEmpty()) {
			return null;
		}
		return accountBussinesses.get(0);
	}
	
	/**
	 * 
	 * @param bussinessId 记录的是做此次业务的记录的id，例如缴款，则记录的是rechargeInfoId
	 * @return AccountBussiness
	 */
	public AccountBussiness findByBussinessId(Long bussinessId){
		String sql="select * from CSMS_account_bussiness where bussinessId=?";

		List<AccountBussiness> accountBussinesses = super.queryObjectList(sql, AccountBussiness.class, bussinessId);
		if (accountBussinesses == null || accountBussinesses.isEmpty()) {
			return null;
		}
		return accountBussinesses.get(0);
	}

	/**
	 * 根据businessId和state找业务
	 * @param businessId
	 * @param state
	 * @return
	 */
	public AccountBussiness findByBusinessIdAndState(Long businessId,String state){
		String sql = "select * from csms_account_bussiness where bussinessId = ? and state = ?";
		List<AccountBussiness> accountBussinessList = queryObjectList(sql,AccountBussiness.class,businessId,state);
		if(!CollectionUtils.isEmpty(accountBussinessList)){
			return accountBussinessList.get(0);
		}
		return new AccountBussiness();
	}

}