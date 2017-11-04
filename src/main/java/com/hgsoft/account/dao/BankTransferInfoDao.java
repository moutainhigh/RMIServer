package com.hgsoft.account.dao;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Component
public class BankTransferInfoDao extends BaseDao {
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	public Pager list(Pager pager,Date starTime ,Date endTime,BankTransferInfo bankTransferInfo  ,Customer customer) {
		String sql= "select b.id as id ,b.mainid as mainid,b.payname as payname,"
				+ "b.transferblanace as transferblanace,b.blanace as blanace,b.bankno as bankno,b.memo as memo,"
				+ "b.auditstate as auditstate,b.hisseqid as hisseqid,b.arrivaltime as arrivaltime,ROWNUM as num from CSMS_BankTransfer_Info b  where 1=1 "
				+ "";
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and b.arrivaltime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and b.arrivaltime <=to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		/*if(customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}*/
		if(StringUtil.isNotBlank(bankTransferInfo.getPayName())){
			params.like("b.payName","%"+bankTransferInfo.getPayName()+"%");
		}
		if(StringUtil.isNotBlank(bankTransferInfo.getBankNo())){
			params.like("b.bankNo",bankTransferInfo.getBankNo());
		}
	
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by  b.id desc ");
		return this.findByPages(sql, pager,Objects);
	}
	public Pager list(Pager pager,String starTime ,String endTime,BankTransferInfo bankTransferInfo  ,Customer customer) {
		String sql= "select b.id as id ,b.mainid as mainid,b.payname as payname,"
				+ "b.transferblanace as transferblanace,b.blanace as blanace,b.bankno as bankno,b.memo as memo,"
				+ "b.auditstate as auditstate,b.hisseqid as hisseqid,b.arrivaltime as arrivaltime,ROWNUM as num from CSMS_BankTransfer_Info b  where 1=1 "
				+ "";
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(starTime)){
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and b.arrivaltime >= to_date('"+starTime+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(StringUtil.isNotBlank(endTime)){
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and b.arrivaltime <=to_date('"+endTime+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		/*if(customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}*/
		if(StringUtil.isNotBlank(bankTransferInfo.getPayName())){
			params.like("b.payName","%"+bankTransferInfo.getPayName()+"%");
		}
		if(StringUtil.isNotBlank(bankTransferInfo.getBankNo())){
			params.like("b.bankNo",bankTransferInfo.getBankNo());
		}
		if(bankTransferInfo.getTransferBlanace()!=null)
			params.eq("b.TransferBlanace", bankTransferInfo.getTransferBlanace().doubleValue()*100);
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by  b.id desc ");
		return this.findByPages(sql, pager,Objects);
	}
	/**
	 *电子标签提货金额登记——收费方式——转账——客户转账列表
	 */
	public Pager listForBankTransfer(Pager pager,BankTransferInfo bankTransferInfo) {
		String sql= "select b.id as id ,b.mainid as mainid,b.payname as payname,"
				+ "b.transferblanace as transferblanace,b.blanace as blanace,b.bankno as bankno,b.memo as memo,"
				+ "b.auditstate as auditstate,b.hisseqid as hisseqid,b.arrivaltime as arrivaltime,ROWNUM as num from CSMS_BankTransfer_Info b  where 1=1 "
				+ "";
		SqlParamer params=new SqlParamer();
		if(bankTransferInfo != null){
			if(StringUtil.isNotBlank(bankTransferInfo.getPayName())){
				params.like("b.payName",bankTransferInfo.getPayName());
			}
			if(StringUtil.isNotBlank(bankTransferInfo.getBankNo())){
				params.eq("b.bankNo",bankTransferInfo.getBankNo());
			}
			if(bankTransferInfo.getTransferBlanace() != null){
				params.eq("b.transferblanace",bankTransferInfo.getTransferBlanace().multiply(new BigDecimal("100")));
			}
			if(bankTransferInfo.getArrivalTime() != null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				params.eqYearMonthDay("b.arrivaltime", dateFormat.format(bankTransferInfo.getArrivalTime()));
			}
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by  b.id desc ");
		return this.findByPages(sql, pager,Objects);
	}
	public BankTransferInfo findBytId(Long id){
		String sql="select "+FieldUtil.getFieldMap(BankTransferInfo.class,new BankTransferInfo()).get("nameStr")+" from CSMS_BankTransfer_Info where id=?";
		return super.queryObjectFromObjectList(sql, BankTransferInfo.class, id);
	}
	
	public void update(BankTransferInfo bankTransferInfo) {
		StringBuffer sql = new StringBuffer("update CSMS_BankTransfer_Info set ");
		String sqlString = "";
		
		if (bankTransferInfo.getBlanace() == null) {
			sql.append("Blanace=NULL,");
		} else {
			sql.append("Blanace=Blanace+" + bankTransferInfo.getBlanace() + " ,");
		}
		
		if(bankTransferInfo.getHisSeqId()!=null){
			sql.append("HisSeqId="+bankTransferInfo.getHisSeqId()+" ," );
		}
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += "  where id=?";
		update(sqlString, bankTransferInfo.getId());
	}
	
	/**
	 * 营运接口调用：银行到账信息列表
	 * @param pager
	 * @param arrivalStartTime    yyyy-MM-dd
	 * @param arrivalEndTime		yyyy-MM-dd
	 * @param fileName
	 * @return Pager
	 */
	public Pager findBankTransferList(Pager pager,String arrivalStartTime,String arrivalEndTime,String fileName){
		//String sql="select "+FieldUtil.getFieldMap(BankTransferInfo.class,new BankTransferInfo()).get("nameStr")+" from CSMS_BankTransfer_Info where 1=1 ";
		String sql="select ID,MainID,PayName,TransferBlanace,Blanace,BankNo,"
				+ "to_char(ArrivalTime,'yyyy-MM-dd HH24:mi:ss') ArrivalTime,to_char(accountTime,'yyyy-MM-dd HH24:mi:ss') accountTime,certificateType,certificateNo,"
				+ "Abstracts,Memo,FileName,to_char(DealTime,'yyyy-MM-dd HH24:mi:ss') DealTime,"
				+ "OperId,OperNo,OperName,AuditId,AuditNo,AuditName,to_char(AuditTime,'yyyy-MM-dd HH24:mi:ss') AuditTime,AuditState,"
				+ "HisSeqID from CSMS_BankTransfer_Info where 1=1 ";
		SqlParamer params=new SqlParamer();
		
		if(StringUtil.isNotBlank(arrivalStartTime)){
			params.geDate("arrivaltime", arrivalStartTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(arrivalEndTime)){
			params.leDate("arrivaltime", arrivalEndTime+" 23:59:59");
		}
		
		if(StringUtil.isNotBlank(fileName)){
			params.like("fileName","%"+fileName+"%");
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by id desc ");
		return this.findByPages(sql, pager,Objects);
	}
	
	public void delete(Long id){
		String sql = "delete from CSMS_BankTransfer_Info where id=? ";
		this.delete(sql, id);
	}
	/**
	 * 修改bankTransferInfo不为空的字段
	 * @param bankTransferInfo
	 * @return void
	 */
	public void updateNotNull(BankTransferInfo bankTransferInfo){
		Map map = FieldUtil.getPreFieldMap(BankTransferInfo.class, bankTransferInfo);
		StringBuffer sql = new StringBuffer("update CSMS_BankTransfer_Info set ");
		sql.append(map.get("updateNameStrNotNull")+" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"), bankTransferInfo.getId());
	}
	
	public void save(BankTransferInfo bankTransferInfo){
		Map map = FieldUtil.getPreFieldMap(BankTransferInfo.class,bankTransferInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_BankTransfer_Info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public int[] batchSave(final List<BankTransferInfo> bankTransferInfos) {  
        String sql = "insert into CSMS_BankTransfer_Info(id,PayName,TransferBlanace,Blanace,BankNo,ArrivalTime,accountTime,"
        		+ "certificateType,certificateNo,Abstracts,Memo,FileName,DealTime,OperId,OperNo,OperName,AuditState)  "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				BankTransferInfo bankTransferInfo = bankTransferInfos.get(i);
				
				if(bankTransferInfo.getId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, bankTransferInfo.getId());
				if(bankTransferInfo.getPayName()==null)ps.setNull(2, Types.VARCHAR);else ps.setString(2, bankTransferInfo.getPayName());
				if(bankTransferInfo.getTransferBlanace()==null)ps.setNull(3, Types.BIGINT);else ps.setBigDecimal(3, bankTransferInfo.getTransferBlanace());
				if(bankTransferInfo.getBlanace()==null)ps.setNull(4, Types.BIGINT);else ps.setBigDecimal(4, bankTransferInfo.getBlanace());
				if(bankTransferInfo.getBankNo()==null)ps.setNull(5, Types.VARCHAR);else ps.setString(5, bankTransferInfo.getBankNo());
				if(bankTransferInfo.getArrivalTime()==null)ps.setNull(6, Types.DATE);else ps.setTimestamp(6, new java.sql.Timestamp(bankTransferInfo.getArrivalTime().getTime()));
				if(bankTransferInfo.getAccountTime()==null)ps.setNull(7, Types.DATE);else ps.setTimestamp(7, new java.sql.Timestamp(bankTransferInfo.getAccountTime().getTime()));
				if(bankTransferInfo.getCertificateType()==null)ps.setNull(8, Types.VARCHAR);else ps.setString(8, bankTransferInfo.getCertificateType());
				if(bankTransferInfo.getCertificateNo()==null)ps.setNull(9, Types.VARCHAR);else ps.setString(9, bankTransferInfo.getCertificateNo());
				if(bankTransferInfo.getAbstracts()==null)ps.setNull(10, Types.VARCHAR);else ps.setString(10, bankTransferInfo.getAbstracts());
				if(bankTransferInfo.getMemo()==null)ps.setNull(11, Types.VARCHAR);else ps.setString(11, bankTransferInfo.getMemo());
				if(bankTransferInfo.getFileName()==null)ps.setNull(12, Types.VARCHAR);else ps.setString(12, bankTransferInfo.getFileName());
				if(bankTransferInfo.getDealTime()==null)ps.setNull(13, Types.DATE);else ps.setTimestamp(13, new java.sql.Timestamp(bankTransferInfo.getDealTime().getTime()));
				if(bankTransferInfo.getOperId()==null)ps.setNull(14, Types.BIGINT);else ps.setLong(14, bankTransferInfo.getOperId());
				if(bankTransferInfo.getOperNo()==null)ps.setNull(15, Types.VARCHAR);else ps.setString(15, bankTransferInfo.getOperNo());
				if(bankTransferInfo.getOperName()==null)ps.setNull(16, Types.VARCHAR);else ps.setString(16, bankTransferInfo.getOperName());
				if(bankTransferInfo.getAuditState()==null)ps.setNull(17, Types.VARCHAR);else ps.setString(17, bankTransferInfo.getAuditState());
			}
			
			@Override
			public int getBatchSize() {
				 return bankTransferInfos.size();
			}
		});
    }
	
}
