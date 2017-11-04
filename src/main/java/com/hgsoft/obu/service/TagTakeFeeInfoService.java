package com.hgsoft.obu.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.common.Enum.*;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.tag.TagTakefeeinfoAddReceipt;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.BankTransferBussinessDao;
import com.hgsoft.account.dao.BankTransferInfoDao;
import com.hgsoft.account.dao.BankTransferInfoHisDao;
import com.hgsoft.account.dao.VoucherDao;
import com.hgsoft.account.entity.BankTransferBussiness;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.BankTransferInfoHis;
import com.hgsoft.account.entity.Voucher;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoHisDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeFeeInfoHis;
import com.hgsoft.obu.serviceInterface.ITagTakeFeeInfoService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;

/**
 * 
 * @ClassName: TagTakeFeeInfoService
 * @Description: 电子标签提货金额登记  服务层
 * @author guanshaofeng
 * @date 2016年1月20日21:19:57
*/
@Service
public class TagTakeFeeInfoService implements ITagTakeFeeInfoService{
	
	private static Logger logger = Logger.getLogger(TagTakeFeeInfoService.class.getName());
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ReceiptDao receiptDao;
	private TagTakeFeeInfoDao tagTakeFeeInfoDao;
	private TagTakeFeeInfoHisDao tagTakeFeeInfoHisDao;
	
	@Resource
	private BankTransferInfoDao bankTransferInfoDao;
	
	@Resource
	private BankTransferInfoHisDao bankTransferInfoHisDao;
	
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	@Resource
	private BankTransferBussinessDao bankTransferBussinessDao;
	
	@Resource
	private VoucherDao voucherDao;

	
	@Resource
	public void setTagTakeFeeInfoDao(TagTakeFeeInfoDao tagTakeFeeInfoDao) {
		this.tagTakeFeeInfoDao = tagTakeFeeInfoDao;
	}
	
	@Resource
	public void setTagTakeFeeInfoHisDao(TagTakeFeeInfoHisDao tagTakeFeeInfoHisDao) {
		this.tagTakeFeeInfoHisDao = tagTakeFeeInfoHisDao;
	}

	@Resource
	private CustomerDao customerDao;

	@Override
	public List<TagTakeFeeInfo> tagTakeFeeInfoList(TagTakeFeeInfo tagTakeFeeInfo) {
		//System.out.println("11111");
		List<TagTakeFeeInfo> tagTakeFeeInfos = tagTakeFeeInfoDao.findAllTagTakeFeeInfos(tagTakeFeeInfo);
		
		
		return tagTakeFeeInfos;
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public Pager tagTakeFeeInfoListByPager(Pager pager,String registerName,String modifyName,
			TagTakeFeeInfo tagTakeFeeInfo,Date modifyStarTime,Date modifyEndTime,Date registStarTime,Date registEndTime) {
		
		return tagTakeFeeInfoDao.findAllTagTakeFeeInfosByPager(pager, registerName, modifyName, tagTakeFeeInfo, modifyStarTime, modifyEndTime, registStarTime, registEndTime);
	}

	
	
	@Override
	public void saveTagTakeFeeInfo(TagTakeFeeInfo tagTakeFeeInfo) {
		try {
			//添加id
			BigDecimal SEQ_CSMSTagTakeFeeInfo_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfo_NO");
			tagTakeFeeInfo.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfo_NO.toString()));
			Receipt receipt=new Receipt();
			receipt.setId(sequenceUtil.getSequenceLong("SEQ_CSMSRECEIPT_NO"));
			receipt.setParentTypeCode("4");
			receipt.setTypeChName("电子标签提货金额登记新增");
			receipt.setTypeCode(TagBussinessTypeEnum.tagtakefeeinfoAdd.getValue());//20标签提货登记金额新增
			tagTakeFeeInfo.setReceiptId(receipt.getId());
			tagTakeFeeInfoDao.save(tagTakeFeeInfo);
			receipt.setBusinessId(tagTakeFeeInfo.getHis_SeqID());
			receiptDao.save(receipt);
		} catch (ApplicationException e) {
			
			logger.error("保存电子标签提货金额的对象失败了"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("保存电子标签提货金额的对象失败了");
		}
		
	}
	
