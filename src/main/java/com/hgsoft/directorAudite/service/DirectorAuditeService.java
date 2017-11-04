package com.hgsoft.directorAudite.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.RefundInfoHisDao;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.common.Enum.RefundAuditStatusEnum;
import com.hgsoft.common.Enum.RefundTypeEnum;
import com.hgsoft.directorAudite.dao.DirectorAuditeDao;
import com.hgsoft.directorAudite.serviceInterface.IDirectorAuditeService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
@Service
public class DirectorAuditeService implements IDirectorAuditeService{
	private static Logger logger = Logger.getLogger(DirectorAuditeService.class.getName());
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private DirectorAuditeDao directorAuditeDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private RefundInfoHisDao refundInfoHisDao;
	
	@Override
	public Pager findDirectorAuditePager(Pager pager, String refundApplyStartTime, String refundApplyEndTime,
			String directorAuditStartTime, String directorAuditEndTime, RefundInfo refundInfo,Long bussinessPlaceId) {
		return directorAuditeDao.findDirectorAuditePager(pager, refundApplyStartTime, refundApplyEndTime, directorAuditStartTime, directorAuditEndTime, refundInfo,bussinessPlaceId);
	}

	@Override
	public Map<String, Object> findDirectorAuditeDetail(Long refundInfoId) {
		return directorAuditeDao.findDirectorAuditeDetail(refundInfoId);
	}

	@Override
	public Map<String, Object> saveRefundAudite(RefundInfo refundInfo) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			//his
			RefundInfoHis refundInfoHis = new RefundInfoHis();
			BigDecimal SEQ_CSMSRefundInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSRefundInfoHis_NO");
			refundInfoHis.setId(Long.valueOf(SEQ_CSMSRefundInfoHis_NO.toString()));
			refundInfoHis.setCreateReason("修改");
			refundInfoHisDao.saveHis(refundInfoHis, refundInfo);
			
			RefundInfo updateObj = refundInfoDao.findById(refundInfo.getId());
			//如果已过资金争议期、且主任审核通过，update waitSettleAuditTime（待结算/营运审核时间）
			if(RefundTypeEnum.preCardCancel.getValue().equals(updateObj.getRefundType()) && "0".equals(updateObj.getExpireFlag()) && refundInfo.getAuditStatus().equals(RefundAuditStatusEnum.directorPass.getValue())){
				updateObj.setWaitSettleAuditTime(new Date());
				updateObj.setAuditStatus(RefundAuditStatusEnum.waitSettle.getValue());
			}else if(RefundTypeEnum.preCardCancel.getValue().equals(updateObj.getRefundType()) && refundInfo.getAuditStatus().equals(RefundAuditStatusEnum.directorNotPass.getValue())){
				updateObj.setWaitSettleAuditTime(null);
				updateObj.setAuditStatus(refundInfo.getAuditStatus());
			}else if(RefundTypeEnum.bailRefund.getValue().equals(updateObj.getRefundType()) && "0".equals(updateObj.getExpireFlag()) && refundInfo.getAuditStatus().equals(RefundAuditStatusEnum.directorPass.getValue())){
				updateObj.setWaitSettleAuditTime(new Date());
				updateObj.setAuditStatus(RefundAuditStatusEnum.waitOmsApp.getValue());
			}else if(RefundTypeEnum.bailRefund.getValue().equals(updateObj.getRefundType()) && refundInfo.getAuditStatus().equals(RefundAuditStatusEnum.directorNotPass.getValue())){
				updateObj.setWaitSettleAuditTime(null);
				updateObj.setAuditStatus(refundInfo.getAuditStatus());
			}else {
				updateObj.setAuditStatus(refundInfo.getAuditStatus());
			}
			updateObj.setDirectorAuditId(refundInfo.getDirectorAuditId());
			updateObj.setDirectorAuditNo(refundInfo.getDirectorAuditNo());
			updateObj.setDirectorAuditName(refundInfo.getDirectorAuditName());
			updateObj.setDirectorAuditTime(refundInfo.getDirectorAuditTime());
			//updateObj.setAuditStatus(refundInfo.getAuditStatus());
			updateObj.setMemo(refundInfo.getMemo());
			//update
			refundInfo.setHisSeqId(refundInfoHis.getId());
			refundInfoDao.updateAll(updateObj);
			
			resultMap.put("result", "true");
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"主任审核失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		
		return resultMap;
	}
	
}
