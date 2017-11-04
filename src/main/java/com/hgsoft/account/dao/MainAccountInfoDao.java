package com.hgsoft.account.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SqlParamer;

@Component("mainAccountInfoDao")
public class MainAccountInfoDao extends BaseDao {

	//更新主账户可退余额，账户余额
	public void updateStatusAndCurReBa(Long mainId,BigDecimal balance){
		String sql = "update csms_mainaccount_info set availablerefundbalance=availablerefundbalance+?,"
				+ "balance=balance+? where id = ?";
		update(sql, balance, balance, mainId);
    	
	}
	public int[] batchUpdateStatusAndCurReBa(final List<Map<String,Object>> list) {  
        String sql = "update csms_mainaccount_info set availablerefundbalance=availablerefundbalance+?,balance=balance+? where id = ?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String,Object> map = list.get(i);
				ps.setBigDecimal(1, (BigDecimal)map.get("BALANCE"));
				ps.setBigDecimal(2, (BigDecimal)map.get("BALANCE"));
				ps.setLong(3, (Long)map.get("MAINACCOUNTID"));
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	@SuppressWarnings("rawtypes")
	public MainAccountInfo findByMainId(Long mainId) {
		String sql="select ID,MAINID,BALANCE,AVAILABLEBALANCE,PREFERENTIALBALANCE,"
				+ "FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,STATE,"
				+ "OPERID,PLACEID,OPEBACCOUNTDATE,HISSEQID  from CSMS_MAINACCOUNT_INFO where MAINID=?";
		List<MainAccountInfo> mainAccountInfos = super.queryObjectList(sql, MainAccountInfo.class, mainId);
		if (mainAccountInfos == null || mainAccountInfos.isEmpty()) {
			return null;
		}
		return mainAccountInfos.get(0);
	}
	@SuppressWarnings("rawtypes")
	public MainAccountInfo findByHisId(Long disId) {
		String sql="select ID,MAINID,BALANCE,AVAILABLEBALANCE,PREFERENTIALBALANCE,"
				+ "FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,STATE,"
				+ "OPERID,PLACEID,OPEBACCOUNTDATE,HISSEQID  from CSMS_MAINACCOUNT_INFO ";
		List list=null;
		if(disId==null){
			sql=sql+" where HISSEQID is null";
			list=queryList(sql);
		}else{
			sql=sql+" where HISSEQID=?";
			list=queryList(sql, disId);
		}
		
		MainAccountInfo mainAccountInfo = null;
		if(list!=null && list.size()!=0) {
			mainAccountInfo = (MainAccountInfo) this.convert2Bean((Map<String, Object>) list.get(0), new MainAccountInfo());
		}

		return mainAccountInfo;
	}
	
	@SuppressWarnings("rawtypes")
	public MainAccountInfo findByMainIdAndBalance(Long mainId) {
		String sql="select ID,MAINID,BALANCE,AVAILABLEBALANCE,PREFERENTIALBALANCE,"
				+ "FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,STATE,"
				+ "OPERID,PLACEID,OPEBACCOUNTDATE,HISSEQID  from CSMS_MAINACCOUNT_INFO where MAINID=? ";
		List<MainAccountInfo> mainAccountInfos = super.queryObjectList(sql, MainAccountInfo.class, mainId);
		if (mainAccountInfos == null || mainAccountInfos.isEmpty()) {
			return null;
		}
		return mainAccountInfos.get(0);
	}

	@SuppressWarnings("rawtypes")
	public List findAllMainAccountInfo() {
		List list = new ArrayList<MainAccountInfo>();
		StringBuffer sql = new StringBuffer("select ID,MAINID,BALANCE,AVAILABLEBALANCE,PREFERENTIALBALANCE,"
				+ "FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,STATE,"
				+ "OPERID,PLACEID,OPEBACCOUNTDATE,HISSEQID from CSMS_MAINACCOUNT_INFO t");
		list = queryList(sql.toString());
		return list;
	}
	
	public void  specialCostSave(MainAccountInfo mainAccountInfo){
		StringBuffer sql = new StringBuffer("update csms_mainaccount_info set");
		if(mainAccountInfo.getBalance()!=null){
			sql.append(" balance = "+mainAccountInfo.getBalance());
		}
		if(mainAccountInfo.getAvailableBalance()!=null){
			sql.append(" , availablebalance = "+mainAccountInfo.getAvailableBalance());
		}
		if(mainAccountInfo.getPreferentialBalance()!=null){
			sql.append(" , preferentialbalance= "+mainAccountInfo.getPreferentialBalance());
		}
		if(mainAccountInfo.getFrozenBalance()!=null){
			sql.append(" , frozenbalance =  "+mainAccountInfo.getFrozenBalance());
		}
		if(mainAccountInfo.getAvailableRefundBalance()!=null){
			sql.append(" , availablerefundbalance="+mainAccountInfo.getAvailableRefundBalance());
		}
		if(mainAccountInfo.getRefundApproveBalance()!=null){
			sql.append(" , refundapprovebalance= "+mainAccountInfo.getRefundApproveBalance());
		}
		 
		if(mainAccountInfo.getState()!=null){
			sql.append(" , state= "+mainAccountInfo.getState());
		}
		sql.append(" , HisSeqId = "+mainAccountInfo.getHisSeqId());
		sql.append("where mainid = "+mainAccountInfo.getMainId());
		update(sql.toString());
		
	}
	
	//最后使用
	public void update(MainAccountInfo mainAccountInfo){
		StringBuffer sql = new StringBuffer("update csms_mainaccount_info set");
		if(mainAccountInfo.getAvailableBalance()!=null){
			sql.append(" availablebalance = availablebalance+  "+mainAccountInfo.getAvailableBalance());
		}
		if(mainAccountInfo.getPreferentialBalance()!=null){
			sql.append(" , preferentialbalance= preferentialbalance+  "+mainAccountInfo.getPreferentialBalance());
		}
		if(mainAccountInfo.getFrozenBalance()!=null){
			sql.append(" , frozenbalance =  frozenbalance+  "+mainAccountInfo.getFrozenBalance());
		}
		if(mainAccountInfo.getAvailableRefundBalance()!=null){
			sql.append(" , availablerefundbalance=availablerefundbalance+  "+mainAccountInfo.getAvailableRefundBalance());
		}
		if(mainAccountInfo.getRefundApproveBalance()!=null){
			sql.append(" , refundapprovebalance=refundapprovebalance+ "+mainAccountInfo.getRefundApproveBalance());
		}
		sql.append(", balance = availablebalance+ "+mainAccountInfo.getAvailableBalance()
					+"+preferentialbalance+ "+mainAccountInfo.getPreferentialBalance()+" "
					+ "+ frozenbalance+ "+mainAccountInfo.getFrozenBalance()
					+" +availablerefundbalance+ "+mainAccountInfo.getAvailableRefundBalance()
					+" +refundapprovebalance+ "+mainAccountInfo.getRefundApproveBalance());
		if(mainAccountInfo.getState()!=null){
			sql.append(" , state= "+mainAccountInfo.getState());
		}
		sql.append(" , HisSeqId = "+mainAccountInfo.getHisSeqId());
		sql.append("where mainid = "+mainAccountInfo.getMainId());
		update(sql.toString());
	}
	
	public int updateBackInt(MainAccountInfo mainAccountInfo){
			StringBuffer sql = new StringBuffer("update csms_mainaccount_info set");
			SqlParamer sqlPar=new SqlParamer();
			if(mainAccountInfo.getAvailableBalance()!=null){
				sql.append(" availablebalance = availablebalance+  "+mainAccountInfo.getAvailableBalance());
				if(mainAccountInfo.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0){
					sqlPar.ge(" availablebalance",mainAccountInfo.getAvailableBalance().abs());
				}
			}
			if(mainAccountInfo.getPreferentialBalance()!=null){
				sql.append(" , preferentialbalance= preferentialbalance+  "+mainAccountInfo.getPreferentialBalance());
				if(mainAccountInfo.getPreferentialBalance().compareTo(BigDecimal.ZERO) < 0){
					sqlPar.ge(" preferentialbalance",mainAccountInfo.getPreferentialBalance().abs());
				}
			}
			if(mainAccountInfo.getFrozenBalance()!=null){
				sql.append(" , frozenbalance =  frozenbalance+  "+mainAccountInfo.getFrozenBalance());
				if(mainAccountInfo.getFrozenBalance().compareTo(BigDecimal.ZERO) < 0){
					sqlPar.ge(" frozenbalance",mainAccountInfo.getFrozenBalance().abs());
				}
			}
			if(mainAccountInfo.getAvailableRefundBalance()!=null){
				sql.append(" , availablerefundbalance=availablerefundbalance+  "+mainAccountInfo.getAvailableRefundBalance());
				if(mainAccountInfo.getAvailableRefundBalance().compareTo(BigDecimal.ZERO) < 0){
					sqlPar.ge(" availablerefundbalance",mainAccountInfo.getAvailableRefundBalance().abs());
				}
			}
			if(mainAccountInfo.getRefundApproveBalance()!=null){
				sql.append(" , refundapprovebalance=refundapprovebalance+ "+mainAccountInfo.getRefundApproveBalance());
				if(mainAccountInfo.getRefundApproveBalance().compareTo(BigDecimal.ZERO) < 0){
					sqlPar.ge(" refundapprovebalance",mainAccountInfo.getRefundApproveBalance().abs());
				}
			}
			sql.append(", balance = availablebalance+ "+mainAccountInfo.getAvailableBalance()
						+"+preferentialbalance+ "+mainAccountInfo.getPreferentialBalance()+" "
						+ "+ frozenbalance+ "+mainAccountInfo.getFrozenBalance()
						+" +availablerefundbalance+ "+mainAccountInfo.getAvailableRefundBalance()
						+" +refundapprovebalance+ "+mainAccountInfo.getRefundApproveBalance());
			if(mainAccountInfo.getState()!=null){
				sqlPar.eq(" state", mainAccountInfo.getState());
			}
			sql.append(" , HisSeqId = "+mainAccountInfo.getHisSeqId());
			sql.append(" where mainid = "+mainAccountInfo.getMainId());
			sql.append(" ");
			sql.append(sqlPar.getParam());
			Object[] Objects= sqlPar.getList().toArray();
			return this.jdbcUtil.getJdbcTemplate().update(sql.toString(),Objects);
			//update(sql.toString());
		}
	/*public void update(MainAccountInfo mainAccountInfo) {
		StringBuffer sql = new StringBuffer("update CSMS_MAINACCOUNT_INFO set ");
		String sqlString = "";
		
		if (mainAccountInfo.getBalance() == null) {
			sql.append("BALANCE=NULL,");
		} else {
			sql.append("BALANCE='" + mainAccountInfo.getBalance() + "',");
		}
		
		if (mainAccountInfo.getAvailableBalance() == null) {
			sql.append("AVAILABLEBALANCE=NULL,");
		} else {
			sql.append("AVAILABLEBALANCE='" + mainAccountInfo.getAvailableBalance() + "',");
		}
		if (mainAccountInfo.getPreferentialBalance() == null) {
			sql.append("PREFERENTIALBALANCE=NULL,");
		} else {
			sql.append("PREFERENTIALBALANCE='" + mainAccountInfo.getPreferentialBalance() + "',");
		}
		
		if (mainAccountInfo.getFrozenBalance() == null) {
			sql.append("FROZENBALANCE=NULL,");
		} else {
			sql.append("FROZENBALANCE='" + mainAccountInfo.getFrozenBalance() + "',");
		}
		
		if (mainAccountInfo.getAvailableRefundBalance() == null) {
			sql.append("AVAILABLEREFUNDBALANCE=NULL,");
		} else {
			sql.append("AVAILABLEREFUNDBALANCE='" + mainAccountInfo.getAvailableRefundBalance() + "',");
		}
		
		if (mainAccountInfo.getRefundApproveBalance() == null) {
			sql.append("REFUNDAPPROVEBALANCE=NULL,");
		} else {
			sql.append("REFUNDAPPROVEBALANCE='" + mainAccountInfo.getRefundApproveBalance() + "',");
		}
		if (mainAccountInfo.getState() == null) {
			sql.append("STATE=NULL,");
		} else {
			sql.append("STATE='" + mainAccountInfo.getState() + "',");
		}
		
		if (mainAccountInfo.getHisSeqId() == null) {
			sql.append("hisSeqid=NULL,");
		} else {
			sql.append("hisSeqid='" + mainAccountInfo.getHisSeqId() + "',");
		}

		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += " where id='" + mainAccountInfo.getId() + "'";
		update(sqlString);
	}*/

	public void save(MainAccountInfo mainAccountInfo) {
		mainAccountInfo.setHisSeqId(-mainAccountInfo.getId());
		StringBuffer sql=new StringBuffer("insert into CSMS_MAINACCOUNT_INFO(");
		sql.append(FieldUtil.getFieldMap(MainAccountInfo.class,mainAccountInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(MainAccountInfo.class,mainAccountInfo).get("valueStr")+")");
		save(sql.toString());
	}
	/**
	 * 根据客户id查找主账户对象
	 * @param customerId
	 * @return
	 */
	public MainAccountInfo findMainAccountInfoByCustomerID(Long customerId){
		String sql = "select * from CSMS_MainAccount_Info where MainID=?";
		List<MainAccountInfo> mainAccountInfos = super.queryObjectList(sql, MainAccountInfo.class, customerId);
		if (mainAccountInfos == null || mainAccountInfos.isEmpty()) {
			return null;
		}
		return mainAccountInfos.get(0);
	}

	/**
	 * 通过子账户id找到主账户信息
	 * @param subAccountId
	 */
	public MainAccountInfo findBySubAccountId(Long subAccountId) {
		String sql="select main.ID,main.MAINID,main.BALANCE,main.AVAILABLEBALANCE,main.PREFERENTIALBALANCE,"
				+ "main.FROZENBALANCE,main.AVAILABLEREFUNDBALANCE,main.REFUNDAPPROVEBALANCE,main.STATE,"
				+ "main.OPERID,main.PLACEID,main.OPEBACCOUNTDATE,main.HISSEQID  from CSMS_MAINACCOUNT_INFO main"
				+ " join csms_subaccount_info sub on sub.mainid=main.id where sub.id=?";
		List<MainAccountInfo> mainAccountInfos = super.queryObjectList(sql, MainAccountInfo.class, subAccountId);
		if (mainAccountInfos == null || mainAccountInfos.isEmpty()) {
			return null;
		}
		return mainAccountInfos.get(0);
	}
	/**
	 * 将可退余额全都扣到退款审批余额
	 * @author gsf
	 * @param mainAccountId 主账户id
	 * 2016-05-30
	 */
	/*public void updateAvRefundToRefundApprove(Long mainAccountId){
		String sql = "update CSMS_MAINACCOUNT_INFO set AvailableRefundBalance=0,"
				+ "RefundApproveBalance=RefundApproveBalance+AvailableRefundBalance where id=?";
		saveOrUpdate(sql, mainAccountId);
	}*/
	/**
	 * 1:若传入的currentRefundBalance为正数，从账户中扣除可退余额（这笔本次退款金额）到退款审批余额中
	 * 2：若传入的currentRefundBalance为负数，从账户中扣除退款审批余额（这笔本次退款金额）到可退余额中
	 * @param mainID
	 * @param currentRefundBalance
	 */
	/*public void updateAvRefundAndRefundAppByCurrent(Long mainID, BigDecimal currentRefundBalance){
		String sql = "update CSMS_MAINACCOUNT_INFO set AvailableRefundBalance=AvailableRefundBalance-?,"
				+ "RefundApproveBalance=RefundApproveBalance+? where id=?";
		saveOrUpdate(sql, currentRefundBalance,currentRefundBalance,mainID);
	}*/
	/**
	 * 从主账户中直接扣除退款审批余额，扣除的金额为：传入的当前退款金额
	 * @param mainID
	 * @param currentRefundBalance
	 */
	/*public void updateRefundAppBalanceByCurrent(Long mainID,BigDecimal currentRefundBalance){
		String sql = "update CSMS_MAINACCOUNT_INFO set RefundApproveBalance=RefundApproveBalance-? where id=?";
		saveOrUpdate(sql, currentRefundBalance,mainID);
	}*/
	/**
	 * 根据id查找对象
	 * @param id
	 * @return
	 */
	public MainAccountInfo findById(Long id) {
		String sql="select ID,MAINID,BALANCE,AVAILABLEBALANCE,PREFERENTIALBALANCE,"
				+ "FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,STATE,"
				+ "OPERID,PLACEID,OPEBACCOUNTDATE,HISSEQID  from CSMS_MAINACCOUNT_INFO where id=?";

		List<MainAccountInfo> mainAccountInfos = super.queryObjectList(sql, MainAccountInfo.class, id);
		if (mainAccountInfos == null || mainAccountInfos.isEmpty()) {
			return null;
		}
		return mainAccountInfos.get(0);
	}
}
