package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.daysettle.entity.DaySetRecord;
import com.hgsoft.daysettle.entity.SumDaySettle;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class DaySetRecordDao extends BaseDao{

	public Pager list(Pager pager,Date starTime ,Date endTime,DaySetRecord daySetRecord){
		String sql="select r.OperID,r.MacAddress,r.DifferentialMarker,r.OperTime,r.OperPlaceID,r.Memo,r.ID,r.settleDay, "
				/*+ ",d.id as detailId,d.MainID,d.FeeType,d.SystemFee,d.HandFee,d.LSadjustFee,d.DifferenceFee, "*/
				+ " ROWNUM as num  from "
//				+ "CSMS_DaySet_Detail as d join "
				+ " CSMS_DaySet_Record r "
//				+ "on d.mainId=r.id "
				+ " where 1=1 and OperPlaceID is not null  ";
		SqlParamer params=new SqlParamer();
		/*if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.OperTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS')  ");
		}
		if(endTime != null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.OperTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}*/
		if(starTime !=null){
			params.geDate("r.OperTime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("r.OperTime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(daySetRecord.getSettleDay())){
			params.eq("r.settleDay",daySetRecord.getSettleDay());
		}
		if(StringUtil.isNotBlank(daySetRecord.getDifferentialMarker())){
			params.eq("r.DifferentialMarker",daySetRecord.getDifferentialMarker());
		}
		if(daySetRecord.getOperPlaceID()!=null){
			params.eq("r.OperPlaceID",daySetRecord.getOperPlaceID());
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by  r.settleDay desc ");
		return this.findByPages(sql, pager,Objects);
	}

	public void updateDifferentialMarkerById(Long id,Long hisSeqID, String type){
		String sql="update  CSMS_DaySet_Record set DifferentialMarker=?,HisSeqID=? where id=?";
		this.jdbcUtil.getJdbcTemplate().update(sql, new Object[]{type,hisSeqID,id});
	}

	public String findDaySettleList(Long operId, List<String> placeList){
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		if(operId!=null){
			sql.append( "select settleDay from CSMS_DaySet_Record where placeno in("); 
			for (int i = 0; i < placeList.size(); i++) {
				sql.append("'"+placeList.get(i)+"'");
				if(i!=placeList.size()-1){
					sql.append(" , ");
				}
			}
			sql.append(") order by settleDay desc FETCH FIRST 1 ROW ONLY ");
		}else{
			sql.append( "select settleDay from CSMS_DaySet_Record where operPlaceId is null order by settleDay desc FETCH FIRST 1 ROW ONLY"); 
		}
		list = queryList(sql.toString());
		String daySettle=null;
		if (!list.isEmpty()) {
			daySettle=list.get(0).get("settleDay").toString();
		}
		return daySettle;
	}
	
	public void save(DaySetRecord daySetRecord) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_DaySet_Record(");
		sql.append(FieldUtil.getFieldMap(DaySetRecord.class,daySetRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(DaySetRecord.class,daySetRecord).get("valueStr")+")");
		save(sql.toString());*/
		
		daySetRecord.setHisSeqID(-daySetRecord.getId());
		Map map = FieldUtil.getPreFieldMap(DaySetRecord.class,daySetRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_DaySet_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public DaySetRecord find(DaySetRecord daySetRecord) {
		DaySetRecord temp = null;
		if (daySetRecord != null) {
			/*StringBuffer sql = new StringBuffer("select * from CSMS_DaySet_Record where 1=1 ");
			sql.append(FieldUtil.getFieldMap(DaySetRecord.class,daySetRecord).get("nameAndValueNotNull"));
			sql.append(" order by SETTLEDAY desc");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			StringBuffer sql = new StringBuffer("select * from CSMS_DaySet_Record ");
			Map map = FieldUtil.getPreFieldMap(DaySetRecord.class,daySetRecord);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by SETTLEDAY desc");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new DaySetRecord();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public SumDaySettle checkDaySettleAmt(String startTime,String endTime,Long placeId,List<String> placeList,String settleDay){
		System.out.println(startTime+"----------------"+endTime);
		SumDaySettle sumDaySettle = new SumDaySettle();
		try {
			//查询缴款 and TransactionType = '1'  and iscorrect is null
			StringBuffer rechargeSql=new StringBuffer(" select sum(TakeBalance)/100 as TakeBalance,PayMentType from csms_rechargeinfo "
					+ " where   "
					+ " to_char(OperTime,'YYYYMMDDHH24MISS')>=? and to_char(OperTime,'YYYYMMDDHH24MISS')<=? and placeno in( ");
			//长短款    日结后差异修正结果
			StringBuffer afterDaySetSql=new StringBuffer(" select sum(DifferenceFee)/100 as DifferenceFee,FeeType from CSMS_AfterDaySetFee"
					+ " where to_char(OperTime,'YYYYMMDDHH24MISS')>=? and to_char(OperTime,'YYYYMMDDHH24MISS')<=? and placeno in( ");
			
			//查询电子标签提货金额
			StringBuffer tagTakeFeeSql = new StringBuffer("select sum(ChargeFee)/100 as ChargeFee,ChargeType from csms_tagtakefee_info "
					+ " where  to_char(RegisterDate,'YYYYMMDDHH24MISS')>=?  and to_char(RegisterDate,'YYYYMMDDHH24MISS')<=? and placeno in( ");
			//网点缴款
			StringBuffer handFee = new StringBuffer(
					" select feetype,sum(handfee/100) handfee from csms_registerdayset_record r "
					+ " join csms_registerdayset_detail d on r.id = d.mainid  where r.settleday  = "+settleDay+" and placeno in(  ");
			
			//查询上一次日结的未缴存金额
			StringBuffer daySetHandSql = new StringBuffer("select HandFee/100 as HandFee from csms_dayset_record r join csms_dayset_detail d on r.id = d.mainid"
					+ " where  FeeType = '3' and placeno in( ");
			
			
			//业务总额
			StringBuffer rechargeAmt = new StringBuffer(
				" select sum(afterAvailableBalance-beforeAvailableBalance)/100 as FEE from csms_accountfundchange a " +
						"where  a.changetype not in('0','1','2','3','4','5','36','37','38') "
						+" and  to_char(a.chgdate,'YYYYMMDDHH24MISS')>="+startTime+"  "
						+" and to_char(a.chgdate,'YYYYMMDDHH24MISS')<="+endTime+" and placeno in(  "
			);
			//业务总额
			StringBuffer tagTakeAmt = new StringBuffer(
					" select sum(TotalPrice)/100*-1 FEE from CSMS_TagTake_Info a " +
							"where  to_char(takedate,'YYYYMMDDHH24MISS')>="+startTime+"  "
							+" and to_char(takedate,'YYYYMMDDHH24MISS')<="+endTime+" and placeno in(  "
				);
			
			
			for (int i = 0; i < placeList.size(); i++) {
				rechargeSql.append("'"+placeList.get(i)+"'");
				afterDaySetSql.append("'"+placeList.get(i)+"'");
				tagTakeFeeSql.append("'"+placeList.get(i)+"'");
				handFee.append("'"+placeList.get(i)+"'");
				daySetHandSql.append("'"+placeList.get(i)+"'");
				rechargeAmt.append("'"+placeList.get(i)+"'");
				tagTakeAmt.append("'"+placeList.get(i)+"'");
				
				if(i!=placeList.size()-1){
					rechargeSql.append(" , ");
					afterDaySetSql.append(" , ");
					tagTakeFeeSql.append(" , ");
					handFee.append( " , ");
					daySetHandSql.append(" , ");
					rechargeAmt.append(" , ");
					tagTakeAmt.append(" , ");
				}
			}
			rechargeSql.append(" ) group by PayMentType ");
			afterDaySetSql.append(" ) group by FeeType ");
			tagTakeFeeSql.append(" ) group by ChargeType ");
			handFee.append( " )group by feetype ");
			daySetHandSql.append( " )order by r.settleday desc  FETCH FIRST 1 ROW ONLY ");
			rechargeAmt.append(" ) ");
			tagTakeAmt.append(" ) ");
			
			StringBuffer serviceAllAmt = new StringBuffer(
					" select sum(fee) as FEE from ("
					+rechargeAmt
					+" union all "
					+tagTakeAmt
					+")"
				);
			
			//查询日结后资金修正
			System.out.println("缴款记录："+rechargeSql);
			System.out.println("日节后资金修正："+afterDaySetSql);
			System.out.println("上一次日结未缴存金额："+daySetHandSql);
			System.out.println("电子标签提货金额："+tagTakeFeeSql);
			System.out.println("网点登记金额："+handFee);
			System.out.println("业务总额："+serviceAllAmt);
			List<Map<String, Object>> rechargeList = queryList(rechargeSql.toString(),startTime,endTime);
			List<Map<String, Object>> afterDaySetList = queryList(afterDaySetSql.toString(),startTime,endTime);
			List<Map<String, Object>> tallyList = queryList(daySetHandSql.toString());
			List<Map<String, Object>> tagTakeFeeList = queryList(tagTakeFeeSql.toString(),startTime,endTime);
			List<Map<String, Object>> handFeeList = queryList(handFee.toString());
			List<Map<String, Object>> serviceAllAmtList = queryList(serviceAllAmt.toString());
			
			//缴款
			if (!rechargeList.isEmpty()) {
				for (Map<String, Object> map : rechargeList) {
					if("1".equals(map.get("PayMentType"))){
						sumDaySettle.setCash(map.get("TakeBalance").toString());
					}else if("2".equals(map.get("PayMentType"))){
						sumDaySettle.setPos(map.get("TakeBalance").toString());
					}else if("3".equals(map.get("PayMentType"))){
						sumDaySettle.setTranfer(map.get("TakeBalance").toString());
					}else if("4".equals(map.get("PayMentType"))){
						sumDaySettle.setApliays(map.get("TakeBalance").toString());
					}else if("5".equals(map.get("PayMentType"))){
						sumDaySettle.setWechat(map.get("TakeBalance").toString());
					}
				}
			}
			
			//网点缴款
			if (!handFeeList.isEmpty()) {
				for (Map<String, Object> map : handFeeList) {
					if("1".equals(map.get("feetype"))){
						sumDaySettle.setPlaceToComparePaid(map.get("handfee").toString());
					}if("2".equals(map.get("feetype"))){
						sumDaySettle.setPlaceToTranferPaid(map.get("handfee").toString());
					}if("3".equals(map.get("feetype"))){
						sumDaySettle.setPlaceNoChargeBankFee(map.get("handfee").toString());
					}if("4".equals(map.get("feetype"))){
						sumDaySettle.setPlaceCashAmt(map.get("handfee").toString());
					}else if("5".equals(map.get("feetype"))){
						sumDaySettle.setPlacePosAmt(map.get("handfee").toString());
					}else if("6".equals(map.get("feetype"))){
						sumDaySettle.setPlaceTranferAmt(map.get("handfee").toString());
					}else if("7".equals(map.get("feetype"))){
						sumDaySettle.setPlaceWeChat(map.get("handfee").toString());
					}else if("8".equals(map.get("feetype"))){
						sumDaySettle.setPlaceAppliy(map.get("handfee").toString());
					}
				}
			}
			
			//电子标签提货金额
			if (!tagTakeFeeList.isEmpty()) {
				for (Map<String, Object> map : tagTakeFeeList) {
					if("1".equals(map.get("ChargeType"))){
						BigDecimal cash = new BigDecimal(sumDaySettle.getCash());
						cash = cash.add(new BigDecimal(map.get("ChargeFee").toString()));
						sumDaySettle.setCash(cash.toString());
					}else if("2".equals(map.get("ChargeType"))){
						BigDecimal pos = new BigDecimal(sumDaySettle.getPos());
						pos = pos.add(new BigDecimal(map.get("ChargeFee").toString()));
						sumDaySettle.setPos(pos.toString());
					}
					/*else if("3".equals(map.get("ChargeType"))){
						BigDecimal tranfer = new BigDecimal(sumDaySettle.getTranfer());
						tranfer = tranfer.add(new BigDecimal(map.get("ChargeFee").toString()));
						sumDaySettle.setTranfer(tranfer.toString());
					}*/
				}
			}
			String tallyCount = null;
			if (!tallyList.isEmpty()) {
				for (Map<String, Object> map : tallyList) {
					tallyCount = map.get("HandFee").toString();
				}
			}
			if(tallyCount!=null){
				BigDecimal tally = new BigDecimal(tallyCount);
				tally=tally.add(new BigDecimal(sumDaySettle.getCash()));
				sumDaySettle.setbPaid(tally.toString());
			}else{
				sumDaySettle.setbPaid(sumDaySettle.getCash());
			}
			//长短款
			if (!afterDaySetList.isEmpty()) {
				for (Map<String, Object> map : afterDaySetList) {
					if("5".equals(map.get("FeeType"))){
						sumDaySettle.setCashAllAmt(map.get("DifferenceFee").toString());
					} else if("6".equals(map.get("FeeType"))){
						sumDaySettle.setPosAllAmt(map.get("DifferenceFee").toString());
					} else if("7".equals(map.get("FeeType"))){
						sumDaySettle.setTranferAllAmt(map.get("DifferenceFee").toString());
					} else if("8".equals(map.get("FeeType"))){
						sumDaySettle.setApliaysAllAmt(map.get("DifferenceFee").toString());
					} else if("9".equals(map.get("FeeType"))){
						sumDaySettle.setWechatAllAmt(map.get("DifferenceFee").toString());
					}
				}
			}
			BigDecimal serviceAmt = new BigDecimal("0");
			if (serviceAllAmtList!=null && !serviceAllAmtList.isEmpty()) {
				for (Map<String, Object> map : serviceAllAmtList) {
					if(map.get("FEE")!=null)serviceAmt = new BigDecimal(map.get("FEE").toString()).multiply(new BigDecimal("-1"));
				}
			}
			sumDaySettle.setServiceAllAmt(serviceAmt.toString());
			sumDaySettle.setaPaid("0");
			System.out.println(sumDaySettle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sumDaySettle;
	}
	
	public void getServiceAllAmt(String startTime,String endTime){
		//标签工本费
		StringBuffer tagAmt = new StringBuffer(
				" select sum(ChargeCost）Fee from csms_tag_info"
				+" where to_char(Issuetime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(Issuetime,'YYYYMMDDHH24MISS')<"+endTime+" and ( "
		);
		//储值卡工本费
		StringBuffer prepaidAmt = new StringBuffer(
				" select sum(RealCost）Fee from csms_prepaidc "
				+" where to_char(SaleTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(SaleTime,'YYYYMMDDHH24MISS')<"+endTime+" and (  "
		);
		//记帐卡工本费
		StringBuffer accountCInfoAmt = new StringBuffer(
				" select sum(RealCost）Fee from csms_accountc_info" 
				+" where to_char(IssueTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(IssueTime,'YYYYMMDDHH24MISS')<"+endTime+" and (  "
		);
		//保证金
		StringBuffer bailAmt = new StringBuffer(
				" select sum(BailFee）Fee from csms_bail BailFee" 
				+" where PayFlag = '0' and to_char(SetTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(SetTime,'YYYYMMDDHH24MISS')<"+endTime+" and ( "
		);
		//账户余额退款
		StringBuffer refundAmt = new StringBuffer(
				" select sum(CurrentRefundBalance）Fee from csms_refundinfo" 
				+" where RefundType = '3' and AuditStatus != '6' and AuditStatus != '7'" +
				" and to_char(RefundApplyTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(RefundApplyTime,'YYYYMMDDHH24MISS')<"+endTime+" and ( "
		);
		//储值卡充值
		StringBuffer prepaidBsThree = new StringBuffer(
				 " select sum(RealPrice）Fee from csms_prepaidc_bussiness" +
				" where State='3' and  tradeState= '1' and to_char(tradeTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(tradeTime,'YYYYMMDDHH24MISS')<"+endTime+" and ( "
		);
		//储值卡快速充值
		StringBuffer prepaidBsNig = new StringBuffer(
				 " select sum(RealPrice）Fee from csms_prepaidc_bussiness" +
				" where State='19' and  tradeState= '1' and to_char(tradeTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(tradeTime,'YYYYMMDDHH24MISS')<"+endTime+" and ( "
		);
		//充值登记
		StringBuffer rechargeRegister = new StringBuffer(
				" select select  sum(d.Fee）Fee from CSMS_add_reg r join CSMS_add_reg_detail d on r.id = d.addregid " +
				" where d.Flag !=4 and to_char(RegistrationTime,'YYYYMMDDHH24MISS')>="+startTime+" and to_char(RegistrationTime,'YYYYMMDDHH24MISS')<"+endTime+" and (  "
		);
			
			
	}
	
	public boolean checkRecord(Long operPlaceId,String SettleDay){
		String sql = "select count(1) from csms_dayset_record r join "
				+ "csms_dayset_detail d on r.id = d.mainid where d.DifferenceFlag = '1' and r.operPlaceId = ? and r.SettleDay = ?";
		int count = count(sql,operPlaceId,SettleDay);
		if(count!=0){
			return false;
		}
		return true;
	}
	
	public BigDecimal getBeforeNoRechargeHandleFee(List<String> placeList){
		//查询上一次日结的未缴存金额
		StringBuffer daySetHandSql = new StringBuffer("select HandFee as HandFee from csms_dayset_record r join csms_dayset_detail d on r.id = d.mainid"
				+ " where  FeeType = '2' and r.placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			daySetHandSql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				daySetHandSql.append(" , ");
			}
		};
		daySetHandSql.append( " )order by r.settleday desc  FETCH FIRST 1 ROW ONLY ");
		
		List<Map<String, Object>> tallyList = queryList(daySetHandSql.toString());
		
		BigDecimal tallyCount = new BigDecimal("0");
		if (!tallyList.isEmpty()) {
			for (Map<String, Object> map : tallyList) {
				tallyCount = (BigDecimal) map.get("HandFee");
			}
		}
		return tallyCount;
	}
	
}
