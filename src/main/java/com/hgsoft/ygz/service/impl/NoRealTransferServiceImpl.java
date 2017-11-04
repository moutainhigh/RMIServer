package com.hgsoft.ygz.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.ygz.common.BusinessTypeEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.entity.NoRealBusinessReq;
import com.hgsoft.ygz.service.NoRealBusinessReqService;
import com.hgsoft.ygz.service.NoRealTransferService;
import com.hgsoft.ygz.util.DateUtil;

@Service
public class NoRealTransferServiceImpl implements NoRealTransferService{

	private static Logger logger = Logger.getLogger(NoRealTransferServiceImpl.class.getName());
	
	@Resource
	private NoRealBusinessReqService noRealBusinessReqService;
	
	@Override
	public void blackListTransfer(String cardNO,Date genTime,Integer CardBlackType,Integer operaTpye) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("creationTime", genTime.getTime());
		map.put("type", CardBlackType);
		map.put("cardId", cardNO);
		map.put("status", operaTpye);
		JSONObject jsonObject=new JSONObject();
		jsonObject.putAll(map);
		logger.debug("blackListTransfer:"+jsonObject.toString());
		NoRealBusinessReq noRealBusinessReq=new NoRealBusinessReq();
		noRealBusinessReq.setBusinessContent(jsonObject.toString());
		noRealBusinessReq.setBusinessType(BusinessTypeEmeu.CARDBLACKLISTUPLOAD.getCode());
		noRealBusinessReq.setOperation(operaTpye);
		noRealBusinessReq.setCreateTime(DateUtil.getNowTime());
		noRealBusinessReqService.save(noRealBusinessReq);
	}

	@Override
	public void balanceTransfer(String userNo,String newCardNo,BigDecimal balance,
			Integer operaTpye) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userNo);
		map.put("cardId", newCardNo);
		map.put("fee", balance);
		JSONObject jsonObject=new JSONObject();
		jsonObject.putAll(map);
		logger.debug("balanceTransfer:"+jsonObject.toString());
		NoRealBusinessReq noRealBusinessReq=new NoRealBusinessReq();
		noRealBusinessReq.setBusinessContent(jsonObject.toString());
		noRealBusinessReq.setBusinessType(BusinessTypeEmeu.BALANCEUPLOAD.getCode());
		noRealBusinessReq.setOperation(operaTpye);
		noRealBusinessReq.setCreateTime(DateUtil.getNowTime());
		noRealBusinessReqService.save(noRealBusinessReq);
	}

	@Override
	public void obuBlackListTransfer(TagInfo tagInfo, Date now,Integer operatorType, Integer obuState) {
		JSONObject rtv = new JSONObject();
		rtv.put("issuerId", "");
		rtv.put("creationTime", now.getTime());
		rtv.put("OBUId", tagInfo.getTagNo());
		rtv.put("type", operatorType);
		rtv.put("status", obuState);
		logger.debug("obuBlackListTransfer:"+rtv.toString());
		NoRealBusinessReq noRealBusinessReq = new NoRealBusinessReq();
		noRealBusinessReq.setBusinessContent(rtv.toString());
		noRealBusinessReq.setBusinessType(BusinessTypeEmeu.OBUBLACKLISTUPLOAD.getCode());
		noRealBusinessReq.setOperation(operatorType);
		noRealBusinessReq.setCreateTime(DateUtil.getNowTime());
		noRealBusinessReqService.save(noRealBusinessReq);
	}

	@Override
	public void refundTransfer(String userNo, String customPointCode,
			Date refundTime, String cardNo, BigDecimal Fee) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userNo);
		map.put("customPointCode", customPointCode);
		map.put("refundTime", refundTime.getTime());
		map.put("cardId", cardNo);
		map.put("fee", Fee);
		JSONObject jsonObject=new JSONObject();
		jsonObject.putAll(map);
		logger.debug("balanceTransfer:"+jsonObject.toString());
		NoRealBusinessReq noRealBusinessReq=new NoRealBusinessReq();
		noRealBusinessReq.setBusinessContent(jsonObject.toString());
		noRealBusinessReq.setBusinessType(BusinessTypeEmeu.REIMBUSREUPLOAD.getCode());
		noRealBusinessReq.setOperation(OperationTypeEmeu.TRANSFER.getCode());
		noRealBusinessReq.setCreateTime(DateUtil.getNowTime());
		noRealBusinessReqService.save(noRealBusinessReq);
	}

}
