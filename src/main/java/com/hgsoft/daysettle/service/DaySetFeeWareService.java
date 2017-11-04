package com.hgsoft.daysettle.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetReportDao;
import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.DaySetCorrectRecord;
import com.hgsoft.daysettle.entity.FeeReportRecord;
import com.hgsoft.daysettle.entity.FeeWareReportVo;
import com.hgsoft.daysettle.entity.LongFeeCorrect;
import com.hgsoft.daysettle.entity.WareReportRecord;
import com.hgsoft.daysettle.serviceInterface.IDaySetApproveService;
import com.hgsoft.daysettle.serviceInterface.IDaySetFeeWareService;
import com.hgsoft.daysettle.serviceInterface.IDaySetReportService;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;

@Service
public class DaySetFeeWareService implements IDaySetFeeWareService{
	
	@Resource
	private DaySetReportDao daySetReportDao;
	@Resource
	private IDaySetApproveService daySetApproveService;
	@Resource
	private IDaySetReportService daySetReportService;
	
	@Override
	public Pager findFeeWareList(Pager pager,Long placeId, String settleDay, String state,SysAdmin sysAdmin) {
		
		return daySetReportDao.findFeeWareList(pager, placeId, settleDay, state,false);
	}
	
	private void saveFee(FeeReportRecord feeReportRecord,CusPointPoJo cuspoint,SysAdmin sysAdmin,DaySetApprove daySetApprove) throws Exception{
		//判断本次上报是新增还是修改
		if(feeReportRecord.getId()==null){//新增
			//保存审批表id
			feeReportRecord.setDaySetID(daySetApprove.getId());
			//保存审批日期为审批表上的审批日期
			feeReportRecord.setSettleDay(daySetApprove.getSettleDay());
		}
		//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
		feeReportRecord.setOperid(sysAdmin.getId());
		feeReportRecord.setOperCode(sysAdmin.getLoginName());
		feeReportRecord.setOperName(sysAdmin.getUserName());
		feeReportRecord.setPlaceid(cuspoint.getCusPoint());
		feeReportRecord.setPlaceCode(cuspoint.getCusPointCode());
		feeReportRecord.setPlaceName(cuspoint.getCusPointName());
		daySetReportDao.saveFee(feeReportRecord);	
	}
	@Override
	public Map<String,Object> saveFeeWareReport(CusPointPoJo cuspoint,SysAdmin sysAdmin, FeeWareReportVo feeWareReportVo, String[] deleteFeeList,
			String[] deleteWareList) throws DuplicateKeyException,Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		//判断前一天是否填报
		//根据需求，资金和产品，只要填写一项，就允许填写第二天的日报
		if(feeWareReportVo.getFeeList().get(0)!=null&&feeWareReportVo.getFeeList().get(0).getId()==null){
			//表示新增
			if(!isPreFeeReport(cuspoint,feeWareReportVo.getDaySetApprove())){
				map.put("success", false);
	  			map.put("msg", "前一天资金没上报不能进行当天资金上报");
	  			return map;
			}
		}
		
		if(!isPreWareReport(cuspoint,feeWareReportVo.getDaySetApprove())){
			map.put("success", false);
  			map.put("msg", "前一天库存没上报不能进行当天资金上报");
  			return map;
		}
		
		//获取上一级即营业厅信息
		CustomPoint parentPoint = daySetReportDao.findCustomPoint(cuspoint.getCusPoint());
		if(parentPoint==null){
			map.put("success", false);
  			map.put("msg", "网点库存地不能为空！");
  			return map;
		}
		//判断审批表的记录是否为空，如果为空则表示新增，否则表示修改
		DaySetApprove daySetApprove = daySetApproveService.saveApprove(cuspoint,feeWareReportVo.getDaySetApprove(),parentPoint);
		if(daySetApprove==null){
  			map.put("success", false);
  			map.put("msg", "系统出错，请联系管理员");
  			return map;
  		}
		//如果日结已经审批，则不允许修改
		if(daySetApproveService.isReportApprove(daySetApprove)){
			map.put("success", false);
  			map.put("msg", "该日期已经审核！");
  			return map;
		}
		
		//删除界面上删除的产品库存记录
		for(int i = 0;i<deleteWareList.length;i++){
			//判断id不能为空
			if(!StringUtil.isEmpty(deleteWareList[i])){
				//执行数据库删除
				daySetReportDao.deleteWare(deleteWareList[i]);
			}
		}
  		List<FeeReportRecord> feeList =  feeWareReportVo.getFeeList();
  		//保存资金上报信息
  		if(feeList!=null&&feeList.get(0)!=null){
  			saveFee(feeList.get(0),cuspoint,sysAdmin,daySetApprove);
  		}
  		
  		//保存库存上报信息
  		List<WareReportRecord> wareList =  feeWareReportVo.getWareList();
  		saveWare(cuspoint, sysAdmin, daySetApprove, wareList);
		
