package com.hgsoft.account.dao;


import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class AccountFundChangeDao extends BaseDao{
	
	/*public void save(AccountFundChange accountFundChange) {
		StringBuffer sql=new StringBuffer("insert into CSMS_AccountFundChange(");
		sql.append(FieldUtil.getFieldMap(AccountFundChange.class,accountFundChange).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountFundChange.class,accountFundChange).get("valueStr")+")");
		save(sql.toString());
	}*/
	public Pager list(Pager pager,Date starTime ,Date endTime,AccountFundChange accountFundChange){
		
		String sql= "select "+FieldUtil.getFieldMap(AccountFundChange.class,new AccountFundChange()).get("nameStr")+", ROWNUM as num from CSMS_AccountFundChange where 1=1 "+
		" ";
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and ChgDate >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and ChgDate <=to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(StringUtil.isNotBlank(accountFundChange.getChangeType()))params.eq("ChangeType", accountFundChange.getChangeType());
		if(accountFundChange.getMainId()!=null)params.eq("mainid", accountFundChange.getMainId());
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by createTime desc,id desc ");
		return this.findByPages(sql, pager,Objects);
	}

	public AccountFundChange find(AccountFundChange accountFundChange) {
		if (accountFundChange == null) {
			return null;
		}
		String condition = FieldUtil.getFieldMap(AccountFundChange.class, accountFundChange).get("nameAndValueNotNull");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("AccountFundChangeDao.find查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_AccountFundChange where 1=1 ");
		sql.append(condition);
		sql.append(" order by ChgDate desc");
		List<AccountFundChange> accountFundChanges = super.queryObjectList(sql.toString(), AccountFundChange.class);
		if (accountFundChanges == null || accountFundChanges.isEmpty()) {
			return null;
		}
		return accountFundChanges.get(0);
	}

	public void saveChange(AccountFundChange accountFundChange) {
		StringBuffer sql=new StringBuffer("insert into CSMS_AccountFundChange("
				+ "ID,MainId,FlowNo,ChangeType,BeforeBalance,BeforeAvailableBalance,BeforeFrozenBalance,"
				+ "BeforepreferentialBalance,BeforeAvailableRefundBalance,BeforeRefundApproveBalance,"
				+ "CurrAvailableBalance,CurrFrozenBalance,CurrpreferentialBalance,CurrAvailableRefundBalance,"
				+ "CurrRefundApproveBalance,AfterBalance,AfterAvailableBalance,AfterFrozenBalance,"
				+ "AfterpreferentialBalance,AfterAvailableRefundBalance,AfterRefundApproveBalance,"
				+ "ChgOperID,ChgPlaceID,ChgDate,Memo,operNo,operName,placeNo,placeName,createTime) select ");
		
		sql.append(accountFundChange.getId()+",");
		sql.append(accountFundChange.getMainId()+",");
		sql.append(accountFundChange.getFlowNo()+",");
		sql.append(accountFundChange.getChangeType()+",");
		
		/*sql.append(" availablebalance BeforeAvailableBalance,"
				+ "frozenbalance BeforeFrozenBalance,"
				+ "preferentialbalance BeforepreferentialBalance,"
				+ "availablerefundbalance BeforeAvailableRefundBalance,"
				+ "refundapprovebalance BeforeRefundApproveBalance, ");*/
		sql.append(" balance - ").append(accountFundChange.getBeforeAvailableBalance())
				.append(" - ").append(accountFundChange.getBeforeFrozenBalance())
				.append(" - ").append(accountFundChange.getBeforeAvailableRefundBalance())
				.append(" - ").append(accountFundChange.getBeforeRefundApproveBalance())
				.append(" - ").append(accountFundChange.getBeforepreferentialBalance())
				.append(" BeforeBalance,");

		sql.append(" availablebalance-(" + accountFundChange.getBeforeAvailableBalance() + ") BeforeAvailableBalance ,"
				+ "frozenbalance-(" + accountFundChange.getBeforeFrozenBalance() + ") BeforeFrozenBalance,"
				+ "preferentialbalance -(" + accountFundChange.getBeforepreferentialBalance() + ")  BeforepreferentialBalance,"
				+ "availablerefundbalance-(" + accountFundChange.getBeforeAvailableRefundBalance() + ")  BeforeAvailableRefundBalance,"
				+ "refundapprovebalance -(" + accountFundChange.getBeforeRefundApproveBalance() + ")  BeforeRefundApproveBalance, ");
		
		sql.append(accountFundChange.getCurrAvailableBalance()+",");
		sql.append(accountFundChange.getCurrFrozenBalance()+",");
		sql.append(accountFundChange.getCurrpreferentialBalance()+",");
		sql.append(accountFundChange.getCurrAvailableRefundBalance()+",");
		sql.append(accountFundChange.getCurrRefundApproveBalance()+",");
		sql.append(" balance AfterBalance,");
		sql.append(" availablebalance AfterAvailableBalance ,"
				+ "frozenbalance AfterFrozenBalance,"
				+ "preferentialbalance  AfterpreferentialBalance,"
				+ "availablerefundbalance  AfterAvailableRefundBalance,"
				+ "refundapprovebalance  AfterRefundApproveBalance, ");
		
		
		
		sql.append(accountFundChange.getChgOperID()+",");
		sql.append(accountFundChange.getChgPlaceID()+",");
		sql.append("sysdate,");
		sql.append("'"+accountFundChange.getMemo()+"',");
		sql.append("'"+accountFundChange.getOperNo()+"',");
		sql.append("'"+accountFundChange.getOperName()+"',");
		sql.append("'"+accountFundChange.getPlaceNo()+"',");
		sql.append("'"+accountFundChange.getPlaceName()+"'");
		
		sql.append(",sysdate from csms_mainaccount_info where id = ?");
		save(sql.toString(), accountFundChange.getMainId());
	}
	
	public void saveChangeDaySet(AccountFundChange accountFundChange) {
		StringBuffer sql=new StringBuffer("insert into CSMS_AccountFundChange("
				+ "ID,MainId,FlowNo,ChangeType,BeforeAvailableBalance,BeforeFrozenBalance,"
				+ "BeforepreferentialBalance,BeforeAvailableRefundBalance,BeforeRefundApproveBalance,"
				+ "CurrAvailableBalance,CurrFrozenBalance,CurrpreferentialBalance,CurrAvailableRefundBalance,"
				+ "CurrRefundApproveBalance,AfterAvailableBalance,AfterFrozenBalance,"
				+ "AfterpreferentialBalance,AfterAvailableRefundBalance,AfterRefundApproveBalance,"
				+ "ChgOperID,ChgPlaceID,ChgDate,Memo,operNo,operName,placeNo,placeName,createTime) select ");
		
		sql.append(accountFundChange.getId()+",");
		sql.append(accountFundChange.getMainId()+",");
		sql.append(accountFundChange.getFlowNo()+",");
		sql.append(accountFundChange.getChangeType()+",");
		
		/*sql.append(" availablebalance BeforeAvailableBalance,"
				+ "frozenbalance BeforeFrozenBalance,"
				+ "preferentialbalance BeforepreferentialBalance,"
				+ "availablerefundbalance BeforeAvailableRefundBalance,"
				+ "refundapprovebalance BeforeRefundApproveBalance, ");*/
		
		sql.append(" availablebalance-("+accountFundChange.getBeforeAvailableBalance()+") BeforeAvailableBalance ,"
				+ "frozenbalance-("+accountFundChange.getBeforeFrozenBalance()+") BeforeFrozenBalance,"
				+ "preferentialbalance -("+accountFundChange.getBeforepreferentialBalance()+")  BeforepreferentialBalance,"
				+ "availablerefundbalance-("+accountFundChange.getBeforeAvailableRefundBalance()+")  BeforeAvailableRefundBalance,"
				+ "refundapprovebalance -("+accountFundChange.getBeforeRefundApproveBalance()+")  BeforeRefundApproveBalance, ");
		
		sql.append(accountFundChange.getCurrAvailableBalance()+",");
		sql.append(accountFundChange.getCurrFrozenBalance()+",");
		sql.append(accountFundChange.getCurrpreferentialBalance()+",");
		sql.append(accountFundChange.getCurrAvailableRefundBalance()+",");
		sql.append(accountFundChange.getCurrRefundApproveBalance()+",");
		
		sql.append(" availablebalance AfterAvailableBalance ,"
				+ "frozenbalance AfterFrozenBalance,"
				+ "preferentialbalance  AfterpreferentialBalance,"
				+ "availablerefundbalance  AfterAvailableRefundBalance,"
				+ "refundapprovebalance  AfterRefundApproveBalance, ");
		
		
		
		sql.append(accountFundChange.getChgOperID()+",");
		sql.append(accountFundChange.getChgPlaceID()+",");
		
		
		if(accountFundChange.getChgDate()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(accountFundChange.getChgDate());
			sql.append("to_date('"+date+" 23:59:59','YYYY-MM-DD HH24:MI:SS'),");
		} else sql.append("sysdate,");
		sql.append("'"+accountFundChange.getMemo()+"',");
		sql.append("'"+accountFundChange.getOperNo()+"',");
		sql.append("'"+accountFundChange.getOperName()+"',");
		sql.append("'"+accountFundChange.getPlaceNo()+"',");
		sql.append("'"+accountFundChange.getPlaceName()+"',");
		sql.append("sysdate");
		sql.append(" from csms_mainaccount_info where id = ? ");
		
		save(sql.toString(), accountFundChange.getMainId());
	}
	
	/**
	 * 查找该账户的本次变动账户可退款余额不为0的所有账户资金变动流水表记录
	 * @author gsf
	 * @param mainID 主账户id
	 * @return List<AccountFundChange>
	 * 2016-05-31
	 */
	public List<AccountFundChange> findByMainIDAndZero(Long mainID){
		List<AccountFundChange> accountFundChanges = new ArrayList<AccountFundChange>();
		String sql = "select "+FieldUtil.getFieldMap(AccountFundChange.class,new AccountFundChange()).get("nameStr")+" where mainID=? order by ChgDate ";
		return super.queryObjectList(sql, AccountFundChange.class, mainID);
	}
}
