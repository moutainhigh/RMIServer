package com.hgsoft.account.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.OtherBussinessTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.httpInterface.dao.SpecialCostSubclassDao;
import com.hgsoft.httpInterface.dao.SpecialCostTypeDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.other.SpecialCostReceipt;
import com.hgsoft.system.dao.ParamConfigDao;
import com.hgsoft.system.entity.SpecialCostSubclass;
import com.hgsoft.system.entity.SpecialCostType;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.ReceiptUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.hgsoft.account.dao.AccountBussinessDao;
import com.hgsoft.account.dao.AccountFundChangeDao;
import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoHisDao;
import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.MainAccountInfoHis;
import com.hgsoft.account.serviceInterface.IMainAccountInfoService;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.JdbcUtil;
import com.hgsoft.utils.SequenceUtil;

/**
 * 
 * @ClassName: MainAccountInfoService
 * @Description: 框架DEMO类
 * @author gaosiling
 * @date 2016年1月14日08:19:57
 */
@Service
public class MainAccountInfoService implements IMainAccountInfoService {

	@Resource
	JdbcUtil jdbcUtil;

	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private MainAccountInfoHisDao mainAccountInfoHisDao;
	@Resource
	private AccountFundChangeDao accountFundChangeDao;
	@Resource
	private ParamConfigDao paramConfigDao;
	@Resource
	private ReceiptDao receiptDao;

	@Resource
	SequenceUtil sequenceUtil;

	private static Logger logger = Logger
			.getLogger(MainAccountInfoService.class.getName());

	@SuppressWarnings("unchecked")
	public List<MainAccountInfo> list() {
		return mainAccountInfoDao.findAllMainAccountInfo();
	}

