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
import com.hgsoft.daysettle.entity.FeeReportRecord;
import com.hgsoft.daysettle.entity.FeeWareReportVo;
import com.hgsoft.daysettle.entity.RemarkInfo;
import com.hgsoft.daysettle.entity.Role;
import com.hgsoft.daysettle.entity.WareReportRecord;
import com.hgsoft.daysettle.entity.WareUntake;
import com.hgsoft.daysettle.serviceInterface.IDaySetReportService;
import com.hgsoft.httpInterface.serviceInterface.IProductListService;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Service
public class DaySetReportService implements IDaySetReportService{

	@Resource
	private DaySetReportDao daySetReportDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private IProductListService productListService;
	
	@SuppressWarnings("unused")
	private void saveFee(CusPointPoJo cuspoint, SysAdmin sysAdmin, DaySetApprove daySetApprove,
			List<FeeReportRecord> feeList) {
		//修改资金上报表的记录
		for(FeeReportRecord feeReportRecord:feeList){
			if(feeReportRecord==null){
				continue;
			}
			//如果自己上报表的id为空，则表示新增加，否则表示修改
//			if(feeReportRecord.getId()==null){
//				
//				//如果收费员已经上报，那不执行添加操作
//				if(isFeeReported(daySetApprove.getId(),feeReportRecord.getPayeeCode(),cuspoint.getCusPoint())){
//					return;
//				}
//			}
			//保存审批表id
			feeReportRecord.setDaySetID(daySetApprove.getId());
			//保存审批日期为审批表上的审批日期
			feeReportRecord.setSettleDay(daySetApprove.getSettleDay());
			//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
			feeReportRecord.setOperid(sysAdmin.getId());
			feeReportRecord.setOperCode(sysAdmin.getLoginName());
			feeReportRecord.setOperName(sysAdmin.getUserName());
			feeReportRecord.setPlaceid(cuspoint.getCusPoint());
			feeReportRecord.setPlaceCode(cuspoint.getCusPointCode());
			feeReportRecord.setPlaceName(cuspoint.getCusPointName());
			daySetReportDao.saveFee(feeReportRecord);
		}
	}
	
	/*****************************************资金库存上报**********************************************************/
	
	public DaySetApprove saveApprove(CusPointPoJo cuspoint,DaySetApprove daySetApprove,CustomPoint stockMap){
		DaySetApprove daySet = daySetReportDao.findDaySetApproveByDate(daySetApprove.getSettleDay(),stockMap.getId()); 
		if(daySet == null){
			//如果日审批报表的id为空
  			//设置用户的库存地
			daySetApprove.setSalesDep(stockMap.getId());
	  		daySetApprove.setSalesDepName(stockMap.getName());
  			daySetApprove.setSettleDay(daySetApprove.getSettleDay());
  			//需要判断是否存在记录，如果存在，则返回数据库记录
//  			DaySetApprove existApprove = daySetReportDao.findByOperAndDay(daySetApprove);
//  			if(existApprove!=null){
//  				return existApprove;
//  			}
  			//审批状态默认为0，未审批
  			daySetApprove.setState("0");
  			
  			daySetApprove.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDAYSETAPPROVAL_NO"));
  			daySetReportDao.saveDaySetApprove(daySetApprove);
  			return daySetApprove;
		}
		return daySet;
	}
	

	@Override
	public FeeWareReportVo findDaySetReportVo(FeeWareReportVo feeWareReportVo,Long placeId) {

		feeWareReportVo.setDaySetApprove(daySetReportDao.findDaySetApproveById(feeWareReportVo.getDaySetApprove().getId()));
		feeWareReportVo.setFeeList(daySetReportDao.findFeeReportByApproveId(feeWareReportVo.getDaySetApprove().getId(),null,placeId));
		feeWareReportVo.setWareList(daySetReportDao.findWareReportByApproveId(feeWareReportVo.getDaySetApprove().getId(),null,null,null,placeId));
		feeWareReportVo.setCorrectList(daySetReportDao.findDaySetCorrectRecordList(feeWareReportVo.getDaySetApprove().getId(), placeId));
		return feeWareReportVo;
		
	}
	
