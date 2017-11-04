package com.hgsoft.agentCard.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.agentCard.entity.CardNoSection;
import com.hgsoft.agentCard.entity.JoinCardNoSection;

public interface ICardNoSectionService {

	//public List findList(CardNoSection cardNoSection, String cardNo);

	//public void save(CardNoSection cardNoSection);

	public boolean checkCardNoInNoSection(String obaNo, String type, String cardNo);

	public void save(JoinCardNoSection joinCardNoSection);

	public List findList(JoinCardNoSection joinCardNoSection, String cardNo);
	public boolean checkCardNoInNoSectionIgnoreBankCode(String obaNo, String type, String cardNo);
	public Map<String,String> findCardNoSegment(JoinCardNoSection joinCardNoSection);
}
