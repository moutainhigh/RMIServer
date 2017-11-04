package com.hgsoft.macao.dao;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.macao.entity.MacaoBankAccount;

@Repository
public class MacaoBankAccountDao extends BaseDao{
	
	public List<Map<String,Object>> getBankAccountNumberList(String idCardType,String idCardNumber){
		String sql = "select mb.bankAccountNumber from csms_macao_bankAccount mb join csms_macao_card_customer mcc on mcc.id=mb.mainid where mcc.idCardType=? and mcc.idCardNumber=?";
		return queryList(sql,idCardType,idCardNumber);
	}
	
	public MacaoBankAccount findByBankAccountNumber(String bankAccountNumber) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_macao_bankaccount where bankAccountNumber=?";
		List<Map<String,Object>> list = queryList(sql,bankAccountNumber);
		MacaoBankAccount macaoBankAccount = null;
		if(list.size()>0){
			macaoBankAccount = new MacaoBankAccount();
			convert2Bean(list.get(0), macaoBankAccount);
		}
		return macaoBankAccount;
	}
	
	public MacaoBankAccount findByVehicleId(Long vehicleId) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "SELECT CMB.* FROM CSMS_MACAO_BANKACCOUNT CMB JOIN CSMS_CARDHOLDER_INFO CCI ON CMB.ID = CCI.MACAOBANKACCOUNTID WHERE CCI.TYPE = 1 AND CCI.TYPEID = ?";
		List<Map<String,Object>> list = queryList(sql,vehicleId);
		MacaoBankAccount macaoBankAccount = null;
		if(list.size()>0){
			macaoBankAccount = new MacaoBankAccount();
			convert2Bean(list.get(0), macaoBankAccount);
		}
		return macaoBankAccount;
	}
	public MacaoBankAccount findByTagNo(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mb.* from csms_macao_bankaccount mb join csms_cardholder_info ci on ci.macaobankaccountid = mb.id join csms_tag_info ti on ti.id = ci.typeid where ci.type = '3' and ti.tagno = ?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		MacaoBankAccount macaoBankAccount = null;
		if(list.size()>0){
			macaoBankAccount = new MacaoBankAccount();
			convert2Bean(list.get(0), macaoBankAccount);
		}
		return macaoBankAccount;
	}
	
	public MacaoBankAccount findByTagInfoId(Long tagInfoId) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mb.* from csms_macao_bankaccount mb join csms_cardholder_info ci on ci.macaobankaccountid = mb.id join csms_tag_info ti on ti.id = ci.typeid where ci.type = '3' and ti.id = ?";
		List<Map<String,Object>> list = queryList(sql,tagInfoId);
		MacaoBankAccount macaoBankAccount = null;
		if(list.size()>0){
			macaoBankAccount = new MacaoBankAccount();
			convert2Bean(list.get(0), macaoBankAccount);
		}
		return macaoBankAccount;
	}
	
	public MacaoBankAccount findByAccountCInfoId(Long accountCInfoId) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mb.* from csms_macao_bankaccount mb join csms_cardholder_info ci on mb.id=ci.macaobankaccountid where  ci.type='2' and ci.typeid=?";
		List<Map<String,Object>> list = queryList(sql,accountCInfoId);
		MacaoBankAccount macaoBankAccount = null;
		if(list.size()>0){
			macaoBankAccount = new MacaoBankAccount();
			convert2Bean(list.get(0), macaoBankAccount);
		}
		return macaoBankAccount;
		
	}
}
