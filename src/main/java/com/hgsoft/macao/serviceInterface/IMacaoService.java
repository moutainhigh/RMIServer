package com.hgsoft.macao.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.CardHolderInfo;
import com.hgsoft.macao.entity.MacaoAddCarReq;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoCancleReqInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.MacaoCardCustomerHis;
import com.hgsoft.macao.entity.MacaoLostReq;
import com.hgsoft.macao.entity.MacaoReqRecord;
import com.hgsoft.macao.entity.NotifyMCRecord;
import com.hgsoft.macao.entity.ReqInterfaceFlow;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IMacaoService {
	//public CarUserCardInfo find(CarUserCardInfo carUserCardInfo);
	//public MacaoCardCustomer findByCardAccount(String accountNo);
	public CardHolderInfo findByCardId(Long id);
	
	public Pager findOpenList(Pager pager,MacaoCardCustomer macaoCardCustomer, String startTime, String endTime);
	public Map<String, String> saveOpenCardNotify(MacaoCardCustomer macaoCardCustomer);
	public Map<String, String> saveLossCard(MacaoLostReq macaoLostReq,AccountCInfo accountCInfo,String systemType);
	public Map<String, String> saveCar(MacaoAddCarReq macaoAddCarReq,VehicleInfo vehicleInfo,Customer customer,MacaoBankAccount macaoBankAccount);
	
	public Pager findCustomerByPage(Pager pager,MacaoCardCustomer macaocardcustomer);
	public MacaoCardCustomer findById(Long id);
	public MacaoCardCustomer findByIdTypeAndNumber(String idType,String idNumber);
	public void updateCustomer(MacaoCardCustomer macaocardcustomer, MacaoCardCustomerHis macaoCardCustomerHis,Customer customer,CustomerBussiness customerBussiness);
	public Map<String, String> saveCancleCardCustomer(MacaoCancleReqInfo macaoCancleReqInfo);
	public Pager requestVehicleInfo(Pager pager, String organ, String idType,String idCode, String vehicleColor, String vehiclePlate);
	public VehicleInfo requestVehicleDetailInfo(String viid);
	public boolean oldPassIsTrue(Long id,String oldPass);
	public String updateOldPass(MacaoCardCustomer macaoCardCustomer, String oldPass, String newPass,String confirmNewPass,CustomerBussiness customerBussiness);
	public String requestupdateVehicle(VehicleInfo vehicleInfo,CustomerBussiness customerBussiness);
	public MacaoCardCustomer getCustomerByCardNo(String cardNo);
	public String resetPass(MacaoCardCustomer macaoCardCustomer, String newPass, String confirmNewPass,CustomerBussiness customerBussiness);
	public MacaoCancleReqInfo findMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo);
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow);
	public String cardOldPassIsTrue(String cardNo,String oldPass);
	public String updateCardOldPass(String cardNo,String oldPass,String newPass,String confirmNewPass,AccountCBussiness accountCBussiness);
	public String resetCardPass(String cardNo,String newPass,String confirmNewPass,AccountCBussiness accountCBussiness);
	public Pager findByCustomer(Pager pager, Customer customer);
	public AccountCInfo findByCardNo(String cardNo);
	public MacaoCardCustomer findMacaoCardCustomerByCardNo(String cardNo);
	public MacaoCardCustomer findMacao(MacaoCardCustomer macaoCard);
	
	public MacaoLostReq findMacaoLostReq(MacaoLostReq macaoLostReq);
	public MacaoAddCarReq findMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq);
	public void updateMacaoLostReq(MacaoLostReq macaoLostReq);
	public void updateMacaoAddCarReq(MacaoAddCarReq macaoAddCarReq);
	public void updateMacaoCancleReqInfo(MacaoCancleReqInfo macaoCancleReqInfo);
	public void updateMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer);
	
	public void saveNotifyMCRecord(NotifyMCRecord notifyMCRecord,NotifyMCRecord oldNotifyMCRecord);
	
	public MacaoBankAccount findByAccountNumber(String bankAccountNumber);
	
	public Map<String, Object> authenticationCheck(String idCardType,
			String idCardNumber, String servicePwd, String cardNo, String type);
	public List<Map<String, Object>> findBankAccountNumberByMainId(Long id);
	
	public MacaoBankAccount findMacaoBankByCardNo(String cardNo);
	
	public void saveMacaoReqRecord(MacaoReqRecord macaoReqRecord);
	
	public Pager findNotifyMCRecords(Pager pager,String beginTime,String endTime,NotifyMCRecord notifyMCRecord);
	public NotifyMCRecord findNotifyMCRecord(Long id);
	public void deleteNotifyMCRecord(Long id);
	
	public MacaoCardCustomer findByVehicleInfo(VehicleInfo vehicleInfo);
	public boolean saveVehicleMigrate(String bankAccountNumber,MacaoCardCustomer macaoCardCustomer,VehicleInfo vehicleInfo, Long customerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);
	public boolean checkCarOtherPlace(String vehiclePlate,String vehicleColor);
}
