package com.hgsoft.online.serviceInterface;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.*;
import com.hgsoft.online.entity.ReqInterfaceFlow;
import com.hgsoft.online.entity.ServicePwdResetInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface IOnlineDataInterfaceService {
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow);
	public AccountCInfo findACByCardNo(String cardNo);
	public PrepaidC findPCByCardNo(String cardNo);
	//--------查询管理-----------
	public List findCheckUserValidity(String IdType,String IdCode,String userNo,String cardNo,String ServicePwd);
	public Map<String, Object> findQueryUserNo(String IdType,String IdCode,String cardNo,String bankAccount);
	public Map<String, Object> findQueryCustormerInfo(String userNo);
	public Map<String, Object> findQueryPrepaidCList(String userNo,Pager pager);
	public Map<String, Object> findQueryAccountCList(String userNo,Pager pager);
	public Map<String, Object> findQueryTagList(String userNo,Pager pager);
	public Map<String, Object> findQueryVehicleList(String userNo,Pager pager);
	public Map<String, Object> findVehicleCardBindQuery(String vehiclePlate,String vehicleColor,String cardNo);
	public Map<String, Object> findQueryCardAcinvoice(String cardUserNo,String cardType,String month);
	
	//---------------客户资料管理--------------------
	public Customer checkCustomer(Customer customer);
	public Map<String, Object> saveAddCustormerInfo(Customer customer,Invoice invoice,MainAccountInfo mainAccountInfo
			,SubAccountInfo subAccountInfo,ServiceFlowRecord serviceFlowRecord);
	public Map<String, Object> updateCustormerInfo(Customer customer,CustomerHis customerHis);
	public Map<String, Object> saveAddVehicleInfo(VehicleInfo vehicleInfo,Customer customer,VehicleBussiness vehicleBussiness);
	public Map<String, Object> updateVehicleInfo(VehicleInfo vehicleInfo,VehicleInfo newvehicleInfo);
	public Map<String, Object> saveFileUpload(Material material, String tempPicName, Customer customer,
			VehicleInfo vehicleInfo);
	
	//--------------服务信息管理----------------------------
	public Map<String, Object> findQueryBillInfo(String userNo);
	public Map<String, Object> saveModifyBillInfo(Customer customer,BillGet billGet);
	
	//-------------发票信息管理--------------------
	public List findQueryInvoiceInfo(Customer customer);
	public Map<String, Object> saveAddInvoiceInfo(Invoice invoice,Customer customer);
	public Map<String, Object> updateInvoiceInfo(Invoice invoice,Customer customer);
	
	//----------------密码管理---------------------------
	public Map<String, Object> updateUserPassword(Customer customer,String type,SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);
	public Map<String, Object> updateUserPassword4OnLine(Customer customer,String type,SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,String remark);
	public Map<String, Object> updateCardPassword(AccountCInfo accountCInfo,PrepaidC prepaidC,SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);

	//---------------------储值卡管理-------------------------------
	public List findQueryPrepaidCardRecharge(String cardNo,String startTime,String endTime);
	public Map<String, Object> updateInvoicePrintType(PrepaidC prepaidC,PrepaidCBussiness prepaidCBussiness);
	
	//---------------------记帐卡管理-------------------------------
	public Map<String, Object> saveRemoveStopList(String bankNo, Customer customer, AccountCBussiness accountCBussiness, AccountCApply accountCApply, Map<String, Object> params);
	public List findQueryBailInfo(String userNo,String cardNo,String bankAccount);
	public List findBillNotification();
	
	//-------------其他服务------------------------
	public Map<String, Object> saveCardLost(AccountCInfo accountCInfo,PrepaidC prepaidC,String systemType);
	//卡号变更情况
	public List<Map<String, Object>> queryCardChageInfo(String cardNo);
	//储值卡发票类型变更查询
	public List findInvoiceChangeFlow(String cardNo);
	//客户资料修改（网厅）
	public Map<String, Object> updateCustormerInfo4OnLine(Customer customer,CustomerHis customerHis,String remark);


	//储值卡通行明细查询
	public List findPrepaidCTradeDetail(String cardNo,String settleMonth);

	//记帐卡通行明细查询
	public List findAccountCTradeDetail(String cardNo,String settleMonth);


	/***
	 * 记帐卡实时消费情况查询
	 * @param cardNo
	 * @param settleMonth
	 * @return
	 */
	public List findAccountCCurrentTradeDetail(String cardNo,String settleMonth);


	/***
	 * 根据客户id查询记帐卡信息集合
	 * @param customerId
	 * @return
     */
	public List<Map<String, Object>> findAccountCListByCustomerId(Long customerId);

	/***
	 * 保存服务密码重设信息
	 * @param servicePwdResetInfo
	 * @return
     */
	public Map<String, Object> saveServicePwdResetInfo(ServicePwdResetInfo servicePwdResetInfo);

	/***
	 * 服务密码重设确认
	 * @param userNo
	 * @param telNum
	 * @param checkCode
	 * @param ServicePwd
	 * @return
	 */
	public Map<String, Object> confirmServicePwdResetConfirm(Customer customer, String type, SysAdmin sysAdmin,
															 CusPointPoJo cusPointPoJo,String remark,String userNo, String telNum, String checkCode, String ServicePwd);

	/***
	 * 9.1	记账卡转账情况查询
	 * @param bankAccount
	 * @param startTime
	 * @param endTime
	 * @param month
	 * @param accType
	 * @param type
	 * @return
	 */
	public Map<String, Object> findAccountCardTransfer(String bankAccount,String startTime,String endTime,String month,String accType,String type);

}
