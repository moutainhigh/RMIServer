package com.hgsoft.prepaidC.service;


import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.hgsoft.common.Enum.PrepaidCardBussinessTradeStateEnum;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.other.dao.ReceiptPrintDetailDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class PrepaidCBussinessService implements IPrepaidCBussinessService{

	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ReceiptPrintDetailDao receiptPrintDetailDao;
	@Resource
	public void setPrepaidCBussinessDao(PrepaidCBussinessDao prepaidCBussinessDao) {
		this.prepaidCBussinessDao = prepaidCBussinessDao;
	}

	@Override
	public PrepaidCBussiness findByCardNoAndState(String cardNo,String state){
		return prepaidCBussinessDao.findByCardNoAndState(cardNo, state);
	}

	@Override
	public Pager findBussinessListByPager(Pager pager, String cardNo, String startTime, String endTime) {
		return prepaidCBussinessDao.findBussinessListByPager(pager, cardNo, startTime, endTime);
	}
	
	@Override
	public PrepaidCBussiness findById(Long id) {
		return prepaidCBussinessDao.findById(id);
	}

	@Override
	public BigDecimal findPrepaidCBussinessId() {
		BigDecimal PrePaidC_bussiness_NO = sequenceUtil.getSequence("SEQ_CSMS_PrePaidC_bussiness_NO");
		return PrePaidC_bussiness_NO;
	}
	
	@Override
	public PrepaidCBussiness findBussinessNotReturn(String cardNo,String state){
		return prepaidCBussinessDao.findBussinessNotReturn(cardNo, state);
	}

	@Override
	public int updateTradeStateFail(PrepaidCBussiness prepaidCBussiness) {
		prepaidCBussiness.setTradestate(PrepaidCardBussinessTradeStateEnum.fail.getValue());
		return prepaidCBussinessDao.updateTradeState(prepaidCBussiness, PrepaidCardBussinessTradeStateEnum.save.getValue());
	}

	@Override
	public int updateTradeStateSuccess(PrepaidCBussiness prepaidCBussiness) {
		prepaidCBussiness.setTradestate(PrepaidCardBussinessTradeStateEnum.success.getValue());
		return prepaidCBussinessDao.updateTradeState(prepaidCBussiness, PrepaidCardBussinessTradeStateEnum.save.getValue());
	}

	@Override
	public PrepaidCBussiness findNewRechargeBusinessByCardNo(String cardNo) {
		return prepaidCBussinessDao.findNewRechargeBusinessByCardNo(cardNo);
	}

	@Override
	public PrepaidCBussiness findNewRechargeBusinessByCardNoMaxTradetime(String cardNo, Date maxTradetime) {
		return prepaidCBussinessDao.findNewRechargeBusinessByCardNoMaxTradetime(cardNo, maxTradetime);
	}

	@Override
	public PrepaidCBussiness findByOldCardNoAndState(String oldCardNo, String state) {
		return prepaidCBussinessDao.findByOldCardNoAndState(oldCardNo, state);
	}

	@Override
	public int findSuccessRechargeNum(String cardNo) {
		return prepaidCBussinessDao.findRechargeNum(cardNo, PrepaidCardBussinessTradeStateEnum.success.getValue());
	}

	@Override
	public PrepaidCBussiness findRechargeBusinessByCardNoTradetime(String cardNo, Date tradetime) {
		return prepaidCBussinessDao.findRechargeBusinessByCardNoTradetime(cardNo, tradetime);
	}
}
