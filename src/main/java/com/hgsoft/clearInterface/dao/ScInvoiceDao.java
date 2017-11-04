/*package com.hgsoft.clearInterface.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.utils.UrlUtils;
@Component
public class ScInvoiceDao extends ClearBaseDao{
	@Autowired
	private UrlUtils urlUtils;
	
	public Pager findByPage(Pager pager,String cardNo,Date starTime,Date endTime) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by Reckontime desc) as num  from "+urlUtils.getEtctolluser()+".TB_ScInvoice t where 1=1 and sccode="+cardNo);
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql.append(" and reckontime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS')  ");
		}
		if(endTime != null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql.append(" and reckontime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
			return this.findByPages(sql.toString(), pager,null);
		}
	public Map<String, Object> find(Long reckonlistno) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from "+urlUtils.getEtctolluser()+".tb_ScInvoice where 1=1 and reckonlistno=?");
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
		StringBuffer sql = new StringBuffer("select * from "+urlUtils.getEtctolluser()+".tb_ScInvoice where 1=1 ");
		sql.append(pars.getParam());
		list=queryList(sql.toString(),pars.getList().toArray());
		return list;
	}
	
	
	*//**
	 * 按年月、卡号(或银行账号)查找记帐卡账单
	 * 注：金额的单位转化为元
	 * @param prepaidC
	 * @param year  YYYY
	 * @param month MM
	 * @return List<Map<String,Object>>
	 *//*
	public List<Map<String, Object>> findPCBillByCardNo(PrepaidC prepaidC,String year,String month){
		StringBuffer sql = new StringBuffer(
				"select dealnum dealNum,realdealfee*0.01 realDealFee,oncenum onceNum,oncefee*0.01 onceFee,"
				+ "Chargenum chargeNum,Chargemoney*0.01 chargeFee,ReturnMoney*0.01 discountFee,TransferSum*0.01 transferFee"
				+ " from "+urlUtils.getEtctolluser()+".tb_scinvoice where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(prepaidC!=null&&StringUtil.isNotBlank(prepaidC.getCardNo())){
			params.eq("cardcode", prepaidC.getCardNo());
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
}
*/