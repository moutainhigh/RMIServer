package com.hgsoft.ivr.service;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.customer.dao.CustomerBussinessDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.ivr.dao.IVRDao;
import com.hgsoft.ivr.entity.ClientLoginInfo;
import com.hgsoft.ivr.entity.ReqInterfaceFlow;
import com.hgsoft.ivr.serviceInterface.IIVRInterfaceService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.settlement.entity.AcBillInfoMonthly;
import com.hgsoft.settlement.entity.ScBillInfoMonthly;
import com.hgsoft.settlement.serviceinterface.IAcBillInfoMonthlyService;
import com.hgsoft.settlement.serviceinterface.IScBillInfoMonthlyService;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ivr接口服务类
 * @author guanshaofeng
 */
@Service
public class IVRInterfaceService implements IIVRInterfaceService{

	private static final Logger logger = LoggerFactory.getLogger(IVRInterfaceService.class);

	private static final BigDecimal FEN = new BigDecimal("0.01");
	
	@Resource
	private IAccountCService accountCService;
	@Resource
	private IPrepaidCService prepaidCService;
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private IVRDao ivrDao;
	@Resource
	private AccountCApplyDao  accountCApplyDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private CustomerHisDao customerHisDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private ReceiptDao receiptDao;
	/*@Resource
	private AcInvoiceDao acInvoiceDao;
	@Resource
	private ScInvoiceDao scInvoiceDao;*/
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private IAcBillInfoMonthlyService acBillInfoMonthlyService;
	@Resource
	private IScBillInfoMonthlyService scBillInfoMonthlyService;
	
