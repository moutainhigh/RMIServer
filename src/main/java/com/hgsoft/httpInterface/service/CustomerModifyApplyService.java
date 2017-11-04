package com.hgsoft.httpInterface.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.dao.MaterialDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.CustomerModifyApplyDao;
import com.hgsoft.httpInterface.entity.CustomerModifyApply;
import com.hgsoft.httpInterface.serviceInterface.ICustomerModifyApplyService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class CustomerModifyApplyService implements ICustomerModifyApplyService {

	private static Logger logger = Logger.getLogger(AccountCInterfaceService.class.getName());
	@Resource
	private CustomerModifyApplyDao customerModifyApplyDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private MaterialDao materialDao;
	@Resource
	private CustomerHisDao customerHisDao;
	@Resource
	private SequenceUtil sequenceUtil;

	/** 查询客户信息修改审核列表
	 * @Descriptioqn:
	 * @param pager
	 * @return
	 * @author lgm
	 * @date 2017年3月15日
	 */
	@Override
	public Pager list(Pager pager, String oldOrgan, String newOrgan,String oldIdType, String newIdType, String oldIdCode,String newIdCode,String appState,String approverName,String appTime,String createTime) {
		try {
			return customerModifyApplyDao.list(pager, oldOrgan, newOrgan, oldIdType, newIdType, oldIdCode, newIdCode, appState, approverName, appTime,createTime);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，客户信息修改审核列表查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，客户信息修改审核列表查询失败！");
		}
	}

	/** 查询客户信息修改审核详细信息
	 * @Descriptioqn:
	 * @param pager
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年3月15日
	 */
	@Override
	public Map<String,Object> detailList(Long id,String rootPath) {
		try {
			return customerModifyApplyDao.detailList(id,rootPath);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，客户信息修改审核详细信息查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，客户信息修改审核详细信息查询失败！");
		}
	}

	/** 客户信息修改审核
	 * @Descriptioqn:
	 * @param id
	 * @param appState
	 * @param approverId
	 * @param approverNo
	 * @param approverName
	 * @param appTime
	 * @return
	 * @author lgm
	 * @date 2017年3月15日
	 */
	@Override
	public boolean approval(Long id, String appState, Long approverId,String approverNo, String approverName, String appTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = format.parse(appTime);
			customerModifyApplyDao.approval(id, appState, approverId, approverNo, approverName, time);
			if("2".equals(appState)){
				CustomerModifyApply customerModifyApply = customerModifyApplyDao.findById(id);
				Customer oldCustomer = customerDao.findById(customerModifyApply.getCustomerId());
				
				//更新图片表code字段
//				if(!oldCustomer.getIdCode().equals(customerModifyApply.getNewIdCode())){
//					Material material = new Material();
//					material.setCustomerID(customerModifyApply.getCustomerId());
//					List<Material> materials = materialDao.findMateria(material);
//					for(Material m:materials){
//						m.setCode(customerModifyApply.getNewIdCode());
//						materialDao.updateMateria(m);
//					}
//				}
				
				//添加客户信息历史表
				CustomerHis customerHis = new CustomerHis(oldCustomer);
				BigDecimal Customer_his_NO = sequenceUtil.getSequence("SEQ_CSMS_Customer_his_NO");
				customerHis.setId(Long.valueOf(Customer_his_NO.toString()));
				customerHis.setGenReason("1");
				customerHis.setGenTime(new Date());
				customerHis.setHisSeqId(oldCustomer.getHisSeqId());
				customerHisDao.save(customerHis);
				
				//更新客户信息表
				oldCustomer.setIdType(customerModifyApply.getNewIdType());
				oldCustomer.setIdCode(customerModifyApply.getNewIdCode());
				oldCustomer.setOrgan(customerModifyApply.getNewOrgan());
				oldCustomer.setUpDateTime(new Date());
				oldCustomer.setHisSeqId(customerHis.getId());
				customerDao.update(oldCustomer);
			}
			return true;
		} catch (ApplicationException | ParseException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
			logger.error(e.getMessage()+"营运系统，客户信息修改审核失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，客户信息修改审核失败！");
		}
	}

}