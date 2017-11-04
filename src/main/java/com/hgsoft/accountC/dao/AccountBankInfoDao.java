package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.AccountBankInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
@Repository
public class AccountBankInfoDao extends BaseDao{
	
	public void save(AccountBankInfo accountBankInfo){
		Map map = FieldUtil.getPreFieldMap(AccountBankInfo.class,accountBankInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_Account_Bank_Info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public void update(AccountBankInfo accountBankInfo){
		Map map = FieldUtil.getPreFieldMap(AccountBankInfo.class, accountBankInfo);
		StringBuffer sql =  new StringBuffer("update CSMS_Account_Bank_Info set ");
		sql.append(map.get("updateNameStrNotNull")+" where id=?");
		super.saveOrUpdate(sql.toString(), (List)map.get("paramNotNull"), accountBankInfo.getId());
	}
	
	public AccountBankInfo findSubAccountNo(String subAccountNo) {
		String sql = "select * from CSMS_Account_Bank_Info where subAccountNo=?";
		List<AccountBankInfo> accountBankInfos = super.queryObjectList(sql, AccountBankInfo.class, subAccountNo);
		if (accountBankInfos == null || accountBankInfos.isEmpty()) {
			return null;
		}
		return accountBankInfos.get(0);
	}
}
