package com.hgsoft.prepaidC.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.prepaidC.entity.PrepaidCTransferDetail;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class PrepaidCDao extends BaseDao{

	private static final Logger logger = LoggerFactory.getLogger(PrepaidCDao.class);

	@Autowired
	private SequenceUtil sequenceUtil;
	
	public List<Map<String,Object>> findSalesType(String type,String salesState,String salesFlag){
		String sql = "select * from OMS_SALETYPE where type=? and state=?";
		return queryList(sql, type,salesState);
	}
	
	public Map<String,Object> findFirstIssueFaceValue(String cardNo){
		String sql = "select faceValue from oms_createinitcard where cardcode=?";
		List<Map<String,Object>> list = queryList(sql,cardNo);
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	public Map<String,Object> findCusPoById(Long id) {
		String sql = "select name from oms_custompoint where id=? ";
		List<Map<String, Object>> list = queryList(sql,id);
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	public List<Map<String, Object>> findAllCusPo() {
		String treeend = "1";//自营层级网点(营业部)
		Long id = 1l;
		String sql = "select id,name from (select * from oms_custompoint start with id=1 connect by prior id=parent ) where treeend=? and id!=? ";
		return queryList(sql, treeend, id);
	}
	
	public Pager prepaidCList(Pager pager,Customer customer,String cardNo) {
		// TODO Auto-generated method stub
		String sql="select c.Organ,c.IdType,c.IdCode,T.id as vehicleID,t.vehicleColor,t.vehiclePlate,p.cardno,p.id as prepaidCID,ROWNUM as num  from CSMS_Vehicle_Info t join CSMS_Customer c on t.customerId=c.id left join CSMS_CAROBUCARD_INFO coc on t.id=coc.vehicleID LEFT JOIN CSMS_PREPAIDC p on coc.prepaidCID = p.id where coc.accountCID is null";			

		SqlParamer params=new SqlParamer();
		
		if(customer.getId() != null) {
			params.eq(" t.customerid", customer.getId());
		}
		/*if(StringUtil.isNotBlank(customer.getOrgan())) {
			params.eq("c.organ", customer.getOrgan());
		}
		if(StringUtil.isNotBlank(customer.getIdType())) {
			params.eq("c.idType", customer.getIdType());
		}
		if(StringUtil.isNotBlank(customer.getIdCode())) {
			params.eq("c.idCode", customer.getIdCode());
		}*/
		if(StringUtil.isNotBlank(cardNo))
			params.eq("p.cardNo", cardNo);
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by t.ID ");
		return this.findByPages(sql, pager, Objects);
		
	}
	public Pager prepaidCListForAMMS(Pager pager,Customer customer,String cardNo,String cardType,String bankNo) {
		String sql = "";
		if(StringUtil.isNotBlank(bankNo)){
			sql =" select c.Organ organ,c.IdType idType,c.IdCode idCode,p.cardno cardno,vi.vehicleColor vehicleColor,vi.vehiclePlate vehiclePlate,vi.id vehicleId"
			    +" from csms_vehicle_Info vi"
			    +" join csms_carobucard_info coc on vi.id = coc.vehicleId"
			    +" left join csms_prepaidc p on coc.prepaidCId = p.id"
			    +" left join csms_joinCardNoSection j on substr(p.cardNo, 0, length(p.cardNo) - 1) between j.code and j.endCode"
			    +" join csms_customer c on vi.customerId = c.id"
			    +" where ((coc.accountCId is null and coc.prepaidcid is not null and j.cardType = ? and j.bankNo = ?) or (coc.accountCId is null and coc.prepaidCId is null))";
		}else{
			sql =" select c.Organ organ,c.IdType idType,c.IdCode idCode,p.cardno cardno,vi.vehicleColor vehicleColor,vi.vehiclePlate vehiclePlate,vi.id vehicleId"
				+" from csms_vehicle_Info vi"
				+" join csms_carobucard_info coc on vi.id = coc.vehicleId"
			    +" left join csms_prepaidc p on coc.prepaidCId = p.id"
			    +" join csms_customer c on vi.customerId = c.id"
			    +" where coc.accountCId is null and coc.prepaidCId is null";
		}
		
		List<String> params = new ArrayList<String>();
		if(StringUtil.isNotBlank(bankNo)){
			params.add(cardType);
			params.add(bankNo);
		}
		if(customer!=null && customer.getId()!=null){
			params.add(customer.getId().toString());
			sql = sql + " and vi.customerId=?";
		}
		if(StringUtil.isNotBlank(cardNo)){
			params.add(cardNo);
			sql = sql + " and p.cardNo=?";
		}
		sql = sql + " order by p.cardNo";
		return this.findByPages(sql, pager, params.toArray());
	}

	
	public PrepaidC findById(Long id) {
		String sql = "select * from csms_prepaidc where id=?";
		List<PrepaidC> prepaidCs = super.queryObjectList(sql, PrepaidC.class, id);
		if (prepaidCs == null || prepaidCs.isEmpty()) {
			return null;
		} else if (prepaidCs.size() > 1) {
			logger.error("csms_prepaidc中id[{}]有多条[{}]记录", id, prepaidCs.size());
			throw new ApplicationException("prepaidCDao.findById有多条记录");
		}
		return prepaidCs.get(0);
	}
	public PrepaidC findByHisId(Long hisId) {
		String sql = "select * from csms_prepaidc where HisSeqID=?";
		List<PrepaidC> prepaidCs = super.queryObjectList(sql, PrepaidC.class, hisId);
		if (prepaidCs == null || prepaidCs.isEmpty()) {
			return null;
		} else if (prepaidCs.size() > 1) {
			logger.error("csms_prepaidc中hisId[{}]有多条[{}]记录", hisId, prepaidCs.size());
			throw new ApplicationException("prepaidCDao.findByHisId有多条记录");
		}
		return prepaidCs.get(0);
	}
	public PrepaidC find(PrepaidC prepaidC) {
		if (prepaidC == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(PrepaidC.class,prepaidC);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("prepaidCDao.find查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from csms_prepaidc where 1=1 ");
		sql.append(condition);
		sql.append(" order by id");
		List<PrepaidC> prepaidCs = super.queryObjectList(sql.toString(), PrepaidC.class, ((List) map.get("paramNotNull")).toArray());
		if (prepaidCs == null || prepaidCs.isEmpty()) {
			return null;
		} else if (prepaidCs.size() > 1) {
			logger.error("csms_prepaidc中有多条[{}]记录", prepaidCs.size());
			throw new ApplicationException("prepaidCDao.find有多条记录");
		}
		return prepaidCs.get(0);
	}
	
	public PrepaidC findByPrepaidCNo(String cardNo) {
		return find(new PrepaidC(cardNo));
	}
	
	public PrepaidC findByPrepaidcNoAndCustomer(String cardNo,Customer customer){
		String sql="select p.*  from CSMS_Vehicle_Info t join CSMS_Customer c on t.customerId=c.id left join CSMS_CAROBUCARD_INFO coc on t.id=coc.vehicleID LEFT JOIN CSMS_PREPAIDC p on coc.prepaidCID = p.id where p.CARDNO = ? and c.id = ?";
		List<PrepaidC> prepaidCs = super.queryObjectList(sql.toString(), PrepaidC.class, cardNo,customer.getId());
		if (prepaidCs == null || prepaidCs.isEmpty()) {
			return null;
		} else if (prepaidCs.size() > 1) {
			logger.error("CSMS_Vehicle_Info中卡号[{}]用户id[{}]有多条[{}]记录", cardNo,customer.getId(), prepaidCs.size());
			throw new ApplicationException("prepaidCDao.findByPrepaidcNoAndCustomer有多条记录");
		}
		return prepaidCs.get(0);
	}
	
	public void update(PrepaidC prepaidC) {
		/*StringBuffer sql=new StringBuffer("update csms_prepaidc set ");
		sql.append(FieldUtil.getFieldMap(PrepaidC.class,prepaidC).get("nameAndValue")+" where id="+prepaidC.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(PrepaidC.class, prepaidC);
		StringBuffer sql=new StringBuffer("update csms_prepaidc set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),prepaidC.getId());
	}
	
	public void updateNotNull(PrepaidC prepaidC) {
		/*StringBuffer sql=new StringBuffer("update csms_prepaidc set ");
		sql.append(FieldUtil.getFieldMap(PrepaidC.class,prepaidC).get("nameAndValue")+" where id="+prepaidC.getId());
		update(sql.toString());*/
		/*Map map = FieldUtil.getPreFieldMap(PrepaidC.class,prepaidC);
		StringBuffer sql=new StringBuffer("update csms_prepaidc set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),prepaidC.getId());*/
		
		String sql ="update csms_prepaidc set RealCost=?,HisSeqID=?";
		
		
		if(prepaidC.getMaintainTime()!=null){
			sql=sql+",MaintainTime=?  where id = ?";
			saveOrUpdate(sql.toString(), prepaidC.getRealCost(),prepaidC.getHisSeqID(),prepaidC.getMaintainTime(),prepaidC.getId());
		}else{
			sql=sql+"  where id = ?";
			saveOrUpdate(sql.toString(), prepaidC.getRealCost(),prepaidC.getHisSeqID(),prepaidC.getId());
		}
		
	}

	/**
	 * 
	 * @param prepaidC 查找出来的储值卡信息记录
	 * @param genReason 进入历史的原因
	 * @return Long 返回历史id
	 */
	public Long savePrepaidCHis(PrepaidC prepaidC,String genReason){
		PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(), genReason, prepaidC);
		prepaidCHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO"));
		prepaidCHis.setGenreason(genReason);
		this.saveHis(prepaidCHis);
		return prepaidCHis.getId();
	}

	public void saveHis(PrepaidCHis prepaidCHis) {
		StringBuffer sql=new StringBuffer("insert into csms_prepaidc_his(");
		sql.append(FieldUtil.getFieldMap(PrepaidCHis.class, prepaidCHis).get("nameStr") + ") values(");
		sql.append(FieldUtil.getFieldMap(PrepaidCHis.class, prepaidCHis).get("valueStr") + ")");
		save(sql.toString());
	}
	
	public PrepaidCHis findFromHisByHisId(Long hisId) {
		String sql = "select * from csms_prepaidc_his where HisSeqID=?";
		List<PrepaidCHis> prepaidCHises = super.queryObjectList(sql.toString(), PrepaidCHis.class, hisId);
		if (prepaidCHises == null || prepaidCHises.isEmpty()) {
			return null;
		} else if (prepaidCHises.size() > 1) {
			logger.error("csms_prepaidc_his中HisSeqID[{}]有多条[{}]记录", hisId, prepaidCHises.size());
			throw new ApplicationException("findFromHisByHisId查出多条记录");
		}
		return prepaidCHises.get(0);
	}
	public PrepaidCHis findFromHisById(Long id) {
		String sql = "select * from csms_prepaidc_his where ID=?";
		List<PrepaidCHis> prepaidCHises = super.queryObjectList(sql, PrepaidCHis.class, id);
		if (prepaidCHises == null || prepaidCHises.isEmpty()) {
			return null;
		} else if (prepaidCHises.size() > 1) {
			logger.error("csms_prepaidc_his中ID[{}]有多条[{}]记录", id, prepaidCHises.size());
			throw  new ApplicationException("findFromHisById查出多条记录");
		}
		return prepaidCHises.get(0);
	}
	/**
	 * 根据ID删除储值卡发行信息
	 * @param id
	 */
	public void delete(Long id){
		String sql = "delete from CSMS_PrepaidC where id=?";

		delete(sql,id);
	}
	
	/**
	 * 储值卡发行保存
	 * @param prepaidC
	 */
	public void save(PrepaidC prepaidC) {
		/*StringBuffer sql=new StringBuffer("insert into csms_prepaidc(");
		sql.append(FieldUtil.getFieldMap(PrepaidC.class,prepaidC).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(PrepaidC.class,prepaidC).get("valueStr")+")");
		save(sql.toString());*/
		prepaidC.setHisSeqID(-prepaidC.getId());
		Map map = FieldUtil.getPreFieldMap(PrepaidC.class,prepaidC);
		StringBuffer sql=new StringBuffer("insert into csms_prepaidc");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}


	public Pager findStopCardByCustomer(Pager pager,Customer customer) {
		StringBuffer sql = new StringBuffer("select a.id,a.code, a.reason,a.remark,a.cancelTime,ROWNUM as num  from CSMS_CANCEL a where a.flag='1'  and a.CUSTOMERID =? order by a.cancelTime desc ");
		return this.findByPages(sql.toString(), pager, new Object[] {customer.getId()});
	}


	public PrepaidC findByIdAndState(Long id, String state) {
		return find(new PrepaidC(id, state));
	}
	
	public Pager findByCustomer(Pager pager, Customer customer) {
		String sql = "select p.*,ROWNUM as num from csms_prepaidc p where p.customerId=? order by p.ID ";
		return super.findByPages(sql.toString(), pager, new Object[] {customer.getId()});
	}
	
	/**
	 * 弃用，因为当一客户多张卡时返回null,
	 * findByCustomer()代替
	 * @param id
	 * @return
	 */
	public PrepaidC findByCustomerId(Long id) {
		String sql = "select * from csms_prepaidc where customerId=?";
		List<PrepaidC> prepaidCs = super.queryObjectList(sql, PrepaidC.class, id);
		if (prepaidCs == null || prepaidCs.isEmpty()) {
			return null;
		} else if (prepaidCs.size() > 1) {
			logger.error("csms_prepaidc中ID[{}]有多条[{}]记录", id, prepaidCs.size());
			throw  new ApplicationException("findByCustomerId查出多条记录");
		}
		return prepaidCs.get(0);
	}


	public PrepaidC findByCardNoToGain(String cardNo) {
		if (StringUtils.isBlank(cardNo)) {
			throw  new ApplicationException("prepaidCDao.findByCardNoToGain查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select a.* from csms_prepaidc a where a.cardno=?");
		sql.append(" and a.CardNo not in(select OldCardNo from csms_prepaidc_bussiness where OldCardNo=? and state=11)");

		List<PrepaidC> prepaidCs = super.queryObjectList(sql.toString(), PrepaidC.class, cardNo, cardNo);
		if (prepaidCs == null || prepaidCs.isEmpty()) {
			return null;
		} else if (prepaidCs.size() > 1) {
			logger.error("卡号[{}]有多条[{}]记录", cardNo, prepaidCs.size());
			throw  new ApplicationException("prepaidCDao.findByCardNoToGain查出多条记录");
		}
		return prepaidCs.get(0);
	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		StringBuffer sql=new StringBuffer("update csms_prepaidc set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(SaleTime,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(SaleTime,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		saveOrUpdate(sql.toString(),settleDay,startTime,endTime);
	}
	
	public List<Map<String, Object>> listCardNByCustomerId(Long customerId) {
		StringBuffer sql = new StringBuffer("select p.id,p.cardno name from csms_prepaidc p left join csms_subaccount_info s on(p.accountid=s.id) left join csms_mainaccount_info m on(s.mainid=m.id) where m.mainid=?");
		sql.append(" and s.subaccounttype=1 and p.state=0");
		return queryList(sql.toString(), customerId);
	}
	
	public List<Map<String, Object>> listCardByCustomerId(Long customerId) {
		//(p.state='0' or p.state='4')
		String sql = "select p.id,p.cardno name from csms_prepaidc p where customerId=? and   p.state='4' ";
		return queryList(sql,customerId);
	}
	
	public void saveTransfer(PrepaidCTransfer prepaidCTransfer) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_PrepaidCTransfer(");
		sql.append(FieldUtil.getFieldMap(PrepaidCTransfer.class,prepaidCTransfer).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(PrepaidCTransfer.class,prepaidCTransfer).get("valueStr")+")");
		save(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(PrepaidCTransfer.class,prepaidCTransfer);
		StringBuffer sql=new StringBuffer("insert into CSMS_PrepaidCTransfer");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
    }
	public PrepaidCTransfer findTransferId(Long id){
		String sql="select * from CSMS_PrepaidCTransfer where id=?";

		List<PrepaidCTransfer> prepaidCTransfers = super.queryObjectList(sql.toString(), PrepaidCTransfer.class, id);
		if (prepaidCTransfers == null || prepaidCTransfers.isEmpty()) {
			return null;
		} else if (prepaidCTransfers.size() > 1) {
			logger.error("id[{}]有多条[{}]记录", id, prepaidCTransfers.size());
			throw  new ApplicationException("prepaidCDao.findTransferId查出多条记录");
		}
		return prepaidCTransfers.get(0);
	}
	public int[] batchSaveTransferDetail(final List<PrepaidCTransferDetail> list) {
        String sql = "insert into Csms_Prepaidctransferdetail(id,transferid,cardNo) values(SEQ_CSMSPrepaidCTransferDt_NO.nextval,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PrepaidCTransferDetail TransferDetail = list.get(i);
				ps.setLong(1, TransferDetail.getTransferId());
				ps.setString(2, TransferDetail.getCardNo());
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	public void updateBind(String state,Long id){
		//String sql="update csms_prepaidc set Bind='"+state+"' where id="+id;
		String sql="update csms_prepaidc set Bind=? where id=?";
		saveOrUpdate(sql,state,id);
	}
	
	public int[] batchUpdatePrepaidC(final List<PrepaidC> list) {
		StringBuffer sql=new StringBuffer("update csms_prepaidc set customerID=?,AccountID=?,Bind=?,HisSeqID=? where CardNo=?");
    	return batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PrepaidC prepaidC = list.get(i);
				ps.setLong(1, prepaidC.getCustomerID());
				ps.setLong(2, prepaidC.getAccountID());
				ps.setString(3, prepaidC.getBind());
				ps.setLong(4, prepaidC.getHisSeqID());
				ps.setString(5, prepaidC.getCardNo());
				
			}
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	public int[] batchSaveHis(final List<String> cardNoList) {
		StringBuffer sql = new StringBuffer("insert into csms_prepaidc_his (id,gentime,cardno,accountid,state,facevalue,cost_,"
				+ "realcost,transfersum,returnmoney,saletime,saleflag,payflag,saleoperid,"
				+ "saleplaceid,operNo,operName,placeNo,placeName,invoiceprint,s_con_pwd_flag,tradingpwd,hisseqid,customerid,bind,"
				+ "isdayset,settleday,settlettime,startdate,enddate,genreason,CARDSERVICEPWD,InvoiceChangeFlag) select SEQ_CSMS_PREPAIDC_HIS_NO.nextval,sysdate,cardno,accountid,state,facevalue,cost_,"
				+ "realcost,transfersum,returnmoney,saletime,saleflag,payflag,saleoperid,"
				+ "saleplaceid,operNo,operName,placeNo,placeName,invoiceprint,s_con_pwd_flag,tradingpwd,hisseqid,customerid,bind,"
				+ "isdayset,settleday,settlettime,startdate,enddate,'9',CARDSERVICEPWD,InvoiceChangeFlag from csms_prepaidc where cardno=?");
    	return batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				//ps.setString(1, list.get(i).getGenreason());
				ps.setString(1, cardNoList.get(i));
			}
			@Override
			public int getBatchSize() {
				 return cardNoList.size();
			}
		});
	}


	public Pager findStopCardByCustomer(Pager pager, Customer customer, PrepaidC prepaidC) {
		StringBuffer sql = new StringBuffer("SELECT A.ID, A.CODE, A.REASON, A.CANCELTIME,r.id rid,r.currentrefundbalance,r.auditstatus,r.refundtime,r.refundname, ROWNUM AS NUM FROM CSMS_CANCEL A JOIN csms_refundinfo r ON a.code=r.cardno WHERE A.FLAG = '1' AND A.CUSTOMERID = "+customer.getId());
		SqlParamer params=new SqlParamer();
		if(prepaidC!=null){
			if(StringUtil.isNotBlank(prepaidC.getCardNo())){
				//sql.append(" and a.code='"+prepaidC.getCardNo()+"'");
				params.eq("a.code", prepaidC.getCardNo());
			}
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append( " order by a.cancelTime desc " );
		return this.findByPages(sql.toString(), pager,Objects);
		//return this.findByPages(sql.toString(), pager, null);
		/*if(prepaidC!=null){
			Map map = FieldUtil.getPreFieldMap(PrepaidC.class,prepaidC);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
		}else{
			return this.findByPages(sql.toString(), pager,null);
		}*/
	}
	
	public int[] batchSaveBillGetHis(final List<BillGet> billGetList) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_bill_get_his("
				+ "ID,GenTime,GenReason,"
				+ "mainId,cardType,CardAccountID,CardBankNo,SerItem,SerType,BegTime,EndTime,OperTime,OperID,PlaceID,HisSeqID,operno,opername,placeno,placename ) "
				+ "select ?,sysdate,'1',"
				+ "mainId,cardType,CardAccountID,CardBankNo,SerItem,SerType,BegTime,EndTime,OperTime,OperID,PlaceID,HisSeqID,operno,opername,placeno,placename"
				+ " from CSMS_BILL_GET where CardBankNo=?");
    	return batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BillGet billGet = billGetList.get(i);
				//ps.setString(1, list.get(i).getGenreason());
				ps.setLong(1, billGet.getHisSeqId());
				ps.setString(2, billGet.getCardBankNo());
			}
			@Override
			public int getBatchSize() {
				 return billGetList.size();
			}
		});
	}
	
	public int[] batchUpdateBill(final List<BillGet> billGetList) {
		StringBuffer sql = new StringBuffer(
				"update csms_bill_get set mainId=?,hisSeqId=?  where CardBankNo = ?");
    	return batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BillGet billGet = billGetList.get(i);
				ps.setLong(1, billGet.getMainId());
				ps.setLong(2, billGet.getHisSeqId());
				ps.setString(3, billGet.getCardBankNo());
			}
			@Override
			public int getBatchSize() {
				 return billGetList.size();
			}
		});
	}

	/**
	 * 修改写卡标志
	 * @param cardNo
	 */
	public void updateWriteCardFlag(String cardNo) {
		String sql = "update csms_prepaidC set writeCardFlag=2 where cardno=?";
		saveOrUpdate(sql, cardNo);
	}

	/**
	 * 把服务密码设置为null
	 * @param id
	 */
	public void updateSerPwd(Long id) {
		String sql = "update csms_prepaidC set CARDSERVICEPWD='' where CUSTOMERID = ?";
		update(sql, id);
	}
	
	/**
	 * 
	 * 
	 * @param cardNo
	 * @param state
	 * @return
	 */
	public PrepaidCHis findByCardNoAndState(String cardNo,String state) {
		StringBuffer sql = new StringBuffer("select p.* from csms_prepaidc_his p where p.cardno=?");
		sql.append(" and p.genreason=? order by id desc");
		List<PrepaidCHis> prepaidCHises = super.queryObjectList(sql.toString(), PrepaidCHis.class, cardNo, state);
		if (prepaidCHises == null || prepaidCHises.isEmpty()) {
			return null;
		}
		return prepaidCHises.get(0);
	}
	/**
	 * 更新有效截止时间
	 * @param prepaidC
	 */
	public void updateEndDate(PrepaidC prepaidC) {
		Format format = new SimpleDateFormat("yyyy/MM/dd");
		String d = format.format(prepaidC.getEndDate());
		String sql = "update csms_prepaidC set endDate=to_date('"+d+"','yyyy/MM/dd') where cardNo=?";
		update(sql, prepaidC.getCardNo());
	}
	
	/**
	 * 查询储值卡信息
	 * @param pager
	 * @param code
	 * @param flag
	 * @return
	 */
	public Pager findPrepaidCInfo(Pager pager,String code,String idCardString,String flag,String secondno){
		StringBuffer sql = new StringBuffer("select " 
		  +" c.userno userno ,  "
		  +" c.organ organ ,  "
		  +" c.usertype usertype ,  "
		  +" c.idtype idtype,  "
		  +" c.idcode idcode,  "
		  +" c.linkman linkman,  "
		  +" c.tel tel, "
		  +" c.mobile mobile , "
		  +" c.addr addr,"
		  +" c.email email, "
		  +" c.zipcode zipcode,  "
		  +" c.operno operno,  "
		  +" c.updatetime  updatetime, "
		  +" c.opername  opername, "
		  +" c.secondno  secondno, "
		  +" c.secondname  secondname, "
		  +" p.cardno cardno, "
		  +" p.state  state, "
		  +" p.blackflag  blackflag, "
		  +" bs.status bs_status, "
		  +" bs.genmode genmode, "
		  +" vi.vehicleplate  vehicleplate, "
		  +" p.invoiceprint "
		  +" from csms_customer c "
		  +" left join csms_prepaidc p on c.id = p.customerid "
		  +" left join csms_carobucard_info ci on ci.prepaidcid = p.id "
		  +" left join csms_vehicle_info vi on vi.id =ci.vehicleid "
		  +" left join CSMS_BLACKLIST_TEMP bs on p.cardno = bs.cardno "
		  +" where 1 = 1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(flag)){
			if("1".equals(flag)){//根据客户名称查询
				sqlp.eq("c.organ", code);
				sqlp.eq("c.idcode", idCardString.split("_")[0]);
				sqlp.eq("c.idtype", idCardString.split("_")[1]);
				if(secondno != null){
				sqlp.eq("c.secondno", secondno);	
				}
				
			}else if("2".equals(flag)){//卡号查询
				sqlp.eq("p.cardno", code);
			}else if("3".equals(flag)){//根据车牌号查询
			 sqlp.eq("vi.vehicleplate", code.split("_")[0]);
			 sqlp.eq("vi.vehiclecolor", code.split("_")[2]);
			}else if("4".equals(flag)){//客户号自动查询
				sqlp.eq("c.userno", code);
			}
		}else{
			sqlp.eq("p.cardno", code);
		}
		sql=sql.append(sqlp.getParam());
		pager = this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		return pager;
	}


	public void updateServiceType(String cardNo,String seritem) {
		// TODO Auto-generated method stub
		String sql = "update CSMS_bill_get set seritem = ? where cardbankno = ?";
		super.update(sql,seritem,cardNo);
		
	}

	public List<Map<String, Object>> getPrepaidCByCustomerId(Long customerId) {
		StringBuffer sql = new StringBuffer("select a.cardno cardno from csms_prepaidc a where a.customerid=?");
		return queryList(sql.toString(), customerId);
	}
	public List findCustomerList(String code, String idCardString) {
		StringBuffer sql = new StringBuffer("select "
		   +" c.userno userno, "
		   +" c.organ organ, "
		   +" (case when c.idtype ='0' then '军官证' "
		   +" when c.idtype ='1' then '身份证' "
		   +" when c.idtype ='2' then '营业执照' "
		   +" when c.idtype ='3' then '其他' "
		   +" when c.idtype ='4' then '临时身份证' "
		   +" when c.idtype ='5' then '入境证' "
		   +" when c.idtype ='6' then '驾驶证' "
		   +" when c.idtype ='7' then '组织机构代码证' "
		   +" when c.idtype ='8' then '护照' "
		   +" when c.idtype ='9' then '信用代码证'  "
		   +" when c.idtype ='10' then '港澳居民来往内地通行证'  "
		   +" when c.idtype ='11' then '台湾居民来往大陆通行证' "
		   +" when c.idtype ='12' then '武警警官证件' end ) idtype, "
		   +" c.idcode idcode, "
		   +" c.secondno secondno, "
		   +" c.secondname secondname "
		  +" from csms_customer c "
		  +" where 1 = 1 ");

		if(StringUtil.isNotBlank(code) && StringUtil.isNotBlank(idCardString)){
			SqlParamer sqlp=new SqlParamer();
			sqlp.eq("c.organ", code);
			sqlp.eq("c.idcode", idCardString.split("_")[0]);
			sqlp.eq("c.idtype", idCardString.split("_")[1]);
			sql=sql.append(sqlp.getParam());
			return queryList(sql.toString(),sqlp.getList().toArray());
		}
		return new ArrayList<Map<String, Object>>(0);
	}

	public boolean isBlack(String cardNo) {
		String sql = "select count(*) from CSMS_BLACKLIST_TEMP where cardNo=?";
		return queryCount(sql, cardNo) > 0;
	}

	public int updateFirstRecharge(String firstRecharge, Long id) {
		String sql = "update csms_prepaidC set firstRecharge=? where id=?";
		return update(sql, firstRecharge, id);
	}
	
	public Map<String,Object> findCardSysBalance(String cardNo) {
		String sql = "select c.cardbalance from csms_cardbalancedata c where c.cardcode=? order by c.balancetime desc";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	public Pager findAllCardInfosByUserNo(String userno, Pager pager) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select p.cardno,p.state, '22' as cardtype from csms_prepaidc p ")
				.append("     inner join csms_customer c on c.id = p.customerid ")
				.append("   where 1=1 and c.userno=? ")
				.append("   union all ")
				.append("   select ai.cardno, ai.state, '23' as cardtype from csms_accountc_info ai ")
				.append("     inner join  csms_subaccount_info si on ai.accountid = si.id ")
				.append("     inner join csms_accountc_apply aa on si.applyid = aa.id  ")
				.append("     inner join csms_customer c on aa.customerid = c.id  ")
				.append("   where 1=1 and c.userno=? ");

		if (pager == null) {
			pager = new Pager();
		}
		return this.findByPages(sqlBuilder.toString(), pager, new Object[] {userno, userno});
	}

	/**
	 * 根据客户id查找储值卡号
	 * @param customerId
	 * @return
	 */
	public List<Map<String,Object>> findCardNoByCustomerId(Long customerId){
		return queryList("select cardNo from csms_prepaidc where customerId = ?",customerId);
	}
}
