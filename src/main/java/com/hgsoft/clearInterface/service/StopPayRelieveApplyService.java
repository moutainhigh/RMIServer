package com.hgsoft.clearInterface.service;

import com.hgsoft.clearInterface.dao.StopPayRelieveApplyDao;
import com.hgsoft.clearInterface.entity.StopPayRelieveApply;
import com.hgsoft.clearInterface.serviceInterface.IStopPayRelieveApplyService;
import com.hgsoft.common.dao.EtcTollingBaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class StopPayRelieveApplyService extends EtcTollingBaseDao  implements IStopPayRelieveApplyService{
	@Resource
	private StopPayRelieveApplyDao stopPayRelieveApplyDao;


	/**
	 * 根据银行账号获取最近的银行回盘结果
	 * @param bankAccount
	 * @return
	 */
	@Override
	public String findMaxAccBankListRecvByBankAccount(String bankAccount) {
		return stopPayRelieveApplyDao.findMaxAccBankListRecvByBankAccount(bankAccount);
	}

	/**
	 * 清算系统申请解除止付数据接口
	 * @param stopPayRelieveApply
	 * @return
	 */
	@Override
	public Boolean saveStopPayRelieveApply(StopPayRelieveApply stopPayRelieveApply){
		try{
			stopPayRelieveApplyDao.saveStopPayRelieveApply(stopPayRelieveApply);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 清算系统申请解除止付数据接口
	 * @param bankAccount 银行卡账号
	 * @param cardNo 卡片号码
	 * @param genTime 生成时间
	 * @param lateFee 滞纳金金额
	 * @param remark 备注
	 * @return
	 */
	@Override
	public Boolean saveStopPayRelieveApply(String bankAccount,String cardNo,Date genTime,Double lateFee,String remark){
		try{
			StopPayRelieveApply stopPayRelieveApply = new StopPayRelieveApply(bankAccount,cardNo,genTime,lateFee,remark,new Date());
			stopPayRelieveApplyDao.saveStopPayRelieveApply(stopPayRelieveApply);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
