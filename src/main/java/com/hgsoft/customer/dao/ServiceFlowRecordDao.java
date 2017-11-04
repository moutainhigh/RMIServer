package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.entity.PrepaidC;

@Repository
public class ServiceFlowRecordDao extends BaseDao{

	public void save(ServiceFlowRecord serviceFlowRecord) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_ServiceFlow_Record(");
		sql.append(FieldUtil.getFieldMap(ServiceFlowRecord.class,serviceFlowRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ServiceFlowRecord.class,serviceFlowRecord).get("valueStr")+")");*/
		//save(sql.toString());
	}
	
	public void saveTagInfoServiceFlow(ServiceFlowRecord serviceFlowRecord,String type) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_ServiceFlow_Record("
		+ "ID,ClientID,CardTagNo,OldCardTagNo,ServiceFlowNO,"
		+ "ServicePTypeCode,ServiceTypeCode,BeforeState,CurrState,"
		+ "AfterState,BeforeAvailableBalance,BeforeFrozenBalance,"
		+ "BeforepreferentialBalance,BeforeAvailableRefundBalance,"
		+ "BeforeRefundApproveBalance,CurrAvailableBalance,"
		+ "CurrFrozenBalance,CurrpreferentialBalance,CurrAvailableRefundBalance,"
		+ "CurrRefundApproveBalance,AfterAvailableBalance,AfterFrozenBalance,"
		+ "AfterpreferentialBalance,AfterAvailableRefundBalance,AfterRefundApproveBalance,"
		+ "OperID,PlaceID,createTime,IsNeedBlacklist,"
		+ "IsDoFlag,DoTime,TransferSum,ReturnMoney,TermCode,TermTradeNo,mac,tac,offlineTradeNo,onlineTradeNo,operNo,operName,placeNo,placeName) select  ");
		sql.append(serviceFlowRecord.getId()+",");
		sql.append(serviceFlowRecord.getClientID()+",");
		sql.append(serviceFlowRecord.getCardTagNO()+",");
		sql.append(serviceFlowRecord.getOldCardTagNO()+",");
		sql.append(serviceFlowRecord.getServiceFlowNO()+",");
		sql.append(serviceFlowRecord.getServicePTypeCode()+",");
		sql.append(serviceFlowRecord.getServiceTypeCode()+",");
		sql.append("c.tagState,");
		sql.append(serviceFlowRecord.getCurrState()+",");
		sql.append(serviceFlowRecord.getAfterState()+",");
		
		if("0".equals(type)){
			
			sql.append(" availablebalance-("+serviceFlowRecord.getBeforeAvailableBalance()+") BeforeAvailableBalance ,"
					+ "frozenbalance-("+serviceFlowRecord.getBeforeFrozenBalance()+") BeforeFrozenBalance,"
					+ "preferentialbalance -("+serviceFlowRecord.getBeforepreferentialBalance()+")  BeforepreferentialBalance,"
					+ "availablerefundbalance-("+serviceFlowRecord.getBeforeAvailableRefundBalance()+")  BeforeAvailableRefundBalance,"
					+ "refundapprovebalance -("+serviceFlowRecord.getBeforeRefundApproveBalance()+")  BeforeRefundApproveBalance, ");
			
			sql.append(serviceFlowRecord.getCurrAvailableBalance()+",");
			sql.append(serviceFlowRecord.getCurrFrozenBalance()+",");
			sql.append(serviceFlowRecord.getCurrpreferentialBalance()+",");
			sql.append(serviceFlowRecord.getCurrAvailableRefundBalance()+",");
			sql.append(serviceFlowRecord.getCurrRefundApproveBalance()+",");
			
			sql.append(" availablebalance AfterAvailableBalance ,"
					+ "frozenbalance AfterFrozenBalance,"
					+ "preferentialbalance  AfterpreferentialBalance,"
					+ "availablerefundbalance  AfterAvailableRefundBalance,"
					+ "refundapprovebalance  AfterRefundApproveBalance, ");
			
		}else{
			sql.append(" null,null,null,null,null,null,null,null,null,null,null,null,null,null,null, ");
		}
		