	@Override
	public Map<String, Object> saveMemo(SysAdmin operator, RemarkInfo remarkInfo){
		
		//保存其他相关信息（操作人、操作人编码，名称，网点编码，名称）
		remarkInfo.setOperid(operator.getId());
		remarkInfo.setOperCode(operator.getLoginName());
		remarkInfo.setOperName(operator.getUserName());
		
		remarkInfo.setRemarkDate(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		
		
		try {
			daySetReportDao.saveMemo(remarkInfo);
			map.put("success", true);
			map.put("msg", "保存成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
		
	}
	
	@Override
	public List<RemarkInfo> findMemoList(RemarkInfo remarkInfo){
		return daySetReportDao.findMemoList(remarkInfo);
	}
	
	@Override

	public List<RemarkInfo> findFeeMemoList(FeeReportRecord feeReportRecord){
		return daySetReportDao.findFeeMemoList(feeReportRecord);
	}

	@Override
	public Pager findUntakeWare(Pager pager,CusPointPoJo cusPoint,String date,String state) {
		//判断库存地不能为空
		CustomPoint parentPoint = daySetReportDao.findCurrentPointById(cusPoint.getCusPoint());
		if(parentPoint==null){
			return null;
		}
		if(state==null||state.equals("")) {
			state = null;
		}
		return daySetReportDao.findUntakeWareList(pager,parentPoint.getStockPlace(),date,parentPoint.getParent(),state);
	}

	@Override
	public WareUntake findWareUntake(WareUntake wareUntake,CusPointPoJo cusPoint) throws ParseException {
		if(wareUntake==null){
			wareUntake=new WareUntake();
		}
		if(wareUntake.getId()==null){//新增
			//判断库存地不能为空
			CustomPoint parentPoint = daySetReportDao.findCurrentPointById(cusPoint.getCusPoint());
			if(parentPoint==null){
				return null;
			}
//			WareUntake lastUntake = daySetReportDao.findLastUntake(parentPoint.getStockPlace());
//			if(lastUntake!=null) {
//				wareUntake.setSettleDay(DateUtil.formatDate(
//						DateUtil.addDay(DateUtil.toDate(lastUntake.getSettleDay(), "yyyy-MM-dd"), 1),"yyyy-MM-dd"));
//			}
			wareUntake.setDepId(parentPoint.getStockPlace());
			wareUntake.setDepName(parentPoint.getName());
			return wareUntake;
		}else{
			return daySetReportDao.findUntakeWare(wareUntake);
		}
	}

	@Override
	public void saveWareUntake(List<WareUntake> untakeList, SysAdmin sysAdmin, CusPointPoJo cusPoint,
			String settleDay,String deleteWare) throws Exception {
		
		
		
		//判断库存地不能为空
		CustomPoint customPoint = daySetReportDao.findCurrentPointById(cusPoint.getCusPoint());
		if(cusPoint==null){
			throw new Exception("该网点的营业部不能为空！");
		}
		//判断前一天是否填报
		if(!isPreWareUntakeReport(customPoint,settleDay)){
			throw new Exception("请先填报前一天的记录！");
		}
		
		//删除要删除的记录
		String[] deleteIds = deleteWare.split(",");
		for(int i = 0;i<deleteIds.length;i++){
			//判断id不能为空
			if(!StringUtil.isEmpty(deleteIds[i])){
				//执行数据库删除
				daySetReportDao.deleteWareUntake(Long.valueOf(deleteIds[i]));
			}
		}
		//判断该日期是否日结，如果日结，则返回错误
		DaySetApprove daySetApprove = daySetReportDao.findDaySetApproveByDate(settleDay, customPoint.getParent());
		if(daySetApprove!=null&&StringUtil.isEquals(daySetApprove.getState(), "1")){
			throw new Exception("该日期已经审核，不能添加或修改信息！");
		}
		//如果是新增的，审批表新增一条记录
		if(daySetApprove==null) {
			daySetApprove = new DaySetApprove();
			
			//获取父节点信息
			CustomPoint parentPoint = daySetReportDao.findCurrentPointById(customPoint.getParent());
			daySetApprove.setSettleDay(settleDay);
			saveApprove(cusPoint,daySetApprove,parentPoint);
			
		}
		if(untakeList==null) {
			return ;
		}
		for(WareUntake wareUntake:untakeList) {
			if(wareUntake==null) {
				continue;
			}
			wareUntake.setDepId(customPoint.getStockPlace());
			wareUntake.setDepName(customPoint.getStockName());
			wareUntake.setOperId(sysAdmin.getId());
			wareUntake.setOperCode(sysAdmin.getLoginName());
			wareUntake.setOperName(sysAdmin.getUserName());
			wareUntake.setSettleDay(settleDay);
			try {
				daySetReportDao.saveWareUntake(wareUntake);
			}catch(DuplicateKeyException e) {
				throw new Exception ("该产品已登记请重新选择产品！");
			}
			catch (Exception e) {
				
				e.printStackTrace();
				throw new Exception("产品登记失败，请联系管理员！");
			}
		}
		
		
		
		
		
	}

	private boolean isPreWareUntakeReport(CustomPoint parentPoint, String settleDay) {
		List<WareUntake> list = daySetReportDao.findWareUntakeByDate(parentPoint.getStockPlace(), 
				DateUtil.getPreDay(settleDay,"yyyy-MM-dd"));
		if(list!=null){
			return true;
		}
		//如果上报日期的前一天没数据，则需要判断本次上报是否第一条记录，如果第一次申报，则允许申报
		//如果本次申报不是第一次申报，而前一天没申报，则不允许申报
		Long reportCount = daySetReportDao.findUntakeWareCount(parentPoint.getStockPlace(),settleDay);
		if(reportCount==null||reportCount == 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void deleteUntakeWare(WareUntake wareUntake,CusPointPoJo cusPoint) throws Exception {
		//判断库存地不能为空
		CustomPoint parentPoint = daySetReportDao.findCustomPoint(cusPoint.getCusPoint());
		if(parentPoint==null){
			throw new Exception("该网点的营业部不能为空！");
		}
		wareUntake = daySetReportDao.findUntakeWare(wareUntake);
		DaySetApprove daySetApprove = daySetReportDao.findDaySetApproveByDate(wareUntake.getSettleDay(), parentPoint.getId());
		if(daySetApprove!=null&&StringUtil.isEquals(daySetApprove.getState(), "1")){
			throw new Exception("该日期已经审核，不能删除信息！");
		}
		daySetReportDao.deleteWareUntake(wareUntake);
	}

	@Override
	public Map<String, Object> checkAllReport(DaySetApprove daySetApprove, CusPointPoJo cusPoint) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String,Object>();
		CustomPoint parentPoint = daySetReportDao.findCurrentPointById(cusPoint.getCusPoint());
		//判断是否所有网点资金上报已经填写
		Long totalCount = daySetReportDao.findUnReportCount(parentPoint.getParent(), daySetApprove.getSettleDay(), "CSMS_FEEREPORT_RECORD");
		if(totalCount!=null&&totalCount>0){
			map.put("success", false);
			map.put("msg", "当天存在资金未上报日结的网点，无法审批");
			return map;
		}
		//判断是否所有网点库存上报已经填写
		totalCount = daySetReportDao.findUnReportCount(parentPoint.getParent(), daySetApprove.getSettleDay(), "CSMS_WAREREPORT_RECORD");
		if(totalCount!=null&&totalCount>0){
			map.put("success", false);
			map.put("msg", "当天存在库存未上报日结的网点，无法审批");
			return map;
		}
		//判断是否所有网点日缴存清空上报已经填写
		totalCount = daySetReportDao.findUnReportCount(parentPoint.getParent(), daySetApprove.getSettleDay(), "CSMS_CASHDEPOSITDAYSET");
		if(totalCount!=null&&totalCount>0){
			map.put("success", false);
			map.put("msg", "当天存在日结缴存未上报的网点，无法审批");
			return map;
		}
		//判断是否所有库存地日结上报已填写
		List<WareUntake> untakeWareList = daySetReportDao.findUntakeWareList(parentPoint.getStockPlace(), daySetApprove.getSettleDay());
		if(untakeWareList==null||untakeWareList.size()==0) {
			map.put("success", false);
			map.put("msg", "当天库存地产品日结信息没上报，无法审批");
			return map;
		}
		map.put("success", true);
		return map;
	}
	
	@Override
	public Boolean isDirector(Long operId){
		if(operId==null){
			return false;
		}
		List<Role> userRoleList = daySetReportDao.findUserRole(operId);
		if(userRoleList.isEmpty()){
			return false;
		}
		for(Role role:userRoleList){
			if(StringUtil.isEquals(role.getName(), "营业部主任")){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<WareUntake> findUntakeWareBySettleDay(Pager pager, CusPointPoJo cusPoint, String settleDay) {
		CustomPoint parentPoint = daySetReportDao.findCurrentPointById(cusPoint.getCusPoint());
		if(parentPoint==null){
			return null;
		}
		return daySetReportDao.findUntakeWareListBySettleDay(parentPoint.getStockPlace(), settleDay);
	}
	
	@Override
	public String findUntakeNextDay(CusPointPoJo cusPoint) throws ParseException{
		CustomPoint parentPoint = daySetReportDao.findCurrentPointById(cusPoint.getCusPoint());
		WareUntake wareUntake = daySetReportDao.findLastUntake(parentPoint.getStockPlace());
		if(wareUntake==null) {
			return DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		}else {
			return DateUtil.formatDate(DateUtil.addDay(DateUtil.toDate(wareUntake.getSettleDay(), "yyyy-MM-dd"), 1), "yyyy-MM-dd");
		}
	}
	
	@Override
	public CustomPoint findCustomPointById(Long placeId) {
		return daySetReportDao.findCurrentPointById(placeId);
	}

	@Override
	public DaySetApprove findDaySetApprove(Long netId, String settleDay) {
		return daySetReportDao.findDaySetApproveByDate(settleDay, netId);
	}
	
	@Override
	public List<RemarkInfo> findWareMemoList(WareReportRecord wareReportRecord){
		 List<WareReportRecord> list = daySetReportDao.findWareReportByApproveId(wareReportRecord.getDaySetID(),
				wareReportRecord.getProductCode(),
				wareReportRecord.getProductState(), wareReportRecord.getProductSource(), wareReportRecord.getPlaceid());
		 if(list==null) {
			 return null;
		 }
		 WareReportRecord wareSolid = list.get(0);
		 RemarkInfo remarkInfo = new RemarkInfo();
		 remarkInfo.setTableName("CSMS_WAREREPORT_RECORD");
		 remarkInfo.setBusinessId(wareSolid.getId());
		 return daySetReportDao.findMemoList(remarkInfo);
	}
	public List<RemarkInfo> findUntakeWareMemoList(WareUntake wareUntake){
		WareUntake untakeWare = daySetReportDao.findUntakeWare(wareUntake.getDepId(),
				wareUntake.getSettleDay(),
				wareUntake.getProductCode(),
				wareUntake.getProductSource(),
				wareUntake.getProductState());
		if(untakeWare==null) {
			return null;
		}
		RemarkInfo remarkInfo = new RemarkInfo();
		remarkInfo.setTableName("CSMS_DAYSET_WAREUNTAKE");
		remarkInfo.setBusinessId(untakeWare.getId());
		return daySetReportDao.findMemoList(remarkInfo);
	}

}
