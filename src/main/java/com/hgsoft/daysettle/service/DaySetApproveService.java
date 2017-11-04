package com.hgsoft.daysettle.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetReportDao;
import com.hgsoft.daysettle.entity.CashDepositDaySet;
import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.DaySetCorrectRecord;
import com.hgsoft.daysettle.entity.FeeReportRecordVo;
import com.hgsoft.daysettle.entity.FeeWareReportVo;
import com.hgsoft.daysettle.entity.LongFeeCorrect;
import com.hgsoft.daysettle.entity.SysFee;
import com.hgsoft.daysettle.entity.SysWare;
import com.hgsoft.daysettle.entity.WareReportRecord;
import com.hgsoft.daysettle.entity.WareUntake;
import com.hgsoft.daysettle.entity.WareUntakeVo;
import com.hgsoft.daysettle.serviceInterface.IDaySetApproveService;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Service
public class DaySetApproveService implements IDaySetApproveService {

	@Resource
	private DaySetReportDao daySetReportDao;
	@Resource
	SequenceUtil sequenceUtil;

	@Override
	public DaySetApprove saveApprove(CusPointPoJo cuspoint, DaySetApprove daySetApprove, CustomPoint stockMap) {
		DaySetApprove daySet = daySetReportDao.findDaySetApproveByDate(daySetApprove.getSettleDay(), stockMap.getId());
		if (daySet == null) {
			// 如果日审批报表的id为空
			// 设置用户的库存地
			daySetApprove.setSalesDep(stockMap.getId());
			daySetApprove.setSalesDepName(stockMap.getName());
			daySetApprove.setSettleDay(daySetApprove.getSettleDay());
			// 审批状态默认为0，未审批
			daySetApprove.setState("0");
			//
			daySetApprove.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDAYSETAPPROVAL_NO"));
			daySetReportDao.saveDaySetApprove(daySetApprove);
			return daySetApprove;
		}
		return daySet;
	}

	@Override
	public Boolean isReportApprove(DaySetApprove daySet) {
		if (daySet == null)
			return false;
		if (StringUtil.isEmpty(daySet.getState()))
			return false;
		if (StringUtil.isEquals(daySet.getState(), "0"))
			return false;
		return true;
	}

	@Override
	public Pager findApproveList(Pager pager, Long operPlaceId, String date, String state) {
		CustomPoint parentPoint = daySetReportDao.findCustomPoint(operPlaceId);
		if (parentPoint != null) {
			return daySetReportDao.findApproveList(pager, parentPoint.getId(), state, date);
		} else {
			return null;
		}
	}

	@Override
	public FeeWareReportVo findApproveVoEdit(DaySetApprove daySetApprove,CusPointPoJo cuspoint) throws Exception {
		CustomPoint customPoint = daySetReportDao.findCurrentPointById(cuspoint.getCusPoint());
		FeeWareReportVo feeWareReportVo = new FeeWareReportVo();
		// 根据审批id获取审批内容
		daySetApprove = daySetReportDao.findDaySetApproveById(daySetApprove.getId());
		DaySetApprove lastApprove = daySetReportDao.findLastApprove(daySetApprove.getSalesDep(),
				daySetApprove.getSettleDay());
		if (lastApprove != null && StringUtil.isEquals(lastApprove.getState(), "0")) {
			throw new Exception("该营业部存在历史未审批的日结数据，无法审批当天日结数据！");
		}
		feeWareReportVo.setDaySetApprove(daySetApprove);

		String startTime = getStartTime(daySetApprove.getSalesDep(), daySetApprove.getSettleDay());
		String endTime = getEndTime(DateUtil.toDate(daySetApprove.getSettleDay()));
		// 设定系统计算时间，防止审批过程中又产生资金变动
		daySetApprove.setSysEndTime(DateUtil.toDate(endTime, "yyyyMMddHHmmss"));
		daySetApprove.setSysStartTime(DateUtil.toDate(startTime, "yyyyMMddHHmmss"));
		// 获取资金收入情况
		List<FeeReportRecordVo> feeVoList = findApproveFeeList(daySetApprove, startTime, endTime);
		// 获取短款修正情况，按修正时间，如果修正时间必须比日结计算开始时间要大
		List<DaySetCorrectRecord> correctList = daySetReportDao.findDaySetCorrectRecord(startTime,endTime,daySetApprove.getSalesDep());
		// 获取现金缴存情况
		List<CashDepositDaySet> toDayCashDepositDay = findCashDepositDayApprove(feeVoList, daySetApprove, correctList);
		// 获取产品库存情况信息
		List<WareReportRecord> wareList = daySetReportDao.findTotalWare(daySetApprove.getId(),startTime, endTime,
				customPoint.getParent(),daySetApprove.getSettleDay());
		//List<WareReportRecord> wareList = findApproveWare(daySetApprove, startTime, endTime);
		// 获取未领取产品信息
		
		List<WareUntakeVo> untakeList = findUntakeWareVo(customPoint.getStockPlace(),daySetApprove.getSettleDay());
		
		
		//feeWareReportVo.setProductNum(getSysProduct(daySetApprove.getId()));

		feeWareReportVo.setFeeVoList(feeVoList);

		feeWareReportVo.setCashDepositList(toDayCashDepositDay);

		feeWareReportVo.setWareList(wareList);

		feeWareReportVo.setCorrectList(correctList);

		feeWareReportVo.setUntakeList(untakeList);
		
		feeWareReportVo.setTotalWare(calcTotalWare(wareList,untakeList));

		return feeWareReportVo;
	}

