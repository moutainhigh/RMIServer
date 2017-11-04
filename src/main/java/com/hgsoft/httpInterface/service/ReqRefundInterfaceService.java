package com.hgsoft.httpInterface.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hgsoft.account.dao.AccountFundChangeDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.clearInterface.dao.CardObuDao;
import com.hgsoft.clearInterface.entity.PrepaidCBalance;
import com.hgsoft.common.Enum.AccChangeTypeEnum;
import com.hgsoft.common.Enum.RefundAuditStatusEnum;
import com.hgsoft.common.Enum.RefundTypeEnum;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.serviceInterface.IReqRefundInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class ReqRefundInterfaceService implements IReqRefundInterfaceService{
	@Resource
	private IUnifiedInterface unifiedInterface;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private AccountFundChangeDao accountFundChangeDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private RefundInfoHisDao refundInfoHisDao;
	@Resource
	private CardObuDao cardObuDao;
	@Resource
	SequenceUtil sequenceUtil;
	
	private static Logger logger = Logger.getLogger(ReqRefundInterfaceService.class.getName());

	/*@Override
	public boolean requestRefund(MainAccountInfo mainAccountInfo,ReqRefundRecord reqRefundRecord) {
		mainAccountInfo = mainAccountInfoDao.findByMainId(mainAccountInfo.getMainId());
		//1、将单个用户的整笔可退余额扣到退款审批余额。
		BigDecimal currentRefundBalance = mainAccountInfo.getAvailableRefundBalance();//保存此次操作的可退余额。。这样是否会有隐患？
		mainAccountInfoDao.updateAvRefundToRefundApprove(mainAccountInfo.getId());
		
		//2、判断退款申请记录表中是否存在该客户的数据
		List<Map<String, Object>> listRecord = reqRefundRecordDao.findByMainID(mainAccountInfo.getMainId());
		if(listRecord.size()==0){
			//如果没有数据，即此客户是首次退款申请，查找账户资金流水表的第一条“本次变动账户可退款余额不为0”到最后一条。
			List<AccountFundChange> accountFundChanges = accountFundChangeDao.findByMainIDAndZero(mainAccountInfo.getId());
			if(accountFundChanges.size()>0){
				//要记录退款申请表
				Long reqRefundRecordId = sequenceUtil.getSequenceLong("SEQ_CSMSReqRefundRecord_NO");
				//其他属性在action已经set
				reqRefundRecord.setId(reqRefundRecordId);
				reqRefundRecord.setCurrentRefundBalance(currentRefundBalance); 
				
				List<ReqRefundDetail> reqRefundDetails = new ArrayList<ReqRefundDetail>();//封装退款明细表记录
				for(int i=0;i<accountFundChanges.size();i++){
					//记录时间戳
					if(i==0){
						reqRefundRecord.setStartTime(accountFundChanges.get(i).getChgDate());
					}else if(i==accountFundChanges.size()-1){
						reqRefundRecord.setEndTime(accountFundChanges.get(i).getChgDate());
					}
					//
					ReqRefundDetail reqRefundDetail = new ReqRefundDetail();
					Long reqRefundDetailId = sequenceUtil.getSequenceLong("SEQ_CSMSReqRefundDetail_NO");
					reqRefundDetail.setId(reqRefundDetailId);
					reqRefundDetail.setMainID(reqRefundRecord.getId());
					reqRefundDetail.setSingleBeRefundBalance(accountFundChanges.get(i).getBeforeAvailableRefundBalance());
					reqRefundDetail.setSingleRefundBalance(accountFundChanges.get(i).getCurrAvailableRefundBalance());
					reqRefundDetail.setSingleAfRefundBalance(accountFundChanges.get(i).getAfterAvailableRefundBalance());
					reqRefundDetail.setRefundType(accountFundChanges.get(i).getChangeType());
					
					reqRefundDetails.add(reqRefundDetail);
				}
				//保存退款申请记录
				reqRefundRecordDao.save(reqRefundRecord);
				//批量保存退款申请明细表
				reqRefundDetailDao.batchSaveReqRefundDetail(reqRefundDetails);
			}
		}
		
		
		return false;
	}*/
	@Override
	public String saveRequestRefundApp(String param){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			JSONObject paramJson = (JSONObject) JSONObject.fromObject(param).get("param");
			JSONArray array = null;
			if(paramJson!=null){
				array = (JSONArray) paramJson.get("resultList");
			}else{
				return "参数param找不到";
			}
			
			List<Map> list = JSON.parseArray(array.toString(),Map.class);
			if(list !=null && !list.isEmpty()){
				StringBuffer errorMsg = new StringBuffer("");
				int errorCount = 0;
				JSONObject json = new JSONObject();
				StringBuffer refundId = new StringBuffer("");
				int i = 1;
				//获取sql拼接字符串
				for (Map<String, String> map : list) {
					Map<String, Object> refundInfo = null;
					if(map.get("refundId")!=null){
						refundInfo = refundInfoDao.findRefundInfoByID(Long.parseLong(map.get("refundId")));
					}else{
						errorMsg.append(","+i+":传入的数据不全");
						errorCount++;
						i++;
						continue;
					}
					
					if (map.get("approverId")==null
							||map.get("approverNo")==null||map.get("approverName")==null
							||map.get("appState")==null||!StringUtil.isNotBlank(paramJson.getString("appTime"))) {
						errorMsg.append(","+i+":传入的数据不全");
						errorCount++;
					}else if(!RefundAuditStatusEnum.operView.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.financeRefund.getValue().equals(map.get("appState"))
							&&!RefundAuditStatusEnum.financeRefundFail.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.settleDone.getValue().equals(map.get("appState"))
							&&!RefundAuditStatusEnum.omsAppNotPass.getValue().equals(map.get("appState"))){
						errorMsg.append(","+i+":传入的审批状态不正确");
						errorCount++;
					}else if(refundInfo!=null){
						/*if((RefundAuditStatusEnum.operView.getValue().equals(map.get("appState"))
								&&!RefundAuditStatusEnum.request.getValue().equals(refundInfo.get("AuditStatus"))&&!RefundAuditStatusEnum.financeRefundFail.getValue().equals(refundInfo.get("AuditStatus")))
								||(RefundAuditStatusEnum.financeRefund.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.operView.getValue().equals(refundInfo.get("AuditStatus")))
								||(RefundAuditStatusEnum.financeRefundFail.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.operView.getValue().equals(refundInfo.get("AuditStatus")))){
								errorMsg.append(","+i+":不能对此退款记录作此次业务");
								errorCount++;
							}*/
						//判断是否能将原本状态修改为审批状态
						if(RefundTypeEnum.preCardCancel.getValue().equals((String)refundInfo.get("RefundType"))){
							//如果是储值卡终止使用退款申请
							if(RefundAuditStatusEnum.settleDone.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.waitSettle.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能结算中心处理");
								errorCount++;
							}
							if(RefundAuditStatusEnum.operView.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.settleDone.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能营运中心审核");
								errorCount++;
							}
							if((RefundAuditStatusEnum.financeRefund.getValue().equals(map.get("appState")) || RefundAuditStatusEnum.financeRefundFail.getValue().equals(map.get("appState"))) && !RefundAuditStatusEnum.operView.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能财务退款成功或财务退款失败");
								errorCount++;
							}
						}else if(RefundTypeEnum.bailRefund.getValue().equals((String)refundInfo.get("RefundType"))){
							//保证金退款
							if(RefundAuditStatusEnum.operView.getValue().equals(map.get("appState")) 
									&& !RefundAuditStatusEnum.waitOmsApp.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能营运审批通过");
								errorCount++;
							}
							if(RefundAuditStatusEnum.omsAppNotPass.getValue().equals(map.get("appState")) 
									&& !RefundAuditStatusEnum.waitOmsApp.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能营运审批失败");
								errorCount++;
							}
							if(RefundAuditStatusEnum.financeRefund.getValue().equals(map.get("appState")) 
									&& !RefundAuditStatusEnum.operView.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能财务退款成功");
								errorCount++;
							}
							if(RefundAuditStatusEnum.financeRefundFail.getValue().equals(map.get("appState")) 
									&& !RefundAuditStatusEnum.operView.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能财务退款失败");
								errorCount++;
							}
						}else{
							if(RefundAuditStatusEnum.settleDone.getValue().equals(map.get("appState"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能结算中心处理");
								errorCount++;
							}
							
							if(RefundAuditStatusEnum.operView.getValue().equals(map.get("appState"))&&!RefundAuditStatusEnum.directorPass.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能营运中心审核");
								errorCount++;
							}
							if((RefundAuditStatusEnum.financeRefund.getValue().equals(map.get("appState")) || RefundAuditStatusEnum.financeRefundFail.getValue().equals(map.get("appState"))) && !RefundAuditStatusEnum.operView.getValue().equals(refundInfo.get("AuditStatus"))){
								errorMsg.append(","+i+":单号"+map.get("refundId")+"不能财务退款成功或财务退款失败");
								errorCount++;
							}
						}
						
						
					}else if(refundInfo==null){
						errorMsg.append(","+i+":退款记录不存在");
						errorCount++;
					}
					//审批状态为财务退款失败7就要传失败原因
					if(RefundAuditStatusEnum.financeRefundFail.getValue().equals(map.get("appState"))&&map.get("refundFailReason")==null){
						errorMsg.append(","+i+":财务退款失败请传入失败原因！");
						errorCount++;
					}
					
					refundId.append(","+map.get("refundId"));	
					
					i++;
				}
				
				//有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}
				//System.out.println("***** refundId:"+refundId.toString());
				//查询数据库
				/*Map<String, Map<String, Object>> refundMaps = refundInfoDao.findAllById(refundId.toString().substring(1));
				
				Map<String, Object> refundMap = null;
				i = 1;
				for (Map<String, String> map : list) {
					refundMap = refundMaps.get(map.get("refundId"));
					if (refundMap == null) {
						errorMsg.append(","+i+":退款记录不存在");
						errorCount++;
					}
					i = i+1;
				}
				//有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}*/
				
				
				//更新
				List<RefundInfo> refundInfoAppList = new ArrayList<RefundInfo>();
				List<RefundInfo> refundInfoCashRefundList = new ArrayList<RefundInfo>();
				List<RefundInfo> refundInfoCashRefundFailList = new ArrayList<RefundInfo>();
				List<RefundInfo> refundInfoSettleList = new ArrayList<RefundInfo>();
				List<RefundInfo> bailRefundOmsAppFailList = new ArrayList<RefundInfo>(); 
				//List<RefundInfoHis> refundInfoHisList = new ArrayList<RefundInfoHis>();
				for (Map<String, Object> map : list) {
					//设置传入的参数值
					RefundInfo refund = refundInfoDao.findById(Long.parseLong(map.get("refundId").toString()));
					refund.setAuditStatus(map.get("appState").toString());
					//3：营运中心审核4：财务退款完成；7：财务退款失败；9结算中心处理完成
					if(refund.getAuditStatus().equals(RefundAuditStatusEnum.operView.getValue())){
						refund.setAuditId(Long.parseLong(map.get("approverId").toString()));
						refund.setAuditNo(map.get("approverNo").toString());
						refund.setAuditName(map.get("approverName").toString());
						refund.setAuditTime(format.parse(paramJson.getString("appTime")));
						refundInfoAppList.add(refund);
					}else if(refund.getAuditStatus().equals(RefundAuditStatusEnum.financeRefund.getValue())){
						refund.setRefundId(Long.parseLong(map.get("approverId").toString()));
						refund.setRefundNo(map.get("approverNo").toString());
						refund.setRefundName(map.get("approverName").toString());
						refund.setRefundTime(format.parse(paramJson.getString("appTime")));
						refundInfoCashRefundList.add(refund);
						if(refund.getRefundType()!=null&&StringUtil.isEquals(refund.getRefundType(), "1")) {
							PrepaidCBalance prepaidCBalance = new PrepaidCBalance( refund.getCardNo(), refund.getCurrentRefundBalance(), new BigDecimal(0),
									 new Date(), "", "储值卡注销退款成功", new Date());
							cardObuDao.saveCardBalance(prepaidCBalance);
						}
						
					}else if(refund.getAuditStatus().equals(RefundAuditStatusEnum.financeRefundFail.getValue())){
						refund.setRefundFailReason(map.get("refundFailReason").toString());
						refund.setRefundId(Long.parseLong(map.get("approverId").toString()));
						refund.setRefundNo(map.get("approverNo").toString());
						refund.setRefundName(map.get("approverName").toString());
						//退款失败不用设置退款时间
						//2017-08-22 需求又说退款失败也要设置退款时间
						refund.setRefundTime(format.parse(paramJson.getString("appTime")));
						refundInfoCashRefundFailList.add(refund);
					}else if(refund.getAuditStatus().equals(RefundAuditStatusEnum.settleDone.getValue())){
						//结算中心处理完成
						refund.setSettleId(Long.parseLong(map.get("approverId").toString()));
						refund.setSettleNo(map.get("approverNo").toString());
						refund.setSettleName(map.get("approverName").toString());
						refund.setSettleTime(format.parse(paramJson.getString("appTime")));
						//
						//if(map.get("personalCorrectAmt") != null) refund.setPersonalCorrectAmt((BigDecimal)map.get("personalCorrectAmt"));
						refundInfoSettleList.add(refund);
					}else if(refund.getAuditStatus().equals(RefundAuditStatusEnum.omsAppNotPass.getValue())){
						//保证金退款的营运审批失败
						refund.setAuditId(Long.parseLong(map.get("approverId").toString()));
						refund.setAuditNo(map.get("approverNo").toString());
						refund.setAuditName(map.get("approverName").toString());
						refund.setAuditTime(format.parse(paramJson.getString("appTime")));
						
						bailRefundOmsAppFailList.add(refund);
					}
					
				}
				//
				if(refundInfoAppList.size()>0){
					//更改金额，调用接口
					UnifiedParam unifiedParam = new UnifiedParam();
					unifiedParam.setRefundInfoList(refundInfoAppList);
					unifiedParam.setType(AccChangeTypeEnum.operationReview.getValue());
					MainAccountInfo mainAccountInfo = mainAccountInfoDao.findById(Long.parseLong(refundInfoAppList.get(0).getMainAccountId().toString()));
					unifiedParam.setMainAccountInfo(mainAccountInfo);
					if(unifiedInterface.saveAccAvailableBalance(unifiedParam)){
						//批量保存退款记录、历史。以及批量update主账户都在接口里面做了。
					}
				}
				if(refundInfoCashRefundList.size()>0){
					//更改金额，调用接口
					UnifiedParam unifiedParam = new UnifiedParam();
					unifiedParam.setRefundInfoList(refundInfoCashRefundList);
					unifiedParam.setType(AccChangeTypeEnum.financeRefund.getValue());
					MainAccountInfo mainAccountInfo = mainAccountInfoDao.findById(Long.parseLong(refundInfoCashRefundList.get(0).getMainAccountId().toString()));
					unifiedParam.setMainAccountInfo(mainAccountInfo);
					if(unifiedInterface.saveAccAvailableBalance(unifiedParam)){
						//批量保存退款记录、历史。以及批量update主账户都在接口里面做了。
					}
				}
				if(refundInfoCashRefundFailList.size()>0){
					//更改金额，调用接口
					UnifiedParam unifiedParam = new UnifiedParam();
					unifiedParam.setRefundInfoList(refundInfoCashRefundFailList);
					unifiedParam.setType(AccChangeTypeEnum.financeRefundFail.getValue());
					MainAccountInfo mainAccountInfo = mainAccountInfoDao.findById(Long.parseLong(refundInfoCashRefundFailList.get(0).getMainAccountId().toString()));
					unifiedParam.setMainAccountInfo(mainAccountInfo);
					if(unifiedInterface.saveAccAvailableBalance(unifiedParam)){
						//批量保存退款记录、历史。以及批量update主账户都在接口里面做了。
					}
				}
				if(refundInfoSettleList.size()>0){
					//结算中心处理完成。直接修改退款记录
					for(RefundInfo refund:refundInfoSettleList){
						//his
						RefundInfoHis refundInfoHis = new RefundInfoHis();
						Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfoHis_NO");
						refundInfoHis.setId(hisId);
						//refundInfoHis.setCreateDate(new Date());
						refundInfoHis.setCreateReason("修改");//1:修改
						refundInfoHisDao.saveHis(refundInfoHis, refund);
						//update -> refundInfoList（参数值已经设置，直接update）
						refund.setHisSeqId(refundInfoHis.getId());
						refundInfoDao.updateForRefundInterface(refund);
					}
				}
				
				if(bailRefundOmsAppFailList.size()>0){
					//更改金额，调用接口
					UnifiedParam unifiedParam = new UnifiedParam();
					unifiedParam.setRefundInfoList(bailRefundOmsAppFailList);
					unifiedParam.setType(AccChangeTypeEnum.bailRefundOmsAppFail.getValue());
					MainAccountInfo mainAccountInfo = mainAccountInfoDao.findById(Long.parseLong(bailRefundOmsAppFailList.get(0).getMainAccountId().toString()));
					unifiedParam.setMainAccountInfo(mainAccountInfo);
					if(unifiedInterface.saveAccAvailableBalance(unifiedParam)){
						//批量保存退款记录、历史。以及批量update主账户都在接口里面做了。
					}
				}
				
				return "true";
			}
			
			return "没有可处理的数据";
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，批量退款审批失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，批量退款审批失败");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "操作时间格式不正确";
		}
	}
	
	/**
	 * 客服提供给营运的退款审批查询列表
	 */
	@Override
	public Pager requestRefundList(Pager pager, String refundType, String refundApplyStartTime,
			String refundApplyEndTime, String auditStatus,Long refundId,String expireFlag,String queryFlag) {
		return refundInfoDao.FindRefundList(pager,refundType,refundApplyStartTime,
				refundApplyEndTime, auditStatus,refundId,expireFlag,queryFlag);
	}

	@Override
	public Map<String, Object> requestRefundInfo(Long refundId) {
		return refundInfoDao.findRefundInfoByID(refundId);
	}

	/**
	 * 修改银行信息资料（修改银行账号）
	 * @param refundId
	 * @param personalCorrectAmt
	 * @param finalRefundAmt
	 * @return
	 */
	@Override
	public String updateAmt(Long refundId, BigDecimal personalCorrectAmt,BigDecimal finalRefundAmt) {
		try {
			String message = "";
			RefundInfo refundInfo = refundInfoDao.findById(refundId);
			if(refundInfo == null){
				message = "数据不存在";
			}else{
				RefundInfoHis refundInfoHis = new RefundInfoHis();
				Long hisID = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfoHis_NO");
				refundInfoHis.setId(hisID);
				refundInfoHis.setCreateReason("修改");//1:修改
				
				//his
				refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
				//update
				if(personalCorrectAmt != null) refundInfo.setPersonalCorrectAmt(personalCorrectAmt);
				if(finalRefundAmt != null) refundInfo.setFinalRefundAmt(finalRefundAmt);
				refundInfo.setHisSeqId(refundInfoHis.getId());
				refundInfoDao.updateForRefundInterface(refundInfo);
				
				message = "true";
			}
			return message;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，修改金额失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，修改金额失败");
		}
	}

}
