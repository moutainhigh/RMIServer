package com.hgsoft.ivr.serviceInterface;

import java.util.List;
import java.util.Map;

public interface IIVRReportService {
	public List findAllCustomPointType();
	public List findAllCustomPointArea();
	public List findAllOperator();
	public List findRoadDiscountList();
    public List<Map<Object, String>> findCardNoList(String bankno,String userNo,String organ);
    public List findPlaceNameList(String area,String placeType);
	public List<Map<String, Object>> findCustomerList(String organ,
			String idCode, String idType);
	
	/**
	 * 获取黑名单记帐卡所欠通行费和滞纳金
	 * @param cardno
	 */
	public Map<String, Object> getEtcFeeAndLatefee(String cardno,String bankno);
	
	/**
	 * 根据记帐卡号查银行号
	 * @param cardNo
	 * @return
	 */
	public String findBankNoBycardNo(String cardNo);
	 
	/**
	 * IVR报表查询-滞纳金查询
	 * @param bankNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String,Object>> getLateFeeInfo(String bankNo,String startTime,String endTime);
	
	/**
	 * 根据银行号查客户号
	 * @param bankNo
	 * @return
	 */
	public String findOrganByBankNo(String bankNo);
}
