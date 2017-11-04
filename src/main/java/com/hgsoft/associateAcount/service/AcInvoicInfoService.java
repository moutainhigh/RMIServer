package com.hgsoft.associateAcount.service;

import com.hgsoft.associateAcount.dao.AcInvoicInfoDao;
import com.hgsoft.associateAcount.dao.AcInvoicInfoHisDao;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.entity.AcInvoicInfo;
import com.hgsoft.associateAcount.entity.AcInvoicInfoHis;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.serviceInterface.IAcInvoicInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @FileName AcInvoicInfoService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月4日 下午2:08:54 
*/
@Service
public class AcInvoicInfoService implements IAcInvoicInfoService {
	
	private static Logger logger = Logger.getLogger(ReqInfoService.class
			.getName());
	
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private AcInvoicInfoDao acInvoicInfoDao;
	@Resource
	private AccardBussinessDao accardBussinessDao;
	@Resource
	private AcInvoicInfoHisDao acInvoicInfoHisDao;

	@Override
	public Pager findByPager(Pager pager, AcInvoicInfo acInvoicInfo) {
		try {
			return acInvoicInfoDao.findByPager(pager, acInvoicInfo);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"发票邮寄服务管理-查询失败");
			throw new ApplicationException("发票邮寄服务管理-查询失败");
		}
	}

	@Override
	public List<Map<String, Object>> timeValidByAcCode(AcInvoicInfo acInvoicInfo) {
		return acInvoicInfoDao.timeValidByAcCode(acInvoicInfo);
	}

	@Override
	public void save(AcInvoicInfo acInvoicInfo) {
		try {
			//记录发票邮件服务表
			acInvoicInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACINVOICINFO_NO"));
			acInvoicInfoDao.save(acInvoicInfo);
			//记录联营卡业务记录表，业务类型为发票邮寄登记
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO"));
			accardBussiness.setCustomerId(acInvoicInfo.getMainId());
			accardBussiness.setCardNo(acInvoicInfo.getAccode());
			accardBussiness.setState("13");//发票邮寄登记
			accardBussiness.setRealPrice(new BigDecimal("0"));//业务费用
			accardBussiness.setTradeTime(new Date());//操作时间
			accardBussiness.setLastState("0");//卡片最后状态?
			accardBussiness.setMemo("发票邮寄登记");
			accardBussiness.setOperId(acInvoicInfo.getOpId());
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setPlaceId(acInvoicInfo.getPlaceId());
			accardBussinessDao.save(accardBussiness);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"发票邮寄服务管理-登记服务失败");
			throw new ApplicationException("发票邮寄服务管理-登记服务失败");
		}
	}

	@Override
	public void saveCancelById(AcInvoicInfo acInvoicInfo) {
		try {
			//将当前邮寄服务移除至历史表，生成原因为删除
			AcInvoicInfoHis acInvoicInfoHis = new AcInvoicInfoHis();
			acInvoicInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACINVOICINFOHIS_NO"));
			acInvoicInfoHis.setCreateReason("1");
			acInvoicInfoHisDao.save(acInvoicInfoHis,acInvoicInfo);
			acInvoicInfoDao.delete(acInvoicInfo);
			//记录联营卡业务记录表，记录业务类型为发票邮寄取消
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO"));
			accardBussiness.setCustomerId(acInvoicInfo.getMainId());
			accardBussiness.setCardNo(acInvoicInfo.getAccode());
			accardBussiness.setState("14");//发票邮寄取消
			accardBussiness.setRealPrice(new BigDecimal("0"));//业务费用
			accardBussiness.setTradeTime(new Date());//操作时间
			accardBussiness.setLastState("0");//卡片最后状态?
			accardBussiness.setMemo("发票邮寄取消");
			accardBussiness.setOperId(acInvoicInfo.getOpId());
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setPlaceId(acInvoicInfo.getPlaceId());
			accardBussinessDao.save(accardBussiness);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"发票邮寄服务管理-取消服务失败");
			throw new ApplicationException("发票邮寄服务管理-取消服务失败");
		}
	}

	@Override
	public AcInvoicInfo findById(Long id) {
		return acInvoicInfoDao.findById(id);
	}

}
