package com.hgsoft.online.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.online.entity.ReqInterfaceFlow;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.RegularUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 线上接口Dao
 * @author gsf
 * 2016-08-03
 */
@Repository
public class OnlineDataDao extends BaseDao{
	
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow){
		Map map = FieldUtil.getPreFieldMap(ReqInterfaceFlow.class,reqInterfaceFlow);
		StringBuffer sql=new StringBuffer("insert into CSMS_REQINTERFACE_FLOW");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	/**
	 * 线上接口使用（客户校验）
	 * @param IdType
	 * @param IdCode
	 * @param userNo
	 * @param cardNo
	 * @param ServicePwd
	 * @return
	 */
	public List findCheckUserValidity(String IdType,String IdCode,String userNo,String cardNo,String ServicePwd){
		StringBuffer sql = new StringBuffer();
		SqlParamer params=new SqlParamer();
		//当卡号不为空
		if(StringUtil.isNotBlank(cardNo)){
			//判断是否储值卡
			if(RegularUtil.isPrepaid(cardNo)){
				sql.append("SELECT c.ID AS ID, c.UserNo AS UserNo, c.Organ AS Organ, c.ServicePwd AS ServicePwd, c.UserType AS UserType ," +
						" c.LinkMan AS LinkMan, c.systemType AS systemType, c.IdType AS IdType, c.IdCode AS IdCode," +
						" c.registeredCapital AS registeredCapital , c.Tel AS Tel, c.Mobile AS Mobile, c.ShortTel AS ShortTel," +
						" c.Addr AS Addr, c.ZipCode AS ZipCode , c.Email AS Email, c.State AS State, c.cancelTime AS cancelTime," +
						" c.OperId AS OperId, c.updateTime AS updateTime , c.firRunTime AS firRunTime, c.PlaceId AS PlaceId," +
						" c.HisSeqID AS HisSeqID, p.CardNo AS CardNo FROM CSMS_Customer c " +
						"LEFT JOIN CSMS_PrePaidC p ON p.customerID = c.id AND p.State = '0'");
			}else {
				sql.append("SELECT c.ID AS ID, c.UserNo AS UserNo, c.Organ AS Organ, c.ServicePwd AS ServicePwd, c.UserType AS UserType ," +
						" c.LinkMan AS LinkMan, c.systemType AS systemType, c.IdType AS IdType, c.IdCode AS IdCode," +
						" c.registeredCapital AS registeredCapital , c.Tel AS Tel, c.Mobile AS Mobile, c.ShortTel AS ShortTel," +
						" c.Addr AS Addr, c.ZipCode AS ZipCode , c.Email AS Email, c.State AS State, c.cancelTime AS cancelTime," +
						" c.OperId AS OperId, c.updateTime AS updateTime , c.firRunTime AS firRunTime, c.PlaceId AS PlaceId, " +
						"c.HisSeqID AS HisSeqID, a.CARDNO AS CardNo FROM CSMS_Customer c " +
						"LEFT JOIN csms_accountc_info a ON a.customerID = c.id AND a.State = '0' WHERE 1 = 1");
			}
			params.eq("CardNo", cardNo);
		}else{
			sql.append("SELECT * FROM (SELECT c.ID AS ID, c.UserNo AS UserNo, c.Organ AS Organ, c.ServicePwd AS ServicePwd, " +
					"c.UserType AS UserType , c.LinkMan AS LinkMan, c.systemType AS systemType, c.IdType AS IdType, " +
					"c.IdCode AS IdCode, c.registeredCapital AS registeredCapital , c.Tel AS Tel, c.Mobile AS Mobile," +
					" c.ShortTel AS ShortTel, c.Addr AS Addr, c.ZipCode AS ZipCode , c.Email AS Email, c.State AS State, " +
					"c.cancelTime AS cancelTime, c.OperId AS OperId, c.updateTime AS updateTime , c.firRunTime AS firRunTime," +
					" c.PlaceId AS PlaceId, c.HisSeqID AS HisSeqID, nvl(p.CardNo, a.CARDNO) AS CardNo FROM CSMS_Customer c " +
					"LEFT JOIN CSMS_PrePaidC p ON p.customerID = c.id AND p.State = '0' " +
					"LEFT JOIN csms_accountc_info a ON a.customerID = c.id AND a.State = '0' ) WHERE 1 = 1");
		}

		if(StringUtil.isNotBlank(ServicePwd)){
			params.eq("ServicePwd", ServicePwd);
		}
		if(StringUtil.isNotBlank(IdType)&&StringUtil.isNotBlank(IdCode)){
			params.eq("IdType", IdType);
			params.eq("IdCode", IdCode);
		}
		if(StringUtil.isNotBlank(userNo)){
			params.eq("UserNo", userNo);
		}

		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		OnlineDataDao onlineDataDao = (OnlineDataDao)context.getBean("onlineDataDao");
		System.out.println("111");
	}
	
	public List<Map<String, Object>> findByTypeCodeCardNoBankNo(String IdType,String IdCode,String cardNo,String bankNo){
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> customerList = null;
		

		if(StringUtil.isNotBlank(IdType)&&StringUtil.isNotBlank(IdCode)){
			sql.append("SELECT c.userno \"userNo\", c.Organ \"organ\", c.secondNo \"secondNo\", c.secondName \"secondName\" FROM CSMS_Customer c WHERE 1 = 1 AND IdType = ? AND IdCode = ?");
			customerList = queryList(sql.toString(),IdType,IdCode);
		}
		if(StringUtil.isNotBlank(cardNo)){
			if(RegularUtil.isPrePaidCard(cardNo)){
				sql.append("SELECT c.userno \"userNo\", c.Organ \"organ\", c.secondNo \"secondNo\", c.secondName \"secondName\" FROM CSMS_Customer c INNER JOIN CSMS_PrePaidC P ON P.customerID = c.ID WHERE 1 = 1 AND p.cardNo = ?");
			}else {
				sql.append("SELECT c.userno \"userNo\", c.Organ \"organ\", c.secondNo \"secondNo\", c.secondName \"secondName\" FROM CSMS_Customer c INNER JOIN csms_accountc_info A ON A.customerID = c.ID WHERE 1 = 1 AND a.cardNo = ?");
			}

			customerList = queryList(sql.toString(),cardNo);
		}
		if(StringUtil.isNotBlank(bankNo)){
			sql.append("SELECT c.userno \"userNo\", c.Organ \"organ\", c.secondNo \"secondNo\", c.secondName \"secondName\" FROM CSMS_Customer c LEFT JOIN CSMS_AccountC_apply ac ON ac.customerid = c.ID WHERE 1 = 1 AND bankAccount = ?");
			customerList = queryList(sql.toString(),bankNo);
		}
		return customerList;

	}
	
	public List<Map<String, Object>> findPrePaidCardList(Long customerId) {
		String sql = "   select p.cardNo \"cardNo\",p.state \"state\",to_char(p.saleTime,'YYYYMMDDHH24MISS') as \"issuTime\",v.vehiclePlate \"vehiclePlate\",v.vehicleColor \"vehicleColor\",t.tagNo \"tagNo\" from csms_prepaidc p "
          +" join csms_customer c on c.id=p.customerid "
          +" join csms_carobucard_info coc on coc.prepaidcid=p.id "
          +" join csms_vehicle_info v on coc.vehicleid=v.id "
          +" left join csms_tag_info t on coc.tagid=t.id where c.id=? ";
		return queryList(sql,customerId);
	}
	public List<Map<String, Object>> findAccountCardList(Long customerId) {
		String sql = "   select a.cardNo \"cardNo\",a.state \"state\",to_char(a.IssueTime,'YYYYMMDDHH24MISS') as \"issuTime\",v.vehiclePlate \"vehiclePlate\",v.vehicleColor \"vehicleColor\",t.tagNo \"tagNo\" from csms_accountc_info a "
		  +" join csms_customer c on c.id=a.customerid "
	      +" join csms_carobucard_info coc on coc.accountcid=a.id "
	      +" join csms_vehicle_info v on coc.vehicleid=v.id "
	      +" left join csms_tag_info t on coc.tagid=t.id where c.id=? ";
		return queryList(sql,customerId);
	}
	public List<Map<String, Object>> findTagList(Long customerId){
		String sql = "  select t.tagNo \"tagNo\",t.IssueType \"IssueType\",t.SalesType \"SalesType\",t.TagState as \"state\",to_char(t.Issuetime,'YYYYMMDDHH24MISS') as \"issuTime\",v.vehiclePlate \"vehiclePlate\",v.vehicleColor \"vehicleColor\" from csms_tag_info t "
          +" join csms_customer c on c.id=t.clientid "
          +" left join csms_carobucard_info coc on coc.tagid=t.id"
          +" left join csms_vehicle_info v on v.id = coc.vehicleid  where c.id=? ";
		return queryList(sql,customerId);
	}
	public List<Map<String, Object>> findVehicleList(Long customerId){
		String sql = "    select vehiclePlate \"vehiclePlate\",vehicleColor \"vehicleColor\",vehicleUserType \"vehicleUserType\",UsingNature \"UsingNature\",IdentificationCode \"IdentificationCode\", "
		  +" VehicleType \"VehicleType\",vehicleWheels \"vehicleWheels\",vehicleAxles \"vehicleAxles\",vehicleWheelBases \"vehicleWheelBases\",vehicleWeightLimits \"vehicleWeightLimits\", "
		  +" vehicleSpecificInformation \"vehicleSpecificInformation\",vehicleEngineNo \"vehicleEngineNo\",vehicleWidth \"vehicleWidth\",vehicleLong \"vehicleLong\",vehicleHeight \"vehicleHeight\", "
		  +" NSCvehicletype \"NSCvehicletype\",owner \"owner\",Model \"Model\",to_char(createTime,'YYYYMMDDHH24MISS') \"createTime\" from csms_vehicle_info v where v.customerid=? ";
		return queryList(sql,customerId);
	}
	
	public Map<String, Object> findVehicleCardBindQuery(String vehiclePlate, String vehicleColor, String cardNo){
		StringBuffer sql = new StringBuffer("");
		
		List<Map<String, Object>> mapList = null;
		if(StringUtil.isNotBlank(vehiclePlate)&&StringUtil.isNotBlank(vehicleColor)&&!StringUtil.isNotBlank(cardNo)){
			sql.append("SELECT NVL(A.cardNo, P.cardNo) AS \"cardNo\", NVL(A.bind, P.bind) AS \"bind\", NVL(A.suit, P.suit) AS \"suit\", middle.tagNo AS \"tagNo\", middle.vehiclePlate AS \"vehiclePlate\" , middle.vehicleColor AS \"vehicleColor\" FROM (SELECT T.tagNo, v.vehiclePlate, v.vehicleColor, coc.accountcid AS accountId, coc.prepaidcid AS prepaidcId FROM csms_vehicle_info v INNER JOIN csms_carobucard_info coc ON coc.vehicleid = v.ID LEFT JOIN csms_tag_info T ON T.ID = coc.tagid WHERE 1 = 1 AND vehiclePlate = ? AND vehicleColor = ? ) middle LEFT JOIN csms_accountc_info A ON A.ID = middle.accountId LEFT JOIN csms_prepaidc P ON P.ID = middle.prepaidcId");
			mapList = queryList(sql.toString(),vehiclePlate,vehicleColor);
		}
		if(StringUtil.isNotBlank(cardNo)){
			if(RegularUtil.isPrePaidCard(cardNo)){
				sql.append("SELECT P.cardNo, P.bind, P.suit, T.tagNo, v.vehiclePlate , v.vehicleColor FROM csms_prepaidc P LEFT JOIN csms_carobucard_info coc ON coc.prepaidCId = P.ID LEFT JOIN csms_vehicle_info v ON v.ID = coc.vehicleid LEFT JOIN csms_tag_info T ON T.ID = coc.tagid WHERE 1 = 1 AND cardNo = ?");
			}else {
				sql.append("SELECT A.cardNo, A.bind, A.suit, T.tagNo, v.vehiclePlate , v.vehicleColor FROM csms_accountc_info A LEFT JOIN csms_carobucard_info coc ON coc.accountcid = A.ID LEFT JOIN csms_vehicle_info v ON v.ID = coc.vehicleid LEFT JOIN csms_tag_info T ON T.ID = coc.tagid WHERE 1 = 1 AND cardNo = ?");
			}

			mapList = queryList(sql.toString(),cardNo);
		}
		if(!mapList.isEmpty()){
			return mapList.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 服务信息查询
	 * @param userNo
	 * @return
	 */
	public List<Map<String, Object>> findQueryBillInfo(String userNo){
		StringBuilder sql = new StringBuilder(
				"SELECT b.cardType AS \"cardType\", CASE WHEN cardtype = 1 THEN b.CardBankNo ELSE NULL END AS \"cardNo\", " +
						"CASE WHEN cardtype = 2 THEN b.CardBankNo ELSE NULL END AS \"bankAccount\", " +
						"ap.accounttype AS \"accountType\", b.SerItem AS \"SerItem\" FROM CSMS_bill_get b " +
						"JOIN CSMS_Customer c ON b.mainId = c.ID LEFT JOIN CSMS_ACCOUNTC_APPLY ap ON b.CardBankNo = ap.bankaccount AND b.cardType = 2 WHERE c.userNo = ?"
				);
		
		List<Map<String, Object>> list = queryList(sql.toString(), userNo);
		
		return list;
	}
	/**
	 * 发票列表查询
	 * @param mainId   客户id
	 * @return
	 */
	public List findQueryInvoiceInfo(Long mainId){
		StringBuffer sql = new StringBuffer(
				"select invoiceTitle \"invoiceTitle\",isDefault \"isDefault\" from CSMS_invoice where mainid=? "
				);
		return queryList(sql.toString(), mainId);
	}
	
	public List findQueryPrepaidCardRecharge(String cardNo,String startTime,String endTime){
		StringBuffer sql = new StringBuffer(
				"select p.cardNo \"cardNo\",p.RealPrice \"rechargeAmt\",p.beforebalance \"BeRechargeAmt\",p.transferSum \"transferSum\",p.returnMoney \"returnMoney\","
				+ "p.balance \"afterRechargeAmt\",p.State \"RechargeType\",to_char(p.tradeTime,'YYYYMMDDHH24MISS') \"rechargeTime\",p.Placename \"placeName\",p.tradeState \"tradeState\" "
				+ " from CSMS_PrePaidC_bussiness p where p.State in ('2','3','4','19','20') "
				);
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("p.cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(startTime)){
			//sql.append(" and a.OperTime>=to_date('"+startTime+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
			params.geDate("p.tradeTime", startTime);
		}
		if(StringUtil.isNotBlank(endTime)){
			//sql.append(" and a.OperTime<=to_date('?','YYYY-MM-DD HH24:MI:SS')");
			params.leDate("p.tradeTime", endTime);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}
	/**
	 * 保证金情况查询
	 */
	public List findQueryBailInfo(String userNo, String cardNo, String bankAccount){
		StringBuffer sql = new StringBuffer("");
		if(StringUtil.isNotBlank(cardNo)&&!StringUtil.isNotBlank(bankAccount)){
			sql.append("select b.PayFlag \"PayFlag\",b.BailFee \"BailFee\",to_char(b.Up_Date,'YYYYMMDDHH24MISS') \"modifyDate\",b.UpReason \"modifyReason\","
						+ "b.Dflag \"flag\",to_char(b.SetTime,'YYYYMMDDHH24MISS') \"SetTime\",b.tradingType \"tradingType\",b.AppState \"AppState\",to_char(b.AppTime,'YYYYMMDDHH24MISS') \"AppTime\" "
						+ ",b.operName \"operName\",b.placeName \"placeName\" "
						+ " from CSMS_bail b join CSMS_AccountC_info a on a.AccountID=b.accountID "
						+ " where b.userNo=? and a.cardNo=? ");
			
			return queryList(sql.toString(), userNo,cardNo);
		}else if(!StringUtil.isNotBlank(cardNo)&&StringUtil.isNotBlank(bankAccount)){
			sql.append("select b.PayFlag \"PayFlag\",b.BailFee \"BailFee\",to_char(b.Up_Date,'YYYYMMDDHH24MISS') \"modifyDate\",b.UpReason \"modifyReason\","
						+ "b.Dflag \"flag\",to_char(b.SetTime,'YYYYMMDDHH24MISS') \"SetTime\",b.tradingType \"tradingType\",b.AppState \"AppState\",to_char(b.AppTime,'YYYYMMDDHH24MISS') \"AppTime\""
						+ ",b.operName \"operName\",b.placeName \"placeName\" "
						+ " from CSMS_bail b "
						+ " join CSMS_SubAccount_Info s on s.id=b.accountID "
						+ " join CSMS_AccountC_apply aa on aa.id=s.ApplyID "
						+ " where b.userNo=? and aa.bankAccount=? ");
		
			return queryList(sql.toString(), userNo,bankAccount);
		}else if(StringUtil.isNotBlank(cardNo)&&StringUtil.isNotBlank(bankAccount)){
			sql.append("select b.PayFlag \"PayFlag\",b.BailFee \"BailFee\",to_char(b.Up_Date,'YYYYMMDDHH24MISS') \"modifyDate\",b.UpReason \"modifyReason\","
						+ " b.Dflag \"flag\",to_char(b.SetTime,'YYYYMMDDHH24MISS') \"SetTime\",b.tradingType \"tradingType\",b.AppState \"AppState\",to_char(b.AppTime,'YYYYMMDDHH24MISS') \"AppTime\""
						+ ",b.operName \"operName\",b.placeName \"placeName\" "
						+ " from CSMS_bail b "
						+ " join CSMS_SubAccount_Info s on s.id=b.accountID "
						+ " join CSMS_AccountC_apply aa on aa.id=s.ApplyID "
						+ " join CSMS_AccountC_info a on a.AccountID=b.accountID "
						+ " where b.userNo=? and aa.bankAccount=? and a.cardNo=?  ");
	
			return queryList(sql.toString(), userNo,bankAccount,cardNo);
		}else{
			sql.append("select b.PayFlag \"PayFlag\",b.BailFee \"BailFee\",to_char(b.Up_Date,'YYYYMMDDHH24MISS') \"modifyDate\",b.UpReason \"modifyReason\","
					+ "b.Dflag \"flag\",to_char(b.SetTime,'YYYYMMDDHH24MISS') \"SetTime\",b.tradingType \"tradingType\",b.AppState \"AppState\",to_char(b.AppTime,'YYYYMMDDHH24MISS') \"AppTime\""
					+ ",b.operName \"operName\",b.placeName \"placeName\" "
					+ " from CSMS_bail b where b.userNo=? ");
			return queryList(sql.toString(), userNo);
		}
	}
	
	/**
	 * 什么参数都不传
	 * （暂时用作记帐卡管理中的账单通知）
	 * @return
	 */
	public List findAcInvoiceNotice(){
		/*String sql = "select userNo,Organ,rechargeAmt,settleTime from csms_acinvoice_notice "
				+ " where sendState='0' ";*/
		String sql = "select userNo \"userNo\",Organ \"Organ\",rechargeAmt \"rechargeAmt\",settleTime \"settleTime\" from csms_acinvoice_notice ";
		return queryList(sql);
	}
	
	/*public List findQueryCardAcinvoice(String cardUserNo,String cardType,String month){
		StringBuffer sql = new StringBuffer(
				"select Dealfee,dealnum,realfee realdealfee,oncenum,oncefee,serverfee,latefee,mendnum,mendfee,otherserverfee,otherfee,otherrealfee,othernum"
				+ " from tb_acinvoice where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardUserNo)&&StringUtil.isNotBlank(cardType)){
			if(cardType.equals("1")){
				params.eq("userno", cardUserNo);
			}else if(cardType.equals("2")){
				params.eq("cardcode", cardUserNo);
			}
			//params.eq("p.cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(month)){
			params.geDate("Reckontime", month+"01000000");
			params.leDate("Reckontime", month+DateUtil.getDaysByYearMonth(month)+"235959");
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}*/
	
	
	public Pager findQueryPrepaidC(String userNo,Pager pager){
		StringBuffer sql = new StringBuffer("select p.cardNo \"cardNo\",p.state \"state\",p.invoicePrint \"invoicePrint\",to_char(p.saleTime,'YYYYMMDDHH24MISS') as \"issuTime\",v.vehiclePlate \"vehiclePlate\",v.vehicleColor \"vehicleColor\",t.tagNo \"tagNo\" from csms_prepaidc p "
		          +" join csms_customer c on c.id=p.customerid "
		          +" left join csms_carobucard_info coc on coc.prepaidcid=p.id "
		          +" left join csms_vehicle_info v on coc.vehicleid=v.id "
		          +" left join csms_tag_info t on coc.tagid=t.id where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("c.userNo", userNo);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by p.id desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	
	public Pager findQueryAccountC(String userNo,Pager pager){
		StringBuffer sql = new StringBuffer("select a.cardNo \"cardNo\",a.state \"state\",to_char(a.IssueTime,'YYYYMMDDHH24MISS') as \"issuTime\",v.vehiclePlate \"vehiclePlate\",v.vehicleColor \"vehicleColor\",t.tagNo \"tagNo\" "
		  +" ,aa.bankAccount \"bankAccount\",aa.bankName \"bankName\",aa.accName \"accName\",aa.accountType \"accountType\" "
		  + ",aa.bank \"bank\",aa.bankSpan \"bankSpan\",aa.obaNo \"obaNo\",aa.bankClearNo \"bankClearNo\",aa.bankAcceptNo \"bankAcceptNo\" "
		  +" from csms_accountc_info a "
		  +" left join CSMS_SubAccount_Info s on s.id= a.accountid "
		  +" left join CSMS_AccountC_apply aa on aa.id=s.applyid "
		  +" join csms_customer c on c.id=a.customerid "
	      +" left join csms_carobucard_info coc on coc.accountcid=a.id "
	      +" left join csms_vehicle_info v on coc.vehicleid=v.id "
	      +" left join csms_tag_info t on coc.tagid=t.id where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("c.userNo", userNo);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by a.id desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	
	public Pager findQueryTag(String userNo,Pager pager){
		StringBuffer sql = new StringBuffer("select t.tagNo \"tagNo\",t.IssueType \"IssueType\",t.SalesType \"SalesType\",t.TagState as \"state\",to_char(t.Issuetime,'YYYYMMDDHH24MISS') as \"issuTime\",v.vehiclePlate \"vehiclePlate\",v.vehicleColor \"vehicleColor\" from csms_tag_info t "
          +" join csms_customer c on c.id=t.clientid "
          +" left join csms_carobucard_info coc on coc.tagid=t.id"
          +" left join csms_vehicle_info v on v.id = coc.vehicleid  where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("c.userNo", userNo);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by t.id desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	
	public Pager findQueryVehicle(String userNo,Pager pager){
		StringBuffer sql = new StringBuffer("select vehiclePlate \"vehiclePlate\",vehicleColor \"vehicleColor\",vehicleUserType \"vehicleUserType\",UsingNature \"UsingNature\",IdentificationCode \"IdentificationCode\", "
		  +" VehicleType \"VehicleType\",vehicleWheels \"vehicleWheels\",vehicleAxles \"vehicleAxles\",vehicleWheelBases \"vehicleWheelBases\",vehicleWeightLimits \"vehicleWeightLimits\", "
		  +" vehicleSpecificInformation \"vehicleSpecificInformation\",vehicleEngineNo \"vehicleEngineNo\",vehicleWidth \"vehicleWidth\",vehicleLong \"vehicleLong\",vehicleHeight \"vehicleHeight\", "
		  +" NSCvehicletype \"NSCvehicletype\",owner \"owner\",Model \"Model\",to_char(createTime,'YYYYMMDDHH24MISS') \"createTime\" from csms_vehicle_info v join csms_customer c on c.id=v.customerid where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("c.userNo", userNo);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by v.id desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	//储值卡发票类型变更查询
	// TODO: 2017/3/28 这里需求不明确,查看相关代码再做
	public List findInvoiceChangeFlow(String cardNo){
		StringBuffer sql = new StringBuffer(
				"select ic.CardNo \"Organ\",cr.Organ \"Organ\",cast(ic.syscost/100 as decimal(12,2)) \"balance\",DECODE(ic.State,'1',realDate,'2',settleDate) \"changetime\",ic.State \"State\" ,1 as \"invoicePrint\"  from CSMS_InvoiceChangeFlow ic " +
						"join CSMS_CUSTOMER cr on cr.id=ic.customerID where 1=1"
		);
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("ic.cardNo", cardNo);
		}else{
			params.eq("ic.cardNo", null);
		}
		sql.append(params.getParam());
		sql.append("ORDER BY ic.settleDate,ic.realDate");
		List list = params.getList();
		Object[] Objects= list.toArray();

		return queryList(sql.toString(),Objects);
	}

	public List findPrepaidCTradeDetail(String cardNo,String settleMonth){
		StringBuffer sql = new StringBuffer(
				"SELECT TO_CHAR(entranceTime, 'YYYYMMDDHH24MISS') AS \"entranceTime\", entranceStationName AS \"entranceStation\", entranceRoadName AS \"entranceRoadName\", TO_CHAR(exitTime, 'YYYYMMDDHH24MISS') AS \"exitTime\", exitStationName AS \"exitStation\" , exitRoadName AS \"exitRoadName\", TradeType AS \"passTimes\", nvl(costAmt, 0) AS \"cost\", nvl(credited, 0) AS \"credited\", passOughtAmt AS \"passOughtAmt\" , passProvince AS \"dealProvince\" FROM CSMS_SC_TRADE_DETAILINFO WHERE 1 = 1 AND dealstatus = 0"
		);
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}if(StringUtil.isNotBlank(settleMonth)){
			params.eq("settleMonth", settleMonth);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();

		return queryList(sql.toString(),Objects);
	}

	public List findAccountCTradeDetail(String cardNo,String settleMonth){
		StringBuffer sql = new StringBuffer(
				"SELECT TO_CHAR(exitTime, 'YYYYMMDDHH24MISS') AS \"exitTime\", exitstationname AS \"exitStation\", toll AS \"amt\", realtoll AS \"actAmt\", TO_CHAR(entranceTime, 'YYYYMMDDHH24MISS') AS \"entranceTime\" , entrancestationname AS \"entranceStation\", PASSTYPE AS \"tradeType\", DISCOUNTAMOUNT AS \"discountAmt\", PASSPROVINCE AS \"passProvince\" FROM CSMS_AC_TRADE_DETAILINFO WHERE 1 = 1 AND dealstatus = 0"
		);
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}if(StringUtil.isNotBlank(settleMonth)){
			params.eq("settleMonth", settleMonth);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();

		return queryList(sql.toString(),Objects);
	}

	public List findAccountCCurrentTradeDetail(String cardNo,String settleMonth){
		StringBuffer sql = new StringBuffer(
				"SELECT TO_CHAR(exitTime, 'YYYYMMDDHH24MISS') AS \"exitTime\", exitstationname AS \"exitStation\", toll AS \"amt\", realtoll AS \"actAmt\", TO_CHAR(entranceTime, 'YYYYMMDDHH24MISS') AS \"entranceTime\" , entrancestationname AS \"entranceStation\", PASSTYPE AS \"tradeType\", DISCOUNTAMOUNT AS \"discountAmt\", TO_CHAR(balanceTime, 'YYYYMMDDHH24MISS') AS \"settleTime\" FROM CSMS_AC_TRADE_DETAILINFO WHERE 1 = 1 AND dealstatus = 0"
		);
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}if(StringUtil.isNotBlank(settleMonth)){
			params.eq("settleMonth", settleMonth);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();

		return queryList(sql.toString(),Objects);
	}

	/***
	 * 记账卡转账情况查询（跨行）
	 * @param bankAccount
	 * @param startDate
	 * @param endDate
     * @return
     */
	public List findAccountCardTransferKH(String bankAccount,Date startDate,Date endDate){
		StringBuffer sql = new StringBuffer(
				"SELECT TO_CHAR(tranferTime, 'YYYYMMDDHH24MISS') AS \"tranferTime\", bankAccount AS \"bankAccount\", SUM(tollAmt) AS \"tollAmt\", SUM(lateFee) AS \"lateFee\", amt AS \"amt\", tranferFlag AS \"tranferFlag\" " +
						"FROM (SELECT acc.GENTIME AS GENTIME, acc.HDLDATETIME AS tranferTime, acc.ACBACCOUNT AS bankAccount, " +
						"CASE WHEN FEETYPE = 1 THEN his.REALTOLL ELSE 0 END AS tollAmt, CASE WHEN FEETYPE = 2 THEN HIS.REALTOLL ELSE 0 END AS lateFee ," +
						" acc.income AS amt, acc.status AS tranferFlag FROM CSMS_ACCBANKLISTRETURNHK acc " +
						"LEFT JOIN CSMS_COMMANDINFO_HIS his ON ACC.BOARDLISTNO = his.BOARDLISTNO " +
						"WHERE acc.status = 0 AND acc.ACBACCOUNT = ? AND ACC.GENTIME BETWEEN ? AND ? ) GROUP BY GENTIME, tranferTime, bankAccount, amt, tranferFlag"
		);

		return queryList(sql.toString(),bankAccount,startDate,endDate);
	}

	/***
	 * 记账卡转账情况查询
	 * @param bankAccount
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List findAccountCardTransfer(String bankAccount,Date startDate,Date endDate){
		StringBuffer sql = new StringBuffer(
				"SELECT TO_CHAR(tranferTime, 'YYYYMMDDHH24MISS') AS \"tranferTime\", bankAccount AS \"bankAccount\", SUM(tollAmt) AS \"tollAmt\", SUM(lateFee) AS \"lateFee\", amt AS \"amt\", tranferFlag AS \"tranferFlag\" " +
						"FROM (SELECT acc.GENTIME AS GENTIME, acc.HDLDATETIME AS tranferTime, acc.ACBACCOUNT AS bankAccount, " +
						"CASE WHEN FEETYPE = 1 THEN his.REALTOLL ELSE 0 END AS tollAmt, CASE WHEN FEETYPE = 2 THEN HIS.REALTOLL ELSE 0 END AS lateFee ," +
						" acc.income AS amt, acc.status AS tranferFlag FROM CSMS_ACCBANKLISTRETURN acc " +
						"LEFT JOIN CSMS_COMMANDINFO_HIS his ON ACC.BOARDLISTNO = his.BOARDLISTNO " +
						"WHERE acc.status = 0 AND acc.ACBACCOUNT = ? AND ACC.GENTIME BETWEEN ? AND ? ) GROUP BY GENTIME, tranferTime, bankAccount, amt, tranferFlag"
		);

		return queryList(sql.toString(),bankAccount,startDate,endDate);
	}

}
