package com.hgsoft.httpInterface.serviceInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface IReqRefundInterfaceService {
	//public boolean requestRefund(MainAccountInfo mainAccountInfo,ReqRefundRecord reqRefundRecord);
	public Map<String , Object> requestRefundInfo(Long refundId);
	public String saveRequestRefundApp(String param);
	public String updateAmt(Long refundId, BigDecimal personalCorrectAmt,BigDecimal finalRefundAmt);
	public Pager requestRefundList(Pager pager, String refundType, String refundApplyStartTime,
			String refundApplyEndTime, String auditStatus,Long refundId,String expireFlag,String queryFlag);
}
