package com.hgsoft.associateAcount.service;

import com.hgsoft.associateAcount.dao.LianCardInfoDao;
import com.hgsoft.associateAcount.dao.LianCardInfoHisDao;
import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.LianCardInfoHis;
import com.hgsoft.associateAcount.serviceInterface.ILianCardInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LianCardInfoService implements ILianCardInfoService  {
	private static Logger logger = Logger.getLogger(LianCardInfoService.class.getName());

	@Resource
	private LianCardInfoDao lianCardInfoDao;
	
	@Resource
	private LianCardInfoHisDao lianCardInfoHisDao;
	
	
	@Resource
	private SequenceUtil sequenceUtil;
	

	@Override
	public void save(LianCardInfo lianCardInfo) {
		
		try {
			Long seq = sequenceUtil.getSequenceLong("seq_csmsliancardinfo_no");
			lianCardInfo.setId(seq);
			lianCardInfoDao.save(lianCardInfo);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"持卡人信息，卡号：" + lianCardInfo.getCardNo() + "登记失败！");
			throw new ApplicationException("持卡人信息，卡号：" + lianCardInfo.getCardNo() + "登记失败！");
		}
		
	}

	@Override
	public LianCardInfo find(LianCardInfo lianCardInfo) {
		try {
			return lianCardInfoDao.find(lianCardInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"持卡人信息，卡号：" + lianCardInfo.getCardNo() + "查询失败");
			throw new ApplicationException("持卡人信息，卡号：" + lianCardInfo.getCardNo() + "查询失败");
		}

	}

	@Override
	public void update(LianCardInfo lianCardInfo) {
		
		LianCardInfoHis lianCardInfoHis = new LianCardInfoHis();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSlianCardInfoHis_NO");
		lianCardInfoHis.setId(seq);
		lianCardInfo.setHisseqId(seq);
		lianCardInfoHis.setMemo("持卡人资料修改");
		lianCardInfoHis.setCreateReason("2");
		
		
		try {
			lianCardInfoHisDao.save(lianCardInfo, lianCardInfoHis);
			lianCardInfoDao.update(lianCardInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"持卡人信息，卡号：" + lianCardInfo.getCardNo() + "修改失败");
			throw new ApplicationException("持卡人信息，卡号：" + lianCardInfo.getCardNo() + "修改失败");
		}
	}
	
	
}
