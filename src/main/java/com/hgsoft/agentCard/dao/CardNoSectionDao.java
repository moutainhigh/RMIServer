package com.hgsoft.agentCard.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.agentCard.entity.CardNoSection;
import com.hgsoft.agentCard.entity.JoinCardNoSection;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

/**
 * @Description: card no. section
 * @author gaosiling
 * @Date 2016年8月6日 09:56:34
*/
@Repository
public class CardNoSectionDao extends BaseDao {

	
	
	/*public void save(CardNoSection cardNoSection){
		Map map = FieldUtil.getPreFieldMap(CardNoSection.class,cardNoSection);
		StringBuffer sql=new StringBuffer("insert into CSMS_CARD_NO_SECTION");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}*/
	
	public void save(JoinCardNoSection joinCardNoSection){
		Map map = FieldUtil.getPreFieldMap(JoinCardNoSection.class,joinCardNoSection);
		StringBuffer sql=new StringBuffer("insert into CSMS_joinCardNoSection");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	/*public List findList(CardNoSection cardNoSection,String cardNo){
		String sql = "select id,type,obaNo,startCardNo,endCardNo from CSMS_CARD_NO_SECTION "
				+ " where ? between startCardNo and endCardNo and type = ? and obano = ? ";
		System.out.println(sql);
		List<Map<String, Object>> list = queryList(sql,cardNo,cardNoSection.getType(),cardNoSection.getObaNo());
		return list;
	}*/
	
	public List findList(JoinCardNoSection joinCardNoSection,String cardNo){
		String sql = "select id,cardType,bankNo,code,EndCode from CSMS_joinCardNoSection "
				+ " where ? between code and endcode and CARDTYPE = ? and BANKNO = ? ";
		System.out.println(sql);
		List<Map<String, Object>> list = queryList(sql,cardNo,joinCardNoSection.getCardType(),joinCardNoSection.getBankNo());
		return list;
	}
	/**
	 * 允许跨行充值
	 * @param joinCardNoSection
	 * @param cardNo
	 * @return
	 */
	public List findList1(JoinCardNoSection joinCardNoSection,String cardNo){
		String sql = "select id,cardType,bankNo,code,EndCode from CSMS_joinCardNoSection "
				+ " where ? between code and endcode and CARDTYPE = ? ";
		System.out.println(sql);
		List<Map<String, Object>> list = queryList(sql,cardNo,joinCardNoSection.getCardType());
		return list;
	}

	/***
	 * 查询卡号段
	 * @param joinCardNoSection
	 * @return
     */
	public List findCardNoSegment(JoinCardNoSection joinCardNoSection){
		String sql = "select code,EndCode from CSMS_joinCardNoSection "
				+ " where CARDTYPE = ? and BANKNO = ? ";
		System.out.println(sql);
		List<Map<String, Object>> list = queryList(sql,joinCardNoSection.getCardType(),joinCardNoSection.getBankNo());
		return list;
	}
	

}
