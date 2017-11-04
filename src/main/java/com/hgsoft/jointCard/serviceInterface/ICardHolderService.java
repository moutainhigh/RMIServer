package com.hgsoft.jointCard.serviceInterface;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.utils.Pager;

public interface ICardHolderService {

	public Pager findByCardNo(String cardNo);

	public CardHolder findCardHolderByCardNo(String cardNo);

	public Pager findByName(String name);

	public Pager findByIdCode(String idType, String idCode);

	public Pager findCardHolders(Pager pager, CardHolder cardHolder);

	public CardHolder findCardHolderById(Long cardHolderId);

	public void update(Customer customer, CardHolder cardHolder);

	/**
	 * 根据证件类型和证件号码查询持卡人
	 *
	 * @param idType
	 * 		证件类型
	 * @param idCode
	 * 		证件号码
	 * @return 持卡人
	 * @author wangjinhao
	 */
	CardHolder findByTypeAndCodeACMS(String idType, String idCode);

	/**
	 * 根据用户编号查找持卡人
	 *
	 * @param userNo
	 * 		用户编号
	 * @return 持卡人
	 * @author wangjinhao
	 */
	CardHolder findByUserNo(String userNo);

}