		sql.append(serviceFlowRecord.getOperID()+",");
		sql.append(serviceFlowRecord.getPlaceID()+",");
		sql.append("sysdate,");
		sql.append(serviceFlowRecord.getIsNeedBlacklist()+",");
		sql.append(serviceFlowRecord.getIsDoFlag()+",");
		sql.append(serviceFlowRecord.getDoTime()+",");
		sql.append(serviceFlowRecord.getTransferSum()+",");
		sql.append(serviceFlowRecord.getReturnMoney()+",");
		
		sql.append(serviceFlowRecord.getTermCode()+",");
		sql.append(serviceFlowRecord.getTermTradeNo()+",");
		sql.append(serviceFlowRecord.getMac()+",");
		sql.append(serviceFlowRecord.getTac()+",");
		sql.append(serviceFlowRecord.getOfflineTradeNO()+",");
		sql.append(serviceFlowRecord.getOnLineTradeNO()+",");
		
		
		sql.append("'"+serviceFlowRecord.getOperNo()+"',");
		sql.append("'"+serviceFlowRecord.getOperName()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceNo()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceName()+"'");
		
		sql.append(" from csms_mainaccount_info m join csms_tag_info c on m.mainid = c.clientid where mainid = "+serviceFlowRecord.getClientID() +" and c.tagNo="+serviceFlowRecord.getOldCardTagNO());
		*///save(sql.toString());
	}
	
	
	public void saveBalanceServiceFlow(ServiceFlowRecord serviceFlowRecord) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_ServiceFlow_Record("
		+ "ID,ClientID,CardTagNo,OldCardTagNo,ServiceFlowNO,"
		+ "ServicePTypeCode,ServiceTypeCode,BeforeState,CurrState,"
		+ "AfterState,BeforeAvailableBalance,BeforeFrozenBalance,"
		+ "BeforepreferentialBalance,BeforeAvailableRefundBalance,"
		+ "BeforeRefundApproveBalance,CurrAvailableBalance,"
		+ "CurrFrozenBalance,CurrpreferentialBalance,CurrAvailableRefundBalance,"
		+ "CurrRefundApproveBalance,AfterAvailableBalance,AfterFrozenBalance,"
		+ "AfterpreferentialBalance,AfterAvailableRefundBalance,AfterRefundApproveBalance,"
		+ "OperID,PlaceID,createTime,IsNeedBlacklist,"
		+ "IsDoFlag,DoTime,TransferSum,ReturnMoney,TermCode,TermTradeNo,mac,tac,offlineTradeNo,onlineTradeNo,operNo,operName,placeNo,placeName) select  ");
		sql.append(serviceFlowRecord.getId()+",");
		sql.append(serviceFlowRecord.getClientID()+",");
		sql.append(serviceFlowRecord.getCardTagNO()+",");
		sql.append(serviceFlowRecord.getOldCardTagNO()+",");
		sql.append(serviceFlowRecord.getServiceFlowNO()+",");
		sql.append(serviceFlowRecord.getServicePTypeCode()+",");
		sql.append(serviceFlowRecord.getServiceTypeCode()+",");
		sql.append(serviceFlowRecord.getBeforeState()+",");
		sql.append(serviceFlowRecord.getCurrState()+",");
		sql.append(serviceFlowRecord.getAfterState()+",");
		
		
		
		sql.append(" availablebalance-("+serviceFlowRecord.getBeforeAvailableBalance()+") BeforeAvailableBalance ,"
				+ "frozenbalance-("+serviceFlowRecord.getBeforeFrozenBalance()+") BeforeFrozenBalance,"
				+ "preferentialbalance -("+serviceFlowRecord.getBeforepreferentialBalance()+")  BeforepreferentialBalance,"
				+ "availablerefundbalance-("+serviceFlowRecord.getBeforeAvailableRefundBalance()+")  BeforeAvailableRefundBalance,"
				+ "refundapprovebalance -("+serviceFlowRecord.getBeforeRefundApproveBalance()+")  BeforeRefundApproveBalance, ");
		
		sql.append(serviceFlowRecord.getCurrAvailableBalance()+",");
		sql.append(serviceFlowRecord.getCurrFrozenBalance()+",");
		sql.append(serviceFlowRecord.getCurrpreferentialBalance()+",");
		sql.append(serviceFlowRecord.getCurrAvailableRefundBalance()+",");
		sql.append(serviceFlowRecord.getCurrRefundApproveBalance()+",");
		
		sql.append(" availablebalance AfterAvailableBalance ,"
				+ "frozenbalance AfterFrozenBalance,"
				+ "preferentialbalance  AfterpreferentialBalance,"
				+ "availablerefundbalance  AfterAvailableRefundBalance,"
				+ "refundapprovebalance  AfterRefundApproveBalance, ");
		
		sql.append(" availablebalance+"+serviceFlowRecord.getAfterAvailableBalance()+" AfterAvailableBalance ,"
				+ "frozenbalance+"+serviceFlowRecord.getAfterFrozenBalance()+" AfterFrozenBalance,"
				+ "preferentialbalance +"+serviceFlowRecord.getAfterpreferentialBalance()+"  AfterpreferentialBalance,"
				+ "availablerefundbalance+"+serviceFlowRecord.getAfterAvailableRefundBalance()+"  AfterAvailableRefundBalance,"
				+ "refundapprovebalance +"+serviceFlowRecord.getAfterRefundApproveBalance()+"  AfterRefundApproveBalance, ");
		
		sql.append(serviceFlowRecord.getOperID()+",");
		sql.append(serviceFlowRecord.getPlaceID()+",");
		sql.append("sysdate,");
		sql.append(serviceFlowRecord.getIsNeedBlacklist()+",");
		sql.append(serviceFlowRecord.getIsDoFlag()+",");
		sql.append(serviceFlowRecord.getDoTime()+",");
		sql.append(serviceFlowRecord.getTransferSum()+",");
		sql.append(serviceFlowRecord.getReturnMoney()+",");
		
		sql.append(serviceFlowRecord.getTermCode()+",");
		sql.append(serviceFlowRecord.getTermTradeNo()+",");
		sql.append(serviceFlowRecord.getMac()+",");
		sql.append(serviceFlowRecord.getTac()+",");
		sql.append(serviceFlowRecord.getOfflineTradeNO()+",");
		sql.append(serviceFlowRecord.getOnLineTradeNO()+",");
		
		sql.append("'"+serviceFlowRecord.getOperNo()+"',");
		sql.append("'"+serviceFlowRecord.getOperName()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceNo()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceName()+"'");
		
		sql.append(" from csms_mainaccount_info where mainid = "+serviceFlowRecord.getClientID());*/
		//save(sql.toString());
	}
	
	
	
	
	/*@SuppressWarnings("rawtypes")
	public List halfList(String cardNo) {
		List list = new ArrayList<ServiceFlowRecord>();
		String sql = "select * from (select * from CSMS_SERVICEFLOW_RECORD where ADDSTATE=1 AND SERVICETYPECODE in(110,111) AND CardTagNo="+cardNo + " order by id desc) where ROWNUM<2";
		list = queryList(sql.toString());
		return list;
	}*/
	
	public int batchSaveServiceFlowRecord(final List<ServiceFlowRecord> list) {
        /*String sql = "insert into CSMS_ServiceFlow_Record(id,ServiceFlowNO,ServicePTypeCode,ServiceTypeCode,ClientID,CardTagNO) values(SEQ_csms_serviceflow_record_NO.nextval,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, list.get(i).getServiceFlowNO());
				ps.setInt(2, list.get(i).getServicePTypeCode());
				ps.setInt(3, list.get(i).getServiceTypeCode());
				ps.setLong(4, list.get(i).getClientID());
				ps.setString(5, list.get(i).getCardTagNO());
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});*/
		return 0;
    }
	

	public int batchSave(final List<ServiceFlowRecord> serviceFlowRecordList) {  
       /* String sql = "insert into CSMS_ServiceFlow_Record(ID,ClientID,CardTagNo,ServiceFlowNO"
        		+ "	,ServicePTypeCode,ServiceTypeCode,OPERID,PLACEID,createTime )  "
				+ "values(?,?,?,?,?,?,?,?,sysdate)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				ServiceFlowRecord serviceFlowRecord = serviceFlowRecordList.get(i);
				ps.setLong(1, serviceFlowRecord.getId());
				ps.setLong(2, serviceFlowRecord.getClientID());
				ps.setString(3, serviceFlowRecord.getCardTagNO());
				ps.setString(4, serviceFlowRecord.getServiceFlowNO());
				ps.setLong(5, serviceFlowRecord.getServicePTypeCode());
				ps.setLong(6, serviceFlowRecord.getServiceTypeCode());
				if(serviceFlowRecord.getOperID()!=null){
					ps.setLong(7, serviceFlowRecord.getOperID());
				}else{
					ps.setNull(7, Types.BIGINT);
				}
				if(serviceFlowRecord.getPlaceID()!=null){
					ps.setLong(8, serviceFlowRecord.getPlaceID());
				}else{
					ps.setNull(8, Types.BIGINT);
				}
				
			}	
			
			@Override
			public int getBatchSize() {
				 return serviceFlowRecordList.size();
			}
		});*/
		return 0;
    }
	
	
	public void savePrepaidServiceFlow(ServiceFlowRecord serviceFlowRecord,PrepaidC prepaidC) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_ServiceFlow_Record("
		+ "ID,ClientID,CardTagNo,OldCardTagNo,ServiceFlowNO,"
		+ "ServicePTypeCode,ServiceTypeCode,BeforeState,CurrState,"
		+ "AfterState,OperID,PlaceID,createTime,IsNeedBlacklist,operNo,operName,placeNo,placeName) select ");
		sql.append(serviceFlowRecord.getId()+",");
		sql.append(serviceFlowRecord.getClientID()+",");
		sql.append(serviceFlowRecord.getCardTagNO()+",");
		sql.append(serviceFlowRecord.getOldCardTagNO()+",");
		sql.append(serviceFlowRecord.getServiceFlowNO()+",");
		sql.append(serviceFlowRecord.getServicePTypeCode()+",");
		sql.append(serviceFlowRecord.getServiceTypeCode()+",");
		sql.append("p.State,");
		sql.append(serviceFlowRecord.getCurrState()+",");
		sql.append(serviceFlowRecord.getAfterState()+",");
		sql.append(serviceFlowRecord.getOperID()+",");
		sql.append(serviceFlowRecord.getPlaceID()+",");
		sql.append("sysdate,");
		sql.append(serviceFlowRecord.getIsNeedBlacklist()+",");
		sql.append("'"+serviceFlowRecord.getOperNo()+"',");
		sql.append("'"+serviceFlowRecord.getOperName()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceNo()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceName()+"'");
		sql.append(" from csms_prepaidc p where id = "+prepaidC.getId());
		save(sql.toString());*/
	}
	
	public void saveAccountServiceFlow(ServiceFlowRecord serviceFlowRecord,AccountCInfo accountC) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_ServiceFlow_Record("
		+ "ID,ClientID,CardTagNo,OldCardTagNo,ServiceFlowNO,"
		+ "ServicePTypeCode,ServiceTypeCode,BeforeState,CurrState,"
		+ "AfterState,OperID,PlaceID,createTime,IsNeedBlacklist,operNo,operName,placeNo,placeName) select ");
		sql.append(serviceFlowRecord.getId()+",");
		sql.append(serviceFlowRecord.getClientID()+",");
		sql.append(serviceFlowRecord.getCardTagNO()+",");
		sql.append(serviceFlowRecord.getOldCardTagNO()+",");
		sql.append(serviceFlowRecord.getServiceFlowNO()+",");
		sql.append(serviceFlowRecord.getServicePTypeCode()+",");
		sql.append(serviceFlowRecord.getServiceTypeCode()+",");
		sql.append("ac.State,");
		sql.append(serviceFlowRecord.getCurrState()+",");
		sql.append(serviceFlowRecord.getAfterState()+",");
		sql.append(serviceFlowRecord.getOperID()+",");
		sql.append(serviceFlowRecord.getPlaceID()+",");
		sql.append("sysdate,");
		sql.append(serviceFlowRecord.getIsNeedBlacklist()+",");
		sql.append("'"+serviceFlowRecord.getOperNo()+"',");
		sql.append("'"+serviceFlowRecord.getOperName()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceNo()+"',");
		sql.append("'"+serviceFlowRecord.getPlaceName()+"'");
		sql.append(" from csms_accountc_info ac where id = "+accountC.getId());
		save(sql.toString());*/
	}
}
