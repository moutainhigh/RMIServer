package com.hgsoft.account.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.account.entity.Voucher;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
@Repository
public class VoucherDao extends BaseDao{
	
	/**
	 * 查找缴款单凭证
	 * @param voucher 携带参数
	 * @return Voucher
	 */
	public Voucher findVoucher(Voucher voucher) {
		if (voucher == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(Voucher.class, voucher);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("VoucherDao.findVoucher查询条件为空");
		}

		StringBuffer sql= new StringBuffer("select "+FieldUtil.getFieldMap(Voucher.class,new Voucher()).get("nameStr")+" from CSMS_VOUCHER where 1=1 ");
		sql.append(condition);
		return super.queryObjectFromObjectList(sql.toString(), Voucher.class, ((List) map.get("paramNotNull")).toArray());
	}
	
	public void updateNotNull(Voucher voucher){
		Map map = FieldUtil.getPreFieldMap(Voucher.class, voucher);
		StringBuffer sql = new StringBuffer("update CSMS_VOUCHER set ");
		sql.append(map.get("updateNameStrNotNull")+" where voucherNo = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"), voucher.getVoucherNo());
	}
}
