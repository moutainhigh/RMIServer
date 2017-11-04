package com.hgsoft.macao.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoHisDao;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.dao.InvoiceDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class MacaoReceiptDao extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	MainAccountInfoDao mainAccountInfoDao;
	@Resource
	MainAccountInfoHisDao mainAccountInfoHisDao;
	@Resource
	VehicleInfoDao vehicleInfoDao;
	@Resource
	VehicleInfoHisDao vehicleInfoHisDao;
	@Resource
	CustomerDao customerDao;
	@Resource
	CustomerHisDao customerHisDao;
	@Resource
	InvoiceDao invoiceDao;
	@Resource
	BillGetDao billGetDao;
	
	/**
	 * 回执重打
	 * @param pager
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pager findPrintAgainAllByPager(Pager pager,Date starTime,Date endTime,Long id) {
		Receipt temp = null;
		List<Receipt> receipts=new ArrayList<Receipt>();
		StringBuffer sql = new StringBuffer("select * from (select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=2 and cp.typecode in (1,5,7,8,9,13,14) and cp.customerid="+id);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		
		//客户信息修改
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=4 and cp.typecode=12 and cp.customerid="+id);
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//客户服务密码修改
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=4 and cp.typecode=22 and cp.customerid="+id);
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//客户服务密码重设
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=4 and cp.typecode=23 and cp.customerid="+id);
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//电子标签
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=3 and cp.typecode in (1,3,4,7,6,5) and cp.customerid="+id);
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		
		//车辆信息修改
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=4 and cp.typecode=16 and cp.customerid="+id);
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//记帐卡卡片车牌启用停用
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=6 and cp.typecode in(53,54) and cp.customerid="+id);
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		
		sql.append(" ) order by createtime desc");
		return findByPages(sql.toString(), pager, null);
		
	}
	
}
