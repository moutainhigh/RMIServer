package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class ReportDao extends BaseDao{
	
	public Pager getStopCardBlackList(Pager pager, String cardNo, String startTime,String endTime,MacaoCardCustomer macaoCardCustomer){
		StringBuffer sql = new StringBuffer(" select cb.cardcode cardCode,to_char(cb.gentime,'yyyy/mm/dd hh24:mi:ss') genTime,'止付' type,cb.gencau genCau"
										   +" from CSMS_STOPCARDBLACKLIST cb"
										   +" join csms_accountc_info ai"
										   +" on cb.cardcode = ai.cardno"
										   +" join csms_cardholder_info ci"
										   +" on ai.id = ci.typeid"
										   +" join csms_macao_bankaccount mb"
										   +" on ci.macaobankaccountid = mb.id"
										   +" join csms_macao_card_customer mcc"
										   +" on mcc.id = mb.mainid"
										   +" where cb.gencau in ('1','4')");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("cb.cardcode", cardNo);
		}
		if(StringUtil.isNotBlank(startTime)){
			sqlp.ge("to_char(cb.gentime,'yyyy/MM/dd')", startTime);
		}
		if(StringUtil.isNotBlank(endTime)){
			sqlp.le("to_char(cb.gentime,'yyyy/MM/dd')", endTime);
		}
		if(macaoCardCustomer != null){
			if(StringUtil.isNotBlank(macaoCardCustomer.getCnName())){
				sqlp.eq("mcc.cnName", macaoCardCustomer.getCnName());
			}
			if(StringUtil.isNotBlank(macaoCardCustomer.getIdCardType())){
				sqlp.eq("mcc.idCardType", macaoCardCustomer.getIdCardType());
			}
			if(StringUtil.isNotBlank(macaoCardCustomer.getIdCardNumber())){
				sqlp.eq("mcc.idCardNumber", macaoCardCustomer.getIdCardNumber());
			}
		}
		sql=sql.append(sqlp.getParam());
		sql.append(" order by ai.issuetime desc ");
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	/**
	 * 根据粤通卡卡号获取粤通卡信息
	 * 
	 * */
	public Pager getAccountCInfoByCardNo(Pager pager,String cardNo,MacaoCardCustomer macaoCardCustomer) {
		StringBuffer sql = new StringBuffer("select ai.* from csms_accountc_info ai join CSMS_Customer c on ai.customerid=c.id join csms_cardholder_info ci on ci.typeid=ai.id join csms_macao_bankaccount mb on mb.id=ci.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid where c.systemType='3' and ci.type='2' ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("ai.cardNo", cardNo);
		}
		if(macaoCardCustomer != null){
			if(StringUtil.isNotBlank(macaoCardCustomer.getCnName())){
				sqlp.eq("mcc.cnName", macaoCardCustomer.getCnName());
			}
			if(StringUtil.isNotBlank(macaoCardCustomer.getIdCardType())){
				sqlp.eq("mcc.idCardType", macaoCardCustomer.getIdCardType());
			}
			if(StringUtil.isNotBlank(macaoCardCustomer.getIdCardNumber())){
				sqlp.eq("mcc.idCardNumber", macaoCardCustomer.getIdCardNumber());
			}
		}
		sql=sql.append(sqlp.getParam());
		sql.append(" order by ai.issuetime desc ");
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	/**
	 * 根据粤通卡卡号和产生时间获取黑名单信息
	 * 
	 * */
	public Pager getAccountCInfoByCardNoAndGenTime(Pager pager,String cardNo,String startTime,String endTime) {
		StringBuffer sql = new StringBuffer("select dl.cardNo cardNo,dl.genCau genCau,to_char(dl.genDate,'yyyy-mm-dd') genDate from csms_dark_list dl join csms_customer c on dl.customerid=c.id where systemtype='3' and dl.cardtype='1' ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(startTime)){
			sqlp.ge("to_char(genDate,'yyyy/MM/dd')", startTime);
		}
		if(StringUtil.isNotBlank(endTime)){
			sqlp.le("to_char(genDate,'yyyy/MM/dd')", endTime);
		}
		sql=sql.append(sqlp.getParam());
//		return null;
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	/**
	 * 查询结算月
	 */
	
	public List<Map<String, Object>> findSettlementPeriod(String date){
		String sql  ="SELECT * from CSMS_SettlementPeriod sp WHERE sp.month='"+date+"' order by sp.periods asc";
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}
	
	public List<Map<String,Object>> getAllServiceType(){
		String sql = "select * from csms_sertype";
		return queryList(sql);
	}
}
