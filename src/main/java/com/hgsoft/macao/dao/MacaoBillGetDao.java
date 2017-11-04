package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.MacaoBillGet;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;
@Repository
public class MacaoBillGetDao extends BaseDao{
	
	public AccountCInfo getAccountCState(String cardNo) throws Exception{
		String sql = "select * from csms_accountc_info where cardNo=?";
		List<Map<String,Object>> list = queryList(sql,cardNo);
		AccountCInfo accountCInfo = null;
		if(list.size()>0){
			accountCInfo = new AccountCInfo();
			convert2Bean(list.get(0), accountCInfo);
		}
		return accountCInfo;
	}
	
	public void save(MacaoBillGet macaoBillGet) {
		macaoBillGet.setHisSeqId(-macaoBillGet.getId());
		Map map = FieldUtil.getPreFieldMap(MacaoBillGet.class,macaoBillGet);
		StringBuffer sql=new StringBuffer("insert into CSMS_MACAO_BILL_GET");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public Pager findByPage(Pager pager,MacaoBillGet macaoBillGet) {
		StringBuffer sql = new StringBuffer("SELECT mbg.*,ROWNUM as num from csms_macao_card_customer mcc JOIN csms_macao_bankaccount mb ON mb.mainid=mcc.id"
				+" JOIN csms_cardholder_info ci ON mb.id=ci.macaobankaccountid"
				+"	JOIN csms_accountc_info ai ON ci.typeid=ai.id"
				+"	JOIN csms_macao_bill_get mbg ON ai.cardno=mbg.cardbankno WHERE mcc.id="+macaoBillGet.getMainId());
//		Map map = FieldUtil.getPreFieldMap(MacaoBillGet.class,macaoBillGet);
//		if (macaoBillGet != null) {
//			sql.append(map.get("selectNameStrNotNullAndWhere"));
//			/*sql.append(FieldUtil.getFieldMap(BillGet.class,billGet).get("nameAndValueNotNull"));*/
//		}
		sql.append(" order by mbg.opertime desc ");
		return this.findByPages(sql.toString(),pager,null);
	}
	
	public MacaoBillGet findById(Long id){
		String sql = "select * from CSMS_MACAO_BILL_GET where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		MacaoBillGet macaoBillGet = null;
		if (!list.isEmpty()) {
			macaoBillGet = new MacaoBillGet();
			this.convert2Bean(list.get(0), macaoBillGet);
		}

		return macaoBillGet;
	}
	
	/**
	 * 根据卡号/银行账号查
	 * @return MacaoBillGet
	 */
	public MacaoBillGet findByCardBankNo(String cardBankNo){
		String sql = "select * from CSMS_MACAO_BILL_GET where cardBankNo=? order by endtime desc";
		List<Map<String, Object>> list = queryList(sql,cardBankNo);
		MacaoBillGet macaoBillGet = null;
		if (!list.isEmpty()) {
			macaoBillGet = new MacaoBillGet();
			this.convert2Bean(list.get(0), macaoBillGet);
		}

		return macaoBillGet;
	}
	
	public void deleteById(Long id){
		String sql = "delete from CSMS_MACAO_BILL_GET where id=?";
		delete(sql, id);
	}
	
	public void update(MacaoBillGet macaoBillGet) {
		Map map = FieldUtil.getPreFieldMap(MacaoBillGet.class,macaoBillGet);
		StringBuffer sql=new StringBuffer("update CSMS_MACAO_BILL_GET set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),macaoBillGet.getId());
	}
	
	public MacaoCardCustomer getMacaoCardCustomerInfo(String cardNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mcc.* from csms_cardholder_info ci join csms_macao_bankaccount mb on ci.MACAOBANKACCOUNTID=mb.id "
				+ "join csms_macao_card_customer mcc on mb.mainid=mcc.id "
				+ "join CSMS_AccountC_info ai on ci.typeid=ai.id where ci.type='2' and ai.cardNo=?";
		List<Map<String,Object>> list = queryList(sql, cardNo);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			convert2Bean(list.get(0), macaoCardCustomer);
		}
		return macaoCardCustomer;
	}
}
