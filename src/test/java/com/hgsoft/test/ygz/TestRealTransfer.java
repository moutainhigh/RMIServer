package com.hgsoft.test.ygz;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.test.common.BaseJunit4Test;
import com.hgsoft.ygz.common.CardStatusEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.RealTransferService;

public class TestRealTransfer extends BaseJunit4Test{

	
	@Resource
	private RealTransferService realTransferService;
	
	
	@Test
	public void userCardTransfer(){
		//{"model":"","vehiclePlate":"粤A83844","channelId":"A11000001","status":"0","cardType ":"123","statusChangeTime":{"bytes":[119,-86,1,1,1,1,1],"length":7,"null":false,"stream":{}},"issuedType":"2","cardNo":"122222222222222","vehicleColor":"1","userId":112438437,"issuedTime":{"date":16,"day":1,"hours":20,"minutes":5,"month":9,"seconds":35,"time":1508155535830,"timezoneOffset":-480,"year":117}}
		for (int i = 0; i < 100; i++) {
			System.out.println("-----------------userCardTransfer start------------");
			Customer customer=new Customer();
			customer.setUserNo("20170509193833685866");
			PrepaidC prepaidC=new PrepaidC();
			prepaidC.setCardNo("7353528977222289");
			prepaidC.setCustomerID(112438437l);
			prepaidC.setEndDate(new Date());
			prepaidC.setStartDate(new Date());
			prepaidC.setPlaceNo("A11000001");
			prepaidC.setSaleTime(new Date());
			prepaidC.setState("0");
			VehicleInfo vehicle=new VehicleInfo();
			vehicle.setVehicleColor("1");
			vehicle.setVehiclePlate("粤A83844T");
			realTransferService.prepaidCTransfer(customer, prepaidC, vehicle, CardStatusEmeu.CARD_LOSS.getCode(),OperationTypeEmeu.ADD.getCode());
			System.out.println("-----------------userCardTransfer end------------");
		}
	}
	
}
