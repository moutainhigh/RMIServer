package com.hgsoft.jointCard.service;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.jointCard.dao.CardHolderDao;
import com.hgsoft.jointCard.dao.CardHolderHisDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.entity.CardHolderHis;
import com.hgsoft.jointCard.serviceInterface.ICardHolderService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CardHolderService implements ICardHolderService {

	private static Logger LOGGER = Logger.getLogger(CardHolderService.class.getName());

	@Resource
	private CardHolderDao cardHolderDao;
	@Resource
	private CardHolderHisDao cardHolderHisDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private SequenceUtil sequenceUtil;

	@Override
	public Pager findByCardNo(String cardNo) {
		return null;
	}

	@Override
	public CardHolder findCardHolderByCardNo(String cardNo) {
		return cardHolderDao.findCardHolderByCardNo(cardNo);
	}

	@Override
	public Pager findByName(String name) {
		return null;
	}

	@Override
	public Pager findByIdCode(String idType, String idCode) {
		return null;
	}

	@Override
	public Pager findCardHolders(Pager pager, CardHolder cardHolder) {
		return cardHolderDao.findCardHolders(pager, cardHolder);
	}

	@Override
	public CardHolder findCardHolderById(Long cardHolderId) {
		return cardHolderDao.findCardHolderById(cardHolderId);
	}

	@Override
	public void update(Customer customer, CardHolder cardHolder) {
		CardHolderHis cardHolderHis = new CardHolderHis();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSlianCardInfoHis_NO");
		cardHolderHis.setId(seq);
		cardHolder.setHisSeqId(seq);
		cardHolderHis.setRemark("持卡人信息修改");
		cardHolderHis.setGenReason("1");
		try {
			cardHolderHisDao.save(cardHolder, cardHolderHis);
			cardHolderDao.update(cardHolder);
		} catch (ApplicationException e) {
			e.printStackTrace();
			LOGGER.error("持卡人信息修改失败：" + e.getMessage());
			throw new ApplicationException("持卡人信息修改失败");
		} // try

		// 调整客服流水
		ServiceWater serviceWater = new ServiceWater();
		seq = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
		serviceWater.setId(seq);
		serviceWater.setCustomerId(customer.getId());
		serviceWater.setUserNo(customer.getUserNo());
		serviceWater.setUserName(customer.getOrgan());
		serviceWater.setCardNo(cardHolder.getCardNo());
		serviceWater.setSerType("801");    // 联营卡持卡人资料修改
		serviceWater.setOperId(cardHolder.getOperId());
		serviceWater.setPlaceId(cardHolder.getPlaceId());
		serviceWater.setOperNo(cardHolder.getOperCode());
		serviceWater.setOperName(cardHolder.getOperName());
		serviceWater.setPlaceNo(cardHolder.getPlaceCode());
		serviceWater.setPlaceName(cardHolder.getPlaceName());
		serviceWater.setOperTime(new Date());
		serviceWater.setRemark("联营卡客服系统：联营卡持卡人资料修改");
		serviceWaterDao.save(serviceWater);
	}

	@Override
	public CardHolder findByTypeAndCodeACMS(String idType, String idCode) {
		return cardHolderDao.findByTypeAndCodeACMS(idType, idCode);
	}

	@Override
	public CardHolder findByUserNo(String userNo) {
		return cardHolderDao.findByUserNo(userNo);
	}

}