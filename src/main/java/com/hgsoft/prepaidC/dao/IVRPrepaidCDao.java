package com.hgsoft.prepaidC.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.dao.ReceiptDao;

@Repository
public class IVRPrepaidCDao extends BaseDao{
	@Resource
	private ReceiptDao receiptDao;
	
	/**
	 * @Description:
	 * @param receiptPrint
	 * @return
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findCustomerInfoByCardNo(String cardno) {
		StringBuffer sql = new StringBuffer("select c.id customerid, c.organ organ,i.id invoiceid,i.invoicetitle invoicetitle,c.usertype usertype,c.idtype idtype,c.idcode idcode,c.linkman linkman,c.tel tel,c.mobile mobile,c.shorttel shorttel,c.email email,c.addr addr,c.zipcode zipcode,bg.id billgetid,bg.seritem seritem " +
				"from csms_prepaidc p join csms_customer c on p.customerid=c.id " +
				"join csms_invoice i on i.mainId=c.id " +
				"join csms_bill_get bg on bg.cardbankno=p.cardno where p.cardno='"+cardno+"'");
		return queryList(sql.toString());
	}

	public List<Map<String, Object>> findCustomerByCardNo(String cardno) {
		StringBuffer sql = new StringBuffer(" select c.* from csms_prepaidc p join csms_customer c on p.customerid=c.id  where p.cardno=?");
		return  queryList(sql.toString(),cardno);
	}
	
}