		map.put("success", true);
		map.put("msg", "上报成功");
		return map;
	}
	
	private boolean isPreWareReport(CusPointPoJo cuspoint, DaySetApprove daySetApprove) {
		//判断是新增还是修改,如果带过来的参数有id则判断为修改
		if(daySetApprove.getId()!=null){
			return true;
		}
		List<WareReportRecord> list = daySetReportDao.findWareByDate(cuspoint.getCusPoint(), 
				DateUtil.getPreDay(daySetApprove.getSettleDay(),"yyyy-MM-dd"));
		if(list!=null){
			return true;
		}
		//如果上报日期的前一天没数据，则需要判断本次上报是否第一条记录，如果第一次申报，则允许申报
		//如果本次申报不是第一次申报，而前一天没申报，则不允许申报
		Long reportCount = daySetReportDao.findFeeWareByCustonPoint(cuspoint.getCusPoint(),"CSMS_WAREREPORT_RECORD");
		if(reportCount==null||reportCount == 0){
			return true;
		}else{
			return false;
		}
	}

	private boolean isPreFeeReport(CusPointPoJo cuspoint, DaySetApprove daySetApprove) {
		List<FeeReportRecord> list = daySetReportDao.findFeeByDate(cuspoint.getCusPoint(), 
				DateUtil.getPreDay(daySetApprove.getSettleDay(),"yyyy-MM-dd"));
		if(list!=null){
			return true;
		}
		//如果上报日期的前一天没数据，则需要判断本次上报是否第一条记录，如果第一次申报，则允许申报
		//如果本次申报不是第一次申报，而前一天没申报，则不允许申报
		Long reportCount = daySetReportDao.findFeeWareByCustonPoint(cuspoint.getCusPoint(),"CSMS_FEEREPORT_RECORD");
		if(reportCount==null||reportCount == 0){
			return true;
		}else{
			return false;
		}
	}

	private void saveWare(CusPointPoJo cuspoint, SysAdmin sysAdmin, DaySetApprove daySetApprove,
			List<WareReportRecord> wareList) throws DuplicateKeyException,Exception {
		if(wareList==null){
			return;
		}
		for(WareReportRecord wareReportRecord:wareList){
			//去除前端删除了导致的null
			if(wareReportRecord==null){
				continue;
			}
			
			//保存审批表id
			wareReportRecord.setDaySetID(daySetApprove.getId());
			//保存审批日期为审批表上的审批日期
			wareReportRecord.setSettleDay(daySetApprove.getSettleDay());
			//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
			wareReportRecord.setOperid(sysAdmin.getId());
			wareReportRecord.setOperCode(sysAdmin.getLoginName());
			wareReportRecord.setOperName(sysAdmin.getUserName());
			wareReportRecord.setPlaceid(cuspoint.getCusPoint());
			wareReportRecord.setPlaceCode(cuspoint.getCusPointCode());
			wareReportRecord.setPlaceName(cuspoint.getCusPointName());
			daySetReportDao.saveWare(wareReportRecord);
		}
	}

	@Override
	public FeeWareReportVo findFeeWareByApprove(Long daySetId, Long placeId) {
		DaySetApprove daySetApprove = daySetReportDao.findDaySetApproveById(daySetId);
		FeeWareReportVo feeWareReportVo = new FeeWareReportVo();
		feeWareReportVo.setFeeList(daySetReportDao.findFeeReportByApproveId(daySetId,null,placeId));
		feeWareReportVo.setWareList(daySetReportDao.findWareReportByApproveId(daySetId,null,null,null,placeId));
		feeWareReportVo.setDaySetApprove(daySetReportDao.findDaySetApproveById(daySetId));
		List<DaySetCorrectRecord> correctList = daySetReportDao.findDaySetCorrectRecordList(daySetId, placeId);
		feeWareReportVo.setCorrectList(correctList );
		List<LongFeeCorrect> longFeeCorrectList;
		try {
			longFeeCorrectList = daySetReportDao.findCustomLongFeeList(placeId, DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay(), "yyyy-MM-dd"), "yyyyMMdd"));
			feeWareReportVo.setLongFeeCorrectList(longFeeCorrectList );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return feeWareReportVo;
		
	}
	
	@Override 
	public FeeWareReportVo findFeeWareDaySettleDay(FeeWareReportVo feeWareReportVo,Long operPlaceId){
		if(feeWareReportVo==null){
			feeWareReportVo = new FeeWareReportVo(new DaySetApprove());
		}
		Date settleDay = daySetReportDao.findSettleDay(operPlaceId);
		if(settleDay!=null){//如果原来有记录，则查找数据库最大的天数+1
			feeWareReportVo.getDaySetApprove().setSettleDay(DateUtil.formatDate(settleDay, "yyyy-MM-dd"));
		}else{//如果原来没有记录，则默认日结日期是当天
			feeWareReportVo.getDaySetApprove().setSettleDay(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		}
		return feeWareReportVo;
	}

}
