package com.hgsoft.airrecharge.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.airrecharge.dao.ReqInterfaceFlowDao;
import com.hgsoft.airrecharge.entity.ReqInterfaceFlow;
import com.hgsoft.airrecharge.serviceInterface.IReqInterfaceFlowService;
import com.hgsoft.common.Enum.MQCodeEnum;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.utils.SequenceUtil;

/**
 * 接口请求流水
 * @author gaosiling
 * 2016年8月21日 16:39:20
 */
@Service
public class ReqInterfaceFlowService implements IReqInterfaceFlowService {

	@Resource
	private ReqInterfaceFlowDao reqInterfaceFlowDao;
	
	@Resource
    SequenceUtil sequenceUtil;
	
	/**
	 * 接口请求流水，此处不做异常处理
	 * 通过交易码取枚举类型的接口名称和接口类型
	 * @author gaosiling
	 * @param reqInterfaceFlow
	 */
	@Override
	public void save(String tradeCode,CusPointPoJo cusPointPoJo){
		ReqInterfaceFlow reqInterfaceFlow = new ReqInterfaceFlow();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSREQINTERFACEFLOW_NO");
		reqInterfaceFlow.setId(seq);
		reqInterfaceFlow.setReqSource("02");
		reqInterfaceFlow.setSerType(MQCodeEnum.getType(tradeCode));
		reqInterfaceFlow.setInterfaceName(MQCodeEnum.getInterfaceName(tradeCode));
		reqInterfaceFlow.setTradeCode(tradeCode);
		reqInterfaceFlow.setReqDate(new Date());
		reqInterfaceFlow.setPlaceName(cusPointPoJo.getCusPointName());
		reqInterfaceFlow.setPlaceNo(cusPointPoJo.getCusPointCode());
		reqInterfaceFlowDao.save(reqInterfaceFlow);
	}
	
}
