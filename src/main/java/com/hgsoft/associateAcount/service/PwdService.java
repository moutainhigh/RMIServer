package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.associateAcount.serviceInterface.IPwdService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PwdService implements IPwdService {
	
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());

	@Override
	public boolean updatePwd(AccountCInfo accountCInfo) {
		try {
			//记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			
			accountCBussiness.setUserId(accountCInfo.getCustomerId());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setState("11"); //11消费密码修改 12重设
			//这个值需要修改
			accountCBussiness.setOperId(1L);
			accountCBussiness.setPlaceId(1L);
			
			accountCBussinessDao.save(accountCBussiness);

			accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
			accountCDao.update(accountCInfo);
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
		}
	}

	@Override
	public boolean resetPwd(AccountCInfo accountCInfo) {
		try {
			//记帐卡业务记录
			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			
			accountCBussiness.setUserId(accountCInfo.getCustomerId());
			accountCBussiness.setCardNo(accountCInfo.getCardNo());
			accountCBussiness.setState("12");
			//这个值需要修改
			accountCBussiness.setOperId(1L);
			accountCBussiness.setPlaceId(1L);
			
			accountCBussinessDao.save(accountCBussiness);
			
			accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
			accountCDao.update(accountCInfo);
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
		}
	}

	@Override
	public boolean updatePwdForACMS(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness) {
		try {
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			accountCBussinessDao.save(accountCBussiness);

			AccountCInfo oldAccountCInfo = accountCDao.findByCardNo(accountCInfo.getCardNo());
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCINFOHIS_NO"));
			accountCInfoHisDao.save(oldAccountCInfo, accountCInfoHis);
			
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
			accountCDao.update(accountCInfo);
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
		}
	}

	@Override
	public boolean resetPwdForACMS(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness) {
		try {
			accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
			accountCBussinessDao.save(accountCBussiness);
			
			AccountCInfo oldAccountCInfo = accountCDao.findByCardNo(accountCInfo.getCardNo());
			AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCOUNTCINFOHIS_NO"));
			accountCInfoHisDao.save(oldAccountCInfo, accountCInfoHis);
			
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
			accountCDao.update(accountCInfo);
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("联营卡消费密码修改失败，联营卡id:" + accountCInfo.getId());
		}
	}

}
