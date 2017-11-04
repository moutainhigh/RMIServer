/*
package com.hgsoft.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.serviceInterface.IAccountCApplyService;
import com.hgsoft.accountC.serviceInterface.IRelieveStopPayService;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.serviceInterface.IElectronicPurseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class LateFeeTest {
	

	@Resource
	private IRelieveStopPayService relieveStopPayService;
	@Resource
	private IAccountCApplyService accountCApplyService;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private IElectronicPurseService electronicPurseService;
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	
	//@Test
	public void test() throws IOException{
//		String cardNo = "1415232000571148";
//		AccountCApply accountCApply = accountCApplyService.findByCardNo(cardNo);
//		
//		BigDecimal lateFee = relieveStopPayService.getLateFee(accountCApply, new Date());
//		
//		System.out.println(lateFee);
	}
	
	@Test
	public void saveBlackListAll() throws Exception{
//		blackListService.saveBlackListAdditionSend();
//		blackListService.saveBlackListAllSend();
		blackListService.dealBlackListWarter();
//		DbasCardFlow dbasCardFlow = new DbasCardFlow();
//		dbasCardFlow.setId(new Long(10086));
//		dbasCardFlow.setOldCardNo("111");
//		dbasCardFlow.setNewCardNo("222");
//		dbasCardFlow.setCardNo("333");
//		dbasCardFlow.setCardType("23");
//		dbasCardFlow.setSerType("0");
//		dbasCardFlow.setApplyTime(new Date());
//		dbasCardFlow.setReadFlag("0");
//		dbasCardFlow.setEndFlag("0");
//		dbasCardFlowDao.save(dbasCardFlow);
//		electronicPurseService.saveTransferMoney();
	}
}
*/
