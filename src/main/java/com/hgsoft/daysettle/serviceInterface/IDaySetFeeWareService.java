package com.hgsoft.daysettle.serviceInterface;

import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.hgsoft.daysettle.entity.FeeReportRecord;
import com.hgsoft.daysettle.entity.FeeWareReportVo;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IDaySetFeeWareService {

	/**
	 * 根据日期，营业员id，状态，查询本营业网点的日结上报信息
	 * @param userId
	 * @param settleDay
	 * @param state
	 * @return
	 */
	public Pager findFeeWareList(Pager pager,Long placeId,String settleDay,String state,SysAdmin sysAdmin);

	public Map<String, Object> saveFeeWareReport(CusPointPoJo cuspoint, SysAdmin sysAdmin, FeeWareReportVo feeWareReportVo,
			String[] deleteFeeList, String[] deleteWareList) throws DuplicateKeyException, Exception;
	
	public FeeWareReportVo findFeeWareByApprove(Long daySetId,Long placeId);

	public FeeWareReportVo findFeeWareDaySettleDay(FeeWareReportVo feeWareReportVo, Long operPlaceId);
}
