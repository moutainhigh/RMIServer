package com.hgsoft.other.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.other.entity.ServiceAssess;

@Repository
public class ServiceAssessDetailDao extends BaseDao{

	private static Logger logger = Logger.getLogger(ServiceAssessDetailDao.class.getName());
	public void batchInsertByCustomer(ServiceAssess serviceAssess, ReceiptPrint receiptPrint, Long customerId) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CallableStatement proc = null;
		Connection con = null;
		try {
			con = jdbcUtil.getJdbcTemplate().getDataSource().getConnection();
			proc = con.prepareCall("{CALL lgOut(?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1, serviceAssess==null?1:0);
			proc.setInt(2, receiptPrint==null?1:0);
			proc.setLong(3, serviceAssess==null?-1:serviceAssess.getId());
			proc.setLong(4, receiptPrint==null?-1:receiptPrint.getId());
			proc.setLong(5, customerId);
			proc.setString(6, serviceAssess==null?"":format.format(serviceAssess.getBeginTime()));
			proc.setString(7, serviceAssess==null?"":format.format(serviceAssess.getEndTime()));
			proc.setString(8, receiptPrint==null?"":format.format(receiptPrint.getBeginTime()));
			proc.setString(9, receiptPrint==null?"":format.format(receiptPrint.getEndTime()));
			proc.execute();
			
			proc.close();
			con.close();
		} catch (SQLException e) {
			logger.error("结束服务失败,数据库存储过程调用失败");
			e.printStackTrace();
		} 
	}
	
}
