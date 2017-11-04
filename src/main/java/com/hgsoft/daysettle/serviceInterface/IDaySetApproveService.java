package com.hgsoft.daysettle.serviceInterface;

import java.text.ParseException;
import java.util.Map;

import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.FeeWareReportVo;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IDaySetApproveService {

	public DaySetApprove saveApprove(CusPointPoJo cuspoint, DaySetApprove daySetApprove, CustomPoint stockMap);

	public  Boolean isReportApprove(DaySetApprove daySet);

	/**
	 * 获取审批列表数据
	 * @param pager
	 * @param operPlaceId
	 * @param date
	 * @param state
	 * @return
	 */
	public Pager findApproveList(Pager pager, Long operPlaceId, String date, String state);

	/**
	 * 点击日报审批时加载资金上报，库存上报等数据
	 * @param daySetApprove
	 * @return
	 * @throws ParseException 
	 * @throws Exception 
	 */
	public FeeWareReportVo findApproveVoEdit(DaySetApprove daySetApprove,CusPointPoJo cuspoint) throws ParseException, Exception;

	public Map<String, Object> saveDaySetApprove(CusPointPoJo cusPointPojo,SysAdmin operator, DaySetApprove daySetApprove) throws ParseException, Exception;

	public FeeWareReportVo findApproveVoView(DaySetApprove daySetApprove) throws ParseException, Exception;

	/**
	 * 判断网点当天是否日结
	 * @param customPointId 网点编码
	 * @param settleDay yyyy-MM-dd
	 * @return
	 */
	public Boolean isCustomPointApprove(Long customPointId, String settleDay);
	
	public DaySetApprove findApproveById(Long id);
}
