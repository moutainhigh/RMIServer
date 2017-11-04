package com.hgsoft.daysettle.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetReportDao;
import com.hgsoft.daysettle.entity.CashDepositDaySet;
import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.FeeReportRecord;
import com.hgsoft.daysettle.serviceInterface.IDaySetApproveService;
import com.hgsoft.daysettle.serviceInterface.IDaySetCashDepositService;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;

@Service
public class DaySetCashDepositService implements IDaySetCashDepositService{

	@Resource
	private DaySetReportDao daySetReportDao;
	@Resource
	private IDaySetApproveService daySetApproveService;
	
	@Override
	public CashDepositDaySet findCashDepositDaySet(Long id) {
		return daySetReportDao.findCashDeposiDaySet(id);
	}
	@Override
	public Map<String, Object> saveCashPeposit(CusPointPoJo cuspoint,SysAdmin sysAdmin,CashDepositDaySet cashDepositDaySet, DaySetApprove daySetApprove){
		Map<String,Object> map = new HashMap<String,Object>();
		
		
		//判断库存地不能为空
		CustomPoint parentPoint = daySetReportDao.findCustomPoint(cuspoint.getCusPoint());
		daySetApprove = daySetApproveService.saveApprove(cuspoint,daySetApprove,parentPoint);
		Boolean isAdd = true;
		//判断新上报 还是修改
		if(daySetReportDao.findCashDepositDayByApprove(daySetApprove.getId(),cuspoint.getCusPoint())==null){
			isAdd = true;
		}else{
			isAdd = false;
		}
		//如果是新上报的，需要判断前一天是否填报
		if(isAdd){
			if(!isPreCashReport(cuspoint,daySetApprove)){
				map.put("success", false);
	  			map.put("msg", "前一天现金缴存信息没上报不能进行当天上报");
	  			return map;
			}
		}
		if(parentPoint==null){
			map.put("success", false);
  			map.put("msg", "网点库存地不能为空！");
  			return map;
		}
		
		//判断日结是否审核，如果已经审核，则不允许新增和修改
		if(daySetApproveService.isReportApprove(daySetApprove)){
			map.put("success", false);
  			map.put("msg", "该日期已经审批，不能进行操作");
  			return map;
		}
		
		
		if(daySetApprove==null){
  			map.put("success", false);
  			map.put("msg", "该日期已上报");
  			return map;
  		}
		cashDepositDaySet.setDaySetID(daySetApprove.getId());
		cashDepositDaySet.setSettleDay(daySetApprove.getSettleDay());
		//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
		cashDepositDaySet.setOperid(sysAdmin.getId());
		cashDepositDaySet.setOperCode(sysAdmin.getLoginName());
		cashDepositDaySet.setOperName(sysAdmin.getUserName());
		cashDepositDaySet.setPlaceid(cuspoint.getCusPoint());
		cashDepositDaySet.setPlaceCode(cuspoint.getCusPointCode());
		cashDepositDaySet.setPlaceName(cuspoint.getCusPointName());
		
		if(isAdd){
			daySetReportDao.insertCashDepositDay(cashDepositDaySet);
		}else{
			daySetReportDao.updateCashDepositDay(cashDepositDaySet);
		}
		
		map.put("success", true);
		map.put("msg", "上报成功");
		return map;
	}
	
	private boolean isPreCashReport(CusPointPoJo cuspoint, DaySetApprove daySetApprove) {
		List<CashDepositDaySet> list = daySetReportDao.findCashByDate(cuspoint.getCusPoint(), 
				DateUtil.getPreDay(daySetApprove.getSettleDay(),"yyyy-MM-dd"));
		if(list!=null){
			return true;
		}
		//如果上报日期的前一天没数据，则需要判断本次上报是否第一条记录，如果第一次申报，则允许申报
		//如果本次申报不是第一次申报，而前一天没申报，则不允许申报
		Long reportCount = daySetReportDao.findFeeWareByCustonPoint(cuspoint.getCusPoint(),"CSMS_CASHDEPOSITDAYSET");
		if(reportCount==null||reportCount == 0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public Pager findCashDepositList(Pager pager, Long operPlaceId, String date, String state){
		return daySetReportDao.findCashDepositList(pager,operPlaceId,date,state);
	}
	
	@Override
	public CashDepositDaySet findCashDepositSettleDay(Long operPlaceId){
		
		CashDepositDaySet cashDepositDaySet = new CashDepositDaySet();
		
		Date settleDay = daySetReportDao.findCashDepositSettleDay(operPlaceId);
		
		if(settleDay!=null){//如果原来有记录，则查找数据库最大的天数+1
			cashDepositDaySet.setSettleDay(DateUtil.formatDate(settleDay, "yyyy-MM-dd"));
		}else{//如果原来没有记录，则默认日结日期是当天
			cashDepositDaySet.setSettleDay(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		}
		
		return cashDepositDaySet;
	}
}
