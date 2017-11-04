package com.hgsoft.directorAudite.serviceInterface;

import java.util.Map;

import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.utils.Pager;

public interface IDirectorAuditeService {
	/**
	 * 主任审核分页查询列表
	 * @param pager
	 * @param refundApplyStartTime
	 * @param refundApplyEndTime
	 * @param directorAuditStartTime
	 * @param directorAuditEndTime
	 * @param refundInfo
	 * @return Pager
	 */
	public Pager findDirectorAuditePager(Pager pager,String refundApplyStartTime,String refundApplyEndTime,String directorAuditStartTime
			,String directorAuditEndTime,RefundInfo refundInfo,Long bussinessPlaceId);
	
	/**
	 * 
	 * @param refundInfoId  根据退款记录id查询详情
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findDirectorAuditeDetail(Long refundInfoId);
	/**
	 * 保存主任审核信息
	 * @param refundInfo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> saveRefundAudite(RefundInfo refundInfo);
}
