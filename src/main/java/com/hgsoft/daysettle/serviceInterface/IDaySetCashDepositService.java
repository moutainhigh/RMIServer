package com.hgsoft.daysettle.serviceInterface;

import java.util.Map;

import com.hgsoft.daysettle.entity.CashDepositDaySet;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IDaySetCashDepositService {

	/**
	 * 根据审批表id获取缴存上报表记录
	 * @param id
	 * @return
	 */
	public CashDepositDaySet findCashDepositDaySet(Long id);

	/**
	 * 保存缴存上报
	 * @param cuspoint
	 * @param sysAdmin
	 * @param cashDepositDaySet
	 * @param daySetApprove
	 * @return
	 */
	public Map<String, Object> saveCashPeposit(CusPointPoJo cuspoint, SysAdmin sysAdmin, CashDepositDaySet cashDepositDaySet,
			DaySetApprove daySetApprove);

	/**
	 * 获取资金上报列表
	 * @param pager
	 * @param operPlaceId
	 * @param date
	 * @param state
	 * @return
	 */
	public Pager findCashDepositList(Pager pager, Long operPlaceId, String date, String state);
	
	/**
	 * 获取日缴存金额的最新日期
	 * @param operPlaceId
	 * @return
	 */
	public CashDepositDaySet findCashDepositSettleDay(Long operPlaceId);
}
