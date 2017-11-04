package com.hgsoft.daysettle.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.DaySetCorrectRecord;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IDaySetCorrectService {
	
	public Pager findCorrectList(Pager pager, Long operPlaceId, String date, String state);
	/**
	 * 获取短款修正列表
	 * @param pager
	 * @param operPlaceId
	 * @param date
	 * @param state
	 * @return
	 */
	public Pager findDaySetCorrectList(Pager pager, Long operPlaceId, String date, String state);

	/**
	 * 获取短款修正记录
	 * @param approveId
	 * @return
	 */
	public List<DaySetCorrectRecord> findDaySetCorrectRecordList(Long approveId,Long placeId);

	/**
	 * 保存短款修正表
	 * @param operPlace
	 * @param operator
	 * @param daySetCorrectRecord
	 * @param daySetApprove
	 * @return
	 */
	public Map<String, Object> saveDaySetCorrect(CusPointPoJo operPlace, SysAdmin operator,
			DaySetCorrectRecord daySetCorrectRecord, DaySetApprove daySetApprove);

}
