package com.hgsoft.unifiedInterface.service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.UserTypeEnum;
import com.hgsoft.customer.dao.*;
import com.hgsoft.customer.entity.*;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.customer.CustomerAddReceipt;
import com.hgsoft.other.vo.receiptContent.customer.CustomerUpdateReceipt;
import com.hgsoft.other.vo.receiptContent.customer.PasswordResetReceipt;
import com.hgsoft.other.vo.receiptContent.customer.PasswordUpdateReceipt;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户信息业务接口类
 * @author zxy
 * 2016年1月22日14:08:36
 */
@Service
public class CustomerUnifiedInterfaceService{
	
	private static Logger logger = Logger.getLogger(CustomerUnifiedInterfaceService.class.getName());
	
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerHisDao customerHisDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private BillGetDao billGetDao;
	@Resource
	private InvoiceDao invoiceDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	
	@Resource
	private MaterialDao materialDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private CustomerImpDao customerImpDao;
	@Resource
	private ReceiptDao receiptDao;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	/**
	 * 新增客户信息接口
	 * 客户信息，主账户信息，子账户信息，服务方式登记，发票信息，客服流水
	 * @throws Exception
	 * @author zxy
	 */
	public String saveCutomer(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo, Invoice invoice, ServiceFlowRecord serviceFlowRecord,String clientSystem){
		try {
			BigDecimal Customer_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_NO");
			customer.setId(Long.valueOf(Customer_NO.toString()));
			//客户信息
			String CustomerUserno=saveCustomer(customer,material,rootPath,imageFile,imageFileName,tempPicNameList,clientSystem);
			//主账户
			saveMainAccountInfo(mainAccountInfo,customer);
			
			//子账户
			saveSubAccountInfo(subAccountInfo, mainAccountInfo, customer);
			
			/*//服务方式登记
			BigDecimal bill_get_NO = null;
			for (BillGet billGet:billGets) {
				bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
				billGet.setId(Long.valueOf(bill_get_NO.toString()));
				billGet.setMainId(customer.getId());
				billGetDao.save(billGet);
			}*/
			//发票信息
			BigDecimal SEQ_CSMS_invoice_NO = sequenceUtil.getSequence("SEQ_CSMS_invoice_NO");
			invoice.setId(Long.valueOf(SEQ_CSMS_invoice_NO.toString()));
			invoice.setMainId(customer.getId());
			invoiceDao.save(invoice);
			
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.customerAdd.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			//客服流水
			saveServiceFlowRecord(customer, serviceFlowRecord);
			
			//操作日志
			
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("109");//109客户资料新增
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setRemark("自营客服系统：客户资料新增");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//设置【客户新增回执】内容
			CustomerAddReceipt customerAddReceipt = new CustomerAddReceipt();
			customerAddReceipt.setTitle("客户信息新增回执");
			customerAddReceipt.setHandleWay("凭资料办理");
			customerAddReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
			customerAddReceipt.setCustomerSecondNo(customer.getSecondNo());
			customerAddReceipt.setCustomerSecondName(customer.getSecondName());
			customerAddReceipt.setCustomerLinkMan(customer.getLinkMan());
			customerAddReceipt.setCustomerMobile(customer.getMobile());
			customerAddReceipt.setCustomerShortTel(customer.getShortTel());
			customerAddReceipt.setCustomerTel(customer.getTel());
			customerAddReceipt.setCustomerEmail(customer.getEmail());
			customerAddReceipt.setCustomerZipCode(customer.getZipCode());
			customerAddReceipt.setCustomerAddr(customer.getAddr());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.customerAdd.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.customerAdd.getName());
			this.saveReceipt(receipt,customerBussiness,customerAddReceipt,customer);	//保存回执

			return CustomerUserno;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户新增失败");
			e.printStackTrace();
			throw new ApplicationException("客户新增失败");
		}
	}
	/**
	 * 新增客户信息接口
	 * 客户信息，主账户信息，子账户信息，服务方式登记，发票信息，客服流水
	 * @throws Exception
	 * @author zxy
	 */
	public String saveCutomerForAMMS(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo, List<BillGet> billGets, Invoice invoice, ServiceFlowRecord serviceFlowRecord,String clientSystem,CustomerImp customerImp){
		try {
			BigDecimal Customer_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_NO");
			customer.setId(Long.valueOf(Customer_NO.toString()));
			//客户信息
			String CustomerUserno=saveCustomerForAMMS(customer,material,rootPath,imageFile,imageFileName,tempPicNameList,clientSystem);
			//主账户
			saveMainAccountInfoForAMMS(mainAccountInfo,customer);
			
			//子账户
			saveSubAccountInfoForAMMS(subAccountInfo, mainAccountInfo, customer);
			
			//服务方式登记
			BigDecimal bill_get_NO = null;
			for (BillGet billGet:billGets) {
				bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
				billGet.setId(Long.valueOf(bill_get_NO.toString()));
				billGet.setMainId(customer.getId());
				billGetDao.save(billGet);
			}
			//发票信息
			BigDecimal SEQ_CSMS_invoice_NO = sequenceUtil.getSequence("SEQ_CSMS_invoice_NO");
			invoice.setId(Long.valueOf(SEQ_CSMS_invoice_NO.toString()));
			invoice.setMainId(customer.getId());
			invoiceDao.save(invoice);
			
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.customerAdd.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			//客服流水
			saveServiceFlowRecordForAMMS(customer, serviceFlowRecord);
			
			//操作日志
			
			//更新客户信息预录入
			if(customerImp!=null && customerImp.getId()!=null)
				customerImpDao.updateFlag(customerImp.getId());
			
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("109");//109客户资料新增
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setRemark("代理点系统：客户资料新增");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//设置【客户新增回执】内容
			CustomerAddReceipt customerAddReceipt = new CustomerAddReceipt();
			customerAddReceipt.setTitle("客户信息新增回执");
			customerAddReceipt.setHandleWay("凭资料办理");
			customerAddReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
			customerAddReceipt.setCustomerSecondNo(customer.getSecondNo());
			customerAddReceipt.setCustomerSecondName(customer.getSecondName());
			customerAddReceipt.setCustomerLinkMan(customer.getLinkMan());
			customerAddReceipt.setCustomerMobile(customer.getMobile());
			customerAddReceipt.setCustomerShortTel(customer.getShortTel());
			customerAddReceipt.setCustomerTel(customer.getTel());
			customerAddReceipt.setCustomerEmail(customer.getEmail());
			customerAddReceipt.setCustomerZipCode(customer.getZipCode());
			customerAddReceipt.setCustomerAddr(customer.getAddr());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.customerAdd.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.customerAdd.getName());
			this.saveReceipt(receipt,customerBussiness,customerAddReceipt,customer);	//保存回执
			
			return CustomerUserno;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户新增失败");
			e.printStackTrace();
			throw new ApplicationException("客户新增失败");
		}
	}

	public  Customer saveCutomer(Customer customer, MainAccountInfo mainAccountInfo,SubAccountInfo subAccountInfo, ServiceFlowRecord serviceFlowRecord) {
		try {
			BigDecimal Customer_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_NO");
			customer.setId(Long.valueOf(Customer_NO.toString()));
			// 客户信息
			saveCustomer(customer,null,null,null,null,null,"");
			// 主账户
			saveMainAccountInfo(mainAccountInfo, customer);

			// 子账户
			saveSubAccountInfo(subAccountInfo, mainAccountInfo, customer);
			
			//默认发票信息
			Invoice invoice=new Invoice();
			BigDecimal SEQ_CSMS_invoice_NO = sequenceUtil.getSequence("SEQ_CSMS_invoice_NO");
			invoice.setId(Long.valueOf(SEQ_CSMS_invoice_NO.toString()));
			invoice.setMainId(customer.getId());
			invoice.setInvoiceTitle(customer.getOrgan());
			invoice.setIsdefault("1");
			invoice.setOperId(serviceFlowRecord.getOperID());
			invoice.setPlaceId(serviceFlowRecord.getPlaceID());
			invoice.setUp_Date(new Date());
			invoiceDao.save(invoice);
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.customerAdd.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			// 客服流水
			saveServiceFlowRecord(customer, serviceFlowRecord);
			
			
			// 操作日志
			
			return customer;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"账户交款时新增用户失败");
			e.printStackTrace();
			throw new ApplicationException("账户交款时新增用户失败");
		}

	}
	private String saveCustomer(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList,String clientSystem){
		
		//客户号与客户id
		customer.setUserNo(StringUtil.generateUserNo());
		//设置新的服务密码
		customer.setServicePwd(StringUtil.md5(customer.getServicePwd()));
		
		//图片资料保存
		/*if(material!=null){
			material.setCustomerID(customer.getId());
			material.setType(customer.getIdType());
			material.setCode(customer.getIdCode());
			if(tempPicNameList!=null){
				customerService.saveOrUpdateMaterial(material, rootPath, tempPicNameList, customer,null,clientSystem);
			}
			
		}*/
		
		
		
		customerDao.save(customer);
		return customer.getUserNo();
	}
	private String saveCustomerForAMMS(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList,String clientSystem){
		
		//客户号与客户id
		customer.setUserNo(StringUtil.generateUserNo());
		//设置新的服务密码
		customer.setServicePwd(StringUtil.md5(customer.getServicePwd()));
		
		//图片资料保存
		/*if(material!=null){
			material.setCustomerID(customer.getId());
			material.setType(customer.getIdType());
			material.setCode(customer.getIdCode());
			if(tempPicNameList!=null){
				customerService.saveOrUpdateMaterial(material, rootPath, tempPicNameList, customer,null,clientSystem);
			}
			
		}*/
		
		
		
		customerDao.save(customer);
		return customer.getUserNo();
	}
	private void saveMainAccountInfo(MainAccountInfo mainAccountInfo,Customer customer){
		Long seq = sequenceUtil.getSequenceLong("seq_csmsmainaccountinfo_no");
		mainAccountInfo.setId(seq);
		mainAccountInfo.setMainId(customer.getId());
		mainAccountInfoDao.save(mainAccountInfo);
	}
	private void saveMainAccountInfoForAMMS(MainAccountInfo mainAccountInfo,Customer customer){
		Long seq = sequenceUtil.getSequenceLong("seq_csmsmainaccountinfo_no");
		mainAccountInfo.setId(seq);
		mainAccountInfo.setMainId(customer.getId());
		mainAccountInfoDao.save(mainAccountInfo);
	}
	private void saveSubAccountInfo(SubAccountInfo subAccountInfo,MainAccountInfo mainAccountInfo,Customer customer){
		BigDecimal SubAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSSubAccountInfo_NO");
		subAccountInfo.setId(Long.valueOf(SubAccountInfo_NO.toString()));
		subAccountInfo.setMainId(mainAccountInfo.getId());
		System.out.println("customer.getUserNo():="+customer.getUserNo());
		subAccountInfo.setSubAccountNo(customer.getUserNo()+subAccountInfo.getSubAccountType()+"001");
		subAccountInfoDao.save(subAccountInfo);
	}
	private void saveSubAccountInfoForAMMS(SubAccountInfo subAccountInfo,MainAccountInfo mainAccountInfo,Customer customer){
		BigDecimal SubAccountInfo_NO = sequenceUtil.getSequence("SEQ_CSMSSubAccountInfo_NO");
		subAccountInfo.setId(Long.valueOf(SubAccountInfo_NO.toString()));
		subAccountInfo.setMainId(mainAccountInfo.getId());
		System.out.println("customer.getUserNo():="+customer.getUserNo());
		subAccountInfo.setSubAccountNo(customer.getUserNo()+subAccountInfo.getSubAccountType()+"001");
		subAccountInfoDao.save(subAccountInfo);
	}
	private void saveServiceFlowRecord(Customer customer,ServiceFlowRecord serviceFlowRecord){
		BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
		serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
		serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
		serviceFlowRecord.setClientID(customer.getId());
		serviceFlowRecordDao.save(serviceFlowRecord);
	}
	private void saveServiceFlowRecordForAMMS(Customer customer,ServiceFlowRecord serviceFlowRecord){
		BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
		serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
		serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
		serviceFlowRecord.setClientID(customer.getId());
		serviceFlowRecordDao.save(serviceFlowRecord);
	}
	/**
	 * 修改客户信息接口
	 * 客户信息，客户历史信息
	 * @throws Exception
	 * @author zxy
	 */
	public boolean updateCutomer(Customer customer, CustomerHis customerHis) {
		try {
			//客户历史信息
			CustomerBussiness customerBussiness = new CustomerBussiness();
			BigDecimal Customer_his_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_his_NO");
			customerHis.setId(Long.valueOf(Customer_his_NO.toString()));
			customerHisDao.save(customerHis);
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customer.setHisSeqId(customerHis.getId());
			
			
			//如果修改客户信息的时候，有修改idCode，图片资料表的code也要修改
			//2017-07-24 由于图片名及路径根据要求修改，code不再记录idCode
			/*Customer oldCustomer = customerDao.findById(customer.getId());
			if(!oldCustomer.getIdCode().equals(customer.getIdCode())){
				Material material = new Material();
				material.setCustomerID(customer.getId());
				List<Material> materials = materialDao.findMateria(material);
				for(Material m:materials){
					m.setCode(customer.getIdCode());
					materialDao.updateMateria(m);
				}
				
			}*/
			
			customerDao.update(customer);
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			
			customerBussiness.setType(CustomerBussinessTypeEnum.customerUpdate.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("104");//104客户资料修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setRemark("自营客服系统：客户资料修改");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户修改失败，客户id:"+customer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户修改失败，客户id:"+customer.getId());
		}
	}

	/**
	 * 修改客户信息接口(网上营业厅)
	 * 客户信息，客户历史信息
	 * @throws Exception
	 * @author zxy
	 */
	public boolean updateCustomer4OnLine(Customer customer, CustomerHis customerHis,String remark) {
		try {
			//客户历史信息
			CustomerBussiness customerBussiness = new CustomerBussiness();
			BigDecimal Customer_his_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_his_NO");
			customerHis.setId(Long.valueOf(Customer_his_NO.toString()));
			customerHisDao.save(customerHis);
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customer.setHisSeqId(customerHis.getId());


			//如果修改客户信息的时候，有修改idCode，图片资料表的code也要修改
			//2017-07-24 由于图片名及路径根据要求修改，code不再记录idCode
			/*Customer oldCustomer = customerDao.findById(customer.getId());
			if(!oldCustomer.getIdCode().equals(customer.getIdCode())){
				Material material = new Material();
				material.setCustomerID(customer.getId());
				List<Material> materials = materialDao.findMateria(material);
				for(Material m:materials){
					m.setCode(customer.getIdCode());
					materialDao.updateMateria(m);
				}

			}*/

			customerDao.update(customer);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());

			customerBussiness.setType(CustomerBussinessTypeEnum.customerUpdate.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("104");//104客户资料修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setRemark(remark);
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户修改失败，客户id:"+customer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户修改失败，客户id:"+customer.getId());
		}
	}
	/**
	 * 修改客户信息接口，修改证件类型、证件号码、客户名称时需要审核
	 * @param  customer 客户信息，客户历史信息
	 * @throws Exception
	 * @author lgm
	 */
	public boolean updateCutomer(Customer customer, CustomerHis customerHis,String newOrgan,Map<String,Object> params) {
		try {
			//客户历史信息
			CustomerBussiness customerBussiness = new CustomerBussiness();
			BigDecimal Customer_his_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_his_NO");
			customerHis.setId(Long.valueOf(Customer_his_NO.toString()));
			customerHisDao.save(customerHis);
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customer.setHisSeqId(customerHis.getId());
			
			
			//由于idCode需要审核，这部分需要在审核通过后再执行
			//如果修改客户信息的时候，有修改idCode，图片资料表的code也要修改
			/*Customer oldCustomer = customerDao.findById(customer.getId());
			if(!oldCustomer.getIdCode().equals(customer.getIdCode())){
				Material material = new Material();
				material.setCustomerID(customer.getId());
				List<Material> materials = materialDao.findMateria(material);
				for(Material m:materials){
					m.setCode(customer.getIdCode());
					materialDao.updateMateria(m);
				}
				
			}*/
			
			customerDao.update(customer);
			
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			
			customerBussiness.setType(CustomerBussinessTypeEnum.customerUpdate.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(newOrgan);//使用修改后的客户名称，无论审核是否通过
			serviceWater.setSerType("104");//104客户资料修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setRemark("自营客服系统：客户资料修改");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//设置【客户信息修改回执】内容
			CustomerUpdateReceipt customerUpdateReceipt = new CustomerUpdateReceipt();
			customerUpdateReceipt.setTitle("客户信息修改回执");
			customerUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			customerUpdateReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
			customerUpdateReceipt.setCustomerSecondNo(customer.getSecondNo());
			customerUpdateReceipt.setCustomerSecondName(customer.getSecondName());
			customerUpdateReceipt.setCustomerLinkMan(customer.getLinkMan());
			customerUpdateReceipt.setCustomerMobile(customer.getMobile());
			customerUpdateReceipt.setCustomerShortTel(customer.getShortTel());
			customerUpdateReceipt.setCustomerTel(customer.getTel());
			customerUpdateReceipt.setCustomerEmail(customer.getEmail());
			customerUpdateReceipt.setCustomerZipCode(customer.getZipCode());
			customerUpdateReceipt.setCustomerAddr(customer.getAddr());

			customerUpdateReceipt.setOldCustomerNo(customerHis.getUserNo());
			customerUpdateReceipt.setOldCustomerType(UserTypeEnum.getName(customerHis.getUserType()));
			customerUpdateReceipt.setOldCustomerIdType(IdTypeEnum.getName(customerHis.getIdType()));
			customerUpdateReceipt.setOldCustomerIdCode(customerHis.getIdCode());
			customerUpdateReceipt.setOldCustomerName(customerHis.getOrgan());
			customerUpdateReceipt.setOldCustomerSecondNo(customerHis.getSecondNo());
			customerUpdateReceipt.setOldCustomerSecondName(customerHis.getSecondName());
			customerUpdateReceipt.setOldCustomerLinkMan(customerHis.getLinkMan());
			customerUpdateReceipt.setOldCustomerMobile(customerHis.getMobile());
			customerUpdateReceipt.setOldCustomerShortTel(customerHis.getShortTel());
			customerUpdateReceipt.setOldCustomerTel(customerHis.getTel());
			customerUpdateReceipt.setOldCustomerEmail(customerHis.getEmail());
			customerUpdateReceipt.setOldCustomerZipCode(customerHis.getZipCode());
			customerUpdateReceipt.setOldCustomerAddr(customerHis.getAddr());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.customerUpdate.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.customerUpdate.getName());
			this.saveReceipt(receipt,customerBussiness,customerUpdateReceipt,customer);		//保存回执
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户修改失败，客户id:"+customer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户修改失败，客户id:"+customer.getId());
		}
	}
	/**
	 *  修改密码接口
	 * @param customer
	 * @return
	 * @author zyh
	 */
	public boolean updatePwd(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params) {
		try {
			customerDao.update(customer);
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(type);
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getUserName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customerBussinessDao.save(customerBussiness);

			//服务密码重设或者修改服务密码，把用户的卡片服务密码设置为null
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)||CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				prepaidCDao.updateSerPwd(customer.getId());
				accountCDao.updateSerPwd(customer.getId());
				
			}
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				serviceWater.setSerType("101");//101服务密码重设
			}else if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				serviceWater.setSerType("102");//102服务密码更改
			}
			
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customerBussiness.getOperId());
			serviceWater.setOperName(customerBussiness.getOperName());
			serviceWater.setOperNo(customerBussiness.getOperNo());
			serviceWater.setPlaceId(customerBussiness.getPlaceId());
			serviceWater.setPlaceName(customerBussiness.getPlaceName());
			serviceWater.setPlaceNo(customerBussiness.getPlaceNo());
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				serviceWater.setRemark("自营客服系统：客户服务密码重设");
			}else if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				serviceWater.setRemark("自营客服系统：客户服务密码更改");
			}
			
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);

			//回执保存
			Receipt receipt = new Receipt();
			if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				//客户服务密码更改回执
				PasswordUpdateReceipt passwordUpdateReceipt = new PasswordUpdateReceipt();
				passwordUpdateReceipt.setTitle("客户服务密码更改回执");
				passwordUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				passwordUpdateReceipt.setCustomerLinkMan(customer.getLinkMan());
				passwordUpdateReceipt.setCustomerMobile(customer.getMobile());
				receipt.setTypeCode(CustomerBussinessTypeEnum.passwordUpdate.getValue());
				receipt.setTypeChName(CustomerBussinessTypeEnum.passwordUpdate.getName());
				this.saveReceipt(receipt,customerBussiness,passwordUpdateReceipt,customer);
			}else if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				//客户服务密码重设回执
				PasswordResetReceipt passwordResetReceipt = new PasswordResetReceipt();
				passwordResetReceipt.setTitle("客户服务密码重设回执");
				passwordResetReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				passwordResetReceipt.setCustomerLinkMan(customer.getLinkMan());
				passwordResetReceipt.setCustomerMobile(customer.getMobile());
				receipt.setTypeCode(CustomerBussinessTypeEnum.passwordReset.getValue());
				receipt.setTypeChName(CustomerBussinessTypeEnum.passwordReset.getName());
				this.saveReceipt(receipt,customerBussiness,passwordResetReceipt,customer);
			}
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户密码修改失败，客户id:"+customer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户密码修改失败，客户id:"+customer.getId());
		}
	}

	/**
	 *  修改密码接口
	 * @param customer
	 * @return
	 * @author zyh
	 */
	public boolean updatePwd4OnLine(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,String remark) {
		try {
			customerDao.update(customer);
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(type);
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getUserName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customerBussinessDao.save(customerBussiness);

			//服务密码重设,修改，把用户的卡片服务密码设置为null
			if(type.equals(CustomerBussinessTypeEnum.passwordUpdate.getValue())||type.equals(CustomerBussinessTypeEnum.passwordReset.getValue())){
				prepaidCDao.updateSerPwd(customer.getId());
				accountCDao.updateSerPwd(customer.getId());

			}

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				serviceWater.setSerType("101");//101服务密码重设
			}else if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				serviceWater.setSerType("102");//102服务密码更改
			}

			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customerBussiness.getOperId());
			serviceWater.setOperName(customerBussiness.getOperName());
			serviceWater.setOperNo(customerBussiness.getOperNo());
			serviceWater.setPlaceId(customerBussiness.getPlaceId());
			serviceWater.setPlaceName(customerBussiness.getPlaceName());
			serviceWater.setPlaceNo(customerBussiness.getPlaceNo());
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				serviceWater.setRemark(remark);
			}else if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				serviceWater.setRemark(remark);
			}

			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户密码修改失败，客户id:"+customer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户密码修改失败，客户id:"+customer.getId());
		}
	}
	/**
	 *  修改密码接口
	 * @param customer
	 * @return
	 * @author zyh
	 */
	public boolean updatePwdForAMMS(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params) {
		try {
			customerDao.update(customer);
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(type);
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getUserName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customerBussinessDao.save(customerBussiness);

			//服务密码重设、修改，需要把用户的卡片服务密码设置为null
			if(type.equals(CustomerBussinessTypeEnum.passwordUpdate.getValue())||type.equals(CustomerBussinessTypeEnum.passwordReset.getValue())){//服务密码重设，把用户的卡片服务密码设置为null
				prepaidCDao.updateSerPwd(customer.getId());
				accountCDao.updateSerPwd(customer.getId());
				
			}
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				serviceWater.setSerType("101");//101服务密码重设
			}else if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				serviceWater.setSerType("102");//102服务密码更改
			}
			
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customerBussiness.getOperId());
			serviceWater.setOperName(customerBussiness.getOperName());
			serviceWater.setOperNo(customerBussiness.getOperNo());
			serviceWater.setPlaceId(customerBussiness.getPlaceId());
			serviceWater.setPlaceName(customerBussiness.getPlaceName());
			serviceWater.setPlaceNo(customerBussiness.getPlaceNo());
			if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				serviceWater.setRemark("代理点系统：客户服务密码重设");
			}else if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				serviceWater.setRemark("代理点系统：客户服务密码更改");
			}
			
			serviceWater.setOperTime(new Date());
			
			//更新默认密码
			customerDao.updateDefaultSerPwd(customer.getId());
			
			serviceWaterDao.save(serviceWater);

			//回执保存
			Receipt receipt = new Receipt();
			if(CustomerBussinessTypeEnum.passwordUpdate.getValue().equals(type)){
				//客户服务密码更改回执
				PasswordUpdateReceipt passwordUpdateReceipt = new PasswordUpdateReceipt();
				passwordUpdateReceipt.setTitle("客户服务密码更改回执");
				passwordUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				passwordUpdateReceipt.setCustomerLinkMan(customer.getLinkMan());
				passwordUpdateReceipt.setCustomerMobile(customer.getMobile());
				receipt.setTypeCode(CustomerBussinessTypeEnum.passwordUpdate.getValue());
				receipt.setTypeChName(CustomerBussinessTypeEnum.passwordUpdate.getName());
				this.saveReceipt(receipt,customerBussiness,passwordUpdateReceipt,customer);
			}else if(CustomerBussinessTypeEnum.passwordReset.getValue().equals(type)){
				//客户服务密码重设回执
				PasswordResetReceipt passwordResetReceipt = new PasswordResetReceipt();
				passwordResetReceipt.setTitle("客户服务密码重设回执");
				passwordResetReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
				passwordResetReceipt.setCustomerLinkMan(customer.getLinkMan());
				passwordResetReceipt.setCustomerMobile(customer.getMobile());
				receipt.setTypeCode(CustomerBussinessTypeEnum.passwordReset.getValue());
				receipt.setTypeChName(CustomerBussinessTypeEnum.passwordReset.getName());
				this.saveReceipt(receipt,customerBussiness,passwordResetReceipt,customer);
			}
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"客户密码修改失败，客户id:"+customer.getId());
			e.printStackTrace();
			throw new ApplicationException("客户密码修改失败，客户id:"+customer.getId());
		}
	}

	/**
	 * 注销客户信息
	 * @param  customer,cancel,serviceFlowRecord
	 * @throws Exception
	 * @author zxy
	 */
	public boolean saveCancel(Customer customer, Cancel cancel, ServiceFlowRecord serviceFlowRecord) {
		try {
			//销户信息
			BigDecimal Cancel_NO = sequenceUtil.getSequence("SEQ_CSMS_cancel_NO");
			cancel.setId(Long.valueOf(Cancel_NO.toString()));
			cancelDao.save(cancel);
			
			//客服流水
			BigDecimal serviceflow_record_NO = sequenceUtil.getSequence("SEQ_csms_serviceflow_record_NO");
			serviceFlowRecord.setId(Long.valueOf(serviceflow_record_NO.toString()));
			serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
			serviceFlowRecordDao.save(serviceFlowRecord);
			//客户信息
			customerDao.update(customer);
			
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.customerCancel.getValue());
			customerBussiness.setOperId(cancel.getOperId());
			customerBussiness.setOperNo(cancel.getOperNo());
			customerBussiness.setOperName(cancel.getOperName());
			customerBussiness.setPlaceId(cancel.getPlaceId());
			customerBussiness.setPlaceNo(cancel.getPlaceNo());
			customerBussiness.setPlaceName(cancel.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussiness.setOldcustomerId(customer.getHisSeqId());
			customerBussinessDao.save(customerBussiness);
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("110");//110客户资料注销
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(cancel.getOperId());
			serviceWater.setOperName(cancel.getOperName());
			serviceWater.setOperNo(cancel.getOperNo());
			serviceWater.setPlaceId(cancel.getPlaceId());
			serviceWater.setPlaceName(cancel.getPlaceName());
			serviceWater.setPlaceNo(cancel.getPlaceNo());
			serviceWater.setRemark("自营客服系统：客户资料注销");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
            Long seq1 = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			
			customerBussiness.setId(seq1);
			customerBussiness.setCustomerId(customer.getId());
			
			customerBussiness.setType(CustomerBussinessTypeEnum.customerCancel.getValue());
			customerBussiness.setOperId(customer.getOperId());
			customerBussiness.setOperNo(customer.getOperNo());
			customerBussiness.setOperName(customer.getOperName());
			customerBussiness.setPlaceId(customer.getPlaceId());
			customerBussiness.setPlaceNo(customer.getPlaceNo());
			customerBussiness.setPlaceName(customer.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"注销客户失败，客户id:"+customer.getId()+";注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("注销客户失败，客户id:"+customer.getId()+";注销登记id:"+cancel.getId());
		}
	}

	/**
	 * 修改客户注销信息
	 * @param  cancel
	 * @throws Exception
	 * @author zxy
	 */
	public boolean updateCancel(Cancel cancel) {
		try {
			//客户历史信息
			cancelDao.update(cancel);
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(cancel.getCustomerId());
			customerBussiness.setType(CustomerBussinessTypeEnum.customerCancelUpdate.getValue());
			customerBussiness.setOperId(cancel.getOperId());
			customerBussiness.setOperNo(cancel.getOperNo());
			customerBussiness.setOperName(cancel.getOperName());
			customerBussiness.setPlaceId(cancel.getPlaceId());
			customerBussiness.setPlaceNo(cancel.getPlaceNo());
			customerBussiness.setPlaceName(cancel.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			Customer customer = customerDao.findById(customerBussiness.getCustomerId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("116");//116客户注销原因修改修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customerBussiness.getOperId());
			serviceWater.setOperName(customerBussiness.getOperName());
			serviceWater.setOperNo(customerBussiness.getOperNo());
			serviceWater.setPlaceId(customerBussiness.getPlaceId());
			serviceWater.setPlaceName(customerBussiness.getPlaceName());
			serviceWater.setPlaceNo(customerBussiness.getPlaceNo());
			serviceWater.setRemark("自营客服系统：客户注销原因修改修改");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"修改客户注销信息,注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("修改客户注销信息,注销登记id:"+cancel.getId());
		}
	}
	
	/**
	 * 保存发票信息
	 * @author lzl
	 */
	public boolean saveInvoice(Invoice invoice) {
		try {
			//
			BigDecimal Invoice_NO = sequenceUtil.getSequence("SEQ_CSMS_Invoice_NO");
			invoice.setId(Long.valueOf(Invoice_NO.toString()));
			invoiceDao.save(invoice);
			if(invoice.getIsdefault().equals("1")){
				invoiceDao.setDefault(invoice.getId(),invoice.getMainId());
			}
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setInvoiceId(invoice.getId());
			customerBussiness.setInvoicetitle(invoice.getInvoiceTitle());
			customerBussiness.setIsdefault(invoice.getIsdefault());
			customerBussiness.setCustomerId(invoice.getMainId());
			customerBussiness.setType(CustomerBussinessTypeEnum.invoiceAdd.getValue());
			customerBussiness.setOperId(invoice.getOperId());
			customerBussiness.setOperNo(invoice.getOperNo());
			customerBussiness.setOperName(invoice.getOperName());
			customerBussiness.setPlaceId(invoice.getPlaceId());
			customerBussiness.setPlaceNo(invoice.getPlaceNo());
			customerBussiness.setPlaceName(invoice.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			Customer customer = customerDao.findById(invoice.getMainId());
			
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("112");//112发票信息新增
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(invoice.getOperId());
			serviceWater.setOperName(invoice.getOperName());
			serviceWater.setOperNo(invoice.getOperNo());
			serviceWater.setPlaceId(invoice.getPlaceId());
			serviceWater.setPlaceName(invoice.getPlaceName());
			serviceWater.setPlaceNo(invoice.getPlaceNo());
			serviceWater.setRemark("自营客服系统：发票信息新增");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			return true;
		} catch (ApplicationException e) {
			//logger.error(e.getMessage()+"修改客户注销信息,注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("添加发票登记信息，发票登记id:"+invoice.getId());
		}
	}

	/**
	 * 更新发票信息
	 * @author lzl
	 */
	public boolean updateInvoice(Invoice invoice) {
		try {
			
			InvoiceHis his = new InvoiceHis();
			
			
			his.setGenTime(new Date());
			his.setGenReason("1");//1：修改
			if(invoice.getHisSeqId() != null){
				his.setHisSeqId(invoice.getHisSeqId());
			}
			his.setIsdefault(invoice.getIsdefault());
			his.setMainId(invoice.getMainId());
			his.setOperId(invoice.getOperId());
			his.setOperNo(invoice.getOperNo());
			his.setOperName(invoice.getOperName());
			his.setPlaceId(invoice.getPlaceId());
			his.setPlaceNo(invoice.getPlaceNo());
			his.setPlaceName(invoice.getPlaceName());
			his.setUp_Date(new Date());
			
			BigDecimal SEQ_CSMS_invoice_his_NO = sequenceUtil.getSequence("SEQ_CSMS_invoice_his_NO");
			his.setId(Long.valueOf(SEQ_CSMS_invoice_his_NO.toString()));
			
			
			invoice.setHisSeqId(his.getId());
			invoiceDao.saveInvoiceHis(his);
			invoiceDao.update(invoice);
			if(invoice.getIsdefault().equals("1")){
				invoiceDao.setDefault(invoice.getId(),invoice.getMainId());
			}
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setInvoiceId(invoice.getId());
			customerBussiness.setInvoicetitle(invoice.getInvoiceTitle());
			customerBussiness.setIsdefault(invoice.getIsdefault());
			customerBussiness.setCustomerId(invoice.getMainId());
			customerBussiness.setType(CustomerBussinessTypeEnum.invoiceUpdate.getValue());
			customerBussiness.setOperId(invoice.getOperId());
			customerBussiness.setOperNo(invoice.getOperNo());
			customerBussiness.setOperName(invoice.getOperName());
			customerBussiness.setPlaceId(invoice.getPlaceId());
			customerBussiness.setPlaceNo(invoice.getPlaceNo());
			customerBussiness.setPlaceName(invoice.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussiness.setInvoiceHisId(invoice.getHisSeqId());
			customerBussinessDao.save(customerBussiness);
			
			
			Customer customer = customerDao.findById(invoice.getMainId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("113");//113发票信息修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(invoice.getOperId());
			serviceWater.setOperName(invoice.getOperName());
			serviceWater.setOperNo(invoice.getOperNo());
			serviceWater.setPlaceId(invoice.getPlaceId());
			serviceWater.setPlaceName(invoice.getPlaceName());
			serviceWater.setPlaceNo(invoice.getPlaceNo());
			serviceWater.setRemark("自营客服系统：发票信息修改");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			//这里更新发票默认信息
			return true;
		} catch (ApplicationException e) {
			//logger.error(e.getMessage()+"修改客户注销信息,注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("更新发票登记信息，发票登记id:"+invoice.getId());
		}
	}
	
	/**
	 * 更新发票信息
	 * @author lzl
	 */
	public boolean deleteInvoice(Long invoiceId) {
		Invoice invoice = invoiceDao.findById(invoiceId);
		try {
			InvoiceHis his = new InvoiceHis();
			
			
			his.setGenTime(new Date());
			his.setGenReason("2");//2删除
			if(invoice.getHisSeqId() != null){
				his.setHisSeqId(invoice.getHisSeqId());
			}
			his.setIsdefault(invoice.getIsdefault());
			his.setMainId(invoice.getMainId());
			his.setOperId(invoice.getOperId());
			his.setOperNo(invoice.getOperNo());
			his.setOperName(invoice.getOperName());
			his.setPlaceId(invoice.getPlaceId());
			his.setPlaceNo(invoice.getPlaceNo());
			his.setPlaceName(invoice.getPlaceName());
			his.setUp_Date(new Date());
			
			BigDecimal SEQ_CSMS_invoice_his_NO = sequenceUtil.getSequence("SEQ_CSMS_invoice_his_NO");
			his.setId(Long.valueOf(SEQ_CSMS_invoice_his_NO.toString()));
			
			
			invoice.setHisSeqId(his.getId());
			invoiceDao.saveInvoiceHis(his);
			invoiceDao.delete(invoiceId);
			
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setInvoiceId(invoice.getId());
			customerBussiness.setInvoicetitle(invoice.getInvoiceTitle());
			customerBussiness.setIsdefault(invoice.getIsdefault());
			customerBussiness.setCustomerId(invoice.getMainId());
			customerBussiness.setType(CustomerBussinessTypeEnum.invoiceDelete.getValue());
			customerBussiness.setOperId(invoice.getOperId());
			customerBussiness.setOperNo(invoice.getOperNo());
			customerBussiness.setOperName(invoice.getOperName());
			customerBussiness.setPlaceId(invoice.getPlaceId());
			customerBussiness.setPlaceNo(invoice.getPlaceNo());
			customerBussiness.setPlaceName(invoice.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussiness.setInvoiceHisId(invoice.getHisSeqId());
			customerBussinessDao.save(customerBussiness);
			
			
			Customer customer = customerDao.findById(invoice.getMainId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("114");//114发票信息删除
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(invoice.getOperId());
			serviceWater.setOperName(invoice.getOperName());
			serviceWater.setOperNo(invoice.getOperNo());
			serviceWater.setPlaceId(invoice.getPlaceId());
			serviceWater.setPlaceName(invoice.getPlaceName());
			serviceWater.setPlaceNo(invoice.getPlaceNo());
			serviceWater.setRemark("自营客服系统：发票信息删除");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			//这里更新发票默认信息
			return true;
		} catch (ApplicationException e) {
			//logger.error(e.getMessage()+"修改客户注销信息,注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("更新发票登记信息，发票登记id:"+invoice.getId());
		}
	}


	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param customerBussiness 客户业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt,CustomerBussiness customerBussiness, BaseReceiptContent baseReceiptContent,Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.customer.getValue());
		receipt.setCreateTime(customerBussiness.getCreateTime());
		receipt.setPlaceId(customerBussiness.getPlaceId());
		receipt.setPlaceNo(customerBussiness.getPlaceNo());
		receipt.setPlaceName(customerBussiness.getPlaceName());
		receipt.setOperId(customerBussiness.getOperId());
		receipt.setOperNo(customerBussiness.getOperName());
		receipt.setOperName(customerBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}
}
