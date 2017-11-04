package com.hgsoft.other.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.other.dao.ReceiptPrintDao;
import com.hgsoft.other.dao.ReceiptPrintDetailDao;
import com.hgsoft.other.dao.ReceiptPrintRecordDao;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.other.entity.ReceiptPrintDetail;
import com.hgsoft.other.entity.ReceiptPrintRecord;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.serviceInterface.IReceiptPrintService;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class ReceiptPrintService implements IReceiptPrintService {

	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());

	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ReceiptPrintDao receiptPrintDao;
	@Resource
	private ReceiptPrintDetailDao receiptPrintDetailDao;
	@Resource
	private ReceiptPrintRecordDao receiptPrintRecordDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	@Resource
	private AccardBussinessDao accardBussinessDao;

	@Override
	public Pager findAllByCustomerId(Pager pager, Long id) {

		return receiptPrintDao.findAllByCustomerId(pager, id);
	}

	@Override
	public ReceiptPrint findById(Long id) {
		return receiptPrintDao.findById(id);
	}

	@Override
	public void updateEndTime(Long id) {
		try {
			receiptPrintDao.updateEndTime(id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"更新回执打印主表结束时间成败");
			throw new ApplicationException();
		}
	}

	@Override
	public void addReceiptPrintDetail(Long customerId, ReceiptPrint receiptPrint) {
		try {
			// 打印若取消或者没有进行下去的话，明细表中将会存在相关的记录，这里将其删除。
			String sql = "delete from CSMS_ReceiptPrint_Detail where mainid = " + receiptPrint.getId();
			receiptPrintDetailDao.delete(sql);

			// 将对应的三个业务操作表相关内容写入回执打印明细表
			PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
			prepaidCBussiness.setUserid(customerId);
			List<Map<String, Object>> prepaidCs = prepaidCBussinessDao.findAllByTime(receiptPrint);
			for (int i = 0; i < prepaidCs.size(); i++) {
				ReceiptPrintDetail receiptPrintDetail = new ReceiptPrintDetail();
				receiptPrintDetail.setMainId(receiptPrint.getId());
				receiptPrintDetail.setParentTypeCode(new Integer(1));// 大类：1代表储值卡
				receiptPrintDetail.setTypeCode(new Integer(prepaidCs.get(i).get("state").toString().trim() + ""));
				receiptPrintDetail.setBusinessId(new Long(prepaidCs.get(i).get("id").toString() + ""));
				if (prepaidCs.get(i).get("cardNo") != null) {
					receiptPrintDetail.setCardTagNo(prepaidCs.get(i).get("cardNo").toString() + "");
				}
				if (prepaidCs.get(i).get("oldCardNo") != null) {
					receiptPrintDetail.setOldCardTagNo(prepaidCs.get(i).get("oldCardNo").toString() + "");
				}
				if (prepaidCs.get(i).get("realPrice") != null) {
					receiptPrintDetail.setRealPrice(new BigDecimal(prepaidCs.get(i).get("realPrice").toString() + ""));
				}
				receiptPrintDetail.setId(sequenceUtil.getSequenceLong("SEQ_CSMSReceiptPrintDetail"));
				receiptPrintDetailDao.save(receiptPrintDetail);
			}

			AccountCBussiness accountCBussiness = new AccountCBussiness();
			accountCBussiness.setUserId(customerId);
			List<Map<String, Object>> accountCs = accountCBussinessDao.findAllByTime(receiptPrint);
			for (int i = 0; i < accountCs.size(); i++) {
				ReceiptPrintDetail receiptPrintDetail = new ReceiptPrintDetail();
				receiptPrintDetail.setMainId(receiptPrint.getId());
				receiptPrintDetail.setParentTypeCode(new Integer(2));// 大类：2代表记帐卡
				receiptPrintDetail.setTypeCode(new Integer(accountCs.get(i).get("state").toString().trim() + ""));
				receiptPrintDetail.setBusinessId(new Long(accountCs.get(i).get("id").toString()));
				if (accountCs.get(i).get("cardNo") != null) {
					receiptPrintDetail.setCardTagNo(accountCs.get(i).get("cardNo").toString() + "");
				}
				if (accountCs.get(i).get("oldCardNo") != null) {
					receiptPrintDetail.setOldCardTagNo(accountCs.get(i).get("oldCardNo").toString() + "");
				}
				if (accountCs.get(i).get("realPrice") != null) {
					// receiptPrintDetail.setRealPrice(new
					// BigDecimal(accountCs.get(i).get("realPrice").toString()+""));
					receiptPrintDetail.setRealPrice(new BigDecimal(accountCs.get(i).get("realPrice").toString() + ""));
				}
				receiptPrintDetail.setId(sequenceUtil.getSequenceLong("SEQ_CSMSReceiptPrintDetail"));
				receiptPrintDetailDao.save(receiptPrintDetail);
			}

			TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
			tagBusinessRecord.setClientID(customerId);
			List<Map<String, Object>> tags = tagBusinessRecordDao.findAllByTime(receiptPrint);
			for (int i = 0; i < tags.size(); i++) {
				ReceiptPrintDetail receiptPrintDetail = new ReceiptPrintDetail();
				receiptPrintDetail.setMainId(receiptPrint.getId());
				receiptPrintDetail.setParentTypeCode(new Integer(3));// 大类：3代表电子标签
				receiptPrintDetail.setTypeCode(new Integer(tags.get(i).get("businessType").toString().trim() + ""));
				receiptPrintDetail.setBusinessId(new Long(tags.get(i).get("id").toString() + ""));
				receiptPrintDetail.setCardTagNo(tags.get(i).get("tagNo").toString() + "");
				receiptPrintDetail.setId(sequenceUtil.getSequenceLong("SEQ_CSMSReceiptPrintDetail"));
				if(tags.get(i).get("realPrice") != null){
					receiptPrintDetail.setRealPrice(new BigDecimal(tags.get(i).get("realPrice").toString() + ""));
				}
				receiptPrintDetailDao.save(receiptPrintDetail);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"更新回执打印主表结束时间失败");
			throw new ApplicationException();
		}

	}

	@Override
	public List<Map<String, Object>> findDetailsByTypeAndTime(ReceiptPrint receiptPrint) {
		return receiptPrintDetailDao.findDetailsByTypeAndTime(receiptPrint);
	}

	@Override
	public ReceiptPrint findByCustomerIdAndTime(Customer customer) {
		return receiptPrintDao.findByCustomerIdAndTime(customer);
	}
	
	@Override
	public ReceiptPrint findByCustomerIdAndBeginTime(Customer customer) {
		return receiptPrintDao.findByCustomerIdAndTime(customer);
	}

	@Override
	public void addReceiptPrint(ReceiptPrint receiptPrint) {
		receiptPrint.setId(sequenceUtil.getSequenceLong("SEQ_CSMSReceiptPrint"));
		receiptPrintDao.save(receiptPrint);
	}

	@Override
	public void updateReceiptPrint(ReceiptPrint receiptPrint) {
		receiptPrintDao.update(receiptPrint);
	}

	@Override
	public void updatePrintNum(ReceiptPrint receiptPrint,ReceiptPrintRecord receiptPrintRecord) {
		try {
			//更新打印次数
			receiptPrintDao.updatePrintNum(receiptPrint);
			receiptPrintRecord.setId(sequenceUtil.getSequenceLong("SEQ_CSMSReceiptPrintRecord_NO"));
			receiptPrintRecordDao.save(receiptPrintRecord);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"更新回执打印次数失败");
			throw new ApplicationException();
		}
	}

	@Override
	public AccountCBussiness findAccountCBusinessById(long id) {
		return accountCBussinessDao.findById(id);
	}

	@Override
	public TagBusinessRecord findTagBusinessById(long id) {
		return tagBusinessRecordDao.findById(id);
	}

	/**
	 * 联营卡查询回执打印
	 * @author gaosilign
	 * 2016-5-5 15:49:20
	 */
	@Override
	public Pager findByAssociate(Pager pager,Date starTime ,Date endTime,String no,String type,Long id) {
		try {
			//更新打印次数
			return tagBusinessRecordDao.findByAssociate(pager, starTime, endTime, no, type, id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询回执打印失败");
			throw new ApplicationException();
		}
	}

	/**
	 * 联营卡查询回执打印
	 * @author gaosilign
	 * 2016-5-5 15:49:20
	 */
	@Override
	public AccardBussiness findAccardBussinessById(Long id) {
		try {
			//更新打印次数
			return accardBussinessDao.findById(id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"查询回执打印失败");
			throw new ApplicationException();
		}
	}

	@Override
	public void updatePrintServiceNum(String type, Long businessId) {
		try {
			if("1".equals(type)){
				accardBussinessDao.updatePrintTimes(businessId);
			}else if("2".equals(type)){
				tagBusinessRecordDao.updatePrintTimes(businessId);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"更新回执打印次数失败");
			throw new ApplicationException();
		}
	}


}
