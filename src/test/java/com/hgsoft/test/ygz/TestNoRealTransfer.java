package com.hgsoft.test.ygz;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.test.common.BaseJunit4Test;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;

public class TestNoRealTransfer extends BaseJunit4Test{

	@Resource
	private NoRealTransferService noRealTransferService;
	
	@Test
	public void blackListTransfer(){
		for (int i = 0; i < 100; i++) {
		noRealTransferService.blackListTransfer("7353528977222288",new Date(),CardBlackTypeEmeu.CARDLOCK.getCode(), OperationTypeEmeu.TRANSFER.getCode());
		}
	}
	
	@Test
	public void  balanceTransfer(){
		for (int i = 0; i < 100; i++) {
		MainAccountInfo mainAccountInfo=new MainAccountInfo();
		mainAccountInfo.setMainId(623442356l);
		noRealTransferService.balanceTransfer("1639221609020426","1711221703170881",new BigDecimal(10), OperationTypeEmeu.TRANSFER.getCode());
		}
	}
}
