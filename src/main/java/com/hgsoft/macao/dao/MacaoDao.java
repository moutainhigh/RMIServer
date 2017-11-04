package com.hgsoft.macao.dao;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.Cancel;
import com.hgsoft.macao.entity.MacaoAddCarReq;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCancleReqInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.MacaoCardCustomerHis;
import com.hgsoft.macao.entity.MacaoLostReq;
import com.hgsoft.macao.entity.ReqInterfaceFlow;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Repository
public class MacaoDao extends BaseDao {
	@Resource
	private ReceiptDao receiptDao;

	public boolean checkCard(Long id, String cardNo) {
		String sql = "select * from csms_macao_card_customer mcc join csms_macao_bankaccount mb on mcc.id=mb.mainid join csms_cardholder_info ci on mb.id=ci.macaobankaccountid join csms_accountc_info ai on ai.id=ci.typeid where mcc.id = ? and ai.cardno=?";
		List<Map<String, Object>> list = queryList(sql, id, cardNo);
		return list.size() > 0;
	}

	public TagInfo getTagInfoByTagNo(String tagNo) throws Exception {
		String sql = "select * from csms_tag_info where tagNo=?";
		List<Map<String, Object>> list = queryList(sql, tagNo);
		TagInfo tagInfo = null;
		if (list.size() > 0) {
			tagInfo = new TagInfo();
			convert2Bean(list.get(0), tagInfo);
		}
		return tagInfo;
	}

	public TagInfo getTagInfoById(Long id) throws Exception {
		String sql = "select * from csms_tag_info where id=?";
		List<Map<String, Object>> list = queryList(sql, id);
		TagInfo tagInfo = null;
		if (list.size() > 0) {
			tagInfo = new TagInfo();
			convert2Bean(list.get(0), tagInfo);
		}
		return tagInfo;
	}

	public CarObuCardInfo getCarObuCardInfoByVehPla(String vehiclePlate) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
		String sql = "select ci.* from csms_vehicle_info vi join CSMS_CarObuCard_info ci on vi.id=ci.vehicleid where vi.vehicleplate=?";
		List<Map<String, Object>> list = queryList(sql, vehiclePlate);
		if (list.size() > 0) {
			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			return (CarObuCardInfo) convert2Bean(list.get(0), carObuCardInfo);
		}

