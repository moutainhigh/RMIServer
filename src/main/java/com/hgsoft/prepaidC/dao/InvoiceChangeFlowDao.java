package com.hgsoft.prepaidC.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.InvoiceChangeFlow;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class InvoiceChangeFlowDao  extends BaseDao{

	public InvoiceChangeFlow findByCardNo(String cardNo) {
		String sql = "select * from CSMS_InvoiceChangeFlow where CardNo=? and state='1'";
		List<Map<String, Object>> list =  queryList(sql,cardNo);
		InvoiceChangeFlow nvoiceChangeFlow = null;
		if (!list.isEmpty()) {
			nvoiceChangeFlow = new InvoiceChangeFlow();
			this.convert2Bean(list.get(0), nvoiceChangeFlow);
		}

		return nvoiceChangeFlow;
	}
	
	public void update(InvoiceChangeFlow nvoiceChangeFlow) {
		Map map = FieldUtil.getPreFieldMap(InvoiceChangeFlow.class,nvoiceChangeFlow);
		StringBuffer sql=new StringBuffer("update CSMS_InvoiceChangeFlow set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),nvoiceChangeFlow.getId());
	}
	
	public void save(InvoiceChangeFlow nvoiceChangeFlow) {
		Map map = FieldUtil.getPreFieldMap(InvoiceChangeFlow.class,nvoiceChangeFlow);
		StringBuffer sql=new StringBuffer("insert into CSMS_InvoiceChangeFlow");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public void updateByCardNo(InvoiceChangeFlow invoiceChangeFlow) {
		Map map = FieldUtil.getPreFieldMap(InvoiceChangeFlow.class,invoiceChangeFlow);
		StringBuffer sql=new StringBuffer("update CSMS_InvoiceChangeFlow set ");
		sql.append(map.get("updateNameStr") +" where cardNo = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),invoiceChangeFlow.getCardNo());
	}

	public void updateStateByCardNo(String cardNo, String state) {
		String sql = "update CSMS_InvoiceChangeFlow set state=? where cardNo=? and state='1'";
		update(sql, state, cardNo);
	}
}
