package com.hgsoft.ivr.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.ivr.entity.ClientLoginInfo;
import com.hgsoft.ivr.entity.ReqInterfaceFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class IVRDao extends BaseDao{
	
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow){
		Map map = FieldUtil.getPreFieldMap(ReqInterfaceFlow.class,reqInterfaceFlow);
		StringBuffer sql=new StringBuffer("insert into CSMS_REQINTERFACE_FLOW");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	/**
	 * 找到的list字段为：
	 * cardno、cardServicePwd、tradingPwd、servicePwd、customerid
	 * 
	 * @param cardNo
	 * @param cardKind   0:普通粤通卡  1：快易通粤通卡
	 * @return
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findCardByNOandType(String cardNo,String cardKind){
		StringBuffer sql = new StringBuffer("");
		if(!StringUtil.isNotBlank(cardNo)||!StringUtil.isNotBlank(cardKind)){
			return null;
		}
		if("0".equals(cardKind)){
			sql.append("select a.cardno,a.CardServicePwd cardServicePwd,a.TradingPwd tradingPwd,c.ServicePwd servicePwd,c.id customerid from csms_accountc_info a "
					+ "join csms_customer c on a.customerid=c.id "
					+ "where c.systemtype='1' and a.cardno=? "
					+ "  union all "
					+ "select p.cardno,p.CardServicePwd cardServicePwd,p.TradingPwd tradingPwd,c.ServicePwd servicePwd,c.id customerid from csms_prepaidc p "
					+ "join csms_customer c on p.customerid=c.id "
					+ "where c.systemtype='1' and p.cardno=? ");
			return queryList(sql.toString(), cardNo,cardNo);
		}else if("1".equals(cardKind)){
			sql.append("select a.cardno,a.CardServicePwd cardServicePwd,a.TradingPwd tradingPwd,c.ServicePwd servicePwd,c.id customerid from csms_accountc_info a "
					+ "join csms_customer c on a.customerid=c.id "
					+ "where c.systemtype='2' and a.cardno=? "
					+ "  union all "
					+ "select p.cardno,p.CardServicePwd cardServicePwd,p.TradingPwd tradingPwd,c.ServicePwd servicePwd,c.id customerid from csms_prepaidc p "
					+ "join csms_customer c on p.customerid=c.id "
					+ "where c.systemtype='2' and p.cardno=? ");
			return queryList(sql.toString(), cardNo,cardNo);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据卡号，卡类型找记帐卡对象
	 * @param cardNo
	 * @param cardKind
	 * @return AccountCInfo
	 */
	public AccountCInfo findACByNOandType(String cardNo,String cardKind){
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		AccountCInfo accountCInfo = null;
		if(!StringUtil.isNotBlank(cardNo)||!StringUtil.isNotBlank(cardKind)){
			return null;
		}
		
		if("0".equals(cardKind)){
			sql.append("select a.* from csms_accountc_info a "
					+ "join csms_customer c on a.customerid=c.id "
					+ "where c.systemtype='1' and a.cardno=? ");
		}else if("1".equals(cardKind)){
			sql.append("select a.* from csms_accountc_info a "
					+ "join csms_customer c on a.customerid=c.id "
					+ "where c.systemtype='2' and a.cardno=? ");
		}
		
		list = queryList(sql.toString(), cardNo);
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}
	
	/**
	 * 根据卡号、卡类型查找储值卡对象
	 * @param cardNo
	 * @param cardKind
	 * @return PrepaidC
	 */
	public PrepaidC findPCByNOandType(String cardNo,String cardKind){
		StringBuffer sql = new StringBuffer("");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PrepaidC prepaidC = null;
		if(!StringUtil.isNotBlank(cardNo)||!StringUtil.isNotBlank(cardKind)){
			return null;
		}
		if("0".equals(cardKind)){
			sql.append("select p.* from csms_prepaidc p "
					+ "join csms_customer c on p.customerid=c.id "
					+ "where c.systemtype='1' and p.cardno=? ");
		}else if("1".equals(cardKind)){
			sql.append("select p.* from csms_prepaidc p "
					+ "join csms_customer c on p.customerid=c.id "
					+ "where c.systemtype='2' and p.cardno=? ");
		}
		
		list = queryList(sql.toString(), cardNo);
		
		if (!list.isEmpty()&&list.size()==1) {
			prepaidC = new PrepaidC();
			this.convert2Bean(list.get(0), prepaidC);
		}

		return prepaidC;
	}
	
	/**
	 * 用来判断银行账号与卡号的组合是否存在
	 * @param accountNo
	 * @param cardNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> checkAccountNOandCardNo(String accountNo,String cardNo){
		StringBuffer sql = new StringBuffer(
				"select * from csms_accountc_info a "
				+ " join CSMS_SubAccount_Info s on a.AccountID=s.id"
				+ " join CSMS_AccountC_apply aa on s.applyid=aa.id"
				+ " where aa.bankaccount=? and a.cardno=?");
		return queryList(sql.toString(), accountNo,cardNo);
	}
	
	/**
	 * 找到最近一条记帐卡挂失业务记录
	 * @param accountCInfo
	 * @return Map<String, Object>
	 */
	public Map<String, Object> findLastByCardNo(AccountCInfo accountCInfo){
		StringBuffer sql = new StringBuffer(
				" select * from (select row_number() over(order by id desc) num,a.* from csms_accountc_bussiness a "
				+ " where a.state='3' ");
		
		SqlParamer params=new SqlParamer();
		if(accountCInfo!=null&&StringUtil.isNotBlank(accountCInfo.getCardNo())){
			params.eq("a.cardno", accountCInfo.getCardNo());
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		sql.append(" ) where num=1 ");
		
		List<Map<String, Object>> resultList = queryList(sql.toString(), Objects);
		if(resultList!=null&&!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}
	/**
	 * 找到最近一条储值卡挂失业务记录
	 * @param prepaidC
	 * @return Map<String, Object>
	 */
	public Map<String, Object> findLastByCardNo(PrepaidC prepaidC){
		StringBuffer sql = new StringBuffer(
				" select * from (select row_number() over(order by id desc) num,p.* from csms_prepaidc_bussiness p "
				+ " where p.state='7' ");
		
		SqlParamer params=new SqlParamer();
		if(prepaidC!=null&&StringUtil.isNotBlank(prepaidC.getCardNo())){
			params.eq("p.cardno", prepaidC.getCardNo());
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		sql.append(" ) where num=1 ");
		
		List<Map<String, Object>> resultList = queryList(sql.toString(), Objects);
		if(resultList!=null&&!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}
	 /**
	  * IVR客户信息验证接口
	  * @param cli
	  * @return
	  */
	public List<Map<String, Object>> saveClientLoginInfo(ClientLoginInfo cli){
		Date loginTime = new Date();
		Date updateTime = loginTime;
		cli.setLoginTime(loginTime);
		cli.setUpdateTime(updateTime);
		Map<?, ?> map = FieldUtil.getPreFieldMap(ClientLoginInfo.class,cli);
		StringBuffer sql=new StringBuffer("insert into CSMS_CLIENT_LOGIN_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List<?>) map.get("param"));
		
		return null;
	}
	
	/**
	 * 
	 * @param tel
	 * @return
	 */
	public Map<String, Object> verifyClientLoginInfoByTel(String tel){
		StringBuffer sql = new StringBuffer(
				" select * from csms_client_login_info c "
				+ " where c.tel='"+tel+"' order by c.updatetime desc");
	 
		try {
			List<Map<String, Object>> resultList = queryList(sql.toString());
			if(resultList!=null && resultList.size() > 0){
				//验证通过，更改验证状态
				StringBuffer updateSql = new StringBuffer(
						" update csms_client_login_info c  set c.status='1'"
						+ " where c.status='0' and c.tel="+tel);
				saveOrUpdate(updateSql.toString());
				Map<String, Object> clientLoginInfo = resultList.get(0);
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap.put("clientLoginInfo", clientLoginInfo);
				
				return resMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	/**
	 * 清除验证信息
	 * @param tel
	 */
	public Map<String, String> clearClientLoginInfoByTel(String tel,List<?> list){
		StringBuffer sql = new StringBuffer(
				" insert into csms_client_clear_info ( select c.*,sysdate as cleartime from csms_client_login_info c "
				+ " where c.tel='"+tel+"' )");
		
		StringBuffer delSql = new StringBuffer(
				" delete from csms_client_login_info c "
				+ " where  c.tel='"+tel+"'");
		Map<String,String> resMap = new HashMap<String,String>();
		try {
			int i = 0;
			 i = saveOrUpdate(sql.toString(),list);
			delete(delSql.toString());
			if(i > 0){
				resMap.put("result", "sucess");
			}else{
				resMap.put("result", "fail");
				resMap.put("errorCode", "1");
			}
			return resMap;
		} catch (Exception e) {
			resMap.put("result", "fail");
			resMap.put("errorCode", "16");
			e.printStackTrace();
		}
		
		return resMap;
		
	}
	/**
	 * 按年月、卡号(或银行账号)查找记帐卡账单
	 * @param accountCInfo
	 * @param accountNo
	 * @param year  YYYY
	 * @param month MM
	 * @return List<Map<String,Object>>
	 */
	/*public List<Map<String, Object>> findACBillByCardAccountNo(AccountCInfo accountCInfo,String accountNo,String year,String month){
		StringBuffer sql = new StringBuffer(
				"select dealnum dealNum,realfee*0.01 realDealFee,oncenum onceNum,oncefee*0.01 onceFee,othernum otherNum,otherfee*0.01 otherFee"
				+ " from tb_acinvoice where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(accountCInfo!=null&&StringUtil.isNotBlank(accountCInfo.getCardNo())){
			params.eq("accode", accountCInfo.getCardNo());
		}
		if(StringUtil.isNotBlank(accountNo)){
			params.eq("usaccount", accountNo);
		}
		if(StringUtil.isNotBlank(year)&&StringUtil.isNotBlank(month)){
			//params.geDate("Reckontime", year+month+"01000000");
			//params.leDate("Reckontime", year+month+"235959");
			params.eqYearMonth("Reckontime", year+month);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}*/
	
	/**
	 * 按年月、卡号(或银行账号)查找记帐卡账单
	 * 注：金额的单位转化为元
	 * @param prepaidC
	 * @param year  YYYY
	 * @param month MM
	 * @return List<Map<String,Object>>
	 */
	/*public List<Map<String, Object>> findPCBillByCardNo(PrepaidC prepaidC,String year,String month){
		StringBuffer sql = new StringBuffer(
				"select dealnum dealNum,realdealfee*0.01 realDealFee,oncenum onceNum,oncefee*0.01 onceFee"
				+ " from tb_scinvoice where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(prepaidC!=null&&StringUtil.isNotBlank(prepaidC.getCardNo())){
			params.eq("sccode", prepaidC.getCardNo());
		}
		if(StringUtil.isNotBlank(year)&&StringUtil.isNotBlank(month)){
			//params.geDate("Reckontime", year+month+"01000000");
			//params.leDate("Reckontime", year+month+"235959");
			params.eqYearMonth("Reckontime", year+month);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}*/
	
}
