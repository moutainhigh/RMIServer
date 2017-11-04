package com.hgsoft.customer.service;

import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.BillGetHisDao;
import com.hgsoft.customer.dao.CustomerBussinessDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.serviceInterface.IBillGetService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillGetService implements IBillGetService{
	
	@Resource
	private BillGetHisDao billGetHisDao;
	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());
	private BillGetDao billGetDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	public void setBillGetDao(BillGetDao billGetDao) {
		this.billGetDao = billGetDao;
	}
	@Resource
	SequenceUtil sequenceUtil;
	
	@Override
	public BillGet find(BillGet billGet) {
		return billGetDao.find(billGet);
	}

	@Override
	public void billGetUpdate(BillGet billGet) {
		billGetDao.update(billGet);
	}

	@Override
	public Pager findByPage(Pager pager, BillGet billGet, Customer customer) {
		return billGetDao.findByPage(pager, billGet, customer);
	}

	@Override
	public Pager findByPage(Pager pager, BillGet billGet) {
		return billGetDao.findByPage(pager, billGet);
	}

	@Override
	public BillGet find(Long id) {
		return billGetDao.findById(id);
	}

	@Override
	public List<Map<String, Object>> findAll(BillGet billGet) {
		return billGetDao.findAll(billGet);
	}

	@Override
	public List<Map<String, Object>> findAllByMainId(Long mainId) {
		return billGetDao.findAllByMainId(mainId);
	}

	@Override
	public void updateBillGet(BillGet billGet, String serTypes, String oldSerTypes) {
		billGet = find(billGet.getId());
		String[] serItemList = null;
		String[] oldSerItemList = null;
		Map<String, String> oldSerItemMap =  null;
		Map<String, String> serItemMap =  null;
		if (StringUtils.isNotBlank(serTypes) && serTypes.equals(oldSerTypes)) {
			return;
		}
		if (StringUtils.isNotBlank(serTypes)) {
			 serItemList = serTypes.split(";");
		}
		if (StringUtils.isNotBlank(oldSerTypes)) {
			oldSerItemList = oldSerTypes.split(";");
		}
		if (oldSerItemList != null) {
			oldSerItemMap = new HashMap<String, String>();
			for (String oldSerItem : oldSerItemList) {
				if (!oldSerItemMap.containsKey(oldSerItem)) {
					oldSerItemMap.put(oldSerItem, oldSerItem);
				}
			}
		}
		if (serItemList != null) {
			serItemMap = new HashMap<String, String>();
			for (String serItem : serItemList) {
				if (!serItemMap.containsKey(serItem)) {
					serItemMap.put(serItem, serItem);
				}
			}
		}
		if (oldSerItemMap != null && serItemList != null) {
			for (String serItem : serItemList) {
				if (oldSerItemMap.containsKey(serItem)) {
					continue;
				} else {
					billGet.setSerItem(serItem);
					BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
					billGet.setId(Long.parseLong(seq.toString()));
					billGetDao.save(billGet);
				}
				
			}
		}
		if (serItemMap != null && oldSerItemList != null) {
			Date date = new Date();
			for (String oldSerItem : oldSerItemList) {
				if (!serItemMap.containsKey(oldSerItem)) {
					BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_bill_get_his_NO");
					BillGetHis billGetHis = new BillGetHis(null, date, "1", billGet.getMainId(), billGet.getSerItem(), 
							billGet.getSerType(), billGet.getBegTime(), billGet.getEndTime(), billGet.getOperId(), 
							billGet.getPlaceId(), billGet.getHisSeqId());
					billGetHis.setId(Long.parseLong(seq.toString()));
					billGetHisDao.save(billGetHis);
					billGetDao.delete("mainId = " + billGet.getMainId() + " and serItem = '" + oldSerItem + "'");
				} 
			}
		}
	}

	@Override
	public void delete(String sql) {
		try{
			billGetDao.delete(sql);
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"取消服务失败");
			throw new ApplicationException();
		}
		
	}


	@Override
	public void save(List<BillGet> addBillGets) {
		try{
			for (int i = 0; i < addBillGets.size(); i++) {
				BigDecimal SEQ_CSMS_bill_get_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_NO");
				addBillGets.get(i).setId(Long.valueOf(SEQ_CSMS_bill_get_NO.toString()));
				billGetDao.save(addBillGets.get(i));
			}
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"新增服务失败");
			throw new ApplicationException();
		}
	}

	@Override
	public void updateCancelSer(List<BillGet> removeBillGets) {
		try{
			for (int i = 0; i < removeBillGets.size(); i++) {
				//删除服务记录
				BillGet temp = removeBillGets.get(i);
				String sql = "mainId = "+ temp.getMainId() + " and serItem = "+ temp.getSerItem();
				billGetDao.delete(sql);
				//新增一条历史记录
				BillGetHis billGetHis = getBillGetHis(temp);
				billGetHis.setGenReason("2");//删除一个服务记录
				BigDecimal bill_get_his_NO = sequenceUtil.getSequence("SEQ_CSMS_bill_get_his_NO");
				billGetHis.setId(Long.valueOf(bill_get_his_NO.toString()));
				billGetHisDao.save(billGetHis);
			}
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"取消服务失败");
			throw new ApplicationException();
		}
	}
	
	/**
	 * 得到一个历史对象
	 * @param billGet
	 * @return
	 */
	public BillGetHis getBillGetHis(BillGet billGet){
		BillGetHis billGetHis = new BillGetHis();
		billGetHis.setGenTime(new Date());
		billGetHis.setHisSeqId(billGet.getId());
		billGetHis.setMainId(billGet.getMainId());
		billGetHis.setSerItem(billGet.getSerItem());
		return billGetHis;
	}
	
	@Override
	public BillGet findByCardBankNo(String cardBankNo) {
		return billGetDao.findByCardBankNo(cardBankNo);
	}

	@Override
	public void updateSerItem(BillGet newBillGet) {
		CustomerBussiness customerBussiness = new CustomerBussiness();
		try{
			//保存历史表
			BillGet billGet = billGetDao.findById(newBillGet.getId());
			Date date = new Date();
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMS_bill_get_his_NO");
			BillGetHis billGetHis = new BillGetHis(null, date, "1", billGet.getMainId(), billGet.getSerItem(), 
					billGet.getSerType(), billGet.getBegTime(), billGet.getEndTime(), billGet.getOperId(), 
					billGet.getPlaceId(), billGet.getHisSeqId());
			billGetHis.setId(seq);
			billGetHis.setCardType(billGet.getCardType());
			billGetHis.setCardAccountID(billGet.getCardAccountID());
			billGetHis.setCardBankNo(billGet.getCardBankNo());
			billGetHis.setOperTime(billGet.getOperTime());
			billGetHisDao.save(billGetHis);
			customerBussiness.setBillHisId(billGet.getHisSeqId());
			//更新服务方式登记表
			billGet.setSerItem(newBillGet.getSerItem());
			billGet.setHisSeqId(billGetHis.getId());
			billGetDao.update(billGet);	
			
			
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			
			customerBussiness.setId(seq);
			customerBussiness.setCustomerId(newBillGet.getMainId());
			customerBussiness.setBillId(billGet.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.billUpdate.getValue());
			customerBussiness.setCardType(billGet.getCardType());
			customerBussiness.setCardAccountId(billGet.getCardAccountID());
			customerBussiness.setCardBankNo(billGet.getCardBankNo());
			customerBussiness.setSeritem(newBillGet.getSerItem());
			customerBussiness.setOperId(newBillGet.getOperId());
			customerBussiness.setOperNo(newBillGet.getOperNo());
			customerBussiness.setOperName(newBillGet.getOperName());
			customerBussiness.setPlaceId(newBillGet.getPlaceId());
			customerBussiness.setPlaceNo(newBillGet.getPlaceNo());
			customerBussiness.setPlaceName(newBillGet.getPlaceName());			
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);
			
			Customer customer = customerDao.findById(newBillGet.getMainId());
			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("115");//115信息服务修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(newBillGet.getOperId());
			serviceWater.setOperName(newBillGet.getOperName());
			serviceWater.setOperNo(newBillGet.getOperNo());
			serviceWater.setPlaceId(newBillGet.getPlaceId());
			serviceWater.setPlaceName(newBillGet.getPlaceName());
			serviceWater.setPlaceNo(newBillGet.getPlaceNo());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("自营客服系统：信息服务修改");
			
			serviceWaterDao.save(serviceWater);
			
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"服务方式更新失败");
			throw new ApplicationException();
		}
	}

}
