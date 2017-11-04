package com.hgsoft.account.serviceInterface;

import java.util.Map;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.entity.Voucher;

public interface IVoucherService {
	/**
	 * 缴款单缴款
	 * @param mainAccountInfo
	 * @param rechargeInfo
	 * @param voucher 缴款单实体
	 * @return Map<String,Object>
	 */
	public Map<String, Object> saveForRechargeInfo(MainAccountInfo mainAccountInfo,RechargeInfo rechargeInfo,Voucher voucher);
	/**
	 * 查找
	 * @param voucher 携带参数
	 * @return Voucher
	 */
	public Voucher findVoucher(Voucher voucher);
}
