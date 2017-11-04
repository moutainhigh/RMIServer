package com.hgsoft.jointCard.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CardHolderDao extends BaseDao {

	public Pager findCardHolders(Pager pager, CardHolder cardHolder) {
		StringBuffer sql = new StringBuffer("select * from CSMS_HK_CARDHOLDER ");
		List list = new ArrayList<>();
		if (cardHolder != null) {
			Map map = FieldUtil.getPreFieldMap(CardHolder.class, cardHolder);
			sql.append(map.get("selectNameStrNotNull"));
			list = ((List) map.get("paramNotNull"));
		} // if
		return this.findByPages(sql.toString(), pager, list.toArray());
	}

	public CardHolder findCardHolderById(Long cardHolderId) {
		String sql = "select * from CSMS_HK_CARDHOLDER where id=?";
		List<Map<String, Object>> list = queryList(sql, cardHolderId);
		CardHolder cardHolder = null;
		if (!list.isEmpty()) {
			cardHolder = new CardHolder();
			this.convert2Bean(list.get(0), cardHolder);
		} // if
		return cardHolder;
	}

	public CardHolder findCardHolderByCardNo(String cardNo) {
		String sql = "select * from CSMS_HK_CARDHOLDER where cardno=?";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		CardHolder cardHolder = null;
		if (!list.isEmpty()) {
			cardHolder = new CardHolder();
			this.convert2Bean(list.get(0), cardHolder);
		} // if
		return cardHolder;
	}

	public void update(CardHolder cardHolder) {
		StringBuffer sql = new StringBuffer("update CSMS_HK_Cardholder set ");
		sql.append(" name = ?, ");
		sql.append(" idType = ?, ");
		sql.append(" idCode = ?, ");
		sql.append(" invoiceTitle = ?, ");
		sql.append(" remark = ?, ");
		sql.append(" cardNo = ?, ");
		sql.append(" accountCId = ?, ");
		sql.append(" hisseqid = ?, ");
		sql.append(" operId = ?, ");
		sql.append(" operCode = ?, ");
		sql.append(" operName = ?, ");
		sql.append(" placeId = ?, ");
		sql.append(" placeCode = ?, ");
		sql.append(" placeName = ?, ");
		sql.append(" linkMan = ?, ");
		sql.append(" phoneNum = ?, ");
		sql.append(" mobileNum = ?, ");
		sql.append(" linkAddr = ? ");
		sql.append(" where id= ? ");
		saveOrUpdate(sql.toString(), cardHolder.getName(), cardHolder.getIdType(), cardHolder.getIdCode(),
				cardHolder.getInvoiceTitle(), cardHolder.getRemark(), cardHolder.getCardNo(),
				cardHolder.getAccountCId(), cardHolder.getHisSeqId(), cardHolder.getOperId(), cardHolder.getOperCode(),
				cardHolder.getOperName(), cardHolder.getPlaceId(), cardHolder.getPlaceCode(), cardHolder.getPlaceName(),
				cardHolder.getLinkMan(), cardHolder.getPhoneNum(), cardHolder.getMobileNum(), cardHolder.getLinkAddr(),
				cardHolder.getId());
	}

	public void save(CardHolder cardHolder) {
		Map map = FieldUtil.getPreFieldMap(CardHolder.class, cardHolder);
		StringBuffer sql = new StringBuffer("insert into CSMS_HK_Cardholder");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public void delete(Long id) {
		String sql = "delete from CSMS_HK_Cardholder where id=?";
		delete(sql, id);
	}

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
	public CardHolder findByTypeAndCodeACMS(String idType, String idCode) {
		StringBuffer sql = new StringBuffer("select chc.id, chc.name, chc.idType, chc.idCode, chc" +
				".secondNo, chc.secondName, chc.userNo from csms_hk_cardHolder chc ");
		sql.append(" inner join (select max(id) as id, idType, idCode, count(*) from " +
				"csms_hk_cardHolder where 1=1 ");

		SqlParamer sqlPar = new SqlParamer();
		if (StringUtils.isNotBlank(idType)) {
			sqlPar.eq("idType", idType);
		}

		if (StringUtils.isNotBlank(idCode)) {
			sqlPar.eq("idCode", idCode);
		}
		sql = sql.append(sqlPar.getParam());

		sql.append(" group by idType, idCode ) A on A.id = chc.id ");

		List<CardHolder> cardHolderList = super.queryObjectList(sql.toString(), CardHolder.class, sqlPar.getList()
				.toArray());
		if (null != cardHolderList && !cardHolderList.isEmpty()) {
			return cardHolderList.get(0);
		}

		return null;
	}

	/**
	 * 根据用户编号
	 *
	 * @param userNo
	 * 		用户编号
	 * @return 持卡人
	 * @author wangjinhao
	 */
	public CardHolder findByUserNo(String userNo) {
		String sql = "select * from csms_hk_cardHolder where userNo = ?";
		if (StringUtils.isNotBlank(userNo)) {
			List<CardHolder> list = super.queryObjectList(sql, CardHolder.class, userNo);
			if (null != list && !list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}

}