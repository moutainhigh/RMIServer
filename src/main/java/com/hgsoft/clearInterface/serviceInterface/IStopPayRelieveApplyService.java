package com.hgsoft.clearInterface.serviceInterface;

import com.hgsoft.clearInterface.entity.StopPayRelieveApply;

import java.util.Date;

public interface IStopPayRelieveApplyService {

	String findMaxAccBankListRecvByBankAccount(String bankAccount);

	Boolean saveStopPayRelieveApply(StopPayRelieveApply stopPayRelieveApply);

	Boolean saveStopPayRelieveApply(String bankAccount, String cardNo, Date genTime, Double lateFee, String remark);


}
