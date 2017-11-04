/*package com.hgsoft.clearInterface.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.clearInterface.entity.ACinfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.utils.SequenceUtil;

@Component
public class ACinfoDao extends BaseDao {
	@Resource
	private CustomerDao customerDao;
	@Resource
	SequenceUtil sequenceUtil;

	public void save(ACinfo aCinfo) {
		StringBuffer sql = new StringBuffer("insert into CSMS_ACinfo(");
		sql.append(FieldUtil.getFieldMap(ACinfo.class, aCinfo).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(ACinfo.class, aCinfo).get("valueStr")
				+ ")");
		save(sql.toString());
	}

	// 清算接口
	public void saveACinfo(AccountCApply accountCApply, ACinfo aCinfo) {
		// Customer
		// customer=customerDao.findById(accountCApply.getCustomerId());
		aCinfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACinfo_NO"));
		// aCinfo.setCardNo(accountCInfo.getCardNo());
		aCinfo.setSubaccountNo(accountCApply.getSubAccountNo());
		aCinfo.setBank(accountCApply.getBank());
		aCinfo.setBankSpan(accountCApply.getBankSpan());
		aCinfo.setBankAccount(accountCApply.getBankAccount());
		aCinfo.setBankName(accountCApply.getBankName());
		aCinfo.setAccName(accountCApply.getAccName());
		aCinfo.setAccountType(accountCApply.getAccountType());
		aCinfo.setVirType(accountCApply.getVirType());
		if (accountCApply.getObaNo() != null)
			aCinfo.setOBANo(Integer.parseInt(accountCApply.getObaNo()));
		aCinfo.setMaxAcr(accountCApply.getMaxacr());
		// aCinfo.setOBANo(oBANo);
		// aCinfo.setState("0");
		aCinfo.setBusinessTime(new Date());
		save(aCinfo);
	}

	public ACinfo findByCardNoAndState(String cardNo, int state) {
		ACinfo temp = null;
		if (cardNo != null) {
			String sql = "select * from CSMS_acinfo  where cardno=" + "?"
					+ " and state=? ";
			List<Map<String, Object>> list = queryList(sql, new Object[] {
					cardNo, state });
			try {
				if (!list.isEmpty() && list.size() != 0) {
					temp = new ACinfo();
					this.convert2Bean(list.get(0), temp);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

	// 清算接口
	public void batchSaveACinfo(List<AccountCApply> accountCApplyList) {
		if (accountCApplyList.size() < 1) {
			return;
		} else {
			for (AccountCApply accountCApply : accountCApplyList) {
				ACinfo aCinfo = new ACinfo();
				saveACinfo(accountCApply, aCinfo);
			}
		}
	}
	*//**
	 * 解除止付修改为按银行账号进行操作
	 * @param cardNo
	 * @param state
	 * @return
	 *//*
	public ACinfo findByBankAccount(String bankAccount, int state) {
		ACinfo temp = null;
		if (bankAccount != null) {
			String sql = "select * from CSMS_acinfo  where bankAccount=" + "?"
					+ " and state=? ";
			List<Map<String, Object>> list = queryList(sql, new Object[] {
					bankAccount, state });
			try {
				if (!list.isEmpty() && list.size() != 0) {
					temp = new ACinfo();
					this.convert2Bean(list.get(0), temp);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
}
*/