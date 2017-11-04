package com.hgsoft.customer.serviceInterface;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.entity.BlackListStatus;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.customer.entity.*;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ICustomerService {
	public Customer find(Customer customer);
	public List<Map<String, Object>> findAll(Customer customer);
	public String saveCustomer(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo, Invoice invoice, ServiceFlowRecord serviceFlowRecord,String clientSystem);
	public String saveCustomerForAMMS(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo, List<BillGet> billGets, Invoice invoice, ServiceFlowRecord serviceFlowRecord,String clientSystem,CustomerImp customerImp);
	public Customer saveCustomer(Customer customer, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo,ServiceFlowRecord serviceFlowRecord);
	public void deleteCustomer(Long id);
	public void updateCustomer(Customer customer, CustomerHis customerHis);
	public void updateCustomer(Customer customer, CustomerHis customerHis,String newOrgan,Map<String,Object> params);
	public void updateCustomer4OnLine(Customer customer, CustomerHis customerHis,String remark);
	public Customer findById(Long id);
	public Pager findByPage(Pager pager,Customer customer);
	public Pager findByPage(Pager pager, Customer customer, String startDate, String endDate);
	public Customer findByIdNo(String idType, String idCode);
	public boolean checkCustomerToCancel(Long id);
	public Map<String,Object> authenticationCheck(String idType,String idCode,String servicePwd,String cardNo,String type);
	public Customer getCustomerByPrepaidCardNo(String cardNo);
	public Customer getCustomerByAccountCardNo(String cardNo);
	public Customer findByUserNo(String userNo);
	public boolean updatePwd(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params);
	public boolean updatePwd4OnLine(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,String remark);
	public List<Map<String, Object>> getCustomerByBank(String bankAccount);
	public Pager getAccountCList(Pager accountCPager, Long customerId,AccountCInfo accountCInfo, VehicleInfo vehicleInfo, TagInfo tagInfo);
	public Pager getAccountCListForAMMS(Pager accountCPager, Long customerId,AccountCInfo accountCInfo, VehicleInfo vehicleInfo, TagInfo tagInfo,String bankCode);
	public Pager getPrepaidCList(Pager prepaidCPager, Long customerId,PrepaidC prePaidc, VehicleInfo vehicleInfo, TagInfo tagInfo);
	public Pager getPrepaidCListForAMMS(Pager prepaidCPager, Long customerId,PrepaidC prePaidc, VehicleInfo vehicleInfo, TagInfo tagInfo,String bankCode);
	public Pager getTagInfoList(Pager tagInfoPager, Long customerId,TagInfo tagInfo);
	public Pager getTagInfoListForAMMS(Pager tagInfoPager, Long customerId,TagInfo tagInfo,String bankCode);
	public Pager getVehicleListForAMMS(Pager vehiclePager, Long customerId,VehicleInfo vehicleInfo,String bankCode);
	public Pager getVehicleList(Pager vehiclePager, Long customerId,VehicleInfo vehicleInfo);
	public Pager getAccountCSubList(Pager accountCSubPager ,Long customerId);//hzw 2017年9月9日添加
	public Pager getAccountCInfoList(Pager accountCInfoPager, Long accountCId);//hzw 2017年9月9日添加
	public Pager getUnBindTagInfoList(Pager unBindTagInfoPager, Long customerId,TagInfo tagInfo);
	public Pager getUnUseVehicleList(Pager unUseVehiclePager, Long customerId);
	public boolean saveOrUpdateMaterial(Material material,String rootPath,List<File> imageFile,List<String> imageFileName,Customer customer);
	public boolean saveOrUpdateMaterial(Material material,String rootPath,String[] tempPicNameList,Customer customer,String[] deleteOldMaterialIDList,String clientSystem);
	public Map<String,Object> saveOrUpdateMaterial1(Material material,String rootPath,String[] tempPicNameList,Customer customer,String[] deleteOldMaterialIDList,String clientSystem);
	public Customer findByCustomer(Customer customer);
	public Customer findByCustomerNameAndIdNo(String customerName,String idType, String idCode);
	public Pager findAll(Pager pager);
	public Pager findByCardNo(Pager pager,String cardNo);
	public List<Map<String, Object>> findCustomerList(String organ,
			String idCode, String idType);


	/**
	 * 获取澳门通的记帐卡信息
	 * @param accountCPager
	 * @param customerId
	 * @return
	 */
	public Pager getMacaoAccountCList(Pager accountCPager, Long customerId);
	
	/**
	 * 获取澳门通用户的标签信息
	 * @param accountCPager
	 * @param customerId
	 * @return
	 */
	public Pager getMacaoTagInfoList(Pager accountCPager, Long customerId);
	
	/**
	 * 获取澳门通账户车辆信息
	 * @param vehiclePager
	 * @param id
	 * @return
	 */
	public Pager getMacaoVehicleList(Pager vehiclePager, Long id);
	
	/**
	 * 获取澳门通用户未绑定车辆的电子标签信息
	 * @param unBindTagInfoPager
	 * @param id
	 * @return
	 */
	public Pager getMacaoUnBindTagInfoList(Pager unBindTagInfoPager, Long id);

	public boolean checkBalance(Long id);
	public boolean hasApproval(Long customerId);
	public void saveApply(Customer oldCustomer,Customer newCustomer,String oldpath,String newpath);
	public boolean hasCustomer2(String idType,String idCode);
	public Map omsInterface(Long operateId,String operateName);

	public Customer findByIdTypeCodeSecondNo(String idType,String idCode,String secondNo);
	public boolean updatePwdForAMMS(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params);
	/**
	 * 根据证件类型+证件号码，查找二级编码不为空的的客户
	 * @param idType
	 * @param idCode
	 * @return Customer
	 */
	public Customer findByIdNoWithSecondNo(String idType, String idCode);
	/**
	 * 2017-06-07 
	 * 查找黑名单状态表
	 * @param cardNo
	 * @return BlackListStatus
	 */
	public List<BlackListTemp> findBlackListTMP(String cardNo,String obuSerial);
	public boolean hasCustomerImp(CustomerImp customerImp);
	public boolean hasCustomer(CustomerImp customerImp);
	public List<Map<String,Object>> findByIdTypeAndIdCode(String idType,String idCode);
	public boolean findByTypeAndCode(String idType,String idCode);
	public boolean hasCardOrTag(Long id);
	public boolean hasCustomerForEdit(Long id,String idType,String idCode,String secondNo);

    List<Map<String,Object>> findCardNoByIdTypeAndIdCode(Long customerId);
    /**
     * 查找验证客户的所有需要提醒的异常数据
     * @param customerId
     * @return List<ExceptionCustomer>
     */
    public List<ExceptionCustomer> findExcCustomerByCustomerId(Long customerId);
    /**
     * 修改客户的所有异常客户数据
     * @param exceptionCustomer
     * @return void
     */
    public Map<String, Object> updateExcCustomer(ExceptionCustomer exceptionCustomer);
    /**
     * 根据客户id查找特殊名单
     * @param customerId
     * @return List<SpecialList>
     */
    public List<SpecialList> findSpecialListCustomerId(Long customerId);
    public Customer findbyTagInfoId(Long id);
    /**
     * 校验    证件类型+证件号码+二级编码   是否已在客户信息修改申请表里面待审批
     * @param customer 证件类型+证件号码+二级编码
     * @return boolean  返回true表示已经存在待审批的新证件类型+证件号码
     */
    public boolean checkModifyApply(Customer customer);
    public CustomerImp findCustomerImpById(Long id);

	/***
	 * 根据卡号获取客户信息
	 * @param cardNo
	 * @return
     */
	public Customer findByCardNo(String cardNo);
	
	/**
	 * 根据客户号查询客户信息
	 * @param pager
	 * @param userNo
	 * @return
	 */
	public Pager findByUserNO(Pager pager, String userNo);
}
