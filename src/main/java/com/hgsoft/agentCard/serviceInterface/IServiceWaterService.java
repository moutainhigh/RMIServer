package com.hgsoft.agentCard.serviceInterface;

import com.hgsoft.customer.entity.Customer;

/**
 * @author ： 孙晓伟
 *         file : IServiceWaterService.java
 *         date : 2017/6/14
 *         time : 11:53
 */
public interface IServiceWaterService {
    public void saveAcServiceWater(Customer customer, String cardNo,String accouncCBussinessType,String serviceWaterType,String remark);
    public void savePcServiceWater(Customer customer, String cardNo, String ServiceWaterType,String remark);
}
