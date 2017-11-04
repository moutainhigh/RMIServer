/*package com.hgsoft.clearInterface.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.utils.UrlUtils;
@Component
public class AcInvoiceDao extends ClearBaseDao{
	@Autowired
	private UrlUtils urlUtils;
	
	public Pager findByPage(Pager pager,String cardNo,Date starTime,Date endTime) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by Reckontime desc) as num  from "+urlUtils.getEtctolluser()+".tb_AcInvoice t where 1=1 ");
		
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql.append(" and reckontime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS')  ");
		}
		if(endTime != null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql.append(" and reckontime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(cardNo==null || "".equals(cardNo) ){
			return this.findByPages(sql.toString(), pager,null);
		}else{
			sql=sql.append(" and accode=?");
			return this.findByPages(sql.toString(), pager,new Object[]{cardNo});
		}
			
		}
	public Map<String, Object> find(Long  reckonlistno) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from "+urlUtils.getEtctolluser()+".td_AcInvoice where 1=1 and  Reckonlistno=?");
		list=queryList(sql.toString(),reckonlistno);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public List<Map<String, Object>> findAll(List<Long> reckonlistnos) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SqlParamer pars=new SqlParamer();
		pars.in("reckonlistno", reckonlistnos);
		StringBuffer sql = new StringBuffer("select * from "+urlUtils.getEtctolluser()+".tb_AcInvoice where 1=1 ");
		sql.append(pars.getParam());
		list=queryList(sql.toString(),pars.getList().toArray());
		return list;
	}
	
	
	*//**
	 * 按年月、卡号(或银行账号)查找记帐卡账单
	 * @param accountCInfo
	 * @param accountNo
	 * @param year  YYYY
	 * @param month MM
	 * @return List<Map<String,Object>>
	 *//*
	public List<Map<String, Object>> findACBillByCardAccountNo(AccountCInfo accountCInfo,String accountNo,String year,String month){
		StringBuffer sql = new StringBuffer(
				"select dealnum dealNum,realfee*0.01 realDealFee,oncenum onceNum,oncefee*0.01 onceFee,othernum otherNum,otherfee*0.01 otherFee"
				+ " from "+urlUtils.getEtctolluser()+".tb_acinvoice where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(accountCInfo!=null&&StringUtil.isNotBlank(accountCInfo.getCardNo())){
			params.eq("cardcode", accountCInfo.getCardNo());
		}
		if(StringUtil.isNotBlank(accountNo)){
			params.eq("ACBAccount", accountNo);
		}
		if(StringUtil.isNotBlank(year)&&StringUtil.isNotBlank(month)){
			//params.geDate("Reckontime", year+month+"01000000");
			//params.leDate("Reckontime", year+month+"235959");
			params.eqYearMonth("SettleMonth", year+month);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}
	
	*//**
	 * 网上营业厅的查询账单方法
	 * @param cardUserNo
	 * @param cardType
	 * @param month
	 * @return List
	 *//*
	public List findQueryCardAcinvoice(String cardUserNo,String cardType,String month){
		StringBuffer sql = new StringBuffer(
				"select Dealfee \"Dealfee\",dealnum \"dealnum\",realfee \"realdealfee\",oncenum \"oncenum\",oncefee \"oncefee\",serverfee \"serverfee\",latefee \"latefee\",mendnum \"mendnum\",mendfee \"mendfee\",otherserverfee \"otherserverfee\",otherfee \"otherfee\",otherrealfee \"otherrealfee\",othernum \"othernum\" "
				+ " from "+urlUtils.getEtctolluser()+".tb_acinvoice where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardUserNo)&&StringUtil.isNotBlank(cardType)){
			if(cardType.equals("1")){
				params.eq("userno", cardUserNo);
			}else if(cardType.equals("2")){
				params.eq("cardcode", cardUserNo);
			}
			//params.eq("p.cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(month)){
			//params.geDate("SettleMonth", month+"01000000");
			//params.leDate("SettleMonth", month+DateUtil.getDaysByYearMonth(month)+"235959");
			params.eqYearMonth("SettleMonth", month);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		return queryList(sql.toString(),Objects);
	}
	
}
*/