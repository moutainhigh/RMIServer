package com.hgsoft.jointCard.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.jointCard.entity.JointCardIssueQuery;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class JointCardIssueDao extends BaseDao {

	public Pager findJointCardIssueInfo(Pager pager, Customer customer, JointCardIssueQuery jointCardIssueQuery) {
		StringBuffer sql = new StringBuffer("select" 
				+ " ac.id as acid," + "ac.cardno," + "ch.name," + "ch.idtype," + "ch.idcode,"
				+ "v.vehicleplate," + "v.vehiclecolor," + "ac.state," + "ac.issuetime "
				+ " from CSMS_ACCOUNTC_INFO ac "
				+ " left join CSMS_HK_CARDHOLDER ch on ac.cardno=ch.cardno "
				+ " left join CSMS_CAROBUCARD_INFO coc on ac.id=coc.accountcid "
				+ " left join CSMS_VEHICLE_INFO v on coc.vehicleid=v.id ");
		sql.append(" where 1=1 ");
		SqlParamer params = new SqlParamer();
		if (customer != null) {
			params.eq("ac.customerid", customer.getId());
		} // if
		if (StringUtil.isNotBlank(jointCardIssueQuery.getCardNo())) {
			params.eq("ac.cardno", jointCardIssueQuery.getCardNo());
		} // if
		if (jointCardIssueQuery.getStartTime() != null) {
			params.geDate("ac.issuetime", params.getFormat().format(jointCardIssueQuery.getStartTime()));
		} // if
		if (jointCardIssueQuery.getEndTime() != null) {
			params.leDate("ac.issuetime", params.getFormat().format(jointCardIssueQuery.getEndTime()));
		} // if
		if (StringUtil.isNotBlank(jointCardIssueQuery.getVehiclePlate())) {
			params.eq("v.vehicleplate", jointCardIssueQuery.getVehiclePlate());
		} // if
		if (StringUtil.isNotBlank(jointCardIssueQuery.getVehicleColor())) {
			params.eq("v.vehiclecolor", jointCardIssueQuery.getVehicleColor());
		} // if
		if (StringUtil.isNotBlank(jointCardIssueQuery.getCardHolderName())) {
			params.eq("ch.name", jointCardIssueQuery.getCardHolderName());
		} // if
		if (StringUtil.isNotBlank(jointCardIssueQuery.getIdType())) {
			params.eq("ch.idtype", jointCardIssueQuery.getIdType());
		} // if
		if (StringUtil.isNotBlank(jointCardIssueQuery.getIdCode())) {
			params.eq("ch.idcode", jointCardIssueQuery.getIdCode());
		} // if
		sql.append(params.getParam());
		Object[] Objects = params.getList().toArray();
		sql.append(" order by issuetime desc ");
		return this.findByPages(sql.toString(), pager, Objects);
	}
	
}