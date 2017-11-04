package com.hgsoft.macao.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.macao.dao.MacaoBillGetDao;
import com.hgsoft.macao.dao.MacaoBillGetHisDao;
import com.hgsoft.macao.dao.MacaoDao;
import com.hgsoft.macao.dao.ServerChangeInfoDao;
import com.hgsoft.macao.entity.MacaoBankAccount;
import com.hgsoft.macao.entity.MacaoBillGet;
import com.hgsoft.macao.entity.MacaoBillGetHis;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.macao.entity.ServerChangeInfo;
import com.hgsoft.macao.serviceInterface.IMacaoBillGetService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class MacaoBillGetService implements IMacaoBillGetService{

	private static Logger logger = Logger.getLogger(MacaoBillGetService.class.getName());
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private MacaoBillGetDao macaoBillGetDao;
	@Resource
	private MacaoDao macaoDao;
	
	@Resource
	private MacaoBillGetHisDao macaoBillGetHisDao;
	@Resource
	private ServerChangeInfoDao serverChangeInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	
	public boolean checkCardNo(String cardNo){
		try {
			AccountCInfo accountCInfo = macaoBillGetDao.getAccountCState(cardNo);
			if(accountCInfo!=null && "0".equals(accountCInfo.getState())){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	@Override
	public Pager findBillGets(Pager pager, MacaoBillGet macaoBillGet) {
		return macaoBillGetDao.findByPage(pager, macaoBillGet);
	}

	@Override
	public void saveMacaoBillGet(MacaoBillGet macaoBillGet) {
		try{
			Long id = sequenceUtil.getSequenceLong("SEQ_CSMSMACAOBILLGET");
			macaoBillGet.setId(id);
			macaoBillGetDao.save(macaoBillGet);
			
			//增加流水
			MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(macaoBillGet.getCardBankNo());
			MacaoBankAccount macaoBankAccount = macaoDao.findBankAccountByCardNo(macaoBillGet.getCardBankNo());
			ServiceWater serviceWater = new ServiceWater(null, macaoBillGet.getMainId(), null, null, macaoBillGet.getCardBankNo(), 
					null, null, null, macaoCardCustomer.getBankAccountNumber(), null, null, 
					null, null, null, null, null,
					null, null, null, null, 
					null, null, macaoBillGet.getOperId(), macaoBillGet.getPlaceId(), macaoBillGet.getOperNo(),
					macaoBillGet.getOperName(), macaoBillGet.getPlaceNo(), 
					macaoBillGet.getPlaceName(), macaoBillGet.getOperTime(), "发票邮寄登记", macaoCardCustomer.getId());
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("105");
			serviceWater.setMacaoBankAccountId(macaoBankAccount.getId());
			serviceWater.setBankAccount(macaoBankAccount.getBankAccountNumber());
			serviceWaterDao.save(serviceWater);
			
			//保存澳门通清算数据
			Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = format.format(macaoBillGet.getOperTime());
			ServerChangeInfo serverChangeInfo = new ServerChangeInfo("91006", s, macaoBillGet.getCardBankNo(),
					macaoCardCustomer.getInvoiceTitle(), "", "", "", "", macaoCardCustomer.getCnName(), macaoBillGet.getRemark(), macaoBillGet.getOperNo(), macaoBillGet.getPlaceNo(),"1");
			serverChangeInfo.setId(sequenceUtil.getSequenceLong("seq_csmsserverchangeinfo_no"));
			serverChangeInfoDao.save(serverChangeInfo);
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"发票邮寄服务登记失败");
			throw new ApplicationException();
		}
	}

	@Override
	public MacaoBillGet find(MacaoBillGet macaoBillGet) {
		return macaoBillGetDao.findByCardBankNo(macaoBillGet.getCardBankNo());
	}

	@Override
	public MacaoBillGet findById(Long id) {
		return macaoBillGetDao.findById(id);
	}

	@Override
	public Map<String, String> delete(Long id) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try{
			
			MacaoBillGet macaoBillGet = macaoBillGetDao.findById(id);
			if(macaoBillGet==null){
				resultMap.put("result", "false");
				return resultMap;
			}
			//移入历史表   "2" 删除
			MacaoBillGetHis macaoBillGetHis = new MacaoBillGetHis(new Date(), "2", macaoBillGet);
			Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSMACAOBILLGETHIS");
			macaoBillGetHis.setId(hisId);
			macaoBillGetHisDao.save(macaoBillGetHis);
			
			
			macaoBillGet.setHisSeqId(hisId);
			macaoBillGetDao.update(macaoBillGet);
			//删除记录
			macaoBillGetDao.deleteById(macaoBillGet.getId());
			
			//增加流水
			MacaoCardCustomer macaoCardCustomer = macaoDao.findMacaoCardCustomerByCardNo(macaoBillGet.getCardBankNo());
			ServiceWater serviceWater = new ServiceWater(null, macaoBillGet.getMainId(), null, null, macaoBillGet.getCardBankNo(), 
					null, null, null, macaoCardCustomer.getBankAccountNumber(), null, null, 
					null, null, null, null, null,
					null, null, null, null, 
					null, null, macaoBillGet.getOperId(), macaoBillGet.getPlaceId(), macaoBillGet.getOperNo(),
					macaoBillGet.getOperName(), macaoBillGet.getPlaceNo(), 
					macaoBillGet.getPlaceName(), macaoBillGet.getOperTime(), "发票邮寄登记删除", macaoCardCustomer.getId());
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setSerType("106");
			serviceWaterDao.save(serviceWater);
			
			
			//保存澳门通清算数据
			Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = format.format(macaoBillGetHis.getGenTime());
			ServerChangeInfo serverChangeInfo = new ServerChangeInfo("91006", s, macaoBillGet.getCardBankNo(),
					macaoCardCustomer.getInvoiceTitle(), "", "", "", "", macaoCardCustomer.getCnName(), macaoBillGet.getRemark(), macaoBillGet.getOperNo(), macaoBillGet.getPlaceNo(),"0");
			serverChangeInfo.setId(sequenceUtil.getSequenceLong("seq_csmsserverchangeinfo_no"));
			serverChangeInfoDao.save(serverChangeInfo);
			
			resultMap.put("result", "true");
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"删除发票邮寄服务失败");
			throw new ApplicationException();
		}
		return resultMap;
	}

	@Override
	public MacaoCardCustomer getMacaoCardCustomerInfo(String cardNo) {
		try {
			return macaoBillGetDao.getMacaoCardCustomerInfo(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
