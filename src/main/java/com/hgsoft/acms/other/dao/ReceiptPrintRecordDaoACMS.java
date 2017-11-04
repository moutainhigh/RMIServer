package com.hgsoft.acms.other.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.entity.ReceiptPrintRecord;

@Repository
public class ReceiptPrintRecordDaoACMS extends BaseDao{
	public void save(ReceiptPrintRecord receiptPrintRecord) {
		StringBuffer sql = new StringBuffer("insert into CSMS_ReceiptPrint_Record");
		Map map = FieldUtil.getPreFieldMap(ReceiptPrintRecord.class,receiptPrintRecord);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(ReceiptPrintRecord.class, receiptPrintRecord).get("nameStr") + ") values(");
		//sql.append(FieldUtil.getFieldMap(ReceiptPrintRecord.class, receiptPrintRecord).get("valueStr") + ")");
		//save(sql.toString());
	}
}
