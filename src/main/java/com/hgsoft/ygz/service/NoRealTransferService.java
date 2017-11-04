package com.hgsoft.ygz.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hgsoft.obu.entity.TagInfo;

/**
 * 同步请求服务接口(非实时)
 * @author saint-yeb
 *
 */
@Service
public interface NoRealTransferService {

	/**
	 * 用户卡黑名单同步
	 * @param cardNO 卡号
	 * @param genTime 黑名单生成时间
	 * @param CardBlackType  见CardBlackTypeEnum
	 * @param operaTpye
	 */
	public void blackListTransfer(String cardNO,Date genTime,Integer CardBlackType,Integer operaTpye);
	
	/**
	 * 用户卡账余额同步
	 * @param userNo 用户编号
	 * @param newCardNo 新卡卡号
	 * @param balance 卡账余额
	 * @param operaTpye
	 */
	public void balanceTransfer(String userNo,String newCardNo,BigDecimal balance,Integer operaTpye);

	
	public void obuBlackListTransfer(TagInfo tagInfo,Date now,Integer operatorType,Integer obuState);

	
	/**
	 * 退款信息同步
	 * @param userNo 用户编号
	 * @param customPointCode 退款网点编码
	 * @param RefundTime	退款时间
	 * @param cardNo	卡号
	 * @param Fee	退款费用
	 */
	public void refundTransfer(String userNo,String customPointCode,Date refundTime,String cardNo,BigDecimal Fee);

}
