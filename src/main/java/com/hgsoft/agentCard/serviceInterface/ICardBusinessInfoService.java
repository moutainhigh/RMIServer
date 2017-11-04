package com.hgsoft.agentCard.serviceInterface;

import com.hgsoft.agentCard.entity.CardBusinessInfo;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

public interface ICardBusinessInfoService {
	public void saveCardBusinessInfos(List<CardBusinessInfo> cardBusinessInfos);
	public Pager findCardBusinessInfosByNum(Pager pager,Long dataNum);
	public Pager findCardBusinessInfosByFileName(Pager pager,String fileName);
	public List<CardBusinessInfo> findByFileName(String fileName);
	public String saveBusiness(List<CardBusinessInfo> cardBusinessInfos,ServiceFlowRecord serviceFlowRecord,String bankCode);
	public Map<String, Object> saveBankBusiness(CardBusinessInfo cardBusinessInfo,ServiceFlowRecord serviceFlowRecord,String bankCode);
	public List<CardBusinessInfo> findByFileNameAndTransact(String fileName);
	public List<CardBusinessInfo> findByFileNameAndTransactSuccess(String fileName);
	public List<CardBusinessInfo> findIssueBusinessRecordBycardNo(String cardNo);
	public CardBusinessInfo findById(Long cardBusinessInfoId);
	public List<CardBusinessInfo> findByErrorAndName(String fileName);
//	public String saveExportFile(HttpServletResponse response,List<CardBusinessInfo> cardBusinessInfos, String exportType);
	public Map<String,Object> dataCheck(String bankCode, CardBusinessInfo cardBusinessInfo);

	public Long findSequence(String sequenceName);
}