	public void updateMainAccountInfo(MainAccountInfo admin) {
		try {
			mainAccountInfoDao.update(admin);
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "更新主账户信息失败");
			e.printStackTrace();
			throw new ApplicationException();
		}

	}

	public MainAccountInfo findByMainId(Long id) {
		return mainAccountInfoDao.findByMainId(id);
	}

	@Override
	public void specialCostSave(MainAccountInfo mainAccountInfo, BigDecimal  beforeBalance, BigDecimal  beforeAvailableBalance, SysAdmin sysAdmin, Customer customer, CusPointPoJo cusPointPoJo, SpecialCostType specialCostType, SpecialCostSubclass specialCostSubclass, String charge, Map<String,Object> params) {
		// 新增账户资金变动流水
		AccountFundChange accountFundChange = new AccountFundChange();
		accountFundChange = getBeforeAccountFundChange(mainAccountInfo.getId(),accountFundChange);
		accountFundChange.setChangeType("47");// 对应资金变动业务操作类型即type
		accountFundChange.setMemo("特殊费用收取");

		accountFundChange.setCurrAvailableBalance(mainAccountInfo.getAvailableBalance());
		accountFundChange.setOperNo(sysAdmin.getStaffNo());
		accountFundChange.setOperName(sysAdmin.getUserName());
		accountFundChange.setPlaceNo(cusPointPoJo.getCusPointCode());
		accountFundChange.setPlaceName(cusPointPoJo.getCusPointName());
		accountFundChange.setAfterBalance(new BigDecimal(0).subtract(mainAccountInfo.getBalance()));
		accountFundChange.setAfterAvailableBalance(new BigDecimal(0).subtract(mainAccountInfo.getAvailableBalance()));
		accountFundChange.setBeforeBalance(mainAccountInfo.getBalance());
		accountFundChange.setBeforeAvailableBalance(mainAccountInfo.getAvailableBalance());

		//账户历史
		MainAccountInfoHis mainAccountInfoHis = new MainAccountInfoHis();
		BigDecimal SEQ_CSMSMAINACCOUNTINFOHIS_NO = sequenceUtil.getSequence("SEQ_CSMSMAINACCOUNTINFOHIS_NO");
		mainAccountInfoHis.setId(Long.valueOf(SEQ_CSMSMAINACCOUNTINFOHIS_NO.toString()));
		mainAccountInfoHis.setHisSeqId(mainAccountInfo.getHisSeqId());
		mainAccountInfoHis.setCreateReason("特殊费用收取");

		try {

			mainAccountInfo.setBalance(beforeBalance.add(mainAccountInfo.getBalance()));
			mainAccountInfo.setAvailableBalance(beforeAvailableBalance.add(mainAccountInfo.getAvailableBalance()));
			mainAccountInfoDao.specialCostSave(mainAccountInfo);
			mainAccountInfo.setBalance(mainAccountInfo.getBalance().subtract(beforeBalance));
			mainAccountInfo.setAvailableBalance(mainAccountInfo.getAvailableBalance().subtract(beforeAvailableBalance));

			mainAccountInfoHisDao.saveHis(mainAccountInfoHis, mainAccountInfo);
			accountFundChangeDao.saveChange(accountFundChange);

			//特殊费用收取回执
			SpecialCostReceipt specialCostReceipt = new SpecialCostReceipt();
			specialCostReceipt.setTitle("特殊费用收取回执");
			specialCostReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			specialCostReceipt.setCustomerSecondNo(customer.getSecondNo());
			specialCostReceipt.setCustomerSecondName(customer.getSecondName());
			specialCostReceipt.setSpecialCostType(specialCostType.getCategoryName());
			specialCostReceipt.setSpecialCostSubType(specialCostSubclass.getCategoryName());
			specialCostReceipt.setCharge(NumberUtil.get2Decimal(new Double(charge)*0.01));
			specialCostReceipt.setCustomerNo(customer.getUserNo());
			specialCostReceipt.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
			specialCostReceipt.setCustomerIdCode(customer.getIdCode());
			specialCostReceipt.setCustomerName(customer.getOrgan());

			Receipt receipt = new Receipt();
			receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.other.getValue());
			receipt.setTypeCode(OtherBussinessTypeEnum.specialCost.getValue());
			receipt.setTypeChName(OtherBussinessTypeEnum.specialCost.getName());
			receipt.setCreateTime(new Date());
			receipt.setPlaceId(cusPointPoJo.getCusPoint());
			receipt.setPlaceNo(cusPointPoJo.getCusPointCode());
			receipt.setPlaceName(cusPointPoJo.getCusPointName());
			receipt.setOperId(sysAdmin.getId());
			receipt.setOperNo(sysAdmin.getStaffNo());
			receipt.setOperName(sysAdmin.getUserName());
			receipt.setOrgan(customer.getOrgan());
			receipt.setContent(JSONObject.fromObject(specialCostReceipt).toString());
			this.receiptDao.saveReceipt(receipt);

		} catch (Exception e) {
			logger.error("特殊费用收取失败："+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	private String getFlowNo(){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		int randomNum1 = (int)((Math.random()*9+1)*10000000);
		int randomNum2 = (int)((Math.random()*9+1)*10000000);
		String flowNo=fomat.format(date)+randomNum1+randomNum2;
		return flowNo;
	}
	private AccountFundChange getBeforeAccountFundChange(Long mainAccountInfoId,AccountFundChange accountFundChange){
		BigDecimal SEQ_CSMSAccountFundChange_NO = sequenceUtil.getSequence("SEQ_CSMSAccountFundChange_NO");
		accountFundChange.setId(Long.valueOf(SEQ_CSMSAccountFundChange_NO.toString()));
		accountFundChange.setFlowNo(getFlowNo());
		
		accountFundChange.setBeforeAvailableBalance(new BigDecimal("0"));
		accountFundChange.setBeforeFrozenBalance(new BigDecimal("0"));
		accountFundChange.setBeforepreferentialBalance(new BigDecimal("0"));
		accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("0"));
		
		accountFundChange.setMainId(mainAccountInfoId);
		
		accountFundChange.setCurrAvailableBalance(new BigDecimal("0"));
		accountFundChange.setCurrFrozenBalance(new BigDecimal("0"));
		accountFundChange.setCurrpreferentialBalance(new BigDecimal("0"));
		accountFundChange.setCurrAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setCurrRefundApproveBalance(new BigDecimal("0"));
		
		accountFundChange.setAfterAvailableBalance(new BigDecimal("0"));
		accountFundChange.setAfterFrozenBalance(new BigDecimal("0"));
		accountFundChange.setAfterpreferentialBalance(new BigDecimal("0"));
		accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setAfterRefundApproveBalance(new BigDecimal("0"));

		return accountFundChange;
	}

}
