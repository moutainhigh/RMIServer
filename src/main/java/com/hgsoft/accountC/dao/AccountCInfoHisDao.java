package com.hgsoft.accountC.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class AccountCInfoHisDao extends BaseDao{
	@Autowired
	private SequenceUtil sequenceUtil;
	
	public int[] batchSaveForbranches(final List<AccountCInfoHis> hisList) {  
        String sql = "insert into CSMS_AccountC_info_his(ID,CARDNO,CUSTOMERID,ACCOUNTID,STATE,COST,"
        		+ "REALCOST,ISSUETIME,startDate,endDate,Bail,ISSUEFLAG,ISSUEOPERID,ISSUEPLACEID,S_CON_PWD_FLAG,TRADINGPWD,"
        		+ "HISSEQID,GENTIME,GENREASON,BIND,SETTLETTIME,SETTLEDAY,ISDAYSET,AGENTSMAY,LINKMAN,"
        		+ "LINKTEL,LINKMOBILE,LINKADDR,LINKZIPCODE,MAINTAINTIME,suit,blackFlag,CARDSERVICEPWD)  "
        		
				+ "SELECT ?,CARDNO,CUSTOMERID,ACCOUNTID,STATE,COST,"
        		+ "REALCOST,ISSUETIME,startDate,endDate,Bail,ISSUEFLAG,ISSUEOPERID,ISSUEPLACEID,S_CON_PWD_FLAG,TRADINGPWD,"
        		+ "HISSEQID,sysdate,?,BIND,SETTLETTIME,SETTLEDAY,ISDAYSET,AGENTSMAY,LINKMAN,"
        		+ "LINKTEL,LINKMOBILE,LINKADDR,LINKZIPCODE,MAINTAINTIME,suit,blackFlag,CARDSERVICEPWD"
				+ " FROM CSMS_AccountC_info WHERE CardNo=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				AccountCInfoHis accountCInfoHis = hisList.get(i);
				ps.setLong(1, accountCInfoHis.getId());
				ps.setString(2, accountCInfoHis.getGenReason());
				ps.setString(3, accountCInfoHis.getCardNo());
			}
			
			@Override
			public int getBatchSize() {
				 return hisList.size();
			}
		});
    } 
	public AccountCInfoHis findByHisId(Long hisId) {
		String sql = "";
		if(hisId!=null){
			sql = "select * from CSMS_AccountC_info_his where hisSeqID="+hisId;
		}else{
			sql = "select * from CSMS_AccountC_info_his where hisSeqID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		AccountCInfoHis accountCInfoHis = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfoHis = new AccountCInfoHis();
			this.convert2Bean(list.get(0), accountCInfoHis);
		}

		return accountCInfoHis;
	}
	public void saveForBranches(AccountCInfo accountCInfo,AccountCInfoHis accountCInfoHis){
		String sql = "insert into CSMS_AccountC_info_his(ID,CARDNO,CUSTOMERID,ACCOUNTID,STATE,COST,"
        		+ "REALCOST,ISSUETIME,startDate,endDate,Bail,ISSUEFLAG,ISSUEOPERID,ISSUEPLACEID,S_CON_PWD_FLAG,TRADINGPWD,"
        		+ "HISSEQID,GENTIME,GENREASON,BIND,SETTLETTIME,SETTLEDAY,ISDAYSET,AGENTSMAY,LINKMAN,"
        		+ "LINKTEL,LINKMOBILE,LINKADDR,LINKZIPCODE,MAINTAINTIME,suit,blackFlag,CARDSERVICEPWD )  "
        		
				+ "SELECT "+accountCInfoHis.getId()+",CARDNO,CUSTOMERID,ACCOUNTID,STATE,COST,"
        		+ "REALCOST,ISSUETIME,startDate,endDate,Bail,ISSUEFLAG,ISSUEOPERID,ISSUEPLACEID,S_CON_PWD_FLAG,TRADINGPWD,"
        		+ "HISSEQID,sysdate,'"+accountCInfoHis.getGenReason()+"',BIND,SETTLETTIME,SETTLEDAY,ISDAYSET,AGENTSMAY,LINKMAN,"
        		+ "LINKTEL,LINKMOBILE,LINKADDR,LINKZIPCODE,MAINTAINTIME,suit,blackFlag,CARDSERVICEPWD FROM CSMS_AccountC_info WHERE id="+accountCInfo.getId();
		save(sql);
	}
	
	
	public int[] batchSave(final List<AccountCInfoHis> hisList) {  
        String sql = "insert into CSMS_AccountC_info_his(ID,genTime,genReason,CardNo,"
				+ "customerID,AccountID,State,Cost,RealCost,IssueTime,startDate,MaintainTime,endDate,IssueFlag,Bind,Bail,IsDaySet,SettleDay,SettletTime,"
				+ "IssueOperId,IssuePlaceId,operNo,operName,placeNo,placeName,S_con_pwd_flag,"
				+ "TradingPwd,HisSeqID,suit,blackFlag,CARDSERVICEPWD )  "
				+ "SELECT ?,sysdate,?,CardNo,"
				+ "customerID,AccountID,State,Cost,RealCost,IssueTime,startDate,MaintainTime,endDate,IssueFlag,Bind,Bail,IsDaySet,SettleDay,SettletTime,"
				+ "IssueOperId,IssuePlaceId,operNo,operName,placeNo,placeName,S_con_pwd_flag,"
				+ "TradingPwd,HisSeqID,suit,blackFlag,CARDSERVICEPWD FROM CSMS_AccountC_info WHERE CardNo=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				AccountCInfoHis accountCInfoHis = hisList.get(i);
				ps.setLong(1, accountCInfoHis.getId());
				ps.setString(2, accountCInfoHis.getGenReason());
				ps.setString(3, accountCInfoHis.getCardNo());
			}
			
			@Override
			public int getBatchSize() {
				 return hisList.size();
			}
		});
    } 
	
	/**
	 * 保存记帐卡历史
	 * @param accountCInfo 查找出来的 记帐卡信息对象
	 * @param genReason 进入历史的原因
	 * @return Long  accountCInfoHis的id
	 */
	public Long saveAccountCInfoHis(AccountCInfo accountCInfo,String genReason){
		AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
		BigDecimal PrePaidC_his_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCinfohis_NO");
		accountCInfoHis.setId(Long.valueOf(PrePaidC_his_NO.toString()));
		accountCInfoHis.setGenReason(genReason);
		this.save(accountCInfo,accountCInfoHis);
		return accountCInfoHis.getId();
	}
	
	public void save(AccountCInfo accountCInfo,AccountCInfoHis accountCInfoHis){
		String sql = "insert into CSMS_AccountC_info_his(ID,genTime,genReason,CardNo,"
				+ "customerID,AccountID,State,Cost,RealCost,IssueTime,startDate,MaintainTime,endDate,IssueFlag,Bind,Bail,IsDaySet,SettleDay,SettletTime,"
				+ "IssueOperId,IssuePlaceId,operNo,operName,placeNo,placeName,S_con_pwd_flag,"
				+ "TradingPwd,HisSeqID,suit,blackFlag,CARDSERVICEPWD )  "
				+ "SELECT "+accountCInfoHis.getId()+",sysdate,"+accountCInfoHis.getGenReason()+",CardNo,"
				+ "customerID,AccountID,State,Cost,RealCost,IssueTime,startDate,MaintainTime,endDate,IssueFlag,Bind,Bail,IsDaySet,SettleDay,SettletTime,"
				+ "IssueOperId,IssuePlaceId,operNo,operName,placeNo,placeName,S_con_pwd_flag,"
				+ "TradingPwd,HisSeqID,suit,blackFlag,CARDSERVICEPWD FROM CSMS_AccountC_info WHERE id="+accountCInfo.getId();
		save(sql);
	}
	
	
	public AccountCInfoHis findByCardNoAndState(String cardNo,String state) {
		AccountCInfoHis temp = null;
		if (StringUtils.isNotBlank(cardNo)) {
			StringBuffer sql = new StringBuffer("select a.* from csms_accountc_info_his a where a.cardno='"+cardNo+"'");
			sql.append(" and a.genreason='"+state+"' order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()&&list.size()>=0) {
				temp = new AccountCInfoHis();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
}