	@Override
	public Long saveAndReturnId(TagTakeFeeInfo tagTakeFeeInfo,BankTransferInfo bankTransferInfo,Map<String,Object> params) {
		try {
			if(3==Long.valueOf(tagTakeFeeInfo.getChargeType())){
				//将当前银行账户信息移入历史表
				BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
				BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
				bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
				bankTransferInfoHis.setCreateReason("电子标签提货金额登记后扣减余额");
				//更新银行账户信息余额
				bankTransferInfo.setHisSeqId(bankTransferInfoHis.getId());
				BigDecimal balance = bankTransferInfo.getBlanace();//先记录缴款前余额
				bankTransferInfo.setBlanace(new BigDecimal("-"+tagTakeFeeInfo.getTakeBalance()));
				bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);
				bankTransferInfoDao.update(bankTransferInfo);
				
				
				//2017/06/05 保存银行到账缴款业务记录表
				BankTransferBussiness bankTransferBussiness = new BankTransferBussiness();
				bankTransferBussiness.setIdType(tagTakeFeeInfo.getCertType());
				bankTransferBussiness.setIdCode(tagTakeFeeInfo.getCertNumber());
				bankTransferBussiness.setClientName(tagTakeFeeInfo.getClientName());
				bankTransferBussiness.setBankTransferId(bankTransferInfo.getId());
				bankTransferBussiness.setPayName(bankTransferInfo.getPayName());
				bankTransferBussiness.setBankNo(bankTransferInfo.getBankNo());
				bankTransferBussiness.setRechargeType(BankTransferRechargeTypeEnum.tagTakeFeeTransfer.getValue());
				bankTransferBussiness.setTransferBlanace(bankTransferInfo.getTransferBlanace());//银行到账金额
				bankTransferBussiness.setBlanace(balance.subtract(tagTakeFeeInfo.getTakeBalance()));//缴款后余额
				bankTransferBussiness.setRechargeCost(tagTakeFeeInfo.getTakeBalance());//缴款金额
				bankTransferBussiness.setOperId(tagTakeFeeInfo.getRegisterOperID());
				bankTransferBussiness.setOperDate(new Date());
				bankTransferBussiness.setPlaceId(tagTakeFeeInfo.getRegisterPlace());
				bankTransferBussiness.setOperName(tagTakeFeeInfo.getOperName());
				bankTransferBussiness.setOperNo(tagTakeFeeInfo.getOperNo());
				bankTransferBussiness.setPlaceName(tagTakeFeeInfo.getPlaceName());
				bankTransferBussiness.setPlaceNo(tagTakeFeeInfo.getPlaceNo());
				bankTransferBussinessDao.saveBailAccount(bankTransferBussiness);
				
			}
			//缴款单缴款
			if(6==Long.valueOf(tagTakeFeeInfo.getChargeType()) && StringUtil.isNotBlank(tagTakeFeeInfo.getVoucherNo())){
				Voucher tmp = new Voucher();
				tmp.setVoucherNo(tagTakeFeeInfo.getVoucherNo());
				Voucher voucher = voucherDao.findVoucher(tmp);
				if(voucher == null){
					logger.info("电子标签提货金额缴款单缴款：异常，保存时缴款单凭证号不存在");
					return null;
				}else{
					//判断金额是否一致
					if(voucher.getBalance().compareTo(tagTakeFeeInfo.getChargeFee()) != 0){
						logger.info("电子标签提货金额缴款单缴款：异常，保存时缴款单余额与提货收费金额不一致");
						return null;
					}
					//将缴款单凭证记录的收费余额更新为0
					voucher = new Voucher();
					voucher.setVoucherNo(tagTakeFeeInfo.getVoucherNo());
					voucher.setBalance(new BigDecimal("0"));
					voucherDao.updateNotNull(voucher);
				}
			}

			//添加id
			BigDecimal SEQ_CSMSTagTakeFeeInfo_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfo_NO");
			tagTakeFeeInfo.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfo_NO.toString()));
			Receipt receipt = new Receipt();
			receipt.setId(this.sequenceUtil.getSequenceLong("SEQ_CSMSRECEIPT_NO"));
			tagTakeFeeInfo.setReceiptId(receipt.getId());
			tagTakeFeeInfoDao.save(tagTakeFeeInfo);

			/*Customer customer = new Customer();
			try {
				customer=customerDao.findByTypeCode(tagTakeFeeInfo.getCertType(), tagTakeFeeInfo.getCertNumber());
			} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			//tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagTakeFeeInfo.getRegisterOperID());
			tagBusinessRecord.setOperplaceID(tagTakeFeeInfo.getRegisterPlace());
			tagBusinessRecord.setOperName(tagTakeFeeInfo.getOperName());
			tagBusinessRecord.setOperNo(tagTakeFeeInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagTakeFeeInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagTakeFeeInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("20");//登记新增
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("新增电子标签提货登记");
			tagBusinessRecord.setRealPrice(tagTakeFeeInfo.getChargeFee());//--业务费用
			tagBusinessRecord.setBussinessid(tagTakeFeeInfo.getId());
			tagBusinessRecordDao.saveWithOutReceiptDao(tagBusinessRecord);

			//电子标签提货金额登记回执
			TagTakefeeinfoAddReceipt tagTakefeeinfoAddReceipt = new TagTakefeeinfoAddReceipt();
			tagTakefeeinfoAddReceipt.setTitle("电子标签提货金额登记回执");
			tagTakefeeinfoAddReceipt.setHandleWay("");
			tagTakefeeinfoAddReceipt.setTakeFeeChargeTypeCode(tagTakeFeeInfo.getChargeType());
			tagTakefeeinfoAddReceipt.setTakeFeeChargeTypeName(TagTakeFeeChargeTypeEnum.getNameByValue(tagTakeFeeInfo.getChargeType()));
			tagTakefeeinfoAddReceipt.setTakeFeeChargeFee(NumberUtil.get2Decimal(tagTakeFeeInfo.getChargeFee().doubleValue()*0.01));
			if(TagTakeFeeChargeTypeEnum.transfer.getValue().equals(tagTakeFeeInfo.getChargeType())){	//转账 特殊处理
				tagTakefeeinfoAddReceipt.setBankTransferNo(bankTransferInfo.getBankNo());
				tagTakefeeinfoAddReceipt.setBankTransferPayName(bankTransferInfo.getPayName());
				tagTakefeeinfoAddReceipt.setBankTransferBalance(NumberUtil.get2Decimal(bankTransferInfo.getTransferBlanace().doubleValue()*0.01));
				tagTakefeeinfoAddReceipt.setBankTransferArrivalTime(DateUtil.formatDate(bankTransferInfo.getArrivalTime(),"yyyy-MM-dd HH:mm:ss"));
			}else{
				tagTakefeeinfoAddReceipt.setTakeFeePayAccount(tagTakeFeeInfo.getPayAccount());
				tagTakefeeinfoAddReceipt.setPosId("");
				tagTakefeeinfoAddReceipt.setTakeFeeVoucherNo(tagTakeFeeInfo.getVoucherNo());
			}
			tagTakefeeinfoAddReceipt.setTakeFeeMemo(tagTakeFeeInfo.getMemo());
			receipt.setTypeCode(TagBussinessTypeEnum.tagtakefeeinfoAdd.getValue());
			receipt.setTypeChName(TagBussinessTypeEnum.tagtakefeeinfoAdd.getName());
			Customer customer = new Customer();
			customer.setOrgan(tagTakeFeeInfo.getClientName());
			customer.setIdType(tagTakeFeeInfo.getCertType());
			customer.setIdCode(tagTakeFeeInfo.getCertNumber());
			this.saveReceipt(receipt,tagBusinessRecord,tagTakefeeinfoAddReceipt,customer);

			return receipt.getId();
		} catch (ApplicationException e) {
			
			logger.error("保存电子标签提货金额的对象失败了"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("保存电子标签提货金额的对象失败了");
		}
		
	}

	@Override
	public void updateTagTakeFeeInfo(TagTakeFeeInfo tagTakeFeeInfo) {
		
		try {
			//保存修改前的到历史表
			TagTakeFeeInfoHis tagTakeFeeInfoHis = new TagTakeFeeInfoHis();
			
			//添加历史id
			//SEQ_CSMSTagTakeFeeInfoHis_NO
			BigDecimal SEQ_CSMSTagTakeFeeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfoHis_NO");
			tagTakeFeeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfoHis_NO.toString()));
			
			tagTakeFeeInfoHis.setCreateReason("修改");
			tagTakeFeeInfoHis.setCreateDate(new Date());
			
			tagTakeFeeInfo.setHis_SeqID(tagTakeFeeInfoHis.getId());
			tagTakeFeeInfoHisDao.save(tagTakeFeeInfo,tagTakeFeeInfoHis);
			//然后再保存修改后的
			tagTakeFeeInfoDao.update(tagTakeFeeInfo);
			
			/*Customer customer =new Customer();
			try {
				customer=customerDao.findByTypeCode(tagTakeFeeInfo.getCertType(), tagTakeFeeInfo.getCertNumber());
			} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			//tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagTakeFeeInfo.getRegisterOperID());
			tagBusinessRecord.setOperplaceID(tagTakeFeeInfo.getRegisterPlace());
			tagBusinessRecord.setOperName(tagTakeFeeInfo.getOperName());
			tagBusinessRecord.setOperNo(tagTakeFeeInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagTakeFeeInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagTakeFeeInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("21");//登记修改
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("修改电子标签提货登记");
			tagBusinessRecord.setRealPrice(tagTakeFeeInfo.getChargeFee());//--业务费用
			tagBusinessRecord.setBussinessid(tagTakeFeeInfo.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			
		} catch (ApplicationException e) {
			//保存修改日志
			logger.error("保存修改的电子标签提货金额的信息失败或者保存修改前的信息失败了"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("更新修改的电子标签提货金额的信息失败或者保存修改前的信息失败了");
		}
	}

	
	@Override
	public void deleteTagTakeFeeInfo(Long id) {
		try {
			//保存删除前的(历史备份)
			TagTakeFeeInfoHis tagTakeFeeInfoHis = new TagTakeFeeInfoHis();
			
			//添加历史id
			//SEQ_CSMSTagTakeFeeInfoHis_NO
			BigDecimal SEQ_CSMSTagTakeFeeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfoHis_NO");
			tagTakeFeeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfoHis_NO.toString()));
			tagTakeFeeInfoHis.setCreateReason("删除");
			tagTakeFeeInfoHis.setCreateDate(new Date());
			
			TagTakeFeeInfo tagTakeFeeInfo = tagTakeFeeInfoDao.findById(id);
			if(tagTakeFeeInfo.getIsDaySet()!=null&&StringUtil.isEquals(tagTakeFeeInfo.getIsDaySet(), "1")){
				throw new Exception("该电子标签提货单已日结，不能删除");
			}
			tagTakeFeeInfoHisDao.save(tagTakeFeeInfo,tagTakeFeeInfoHis);
			
			tagTakeFeeInfo.setHis_SeqID(tagTakeFeeInfoHis.getHis_SeqID());
			tagTakeFeeInfoDao.update(tagTakeFeeInfo);
			
			//然后再删除
			tagTakeFeeInfoDao.delete(id);
			
			
			if(3==Long.valueOf(tagTakeFeeInfo.getChargeType())){
				BankTransferInfo bankTransferInfo = bankTransferInfoDao.findBytId(tagTakeFeeInfo.getBankTransferInfoId());
				if(bankTransferInfo!=null){
					//将当前银行账户信息移入历史表
					BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
					BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
					bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
					bankTransferInfoHis.setCreateReason("电子标签提货金额登记删除后增加余额");
					//更新银行账户信息余额
					bankTransferInfo.setHisSeqId(bankTransferInfoHis.getId());
					//提货登记余额剩下多少，就往银行到账信息表里面补充
					bankTransferInfo.setBlanace(tagTakeFeeInfo.getTakeBalance());
					bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);
					bankTransferInfoDao.update(bankTransferInfo);
				}
			}
			
			/*Customer customer =new Customer();
			try {
				customer=customerDao.findByTypeCode(tagTakeFeeInfo.getCertType(), tagTakeFeeInfo.getCertNumber());
			} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			//tagBusinessRecord.setClientID(customer.getId());
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagTakeFeeInfo.getRegisterOperID());
			tagBusinessRecord.setOperplaceID(tagTakeFeeInfo.getRegisterPlace());
			tagBusinessRecord.setOperName(tagTakeFeeInfo.getOperName());
			tagBusinessRecord.setOperNo(tagTakeFeeInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagTakeFeeInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagTakeFeeInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("22");//登记删除
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("修改电子标签提货登记");
			tagBusinessRecord.setRealPrice(tagTakeFeeInfo.getChargeFee());//--业务费用
			tagBusinessRecord.setBussinessid(tagTakeFeeInfo.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			
		} catch (ApplicationException e) {
			//保存删除日志
			logger.error("保存历史电子标签提货金额信息失败或者删除失败!"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("保存历史电子标签提货金额信息失败或者删除失败!");
		} catch (Exception e) {
			logger.error("该提货金额登记已日结，不能删除!"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("该提货金额登记已日结，不能删除!");
		}
		
	}
	
	@Override
	public TagTakeFeeInfo findById(Long id) {
		TagTakeFeeInfo tagTakeFeeInfo = null;
		tagTakeFeeInfo = tagTakeFeeInfoDao.findById(id);
		return tagTakeFeeInfo;
	}
	
	/**
	 * 查找客户表中所有客户
	 */
	@Override
	public List<Map<String, Object>> findAllCustomers(Customer customer) {
		return customerDao.findAll(customer);
	}

	@Override
	public Map<String, Object> updateTagTakeFeeInfo(TagTakeFeeInfo tagTakeFeeInfo, BankTransferBussiness bankTransferBussiness) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			if(3==Long.valueOf(tagTakeFeeInfo.getChargeType())){
				BankTransferInfo bankTransfer = bankTransferInfoDao.findBytId(tagTakeFeeInfo.getBankTransferInfoId());
				if(bankTransfer!=null){
					
					//判断收费金额是增加还是减少还是没有变
					TagTakeFeeInfo oldTagTakeFee = tagTakeFeeInfoDao.findById(tagTakeFeeInfo.getId());
					if(tagTakeFeeInfo.getChargeFee().compareTo(oldTagTakeFee.getChargeFee()) == 0){
						
						//等于0不用做任何操作
						
					}else if(tagTakeFeeInfo.getChargeFee().compareTo(oldTagTakeFee.getChargeFee()) >0 ){
						//若收费金额修改为比原来增加了，则要判断多出来的金额是否能够被到账记录的余额扣除
						BigDecimal cost = tagTakeFeeInfo.getChargeFee().subtract(oldTagTakeFee.getChargeFee());
						if(bankTransfer.getBlanace().compareTo(cost) < 0){
							resultMap.put("result", "false");
							resultMap.put("message", "操作失败，银行到账信息记录的余额不够");
							return resultMap;
						}else{
							//将当前银行账户信息移入历史表
							BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
							BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
							bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
							bankTransferInfoHis.setCreateReason("电子标签提货金额登记修改后由于收费金额增加了而扣减到账余额");
							//更新银行账户信息余额
							bankTransfer.setHisSeqId(bankTransferInfoHis.getId());
							
							BigDecimal balance = bankTransfer.getBlanace();//先记录缴款前余额
							
							bankTransfer.setBlanace(new BigDecimal("-"+cost));
							
							bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransfer);
							bankTransferInfoDao.update(bankTransfer);
							
							//2017/06/05 保存银行到账缴款业务记录表
							bankTransferBussiness.setIdType(tagTakeFeeInfo.getCertType());
							bankTransferBussiness.setIdCode(tagTakeFeeInfo.getCertNumber());
							bankTransferBussiness.setClientName(tagTakeFeeInfo.getClientName());
							bankTransferBussiness.setBankTransferId(bankTransfer.getId());
							bankTransferBussiness.setPayName(bankTransfer.getPayName());
							bankTransferBussiness.setBankNo(bankTransfer.getBankNo());
							bankTransferBussiness.setRechargeType(BankTransferRechargeTypeEnum.tagTakeFeeTransferUpdate.getValue());
							bankTransferBussiness.setTransferBlanace(bankTransfer.getTransferBlanace());//银行到账金额
							bankTransferBussiness.setBlanace(balance.subtract(cost));//缴款后余额
							bankTransferBussiness.setRechargeCost(cost);//缴款金额
							bankTransferBussiness.setOperDate(new Date());
							bankTransferBussinessDao.saveBailAccount(bankTransferBussiness);
							
						}
					}else{
						//若收费金额修改为比原来减少了，到账记录的余额要增加
						BigDecimal add = oldTagTakeFee.getChargeFee().subtract(tagTakeFeeInfo.getChargeFee());
						//将当前银行账户信息移入历史表
						BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
						BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
						bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
						bankTransferInfoHis.setCreateReason("电子标签提货金额登记修改后由于收费金额减少了而增加到账余额");
						//更新银行账户信息余额
						bankTransfer.setHisSeqId(bankTransferInfoHis.getId());
						
						BigDecimal balance = bankTransfer.getBlanace();//先记录缴款前余额
						
						bankTransfer.setBlanace(add);
						bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransfer);
						bankTransferInfoDao.update(bankTransfer);
						
						
						//2017/06/05 保存银行到账缴款业务记录表
						bankTransferBussiness.setIdType(tagTakeFeeInfo.getCertType());
						bankTransferBussiness.setIdCode(tagTakeFeeInfo.getCertNumber());
						bankTransferBussiness.setClientName(tagTakeFeeInfo.getClientName());
						bankTransferBussiness.setBankTransferId(bankTransfer.getId());
						bankTransferBussiness.setPayName(bankTransfer.getPayName());
						bankTransferBussiness.setBankNo(bankTransfer.getBankNo());
						bankTransferBussiness.setRechargeType(BankTransferRechargeTypeEnum.tagTakeFeeTransferUpdate.getValue());
						bankTransferBussiness.setTransferBlanace(bankTransfer.getTransferBlanace());//银行到账金额
						bankTransferBussiness.setBlanace(balance.add(add));//缴款后余额
						bankTransferBussiness.setRechargeCost(new BigDecimal("-"+add));//缴款金额
						bankTransferBussiness.setOperDate(new Date());
						bankTransferBussinessDao.saveBailAccount(bankTransferBussiness);
					}
					
					
				}else{
					resultMap.put("result", "false");
					resultMap.put("message", "操作失败，无法找到关联的银行到账信息记录。");
					return resultMap;
				}
				
			}
			//保存修改前的到历史表
			TagTakeFeeInfoHis tagTakeFeeInfoHis = new TagTakeFeeInfoHis();
			
			//添加历史id
			//SEQ_CSMSTagTakeFeeInfoHis_NO
			BigDecimal SEQ_CSMSTagTakeFeeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfoHis_NO");
			tagTakeFeeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfoHis_NO.toString()));
			
			tagTakeFeeInfoHis.setCreateReason("修改");
			tagTakeFeeInfoHis.setCreateDate(new Date());
			
			tagTakeFeeInfo.setHis_SeqID(tagTakeFeeInfoHis.getId());
			tagTakeFeeInfoHisDao.save(tagTakeFeeInfo,tagTakeFeeInfoHis);
			//然后再保存修改后的
			tagTakeFeeInfoDao.update(tagTakeFeeInfo);
			
			Customer customer =new Customer();
			try {
				customer=customerDao.findByTypeCode(tagTakeFeeInfo.getCertType(), tagTakeFeeInfo.getCertNumber());
			} catch (ApplicationException e) {
				logger.error("查找客户信息错误！"+e.getMessage());
				e.printStackTrace();
				throw new ApplicationException();
			}
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			if(customer!=null)tagBusinessRecord.setClientID(customer.getId());
			else tagBusinessRecord.setClientID(0L);
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagTakeFeeInfo.getRegisterOperID());
			tagBusinessRecord.setOperplaceID(tagTakeFeeInfo.getRegisterPlace());
			tagBusinessRecord.setOperName(tagTakeFeeInfo.getOperName());
			tagBusinessRecord.setOperNo(tagTakeFeeInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagTakeFeeInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagTakeFeeInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType(TagBussinessTypeEnum.tagtakefeeinfoUpdate.getValue());//登记修改
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("修改电子标签提货登记");
			tagBusinessRecord.setRealPrice(tagTakeFeeInfo.getChargeFee());//--业务费用
			tagBusinessRecord.setBussinessid(tagTakeFeeInfo.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			
			resultMap.put("result", "true");
			return resultMap;
		} catch (ApplicationException e) {
			//保存修改日志
			logger.error("保存修改的电子标签提货金额的信息失败或者保存修改前的信息失败了"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("更新修改的电子标签提货金额的信息失败或者保存修改前的信息失败了");
		}
	}

	
	/**
	 * 2017-06-05
	 * 增加银行到账缴款业务记录
	 * @param id
	 * @param bankTransferBussiness
	 * @return void
	 * @throws Exception 
	 */
	@Override
	public void deleteTagTakeFeeInfo(Long id, BankTransferBussiness bankTransferBussiness) throws Exception {
		try {
			//保存删除前的(历史备份)
			TagTakeFeeInfoHis tagTakeFeeInfoHis = new TagTakeFeeInfoHis();
			
			//添加历史id
			//SEQ_CSMSTagTakeFeeInfoHis_NO
			BigDecimal SEQ_CSMSTagTakeFeeInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeFeeInfoHis_NO");
			tagTakeFeeInfoHis.setId(Long.valueOf(SEQ_CSMSTagTakeFeeInfoHis_NO.toString()));
			tagTakeFeeInfoHis.setCreateReason("删除");
			tagTakeFeeInfoHis.setCreateDate(new Date());
			
			TagTakeFeeInfo tagTakeFeeInfo = tagTakeFeeInfoDao.findById(id);
			if(tagTakeFeeInfo.getIsDaySet()!=null&&StringUtil.isEquals(tagTakeFeeInfo.getIsDaySet(), "1")){
				throw new Exception("该提货登记已经日结，不能删除！");
			}
			tagTakeFeeInfoHisDao.save(tagTakeFeeInfo,tagTakeFeeInfoHis);
			
			tagTakeFeeInfo.setHis_SeqID(tagTakeFeeInfoHis.getHis_SeqID());
			tagTakeFeeInfoDao.update(tagTakeFeeInfo);
			
			//然后再删除
			tagTakeFeeInfoDao.delete(id);
			
			
			if(3==Long.valueOf(tagTakeFeeInfo.getChargeType())){
				BankTransferInfo bankTransferInfo = bankTransferInfoDao.findBytId(tagTakeFeeInfo.getBankTransferInfoId());
				if(bankTransferInfo!=null){
					//将当前银行账户信息移入历史表
					BankTransferInfoHis bankTransferInfoHis = new BankTransferInfoHis();
					BigDecimal SEQ_CSMSBankTransferInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBankTransferInfoHis_NO");
					bankTransferInfoHis.setId(Long.valueOf(SEQ_CSMSBankTransferInfoHis_NO.toString()));
					bankTransferInfoHis.setCreateReason("电子标签提货金额登记删除后增加余额");
					//更新银行账户信息余额
					bankTransferInfo.setHisSeqId(bankTransferInfoHis.getId());
					
					BigDecimal balance = bankTransferInfo.getBlanace();//先记录缴款前余额
					
					//提货登记余额剩下多少，就往银行到账信息表里面补充
					bankTransferInfo.setBlanace(tagTakeFeeInfo.getTakeBalance());
					bankTransferInfoHisDao.saveHis(bankTransferInfoHis, bankTransferInfo);
					bankTransferInfoDao.update(bankTransferInfo);
					
					
					//
					bankTransferBussiness.setIdType(tagTakeFeeInfo.getCertType());
					bankTransferBussiness.setIdCode(tagTakeFeeInfo.getCertNumber());
					bankTransferBussiness.setClientName(tagTakeFeeInfo.getClientName());
					bankTransferBussiness.setBankTransferId(bankTransferInfo.getId());
					bankTransferBussiness.setPayName(bankTransferInfo.getPayName());
					bankTransferBussiness.setBankNo(bankTransferInfo.getBankNo());
					bankTransferBussiness.setRechargeType(BankTransferRechargeTypeEnum.tagTakeFeeTransferDelete.getValue());
					bankTransferBussiness.setTransferBlanace(bankTransferInfo.getTransferBlanace());//银行到账金额
					bankTransferBussiness.setBlanace(balance.add(tagTakeFeeInfo.getTakeBalance()));//缴款后余额
					bankTransferBussiness.setRechargeCost(new BigDecimal("-"+tagTakeFeeInfo.getTakeBalance()));//缴款金额
					bankTransferBussiness.setOperDate(new Date());
					bankTransferBussinessDao.saveBailAccount(bankTransferBussiness);
				}
				
			}
			
			TagBusinessRecord tagBusinessRecord=new TagBusinessRecord();
			BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil
					.getSequence("SEQ_CSMSTagBusinessRecord_NO");
			tagBusinessRecord.setId(Long.parseLong(SEQ_CSMSTagBusinessRecord_NO.toString()));
			tagBusinessRecord.setOperTime(new Date());
			
			tagBusinessRecord.setOperID(tagTakeFeeInfo.getRegisterOperID());
			tagBusinessRecord.setOperplaceID(tagTakeFeeInfo.getRegisterPlace());
			tagBusinessRecord.setOperName(tagTakeFeeInfo.getOperName());
			tagBusinessRecord.setOperNo(tagTakeFeeInfo.getOperNo());
			tagBusinessRecord.setPlaceName(tagTakeFeeInfo.getPlaceName());
			tagBusinessRecord.setPlaceNo(tagTakeFeeInfo.getPlaceNo());
			
			tagBusinessRecord.setBusinessType("22");//登记删除
			tagBusinessRecord.setCurrentTagState(TagStateEnum.normal.getValue());//正常
			tagBusinessRecord.setMemo("修改电子标签提货登记");
			tagBusinessRecord.setRealPrice(tagTakeFeeInfo.getChargeFee());//--业务费用
			tagBusinessRecord.setBussinessid(tagTakeFeeInfo.getId());
			tagBusinessRecordDao.save(tagBusinessRecord);
			
		} catch (ApplicationException e) {
			//保存删除日志
			logger.error("保存历史电子标签提货金额信息失败或者删除失败!"+e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("保存历史电子标签提货金额信息失败或者删除失败!");
		} 
		
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param tagBusinessRecord 电子标签业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, TagBusinessRecord tagBusinessRecord, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.tag.getValue());
		receipt.setCreateTime(tagBusinessRecord.getOperTime());
		receipt.setPlaceId(tagBusinessRecord.getOperplaceID());
		receipt.setPlaceNo(tagBusinessRecord.getPlaceNo());
		receipt.setPlaceName(tagBusinessRecord.getPlaceName());
		receipt.setOperId(tagBusinessRecord.getOperID());
		receipt.setOperNo(tagBusinessRecord.getOperName());
		receipt.setOperName(tagBusinessRecord.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

}
