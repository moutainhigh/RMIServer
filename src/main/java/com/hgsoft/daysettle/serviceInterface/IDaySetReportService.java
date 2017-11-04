package com.hgsoft.daysettle.serviceInterface;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.FeeReportRecord;
import com.hgsoft.daysettle.entity.FeeWareReportVo;
import com.hgsoft.daysettle.entity.RemarkInfo;
import com.hgsoft.daysettle.entity.WareReportRecord;
import com.hgsoft.daysettle.entity.WareUntake;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IDaySetReportService {

	/**
	 * 修改时，根据审批ID获取资金上报记录以及库存上报记录
	 * @param feeWareReportVo
	 * @return
	 */
	public FeeWareReportVo findDaySetReportVo(FeeWareReportVo feeWareReportVo,Long placeId);

	/**
	 * 保存新增的备注信息
	 * @param operator
	 * @param remarkInfo
	 * @return
	 */
	public Map<String, Object> saveMemo(SysAdmin operator, RemarkInfo remarkInfo);

	/**
	 * 获取备注信息列表
	 * @param remarkInfo
	 * @return
	 */
	public List<RemarkInfo> findMemoList(RemarkInfo remarkInfo);

	/**
	 * 获取审批的资金说明
	 * @param feeReportRecord
	 * @return
	 */
	public List<RemarkInfo> findFeeMemoList(FeeReportRecord feeReportRecord);
	
	/**
	 * 查询网点未领取产品列表
	 * @param cusPoint
	 * @return
	 */
	public Pager findUntakeWare(Pager pager,CusPointPoJo cusPoint,String date,String state);
	
	public List<WareUntake> findUntakeWareBySettleDay(Pager pager, CusPointPoJo cusPoint, String settleDay);
	
	public WareUntake findWareUntake(WareUntake wareUntake,CusPointPoJo cusPoint) throws ParseException;
	
	public void saveWareUntake(List<WareUntake> untakeList, SysAdmin sysAdmin, CusPointPoJo operPlace,
			String settleDay,String deleteWare) throws Exception;
	
	public void deleteUntakeWare(WareUntake wareUntake,CusPointPoJo cusPoint)  throws Exception;
	
	public Map<String, Object> checkAllReport(DaySetApprove daySetApprove,CusPointPoJo cusPoint);

	Boolean isDirector(Long operId);

	String findUntakeNextDay(CusPointPoJo cusPoint) throws ParseException;

	CustomPoint findCustomPointById(Long placeId);
	
	public DaySetApprove findDaySetApprove(Long netId, String settleDay);

	List<RemarkInfo> findWareMemoList(WareReportRecord wareReportRecord);
	
	public List<RemarkInfo> findUntakeWareMemoList(WareUntake wareUntake);
	
}
