package com.hgsoft.other.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.other.entity.ReceiptPrintDetail;

@Repository
public class ReceiptPrintDetailDao extends BaseDao {

	public void save(ReceiptPrintDetail receiptPrintDetail) {
		StringBuffer sql = new StringBuffer("insert into CSMS_ReceiptPrint_Detail");
		Map map = FieldUtil.getPreFieldMap(ReceiptPrintDetail.class,receiptPrintDetail);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(ReceiptPrintDetail.class, receiptPrintDetail).get("nameStr") + ") values(");
		//sql.append(FieldUtil.getFieldMap(ReceiptPrintDetail.class, receiptPrintDetail).get("valueStr") + ")");
		//save(sql.toString());
	}

	public List<Map<String, Object>> findDetailsByTypeAndTime(ReceiptPrint receiptPrint) {
		StringBuffer sql = new StringBuffer("select * from CSMS_RECEIPTPRINT_DETAIL where mainid = "+receiptPrint.getId());
		return queryList(sql.toString());
	}



}
