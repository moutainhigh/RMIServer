package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.accountC.entity.HandPaymentDetail;
import com.hgsoft.clearInterface.entity.HandlePayToll;
import com.hgsoft.clearInterface.entity.RelieveStopPay;
import com.hgsoft.clearInterface.entity.RelieveStopPayDetail;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangzhongji on 17/9/28.
 */
@Repository
public class HandPaymentDao extends BaseDao {

    public List<HandPayment> listByBaccountAndPayTime(String baccount, Date payTime) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT * ")
                .append(" FROM CSMS_HANDPAYMENT ")
                .append(" WHERE baccount=? and paytime=? ");

        return super.queryObjectList(sqlBuilder.toString(), HandPayment.class,
                baccount, payTime);
    }
	/**
	 * 手工缴纳通行费用：
	 * 获取需要缴纳的通行流水的最后时间
	 * @return String
	 */
	public String getRecentlyClearTime(){
		Date newDate = new Date(); 
		String endTime = "";
		Map<String, Object> clearStateMap = this.findRecentlyClearResultState();
		if(clearStateMap != null && clearStateMap.get("BALANCETIME") != null){
			Timestamp balanceTime = (Timestamp) clearStateMap.get("BALANCETIME");
			//就要过滤到balanceTime前的通行费数据
			if(balanceTime.getTime() <= newDate.getTime()){
				endTime = DateUtil.formatDate(new Date(balanceTime.getTime()), "yyyyMMddHHmmss");
			}else{
				endTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
			}
		}else{
			endTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
		}
		return endTime;
	}
	/**
	 * 查找最近一条清算的状态记录
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findRecentlyClearResultState(){
		String sql = "select ID,BALANCETIME  from CSMS_CLEARRESULTSTATE order by id desc";
		List<Map<String, Object>> list = queryList(sql);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据银行账号查询 记账卡转账中间数据  ，用户校验统计出来的卡号通行费是否准确
	 * @param bankAccount
	 * @return List<Map<String,Object>>
	 * select ACCODE,MAX(ID) MAXID FROM CSMS_ACCCARDVIREMENTMID WHERE STAT='00' and USACCOUNT=? group by ACCODE
	 * 子查询的原因是为了查询每个卡号最新的生成时间，从而获取最新的汇总数据
	 */
	public List<Map<String, Object>> findAccTransferMid(String bankAccount){
		List<Map<String, Object>> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer(
        		  " SELECT A.ID,A.ACCODE CARDNO,USACCOUNT,LATEFEE,REALDEALFEE FROM (select ACCODE,MAX(genTime) MAXGenTime FROM CSMS_ACCCARDVIREMENTMID WHERE STAT='00' and USACCOUNT=? group by ACCODE) B"
        		+ " JOIN CSMS_ACCCARDVIREMENTMID A ON A.ACCODE=B.ACCODE AND B.MAXGenTime=A.genTime ORDER BY CARDNO DESC");
        list = queryList(sql.toString(),bankAccount);
        return list;
	}
	
	/**
	 * 保存HandPayMent
	 * @param relieveStopPay
	 */
	public void save(HandPayment handPayment) {
		StringBuffer sql=new StringBuffer("insert into CSMS_HANDPAYMENT(");
		sql.append(FieldUtil.getFieldMap(HandPayment.class,handPayment).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(HandPayment.class,handPayment).get("valueStr")+")");
		save(sql.toString());
	}
	
	/**
	 * 手工缴纳通行费列表
	 * @param pager
	 * @param paymentCardBlacklistRecv
     * @return
     */
	public Pager findAcctollcollectList(Pager pager, String bankAccount){
		StringBuffer sql = new StringBuffer();
		if(bankAccount!=null){
			sql.append("select ch.id,ch.BACCOUNT,ch.PAYTIME," +
					"ch.STARTTIME,ch.ENDTIME,ch.etcfee,ch.flag,ch.remark,ch.operName, ch.placeNo,ch.placeName from CSMS_HANDPAYMENT ch  " +
					"where ch.BACCOUNT = '"+bankAccount+"'order by ch.payTime desc ");
			pager=findByPages(sql.toString(), pager,null);
		}else {
			sql.append("select ch.id,ch.BACCOUNT,ch.PAYTIME," +
					"ch.STARTTIME,ch.ENDTIME,ch.etcfee,ch.flag,ch.remark,ch.operName, ch.placeNo,ch.placeName  from CSMS_HANDPAYMENT ch  " +
					"order by ch.payTime desc ");
			pager=findByPages(sql.toString(), pager,null);
		}
		return pager;
	}
	/**
	 * 列表详情查询方法
	 * @param pager
	 * @param relieveStopPay
	 * 2017年10月15日16:51:22
	 * @return
	 */
	public Pager accTollPageDetail(Pager pager,HandPayment handPayment){
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,CARDNO,BACCOUNT,STARTTIME,ENDTIME,ETCFEE,PAYTIME, " +
				"REMARK,operName,placeNo,placeName "+
				"from CSMS_HANDPAYMENT_DETAIL ");
		sql.append(" where handPayMentId = "+handPayment.getId()+" ");
		
		return findByPages(sql.toString(), pager, null);
	}

	/**
	 * 校验 记帐卡通行流水与记帐卡转账中间数据
	 * @param acEtcFeeList 需要按照卡号order by cardno desc
	 * @param accTransferMidList 需要按照卡号order by cardno desc
	 * @return String
	 */
	public String checkTollAvailable(List<Map<String, Object>> acEtcFeeList,List<Map<String, Object>> accTransferMidList){
		String message = "";
		if(acEtcFeeList.size() != accTransferMidList.size()){
			message = "记帐卡通行流水与记帐卡转账中间数据不一致";
			return message;
		}
		
		for(int i = 0;i < acEtcFeeList.size();i++){
			String acEtcFeeCardNo = (String) acEtcFeeList.get(i).get("CARDNO");
			String accTransferMidCardNo = (String) accTransferMidList.get(i).get("CARDNO");
			//两个list集合里面的卡号要排序好查询出来
			if(!acEtcFeeCardNo.equals(accTransferMidCardNo)){
				message = "记帐卡通行流水与记帐卡转账中间数据不一致";
				return message;
			}
			//判断两个集合的卡号对应通行费是否一致
			BigDecimal acEtcFeeTollFee = (BigDecimal) acEtcFeeList.get(i).get("REALTOLLALL");
			BigDecimal accTransferMidTollFee = (BigDecimal) accTransferMidList.get(i).get("TOLLFEE");
			if(acEtcFeeTollFee.compareTo(accTransferMidTollFee) != 0){
				message = "记帐卡通行流水与记帐卡转账中间数据不一致";
				return message;
			}
		}
		
		return message;
	}
	
	/**
	 * 获取抵扣列表详情(手工缴纳通行费用)
	 * @param pager
	 * @param cardnos
	 * @param newtime
	 * @return Pager
	 */
	public Pager deductionDetail4PayToll(Pager pager,String cardnos,String startTime,String endTime){
		StringBuffer sql = new StringBuffer();
		//只显示  记帐卡卡号 车道序列号 入口路段名称 入口站名 入口时间 
		//出口路段名称 出口站名 出口时间 实收金额 优惠金额 应收金额
		sql.append("select CARDNO,TABLEID,ENTRANCEROADNAME,ENTRANCESTATIONNAME," +
				"TO_CHAR(ENTRANCETIME,'YYYY-MM-DD HH24:MI:SS') ENTRANCETIME," +
				"EXITROADNAME,EXITSTATIONNAME,"+
				"TO_CHAR(EXITTIME,'YYYY-MM-DD HH24:MI:SS') EXITTIME," +
				"REALTOLL,DISCOUNTAMOUNT,TOLL,row_number() over (order by BalanceTIME desc) as num " +
				"from CSMS_AC_TRADE_DETAILINFO ");

		sql.append(" where CARDNO in ("+cardnos+") AND DEALSTATUS=0 AND PAYFLAG=0 ");
		
		SqlParamer sqlParamer = new SqlParamer();
		if(StringUtil.isNotBlank(startTime)){
			sqlParamer.ge("to_char(BALANCETIME,'YYYYMMDDHH24MISS')", startTime);
		}
		sqlParamer.le("to_char(BALANCETIME,'YYYYMMDDHH24MISS')", endTime);
		
		sql.append(sqlParamer.getParam());
		sql=sql.append(" order by BalanceTIME ");
		return findByPages(sql.toString(), pager, sqlParamer.getList().toArray());
	}
	/**
	 * 批量插入手工缴纳通行费明细
	 * @param reDetailList
	 * @return
	 */
	public int[] batchSaveDetail(final List<HandPaymentDetail> HandPaymentDetailList) {
        String sql = "insert into CSMS_HANDPAYMENT_DETAIL(id,cardNo,handPayMentId,bacCount,payTime,startTime,endTime,etcFee,"
        		+ "operId,operNo,operName,placeId,placeNo,placeName,remark) "
        		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				HandPaymentDetail handPaymentDetail = HandPaymentDetailList.get(i);
				if(handPaymentDetail.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, handPaymentDetail.getId());
				if(handPaymentDetail.getCardNo()==null)ps.setNull(2, Types.VARCHAR);else ps.setString(2, handPaymentDetail.getCardNo());
				if(handPaymentDetail.getHandPayMentId()==null)ps.setNull(3, Types.BIGINT);else ps.setLong(3, handPaymentDetail.getHandPayMentId());
				if(handPaymentDetail.getBaccount()==null)ps.setNull(4, Types.VARCHAR);else ps.setString(4, handPaymentDetail.getBaccount());
				if(handPaymentDetail.getPayTime()==null)ps.setNull(5, Types.DATE);else ps.setTimestamp(5, new java.sql.Timestamp(handPaymentDetail.getPayTime().getTime()));
				if(handPaymentDetail.getStartTime()==null)ps.setNull(6, Types.DATE);else ps.setTimestamp(6, new java.sql.Timestamp(handPaymentDetail.getStartTime().getTime()));
				if(handPaymentDetail.getEndTime()==null)ps.setNull(7, Types.DATE);else ps.setTimestamp(7, new java.sql.Timestamp(handPaymentDetail.getEndTime().getTime()));
				if(handPaymentDetail.getEtcFee()==null)ps.setNull(8, Types.DECIMAL);else ps.setBigDecimal(8, handPaymentDetail.getEtcFee());
				if(handPaymentDetail.getOperId()==null)ps.setNull(9, Types.BIGINT);else ps.setLong(9, handPaymentDetail.getOperId());
				if(handPaymentDetail.getOperNo()==null)ps.setNull(10, Types.VARCHAR);else ps.setString(10, handPaymentDetail.getOperNo());
				if(handPaymentDetail.getOperName()==null)ps.setNull(11, Types.VARCHAR);else ps.setString(11, handPaymentDetail.getOperName());
				if(handPaymentDetail.getPlaceId()==null)ps.setNull(12, Types.BIGINT);else ps.setLong(12, handPaymentDetail.getPlaceId());
				if(handPaymentDetail.getPlaceNo()==null)ps.setNull(13, Types.VARCHAR);else ps.setString(13, handPaymentDetail.getPlaceNo());
				if(handPaymentDetail.getPlaceName()==null)ps.setNull(14, Types.VARCHAR);else ps.setString(14, handPaymentDetail.getPlaceName());
				if(handPaymentDetail.getRemark()==null)ps.setNull(15, Types.VARCHAR);else ps.setString(15, handPaymentDetail.getRemark());
				
			}
			@Override
			public int getBatchSize() {
				 return HandPaymentDetailList.size();
			}
		});
    }
	
	/**
	 * 批量插入手工缴纳通行费数据清算表
	 * @param handlePayTolls
	 * @return
	 */
	public int[] batchSaveHandlePayToll(final List<HandlePayToll> handlePayTolls) {
        String sql = "insert into TB_PAYTOLLBYHAND_SEND(id,bacCount,payAccount,payFlag,cardNo,handPayTime,"
        		+ "handPayFee,updateTime,bankFlag,remark,boardListNo) "
        		+ "values(?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				HandlePayToll handlePayToll = handlePayTolls.get(i);
				if(handlePayToll.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, handlePayToll.getId());
				if(handlePayToll.getBaccount() ==null)ps.setNull(2, Types.LONGVARCHAR);else ps.setString(2, handlePayToll.getBaccount());
				if(handlePayToll.getPayAccount() ==null)ps.setNull(3, Types.LONGVARCHAR);else ps.setString(3, handlePayToll.getPayAccount());
				if(handlePayToll.getPayFlag() ==null)ps.setNull(4, Types.VARCHAR);else ps.setString(4, handlePayToll.getPayFlag());
				if(handlePayToll.getCardNo()==null)ps.setNull(5, Types.VARCHAR);else ps.setString(5, handlePayToll.getCardNo());
				if(handlePayToll.getHandPayTime()==null)ps.setNull(6, Types.DATE);else ps.setTimestamp(6, new java.sql.Timestamp(handlePayToll.getHandPayTime().getTime()));
				if(handlePayToll.getHandPayFee()==null)ps.setNull(7, Types.DECIMAL);else ps.setBigDecimal(7, handlePayToll.getHandPayFee());
				if(handlePayToll.getUpdateTime()==null)ps.setNull(8, Types.DATE);else ps.setTimestamp(8, new java.sql.Timestamp(handlePayToll.getUpdateTime().getTime()));
				if(handlePayToll.getBankFlag()==null)ps.setNull(9, Types.VARCHAR);else ps.setString(9, handlePayToll.getBankFlag());
				if(handlePayToll.getRemark()==null)ps.setNull(10, Types.VARCHAR);else ps.setString(10, handlePayToll.getRemark());
				if(handlePayToll.getBoardListNo()==null)ps.setNull(11, Types.BIGINT);else ps.setLong(11, handlePayToll.getBoardListNo());
				
			}
			@Override
			public int getBatchSize() {
				 return handlePayTolls.size();
			}
		});
    }
	
}
