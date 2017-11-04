package com.hgsoft.invoice.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.invoice.entity.InvoiceDetail;
import com.hgsoft.invoice.entity.PassInvoice;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class InvoiceDetailDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	public void save(InvoiceDetail invoiceDetail) {
		if(invoiceDetail.getId()==null){
			invoiceDetail.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSINVOICEDETAIL_NO"));
		}
		StringBuffer sql=new StringBuffer("insert into CSMS_invoice_detail");
		Map map = FieldUtil.getPreFieldMap(InvoiceDetail.class,invoiceDetail);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public void save(List<Map<String,Object>> list,PassInvoice passInvoice,String type){
		InvoiceDetail invoiceDetail=null;
 		for(int i=0;i<list.size();i++){
			invoiceDetail=new InvoiceDetail();
			//invoiceDetail.setId(1L);
			invoiceDetail.setType(type);
			invoiceDetail.setPassInvoiceId(passInvoice.getId());
			if("1".equals(type)){
				invoiceDetail.setCardNo(list.get(i).get("ACCODE").toString());
			}else{
				invoiceDetail.setCardNo(list.get(i).get("SCCODE").toString());
			}
			invoiceDetail.setReckOnListNo(Long.parseLong((list.get(i).get("RECKONLISTNO")).toString()));
			invoiceDetail.setReckOnTime((Date)(list.get(i).get("RECKONTIME")));
			save(invoiceDetail);
		}
	}
	
	public List<Map<String,Object>> findReckOnListNoByPassInvoicId(Long id){
		String sql="select * from CSMS_invoice_detail where PassInvoiceID="+id;
		return this.jdbcUtil.getJdbcTemplate().queryForList(sql);	
	}
	public List<Map<String,Object>>  findInvoice(String cardNo,Date starTime,Date endTime){
		StringBuffer sql=new StringBuffer("select * from CSMS_invoice_detail where 1=1  ");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sql=sql.append(" and reckontime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		sql=sql.append(" and reckontime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		if(cardNo==null || cardNo.equals("")){
				return jdbcUtil.getJdbcTemplate().queryForList(sql.toString());
		}else{
			sql.append(" and cardNo=? ");
			return jdbcUtil.getJdbcTemplate().queryForList(sql.toString(), cardNo);
		}
		
	}

	public List<Map<String,Object>> findInvoiceTitleList(String cardno){
		StringBuffer sql=new StringBuffer("select cr.UserNo,cr.Organ,ci.invoiceTitle,aca.bankAccount,yc.yctype from " +
				"(select pc.CardNo CardNo,pc.customerID customerID ,pc.AccountID AccountID,'1' yctype from CSMS_PrePaidC pc " +
				"union all select ai.CardNo CardNo, ai.customerID customerID,ai.AccountID AccountID,'2' yctype from CSMS_AccountC_info ai " +
				") yc left join CSMS_SubAccount_Info sai on sai.id=yc.AccountID and yc.yctype=sai.subAccountType " +
				"left join CSMS_AccountC_apply aca on aca.id=sai.APPLYID " +
				"left join CSMS_invoice ci on ci.mainId=yc.customerID " +
				"left join CSMS_Customer cr on cr.id = yc.customerID " +
				"where 1=1");
		if(cardno==null || cardno.equals("")){
			return null;
		}else{
			sql.append(" and yc.CardNo=? ");
			return jdbcUtil.getJdbcTemplate().queryForList(sql.toString(), cardno);
		}
	}


}
