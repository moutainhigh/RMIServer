package com.hgsoft.clearInterface.serviceInterface;

import java.math.BigDecimal;
import java.util.Date;

public interface ICardObuService {
	
	/**
	 * 储值卡发行接口
	 * @param cardCode 卡号
	 * @param sDate 发行时间
	 * @param status 0表示生效，1表示失效
	 * @return
	 */
	public Boolean prepaidCIssue(String cardCode,Date sDate,Integer status);
	
	/**
	 * @param cardCode 卡号
	 * @param bankNo 开户银行编码
	 * @param bankAccount 开户银行账号
	 * @param sDate 发行时间
	 * @param status 状态：0表示生效，1表示失效
	 * @return
	 */
	public Boolean accountCIssue(String cardCode,String bankNo,String bankAccount,Date sDate,Integer status);
	
	/**
	 * @param organ客户名称
	 * @param accountType 账号类型：0表示对公，1表示储蓄，2表示信用卡，3表示跨行划扣
	 * @param bankNo 本行划扣时填写（accountType<>3）
	 * @param accName 开户账户户名
	 * @param bankAccount 开户银行账号
	 * @param bankName 开户银行名称
	 * @param spanBankNo 跨行划扣银行编码（accountType=3时填写）
	 * @param reconBankNo 开户行清算行号（accountType=3时填写）
	 * @param branchBankNo 开户行集中受理行号（accountType=3时填写）
	 * @param virType 转账类型：0表示定额转账，1表示限额转账，2表示零余额定期转账，3表示零余额限额转账
	 * @param virCount 转账次数
	 * @param maxAcr 通行费转账限额
	 * @param systemType 合作单位标识:0表示普通客户，1表示香港快易通，2表示澳门通
	 * @param reqTime 申请时间
	 * @param checkTime 审批通过时间
	 * @return
	 */
	public Boolean accountCUserInfo(String organ,String accountType,String bankNo,String accName,String bankAccount,String bankName,String spanBankNo,String reconBankNo,String branchBankNo,String virType,String virCount,BigDecimal maxAcr,String systemType,Date reqTime,Date checkTime);
	
	/**
	 * @param genTime 生成时间
	 * @param stateFlag 卡片状态 ：0表示正常，1表示挂失，2表示注销，3表示挂起
	 * @param serType 业务类型：27表示储值卡发行，24表示记帐卡发行，10表示卡片挂失，05表示卡片解挂，G1表示有卡挂起，G2表示无卡挂起，G3表示卡片解除挂起，06表示有卡注销，01表示无卡注销
	 * @param cardCode 卡号
	 * @param cardType 卡类型
	 * @param userType 客户类型：0表示个人，1表示单位
	 * @return
	 */
	public Boolean userStatusInfo(Date genTime,String stateFlag,String serType,String cardCode,String cardType,String userType);
	
	/**
	 * @param genTime 生成时间
	 * @param delFlag 处理标志
	 * @param cardCode 卡号
	 * @param cardType 卡类型：22表示储值卡，23表示记帐卡
	 * @param vehColor 车牌颜色
	 * @param license 车牌号
	 * @param vehType 国标车型
	 * @param obuSeq OBU合同序列号
	 * @param obuIssueTime OBU启用时间
	 * @param obuExpireTime OBU到期时间
	 * @return
	 */
	public void saveUserStateInfo(Date genTime, Integer delFlag, String cardCode, String cardType, Integer vehColor,
			String license, String vehType,String obuCode, String obuSeq, Date obuIssueTime, Date obuExpireTime,String remark);
	
	/**
	 * @param genTime 生成时间
	 * @param stateFlag 卡片状态
	 * @param serType 业务类型
	 * @param cardCode 卡号
	 * @param cardType 卡类型
	 * @param userType 客户类型
	 * @return
	 */
	public void saveCardStateInfo(Date genTime,Integer stateFlag,String serType,String cardCode,String cardType,String userType);

	/**
	 * @param cardCode 卡号
	 * @param systemBalance 系统余额
	 * @param returnMoney 回退余额
	 * @param genTime 生成时间
	 * @param account 银行账号
	 * @param remark 备注
	 * @param updateTime 更新时间
	 */
	public void saveCardBalance(String cardCode, BigDecimal systemBalance, BigDecimal returnMoney, Date genTime,
			String account, String remark, Date updateTime);

	public void saveCardUnusable(Date tradeTime, int parseInt, String value, String cardNo, String string,
			String userType,Integer vehColor,String license,String vehType);
	
}