	/**
	 * 计算产品合计信息
	 * @param wareList
	 * @param untakeList
	 * @return
	 */
	private List<WareReportRecord> calcTotalWare(List<WareReportRecord> wareList, List<WareUntakeVo> untakeList) {
		String productCode = "";
		String productName = "";
		String productSource = "";
		String productState = "";
		Integer wareCount = 0;
		Integer sysCount = 0;
		//先计算出传进来的list进行分组合并
		List<WareReportRecord> tmpWareList = null;
		if(wareList!=null) {
			tmpWareList = new ArrayList<WareReportRecord>();
			Boolean isExist = false;
			for(WareReportRecord wareReportRecord:wareList){
				isExist = false;
				
				productCode = wareReportRecord.getProductCode();
				productName = wareReportRecord.getProductName();
				productSource = wareReportRecord.getProductSource();
				productState = wareReportRecord.getProductState();
				wareCount = wareReportRecord.getWareCount()==null?0:wareReportRecord.getWareCount();
				sysCount = wareReportRecord.getSysCount()==null?0:wareReportRecord.getSysCount();
				//查找临时表里面是否已经统计改产品
				for(WareReportRecord wRecord:tmpWareList) {
					//已经统计过该产品，则在原来的数量基础上加上本次数量
					if(StringUtil.isEquals(productCode, wRecord.getProductCode())
							&&StringUtil.isEquals(productName, wRecord.getProductName())
							&&StringUtil.isEquals(productSource, wRecord.getProductSource())
							&&StringUtil.isEquals(productState, wRecord.getProductState())) {
						isExist = true;
						wRecord.setSysCount(wRecord.getSysCount()==null?0:wRecord.getSysCount()+sysCount);
						wRecord.setWareCount(wRecord.getWareCount()==null?0:wRecord.getWareCount()+wareCount);
					}
				}
				if(!isExist) {
					//new一个对象防止对原列表的数据有修改
					WareReportRecord nReportRecord = new WareReportRecord();
					nReportRecord.setId(wareReportRecord.getId());
					nReportRecord.setDaySetID(wareReportRecord.getDaySetID());
					nReportRecord.setSettleDay(wareReportRecord.getSettleDay());
					nReportRecord.setProductCode(productCode);
					nReportRecord.setProductName(productName);
					nReportRecord.setProductSource(productSource);
					nReportRecord.setProductState(productState);
					nReportRecord.setSysCount(sysCount);
					nReportRecord.setWareCount(wareCount);
					nReportRecord.setOperid(wareReportRecord.getOperid());
					nReportRecord.setOperCode(wareReportRecord.getOperCode());
					nReportRecord.setOperName(wareReportRecord.getOperName());
					nReportRecord.setPlaceid(wareReportRecord.getPlaceid());
					nReportRecord.setPlaceCode(wareReportRecord.getPlaceCode());
					nReportRecord.setPlaceName(wareReportRecord.getPlaceName());
					tmpWareList.add(nReportRecord);
				}
			}
		}
		
		if(tmpWareList==null) {
			tmpWareList = new ArrayList<WareReportRecord>();
		}
		if(untakeList==null) {
			return tmpWareList;
		}else {
			for(WareUntakeVo wareUntakeVo:untakeList){
				Boolean isExist = false;
				productCode = wareUntakeVo.getProductCode();
				productName = wareUntakeVo.getProductName();
				productSource = wareUntakeVo.getProductSource();
				productState = wareUntakeVo.getProductState();
				wareCount = wareUntakeVo.getWareCount()==null?0:wareUntakeVo.getWareCount();
				sysCount = wareUntakeVo.getSysCount()==null?0:wareUntakeVo.getSysCount();
				for(WareReportRecord wRecord:tmpWareList) {
					//已经统计过该产品，则在原来的数量基础上加上本次数量
					if(StringUtil.isEquals(productCode, wRecord.getProductCode())
							&&StringUtil.isEquals(productName, wRecord.getProductName())
							&&StringUtil.isEquals(productSource, wRecord.getProductSource())
							&&StringUtil.isEquals(productState, wRecord.getProductState())) {
						isExist = true;
						wRecord.setSysCount(wRecord.getSysCount()+sysCount);
						wRecord.setWareCount(wRecord.getWareCount()+wareCount);
					}
				}
				if(!isExist) {
					WareReportRecord wrr = new WareReportRecord();
					wrr.setProductCode(productCode);
					wrr.setProductName(productName);
					wrr.setProductSource(productSource);
					wrr.setProductState(productState);
					wrr.setWareCount(wareCount);
					wrr.setSysCount(sysCount);
					tmpWareList.add(wrr);
				}
			}
		}
		return tmpWareList;
//		List<WareReportRecord> wList = new ArrayList<WareReportRecord>();
//		List<WareUntakeVo> uList = new  ArrayList<WareUntakeVo>();
//		if(wareList==null) {
//			wareList = new ArrayList<WareReportRecord>();
//		}
//		if(untakeList==null) {
//			untakeList = new ArrayList<WareUntakeVo>();
//		}
//		//上报的库存信息以及营业部未派发的产品信息以计算合计信息
//		if(wareList!=null) {
//			wList.addAll(wareList);
//		}
//		if(untakeList!=null) {
//			uList.addAll(untakeList);
//		}
//		
//		List<WareReportRecord> reList = new ArrayList<WareReportRecord>();
//		if(wareList.isEmpty()&&untakeList.isEmpty()){
//			return reList;
//		}
//		
//		
//		if(!wareList.isEmpty()){
//			
//			for(WareReportRecord wareReportRecord:wareList){
//				productCode = wareReportRecord.getProductCode();
//				productName = wareReportRecord.getProductName();
//				productSource = wareReportRecord.getProductSource();
//				productState = wareReportRecord.getProductState();
//				wareCount = 0;
//				sysCount = 0;
//				if(!wList.isEmpty()){
//					for(int i = 0;i<wList.size();i++){
//						WareReportRecord ware = wList.get(i);
//						if(ware.getProductCode()==null) {
//							wList.remove(i);
//							continue;
//						}
//						if(ware.getProductCode().equals(productCode)
//								&&ware.getProductSource().equals(productSource)
//								&&ware.getProductState().equals(productState)){
//							wareCount+=(ware.getWareCount()==null?0:ware.getWareCount());
//							sysCount+=(ware.getSysCount()==null?0:ware.getSysCount());
//							wList.remove(i);
//						}
//					}
//				}
//				if(!uList.isEmpty()){
//					for(int i=0;i<uList.size();i++){
//						WareUntakeVo wareUntakeVo = uList.get(i);
//						if(wareUntakeVo.getProductCode().equals(productCode)
//								&&wareUntakeVo.getProductSource().equals(productSource)
//								&&wareUntakeVo.getProductState().equals(productState)){
//							wareCount+=(wareUntakeVo.getWareCount()==null?0:wareUntakeVo.getWareCount());
//							sysCount+=(wareUntakeVo.getSysCount()==null?0:wareUntakeVo.getSysCount());
//							uList.remove(i);
//						}
//					}
//				}
//				
//				if(!wareCount.equals(0)||!sysCount.equals(0)){
//					WareReportRecord wrr = new WareReportRecord();
//					wrr.setProductCode(productCode);
//					wrr.setProductName(productName);
//					wrr.setProductSource(productSource);
//					wrr.setProductState(productState);
//					wrr.setWareCount(wareCount);
//					wrr.setSysCount(sysCount);
//					reList.add(wrr);
//				}
//				
//			}
//			for(WareUntakeVo wareUntakeVo:untakeList){
//				productCode = wareUntakeVo.getProductCode();
//				productName = wareUntakeVo.getProductName();
//				productSource = wareUntakeVo.getProductSource();
//				productState = wareUntakeVo.getProductState();
//				wareCount = 0;
//				sysCount = 0;
//				if(!wList.isEmpty()){
//					for(int i=0;i<wList.size();i++){
//						WareReportRecord ware = wList.get(i);
//						if(ware.getProductCode().equals(productCode)
//								&&ware.getProductSource().equals(productSource)
//								&&ware.getProductState().equals(productState)){
//							wareCount+=(ware.getWareCount()==null?0:ware.getWareCount());
//							sysCount+=(ware.getSysCount()==null?0:ware.getSysCount());
//							wList.remove(i);
//						}
//					}
//				}
//				if(!uList.isEmpty()){
//					for(int i=0;i<uList.size();i++){
//						WareUntakeVo wVo = uList.get(i);
//						if(wVo.getProductCode().equals(productCode)
//								&&wVo.getProductSource().equals(productSource)
//								&&wVo.getProductState().equals(productState)){
//							wareCount+=(wVo.getWareCount()==null?0:wVo.getWareCount());
//							sysCount+=(wVo.getSysCount()==null?0:wVo.getSysCount());
//							uList.remove(i);
//						}
//					}
//				}
//				
//				
//				//if(!wareCount.equals(0)||!sysCount.equals(0)){
//					WareReportRecord wrr = new WareReportRecord();
//					wrr.setProductCode(productCode);
//					wrr.setProductName(productName);
//					wrr.setProductSource(productSource);
//					wrr.setProductState(productState);
//					wrr.setWareCount(wareCount);
//					wrr.setSysCount(sysCount);
//					reList.add(wrr);
//				//}
//			}
//		}
//		return reList;
	}

