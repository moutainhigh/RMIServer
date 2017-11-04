package com.hgsoft.bank.dao;

import com.alibaba.fastjson.JSONObject;
import com.hgsoft.agentCard.entity.CardBusinessInfo;
import com.hgsoft.bank.entity.BankInterface;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 吴锡霖
 *         file : BankInterfaceDao.java
 *         date : 2017-07-10
 *         time : 13:53
 */
@Repository
public class BankInterfaceDao extends BaseDao {

    @Resource
    private SequenceUtil sequenceUtil;

    public void save(BankInterface bankInterface) {
        bankInterface.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_BANK_INTERFACE_NO"));
        Map map = FieldUtil.getPreFieldMap(BankInterface.class, bankInterface);
        StringBuffer sql = new StringBuffer("insert into CSMS_BANK_INTERFACE_INFO");
        sql.append(map.get("insertNameStrNotNull"));
        saveOrUpdate(sql.toString(),
                (List) map.get("paramNotNull"));
    }

    public void update(BankInterface bankInterface) {
		StringBuffer sql = new StringBuffer(
				"update CSMS_BANK_INTERFACE_INFO set ");
		sql.append(FieldUtil.getFieldMap(BankInterface.class, bankInterface)
				.get("nameAndValue") + " where id=" + bankInterface.getId());
		update(sql.toString());
	}


    /***
     * 查找存在09销卡业务记录且不存在换卡、补领、非过户补领
     * @param cardNo
     * @param creditCardNo
     * @return
     */
    public List<CardBusinessInfo> findAvailableCancelRecord(String cardNo,String creditCardNo) {
        StringBuffer sql = new StringBuffer(  "select * from CSMS_BANK_INTERFACE_INFO p where " +
                "isSuccess = 1 and code = '009108' " +
                "AND UTCardNo = ? " +
                "AND creditCardNo = ? " +
                " AND NOT EXISTS( SELECT * FROM CSMS_BANK_INTERFACE_INFO T WHERE isSuccess = 1 AND code  IN ('009104', '009106', '009113') AND P .UTCARDNO = T .OLDUTCARDNO) ");


        List<BankInterface> mapList = queryObjectList(sql.toString(), BankInterface.class, cardNo,creditCardNo);
        List<CardBusinessInfo> cardBusinessInfos = new ArrayList<>();
		for (BankInterface bankInterface : mapList) {
			CardBusinessInfo cardBusinessInfo = JSONObject.parseObject(
					bankInterface.getCardBusinessInfo(),
					CardBusinessInfo.class);
            cardBusinessInfos.add(cardBusinessInfo);
        }
        return cardBusinessInfos;

    }

    /***
     * 查找存在09销卡业务记录且不存在换卡、补领、非过户补领
     * @param cardNo
     * @param creditCardNo
     * @return
     */
    public List<CardBusinessInfo> findAccountCByCardNoType(String cardNo,String creditCardNo,String oldCardNo,String oldCreditCardNo , String businessType) {
        StringBuffer sql = new StringBuffer(  "select * from CSMS_BANK_INTERFACE_INFO where " +
                "isSuccess = 1 and code between '009100' and '009119'");
        SqlParamer params=new SqlParamer();
        if(StringUtil.isNotBlank(cardNo)){
            params.eq("UTCardNo", cardNo);
        }
        if(StringUtil.isNotBlank(creditCardNo)){
            params.eq("creditCardNo", creditCardNo);
        }
        if(StringUtil.isNotBlank(oldCardNo)){
            params.eq("oldUTCardNo", oldCardNo);
        }
        if(StringUtil.isNotBlank(oldCreditCardNo)){
            params.eq("oldCreditCardNo", oldCreditCardNo);
        }
        if(StringUtil.isNotBlank(businessType)){
            params.eq("businessType", businessType);
        }
        sql.append(params.getParam());
        List list = params.getList();
        Object[] Objects= list.toArray();

        List<BankInterface> mapList = queryObjectList(sql.toString(), BankInterface.class, Objects);
        List<CardBusinessInfo> cardBusinessInfos = new ArrayList<>();
        for (BankInterface bankInterface : mapList) {
            CardBusinessInfo cardBusinessInfo = JSONObject.parseObject(
                    bankInterface.getCardBusinessInfo(),
                    CardBusinessInfo.class);
            cardBusinessInfos.add(cardBusinessInfo);
        }
        return cardBusinessInfos;


    }
}
