package com.hgsoft.agentCard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.agentCard.dao.CardNoSectionDao;
import com.hgsoft.agentCard.entity.JoinCardNoSection;
import com.hgsoft.agentCard.serviceInterface.ICardNoSectionService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.SequenceUtil;

/**
 * @meno card no. section service
 * @author gaosiling
 * @time 2016-08-06 10:29:04
 *
 */
@Service
public class CardNoSectionService implements ICardNoSectionService{
	
	private static Logger logger = Logger.getLogger(CardNoSectionService.class.getName());

	@Resource
	private CardNoSectionDao cardNoSectionDao;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	/**
	 * @meno the function mean is 'save card no. sections'
	 * @author gaosiling
	 * @param cardNoSection
	 */
	/*@Override
	public void save(CardNoSection cardNoSection){
		try {
			Long seq = sequenceUtil.getSequenceLong("seq_csmscardnosection_no");
			cardNoSection.setId(seq);
			cardNoSectionDao.save(cardNoSection);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"保存卡号段信息失败");
			throw new ApplicationException();
		}
	}*/
	@Override
	public void save(JoinCardNoSection joinCardNoSection){
		try {
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSJoinCardNoSection_NO");
			joinCardNoSection.setId(seq);
			cardNoSectionDao.save(joinCardNoSection);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"保存卡号段信息失败");
			throw new ApplicationException();
		}
	}
	/**
	 * @meno the function mean is 'find the cardno in card no. section '
	 * @author gaosiling
	 * @param joinCardNoSection
	 * @param cardNo
	 * @return
	 */
	@Override
	public List findList(JoinCardNoSection joinCardNoSection,String cardNo){
		try {
			return cardNoSectionDao.findList(joinCardNoSection, cardNo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询卡号段信息失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * @meno the function mean is 'check the cardno in card no. section '
	 * @author gaosiling
	 * @param obaNo
	 * @param type
	 * @param cardNo
	 * @return
	 */
/*	@Override
	public boolean checkCardNoInNoSection(String obaNo,String type,String cardNo){
		CardNoSection cardNoSection = new CardNoSection();
		cardNoSection.setObaNo(obaNo);
		cardNoSection.setType(type);
		List list = cardNoSectionDao.findList(cardNoSection, cardNo);
		if(list == null || list.size()==0){
			return false;
		}
		return true;
	}*/
	
	@Override
	public boolean checkCardNoInNoSection(String obaNo,String type,String cardNo){
		JoinCardNoSection joinCardNoSection = new JoinCardNoSection();
		joinCardNoSection.setBankNo(obaNo);
		joinCardNoSection.setCardType(type);
		
		cardNo = cardNo.substring(0,cardNo.length()-1);
		
		List list = cardNoSectionDao.findList(joinCardNoSection, cardNo);
		if(list == null || list.size()==0){
			return false;
		}
		return true;
	}
	@Override
	public boolean checkCardNoInNoSectionIgnoreBankCode(String obaNo, String type, String cardNo) {
		JoinCardNoSection joinCardNoSection = new JoinCardNoSection();
		joinCardNoSection.setBankNo(obaNo);
		joinCardNoSection.setCardType(type);
		
		List list = cardNoSectionDao.findList1(joinCardNoSection, cardNo);
		if(list == null || list.size()==0){
			return false;
		}
		return true;
	}

	@Override
	public Map<String, String> findCardNoSegment(JoinCardNoSection joinCardNoSection) {
		try {
			Map<String,String> cardNoMap = new HashMap<String,String>();
			List list = cardNoSectionDao.findCardNoSegment(joinCardNoSection);
			if(list.size()!= 0){
				cardNoMap= (Map<String, String>) list.get(0);
			}
			return cardNoMap;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询卡号段信息失败");
			throw new ApplicationException();
		}
	}
	
}
