package com.hgsoft.clearInterface.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.RelieveStopPay;
import com.hgsoft.clearInterface.entity.StopPayRelieveApply;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Repository
public class StopPayRelieveApplyDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	
	
	/**
	 * 子账户ID查找记帐卡交易明细对象
	 * @param id
	 * @param startTime
	 * @param nowTime
	 * @return
	 */
	public List<RelieveStopPay> findListRelieveStopPay(String bankAccount, String startTime, String endTime) {
        StringBuilder sqlBuilder = new StringBuilder();
        	 sqlBuilder.append("  SELECT etcFee,lateFee,LateFeeStartTime,applyTime  FROM CSMS_RELIEVE_STOPPAY   ")
             .append(" WHERE  BACCOUNT=? ")
        	 .append(" AND to_char(LateFeeStartTime,'YYYYMMDDHH24MISS')>= ?")
        	 .append(" AND to_char(LateFeeStartTime,'YYYYMMDDHH24MISS')<= ?");
        	 
        	 return super.queryObjectList(sqlBuilder.toString(), RelieveStopPay.class, bankAccount,startTime, endTime);
	}
	
	public void saveStopPayRelieveApply(StopPayRelieveApply stopPayRelieveApply){
		stopPayRelieveApply.setId(sequenceUtil.getSequenceLong("SEQ_TBAPPLYREMOVEPAYSEND_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_APPLYREMOVEPAYMENT_SEND(");
		sql.append(FieldUtil.getFieldMap(StopPayRelieveApply.class, stopPayRelieveApply).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(StopPayRelieveApply.class, stopPayRelieveApply).get("valueStr")
				+ ")");
		save(sql.toString());
	}
	//获取最后一次扣款时间，即指令生成时间
	public String findStopPayFeeStartDate(String acbAccount){
		StringBuffer sql = new StringBuffer("SELECT GENTIME FROM CSMS_ACCBANKLISTRETURN_HIS WHERE ACBACCOUNT = '"+acbAccount+"' AND STATUS ='0' ");
		sql.append(" UNION ");
		sql.append("SELECT GENTIME FROM CSMS_ACCBANKLISTRETURNHK_HIS WHERE ACBACCOUNT = '"+acbAccount+"' AND STATUS ='0' ");
		List<Map<String,Object>> list = queryList("SELECT to_char(MAX(GENTIME),'YYYYMMDDHH24MISS') AS GENTIME FROM("+sql.toString()+")");
		if(list.size()>0&&list.get(0).get("GENTIME")!=null){
			return (String)list.get(0).get("GENTIME");
		}else{
			return null;
		}
	}
	
	//如果不存在任意一次扣款记录，那么计算账号的最早一次通行时间
		public Timestamp findEarliestDate(String cardnos,String newtime) {
			List<Map<String,Object>> list=new ArrayList<>();
			StringBuilder sql = new StringBuilder("select min(balanceTime) as balanceTime from CSMS_AC_TRADE_DETAILINFO where CARDNO IN("+cardnos+") ");
			sql.append(" and DEALSTATUS=0 and PAYFLAG=0");
			if(newtime!=null){
				sql.append("and to_char(BALANCETIME,'YYYYMMDDHH24MISS')>?");
				list = queryList(sql.toString(),newtime);
			}else{
				list = queryList(sql.toString());
			}
			if(list.size()>0){
				return (Timestamp)list.get(0).get("BALANCETIME");
			}
			return null;
		}
		
	public Pager stopPageDetail(Pager pager,RelieveStopPay relieveStopPay){
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,CARDNO,BACCOUNT," +
				"LATEFEESTARTTIME," +
				"ETCFEESTARTTIME," +
				"FEEENDTIME," +
				"APPLYTIME," +
				"ETCFEE,LATEFEE,REMARK "+
				"from CSMS_RELIEVE_STOPPAY_DETAIL  ");

		sql.append(" where RELIEVESTOPPAYID = "+relieveStopPay.getId()+" ");
		
		return findByPages(sql.toString(), pager, null);
	}
	
	public List<Map<String,Object>> findEtcFee(String startTime,String endTime,String cardno){
		if(!"".equals(cardno)){
			StringBuffer sql = null;
			if (startTime!=null) {
				sql = new StringBuffer("SELECT PAYFLAG,DETAILNO,DEALSTATUS,REALTOLL FROM CSMS_AC_TRADE_DETAILINFO  ");
				sql.append("WHERE CARDNO = '"+cardno+ "'");
				sql.append(" AND DEALSTATUS=0 AND PAYFLAG=0");
				sql.append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')>= ?");
				sql.append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')<= ?");
				List<Map<String,Object>> list = queryList(sql.toString(),startTime,endTime);
				
				if(list.size()>0){
//					realToll = (BigDecimal)list.get(0).get("REALTOLL");
//					return realToll;
					return list;
				}
			}else {
				sql = new StringBuffer("SELECT PAYFLAG,DETAILNO,DEALSTATUS,REALTOLL FROM CSMS_AC_TRADE_DETAILINFO  ");
				sql.append("WHERE CARDNO = '"+cardno+ "'");
				sql.append(" AND DEALSTATUS=0 AND PAYFLAG=0");
				List<Map<String,Object>> list = queryList(sql.toString());
				
				if(list.size()>0){
//					realToll = (BigDecimal)list.get(0).get("REALTOLL");
//					return realToll;
					return list;
				}
			}
		}
		return null;
	}
	
	public BigDecimal findOtherProvinceEtcFee(String startTime,String endTime,String cardnos){
		if(!"".equals(cardnos)){
//			cardnos = cardnos.substring(0, cardnos.length()-1);
			StringBuffer sql = new StringBuffer("SELECT SUM(AA.REALTOLL) AS REALTOLL FROM TB_CARDOUTSETTLEDETAIL_RECV AA ");
			sql.append("WHERE CARDCODE IN ("+cardnos+") ");
			sql.append(" AND to_char(BALANCETIME,'YYYYMMDDHH24MISS')>= ? ");
			sql.append(" and to_char(BALANCETIME,'YYYYMMDDHH24MISS')<= ? ");
			List<Map<String,Object>> list = queryList(sql.toString(),startTime,endTime);
			if(list.size()>0){
				return (BigDecimal)list.get(0).get("REALTOLL");
			}
		}
		return null;
	}

	/**
	 * 根据银行账号获取最近的银行回盘结果
	 * @param bankAccount
	 * @return
     */
	public String findMaxAccBankListRecvByBankAccount(String bankAccount){
		StringBuffer sql = new StringBuffer("SELECT STATUS FROM CSMS_ACCBANKLISTRETURN_HIS ");
		sql.append("WHERE HDLDATETIME = ( SELECT MAX( HDLDATETIME ) FROM CSMS_ACCBANKLISTRETURN_HIS ) AND ACBACCOUNT = ?");
		List<Map<String,Object>> list = queryList(sql.toString(),bankAccount);
		if(list!=null&&!list.isEmpty()){
			return list.get(0).get("STATUS").toString();
		}else {
			return null;
		}
	}

	
	
	/**
	 * 根据银行账号查找最新下止付的时间
	 * @author HZW
	 * @param bankaccout
	 * @param cardnos
	 * @return
	 */
	public Timestamp findNewStopPayMenuTime(String bankAccount){
		List<Map<String,Object>> list=new ArrayList<>();
		if(bankAccount!=null){
			String sql = "select flag,gentime from CSMS_PAYMENTCARDBLACKLIST where  " +
					"  acbAccount=?  order by BOARDLISTNO desc,gentime desc";
			list = queryList(sql,bankAccount);
		}
		if(list.size() <= 0){
			return null;
		}
		//当处理标志为 非"已经解除成功"的记录才返回
		BigDecimal flag = (BigDecimal) list.get(0).get("FLAG");
		if(list.size()>0 && new BigDecimal(3).compareTo(flag)!=0){
			return (Timestamp)list.get(0).get("GENTIME");
		}else{
			return null;
		}
	}
	
	/**
	 * 查找最近一条  铭鸿的   止付卡黑名单全量数据
	 * @param bankAccount
	 * @2017年10月17日19:30:38 更新
	 * 返回true就是止付的
	 */
	public boolean checkStopBlackList(String bankAccount){
		List<Map<String,Object>> list=new ArrayList<>();
		if(bankAccount!=null){
			String sql = "select flag from CSMS_PAYMENTCARDBLACKLIST where  " 
						+ "  acbAccount=?  order by BOARDLISTNO desc,gentime desc ";
			list = queryList(sql,bankAccount);
		}
		//0：未处理； 1：已处理； 2：申请解除止付；3：解除成功
		if(list.size() <= 0){
			return false;
		}
		BigDecimal flag = (BigDecimal)(list.get(0).get("FLAG"));
		//0,1,2的状态都是止付黑名单
		if(new BigDecimal(3).compareTo(flag) != 0){
			return true;
		}else{
			return false;
		}
	}

	public List<Map<String,Object>> getPayTimeList(String account,String cardnos,String endTime) {
		 
		StringBuffer sql = new StringBuffer("SELECT GENTIME FROM TB_ACCBANKLISTRETURN_RECV WHERE ACBACCOUNT = ? AND STATUS in ('0','1') AND to_char(GENTIME,'yyyy-MM-dd') > ?");
		sql.append(" UNION ");
		sql.append("SELECT GENTIME FROM TB_ACCBANKLISTRETURNHK_RECV WHERE ACBACCOUNT = ? AND STATUS in('0','1') AND to_char(GENTIME,'yyyy-MM-dd') > ?");
		List<Map<String,Object>> list = queryList("SELECT to_char(GENTIME,'YYYYMMDDHH24MISS') AS GENTIME FROM("+sql.toString()+")", account, endTime, account, endTime);
		 
		return list.size()>0?list:null;
	}

	public List<Map<String,Object>> findStopPayTimeList(String bankNo, String startTime,
			String endTime) {
		 
		List<String> paramList = new ArrayList<String>();
		StringBuffer sql = new StringBuffer("select  pcr.GenTime  from tb_paymentcardblacklist_recv pcr where 1=1 ");//这个是敏鸿的表
		 
		if(StringUtil.isNotBlank(bankNo)){
			paramList.add(bankNo);
			sql.append(" and pcr.AcbAccount in (?) ");
		}
		if(StringUtil.isNotBlank(startTime)){
			paramList.add(startTime);
			sql.append(" and to_char(pcr.GenTime,'yyyy-MM-dd') > ? ");
		}
		if(StringUtil.isNotBlank(endTime)){
			paramList.add(endTime);
			sql.append(" and to_char(pcr.GenTime,'yyyy-MM-dd') < ? ");
		}
		sql=sql.append(" order by pcr.AcbAccount");
		List<Map<String,Object>> list=queryList(sql.toString(),paramList.toArray());
		return list.size()>0?list:null;
	}

}
