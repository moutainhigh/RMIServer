package com.hgsoft.daysettle.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetReportDao;
import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.DaySetCorrectRecord;
import com.hgsoft.daysettle.serviceInterface.IDaySetCorrectService;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;

@Service
public class DaySetCorrectService implements IDaySetCorrectService{

	@Resource
	private DaySetReportDao daySetReportDao;
	
	@Override
	public Pager findCorrectList(Pager pager, Long operPlaceId, String date, String state){
		CustomPoint custompoint = daySetReportDao.findCurrentPointById(operPlaceId);
		return daySetReportDao.findCorrectList(pager,custompoint.getParent(),state,date);
	}
	
	@Override
	public Pager findDaySetCorrectList(Pager pager, Long operPlaceId, String date, String state){
		return daySetReportDao.findDaySetCorrectList(pager,operPlaceId,date,state);
	}
	
	@Override
	public List<DaySetCorrectRecord> findDaySetCorrectRecordList(Long approveId,Long placeId){
		return daySetReportDao.findDaySetCorrectRecordList(approveId,placeId);
	}
	
	@Override
	public Map<String, Object> saveDaySetCorrect(CusPointPoJo operPlace, SysAdmin operator,
			DaySetCorrectRecord daySetCorrectRecord, DaySetApprove daySetApprove){
		Map<String,Object> map = new HashMap<String,Object>();
		//根据审批id查询审批信息
		daySetApprove = daySetReportDao.findDaySetApproveById(daySetApprove.getId());
		if(daySetApprove==null){
			map.put("success", false);
			map.put("msg", "保存失败，请联系管理员");
			return map;
		}
//		String[] deleteCorrectList = deleteCorrect.split(",");
//		//删除界面上删除的修正记录
//		for(int i = 0;i<deleteCorrectList.length;i++){
//			//判断id不能为空
//			if(!StringUtil.isEmpty(deleteCorrectList[i])){
//				//执行数据库删除
//				daySetReportDao.deleteCorrect(deleteCorrectList[i]);
//			}
//		}
//		if(correctList==null){
//			map.put("success", false);
//			map.put("msg", "保存失败，请联系管理员");
//			return map; 
//		}
//		for(DaySetCorrectRecord correct:correctList){
//			if(correct==null)
//				continue;
//			if(correct.getId()==null){
//				//默认只有短款修正
//				correct.setCorrectType("1");
//				
//				//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
//				correct.setOperid(operator.getId());
//				correct.setOperCode(operator.getLoginName());
//				correct.setOperName(operator.getUserName());
//				correct.setPlaceid(operPlace.getCusPoint());
//				correct.setPlaceCode(operPlace.getCusPointCode());
//				correct.setPlaceName(operPlace.getCusPointName());
//				correct.setCorrectTime(new Date());
//				correct.setDaySetID(daySetApprove.getId());
//				correct.setSettleDay(daySetApprove.getSettleDay());
//				daySetReportDao.insertDaySetCorrectRecord(correct);
//			}else{
//				
//				daySetReportDao.updateDaySetCorrectRecord(correct);
//			}
//		}
		
		if(daySetCorrectRecord.getId()==null){
			//默认只有短款修正
			daySetCorrectRecord.setCorrectType("1");
			
			//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
			daySetCorrectRecord.setOperid(operator.getId());
			daySetCorrectRecord.setOperCode(operator.getLoginName());
			daySetCorrectRecord.setOperName(operator.getUserName());
			daySetCorrectRecord.setPlaceid(operPlace.getCusPoint());
			daySetCorrectRecord.setPlaceCode(operPlace.getCusPointCode());
			daySetCorrectRecord.setPlaceName(operPlace.getCusPointName());
			daySetCorrectRecord.setCorrectTime(new Date());
			daySetCorrectRecord.setDaySetID(daySetApprove.getId());
			daySetCorrectRecord.setSettleDay(daySetApprove.getSettleDay());
			daySetReportDao.insertDaySetCorrectRecord(daySetCorrectRecord);
		}else{
			
			daySetReportDao.updateDaySetCorrectRecord(daySetCorrectRecord);
		}
		map.put("success", true);
		map.put("msg", "上报成功");
		return map;
	}
}
