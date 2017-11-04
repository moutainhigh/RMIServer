package com.hgsoft.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.*;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.account.RefundReceipt;
import com.hgsoft.other.vo.receiptContent.account.RefundRevokeReceipt;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.account.serviceInterface.IRefundInfoService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.MaterialDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.serviceInterface.IMaterialService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
@Service
public class RefundInfoService implements IRefundInfoService {
	private static Logger logger = Logger.getLogger(RefundInfoService.class
			.getName());
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource 
	private RefundInfoHisDao refundInfoHisDao;
	@Resource
	private RechargeInfoDao rechargeInfoDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private AccountBussinessDao accountBussinessDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	MaterialDao materialDao;
	@Resource
	private IMaterialService materialService;
	@Resource
	private ReceiptDao receiptDao;
	
	@Override
	public Pager list(Pager pager,Date starTime, Date endTime, Customer customer) {
		return refundInfoDao.list(pager, starTime, endTime, customer);
	}
	@Override
	public Pager list(Pager pager,String starTime, String endTime, Customer customer) {
		return refundInfoDao.list(pager, starTime, endTime, customer);
	}
	@Override
	public RefundInfo findById(Long id) {
		return  refundInfoDao.findById(id);
	
	}
	/**
	 * 保存账户退款申请  typ=4
	 */
	@Override
	public boolean save(String type, RefundInfo refundInfo, MainAccountInfo mainAccountInfo) {
		try{
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSRefundInfo_NO");
			refundInfo.setId(Long.parseLong(seq.toString()));
			mainAccountInfo=mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType(type);
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setRefundInfo(refundInfo);
			refundInfo.setAuditStatus("1");//审核状态
			unifiedParam.setOperId(refundInfo.getOperId());
			unifiedParam.setPlaceId(refundInfo.getRefundApplyOper());
			unifiedParam.setOperName(refundInfo.getOperName());
			unifiedParam.setOperNo(refundInfo.getOperNo());
			unifiedParam.setPlaceName(refundInfo.getPlaceName());
			unifiedParam.setPlaceNo(refundInfo.getPlaceNo());
			if(unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
				/*if("2".equals(refundInfo.getRefundType())){//退款类型为账户装，要插主账户交款记录表
				RechargeInfo rechargeInfo=new RechargeInfo();
				Customer customer=new Customer();
				customer=customerDao.findById(mainAccountInfo.getMainId());
				rechargeInfo.setMainId(customer.getId());
				rechargeInfo.setMainAccountId(mainAccountInfo.getId());
				rechargeInfo.setPayMember(customer.getOrgan());
				rechargeInfo.setTransactionType("1");//缴款
				rechargeInfo.setRefundID(refundInfo.getId());//退款记录id
				rechargeInfo.setPayMentType("4");//缴款方式
				rechargeInfo.setTakeBalance(refundInfo.getCurrentRefundBalance());//设置交款额
				rechargeInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance().add(rechargeInfo.getTakeBalance()));
				//rechargeInfo.setBalance(mainAccountInfo.getBalance().add(rechargeInfo.getTakeBalance()));
				rechargeInfo.setBalance(mainAccountInfo.getBalance());
				rechargeInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());
				rechargeInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());
				//rechargeInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());
				rechargeInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance().subtract(rechargeInfo.getTakeBalance()));
				rechargeInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());
				rechargeInfo.setState(mainAccountInfo.getState());
				rechargeInfo.setOperId(refundInfo.getOperId());
				rechargeInfo.setPlaceId(refundInfo.getRefundApplyOper());
				rechargeInfo.setOperTime(new Date());
				BigDecimal SEQ_CSMSRechargeInfo_NO = sequenceUtil.getSequence("SEQ_CSMSRechargeInfo_NO");
				rechargeInfo.setId(Long.parseLong(SEQ_CSMSRechargeInfo_NO.toString()));
				rechargeInfoDao.save(rechargeInfo);
				refundInfo.setAuditStatus("5");//审核状态
			}*/
				refundInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());
				refundInfo.setBalance(mainAccountInfo.getBalance());
				refundInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());
				refundInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());
				refundInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());
				refundInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());
				refundInfoDao.save(refundInfo);
				
				
				AccountBussiness accountBussiness = new AccountBussiness();
				accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
				accountBussiness.setRealPrice(refundInfo.getCurrentRefundBalance());
				accountBussiness.setState("5");
				accountBussiness.setTradeTime(new Date());
				accountBussiness.setUserId(refundInfo.getMainId());
				accountBussiness.setOperId(refundInfo.getOperId());
				accountBussiness.setPlaceId(refundInfo.getRefundApplyOper());
				accountBussiness.setOperName(refundInfo.getOperName());
				accountBussiness.setOperNo(refundInfo.getOperNo());
				accountBussiness.setPlaceName(refundInfo.getPlaceName());
				accountBussiness.setPlaceNo(refundInfo.getPlaceNo());
				accountBussiness.setBussinessId(refundInfo.getId());
				
				accountBussinessDao.save(accountBussiness);
				
				
				Customer customer = customerDao.findById(accountBussiness.getUserId());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setAulAmt(accountBussiness.getRealPrice());
				serviceWater.setSerType("605");//账户退款
				serviceWater.setRemark("自营客服系统：账户退款");
				serviceWater.setFlowState("1");//正常
				serviceWater.setAccountBussinessId(accountBussiness.getId());
				serviceWater.setOperId(accountBussiness.getOperId());
				serviceWater.setOperNo(accountBussiness.getOperNo());
				serviceWater.setOperName(accountBussiness.getOperName());
				serviceWater.setPlaceId(accountBussiness.getPlaceId());
				serviceWater.setPlaceNo(accountBussiness.getPlaceNo());
				serviceWater.setPlaceName(accountBussiness.getPlaceName());
				serviceWater.setOperTime(new Date());
				serviceWaterDao.save(serviceWater);
				return true;
			}else{
				return false;
			}	
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"璐︽埛閫�娆剧敵璇峰け璐�");
			e.printStackTrace();
			throw new ApplicationException();
		}
		}	
	@Override
	public Map<String, Object> save(String type,RefundInfo refundInfo,MainAccountInfo mainAccountInfo,Customer customer, Material material, String[] tempPicNameList,String species,Long bussinessPlaceId,Map<String,Object> params) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try{
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSRefundInfo_NO");
		refundInfo.setId(Long.parseLong(seq.toString()));
		mainAccountInfo=mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());
		UnifiedParam unifiedParam = new UnifiedParam();
		unifiedParam.setType(type);
		unifiedParam.setMainAccountInfo(mainAccountInfo);
		unifiedParam.setRefundInfo(refundInfo);
		refundInfo.setAuditStatus("1");//审核状态
		unifiedParam.setOperId(refundInfo.getOperId());
		unifiedParam.setPlaceId(refundInfo.getRefundApplyOper());
		unifiedParam.setOperName(refundInfo.getOperName());
		unifiedParam.setOperNo(refundInfo.getOperNo());
		unifiedParam.setPlaceName(refundInfo.getPlaceName());
		unifiedParam.setPlaceNo(refundInfo.getPlaceNo());
		if(unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
			refundInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance());
			refundInfo.setBalance(mainAccountInfo.getBalance());
			refundInfo.setPreferentialBalance(mainAccountInfo.getPreferentialBalance());
			refundInfo.setFrozenBalance(mainAccountInfo.getFrozenBalance());
			refundInfo.setAvailableRefundBalance(mainAccountInfo.getAvailableRefundBalance());
			refundInfo.setRefundApproveBalance(mainAccountInfo.getRefundApproveBalance());
			
			//操作员所属营业部
			refundInfo.setBussinessPlaceId(bussinessPlaceId);
			
			refundInfoDao.save(refundInfo);
			/*if(!StringUtil.isEmpty(materialIds)){
				String materialId[]=null;
				if(materialIds!=null){
					materialId=materialIds.split(";");
					for(int i=0;i<materialId.length;i++){
						if(materialId[i]!=""){
							Material material=materialDao.findById(Long.parseLong(materialId[i]));
							material.setVehicleID(Long.parseLong(seq.toString()));
							materialDao.updateMateria(material);
						}
					}
				}
			}*/
			
			
			
			AccountBussiness accountBussiness = new AccountBussiness();
			accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
			accountBussiness.setRealPrice(refundInfo.getCurrentRefundBalance());
			accountBussiness.setState("5");
			accountBussiness.setTradeTime(new Date());
			accountBussiness.setUserId(refundInfo.getMainId());
			accountBussiness.setOperId(refundInfo.getOperId());
			accountBussiness.setPlaceId(refundInfo.getRefundApplyOper());
			accountBussiness.setOperName(refundInfo.getOperName());
			accountBussiness.setOperNo(refundInfo.getOperNo());
			accountBussiness.setPlaceName(refundInfo.getPlaceName());
			accountBussiness.setPlaceNo(refundInfo.getPlaceNo());
			accountBussiness.setBussinessId(refundInfo.getId());
			
			accountBussinessDao.save(accountBussiness);
			
			
			//Customer customer = customerDao.findById(accountBussiness.getUserId());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setSerType("605");//账户退款
			serviceWater.setRemark("自营客服系统：账户退款");
			serviceWater.setFlowState("1");//正常
			serviceWater.setAccountBussinessId(accountBussiness.getId());
			serviceWater.setOperId(accountBussiness.getOperId());
			serviceWater.setOperNo(accountBussiness.getOperNo());
			serviceWater.setOperName(accountBussiness.getOperName());
			serviceWater.setPlaceId(accountBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);

			//账户退款申请回执
			RefundReceipt refundReceipt = new RefundReceipt();
			refundReceipt.setTitle("账户退款申请回执");
			refundReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			refundReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
			refundReceipt.setCustomerSecondNo(customer.getSecondNo());
			refundReceipt.setCustomerSecondName(customer.getSecondName());
			refundReceipt.setBankNo(refundInfo.getBankNo());
			refundReceipt.setBankMember(refundInfo.getBankMember());
			refundReceipt.setBankOpenBranches(refundInfo.getBankOpenBranches());
			refundReceipt.setCurrentRefundBalance(NumberUtil.get2Decimal(refundInfo.getCurrentRefundBalance().doubleValue()*0.01));
			Receipt receipt = new Receipt();
			receipt.setBusinessId(accountBussiness.getId());
			receipt.setTypeCode(AccountBussinessTypeEnum.refund.getValue());
			receipt.setTypeChName(AccountBussinessTypeEnum.refund.getName());
			this.saveReceipt(receipt,accountBussiness,refundReceipt,customer);
			
			material.setBussinessId(refundInfo.getId());
			Map<String, Object> materiaMap = materialService.saveMateria(customer, null, material, tempPicNameList, null, species);
			if(materiaMap!=null&&"true".equals(materiaMap.get("result"))){
				String tempPicPaths = (String) materiaMap.get("tempPicPaths");
				String oldPicPaths = (String) materiaMap.get("oldPicPaths");
				String savePaths = (String) materiaMap.get("savePaths");
				
				resultMap.put("result", "true");
				resultMap.put("tempPicPaths", tempPicPaths);
				resultMap.put("oldPicPaths", oldPicPaths);
				resultMap.put("savePaths", savePaths);
				return resultMap;
			}else{
				resultMap.put("result", "false");
				resultMap.put("message", "图片资料处理失败");
				return resultMap;
			}
		}else{
			resultMap.put("result", "false");
			resultMap.put("message", "账户余额不足，退款失败！");
			return resultMap;
		}	
	} catch (ApplicationException e) {
		logger.error(e.getMessage()+"账户退款申请失败");
		e.printStackTrace();
		throw new ApplicationException();
	}
	}
	/**
	 * 账户退款申请修改
	 */
	@Override
	public void update(RefundInfo refundInfo) {
		// TODO Auto-generated method stub
		try{
		RefundInfoHis refundInfoHis = new RefundInfoHis();
		BigDecimal SEQ_CSMSRefundInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSRefundInfoHis_NO");
		refundInfoHis.setId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
		refundInfoHis.setCreateReason("修改");
		refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
		refundInfoDao.updateBankInfo(refundInfo);
	} catch (ApplicationException e) {
		logger.error(e.getMessage()+"账户退款申请修改");
		e.printStackTrace();
		throw new ApplicationException();
	}
		
	}
	/**
	 * 账户退款撤销 typ=5
	 * 
	 */
	
	@Override
	public boolean updateRetracement(String type, RefundInfo refundInfo, MainAccountInfo mainAccountInfo ,Map<String,Object> params) {
		try{
		UnifiedParam unifiedParam = new UnifiedParam();
		unifiedParam.setType(type);
		unifiedParam.setMainAccountInfo(mainAccountInfo);
		unifiedParam.setOperId(refundInfo.getOperId());
		unifiedParam.setPlaceId(refundInfo.getRefundApplyOper());
		unifiedParam.setOperName(refundInfo.getOperName());
		unifiedParam.setOperNo(refundInfo.getOperNo());
		unifiedParam.setPlaceName(refundInfo.getPlaceName());
		unifiedParam.setPlaceNo(refundInfo.getPlaceNo());
		
		refundInfo=refundInfoDao.findById(refundInfo.getId());
		unifiedParam.setRefundInfo(refundInfo);
		if(unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
			
			//将当前退款记录移入历史表
			RefundInfoHis refundInfoHis = new RefundInfoHis();
			BigDecimal SEQ_CSMSRefundInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSRefundInfoHis_NO");
			refundInfoHis.setId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
			refundInfoHis.setCreateReason("撤销");
			//更新退款记录
			refundInfo.setHisSeqId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
			refundInfo.setAuditStatus("6");//已撤销
			refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
			refundInfoDao.update(refundInfo);
			
			
			AccountBussiness accountBussiness = new AccountBussiness();
			accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
			accountBussiness.setRealPrice(refundInfo.getCurrentRefundBalance());
			accountBussiness.setState("6");
			accountBussiness.setTradeTime(new Date());
			accountBussiness.setUserId(refundInfo.getMainId());
			accountBussiness.setOperId(refundInfo.getOperId());
			accountBussiness.setPlaceId(refundInfo.getRefundApplyOper());
			accountBussiness.setOperName(refundInfo.getOperName());
			accountBussiness.setOperNo(refundInfo.getOperNo());
			accountBussiness.setPlaceName(refundInfo.getPlaceName());
			accountBussiness.setPlaceNo(refundInfo.getPlaceNo());
			accountBussiness.setBussinessId(refundInfoHis.getId());
			
			if(accountBussiness.getRealPrice()!=null)accountBussiness.setRealPrice(accountBussiness.getRealPrice().multiply(new BigDecimal("-1")));
			
			accountBussinessDao.save(accountBussiness);
			
			
			
			Customer customer = customerDao.findById(accountBussiness.getUserId());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setSerType("606");//账户退款撤销
			serviceWater.setRemark("自营客服系统：账户退款撤销");
			serviceWater.setAccountBussinessId(accountBussiness.getId());
			serviceWater.setOperId(accountBussiness.getOperId());
			serviceWater.setOperNo(accountBussiness.getOperNo());
			serviceWater.setOperName(accountBussiness.getOperName());
			serviceWater.setPlaceId(accountBussiness.getPlaceId());
			serviceWater.setPlaceNo(accountBussiness.getPlaceNo());
			serviceWater.setPlaceName(accountBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);
			
			//账户退款申请撤销回执
			AccountBussiness refundBussiness = this.accountBussinessDao.findByBusinessIdAndState(refundInfo.getId(),AccountBussinessTypeEnum.refund.getValue());
			Receipt refundReceipt = this.receiptDao.findByBusIdAndPTC(refundBussiness.getId(),ReceiptParentTypeCodeEnum.account.getValue());
			RefundRevokeReceipt refundRevokeReceipt = new RefundRevokeReceipt();
			refundRevokeReceipt.setTitle("账户退款申请撤销回执");
			refundRevokeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			refundRevokeReceipt.setRefundReceiptNo(refundReceipt.getReceiptNo());
			refundRevokeReceipt.setCustomerType(UserTypeEnum.getName(customer.getUserType()));
			refundRevokeReceipt.setCustomerSecondNo(customer.getSecondNo());
			refundRevokeReceipt.setCustomerSecondName(customer.getSecondName());
			refundRevokeReceipt.setBankNo(refundInfo.getBankNo());
			refundRevokeReceipt.setBankMember(refundInfo.getBankMember());
			refundRevokeReceipt.setBankOpenBranches(refundInfo.getBankOpenBranches());
			refundRevokeReceipt.setRevokeBalance(NumberUtil.get2Decimal(refundInfo.getCurrentRefundBalance().doubleValue()*0.01));
			Receipt receipt = new Receipt();
			receipt.setTypeCode(AccountBussinessTypeEnum.refundRevoke.getValue());
			receipt.setTypeChName(AccountBussinessTypeEnum.refundRevoke.getName());
			this.saveReceipt(receipt,accountBussiness,refundRevokeReceipt,customer);

			return true;
		}else{
			return false;
		}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"账户退款撤销失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * @Descriptioqn:
	 * @param refundInfo
	 * @param materialIds
	 * @return
	 * @author lgm
	 * @date 2017年5月5日
	 */
	@Override
	public boolean saveRefund(RefundInfo refundInfo, String materialIds) {
		return false;
	}
	/**
	 * @Descriptioqn:
	 * @param cardNo
	 * @param customerId
	 * @return
	 * @author lgm
	 * @date 2017年5月11日
	 */
	/*@Override
	public RefundInfo findByCardNoAndId(String cardNo, Long customerId) {
		try {
			return refundInfoDao.findByCardNoAndId(cardNo,customerId);
		} catch (Exception e) {
			logger.error("退款信息查询失败",e);
			e.printStackTrace();
			throw new ApplicationException();
		}
	}*/
	
	/**
	 * @Descriptioqn:  查找储值卡注销的退款记录
	 * @param cardNo
	 * @param refundType
	 * @return List<Map<String, Object>>
	 * @author gsf
	 * @date 2017年6月2日
	 */
	@Override
	public List<Map<String, Object>> findByCardNoAndType(String cardNo, String refundType) {
		try {
			return refundInfoDao.findByCardNoAndType(cardNo, refundType);
		} catch (Exception e) {
			logger.error("查找储值卡注销的退款记录",e);
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	@Override
	public Pager findRefundServicePager(Pager pager, RefundInfo refundInfo, String refundApplyStartTime,
			String refundApplyEndTime,Long bussinessPlaceId) {
		return refundInfoDao.findRefundServicePager(pager, refundInfo, refundApplyStartTime, refundApplyEndTime,bussinessPlaceId);
	}
	@Override
	public Pager findRefundServicePagerForAMMS(Pager pager,String bankCode,String cardType,RefundInfo refundInfo,String refundApplyStartTime,String refundApplyEndTime){
		return refundInfoDao.findRefundServicePagerForAMMS(pager,bankCode,cardType,refundInfo,refundApplyStartTime,refundApplyEndTime);
	}
	@Override
	public Map<String, Object> findRefundServiceDetail(Long refundInfoId) {
		return refundInfoDao.findRefundServiceDetail(refundInfoId);
	}
	@Override
	public Map<String, Object> saveRefundServiceCheckEdit(RefundInfo refundInfo) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//his
		RefundInfoHis refundInfoHis = new RefundInfoHis();
		BigDecimal SEQ_CSMSRefundInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSRefundInfoHis_NO");
		refundInfoHis.setId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
		refundInfoHis.setCreateReason("修改");
		refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
		//update
		//如果原本是主任审核不通过的退款记录，修改为申请状态
		if(refundInfo.getAuditStatus().equals(RefundAuditStatusEnum.directorNotPass.getValue()))
			refundInfo.setAuditStatus(RefundAuditStatusEnum.request.getValue());
		else 
			refundInfo.setAuditStatus(null);
		refundInfo.setHisSeqId(refundInfoHis.getId());
		refundInfoDao.updateNotNull(refundInfo);
		
		resultMap.put("result", "true");
		return resultMap;
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param accountBussiness 账户业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, AccountBussiness accountBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.account.getValue());
		receipt.setCreateTime(accountBussiness.getTradeTime());
		receipt.setPlaceId(accountBussiness.getPlaceId());
		receipt.setPlaceNo(accountBussiness.getPlaceNo());
		receipt.setPlaceName(accountBussiness.getPlaceName());
		receipt.setOperId(accountBussiness.getOperId());
		receipt.setOperNo(accountBussiness.getOperName());
		receipt.setOperName(accountBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}
}