	@Override
	public Map<String, Object> saveDaySetApprove(CusPointPoJo cusPointPojo, SysAdmin operator,
			DaySetApprove daySetApprove) throws Exception {
		DaySetApprove sysApprove = daySetReportDao.findDaySetApproveById(daySetApprove.getId());
		daySetApprove.setSettleDay(sysApprove.getSettleDay());
		daySetApprove.setSalesDep(sysApprove.getSalesDep());
		DaySetApprove lastApprove = daySetReportDao.findLastApprove(daySetApprove.getSalesDep(),
				sysApprove.getSettleDay());
		if (lastApprove != null && StringUtil.isEquals(lastApprove.getState(), "0")) {
			throw new Exception("该营业部存在历史未审批的日结数据，无法审批当天日结数据！");
		}
		DaySetApprove sysDaySet = daySetReportDao.findDaySetApproveById(daySetApprove.getId());
		sysDaySet.setSysStartTime(daySetApprove.getSysStartTime());
		sysDaySet.setSysEndTime(daySetApprove.getSysEndTime());
		sysDaySet.setAppOpinion(daySetApprove.getAppOpinion());
		// 保持系统计算金额以及产品库存
		saveSysFee(daySetApprove);
		saveSysWare(daySetApprove);
		
		// 设置库存地
		CustomPoint customPoint = daySetReportDao.findCurrentPointById(cusPointPojo.getCusPoint());
		if (customPoint == null) {
			throw new Exception("库存地不能为空");
		}
		saveUntakeWareSolid(customPoint,daySetApprove);
		// TODO
		// 更新主账户缴款记录表，储值卡业务记录表，记帐卡业务记录表，电子标签业务记录表
		updateDaySetApproveBusiness(sysDaySet);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置审批人信息
			sysDaySet.setApprover(operator.getId());
			sysDaySet.setApproverNo(operator.getLoginName());
			sysDaySet.setApproverName(operator.getUserName());
			// 审批日期是当天
			sysDaySet.setAppTime(new Date());
			// 设置审批状态为已审批
			sysDaySet.setState("1");
			
			sysDaySet.setStockPlace(customPoint.getStockPlace());
			daySetReportDao.updateDaySetApprove(sysDaySet);
			map.put("success", true);
			map.put("msg", "上报成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	private void saveUntakeWareSolid(CustomPoint customPoint,DaySetApprove daySetApprove) throws Exception {
		List<WareUntakeVo> untakeList = findUntakeWareVo(customPoint.getStockPlace(),daySetApprove.getSettleDay());
		if(untakeList==null) {
			throw new Exception("存在库存地没上报产品库存情况！");
		}
		for(WareUntakeVo wareUntakeVo:untakeList){
			daySetReportDao.saveUntakeSolid(wareUntakeVo,daySetApprove);
		}
	}

	private void saveSysWare(DaySetApprove daySetApprove) throws ParseException {
		List<WareReportRecord> wareList = daySetReportDao.findTotalWare(daySetApprove.getId(),
				DateUtil.formatDate(daySetApprove.getSysStartTime(), "yyyyMMddHHmmss"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyyMMddHHmmss"),
				daySetApprove.getSalesDep(),daySetApprove.getSettleDay());
		for (WareReportRecord wareReportRecord : wareList) {
			daySetReportDao.saveSysWareSolid(wareReportRecord, daySetApprove.getId());
		}
	}

	private void saveSysFee(DaySetApprove daySetApprove) throws ParseException {
		// 获取该营业点下所有网点信息
		List<FeeReportRecordVo> feeVoList = findApproveFeeList(daySetApprove,
				DateUtil.formatDate(daySetApprove.getSysStartTime(), "yyyyMMddHHmmss"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyyMMddHHmmss"));
		for (FeeReportRecordVo feeReportRecordVo : feeVoList) {
			daySetReportDao.saveSysFeeSolid(feeReportRecordVo, daySetApprove.getId());
		}

	}

	private void updateDaySetApproveBusiness(DaySetApprove daySetApprove) throws ParseException {
		// 获取该营业点下所有网点信息
		List<CustomPoint> customPointList = daySetReportDao.findCustomPointList(daySetApprove.getSalesDep());
		String placeCodeList = "";
		if (!customPointList.isEmpty()) {
			for (CustomPoint customPoint : customPointList) {
				placeCodeList += "'" + customPoint.getCode() + "',";
			}
		}
		if (!StringUtil.isEmpty(placeCodeList)) {
			placeCodeList = placeCodeList.substring(0, placeCodeList.length() - 1);
		}
		// 更新主账户缴款信息CSMS_RECHARGEINFO
		daySetReportDao.updateRechargeInfo(
				DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss"), placeCodeList);
		// 更新储值卡卡信息表CSMS_PREPAIDC
		// 更新储值卡业务记录表CSMS_PREPAIDC_BUSINESS
		daySetReportDao.updatePrepaidCBussiness(
				DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss"), placeCodeList);
		// 更新记帐卡卡信息表CSMS_ACCOUNTC_INFO
		// 更新记帐卡业务记录表CSMS_ACCOUNTC_BUSINESS
		daySetReportDao.updateAccountCBussiness(
				DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss"), placeCodeList);
		// 更新电子标签提货金额登记表CSMS_TAGTAKEFEE_INFO
		daySetReportDao.updateTagTakeFee(DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss"), placeCodeList);
		// 更新电子标签提货记录表CSMS_TAGTAKE_INFO
		daySetReportDao.updateTagTakeInfo(
				DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss"), placeCodeList);
		// 更新电子标签发行信息表CSMS_TAG_INFO
		// 更新电子标签维护记录表CSMS_TAGMAIN_RECORD
		// 更新电子标签业务操作记录表CSMS_TAG_BUSINESSRECORD
		daySetReportDao.updateTagBusiness(
				DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss"), placeCodeList);
	}

	private String getStartTime(Long stockId, String settleDay) throws ParseException {
		String startTime = "";
		// 获取前一的系统开始时间和结束时间
		DaySetApprove lastDaySetApprove = daySetReportDao.findLastApprove(stockId, settleDay);

		// 如果前一天的记录找不到，那就从当天的00:00:00 00:00:00至当前时间
		if (lastDaySetApprove == null) {
			startTime = DateUtil.formatDate(DateUtil.toDate(settleDay), "yyyyMMdd") + "000000";
		} else {
			if (lastDaySetApprove.getSysEndTime() == null) {
				return null;
			}
			startTime = DateUtil.formatDate(DateUtil.addSecond(lastDaySetApprove.getSysEndTime(), 1), "yyyyMMddHHmmss");
		}
		return startTime;
	}

	private String getEndTime(Date settleDay) {
		String endTime = "";
		// 如果传入时间为今天，则结束时间为传入时间，否则为传入时间的23:59:59
		if (StringUtil.isEquals(DateUtil.formatDate(settleDay, "yyyyMMdd"),
				DateUtil.formatDate(new Date(), "yyyyMMdd"))) {
			endTime = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		} else {
			endTime = DateUtil.formatDate(settleDay, "yyyyMMdd") + "235959";
		}
		return endTime;
	}

	private List<FeeReportRecordVo> findApproveFeeList(DaySetApprove daySetApprove, String lastTime, String endTime)
			throws ParseException {
		// 获取资金上报信息
		List<FeeReportRecordVo> feeList = daySetReportDao.findFeeReportByApproveId(daySetApprove);
		// 获取系统计算资金信息
		for (FeeReportRecordVo feeReportRecordVo : feeList) {
			SysFee sysFee = calcSysFee(daySetApprove.getId(), lastTime, endTime, feeReportRecordVo.getPlaceCode());
			if (sysFee == null) {// 系统计算金额不存在该记录
				feeReportRecordVo.setSysCash(0.00);
				feeReportRecordVo.setSysPos(0.00);
				feeReportRecordVo.setSysAlipay(0.00);
				feeReportRecordVo.setSysWechat(0.00);
				feeReportRecordVo.setSysTransferAccount(0.00);
				feeReportRecordVo.setSysBill(0.00);
			}else{
				feeReportRecordVo.setSysCash(sysFee.getCash()==null?0.00:sysFee.getCash());
				feeReportRecordVo.setSysPos(sysFee.getPos()==null?0.00:sysFee.getPos());
				feeReportRecordVo.setSysAlipay(sysFee.getAlipay()==null?0.00:sysFee.getAlipay());
				feeReportRecordVo.setSysWechat(sysFee.getWechat()==null?0.00:sysFee.getWechat());
				feeReportRecordVo.setSysTransferAccount(sysFee.getTransferAccount()==null?0.00:sysFee.getTransferAccount());
				feeReportRecordVo.setSysBill(sysFee.getBill()==null?0.00:sysFee.getBill());
			}
		}

		return feeList;
	}

	private SysFee calcSysFee(Long daySetId, String startTime, String endTime, String placeCode) throws ParseException {
		SysFee sysFeeDaySetReport = new SysFee();
		// 根据开始时间和结束时间，查询系统计算的金额
		List<Map<String, Object>> systemFeeList = daySetReportDao.findPlaceSysFee(placeCode, startTime, endTime);
		if (systemFeeList == null || systemFeeList.size() == 0) {
			return null;
		} else {
			for (Map<String, Object> systemFee : systemFeeList) {
				switch ((String) systemFee.get("PAYMENTTYPE")) {
				case "1":
					sysFeeDaySetReport.setCash(new Double(systemFee.get("TAKEBALANCE").toString()));
					break;
				case "2":
					sysFeeDaySetReport.setPos(new Double(systemFee.get("TAKEBALANCE").toString()));
					break;
				case "3":
					sysFeeDaySetReport.setTransferAccount(new Double(systemFee.get("TAKEBALANCE").toString()));
					break;
				case "4":
					sysFeeDaySetReport.setAlipay(new Double(systemFee.get("TAKEBALANCE").toString()));
					break;
				case "5":
					sysFeeDaySetReport.setWechat(new Double(systemFee.get("TAKEBALANCE").toString()));
					break;
				case "7":
					sysFeeDaySetReport.setBill(new Double(systemFee.get("TAKEBALANCE").toString()));
				}
			}

		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

		sysFeeDaySetReport.setStartTime(sdf2.parse(startTime));
		sysFeeDaySetReport.setEndTime(sdf2.parse(endTime));
		sysFeeDaySetReport.setCreateTime(new Date());
		sysFeeDaySetReport.setDaySetId(daySetId);
		return sysFeeDaySetReport;
	}

	private List<CashDepositDaySet> findCashDepositDayApprove(List<FeeReportRecordVo> feeVoList,
			DaySetApprove daySetApprove, List<DaySetCorrectRecord> correctList) {
		List<CashDepositDaySet> toDayCashDepositDay = daySetReportDao.findCashDepositDayByDate(daySetApprove);

		List<CashDepositDaySet> lastDayCashDepositDay = daySetReportDao
				.findCashDepositDayByDate(DateUtil.getPreDay(daySetApprove.getSettleDay(), "yyyy-MM-dd"));

		if (toDayCashDepositDay != null) {
			for (CashDepositDaySet cashDepositDaySet : toDayCashDepositDay) {
				// 获取上一日的未缴存金额
				if (lastDayCashDepositDay != null) {
					for (CashDepositDaySet lastDepositDaySet : lastDayCashDepositDay) {
						if (cashDepositDaySet.getPlaceid().equals(lastDepositDaySet.getPlaceid())) {
							cashDepositDaySet.setPreDayOutstandingAmount(lastDepositDaySet.getOutstandingAmount());
						}
					}
				}

				// 获取本日修正金额
				Double total = new Double(0);
				if (correctList != null) {
					for (DaySetCorrectRecord daySetCorrectRecord : correctList) {

						if (StringUtil.isEquals(cashDepositDaySet.getPlaceCode(), daySetCorrectRecord.getPlaceCode())
								&& daySetCorrectRecord.getCorrectFee() != null&&StringUtil.isEquals(daySetCorrectRecord.getCorrectType(), "2")) {
							total = total.doubleValue() + daySetCorrectRecord.getCorrectFee().doubleValue();
						}
					}
				}
				cashDepositDaySet.setCorrectCash(total);
				// 获取本日现金收款金额
				Double todayFee = new Double(0);
				if (feeVoList != null) {
					for (FeeReportRecordVo feeReportRecordVo : feeVoList) {
						if (feeReportRecordVo.getPlaceid().equals(cashDepositDaySet.getPlaceid())) {
							todayFee = feeReportRecordVo.getCash();
						}
					}
				}
				cashDepositDaySet.setToDayCash(todayFee);
			}
		}
		return toDayCashDepositDay;
	}

	private List<WareReportRecord> findApproveWare(DaySetApprove daySetApprove, String startTime, String endTime)
			throws ParseException {

		// 获取产品库存情况信息
		List<WareReportRecord> wareList = daySetReportDao.findWareReportByApproveId(daySetApprove.getId());

		// 获取该营业点下所有网点信息
		List<CustomPoint> customPointList = daySetReportDao.findCustomPointList(daySetApprove.getSalesDep());

		// 获取系统计算开始时间

		// 系统计算的回收和代销回收的产品信息
		List<Map<String, Object>> sysWareCycleList = null;
		// 开始时间为空即前一天没审批，无法获取系统数据
		if (startTime != null) {
			sysWareCycleList = daySetReportDao.findSysWareList(daySetApprove.getSalesDep(), startTime, endTime);
		}
		List<WareReportRecord> wareListT = new ArrayList<WareReportRecord>();
		// 库存的产品信息合并
		for (CustomPoint customPoint : customPointList) {
			// 设置人工填写信息
			if (wareList != null) {
				for (WareReportRecord handleRecord : wareList) {
					// 计算产品的系统数量
					if (sysWareCycleList != null) {
						for (Map<String, Object> sysWareCycle : sysWareCycleList) {
							// 根据营业网点、产品编码、产品来源、产品状态匹配系统计算的产品的数量
							if (handleRecord.getPlaceid().equals(new Long(sysWareCycle.get("SOURCE").toString()))
									&& StringUtil.isEquals(handleRecord.getProductCode(),
											sysWareCycle.get("PRODUCTCODE").toString())// 产品编码一样
									&& StringUtil.isEquals(handleRecord.getProductSource(),
											sysWareCycle.get("PRODUCTSOURCE").toString())// 产品来源一样
									&& StringUtil.isEquals(handleRecord.getProductState(),
											sysWareCycle.get("PRODUCTSTATE").toString())) {// 产品状态一样
								handleRecord.setSysCount(Integer.parseInt(sysWareCycle.get("TOTALCOUNT").toString()));
								// 打标记，系统计算出来的，人工已填写
								sysWareCycle.put("isReport", true);

							}
						}
					}
					if (StringUtil.isEquals(customPoint.getCode(), handleRecord.getPlaceCode())) {
						wareListT.add(handleRecord);
					}
				}
			}
		}
		// 设置系统计算有的，人工没填写的
		if (sysWareCycleList != null) {
			//如果没有进行产品库存上报的话，wareList会为空
			if(wareList==null){
				wareList = new ArrayList<WareReportRecord>();
			}
			for (Map<String, Object> sysWareCycle : sysWareCycleList) {
				if (sysWareCycle.get("isReport") == null || !Boolean.valueOf(sysWareCycle.get("isReport").toString())) {
					WareReportRecord sysWareCycleRecord = new WareReportRecord();
					sysWareCycleRecord.setPlaceCode(sysWareCycle.get("SOURCE").toString());
					sysWareCycleRecord.setPlaceName(sysWareCycle.get("SOURCENAME").toString());
					sysWareCycleRecord.setProductCode(sysWareCycle.get("PRODUCTCODE").toString());
					sysWareCycleRecord.setProductName(sysWareCycle.get("PRODUCTNAME").toString());
					sysWareCycleRecord.setProductSource(sysWareCycle.get("PRODUCTSOURCE").toString().trim());
					sysWareCycleRecord.setProductState(sysWareCycle.get("PRODUCTSTATE").toString().trim());
					sysWareCycleRecord.setWareCount(0);
					sysWareCycleRecord.setSysCount(Integer.parseInt(sysWareCycle.get("TOTALCOUNT").toString()));
					wareList.add(sysWareCycleRecord);
				}
			}
		}
		return wareList;
	}

	private Integer getSysProduct(Long id) {
		List<SysWare> wareList = daySetReportDao.findSysWare(id);
		Integer num = 0;
		if (wareList == null) {
			return num;
		}
		for (SysWare sysWare : wareList) {
			num += sysWare.getProductNum();
		}
		return num;
	}

	@Override
	public FeeWareReportVo findApproveVoView(DaySetApprove daySetApprove) throws ParseException, Exception {
		FeeWareReportVo feeWareReportVo = new FeeWareReportVo();
		// 根据审批id获取审批内容
		daySetApprove = daySetReportDao.findDaySetApproveById(daySetApprove.getId());
		feeWareReportVo.setDaySetApprove(daySetApprove);
		// 获取资金收入情况
		List<FeeReportRecordVo> feeVoList = daySetReportDao.findFeeSolid(daySetApprove.getId());
		// 获取短款修正情况
		List<DaySetCorrectRecord> correctList = daySetReportDao.findDaySetCorrectRecordList(daySetApprove.getId(),
				null);
		// 获取短款修正情况，按修正时间，如果修正时间必须比日结计算开始时间要大
		List<DaySetCorrectRecord> sysCorrectList = daySetReportDao.findDaySetCorrectRecord(DateUtil.formatDate(daySetApprove.getSysStartTime(), "yyyyMMddHHmmss"),
				DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyyMMddHHmmss"),daySetApprove.getSalesDep());
		// 获取现金缴存情况
		List<CashDepositDaySet> toDayCashDepositDay = findCashDepositDayApprove(feeVoList, daySetApprove, sysCorrectList);
		// 获取产品库存情况信息
		List<WareReportRecord> wareList = daySetReportDao.findWareSolid(daySetApprove.getId());
		// 获取未领取产品信息
		List<WareUntakeVo> untakeList = daySetReportDao.findUntakeSolid(daySetApprove.getId());
		//长款修正
		List<LongFeeCorrect> longFeeCorrectList = daySetReportDao.findLongFeeList(daySetApprove.getSalesDep(),DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay(), "yyyy-MM-dd"), "yyyyMMdd") );

		//feeWareReportVo.setProductNum(getSysProduct(daySetApprove.getId()));

		feeWareReportVo.setFeeVoList(feeVoList); 

		feeWareReportVo.setCashDepositList(toDayCashDepositDay);

		feeWareReportVo.setWareList(wareList);

		feeWareReportVo.setCorrectList(correctList);

		feeWareReportVo.setUntakeList(untakeList);
		
		feeWareReportVo.setTotalWare(calcTotalWare(wareList,untakeList));
		
		feeWareReportVo.setLongFeeCorrectList(longFeeCorrectList);

		return feeWareReportVo;
	}

	
	@Override
	public Boolean isCustomPointApprove(Long customPointId,String settleDay){
		CustomPoint customPoint = daySetReportDao.findCurrentPointById(customPointId);
		DaySetApprove daySetApprove = daySetReportDao.findDaySetApproveByDate(settleDay, customPoint.getParent());
		return isReportApprove(daySetApprove);
	}
	
	private List<WareUntakeVo> findUntakeWareVo(Long stockId,String settleDay){
		return daySetReportDao.findUntakeWareListAll(stockId, settleDay);
//		List<WareUntakeVo> list = daySetReportDao.findSysUntakeWareList(stockId, settleDay);
//		if(list==null){
//			list = new ArrayList<WareUntakeVo>();
//		}
//		List<WareUntake> handleList = daySetReportDao.findUntakeWareList(stockId, settleDay);
//		if(handleList!=null){
//			for(WareUntake wareUntake:handleList){
//				Boolean exits = false;
//				if(list!=null){
//				for(WareUntakeVo wareUntakeVo:list){
//					if(StringUtil.isEquals(wareUntakeVo.getProductCode(), wareUntake.getProductCode())&&
//							StringUtil.isEquals(wareUntakeVo.getProductSource(), wareUntake.getProductSource())&&
//							StringUtil.isEquals(wareUntakeVo.getProductState(), wareUntake.getProductState())){
//						exits = true;
//					}
//				}
//				}
//				if(!exits){
//					WareUntakeVo vo = new WareUntakeVo();
//					vo.setProductName(wareUntake.getProductCode());
//					vo.setProductName(wareUntake.getProductName());
//					vo.setProductSource(wareUntake.getProductSource());
//					vo.setProductState(wareUntake.getProductState());
//					vo.setSysCount(0);
//					vo.setWareCount(wareUntake.getWareCount());
//					list.add(vo);
//				}
//			}
//		}
//		
//		return list;
	}

	@Override
	public DaySetApprove findApproveById(Long id) {
		return daySetReportDao.findDaySetApproveById(id);
	}
}
