package com.hgsoft.account.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.utils.Pager;

public interface IRefundInfoService {
	public Pager list(Pager pager,Date starTime,Date endTime,Customer customer);
	public Pager list(Pager pager,String starTime,String endTime,Customer customer);
	public RefundInfo findById(Long id);
	public boolean save(String type,RefundInfo refundInfo,MainAccountInfo mainAccountInfo);
	public Map<String, Object> save(String type,RefundInfo refundInfo,MainAccountInfo mainAccountInfo,Customer customer, Material material, String[] tempPicNameList,String species,Long bussinessPlaceId,Map<String,Object> params);
	public void update(RefundInfo refundInfo);
	public boolean updateRetracement(String type,RefundInfo refundInfo,MainAccountInfo mainAccountInfo,Map<String,Object> params);
	public boolean saveRefund(RefundInfo refundInfo,String materialIds);
	//public RefundInfo findByCardNoAndId(String cardNo,Long customerId);
	public List<Map<String, Object>> findByCardNoAndType(String cardNo,String refundType);
	
	/**
	 * 客服退款查询列表
	 * @param pager
	 * @param refundInfo
	 * @param refundApplyStartTime 退款申请开始时间
	 * @param refundApplyEndTime  退款申请结束时间
	 * @return Pager
	 */
	public Pager findRefundServicePager(Pager pager,RefundInfo refundInfo,String refundApplyStartTime,String refundApplyEndTime,Long bussinessPlaceId);
	/**
	 * 
	 * @param refundInfoId  根据退款记录id查询详情
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findRefundServiceDetail(Long refundInfoId);
	/**
	 * 客服退款查询  修改银行账号信息
	 * @param refundInfo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> saveRefundServiceCheckEdit(RefundInfo refundInfo);
	public Pager findRefundServicePagerForAMMS(Pager pager,String bankCode,String cardType,RefundInfo refundInfo,String refundApplyStartStr,String refundApplyEndStr);

}
