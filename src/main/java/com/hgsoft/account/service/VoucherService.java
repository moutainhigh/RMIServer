package com.hgsoft.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.VoucherDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.entity.Voucher;
import com.hgsoft.account.serviceInterface.IRechargeInfoService;
import com.hgsoft.account.serviceInterface.IVoucherService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.SequenceUtil;
@Service
public class VoucherService implements IVoucherService{
	
	private static Logger logger = Logger.getLogger(VoucherService.class.getName());
	@Resource
	private SequenceUtil sequenceUtil;
	
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private AccountBussinessDao accountBussinessDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private VoucherDao voucherDao;
	
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	@Resource
	private IRechargeInfoService rechargeInfoService;

	@Override
	public Map<String, Object> saveForRechargeInfo(MainAccountInfo mainAccountInfo, RechargeInfo rechargeInfo,
			Voucher voucher) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		try {
			
			//校验此时该缴款单是否被别用了
			Voucher tmpVoucher = new Voucher();
			tmpVoucher.setVoucherNo(voucher.getVoucherNo());
			Voucher checkVoucher = voucherDao.findVoucher(tmpVoucher);
			if(checkVoucher != null){
				if(checkVoucher.getBalance().compareTo(rechargeInfo.getTakeBalance()) != 0){
					resultMap.put("result", "false");
					resultMap.put("message", "异常情况，缴款单凭证余额不等于缴款金额");
					return resultMap;
				}
			}else{
				resultMap.put("result", "false");
				resultMap.put("message", "异常情况，缴款单凭证不存在");
				return resultMap;
			}
			
			AccountBussiness accountBussiness = new AccountBussiness();
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType("1");//1缴款
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setNewRechargeInfo(rechargeInfo);
			unifiedParam.setOperId(rechargeInfo.getOperId());
			unifiedParam.setPlaceId(rechargeInfo.getPlaceId());
			unifiedParam.setOperName(rechargeInfo.getOperName());
			unifiedParam.setOperNo(rechargeInfo.getOperNo());
			unifiedParam.setPlaceName(rechargeInfo.getPlaceName());
			unifiedParam.setPlaceNo(rechargeInfo.getPlaceNo());
			
			if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
				rechargeInfoService.save(rechargeInfo);
				
				
				accountBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSaccountbussiness_NO"));
				accountBussiness.setRealPrice(rechargeInfo.getTakeBalance());
				accountBussiness.setState("1");//缴款
				accountBussiness.setTradeTime(new Date());
				accountBussiness.setUserId(rechargeInfo.getMainId());
				accountBussiness.setOperId(rechargeInfo.getOperId());
				accountBussiness.setPlaceId(rechargeInfo.getPlaceId());
				accountBussiness.setOperName(rechargeInfo.getOperName());
				accountBussiness.setOperNo(rechargeInfo.getOperNo());
				accountBussiness.setPlaceName(rechargeInfo.getPlaceName());
				accountBussiness.setPlaceNo(rechargeInfo.getPlaceNo());
				accountBussiness.setBussinessId(rechargeInfo.getId());
				mainAccountInfo=mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());
				accountBussiness.setHisSeqId(mainAccountInfo.getHisSeqId());
				accountBussinessDao.save(accountBussiness);
				
				//缴款单凭证记录update余额为0
				voucher.setBalance(new BigDecimal("0"));
				voucher.setGetFee(null);
				voucherDao.updateNotNull(voucher);
				
				
				Customer customer = customerDao.findById(accountBussiness.getUserId());
				//客户服务流水
				ServiceWater serviceWater = new ServiceWater();
				serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
				if(customer!=null)serviceWater.setCustomerId(customer.getId());
				if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
				if(customer!=null)serviceWater.setUserName(customer.getOrgan());
				serviceWater.setAulAmt(accountBussiness.getRealPrice());
				serviceWater.setSerType("601");//账户缴款（缴款单缴款）
				serviceWater.setRemark("自营客服系统：账户缴款(缴款单缴款)");
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
				
				
				resultMap.put("result", "true");
			} else {
				resultMap.put("result", "false");
			}

		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"转账账户交款失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return resultMap;
	}

	@Override
	public Voucher findVoucher(Voucher voucher) {
		return voucherDao.findVoucher(voucher);
	}
	
}
