package com.hgsoft.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.common.Enum.*;
import com.hgsoft.customer.dao.*;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.customer.CustomerInfoCombineReceipt;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.myAbstract.entity.CombineExceptionCustomerDataJob;
import com.hgsoft.customer.serviceInterface.IExceptionCustomerDataService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.CustomerModifyApplyDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.SequenceUtil;

@Service
public class ExceptionCustomerDataService implements IExceptionCustomerDataService{
	private static Logger logger = Logger.getLogger(ExceptionCustomerDataService.class.getName());
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private ExceptionCustomerDataDao exceptionCustomerDataDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerModifyApplyDao customerModifyApplyDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private CustomerCombineRecordDao customerCombineRecordDao;
	@Resource
	private CustomerHisDao customerHisDao;
	
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private CombineExceptionCustomerDataJob combineExceptionCustomerDataJob;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;

	@Override
	public List<Map<String, Object>> findExceptionCustomerDatas(Customer searchCustomer, VehicleInfo searchVehicle,
			String cardNo, String tagNo) {
		return exceptionCustomerDataDao.findExceptionCustomerDatas(searchCustomer, searchVehicle, cardNo, tagNo);
	}

	@Override
	public List<Map<String, Object>> findProductInfoList(Long customerId) {
		return exceptionCustomerDataDao.findProductInfoList(customerId);
	}

	@Override
	public List<Map<String, Object>> findVehicleList(Long customerId) {
		return exceptionCustomerDataDao.findVehicleList(customerId);
	}