		return null;
	}

	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow) {
		Map map = FieldUtil.getPreFieldMap(ReqInterfaceFlow.class, reqInterfaceFlow);
		StringBuffer sql = new StringBuffer("insert into CSMS_REQINTERFACE_FLOW");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	/**
	 * 保存持卡人信息表记录
	 *
	 * @param pager
	 * @return void
	 */
	public Pager getVehicleInfo(Pager pager, String organ, String idType, String idCode, String vehicleColor, String vehiclePlate) {
		StringBuffer sql = new StringBuffer(" select vi.id,vi.vehicleColor,vi.vehiclePlate,"
				+ " vi.vehicleUserType,"
				+ " vi.UsingNature,"
				+ " vi.IdentificationCode,"
				+ " vi.VehicleType,"
				+ " vi.vehicleWheels,"
				+ " vi.vehicleAxles,"
				+ " vi.vehicleWheelBases,"
				+ " vi.vehicleWeightLimits,"
				+ " vi.vehicleEngineNo,"
				+ " vi.vehicleWidth,"
				+ " vi.vehicleLong,"
				+ " vi.vehicleHeight,"
				+ " vi.owner,"
				+ " vi.Model,vi.NSCVEHICLETYPE"
				+ " from CSMS_Vehicle_Info vi"
				+ " join csms_cardholder_info ci on ci.Typeid=vi.id"
				+ " join csms_macao_bankaccount mb on ci.MACAOBANKACCOUNTID=mb.id"
				+ " join CSMS_MACAO_CARD_CUSTOMER cc on mb.mainid=cc.id"
				+ " join CSMS_Customer c on c.id=vi.customerid"
				+ " where c.systemType='3' and ci.type='1' ");
		SqlParamer sqlp = new SqlParamer();
		if (StringUtil.isNotBlank(organ)) {
			sqlp.eq("cc.Cnname", organ);
		}
		if (StringUtil.isNotBlank(idType)) {
			sqlp.eq("cc.Idcardtype", idType);
		}
		if (StringUtil.isNotBlank(idCode)) {
			sqlp.eq("cc.Idcardnumber", idCode);
		}
		if (StringUtil.isNotBlank(vehicleColor)) {
			sqlp.eq("vi.vehicleColor", vehicleColor);
		}
		if (StringUtil.isNotBlank(vehiclePlate)) {
			sqlp.eq("vi.vehiclePlate", vehiclePlate);
		}
		sql = sql.append(sqlp.getParam());
		sql.append(" order by vi.id desc ");
		return this.findByPages(sql.toString(), pager, sqlp.getList().toArray());
	}

	public void saveMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer) {
		macaoCardCustomer.setHisSeqId(-macaoCardCustomer.getId());
		Map map = FieldUtil.getPreFieldMap(MacaoCardCustomer.class, macaoCardCustomer);
		StringBuffer sql = new StringBuffer("insert into CSMS_MACAO_CARD_CUSTOMER");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*save(sql.toString());*/
	}

	/**
	 * 保存销户申请记录
	 *
	 * @param macaoCancleReqInfo
	 * @return void
	 */
	public void saveMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo) {
		Map map = FieldUtil.getPreFieldMap(MacaoCancleReqInfo.class, macaoCancleReqInfo);
		StringBuffer sql = new StringBuffer("insert into csms_macaocanclereq_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*save(sql.toString());*/
	}

	public Pager findOpenList(Pager pager, MacaoCardCustomer macaoCardCustomer, String startTime, String endTime) {

		StringBuffer sql = new StringBuffer(
				"select " + FieldUtil.getFieldMap(MacaoCardCustomer.class, macaoCardCustomer).get("nameStr") + ",ROWNUM AS num from csms_macao_card_customer where 1=1 ");
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(startTime)) {
			params.geDate("reqtime", "to_date(" + startTime + " 00:00:00,yyyy-mm-dd 24hh:mi:ss)");
		}
		if (StringUtil.isNotBlank(endTime)) {
			params.leDate("reqtime", "to_date(" + endTime + " 23:59:59,yyyy-mm-dd 24hh:mi:ss)");
		}
		sql = sql.append(params.getParam());
		Object[] Objects = params.getList().toArray();
		sql.append(" order by reqtime desc ");
		return this.findByPages(sql.toString(), pager, Objects);
	}

	/**
	 * 根据卡号和银行账号查询开户信息对象
	 * @param accountNo     银行账号
	 * @return MacaoCardCustomer
	 */
	/*public MacaoCardCustomer findByCardAccount(String accountNo){
		String sql = "select "+FieldUtil.getFieldMap(MacaoCardCustomer.class, new MacaoCardCustomer()).get("nameStr")+" from csms_macao_card_customer where 1=1 ";
		
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(accountNo)) {
			params.eq("bankAccountNumber", accountNo);
		}
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();
		
		List<Map<String, Object>> list = queryList(sql,Objects);
		MacaoCardCustomer macaoCardCustomer = null;
		try {
			if (!list.isEmpty()) {
				macaoCardCustomer = new MacaoCardCustomer();
				this.convert2Bean(list.get(0), macaoCardCustomer);
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
		return macaoCardCustomer;
	}*/

	/**
	 * 查询开户信息对象
	 */
	public MacaoCardCustomer findMacao(MacaoCardCustomer macaoCard) {
		String sql = "select " + FieldUtil.getFieldMap(MacaoCardCustomer.class, new MacaoCardCustomer()).get("nameStr") + " from csms_macao_card_customer where 1=1 ";

		SqlParamer params = new SqlParamer();
		/*if (macaoCard!=null && StringUtil.isNotBlank(macaoCard.getBankAccountNumber())) {
			params.eq("bankAccountNumber", macaoCard.getBankAccountNumber());
		}*/
		if (macaoCard != null && StringUtil.isNotBlank(macaoCard.getIdCardNumber())) {
			params.eq("idCardNumber", macaoCard.getIdCardNumber());
		}
		if (macaoCard != null && StringUtil.isNotBlank(macaoCard.getIdCardType())) {
			params.eq("idCardType", macaoCard.getIdCardType());
		}
		if (macaoCard != null && macaoCard.getId() != null) {
			params.eq("id", macaoCard.getId());
		}
		if (macaoCard != null && macaoCard.getCnName() != null) {
			params.eq("cnname", macaoCard.getCnName());
		}

		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		List<Map<String, Object>> list = queryList(sql, Objects);
		MacaoCardCustomer macaoCardCustomer = null;
		if (!list.isEmpty()) {
			macaoCardCustomer = new MacaoCardCustomer();
			this.convert2Bean(list.get(0), macaoCardCustomer);
		}

		return macaoCardCustomer;
	}

	/**
	 * 根据证件类型与证件号码
	 *
	 * @param idType
	 * @param idNumber
	 * @return MacaoCardCustomer
	 */
	public MacaoCardCustomer findByIdTypeAndNumber(String idType, String idNumber) {
		String sql = "select " + FieldUtil.getFieldMap(MacaoCardCustomer.class, new MacaoCardCustomer()).get("nameStr") + " from csms_macao_card_customer where 1=1 ";

		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(idType)) {
			params.eq("idCardType", idType);
		}
		if (StringUtil.isNotBlank(idNumber)) {
			params.eq("idCardNumber", idNumber);
		}

		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		List<Map<String, Object>> list = queryList(sql, Objects);
		MacaoCardCustomer macaoCardCustomer = null;
		if (!list.isEmpty()) {
			macaoCardCustomer = new MacaoCardCustomer();
			this.convert2Bean(list.get(0), macaoCardCustomer);
		}

		return macaoCardCustomer;
	}

	public Pager findByPage(Pager pager, MacaoCardCustomer macaoCardCustomer) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_macao_card_customer t where 1=1");
		if (macaoCardCustomer != null) {
			Map map = FieldUtil.getPreFieldMap(MacaoCardCustomer.class, macaoCardCustomer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			sql.append(" order by id desc ");
			return this.findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else {
			sql.append(" order by id desc ");
			return this.findByPages(sql.toString(), pager, null);
		}
	}

	public Pager findByPageAndId(Pager pager, MacaoCardCustomer macaoCardCustomer) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_macao_card_customer t where t.id=" + macaoCardCustomer.getId());
		return this.findByPages(sql.toString(), pager, null);
	}

	/**
	 * 根据id查询客户信息
	 *
	 * @param id
	 * @return
	 */
	public MacaoCardCustomer findById(Long id) {
		String sql = "select * from csms_macao_card_customer where id=" + id;
		List<Map<String, Object>> list = queryList(sql);
		MacaoCardCustomer macaoCardCustomer = null;
		if (!list.isEmpty()) {
			macaoCardCustomer = new MacaoCardCustomer();
			this.convert2Bean(list.get(0), macaoCardCustomer);
		}

		return macaoCardCustomer;
	}

	public void saveMacaoCardCustomerHis(MacaoCardCustomerHis macaoCardCustomerHis) {
		Map map = FieldUtil.getPreFieldMap(MacaoCardCustomerHis.class, macaoCardCustomerHis);
		StringBuffer sql = new StringBuffer("insert into CSMS_MACAO_CARD_CUSTOMER_HIS");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));

	}

	public void update(MacaoCardCustomer macaoCardCustomer) {
		Map map = FieldUtil.getPreFieldMap(MacaoCardCustomer.class, macaoCardCustomer);
		StringBuffer sql = new StringBuffer("update CSMS_MACAO_CARD_CUSTOMER set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), macaoCardCustomer.getId());
	}

	public VehicleInfo getVehicleDetailInfo(String viid) throws Exception {
		//String sql = "select vehicleColor,vehiclePlate,vehicleUserType,usingNature, identificationCode, vehicleType, vehicleWheels, vehicleAxles,
		// vehicleWheelBases, vehicleWeightLimits, vehicleEngineNo, vehicleWidth, vehicleLong, vehicleHeight, owner, Model,customerID from CSMS_Vehicle_Info where id=?";

		//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
		String sql = "select vehicleColor,vehiclePlate,vehicleUserType,usingNature, identificationCode, vehicleType, vehicleWheels, vehicleAxles, " +
				"vehicleWheelBases, vehicleWeightLimits, vehicleEngineNo, vehicleWidth, vehicleLong, vehicleHeight, owner, Model,customerID, userNo" +
				" from CSMS_Vehicle_Info where id=?";
		//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		List<Map<String, Object>> list = queryList(sql, viid);
		if (list.size() > 0) {
			VehicleInfo vehicle = (VehicleInfo) convert2Bean(list.get(0), new VehicleInfo());
			return vehicle;
		}
		return null;
	}

	public void updateVehicleId(VehicleInfo vehicleInfo) {
		String sql = "update CSMS_Vehicle_Info set vehicleUserType=?,UsingNature=?,IdentificationCode=?,vehicleWheels=?,vehicleAxles=?,vehicleWheelBases=?,vehicleEngineNo=?,vehicleWidth=?,vehicleLong=?,vehicleHeight=?,owner=?,Model=?,HISSEQID=?,vehicleSpecificInformation=? where id=?";
		saveOrUpdate(sql, vehicleInfo.getVehicleUserType(), vehicleInfo.getUsingNature(), vehicleInfo.getIdentificationCode(), vehicleInfo.getVehicleWheels(), vehicleInfo.getVehicleAxles(), vehicleInfo.getVehicleWheelBases(), vehicleInfo.getVehicleEngineNo(), vehicleInfo.getVehicleWidth(), vehicleInfo.getVehicleLong(), vehicleInfo.getVehicleHeight(), vehicleInfo.getOwner(), vehicleInfo.getModel(), vehicleInfo.getHisSeqId(), vehicleInfo.getVehicleSpecificInformation(), vehicleInfo.getId());
	}

	public Map<String, Object> getTagAndAccountC(String id) {
		String sql = "select ci.TagID,ci.AccountCID from CSMS_CarObuCard_info ci where ci.VehicleID=?";
		List<Map<String, Object>> list = queryList(sql, id);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/*public void updateMacaocarcustomerid(String id){
		String sql = "update csms_carusercard_info set macaocardcustomerid='' where VehicleID=?";
		saveOrUpdate(sql, id);
	}*/

	/**
	 * 获取旧的服务密码
	 */
	public String getOldPass(Long id) {
		String sql = "select mcc.Servicepwd from CSMS_MACAO_CARD_CUSTOMER mcc where mcc.id=?";
		List<Map<String, Object>> list = queryList(sql, id);
		Map<String, Object> map = null;
		if (list.size() > 0) {
			map = list.get(0);
			String pass = (String) map.get("SERVICEPWD");
			return pass;
		}
		return "";
	}

	/**
	 * 根据卡号获取 澳门通持卡人信息表 id
	 */
	public String getMccId(String cardNo) {
		String sql = "select mcc.id from CSMS_MACAO_CARD_CUSTOMER mcc join csms_cardholder_info ci on ci.macaocarcustomerid=mcc.id join CSMS_AccountC_info ai on ci.typeid=ai.id where ai.cardno=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		Map<String, Object> map = null;
		if (list.size() > 0) {
			map = list.get(0);
			String MccId = map.get("ID") + "";
			return MccId;
		}
		return "";
	}

	/**
	 * 更新服务密码
	 */
	public void updatePass(Long id, String newPass) {
		String sql = "update CSMS_MACAO_CARD_CUSTOMER mcc set mcc.Servicepwd=? where id=?";
		saveOrUpdate(sql, newPass, id);
	}

	/**
	 * 根据记帐卡卡号获取车辆信息
	 *
	 * @return VehicleInfo
	 * @author lgm
	 */
	public VehicleInfo getVehicleInfoBycardNo(String cardNo) throws Exception {
		String sql = "select vi.* from CSMS_Vehicle_Info vi join CSMS_CarObuCard_info ci on ci.VehicleID=vi.id join CSMS_AccountC_info ai on ai.id=ci.AccountCID join CSMS_Customer c on c.id=vi.customerid where c.systemType='3' and ai.cardno=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		if (list.size() > 0) {
			VehicleInfo vehicle = (VehicleInfo) convert2Bean(list.get(0), new VehicleInfo());
			return vehicle;
		}
		return null;
	}


	/**
	 * 获取客户信息
	 *
	 * @return VehicleInfo
	 * @author lgm
	 */
	public MacaoCardCustomer getCustomerByCardNo(String cardNo) {
		try {
			String sql = "select mcc.* from csms_cardholder_info ci join csms_macao_bankaccount mb on ci.MACAOBANKACCOUNTID=mb.id "
					+ "join csms_macao_card_customer mcc on mb.mainid=mcc.id "
					+ "join CSMS_AccountC_info ai on ci.typeid=ai.id where ai.cardno=? ";
			List<Map<String, Object>> list = queryList(sql, cardNo);
			if (list.size() > 0) {
				MacaoCardCustomer macaoCardCustomer = (MacaoCardCustomer) convert2Bean(list.get(0), new MacaoCardCustomer());
				return macaoCardCustomer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取注销申请表的数据
	 *
	 * @param macaoCancleReqInfo（银行账号、卡号）
	 * @return MacaoCancleReqInfo
	 */
	public MacaoCancleReqInfo findMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo) {
		String sql = "select " + FieldUtil.getFieldMap(MacaoCancleReqInfo.class, new MacaoCancleReqInfo()).get("nameStr") + " from csms_macaocanclereq_info where (state='0' or state='2') ";

		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(macaoCancleReqInfo.getBankAccountNumber())) {
			params.eq("bankAccountNumber", macaoCancleReqInfo.getBankAccountNumber());
		}
		if (StringUtil.isNotBlank(macaoCancleReqInfo.getEtcCardNumber())) {
			params.eq("etcCardNumber", macaoCancleReqInfo.getEtcCardNumber());
		}
		/*if (StringUtil.isNotBlank(macaoCancleReqInfo.getState())) {
			params.eq("state", macaoCancleReqInfo.getState());
		}*/

		sql = sql + params.getParam();
		sql = sql + " order by reqopertime desc ";
		Object[] Objects = params.getList().toArray();

		List<Map<String, Object>> list = queryList(sql, Objects);
		MacaoCancleReqInfo macaoCancleReq = null;
		if (!list.isEmpty()) {
			macaoCancleReq = new MacaoCancleReqInfo();
			this.convert2Bean(list.get(0), macaoCancleReq);
		}

		return macaoCancleReq;
	}

	/**
	 * 获取卡片旧交易密码
	 *
	 * @return VehicleInfo
	 * @author lgm
	 */
	public String getCardOldPass(String cardNo) {
		String sql = "select TradingPwd from CSMS_AccountC_info where cardNo=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		Map<String, Object> map = null;
		if (list.size() > 0) {
			map = list.get(0);
			String pass = (String) map.get("TRADINGPWD");
			return pass;
		}
		return "";
	}

	/**
	 * 获取记帐卡信息表id
	 *
	 * @author lgm
	 */
	public String getAccountCIdByCardNo(String cardNo) {
		String sql = "select id from CSMS_AccountC_info where cardNo=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		Map<String, Object> map = null;
		if (list.size() > 0) {
			map = list.get(0);
			String id = map.get("ID") + "";
			return id;
		}
		return "";
	}

	/**
	 * 更改交易密码
	 *
	 * @author lgm
	 */
	public void updateCardOldPass(String id, String newPass) {
		String sql = "update CSMS_AccountC_info set TradingPwd=? where id=?";
		saveOrUpdate(sql, newPass, id);
	}

	/**
	 * 获取accountCApply
	 *
	 * @author lgm
	 */
	public AccountCApply findByCardNo(String cardNo) {
		String sql = "select ap.* from csms_accountc_info a left join csms_subaccount_info s on a.accountid=s.id left join csms_accountc_apply ap on ap.id=s.applyid where a.cardNo=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		AccountCApply accountCApply = null;
		if (!list.isEmpty()) {
			accountCApply = new AccountCApply();
			this.convert2Bean(list.get(0), accountCApply);
		}

		return accountCApply;
	}

	/**
	 * 记帐卡业务记录保存
	 *
	 * @author lgm
	 */
	public void save(AccountCBussiness accountCBussiness) {
		Map map = FieldUtil.getPreFieldMap(AccountCBussiness.class, accountCBussiness);
		StringBuffer sql = new StringBuffer("insert into Csms_Accountc_Bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		receiptDao.saveByBussiness(null, null, null, null, accountCBussiness);
	}

	/**
	 * 更新
	 *
	 * @author lgm
	 */
	public void update(AccountCInfo accountCInfo) {
		Map map = FieldUtil.getPreFieldMap(AccountCInfo.class, accountCInfo);
		StringBuffer sql = new StringBuffer("update csms_accountc_info set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), accountCInfo.getId());
	}

	/**
	 * 获取AccountCInfo
	 *
	 * @author lgm
	 */
	public AccountCInfo getAccountCInfoById(String id) {
		String sql = "select * from CSMS_AccountC_info where id=?";
		List<Map<String, Object>> list = queryList(sql, id);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}

	/**
	 * 重设卡片密码
	 *
	 * @author lgm
	 */
	public void updateCardPass(String id, String newPass) {
		String sql = "update CSMS_AccountC_info set TradingPwd=? where id=?";
		saveOrUpdate(sql, newPass, id);
	}

	public Pager findByCustomer(Pager pager, Customer customer) {
		String sql = "select a.*,ROWNUM as num from csms_accountc_info a where a.customerId=" + customer.getId();
		sql = sql + (" order by a.ID ");
		return super.findByPages(sql.toString(), pager, null);
	}

	public AccountCInfo getByCardNo(String cardNo) {
		String sql = "select * from CSMS_AccountC_info where cardNo=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}

	/**
	 * 电子标签维护信息查询
	 *
	 * @param pager
	 * @param startTime
	 * @param endTime
	 * @param tagInfo
	 * @param vehicleInfo
	 * @param customer
	 * @return Pager
	 */
	public Pager findTagMainManageList(Pager pager, String startTime, String endTime, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer) {
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,ti.Issuetime,ROWNUM as num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id "
				+ "where ti.tagState='1' and cus.id=" + customer.getId();
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(tagInfo.getTagNo())) {
			params.eq("tagNo", tagInfo.getTagNo());
		}
		if (StringUtil.isNotBlank(vehicleInfo.getVehicleColor()) && StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())) {
			params.eq("vehicleColor", vehicleInfo.getVehicleColor());
			params.eq("vehiclePlate", vehicleInfo.getVehiclePlate());
		}
		if (StringUtil.isNotBlank(startTime)) {
			params.geDate("ti.Issuetime", startTime + " 00:00:00");
		}
		if (StringUtil.isNotBlank(endTime)) {
			params.leDate("ti.Issuetime", endTime + " 23:59:59");
		}

		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();
		sql = sql + (" order by ti.id desc ");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}

	/**
	 * 电子标签维护管理详情查找
	 *
	 * @param tagInfoId
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findTagMainManageDetail(Long tagInfoId) {
		String sql = "select ti.id as tagInfoId,cus.id as clientID,Organ,coc.vehicleID,vehiclePlate,Model,vehicleColor,vehicleSpecificInformation,"
				+ "VehicleType,vehicleWeightLimits,vehicleAxles,vehicleEngineNo,"
				+ "UsingNature,IdentificationCode,owner,tagNo,ChargeCost,Issuetime,"
				+ "MaintenanceTime,StartTime,EndTime,vc.NSCVehicleType "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.tagId=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id where ti.id=?";

		List<Map<String, Object>> list = queryList(sql, tagInfoId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 记帐卡发行时查询可用车辆
	 *
	 * @param bankAccountNumber
	 * @return
	 */
	public List<Map<String, Object>> findAvailableVehicle(String bankAccountNumber) {
		String sql = "SELECT VI.ID, CUSTOMERID,VEHICLEPLATE,VEHICLECOLOR,MODEL,OWNER,NSCVEHICLETYPE,VEHICLEWEIGHTLIMITS,VEHICLETYPE,TAGNO,COI.ACCOUNTCID,COI.TAGID"
				+ " FROM CSMS_CARDHOLDER_INFO CI "
				+ " JOIN CSMS_VEHICLE_INFO VI ON CI.TYPEID = VI.ID "
				+ " JOIN CSMS_CAROBUCARD_INFO COI ON COI.VEHICLEID = VI.ID "
				+ " JOIN csms_macao_bankaccount mb ON CI.MACAOBANKACCOUNTID = mb.ID "
				+ " LEFT JOIN CSMS_TAG_INFO T ON COI.TAGID = T.ID WHERE COI.ACCOUNTCID IS NULL "
				+ " AND COI.PREPAIDCID IS NULL "
				+ " AND mb.BANKACCOUNTNUMBER = '" + bankAccountNumber + "'";
		return queryList(sql);
	}

	public MacaoCardCustomer findByHisId(Long id) {
		String sql = "select * from csms_macao_card_customer ";
		if (id != null) {
			sql = sql + " where HISSEQID=" + id;
		} else {
			sql = sql + " where HISSEQID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		MacaoCardCustomer macaoCardCustomer = null;
		if (!list.isEmpty()) {
			macaoCardCustomer = new MacaoCardCustomer();
			this.convert2Bean(list.get(0), macaoCardCustomer);
		}

		return macaoCardCustomer;
	}

	public MacaoCardCustomerHis findByMacaoHisId(Long id) {
		String sql = "select * from csms_macao_card_customer_his ";
		if (id != null) {
			sql = sql + " where HISSEQID=" + id;
		} else {
			sql = sql + " where HISSEQID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		MacaoCardCustomerHis macaoCardCustomerHis = null;
		if (!list.isEmpty()) {
			macaoCardCustomerHis = new MacaoCardCustomerHis();
			this.convert2Bean(list.get(0), macaoCardCustomerHis);
		}

		return macaoCardCustomerHis;
	}

	/**
	 * 根据粤通卡卡号查找MacaoCardCustomer
	 */
	public MacaoCardCustomer findMacaoCardCustomerByCardNo(String cardNo) {
		MacaoCardCustomer temp = null;
		StringBuffer sql = new StringBuffer("SELECT mcc.* from csms_cardholder_info ci "
				+ "JOIN csms_macao_bankaccount mb ON ci.MACAOBANKACCOUNTID=mb.id "
				+ "JOIN csms_macao_card_customer mcc ON mb.mainid=mcc.id "
				+ "JOIN csms_accountc_info ai ON ai.id=ci.typeid WHERE ai.cardno= '" + cardNo + "'");
		System.out.println(sql.toString());
		List<Map<String, Object>> list = queryList(sql.toString());
		if (!list.isEmpty()) {
			temp = new MacaoCardCustomer();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}

	/**
	 * 根据电子标签获取客户信息
	 *
	 * @return VehicleInfo
	 * @author lgm
	 */
	public MacaoCardCustomer findCustomerByTagNo(String tagNo) {

		String sql = "select mcc.* from csms_cardholder_info ci join csms_macao_bankaccount mb on ci.MACAOBANKACCOUNTID=mb.id "
				+ "join csms_macao_card_customer mcc on mb.mainid=mcc.id "
				+ "join csms_tag_info ti on ci.typeid=ti.id where ti.tagno=? ";
		List<Map<String, Object>> list = queryList(sql, tagNo);
		if (list.size() > 0) {
			MacaoCardCustomer macaoCardCustomer = (MacaoCardCustomer) convert2Bean(list.get(0), new MacaoCardCustomer());
			return macaoCardCustomer;
		}
		return null;

	}


	/**
	 * 保存澳门通挂失申请表记录
	 *
	 * @param macaoLostReq
	 * @return void
	 */
	public void saveMacaoLostReq(MacaoLostReq macaoLostReq) {
		Map map = FieldUtil.getPreFieldMap(MacaoLostReq.class, macaoLostReq);
		StringBuffer sql = new StringBuffer("insert into CSMS_MACAO_LOST_REQ");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	/**
	 * 保存澳门通新增车辆申请表记录
	 *
	 * @param macaoAddCarReq
	 * @return void
	 */
	public void saveMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq) {
		Map map = FieldUtil.getPreFieldMap(MacaoAddCarReq.class, macaoAddCarReq);
		StringBuffer sql = new StringBuffer("insert into CSMS_MACAO_ADDCAR_REQ ");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	/**
	 * 获取澳门通挂失申请表记录
	 *
	 * @param macaoLostReq
	 * @return MacaoLostReq
	 */
	public MacaoLostReq findMacaoLostReq(MacaoLostReq macaoLostReq) {
		String sql = "select " + FieldUtil.getFieldMap(MacaoLostReq.class, new MacaoLostReq()).get("nameStr") + " from CSMS_MACAO_LOST_REQ where isNotify='0' or isNotify='2'  ";

		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(macaoLostReq.getEtcCardNumber())) {
			params.eq("etcCardNumber", macaoLostReq.getEtcCardNumber());
		}
		/*if (StringUtil.isNotBlank(macaoLostReq.getIsNotify())) {
			params.eq("isNotify", macaoLostReq.getIsNotify());
		}*/

		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		List<Map<String, Object>> list = queryList(sql, Objects);
		MacaoLostReq temp = null;
		if (!list.isEmpty()) {
			temp = new MacaoLostReq();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}

	/**
	 * 获取澳门通挂失申请表记录
	 *
	 * @param macaoAddCarReq
	 * @return MacaoLostReq
	 */
	public MacaoAddCarReq findMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq) {
		String sql = "select " + FieldUtil.getFieldMap(MacaoAddCarReq.class, new MacaoAddCarReq()).get("nameStr") + " from CSMS_MACAO_ADDCAR_REQ where 1=1 ";

		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(macaoAddCarReq.getCarColor())) {
			params.eq("CarColor", macaoAddCarReq.getCarColor());
		}
		if (StringUtil.isNotBlank(macaoAddCarReq.getCarNumber())) {
			params.eq("CarNumber", macaoAddCarReq.getCarNumber());
		}

		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		List<Map<String, Object>> list = queryList(sql, Objects);
		MacaoAddCarReq temp = null;
		if (!list.isEmpty()) {
			temp = new MacaoAddCarReq();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}

	public void save(Cancel cancel) {
		Map map = FieldUtil.getPreFieldMap(Cancel.class, cancel);
		StringBuffer sql = new StringBuffer("insert into CSMS_Cancel");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	/**
	 * 根据车辆获取持卡人信息
	 *
	 * @return VehicleInfo
	 * @author lgm
	 */
	public MacaoCardCustomer getMacaoCardCustomerByVehicleId(Long id) {
		try {
			String sql = "SELECT mcc.* from csms_cardholder_info cci join csms_macao_bankaccount mb ON mb.id=cci.MACAOBANKACCOUNTID "
					+ " JOIN csms_macao_card_customer mcc ON mcc.id=mb.mainid "
					+ " JOIN csms_vehicle_info cvi ON cci.typeid=cvi.id WHERE cvi.id=?";
			List<Map<String, Object>> list = queryList(sql, id);
			if (list.size() > 0) {
				MacaoCardCustomer macaoCardCustomer = (MacaoCardCustomer) convert2Bean(list.get(0), new MacaoCardCustomer());
				return macaoCardCustomer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @param macaoAddCarReq
	 * @return void
	 */
	public void updateMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq) {
		Map map = FieldUtil.getPreFieldMap(MacaoAddCarReq.class, macaoAddCarReq);
		StringBuffer sql = new StringBuffer("update CSMS_MACAO_ADDCAR_REQ set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), macaoAddCarReq.getId());
	}

	/**
	 * 更新挂失申请表记录
	 *
	 * @param macaoLostReq
	 * @return void
	 */
	public void updateMacaoLostReq(MacaoLostReq macaoLostReq) {
		Map map = FieldUtil.getPreFieldMap(MacaoLostReq.class, macaoLostReq);
		StringBuffer sql = new StringBuffer("update CSMS_MACAO_LOST_REQ set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), macaoLostReq.getId());
	}

	/**
	 * 更新注销申请表
	 *
	 * @param macaoCancleReqInfo
	 * @return void
	 */
	public void updateMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo) {
		Map map = FieldUtil.getPreFieldMap(MacaoCancleReqInfo.class, macaoCancleReqInfo);
		StringBuffer sql = new StringBuffer("update csms_macaocanclereq_info set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), macaoCancleReqInfo.getId());
	}

	/**
	 * 更新持卡人信息表
	 *
	 * @param macaoCardCustomer
	 * @return void
	 */
	public void updateMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer) {
		Map map = FieldUtil.getPreFieldMap(MacaoCardCustomer.class, macaoCardCustomer);
		StringBuffer sql = new StringBuffer("update csms_macao_card_customer set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), macaoCardCustomer.getId());
	}

	public MacaoBankAccount findByAccountNumber(String bankAccountNumber) {
		String sql = "select " + FieldUtil.getFieldMap(MacaoBankAccount.class, new MacaoBankAccount()).get("nameStr") + " from CSMS_MACAO_BANKACCOUNT where 1=1 ";

		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(bankAccountNumber)) {
			params.eq("bankAccountNumber", bankAccountNumber);
		}

		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();

		List<Map<String, Object>> list = queryList(sql, Objects);
		MacaoBankAccount macaoBankAccount = null;
		if (!list.isEmpty()) {
			macaoBankAccount = new MacaoBankAccount();
			this.convert2Bean(list.get(0), macaoBankAccount);
		}

		return macaoBankAccount;
	}

	/**
	 * 保存澳门通银行信息表记录
	 *
	 * @param macaoBankAccount
	 * @return void
	 */
	public void saveMacaoBankAccount(MacaoBankAccount macaoBankAccount) {
		Map map = FieldUtil.getPreFieldMap(MacaoBankAccount.class, macaoBankAccount);
		StringBuffer sql = new StringBuffer("insert into CSMS_MACAO_BANKACCOUNT");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	/**
	 * 身份验证
	 *
	 * @param idType
	 * @param idCode
	 * @param servicePwd
	 * @param cardNo
	 * @param type
	 * @return
	 */
	public Map<String, Object> authenticationCheck(String idType, String idCode, String servicePwd, String cardNo, String type) {
		List<Map<String, Object>> listMap = null;
		StringBuffer sql = new StringBuffer(
				"select c.id id,c.reqTime reqTime,c.bankCode bankCode,c.branchId branchId,c.idCardType idCardType,"
						+ "c.idCardNumber idCardNumber,c.enName enName,c.cnName cnName,c.birDate birDate,c.sex sex,c.tel tel,c.address address,"
						+ "c.email email,c.userType userType,c.shortMsg shortMsg,c.invoiceTitle invoiceTitle,"
						+ "a.CardNo CardNo,c.servicepwd ServicePwd "
						+ " from  csms_cardholder_info ci join csms_accountc_info a on ci.typeid=a.id join csms_macao_bankaccount mb on ci.macaobankaccountid=mb.id join csms_macao_card_customer c on mb.mainid=c.id where a.State = '0' "
		);
		if ("4".equals(type)) {
			sql.append(" and a.CardNo=? and c.IdCardType=? and c.IdCardNumber=? ");
			listMap = queryList(sql.toString(), cardNo, idType, idCode);
		} else if ("3".equals(type)) {
			sql.append(" and  a.CardNo=?");
			listMap = queryList(sql.toString(), cardNo);
		} else {
			return null;
		}
		if (listMap != null && listMap.size() != 0) return listMap.get(0);
		return null;
	}

	public List<Map<String, Object>> findBankAccountNumberByMainId(Long id) {
		String sql = "SELECT ID, bankaccountnumber from csms_macao_bankaccount WHERE mainid=?";
		List<Map<String, Object>> bankAccounts = queryList(sql, id);
		return bankAccounts;
	}

	public MacaoBankAccount findBankAccountByVehicleId(Long id) {
		MacaoBankAccount bankAccount = null;
		try {
			String sql = "SELECT mb.* from csms_cardholder_info ci JOIN csms_macao_bankaccount mb ON ci.macaobankaccountid=mb.id "
					+ " JOIN csms_vehicle_info vi ON vi.id=ci.typeid AND vi.id=?";
			List<Map<String, Object>> bankAccounts = queryList(sql, id);
			if (bankAccounts != null && bankAccounts.size() > 0) {
				bankAccount = new MacaoBankAccount();
				convert2Bean(bankAccounts.get(0), bankAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankAccount;
	}

	public MacaoBankAccount findBankAccountByCardNo(String cardNo) {
		MacaoBankAccount bankAccount = null;
		try {
			String sql = "SELECT mb.* from csms_cardholder_info ci JOIN csms_accountc_info ai ON ci.typeid=ai.id JOIN csms_macao_bankaccount mb ON mb.id=ci.macaobankaccountid "
					+ "WHERE ai.cardno='" + cardNo + "'";
			List<Map<String, Object>> bankAccounts = queryList(sql);
			if (bankAccounts != null && bankAccounts.size() > 0) {
				bankAccount = new MacaoBankAccount();
				convert2Bean(bankAccounts.get(0), bankAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankAccount;
	}


	public Pager findCardByPager(Pager pager, MacaoCardCustomer macaoCardCustomer, AccountCInfo accountCInfo,
	                             VehicleInfo vehicleInfo) {
		StringBuffer sql = new StringBuffer(
				"select a.id accountCId,a.cardNo,a.issueFlag,a.issueTime,a.operName,a.placeName,mc.id,mc.cnName,mc.enName,v.vehiclePlate,v.vehicleColor "
						+ " from csms_accountc_info a "
						+ " join csms_carobucard_info coc on a.id=coc.accountcid"
						+ " join csms_vehicle_info v on v.id=coc.vehicleid"
						+ " join csms_cardholder_info ch on ch.typeid=a.id and ch.type='2' "
						+ " join csms_macao_bankaccount mb on mb.id=ch.macaobankaccountid "
						+ " join csms_macao_card_customer mc on mc.id=mb.mainid where 1=1 ");
		SqlParamer params = new SqlParamer();

		if (macaoCardCustomer != null) {
			if (macaoCardCustomer.getId() != null) {
				params.eq("mc.id", macaoCardCustomer.getId());
			}
		}

		if (accountCInfo != null) {
			if (StringUtil.isNotBlank(accountCInfo.getCardNo())) {
				params.eq("a.cardNo", accountCInfo.getCardNo());
			}
		}

		if (vehicleInfo != null) {
			if (StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())) {
				params.eq("v.vehiclePlate", vehicleInfo.getVehiclePlate());
			}
			if (StringUtil.isNotBlank(vehicleInfo.getVehicleColor())) {
				params.eq("v.vehicleColor", vehicleInfo.getVehicleColor());
			}

		}

		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects = list.toArray();
		sql.append(" order by a.id desc ");
		pager = this.findByPages(sql.toString(), pager, Objects);

		return pager;
	}

	/**
	 * 查找发行信息详情
	 *
	 * @param id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findIssueDetail(Long id) {
		String sql = "select mc.idCardType,mc.idCardNumber,mc.enName,mc.cnName,mc.userType,mb.bankAccountNumber,v.vehiclePlate,"
				+ "v.vehicleColor,v.vehicleWeightLimits,v.owner,v.NSCVehicleType,v.vehicleType,a.cardNo,a.realCost,a.issueFlag,"
				+ "a.issueTime,a.operNo,a.placeName,a.suit "
				+ " from csms_accountc_info a "
				+ " join csms_carobucard_info coc on a.id=coc.accountcid"
				+ " join csms_vehicle_info v on v.id=coc.vehicleid"
				+ " join csms_cardholder_info ch on ch.typeid=a.id and ch.type='2' "
				+ " join csms_macao_bankaccount mb on mb.id=ch.macaobankaccountid "
				+ " join csms_macao_card_customer mc on mc.id=mb.mainid where a.id=? ";
		List<Map<String, Object>> list = queryList(sql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> MacaoBankAccountByCardNo(String cardNo) {
		String sql = "SELECT mb.* from csms_accountc_info a "
				+ "JOIN csms_cardholder_info ci ON ci.typeid=a.id "
				+ "JOIN csms_macao_bankaccount mb ON mb.id=ci.macaobankaccountid "
				+ "WHERE ci.type='2' AND a.cardno=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Pager findStopCardByCustomer(Pager pager, MacaoCardCustomer macaoCardCustomer, AccountCInfo accountCInfo) {
		StringBuffer sql = new StringBuffer(
				"SELECT c.* from csms_cancel c JOIN csms_accountc_info ai ON c.code=ai.cardno"
						+ " JOIN csms_cardholder_info ci ON ai.id=ci.typeid "
						+ " JOIN csms_macao_bankaccount mb ON mb.id=ci.macaobankaccountid"
						+ " JOIN csms_macao_card_customer mcc ON mcc.id=mb.mainid"
						+ " AND mcc.id=" + macaoCardCustomer.getId() + " and c.flag='2'");
		if (accountCInfo != null) {
			sql.append(" AND c.CODE='" + accountCInfo.getCardNo() + "'");
		}
		sql.append(" order by c.id desc ");
		pager = this.findByPages(sql.toString(), pager, null);
		return pager;
	}

	public Cancel findByCancelId(Long id) {
		String sql = "select * from CSMS_Cancel where id=?";
		List<Map<String, Object>> list = queryList(sql, id);
		Cancel cancel = null;
		if (!list.isEmpty()) {
			cancel = new Cancel();
			this.convert2Bean(list.get(0), cancel);
		}

		return cancel;
	}


	/**
	 * 暂时的验证车牌是否能用的方法
	 *
	 * @param vehiclePlate
	 * @param vehicleColor
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> checkVehicle(String vehiclePlate, String vehicleColor) {
		String sql = "SELECT vehiclePlate,vehicleColor from csms_checkVehicle WHERE vehiclePlate=? and vehicleColor=?";
		List<Map<String, Object>> list = queryList(sql, vehiclePlate, vehicleColor);
		return list;
	}

	/**
	 * 澳门通，通过卡号查询该持卡人下面所有没有绑定卡片的车辆。这个表只查记帐卡关联的
	 *
	 * @param cardNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> listNotBind(String cardNo) {
		String sql = " select vi.*,ti.tagno"
				+ " from csms_vehicle_info vi"
				+ " left join csms_carobucard_info coc on coc.vehicleid = vi.id"
				+ " left join csms_tag_info ti on coc.tagid=ti.id"
				+ " join csms_cardholder_info ci on (ci.typeid = vi.id and type='1')"
				+ " join csms_macao_bankaccount mb on mb.id = ci.macaobankaccountid"
				+ " join csms_macao_card_customer mcc on mcc.id = mb.mainid "
				+ " where mb.id=(select ci2.macaobankaccountid from csms_accountc_info a "
				+ " join csms_cardholder_info ci2 on (ci2.typeid=a.id and type='2') where a.cardno=?) "
				+ " and coc.accountcid is null and coc.prepaidcid is null ";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		return list;
	}
}
