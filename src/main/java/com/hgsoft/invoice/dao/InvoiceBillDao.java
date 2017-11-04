package com.hgsoft.invoice.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.invoice.entity.AddBill;
import com.hgsoft.invoice.entity.InvoiceRecord;
import com.hgsoft.invoice.entity.PassBill;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class InvoiceBillDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;

	public Pager getClearACBillByBankNo(Pager pager, String bankNo,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer(
				"select distinct organ,userno,acbaccount,'1' billtype,"
						+ " sum(DEALNUM + ONCENUM+MENDNUM+OTHERNUM+CONSUMENUM)"
						+ " over(partition by ACBACCOUNT,SETTLEMONTH) PASSNUM,"
						+ " sum(REALFEE+SERVERFEE+LATEFEE+ONCEREALFEE+ MENDFEE+OTHERREALFEE+OTHERSERVERFEE+CONSUMEFEE)"
						+ " over(partition by ACBACCOUNT,SETTLEMONTH) PASSFEE,to_char(SETTLEMONTH,'yyyy-MM') SETTLEMONTH"
						+ " from tb_acinvoice ai , csms_accountc_apply ap where ai.acbaccount=ap.bankaccount and ap.invoiceprn='2' ");
		sql = sql.append(" AND ACBACCOUNT = '" + bankNo + "' ");
		sql = sql.append(" AND SETTLEMONTH BETWEEN " + "to_date('" + startDate
				+ "','yyyy-MM')");
		sql = sql.append(" AND " + "to_date('" + endDate + "','yyyy-MM')");
		sql = sql.append(" order by SETTLEMONTH desc");
		return this.findByPages(sql.toString(), pager, null);
	}

	public List<Map<String, Object>> getACPassBillByBankNo(String bankNo,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer(
				"select * from csms_pass_bill where cardtype='2'");
		sql = sql.append(" AND bankno = '" + bankNo + "' ");
		sql = sql.append(" AND PASSMONTH BETWEEN " + "to_date('" + startDate
				+ "','yyyy-MM')");
		sql = sql.append(" AND " + "to_date('" + endDate + "','yyyy-MM')");

		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public Pager getClearACBillByCardNo(Pager pager, String cardNo,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer(
				"select organ,cardcode cardno,userno,acbaccount,'2' billtype,"
						+ " DEALNUM + ONCENUM+MENDNUM+OTHERNUM+CONSUMENUM PASSNUM,"
						+ " REALFEE+SERVERFEE+LATEFEE+ONCEREALFEE+ MENDFEE+OTHERREALFEE+OTHERSERVERFEE+CONSUMEFEE PASSFEE,"
						+ "to_char(SETTLEMONTH,'yyyy-MM') SETTLEMONTH from tb_acinvoice ta,csms_accountc_info cai,csms_subaccount_info csi,csms_accountc_apply caa where "
						+ "ta.cardcode=cai.cardno and cai.accountid=csi.id and caa.subaccountno=csi.subaccountno and caa.invoiceprn='1'");
		sql = sql.append(" and cardcode = '" + cardNo+"'");
		sql = sql.append(" and SETTLEMONTH BETWEEN to_date('" + startDate
				+ "','yyyy-MM') and to_date('" + endDate + "','yyyy-MM')");
		sql = sql.append(" order by SETTLEMONTH desc");
		return this.findByPages(sql.toString(), pager, null);
	}

	public List<Map<String, Object>> getACPassBillByCardNo(String cardNo,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer(
				"select * from csms_pass_bill where cardtype='2' ");
		sql = sql.append(" AND cardno = '" + cardNo + "' ");
		sql = sql.append(" AND PASSMONTH BETWEEN " + "to_date('" + startDate
				+ "','yyyy-MM')");
		sql = sql.append(" AND " + "to_date('" + endDate + "','yyyy-MM')");

		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public List<PassBill> getACBillByCardno(String cardNo, String billMonth) {
		StringBuffer sql = new StringBuffer(
				"select * from CSMS_PASS_BILL where 1=1");
		sql = sql.append(" AND CARDNO='" + cardNo + "'");
		sql = sql.append(" AND PASSMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
		return jdbcUtil.getJdbcTemplate().query(sql.toString(),
				new RowMapper<PassBill>() {
					@Override
					public PassBill mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PassBill tmp = new PassBill();
						tmp.setID(rs.getLong("id"));
						tmp.setCardNo(rs.getString("cardno"));
						tmp.setUserNo(rs.getString("userno"));
						tmp.setCardType(rs.getString("cardtype"));
						tmp.setBankNo(rs.getString("bankno"));
						tmp.setPassMonth(rs.getDate("passmonth"));
						tmp.setPassNum(rs.getInt("passnum"));
						tmp.setPassFee(new BigDecimal(rs.getDouble("passfee")));
						tmp.setMendNum(rs.getInt("mendnum"));
						tmp.setMendFee(new BigDecimal(rs.getDouble("mendfee")));
						tmp.setOtherNum(rs.getInt("othernum"));
						tmp.setOtherFee(new BigDecimal(rs.getDouble("otherfee")));
						tmp.setPrintNum(rs.getInt("printnum"));
						tmp.setOnceNum(rs.getInt("oncenum"));
						tmp.setOnceFee(new BigDecimal(rs.getDouble("oncefee")));
						tmp.setState(rs.getString("state"));
						tmp.setOfficeDealNum(rs.getInt("officedealnum"));
						tmp.setOfficeDealFee(new BigDecimal(rs
								.getDouble("officedealfee")));
						tmp.setManageFee(new BigDecimal(rs
								.getDouble("managefee")));
						tmp.setServiceFee(new BigDecimal(rs
								.getDouble("servicefee")));
						tmp.setLateFee(new BigDecimal(rs.getDouble("latefee")));
						tmp.setBackFee(new BigDecimal(rs.getDouble("backfee")));
						tmp.setCardNos(rs.getString("cardnos"));

						return tmp;
					}
				});
	}

	public List<PassBill> getACBillByBankno(String bankNo, String billMonth) {
		StringBuffer sql = new StringBuffer(
				"select * from CSMS_PASS_BILL where 1=1");
		sql = sql.append(" AND BANKNO='" + bankNo+"'");
		sql = sql.append(" AND PASSMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
		return jdbcUtil.getJdbcTemplate().query(sql.toString(),
				new RowMapper<PassBill>() {
					@Override
					public PassBill mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PassBill tmp = new PassBill();
						tmp.setID(rs.getLong("id"));
						tmp.setCardNo(rs.getString("cardno"));
						tmp.setUserNo(rs.getString("userno"));
						tmp.setCardType(rs.getString("cardtype"));
						tmp.setBankNo(rs.getString("bankno"));
						tmp.setPassMonth(rs.getDate("passmonth"));
						tmp.setPassNum(rs.getInt("passnum"));
						tmp.setPassFee(new BigDecimal(rs.getDouble("passfee")));
						tmp.setMendNum(rs.getInt("mendnum"));
						tmp.setMendFee(new BigDecimal(rs.getDouble("mendfee")));
						tmp.setOtherNum(rs.getInt("othernum"));
						tmp.setOtherFee(new BigDecimal(rs.getDouble("otherfee")));
						tmp.setPrintNum(rs.getInt("printnum"));
						tmp.setOnceNum(rs.getInt("oncenum"));
						tmp.setOnceFee(new BigDecimal(rs.getDouble("oncefee")));
						tmp.setState(rs.getString("state"));
						tmp.setOfficeDealNum(rs.getInt("officedealnum"));
						tmp.setOfficeDealFee(new BigDecimal(rs
								.getDouble("officedealfee")));
						tmp.setManageFee(new BigDecimal(rs
								.getDouble("managefee")));
						tmp.setServiceFee(new BigDecimal(rs
								.getDouble("servicefee")));
						tmp.setLateFee(new BigDecimal(rs.getDouble("latefee")));
						tmp.setBackFee(new BigDecimal(rs.getDouble("backfee")));
						tmp.setCardNos(rs.getString("cardnos"));

						return tmp;
					}
				});
	}

	public List<Map<String, Object>> getClearACBillByCardNo(String cardNo,
			String billMonth) {
		StringBuffer sql = new StringBuffer(
				"select ta.* from  tb_acinvoice ta,csms_accountc_info cai,csms_subaccount_info csi,csms_accountc_apply caa where 1=1"
				+" and ta.cardcode=cai.cardno and cai.accountid=csi.id and caa.subaccountno=csi.subaccountno and caa.invoiceprn='1'");
		sql = sql.append(" AND CARDCODE='" + cardNo+"'");
		sql = sql.append(" AND SETTLEMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public List<Map<String, Object>> getClearACBillByBankNo(String bankNo,
			String billMonth) {
		StringBuffer sql = new StringBuffer(
				"select ai.* from  tb_acinvoice ai , csms_accountc_apply ap where ai.acbaccount=ap.bankaccount and ap.invoiceprn='2' ");
		sql = sql.append(" AND ACBACCOUNT='" + bankNo + "'");
		sql = sql.append(" AND SETTLEMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public List<Map<String, Object>> getACClearDetails(String cardNo,
			String billMonth) {
		StringBuffer sql = new StringBuffer(
				"select cardcode,etcmoney from TB_ACINVOICEDETAILS where cardcode='"
				+ cardNo+"' and EXTIME=to_date('"+billMonth+"','yyyy-MM')");
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	@SuppressWarnings("rawtypes")
	public void saveBill(PassBill passBill) {
		Map map = FieldUtil.getPreFieldMap(PassBill.class, passBill);
		StringBuffer sql = new StringBuffer("insert into CSMS_PASS_BILL");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	@SuppressWarnings("rawtypes")
	public void saveInvoice(InvoiceRecord invoice) {
		Map map = FieldUtil.getPreFieldMap(InvoiceRecord.class, invoice);
		StringBuffer sql = new StringBuffer("insert into CSMS_INVOICE_RECORD");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public String checkCardPrint(String cardNo,
			String printType) {
		
		List<Map<String,Object>> tmp = new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer("select '0' invoiceprn from dual");
		if(printType.equals("1")){//按卡号打印

			sql = new StringBuffer("select ac.cardno,ap.invoiceprn from csms_accountc_info ac,"
					+ "csms_subaccount_info su,csms_accountc_apply ap where ac.cardno = '"+cardNo+"'"
					+ " and ac.accountid = su.id and ap.subaccountno=su.subaccountno");
			System.out.println("type"+1);
		}else if (printType.equals("2")){//按银行卡卡号打印
			sql = new StringBuffer("select invoiceprn from csms_accountc_apply where bankaccount = '"+cardNo+"'");
			System.out.println("type"+2);
		}
		
		tmp = jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
		
		if(tmp.size()>0){
			return tmp.get(0).get("invoiceprn").toString();
		}
		
		return "0";
	}

	public Long findInvoiceRecrod(String invoiceNum, String invoiceCode) {
		StringBuffer sql = new StringBuffer("select * from csms_invoice_record where invoicenum = '"
				+ invoiceNum + "' and invoicecode= '" + invoiceCode +"'");
		List<Map<String,Object>> tmp =  jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
		if(tmp.size()>0){
			Map<String,Object> m = tmp.get(0);
			if(m.get("passBillID") != null){
				return Long.valueOf(tmp.get(0).get("passBillID").toString());
			}else{
				return Long.valueOf(tmp.get(0).get("addBillID").toString());
			}
		}else{
			return 0l;
		}
			
	}

	public void updateState(String invoiceNum,String invoiceCode, String state) {
		StringBuffer sql = new StringBuffer("update csms_invoice_record set state='"
				+ state + "' where invoiceNum='" + invoiceNum+"' and invoiceCode='"+invoiceCode+"'");
		jdbcUtil.getJdbcTemplate().update(sql.toString());
	}

	@SuppressWarnings("rawtypes")
	public void update(PassBill passBill) {
		Map map = FieldUtil.getPreFieldMap(PassBill.class,passBill);
		StringBuffer sql=new StringBuffer("update CSMS_PASS_BILL set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),passBill.getID());
	}
	
	@SuppressWarnings("rawtypes")
	public void update(AddBill addBill) {
		Map map = FieldUtil.getPreFieldMap(AddBill.class,addBill);
		StringBuffer sql=new StringBuffer("update CSMS_ADD_BILL set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),addBill.getId());
	}

	public List<Map<String,Object>> getACBillDetail(String flag, String cardNo,
			String billMonth) {
		StringBuffer sql = null;
		if(flag.equals("1")){
			sql = new StringBuffer("select I.INVOICENUM, I.INVOICECODE, I.REMARK, I.INVOICETITLE,I.PRINTTIME,P.ID,P.CARDNO,P.USERNO,P.CARDTYPE,P.BANKNO,P.PASSMONTH,P.PASSNUM,P.PASSFEE,P.MENDNUM,P.MENDFEE,P.OTHERNUM,P.OTHERFEE,P.PRINTNUM,P.ONCENUM,P.ONCEFEE,P.STATE,P.OFFICEDEALNUM,P.OFFICEDEALFEE,P.MANAGEFEE,P.SERVICEFEE,P.LATEFEE,P.BACKFEE,P.CARDNOS "
					+ "FROM CSMS_PASS_BILL P LEFT JOIN CSMS_INVOICE_RECORD I ON P.ID=I.PASSBILLID AND I.STATE='1'"
					+ " WHERE P.CARDNO='"+cardNo+"' ");
		}else if(flag.equals("2")){
			sql = new StringBuffer("select I.INVOICENUM, I.INVOICECODE, I.REMARK, I.INVOICETITLE,I.PRINTTIME,P.ID,P.CARDNO,P.USERNO,P.CARDTYPE,P.BANKNO,P.PASSMONTH,P.PASSNUM,P.PASSFEE,P.MENDNUM,P.MENDFEE,P.OTHERNUM,P.OTHERFEE,P.PRINTNUM,P.ONCENUM,P.ONCEFEE,P.STATE,P.OFFICEDEALNUM,P.OFFICEDEALFEE,P.MANAGEFEE,P.SERVICEFEE,P.LATEFEE,P.BACKFEE,P.CARDNOS "
					+ "FROM CSMS_PASS_BILL P LEFT JOIN CSMS_INVOICE_RECORD I ON P.ID=I.PASSBILLID AND I.STATE='1'"
					+ " WHERE P.BANKNO='"+cardNo+"' ");
		}
		sql = sql.append(" AND P.PASSMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
			
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public  Pager getSCBillByCardNo(Pager pager, String cardNo,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer("select cardcode cardno,dealnum+oncenum+consumenum passnum,realdealfee+oncefee+consumefee passfee,"
				+ "to_char(SETTLEMONTH,'yyyy-MM') SETTLEMONTH from tb_scinvoice"
				+ " WHERE CARDCODE='"+cardNo+"'");//TODO
		sql = sql.append(" AND SETTLEMONTH BETWEEN " + "to_date('" + startDate
				+ "','yyyy-MM')");
		sql = sql.append(" AND " + "to_date('" + endDate + "','yyyy-MM')");
		sql = sql.append(" order by SETTLEMONTH desc");
		return this.findByPages(sql.toString(), pager, null);
	}

	public List<Map<String, Object>> getSCPassBillByCardNo(String cardNo,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer(
				"select * from csms_pass_bill where cardtype='1' ");
		sql = sql.append(" AND cardno = '" + cardNo + "' ");
		sql = sql.append(" AND PASSMONTH BETWEEN " + "to_date('" + startDate
				+ "','yyyy-MM')");
		sql = sql.append(" AND " + "to_date('" + endDate + "','yyyy-MM')");

		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public List<Map<String, Object>> getClearSCBillByCardNo(String cardNo,
			String billMonth) {
		StringBuffer sql = new StringBuffer("select cardcode,dealnum,realdealfee realfee,oncenum,oncefee,consumefee otherfee,consumenum othernum,settlemonth from tb_scinvoice where 1=1");
		sql = sql.append(" AND cardcode='" + cardNo + "'");
		sql = sql.append(" AND SETTLEMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public List<Map<String, Object>> getSCClearDetails(String cardNo,
			String billMonth) {
		StringBuffer sql = new StringBuffer(
				"select PAYCARDID,etcmoney from TB_SCINVOICEDETAILS where PAYCARDID='"
				+ cardNo+"' and to_date(EXTIME,'yyyy-MM')=");
		sql = sql.append(" " + billMonth);
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public Pager findAddBillList(Pager pager,
			String cardNo, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer("select * from csms_add_bill where 1=1 ");
		SqlParamer params=new SqlParamer();
		if (StringUtil.isNotBlank(cardNo)) {
			params.eq("cardno", cardNo);
		}
		if (StringUtil.isNotBlank(startDate)) {
			params.geDate("addTime", startDate);
		}
		if (StringUtil.isNotBlank(endDate)) {
			params.leDate("addTime", endDate);
		}
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by addtime desc ");
		return this.findByPages(sql.toString(),pager,Objects);
	}

	public AddBill findAddBillByID(Long id) {
		String sql = "select * from csms_add_bill where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		AddBill addBill = null;
		if (!list.isEmpty()&&list.size()==1) {
			addBill = new AddBill();
			this.convert2Bean(list.get(0), addBill);
		}

			
		return addBill;
	}

	@SuppressWarnings("rawtypes")
	public void updateAddBill(AddBill addBill) {
		Map map = FieldUtil.getPreFieldMap(AddBill.class,addBill);
		StringBuffer sql=new StringBuffer("update CSMS_ADD_BILL set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),addBill.getId());
	}

	public List<Map<String, Object>> findAddBillDetail(Long id) {
		StringBuffer sql = new StringBuffer("select ir.printtime,ir.invoicenum,ir.invoicecode,ir.invoicetitle,ir.remark,ab.* from csms_add_bill ab,csms_invoice_record ir where ab.id="+id);
		sql.append(" and ab.id=ir.addBillID and ir.state='1'");
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

	public List<Map<String, Object>> getSCBillDetail(String cardNo,
			String billMonth) {
		StringBuffer sql = new StringBuffer("select I.INVOICENUM, I.INVOICECODE, I.REMARK, I.INVOICETITLE,I.PRINTTIME,P.ID,P.CARDNO,P.USERNO,P.CARDTYPE,P.BANKNO,P.PASSMONTH,P.PASSNUM,P.PASSFEE,P.MENDNUM,P.MENDFEE,P.OTHERNUM,P.OTHERFEE,P.PRINTNUM,P.ONCENUM,P.ONCEFEE,P.STATE,P.OFFICEDEALNUM,P.OFFICEDEALFEE,P.MANAGEFEE,P.SERVICEFEE,P.LATEFEE,P.BACKFEE,P.CARDNOS "
					+ "FROM CSMS_PASS_BILL P LEFT JOIN CSMS_INVOICE_RECORD I ON P.ID=I.PASSBILLID AND I.STATE='1'"
					+ " WHERE P.CARDNO='"+cardNo+"' ");
		
		sql = sql.append(" AND P.PASSMONTH = to_date('" + billMonth
				+ "','yyyy-MM')");
			
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
	}

}
