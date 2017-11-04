package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class IvrAccountCDao extends BaseDao{

	public Pager finCustomerInfo(Pager pager, String ivrValidateCustomer,
			String artificialValidateCustomer) {
		StringBuffer sql = new StringBuffer("select aa.id aaid,"
										  +" c.id cid,"
										  +" c.userno userNo,"
										  +" aa.bankAccount bankAccount,"
										  +" c.placeno placeno,"
										  +" aa.virtype virType,"
										  +" '' sign,"
										  +" c.zipcode zipCode,"
										  +" c.usertype userType,"
										  +" aa.InvoicePrn invoicePrn,"
										  +" aa.bankName bankName,"
										  +" '' failMoney,"
										  +" aa.AccountType accountType,"
										  +" '' cancelDate,"
										  +" '没有此字段' name,"
										  +" '没有此字段' fax,"
										  +" c.Organ organ,"
										  +" c.Email email,"
										  +" c.LinkMan linkMan,"
										  +" aa.AppTime appTime,"
										  +" c.IdType IdType,"
										  +" '' cardNumber,"
										  +" c.IdCode idCode,"
										  +" '' limitMoney,"
										  +" c.Tel tel,"
										  +" c.Addr addr"
										  +" from csms_customer c"
										  +" join csms_accountc_info ai"
										  +" on c.id = ai.customerid"
										  +" join csms_accountc_apply aa"
										  +" on c.id = aa.customerid "
										  +" where 1 = 1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(ivrValidateCustomer)){
			sqlp.eq("c.userno", ivrValidateCustomer);
		}
		if(StringUtil.isNotBlank(artificialValidateCustomer)){
			sqlp.eq("c.userno", artificialValidateCustomer);
		}
		sql=sql.append(sqlp.getParam());
		pager = this.findByPages(sql.toString(), pager,sqlp.getList().toArray());

		return pager;
		
	}
	
	public void updateCustomer(String cid,String zipCode,String organ,String email,String linkMan,String idType,String idCode,String tel,String addr){
		String sql = "update csms_customer set zipcode=?,organ=?,email=?,linkman=?,idtype=?,idcode=?,tel=?,addr=? where id=?";
		saveOrUpdate(sql, zipCode,organ,email,linkMan,idType,idCode,tel,addr,cid);
	}
	
	public void updateAccountCApply(String aaid,String invoicePrn,String linkMan,String tel){
		String sql = "update csms_accountc_apply set invoiceprn=?,linkman=?,tel=? where id=?";
		saveOrUpdate(sql, invoicePrn,linkMan,tel,aaid);
	}
	
	public Pager findAccountCMessage(Pager pager, String ivrValidateCustomer,String artificialValidateCustomer){
		StringBuffer sql =new StringBuffer("select c.id cid,"
                						+" bg.id bgid,"
				                        +" c.organ organ,"
										+" '无' invoicename,"
										+" c.idtype idtype,"
										+" c.idcode idcode,"
										+" c.zipcode zipcode,"
										+" c.addr addr,"
										+" c.shorttel shorttel,"
										+" c.email email,"
										+" '无' mailinginvoice,"
										+" '无' emailservice,"
										+" bg.seritem seritem"
										+" from csms_customer c"
										+" join csms_bill_get bg on "
										+" bg.mainid=c.id");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(ivrValidateCustomer)){
			sqlp.eq("c.userno", ivrValidateCustomer);
		}
		if(StringUtil.isNotBlank(artificialValidateCustomer)){
			sqlp.eq("c.userno", artificialValidateCustomer);
		}
		sql=sql.append(sqlp.getParam());
		pager = this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		return pager;
	}
	
	public void updateAccountCMessage(String bgid,String seritem){
		String sql = "update csms_bill_get set seritem=?where id=?";
		saveOrUpdate(sql,seritem,bgid);
	}
	public Pager findAccountCInfo(Pager pager,String code,String flag){
		StringBuffer sql = new StringBuffer("select c.organ organ, "
				  +"c.userno userno, "  
				  +"c.idcode idcode, "  
			      +"c.tel tel, "  
			      +"c.addr addr, "  
			      +"c.email email, "  
			      +"c.updatetime updatetime, "  
			      +"c.usertype usertype, "  
			      +"c.idtype idtype, "  
			      +"c.linkman linkman, "  
			      +"c.mobile mobile, "  
			      +"c.zipcode zipcode, "  
			      +"c.opername opername, "
			      +"c.secondno secondno, "
			      +"c.secondname secondname, "
			      +"c.systemtype systemType, "
			      +"ai.cardno cardno, "  
			      +"ai.state state, "
			      +"ai.blackflag blackflag, "
			      +"aa.bankname bank, "
			      +"aa.bankname bankname, " 
			      +"aa.bankaccount bankaccount, "  
			      +"aa.maxacr maxacr, " 
			      +"ai.bail bail, "  
			      +"aa.virtype virtype, "  
			      +"vi.vehicleplate, " 
			      +"ai.issuetime issuetime, "  
			      +"bs.status bs_status, "  
			      +"bs.genmode genmode "
			  +" from csms_customer c  "
			  +" left join csms_accountc_apply aa on aa.customerid = c.id "
			  +" left join csms_subaccount_info si on si.applyid = aa.id "
			  +" left join csms_accountc_info ai on ai.accountid = si.id "
			  +" left join csms_carobucard_info ci on ci.accountcid = ai.id "
			  +" left join Csms_Vehicle_Info vi on vi.id = ci.vehicleid "
			  +" left join CSMS_BLACKLIST_TEMP bs on ai.cardno = bs.cardno "
			 +" where 1 = 1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(flag)){
			if("1".equals(flag)){//根据客户号查询
				sqlp.eq("c.userno", code);
			}else if("2".equals(flag)){//根据银行号查询
				sqlp.eq("aa.bankaccount", code);
			}else if("3".equals(flag)){//根据记帐卡号查询
				sqlp.eq("ai.cardno", code);
			}else if("4".equals(flag)){//根据车牌号查询
			 sqlp.eq("vi.vehicleplate", code.split("_")[0]);
			 sqlp.eq("vi.vehiclecolor", code.split("_")[2]);
			} 
		}else{
			sqlp.eq("ai.cardno", code);
		}
		sql=sql.append(sqlp.getParam());
		pager = this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		return pager;
	}
	public void updateState(String cardno){
		String sql = "update csms_accountc_info set state='1', blackFlag = '1' where cardno=?";
		saveOrUpdate(sql, cardno);
	}
	
	public AccountCInfo getAccountCInfoById(String cardNo){
		  return find(new AccountCInfo(cardNo));
	}
	
	
	public AccountCInfo find(AccountCInfo accountCInfo) {
		AccountCInfo temp = null;
		if (accountCInfo != null) {
			StringBuffer sql = new StringBuffer("select * from csms_accountc_info where 1=1 ");
			 
			Map map = FieldUtil.getPreFieldMap(AccountCInfo.class,accountCInfo);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List)map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new AccountCInfo();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

	public String getAccountCTotalBail(String userNo) {
		StringBuffer sql = new StringBuffer(" select sum(ai.bail)  total from csms_customer  c "
				+" left join csms_accountc_info ai on c.id = ai.customerid "
				+" where 1 = 1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("c.userno",userNo);
		}
		sql.append(params.getParam());
		sql.append(" group by c.userno ");
		List<Map<String,Object>> resList = queryList(sql.toString(),params.getList().toArray());
		if(resList.size() > 0){
			return  String.valueOf(resList.get(0).get("total"));
		}else{
			return "0";
		}
	}

	public List<Map<String, Object>> findCustomerByCardNo(String cardno) {
		StringBuffer sql = new StringBuffer(" select c.* from csms_accountc_info ai join csms_customer c on ai.customerid=c.id  where ai.cardno=?");
		return  queryList(sql.toString(),cardno);
	}
}