	@Override
	public void updateExceptionCustomer(Customer customer, CustomerBussiness customerBussiness,
			Customer beforeCustomer) {
		
		try {
			//客户进入历史
			CustomerHis existCustomerHis = new CustomerHis(beforeCustomer);
			existCustomerHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_Customer_his_NO"));
			existCustomerHis.setGenReason("1");//资料更改
			existCustomerHis.setGenTime(new Date());
			customerHisDao.save(existCustomerHis);
			//修改客户信息
			customer.setHandleFlag("1");//数据迁移相关：异常客户数据是否已处理标志。1：已处理
			customerDao.update4NotNullStr(customer);
			
			//保存客户信息业务记录
			Long customerBussinessId = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			customerBussiness.setId(customerBussinessId);
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
			serviceWater.setUserName(customer.getOrgan());//使用修改后的客户名称，无论审核是否通过
			serviceWater.setSerType(ServiceWaterSerType.customerInfoUpdate.getValue());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(customer.getOperId());
			serviceWater.setOperName(customer.getOperName());
			serviceWater.setOperNo(customer.getOperNo());
			serviceWater.setPlaceId(customer.getPlaceId());
			serviceWater.setPlaceName(customer.getPlaceName());
			serviceWater.setPlaceNo(customer.getPlaceNo());
			serviceWater.setRemark("自营客服系统：异常客户资料修改");
			serviceWater.setOperTime(customerBussiness.getCreateTime());
			
			serviceWaterDao.save(serviceWater);
			
			//按需求，不需要送审
			//若要保存客户信息修改申请记录
			/*if(customerModifyApply != null){
				Long customerModifyApplyId = sequenceUtil.getSequenceLong("SEQ_CSMSCUSTOMERMODIFYAPPLY_NO");
				customerModifyApply.setId(customerModifyApplyId);
				customerModifyApplyDao.saveCustomerModifyApply(customerModifyApply);
			}*/
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"异常客户信息失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Map<String, Object> saveCombine(Customer existCustomer, Customer thisCustomer,CustomerBussiness customerBussiness,Map<String,Object> params) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			MainAccountInfo existMainAccountInfo = mainAccountInfoDao.findByMainId(existCustomer.getId());
			MainAccountInfo thisMainAccountInfo = mainAccountInfoDao.findByMainId(thisCustomer.getId());
			
			//合并的客户信息字段
			combineExceptionCustomerDataJob.combineCustomerInfo(existCustomer, thisCustomer);
			
			//车辆信息合并
			exceptionCustomerDataDao.combineVehicleInfo(existCustomer, thisCustomer);
			
			//特殊名单表CSMS_Special_list
			exceptionCustomerDataDao.combineSpecialList(existCustomer, thisCustomer);
			
			//资金合并
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setMainAccountInfo(existMainAccountInfo);
			unifiedParam.setBeCombinedMainAccountInfo(thisMainAccountInfo);
			unifiedParam.setType(AccChangeTypeEnum.accountCombine.getValue());
			unifiedParam.setOperId(customerBussiness.getOperId());
			unifiedParam.setOperNo(customerBussiness.getOperNo());
			unifiedParam.setOperName(customerBussiness.getOperName());
			unifiedParam.setPlaceId(customerBussiness.getPlaceId());
			unifiedParam.setPlaceNo(customerBussiness.getPlaceNo());
			unifiedParam.setPlaceName(customerBussiness.getPlaceName());
			unifiedInterfaceService.saveAccAvailableBalance(unifiedParam);
			
			//主账户缴款记录表合并(不需要)
			
			//主账户退款记录表合并(状态非：4、6、8)
			exceptionCustomerDataDao.combineRefundInfo(existCustomer, thisCustomer);
			
			//银行到账信息表合并
			exceptionCustomerDataDao.combineBankTransferInfo(existCustomer, thisCustomer);
			
			//子账户信息表合并
			exceptionCustomerDataDao.combineSubAccountInfo(existMainAccountInfo, thisMainAccountInfo);
			
			//缴款单信息表
			
			//储值卡卡信息表
			exceptionCustomerDataDao.combinePrepaidC(existCustomer, thisCustomer);
			
			//储值卡业务记录表
			exceptionCustomerDataDao.combinePrepaidCBussiness(existCustomer, thisCustomer);
			
			//快速充值登记表
			exceptionCustomerDataDao.combineAddReg(existCustomer, thisCustomer);
			
			//储值卡过户记录表
			
			//储值卡发票类型变更记录表
			
			//记帐卡信息表
			exceptionCustomerDataDao.combineAccountCInfo(existCustomer, thisCustomer);
			
			//记帐卡业务记录表
			
			//记帐卡申请表
			exceptionCustomerDataDao.combineAccountCApply(existCustomer, thisCustomer);
			
			//记帐卡银行信息表
			
			//保证金设置表
			exceptionCustomerDataDao.combineBail(existCustomer, thisCustomer);
			
			//记账卡通行费汇总表
			
			//电子标签发行信息表
			exceptionCustomerDataDao.combineTagInfo(existCustomer, thisCustomer);
			
			//电子标签维护记录表
			exceptionCustomerDataDao.combineTagMainRecord(existCustomer, thisCustomer);
			
			//电子标签迁移申请记录表(现在不需要迁移审批，所以应该不需要这个)
			
			//发票打印记录表
			
			//通行费账单表
			
			//图片资料表
			
			//回执打印主表(不需要)
			
			//回执记录表(不需要)
			
			//清帐单表要吗？
			
			//业务记录保存
			
			//客服流水保存
			
			//保存客户合并记录
			//被合并客户信息：要显示合并修改前的
			thisCustomer = customerDao.findById(thisCustomer.getId());
			//合并后客户信息：要显示合并后的金额
			existMainAccountInfo = mainAccountInfoDao.findById(existMainAccountInfo.getId());
			combineExceptionCustomerDataJob.saveCustomerCombineRecord(existCustomer, thisCustomer, existMainAccountInfo, thisMainAccountInfo, customerBussiness);

			//客户信息合并回执(thisCustomer被销户，existCustomer为合并后信息？)
			CustomerInfoCombineReceipt customerInfoCombineReceipt = new CustomerInfoCombineReceipt();
			customerInfoCombineReceipt.setTitle("客户信息合并回执");
			customerInfoCombineReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			customerInfoCombineReceipt.setCustomerType(UserTypeEnum.getName(existCustomer.getUserType()));
			customerInfoCombineReceipt.setCustomerSecondNo(existCustomer.getSecondNo());
			customerInfoCombineReceipt.setCustomerSecondName(existCustomer.getSecondName());
			customerInfoCombineReceipt.setCustomerLinkMan(existCustomer.getLinkMan());
			customerInfoCombineReceipt.setCustomerMobile(existCustomer.getMobile());
			customerInfoCombineReceipt.setCustomerShortTel(existCustomer.getShortTel());
			customerInfoCombineReceipt.setCustomerTel(existCustomer.getTel());
			customerInfoCombineReceipt.setCustomerEmail(existCustomer.getEmail());
			customerInfoCombineReceipt.setCustomerZipCode(existCustomer.getZipCode());
			customerInfoCombineReceipt.setCustomerAddr(existCustomer.getAddr());
			//储值卡记账卡
			List<Map<String,Object>> cardList = this.prepaidCDao.findCardNoByCustomerId(existCustomer.getId());
			if(CollectionUtils.isEmpty(cardList)){
				cardList = this.accountCDao.findCardNoByCustomerId(existCustomer.getId());
			}else{
				cardList.addAll(this.accountCDao.findCardNoByCustomerId(existCustomer.getId()));
			}
			customerInfoCombineReceipt.setCardNoJson(JSONArray.fromObject(cardList).toString());
			customerInfoCombineReceipt.setTagNoJson(JSONArray.fromObject(this.tagInfoDao.findTagNoByCustomerId(existCustomer.getId())).toString());	//电子标签号
			customerInfoCombineReceipt.setPlateAndColorJson(JSONArray.fromObject(this.vehicleInfoDao.findPlateAndColorByCustomerId(existCustomer.getId())).toString());	//车牌号码和颜色
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.customerInfoCombine.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.customerInfoCombine.getName());
			this.saveReceipt(receipt,customerBussiness,customerInfoCombineReceipt,existCustomer);

		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户合并失败!");
			throw new ApplicationException();
		}
		resultMap.put("result", "true");
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> findCustomerCombineRecordList(String combineStartTime, String combineEndTime,
			Customer searchCustomer) {
		return customerCombineRecordDao.findCustomerCombineRecordList(combineStartTime, combineEndTime, searchCustomer);
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param customerBussiness 客户业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, CustomerBussiness customerBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
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