	/**
	 * 增加接口请求流水
	 */
	@Override
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow) {
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSREQINTERFACEFLOW_NO");
		reqInterfaceFlow.setId(seq);
		ivrDao.saveReqInterfaceFlow(reqInterfaceFlow);
	}
	
	@Override
	public AccountCInfo findACByCardNo(String cardNo,String cardType) {
		return ivrDao.findACByNOandType(cardNo, cardType);
	}

	@Override
	public PrepaidC findPCByCardNo(String cardNo,String cardType) {
		return ivrDao.findPCByNOandType(cardNo, cardType);
	}
	@Override
	public List<Map<String, Object>> findCardByNOandType(String cardNo, String cardKind) {
		return ivrDao.findCardByNOandType(cardNo, cardKind);
	}
	
	/**
	 * 判断卡号与银行账号的组合是否存在
	 */
	@Override
	public boolean checkAccountNOandCardNo(String accountNo, String cardNo) {
		List<Map<String, Object>> list = ivrDao.checkAccountNOandCardNo(accountNo, cardNo);
		if(list!=null&&!list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	/**判断卡号是否存在
	 * cardNo
	 * cardKind  0:普通卡  1：快易通
	 */
	@Override
	public Map<String, String> findIsExistCardNo(String cardNo, String cardKind) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			List<Map<String, Object>> list = ivrDao.findCardByNOandType(cardNo, cardKind);
			if(list!=null&&!list.isEmpty()){
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "1");//卡号不存在
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：判断卡号是否存在失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：判断卡号是否存在失败");
		}
		return resultMap;
	}

	/**
	 * 卡号密码鉴权
	 * cardNo
	 * cardKind  0:普通卡  1：快易通
	 * password  3des解密后的卡服务密码
	 */
	@Override
	public Map<String, String> findVerifyByCardNo(String cardNo, String cardKind, String password) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			List<Map<String, Object>> list = ivrDao.findCardByNOandType(cardNo, cardKind);
			if(list!=null&&!list.isEmpty()){
				Map<String, Object> map = list.get(0);
				
				if(!StringUtil.isNotBlank(map.get("cardServicePwd")+"")){
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
					return resultMap;
				}
				
				if(StringUtil.md5(password).equals(map.get("cardServicePwd"))){
					//客户服务密码鉴权成功
					resultMap.put("result", "success");
					resultMap.put("errorCode", "");
				}else{
					//客户服务密码鉴权失败
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
				}
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "1");//卡号不存在
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：密码鉴权失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：密码鉴权失败");
		}
		return resultMap;
	}

	/**
	 * 判断银行账号是否存在
	 * accountNo
	 */
	@Override
	public Map<String, String> findIsExistAccountNo(String accountNo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			AccountCApply accountCApply = accountCApplyDao.findByBankAccount(accountNo);
			if(accountCApply!=null){
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "2");//银行账号 不存在
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：判断银行账号是否存在失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：判断银行账号是否存在失败");
		}
		return resultMap;
	}

	/**
	 * 客户号密码鉴权
	 * userNo   客户号
	 * password  3des解密后的客户服务密码
	 */
	@Override
	public Map<String, String> findVerifyByUserNo(String userNo, String password) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			Customer customer = customerDao.findByUserNo(userNo);
			if(customer!=null){
				if(!StringUtil.isNotBlank(customer.getServicePwd())){
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码校验失败
					logger.info("客户的服务密码为空");
					return resultMap;
				}
				
				//最后判断密码是否一致
				if(StringUtil.md5(password).equals(customer.getServicePwd())){
					resultMap.put("result", "success");
					resultMap.put("errorCode", "");//
				}else{
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码校验失败
					logger.info("密码不一致，即密码鉴权失败");
				}
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库错误
				logger.info("没有找到相关客户");
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：银行账号密码鉴权失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：银行账号密码鉴权失败");
		}
		return resultMap;
	}

	/**
	 * 根据卡号修改卡的服务密码
	 * @param cardNo
	 * @param cardKind
	 * @param oldPwd
	 * @param newPwd
	 * @return Map<String,String>
	 */
	@Override
	public Map<String, String> saveChangPwdByCardNo(String cardNo, String cardKind, String oldPwd, String newPwd, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			AccountCInfo accountCInfo = null;
			PrepaidC prepaidC = null;
			Customer customer = null;
			String oldCardServicePwd = "";
			accountCInfo = ivrDao.findACByNOandType(cardNo, cardKind);
			if(accountCInfo == null){
				prepaidC = ivrDao.findPCByNOandType(cardNo, cardKind);
				if(prepaidC!=null){
					customer = customerDao.findById(prepaidC.getCustomerID());
					
					//首先判断卡的旧服务密码是否为空
					if(!StringUtil.isNotBlank(prepaidC.getCardServicePwd())){
						//卡的旧服务密码为空
						resultMap.put("result", "fail");
						resultMap.put("errorCode", "3");//密码验证失败
						logger.info("卡的旧服务密码为空");
						return resultMap;
					}else{
						oldCardServicePwd = prepaidC.getCardServicePwd();
					}
				}
			}else{
				customer = customerDao.findById(accountCInfo.getCustomerId());
				
				//首先判断卡的旧服务密码是否为空
				if(!StringUtil.isNotBlank(accountCInfo.getCardServicePwd())){
					//卡的旧服务密码为空
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
					logger.info("卡的旧服务密码为空");
					return resultMap;
				}
				
				oldCardServicePwd = accountCInfo.getCardServicePwd();
			}
			
			if(accountCInfo==null&&prepaidC==null){
				//卡号不存在
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "1");//卡号不存在
				logger.info("卡号不存在");
				return resultMap;
			}
			
			if(customer!=null){
				if(StringUtil.md5(oldPwd).equals(oldCardServicePwd)){
					/*//旧服务密码一致，执行下面操作
					//首先将customer信息移到历史表
					CustomerHis customerHis = new CustomerHis(customer);
					customerHis.setGenReason("1");
					customerHis.setGenTime(new Date());
					customerHis.setHisSeqId(customer.getHisSeqId());
					BigDecimal Customer_his_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_his_NO");
					customerHis.setId(Long.valueOf(Customer_his_NO.toString()));
					customerHisDao.save(customerHis);
					//修改密码
					customer.setServicePwd(StringUtil.md5(newPwd));//设置新密码
					customer.setHisSeqId(customerHis.getId());
					customerDao.update(customer);
					
					//客户信息业务记录表
					CustomerBussiness customerBussiness = new CustomerBussiness();
					customerBussiness.setOldcustomerId(customer.getHisSeqId());
					Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
					
					customerBussiness.setId(seq);
					customerBussiness.setCustomerId(customer.getId());
					
					customerBussiness.setType(CustomerBussinessTypeEnum.passwordUpdate.getValue());
					customerBussiness.setOperId(sysAdmin.getId());
					customerBussiness.setOperNo(sysAdmin.getStaffNo());
					customerBussiness.setOperName(sysAdmin.getUserName());
					customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
					customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
					customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
					customerBussiness.setCreateTime(new Date());
					customerBussinessDao.save(customerBussiness);*/
					
					//下面是修改卡的服务密码的代码
					if(accountCInfo!=null&&prepaidC==null){
						AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
						Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCINFOHIS_NO");
						accountCInfoHis.setId(hisId);
						accountCInfoHis.setGenReason("18");//18卡服务密码修改
						
						accountCInfoHisDao.save(accountCInfo, accountCInfoHis);
						
						accountCInfo.setHisSeqId(accountCInfoHis.getId());
						accountCInfo.setCardServicePwd(StringUtil.md5(newPwd));
						accountCInfoDao.update(accountCInfo);
						
						//保存业务记录表
						AccountCBussiness accountCBussiness = new AccountCBussiness();
						BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
						accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
						accountCBussiness.setUserId(customer.getId());
						accountCBussiness.setCardNo(accountCInfo.getCardNo());
						accountCBussiness.setAccountId(accountCInfo.getAccountId());
						accountCBussiness.setState("28");// 28.	服务密码修改
						accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
						accountCBussiness.setTradeTime(new Date());
						accountCBussiness.setOperId(sysAdmin.getId());
						accountCBussiness.setPlaceId(cusPointPoJo.getCusPoint());
						//新增的字段
						accountCBussiness.setOperName(sysAdmin.getUserName());
						accountCBussiness.setOperNo(sysAdmin.getStaffNo());
						accountCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
						accountCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
						//accountCBussiness.setBusinessId(accountCApply.getHisseqId());
						//receiptDao.saveByBussiness(null, null, null, null, accountCBussiness);
						accountCBussinessDao.save(accountCBussiness);
						
					}else if(accountCInfo==null&&prepaidC!=null){
						//储值卡历史
						PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(), "", prepaidC);
						prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO"));
						prepaidCHis.setGenreason("12");//12：服务密码修改
						prepaidCDao.saveHis(prepaidCHis);
						
						prepaidC.setHisSeqID(prepaidCHis.getId());
						prepaidC.setCardServicePwd(StringUtil.md5(newPwd));
						prepaidCDao.update(prepaidC);
						
						PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
						prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));

						prepaidCBussiness.setUserid(prepaidC.getCustomerID());
						prepaidCBussiness.setCardno(prepaidC.getCardNo());
						prepaidCBussiness.setState("92"); // 92卡服务密码修改
						prepaidCBussiness.setPlaceid(cusPointPoJo.getCusPoint());
						prepaidCBussiness.setOperid(sysAdmin.getId());
						prepaidCBussiness.setOperName(sysAdmin.getUserName());
						prepaidCBussiness.setOperNo(sysAdmin.getStaffNo());
						prepaidCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
						prepaidCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
						prepaidCBussiness.setTradetime(new Date());
						prepaidCBussinessDao.save(prepaidCBussiness);
						
					}
					
					resultMap.put("result", "success");
					resultMap.put("errorCode", "");
				}else{
					//客户服务密码旧密码错误
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
					logger.info("卡的旧服务密码不一致");
				}
			}else{
				//找不到客户，即验证不了旧密码
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
				logger.info("找不到客户");
			}
				
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据卡号修改卡的服务密码失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据卡号修改卡的服务密码失败");
		}
		return resultMap;	
	}
	
	/**
	 * 根据客户号修改客户服务密码
	 */
	@Override
	public Map<String, String> saveChangPwdByUserNo(String userNo, String oldPwd, String newPwd,
			SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			//AccountCApply accountCApply = accountCApplyDao.findByBankAccount(accountNo);
			Customer customer = customerDao.findByUserNo(userNo);
			if(customer!=null){
				if(StringUtil.md5(oldPwd).equals(customer.getServicePwd())){
					//旧服务密码一致，执行下面操作
					//首先将customer信息移到历史表
					CustomerHis customerHis = new CustomerHis(customer);
					customerHis.setGenReason("1");
					customerHis.setGenTime(new Date());
					customerHis.setHisSeqId(customer.getHisSeqId());
					BigDecimal Customer_his_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_his_NO");
					customerHis.setId(Long.valueOf(Customer_his_NO.toString()));
					customerHisDao.save(customerHis);
					//修改密码
					customer.setServicePwd(StringUtil.md5(newPwd));//设置新密码
					customer.setHisSeqId(customerHis.getId());
					customerDao.update(customer);
					
					//客户信息业务记录表
					CustomerBussiness customerBussiness = new CustomerBussiness();
					customerBussiness.setOldcustomerId(customer.getHisSeqId());
					Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
					
					customerBussiness.setId(seq);
					customerBussiness.setCustomerId(customer.getId());
					
					customerBussiness.setType(CustomerBussinessTypeEnum.passwordUpdate.getValue());
					customerBussiness.setOperId(sysAdmin.getId());
					customerBussiness.setOperNo(sysAdmin.getStaffNo());
					customerBussiness.setOperName(sysAdmin.getUserName());
					customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
					customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
					customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
					customerBussiness.setCreateTime(new Date());
					customerBussinessDao.save(customerBussiness);
					
					resultMap.put("result", "success");
					resultMap.put("errorCode", "");
				}else{
					//客户服务密码旧密码错误
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
				}
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
				logger.info("没有找到相关客户");
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据银行账号修改客户服务密码失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据银行账号修改客户服务密码失败");
		}
		return resultMap;
	}

	/**
	 * 根据卡号挂失卡片
	 */
	@Override
	public Map<String, String> saveLostByCardNo(AccountCInfo accountCInfo,PrepaidC prepaidC,String systemType) {
		Map<String, String> resultMap = new HashMap<String,String>();
		Receipt receipt = null;
		try {
			
			if(accountCInfo!=null&&prepaidC==null){
				accountCService.saveLock(accountCInfo, systemType,new HashMap<String,Object>());
				
				Map<String, Object> acMap = ivrDao.findLastByCardNo(accountCInfo);
				if(acMap!=null){
					Receipt tempRec = new Receipt();
					tempRec.setBusinessId(Long.parseLong(acMap.get("id")+""));
					tempRec.setParentTypeCode("3");//记帐卡大类
					receipt = receiptDao.find(tempRec);
				}
				
				resultMap.put("result", "success");
				if(receipt!=null)resultMap.put("listNo", receipt.getReceiptNo().substring(2));//挂失回执编号
				else resultMap.put("listNo", "");//挂失回执编号
				resultMap.put("errorCode", "");
			}else if(accountCInfo==null&&prepaidC!=null){
				prepaidCService.saveLock(prepaidC,new HashMap<String,Object>());
				
				Map<String, Object> acMap = ivrDao.findLastByCardNo(prepaidC);
				if(acMap!=null){
					Receipt tempRec = new Receipt();
					tempRec.setBusinessId(Long.parseLong(acMap.get("id")+""));
					tempRec.setParentTypeCode("2");//储值卡大类
					receipt = receiptDao.find(tempRec);
				}
				
				resultMap.put("result", "success");
				if(receipt!=null)resultMap.put("listNo", receipt.getReceiptNo().substring(2));//挂失回执编号
				else resultMap.put("listNo", "");//挂失回执编号
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
				logger.info("没有找到记帐卡对象和储值卡对象");
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据卡号挂失卡片失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据卡号挂失卡片失败");
		}
		return resultMap;
	}

	/**
	 * 根据客户号挂失卡片
	 */
	@Override
	public Map<String, String> saveLostByUserNo(AccountCInfo accountCInfo,PrepaidC prepaidC, String systemType) {
		Map<String, String> resultMap = new HashMap<String,String>();
		Receipt receipt = null;
		try {
			
			if(accountCInfo!=null&&prepaidC==null){
				accountCService.saveLock(accountCInfo, systemType,new HashMap<String,Object>());
				
				Map<String, Object> acMap = ivrDao.findLastByCardNo(accountCInfo);
				if(acMap!=null){
					Receipt tempRec = new Receipt();
					tempRec.setBusinessId(Long.parseLong(acMap.get("id")+""));
					tempRec.setParentTypeCode("3");//记帐卡大类
					receipt = receiptDao.find(tempRec);
				}
				
				resultMap.put("result", "success");
				if(receipt!=null)resultMap.put("listNo", receipt.getReceiptNo().substring(2));//挂失回执编号
				else resultMap.put("listNo", "");//挂失回执编号
				resultMap.put("errorCode", "");
			}else if(accountCInfo==null&&prepaidC!=null){
				prepaidCService.saveLock(prepaidC,new HashMap<String,Object>());
				
				Map<String, Object> acMap = ivrDao.findLastByCardNo(prepaidC);
				if(acMap!=null){
					Receipt tempRec = new Receipt();
					tempRec.setBusinessId(Long.parseLong(acMap.get("id")+""));
					tempRec.setParentTypeCode("2");//储值卡大类
					receipt = receiptDao.find(tempRec);
				}
				
				resultMap.put("result", "success");
				if(receipt!=null)resultMap.put("listNo", receipt.getReceiptNo().substring(2));//挂失回执编号
				else resultMap.put("listNo", "");//挂失回执编号
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
				logger.info("没有找到记帐卡对象和储值卡对象");
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据银行账号挂失卡片失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据银行账号挂失卡片失败");
		}
		return resultMap;
	}

	/**
	 * 根据卡号解挂
	 */
	@Override
	public Map<String, String> saveReleaseByCardNo(AccountCInfo accountCInfo, PrepaidC prepaidC, String systemType) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			
			if(accountCInfo!=null&&prepaidC==null){
				accountCService.saveUnLock(accountCInfo, systemType, new HashMap<String, Object>());
				
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else if(accountCInfo==null&&prepaidC!=null){
				prepaidCService.unLock(prepaidC, new HashMap<String, Object>());
				
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据卡号解挂失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据卡号解挂失败");
		}
		return resultMap;
	}

	/**
	 * 根据客户号解挂
	 */
	@Override
	public Map<String, String> saveReleaseByUserNo(AccountCInfo accountCInfo,PrepaidC prepaidC, String systemType) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			
			if(accountCInfo!=null&&prepaidC==null){
				accountCService.saveUnLock(accountCInfo, systemType, new HashMap<String, Object>());
				
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else if(accountCInfo==null&&prepaidC!=null){
				prepaidCService.unLock(prepaidC, new HashMap<String, Object>());
				
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据银行账号解挂失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据银行账号解挂失败");
		}
		return resultMap;
	}

	private Map<String, Object> findScBillMapByCardNo(PrepaidC prepaidC, String settleMonth) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		if (StringUtils.isBlank(prepaidC.getCardNo())) {
			logger.warn("卡号为空");
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "6");//数据库出错
			return resultMap;
		}
		List<ScBillInfoMonthly> scBillInfoMonthlies = scBillInfoMonthlyService.findUserBillByCardNoAndSettleMonth(prepaidC.getCardNo(), settleMonth);
		if (scBillInfoMonthlies == null || scBillInfoMonthlies.isEmpty()) {
			logger.warn("储值卡[{}]结算月[{}]没有账单", prepaidC.getCardNo(), settleMonth);
			resultMap.put("dealNum", 0);
			resultMap.put("realDealFee", BigDecimal.ZERO);
			resultMap.put("onceNum", 0);
			resultMap.put("onceFee", BigDecimal.ZERO);
			resultMap.put("chargeNum", 0);
			resultMap.put("chargeFee", BigDecimal.ZERO);
			resultMap.put("otherNum", 0);
			resultMap.put("otherFee", BigDecimal.ZERO);
			resultMap.put("discountFee", BigDecimal.ZERO);
			resultMap.put("transferFee", BigDecimal.ZERO);
			return resultMap;
		}
		if (scBillInfoMonthlies.size() > 1) {
			logger.error("储值卡[{}]结算月[{}]账单数量[{}]多于1条记录，异常", prepaidC.getCardNo(), settleMonth, scBillInfoMonthlies.size());
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "16");//数据库出错
			return resultMap;
		}
		ScBillInfoMonthly scBillInfoMonthly = scBillInfoMonthlies.get(0);
		if (scBillInfoMonthly.getDealNum() != null) {
			resultMap.put("dealNum", scBillInfoMonthly.getDealNum());
		} else {
			resultMap.put("dealNum", 0);
		}
		if (scBillInfoMonthly.getRealDealFee() != null) {
			resultMap.put("realDealFee", scBillInfoMonthly.getRealDealFee().multiply(FEN));
		} else {
			resultMap.put("realDealFee", BigDecimal.ZERO);
		}
		if (scBillInfoMonthly.getOnceNum() != null) {
			resultMap.put("onceNum", scBillInfoMonthly.getOnceNum());
		} else {
			resultMap.put("onceNum", 0);
		}
		if (scBillInfoMonthly.getOnceFee() != null) {
			resultMap.put("onceFee", scBillInfoMonthly.getOnceFee().multiply(FEN));
		} else {
			resultMap.put("onceFee", BigDecimal.ZERO);
		}
		if (scBillInfoMonthly.getRechargeNum() != null) {
			resultMap.put("chargeNum", scBillInfoMonthly.getRechargeNum());
		} else {
			resultMap.put("chargeNum", 0);
		}
		if (scBillInfoMonthly.getRechargeFee() != null) {
			resultMap.put("chargeFee", scBillInfoMonthly.getRechargeFee().multiply(FEN));
		} else {
			resultMap.put("chargeFee", BigDecimal.ZERO);
		}
		if (scBillInfoMonthly.getOtherNum() != null) {
			resultMap.put("otherNum", scBillInfoMonthly.getOtherNum());
		} else {
			resultMap.put("otherNum", 0);
		}
		if (scBillInfoMonthly.getOtherFee() != null) {
			resultMap.put("otherFee", scBillInfoMonthly.getOtherFee().multiply(FEN));
		} else {
			resultMap.put("otherFee", BigDecimal.ZERO);
		}
		if (scBillInfoMonthly.getHadReturnFee() != null) {
			resultMap.put("discountFee", scBillInfoMonthly.getHadReturnFee().multiply(FEN));
		} else {
			resultMap.put("discountFee", BigDecimal.ZERO);
		}
		if (scBillInfoMonthly.getTransferFee() != null) {
			resultMap.put("transferFee", scBillInfoMonthly.getTransferFee().multiply(FEN));
		} else {
			resultMap.put("transferFee", BigDecimal.ZERO);
		}
		return resultMap;
	}


	private Map<String, Object> findAcBillMapByCardNo(AccountCInfo accountCInfo, String settleMonth) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		if (StringUtils.isBlank(accountCInfo.getCardNo())) {
			logger.warn("卡号为空");
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "6");//数据库出错
			return resultMap;
		}

		List<AcBillInfoMonthly> acBillInfoMonthlies = acBillInfoMonthlyService.findUserBillByCardNoAndSettleMonth(accountCInfo.getCardNo(), settleMonth);
		if (acBillInfoMonthlies == null || acBillInfoMonthlies.isEmpty()) {
			logger.warn("记账卡[{}]结算月[{}]没有账单", accountCInfo.getCardNo(), settleMonth);
			resultMap.put("dealNum", 0);
			resultMap.put("realDealFee", BigDecimal.ZERO);
			resultMap.put("onceNum", 0);
			resultMap.put("onceFee", BigDecimal.ZERO);
			resultMap.put("chargeNum", 0);
			resultMap.put("chargeFee", BigDecimal.ZERO);
			resultMap.put("otherNum", 0);
			resultMap.put("otherFee", BigDecimal.ZERO);
			resultMap.put("discountFee", BigDecimal.ZERO);
			resultMap.put("transferFee", BigDecimal.ZERO);
			return resultMap;
		}
		if (acBillInfoMonthlies.size() > 1) {
			logger.error("记账卡[{}]结算月[{}]账单数量[{}]多于1条记录，异常", accountCInfo.getCardNo(), settleMonth, acBillInfoMonthlies.size());
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "16");//数据库出错
			return resultMap;
		}

		AcBillInfoMonthly acBillInfoMonthly = acBillInfoMonthlies.get(0);
		if (acBillInfoMonthly.getDealNum() != null) {
			resultMap.put("dealNum", acBillInfoMonthly.getDealNum());
		} else {
			resultMap.put("dealNum", 0);
		}
		if (acBillInfoMonthly.getRealDealFee() != null) {
			resultMap.put("realDealFee", acBillInfoMonthly.getRealDealFee().multiply(FEN));
		} else {
			resultMap.put("realDealFee", BigDecimal.ZERO);
		}
		if (acBillInfoMonthly.getOnceNum() != null) {
			resultMap.put("onceNum", acBillInfoMonthly.getOnceNum());
		} else {
			resultMap.put("onceNum", 0);
		}
		if (acBillInfoMonthly.getOnceFee() != null) {
			resultMap.put("onceFee", acBillInfoMonthly.getOnceFee().multiply(FEN));
		} else {
			resultMap.put("onceFee", BigDecimal.ZERO);
		}
		if (acBillInfoMonthly.getOtherNum() != null) {
			resultMap.put("otherNum", acBillInfoMonthly.getOtherNum());
		} else {
			resultMap.put("otherNum", 0);
		}
		if (acBillInfoMonthly.getOtherFee() != null) {
			resultMap.put("otherFee", acBillInfoMonthly.getOtherFee().multiply(FEN));
		} else {
			resultMap.put("otherFee", BigDecimal.ZERO);
		}
		resultMap.put("chargeNum", 0);
		resultMap.put("chargeFee", BigDecimal.ZERO);
		resultMap.put("discountFee", BigDecimal.ZERO);
		resultMap.put("transferFee", BigDecimal.ZERO);
		return resultMap;
	}

	/**
	 * 根据卡号查询账单
	 */
	@Override
	public Map<String, Object> queryBillByCardNo(AccountCInfo accountCInfo, PrepaidC prepaidC, String year,
			String month) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			if (accountCInfo == null && prepaidC == null) {
				logger.warn("记账卡、储值卡信息都为空");
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "1");//数据库出错
				return resultMap;
			} else if (accountCInfo != null && prepaidC != null) {
				logger.warn("记账卡、储值卡信息都不为空");
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
				return resultMap;
			}

			String settleMonth = year + month;
			if (accountCInfo != null) {
				return findAcBillMapByCardNo(accountCInfo, settleMonth);
			} else {
				return findScBillMapByCardNo(prepaidC, settleMonth);
			}
		} catch (ApplicationException e) {
			logger.error("IVR服务：根据卡号查询账单失败", e);
			throw new ApplicationException("IVR服务：根据卡号查询账单失败");
		}
	}

	/**
	 * 根据银行账号查询账单
	 */
	@Override
	public Map<String, Object> queryBillByAccountNo(String accountNo, String year, String month) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		if (StringUtils.isBlank(accountNo)) {
			logger.warn("银行帐号为空");
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "12");//银行帐号错误
			return resultMap;
		}

		String settleMonth = year + month;
		List<AcBillInfoMonthly> acBillInfoMonthlies = acBillInfoMonthlyService.findUserBillByBankAccSettleMonth(accountNo, settleMonth);
		if (acBillInfoMonthlies == null || acBillInfoMonthlies.isEmpty()) {
			logger.warn("银行帐号[{}]结算月[{}]没有账单", accountNo, settleMonth);
			resultMap.put("dealNum", 0);
			resultMap.put("realDealFee", BigDecimal.ZERO);
			resultMap.put("onceNum", 0);
			resultMap.put("onceFee", BigDecimal.ZERO);
			resultMap.put("chargeNum", 0);
			resultMap.put("chargeFee", BigDecimal.ZERO);
			resultMap.put("otherNum", 0);
			resultMap.put("otherFee", BigDecimal.ZERO);
			resultMap.put("discountFee", BigDecimal.ZERO);
			resultMap.put("transferFee", BigDecimal.ZERO);
			return resultMap;
		}
		if (acBillInfoMonthlies.size() > 1) {
			logger.error("银行帐号[{}]结算月[{}]账单数量[{}]多于1条记录，异常", accountNo, settleMonth, acBillInfoMonthlies.size());
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "16");//数据库出错
			return resultMap;
		}

		AcBillInfoMonthly acBillInfoMonthly = acBillInfoMonthlies.get(0);
		if (acBillInfoMonthly.getDealNum() != null) {
			resultMap.put("dealNum", acBillInfoMonthly.getDealNum());
		} else {
			resultMap.put("dealNum", 0);
		}
		if (acBillInfoMonthly.getRealDealFee() != null) {
			resultMap.put("realDealFee", acBillInfoMonthly.getRealDealFee().multiply(FEN));
		} else {
			resultMap.put("realDealFee", BigDecimal.ZERO);
		}
		if (acBillInfoMonthly.getOnceNum() != null) {
			resultMap.put("onceNum", acBillInfoMonthly.getOnceNum());
		} else {
			resultMap.put("onceNum", 0);
		}
		if (acBillInfoMonthly.getOnceFee() != null) {
			resultMap.put("onceFee", acBillInfoMonthly.getOnceFee().multiply(FEN));
		} else {
			resultMap.put("onceFee", BigDecimal.ZERO);
		}
		if (acBillInfoMonthly.getOtherNum() != null) {
			resultMap.put("otherNum", acBillInfoMonthly.getOtherNum());
		} else {
			resultMap.put("otherNum", 0);
		}
		if (acBillInfoMonthly.getOtherFee() != null) {
			resultMap.put("otherFee", acBillInfoMonthly.getOtherFee().multiply(FEN));
		} else {
			resultMap.put("otherFee", BigDecimal.ZERO);
		}
		resultMap.put("chargeNum", 0);
		resultMap.put("chargeFee", BigDecimal.ZERO);
		resultMap.put("discountFee", BigDecimal.ZERO);
		resultMap.put("transferFee", BigDecimal.ZERO);
		return  resultMap;
	}

	/**
	 * 根据卡号修改交易密码
	 */
	@Override
	public Map<String, String> saveChangTradingPwdByCardNo(String cardNo, String cardKind, String oldTradingPwd,
			String newTradingPwd, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			AccountCInfo accountCInfo = null;
			PrepaidC prepaidC = null;
			Customer customer = null;
			String oldCardTradingServicePwd = "";
			accountCInfo = ivrDao.findACByNOandType(cardNo, cardKind);
			if(accountCInfo == null){
				prepaidC = ivrDao.findPCByNOandType(cardNo, cardKind);
				if(prepaidC!=null){
					customer = customerDao.findById(prepaidC.getCustomerID());
					
					//首先判断卡的旧消费密码是否为空
					if(!StringUtil.isNotBlank(prepaidC.getTradingPwd())){
						//卡的旧消费密码为空
						resultMap.put("result", "fail");
						resultMap.put("errorCode", "3");//密码验证失败
						logger.info("卡的旧消费密码为空");
						return resultMap;
					}else{
						oldCardTradingServicePwd = prepaidC.getTradingPwd();
					}
				}
			}else{
				customer = customerDao.findById(accountCInfo.getCustomerId());
				
				//首先判断卡的旧消费密码是否为空
				if(!StringUtil.isNotBlank(accountCInfo.getTradingPwd())){
					//卡的旧消费密码为空
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
					logger.info("卡的旧消费密码为空");
					return resultMap;
				}
				
				oldCardTradingServicePwd = accountCInfo.getTradingPwd();
			}
			
			if(accountCInfo==null&&prepaidC==null){
				//卡号不存在
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "1");//卡号不存在
				logger.info("卡号不存在");
				return resultMap;
			}
			
			if(customer!=null){
				if(StringUtil.md5(oldTradingPwd).equals(oldCardTradingServicePwd)){
					//下面是修改卡的服务密码的代码
					if(accountCInfo!=null&&prepaidC==null){
						AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
						Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCINFOHIS_NO");
						accountCInfoHis.setId(hisId);
						accountCInfoHis.setGenReason("16");//16：消费密码修改
						
						accountCInfoHisDao.save(accountCInfo, accountCInfoHis);
						
						accountCInfo.setHisSeqId(accountCInfoHis.getId());
						accountCInfo.setTradingPwd(StringUtil.md5(newTradingPwd));
						accountCInfoDao.update(accountCInfo);
						
						//保存业务记录表
						AccountCBussiness accountCBussiness = new AccountCBussiness();
						BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
						accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
						accountCBussiness.setUserId(customer.getId());
						accountCBussiness.setCardNo(accountCInfo.getCardNo());
						accountCBussiness.setAccountId(accountCInfo.getAccountId());
						accountCBussiness.setState("13");// 13.	消费密码修改
						accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
						accountCBussiness.setTradeTime(new Date());
						accountCBussiness.setOperId(sysAdmin.getId());
						accountCBussiness.setPlaceId(cusPointPoJo.getCusPoint());
						//新增的字段
						accountCBussiness.setOperName(sysAdmin.getUserName());
						accountCBussiness.setOperNo(sysAdmin.getStaffNo());
						accountCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
						accountCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
						//accountCBussiness.setBusinessId(accountCApply.getHisseqId());
						//receiptDao.saveByBussiness(null, null, null, null, accountCBussiness);
						accountCBussinessDao.save(accountCBussiness);
						
					}else if(accountCInfo==null&&prepaidC!=null){
						//储值卡历史
						PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(), "", prepaidC);
						prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO"));
						prepaidCHis.setGenreason("10");//10：消费密码修改
						prepaidCDao.saveHis(prepaidCHis);
						
						prepaidC.setHisSeqID(prepaidCHis.getId());
						prepaidC.setTradingPwd(StringUtil.md5(newTradingPwd));
						prepaidCDao.update(prepaidC);
						
						PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
						prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));

						prepaidCBussiness.setUserid(prepaidC.getCustomerID());
						prepaidCBussiness.setCardno(prepaidC.getCardNo());
						prepaidCBussiness.setState("5"); // 5.消费密码修改
						prepaidCBussiness.setPlaceid(cusPointPoJo.getCusPoint());
						prepaidCBussiness.setOperid(sysAdmin.getId());
						prepaidCBussiness.setOperName(sysAdmin.getUserName());
						prepaidCBussiness.setOperNo(sysAdmin.getStaffNo());
						prepaidCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
						prepaidCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
						prepaidCBussiness.setTradetime(new Date());
						prepaidCBussinessDao.save(prepaidCBussiness);
						
					}
					
					resultMap.put("result", "success");
					resultMap.put("errorCode", "");
				}else{
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
					logger.info("卡的旧消费密码不一致");
				}
			}else{
				//找不到客户，即验证不了旧密码
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "16");//数据库出错
				logger.info("找不到客户");
			}
				
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：根据卡号修改卡号消费密码失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：根据卡号消费密码失败");
		}
		return resultMap;	
	}

	/**
	 * 判断客户号是否存在
	 */
	@Override
	public Map<String, String> findIsExistUserNo(String userNo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			Customer customer = customerDao.findByUserNo(userNo);
			if(customer!=null){
				resultMap.put("result", "success");
				resultMap.put("errorCode", "");
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "5");//找不到客户
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：判断客户号是否存在失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：判断客户号是否存在失败");
		}
		return resultMap;
	}

	/**
	 * 卡的消费密码鉴权
	 */
	@Override
	public Map<String, String> findVerifyTradingPwdByCardNo(String cardNo, String cardKind, String password) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			List<Map<String, Object>> list = ivrDao.findCardByNOandType(cardNo, cardKind);
			if(list!=null&&!list.isEmpty()){
				Map<String, Object> map = list.get(0);
				//消费密码验证
				if(!StringUtil.isNotBlank(map.get("tradingPwd")+"")){
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
					return resultMap;
				}
				
				if(StringUtil.md5(password).equals(map.get("tradingPwd"))){
					//客户服务密码鉴权成功
					resultMap.put("result", "success");
					resultMap.put("errorCode", "");
				}else{
					//客户服务密码鉴权失败
					resultMap.put("result", "fail");
					resultMap.put("errorCode", "3");//密码验证失败
				}
			}else{
				resultMap.put("result", "fail");
				resultMap.put("errorCode", "1");//卡号不存在
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"IVR服务：消费密码鉴权失败");
			e.printStackTrace();
			throw new ApplicationException("IVR服务：消费密码鉴权失败");
		}
		return resultMap;
	}

	@Override
	public Map<String, String> saveClientLoginInfo(ClientLoginInfo cli) {
		Map<String, String> resultMap = new HashMap<String,String>();
	    try {
			ivrDao.saveClientLoginInfo(cli);
			resultMap.put("result", "success");
			resultMap.put("errorCode", "");
		} catch (Exception e) {
			resultMap.put("result", "fail");
			resultMap.put("errorCode", "16");
			logger.error(e.getMessage()+"IVR saveClientLoginInfo 失败");
			e.printStackTrace();
		}
		return resultMap; 
	}

	@Override
	public Map<String, Object> verifyClientLoginInfo(String tel) {
	 
		Map<String, Object> resultMap = ivrDao.verifyClientLoginInfoByTel(tel);
		if(resultMap != null){
			return resultMap;
		}
		return null;
	}

	@Override
	public Map<String, String> clearClientLoginInfo(String tel,List<ClientLoginInfo> list) {
		Map<String, String> resultMap = ivrDao.clearClientLoginInfoByTel(tel,list);
		return resultMap;
	}
}
