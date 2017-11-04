package com.hgsoft.other.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCApplyHisDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.MainAccountInfoHisDao;
import com.hgsoft.account.entity.AccountBussiness;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.common.Enum.AccountBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.TagBussinessTypeEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.dao.BillGetDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerHisDao;
import com.hgsoft.customer.dao.InvoiceDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class ReceiptDao extends BaseDao{
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
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private AccountCApplyHisDao accountCApplyHisDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@SuppressWarnings("rawtypes")
	public List<Receipt> findAll(Receipt receipt,Date starTime,Date endTime) {
		Receipt temp = null;
		List<Receipt> receipts=new ArrayList<Receipt>();
		if (receipt!= null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_RECEIPT where 1=1 ");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(starTime!=null){
				sql.append(" and createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			if(endTime !=null){
				sql.append(" and createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			Map map = FieldUtil.getPreFieldMap(Receipt.class,receipt);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()>0) {
				for(int i=0;i<list.size();i++){
					temp = new Receipt();
					this.convert2Bean(list.get(i), temp);
					receipts.add(temp);
				}
			}

		}
		return receipts;
	}
	/**
	 * 回执重打
	 * @param receipt
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Pager findPrintAgainAllByPager(Pager pager,Date starTime,Date endTime) {
		Receipt temp = null;
		List<Receipt> receipts=new ArrayList<Receipt>();
		StringBuffer sql = new StringBuffer("select * from (select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=1 and cp.typecode=19  ");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//账户缴款
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=5 and cp.typecode=1 ");
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//账户缴款修改
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id where cp.parenttypecode=5 and cp.typecode=3 ");
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//账户缴款冲正
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ "  cp.customerid=c.id where cp.parenttypecode=5 and cp.typecode=2 ");
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		//储值卡快速充值冲正
		sql.append(" union all select cp.*,c.organ from CSMS_RECEIPT cp join csms_customer c on "
				+ " cp.customerid=c.id  where cp.parenttypecode=1 and cp.typecode=20 ");
		if(starTime!=null){
			sql.append(" and cp.createTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			sql.append(" and cp.createTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}

		sql.append(" ) order by createtime desc");
		return findByPages(sql.toString(), pager, null);

	}

	public Receipt find(Receipt receipt){
		List<Receipt> receipts=findAll(receipt,null,null);
		if(receipts.size()>0){
			return receipts.get(0);
		}else{
			return null;
		}
	}
	public Receipt findById(Long id){
		Receipt receipt=new Receipt();
		receipt.setId(id);
		return find( receipt);
	}

	@SuppressWarnings("rawtypes")
	public void save(Receipt receipt){
		if(receipt.getId()==null){
			receipt.setId(sequenceUtil.getSequenceLong("SEQ_CSMSRECEIPT_NO"));
		}
		//测试
		Random random = new Random();
		int result = random.nextInt(333333);
		receipt.setReceiptNo("HB"+result);
		//receipt.setReceiptNo("HB250225");
		receipt.setPrintNum(0);
		Map map = FieldUtil.getPreFieldMap(Receipt.class,receipt);
		StringBuffer sql=new StringBuffer("insert into CSMS_RECEIPT");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));

	}
	
	
	//	账户信息：1
	//	储值卡：2
	//	记帐卡：3
	//	电子标签：4
	//	客户信息：5
	//	日结：6
	//	车辆信息业务：7
	public Receipt saveByBussiness(PrepaidCBussiness prepaidCBussiness,AccountBussiness accountBussiness,
								   TagBusinessRecord tagBusinessRecord,CustomerBussiness customerBussiness,AccountCBussiness accountCBussiness){
		Receipt receipt=new Receipt();
		VehicleInfo vehicleInfo=null;
		if(prepaidCBussiness!=null){

			receipt.setParentTypeCode("2");
			receipt.setTypeCode(prepaidCBussiness.getState());
			receipt.setTypeChName(PrepaidCardBussinessTypeEnum.getNameByValue(prepaidCBussiness.getState()));
			receipt.setBusinessId(prepaidCBussiness.getId());
			receipt.setCustomerId(prepaidCBussiness.getUserid());

			vehicleInfo=vehicleInfoDao.findByPrepaidCardNo(prepaidCBussiness.getCardno());
			if(vehicleInfo!=null){
				receipt.setVehicleHisId(vehicleInfo.getHisSeqId());
			}

			receipt.setOperNo(prepaidCBussiness.getOperNo());
			receipt.setOperName(prepaidCBussiness.getOperName());
			receipt.setPrintNum(0);
			BillGet billGet=billGetDao.findPreCardNo(prepaidCBussiness.getCardno());
			if(billGet!=null){
				receipt.setSeritem(billGet.getSerItem());
			}
			receipt.setOperId(prepaidCBussiness.getOperid());
			receipt.setPlaceId(prepaidCBussiness.getPlaceid());
			receipt.setPlaceNo(prepaidCBussiness.getPlaceNo());
			receipt.setPlaceName(prepaidCBussiness.getPlaceName());
			receipt.setCreateTime(prepaidCBussiness.getTradetime());
		}else if(accountCBussiness!=null){
			receipt.setParentTypeCode("3");
			receipt.setBusinessId(accountCBussiness.getId());
			receipt.setCustomerId(accountCBussiness.getUserId());
			receipt.setTypeCode(accountCBussiness.getState());
			receipt.setOperId(accountCBussiness.getOperId());
			receipt.setPlaceId(accountCBussiness.getPlaceId());
			receipt.setTypeChName(AccountCBussinessTypeEnum.getNameByValue(accountCBussiness.getState()));
			vehicleInfo=vehicleInfoDao.findByAccountCNo(accountCBussiness.getCardNo());
			if(vehicleInfo!=null){
				receipt.setVehicleHisId(vehicleInfo.getHisSeqId());
			}
			BillGet billGet=null;
			if(StringUtil.isNotBlank(accountCBussiness.getCardNo())){
				billGet=billGetDao.findAccCardNo(accountCBussiness.getCardNo());
			}else{
				if(accountCBussiness.getAccountId()!=null){
					billGet=billGetDao.findByCardAccountID(accountCBussiness.getAccountId());
				}
			}

			if(billGet!=null){
				receipt.setSeritem(billGet.getSerItem());
			}
			receipt.setOperNo(accountCBussiness.getOperNo());
			receipt.setOperName(accountCBussiness.getOperName());
			receipt.setPlaceNo(accountCBussiness.getPlaceNo());
			receipt.setPlaceName(accountCBussiness.getPlaceName());
			//receipt.setCreateTime(accountCBussiness.getCreateTime());

		}else if(tagBusinessRecord!=null){
			receipt.setParentTypeCode("4");
			receipt.setBusinessId(tagBusinessRecord.getId());
			receipt.setCustomerId(tagBusinessRecord.getClientID());
			receipt.setTypeCode(tagBusinessRecord.getBusinessType());
			receipt.setOperId(tagBusinessRecord.getOperID());
			receipt.setPlaceId(tagBusinessRecord.getOperplaceID());
			receipt.setCreateTime(tagBusinessRecord.getOperTime());
			receipt.setTypeChName(TagBussinessTypeEnum.getNameByValue(tagBusinessRecord.getBusinessType()));
			vehicleInfo=vehicleInfoDao.findByTagNo(tagBusinessRecord.getTagNo());
			if(vehicleInfo!=null){
				receipt.setVehicleHisId(vehicleInfo.getHisSeqId());
			}else{
				vehicleInfo=vehicleInfoDao.findById(tagBusinessRecord.getVehicleID());
				if(vehicleInfo!=null){
					receipt.setVehicleHisId(vehicleInfo.getHisSeqId());
				}
			}
			receipt.setOperNo(tagBusinessRecord.getOperNo());
			receipt.setOperName(tagBusinessRecord.getOperName());
			receipt.setPlaceNo(tagBusinessRecord.getPlaceNo());
			receipt.setPlaceName(tagBusinessRecord.getPlaceName());
		}else if(customerBussiness!=null){
			receipt.setParentTypeCode("5");
			receipt.setBusinessId(customerBussiness.getId());
			receipt.setCustomerId(customerBussiness.getCustomerId());
			receipt.setTypeCode(customerBussiness.getType());
			receipt.setOperId(customerBussiness.getOperId());
			receipt.setPlaceId(customerBussiness.getPlaceId());
			receipt.setCreateTime(customerBussiness.getCreateTime());
			receipt.setTypeChName(CustomerBussinessTypeEnum.getNameByValue(customerBussiness.getType()));
			receipt.setOperNo(customerBussiness.getOperNo());
			receipt.setOperName(customerBussiness.getOperName());
			receipt.setPlaceNo(customerBussiness.getPlaceNo());
			receipt.setPlaceName(customerBussiness.getPlaceName());
		}else if(accountBussiness!=null){
			receipt.setParentTypeCode("1");
			receipt.setBusinessId(accountBussiness.getId());
			receipt.setCustomerId(accountBussiness.getUserId());
			receipt.setTypeCode(accountBussiness.getState());
			receipt.setOperId(accountBussiness.getOperId());
			receipt.setPlaceId(accountBussiness.getPlaceId());
			receipt.setTypeChName(AccountBussinessTypeEnum.getNameByValue(accountBussiness.getState()));
			//receipt.setCreateTime(accountBussiness.getCreateTime());
			receipt.setOperNo(accountBussiness.getOperNo());
			receipt.setOperName(accountBussiness.getOperName());
			receipt.setPlaceNo(accountBussiness.getPlaceNo());
			receipt.setPlaceName(accountBussiness.getPlaceName());
		}
		MainAccountInfo mainAccountInfo  = mainAccountInfoDao.findByMainId(receipt.getCustomerId());
		if(mainAccountInfo!=null)receipt.setMainAccountHisId(mainAccountInfo.getHisSeqId());
		Customer c = customerDao.findById(receipt.getCustomerId());
		if(c!=null)receipt.setCustomerHisId(c.getHisSeqId());
		Invoice invoice = invoiceDao.findDefaultById(receipt.getCustomerId());
		if(invoice!=null)receipt.setInvoiceTitle(invoice.getInvoiceTitle());
		if(receipt.getCreateTime()==null){
			receipt.setCreateTime(new Date());
		}
		save(receipt);
		return receipt;
	}

	public void saveByVehicleBussiness(VehicleBussiness vehicleBussiness){
		Receipt receipt=new Receipt();
		VehicleInfo vehicleInfo=null;
		if(vehicleBussiness!=null){

			receipt.setParentTypeCode("7");
			receipt.setBusinessId(vehicleBussiness.getId());
			receipt.setCustomerId(vehicleBussiness.getCustomerID());
			receipt.setTypeCode(vehicleBussiness.getType());
			receipt.setOperId(vehicleBussiness.getOperID());
			receipt.setPlaceId(vehicleBussiness.getPlaceID());
			receipt.setTypeChName(VehicleBussinessEnum.getNameByValue(vehicleBussiness.getType()));
			receipt.setOperNo(vehicleBussiness.getOperNo());
			receipt.setOperName(vehicleBussiness.getOperName());
			receipt.setPlaceNo(vehicleBussiness.getPlaceNo());
			receipt.setPlaceName(vehicleBussiness.getPlaceName());
		}
		receipt.setMainAccountHisId(mainAccountInfoDao.findByMainId(receipt.getCustomerId()).getHisSeqId());
		receipt.setCustomerHisId(customerDao.findById(receipt.getCustomerId()).getHisSeqId());

		//发票取消，可能查询不到客户的发票信息
		if(invoiceDao.findDefaultById(receipt.getCustomerId())!=null){
			receipt.setInvoiceTitle(invoiceDao.findDefaultById(receipt.getCustomerId()).getInvoiceTitle());
		}
		if(receipt.getCreateTime()==null){
			receipt.setCreateTime(new Date());
		}
		save(receipt);
	}

	public void updatePrintNum(Long id){
		StringBuffer sql = new StringBuffer("update CSMS_RECEIPT set printNum=printNum+1 where id="+id);
		saveOrUpdate(sql.toString());
	}

	public Map<String,Object> findVehByReceipt(Receipt receipt){
		Map<String,Object> map=new HashMap<String, Object>();
		VehicleInfo vehicleInfo =vehicleInfoDao.findByHisId(receipt.getVehicleHisId());
		map.put("vehicleInfo",vehicleInfo);
		if(vehicleInfo==null){
			map.put("vehicleInfo",vehicleInfoHisDao.findByHisId(receipt.getVehicleHisId()));
			return map;
		}
		return map;
	}

	public  Map<String,Object> findCusByReceipt(Receipt receipt){
		Map<String,Object> map=new HashMap<String, Object>();
		Customer customer=customerDao.findByHisId(receipt.getCustomerHisId());
		map.put("customer", customer);
		if(customer==null){
			//这里怎么能把customerHis当作customer，肯定cast to的错误
			//customer = new Customer();
			//CustomerHis customerHis = customerHisDao.findByHisId(receipt.getCustomerHisId());
			customer = customerHisDao.findCustomerByHisId(receipt.getCustomerHisId());

			map.put("customer", customer);
			return map;
		}
		return map;
	}

	public  Map<String,Object> findMainAccByReceipt(Receipt receipt){
		Map<String,Object> map=new HashMap<String, Object>();
		MainAccountInfo mainAccountInfo=mainAccountInfoDao.findByHisId(receipt.getMainAccountHisId());
		map.put("mainAccountInfo", mainAccountInfo);
		if(mainAccountInfo==null){
			map.put("mainAccountInfo", mainAccountInfoHisDao.findByHisId(receipt.getMainAccountHisId()));
		}
		return map;
	}

	/**
	 * 根据业务id和业务大类查找回执
	 * @param businessId 业务id
	 * @param parentTypeCode 业务大类
	 * @return
	 */
	public Receipt findByBusIdAndPTC(Long businessId,String parentTypeCode) {
		String sql = "select "+FieldUtil.getFieldMap(Receipt.class,new Receipt()).get("nameStr")+" from CSMS_RECEIPT where businessId=? and parentTypeCode=? ";
		List<Map<String, Object>> list = queryList(sql,businessId,parentTypeCode);
		Receipt receipt = null;
		if (!list.isEmpty()&&list.size()==1) {
			receipt = new Receipt();
			this.convert2Bean(list.get(0), receipt);
			return receipt;
		}
		return new Receipt();
	}
	public int[] batchUpdateReceipt(final List<Long> receiptIds) {
		String sql = "update CSMS_RECEIPT set printNum=printNum+1 where id=?";
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter(){

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException{
				ps.setLong(1, receiptIds.get(i));
			}

			@Override
			public int getBatchSize() {
				if(receiptIds==null)return 0;
				return receiptIds.size();
			}
		});
	}
	/**
	 * 销户之后打印回执
	 * @param id
	 * @return
	 */
	public Receipt findByCustomerId(Long id) {
		String sql = "select * from CSMS_RECEIPT where customerid="+id+" order by id desc";
		List<Map<String, Object>> list = queryList(sql);
		Receipt receipt = null;
		if (!list.isEmpty()&&list.size()>0) {
			receipt = new Receipt();
			this.convert2Bean(list.get(0), receipt);
		}

		return receipt;
	}

	/**
	 * 分页查询回执
	 * @param pager
	 * @param params
	 * @return
	 */
	public Pager findPrintReceiptsByPager(Pager pager,Map<String,String> params){
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(Receipt.class,new Receipt()).get("nameStr")+" from csms_receipt where 1=1 ");

		SqlParamer sqlParamer = new SqlParamer();
		if(StringUtil.isNotBlank(params.get("organ"))){
			sqlParamer.eq("organ",params.get("organ"));
		}
		if(StringUtil.isNotBlank(params.get("placeId"))){
			sqlParamer.eq("placeId",params.get("placeId"));
		}
		if(StringUtil.isNotBlank(params.get("parentTypeCode"))){
			sqlParamer.eq("parentTypeCode",params.get("parentTypeCode"));
		}
		if(StringUtil.isNotBlank(params.get("typeCode"))){
			sqlParamer.eq("typeCode",params.get("typeCode"));
		}
		if(StringUtil.isNotBlank(params.get("alreadyPrint"))){
			if("1".equals(params.get("alreadyPrint"))){
				//1代表已经打印过
				sqlParamer.ge("printNum", 1);
			}else{
				sqlParamer.lt("printNum", 1);
			}
		}
		if(StringUtil.isNotBlank(params.get("beginTime"))){
			sqlParamer.geDate("createTime",params.get("beginTime"));
		}
		if(StringUtil.isNotBlank(params.get("endTime"))){
			sqlParamer.leDate("createTime",params.get("endTime"));
		}
		if(StringUtil.isNotBlank(params.get("vehicleColor"))){
			sqlParamer.eq("vehicleColor",params.get("vehicleColor"));
		}
		if(StringUtil.isNotBlank(params.get("vehiclePlate"))){
			sqlParamer.eq("vehiclePlate",params.get("vehiclePlate"));
		}
		if(StringUtil.isNotBlank(params.get("cardNo"))){
			sqlParamer.eq("cardNo",params.get("cardNo"));
		}
		if(StringUtil.isNotBlank(params.get("tagNo"))){
			sqlParamer.eq("tagNo",params.get("tagNo"));
		}
		sql.append(sqlParamer.getParam());
		List list = sqlParamer.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by createtime desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}

	/**
	 * 根据历史id获取AccountCApply
	 * @param hisSeqId 历史id
	 * @return
	 */
	public Map<String,Object> findAccountCApplyByHisSeqId(Long hisSeqId){
		Map<String,Object> result = new HashMap<String,Object>();
		AccountCApply accountCApply = accountCApplyDao.findByHisId(hisSeqId);
		if (accountCApply != null) {
			result.put("accountCApply", accountCApply);
		} else {
			result.put("accountCApply", accountCApplyHisDao.findByHisId(hisSeqId));
		}
		return result;
	}

	/**
	 * 根据历史id获取AccountC
	 * @param hisSeqId 历史id
	 * @return
	 */
	public Map<String,Object> findAccountCInfoByHisSeqId(Long hisSeqId){
		Map<String,Object> result = new HashMap<String,Object>();
		AccountCInfo accountCInfo = accountCInfoDao.findByHisId(hisSeqId);
		if (accountCInfo != null) {
			result.put("accountCInfo", accountCInfo);
		} else {
			result.put("accountCInfo", accountCInfoHisDao.findByHisId(hisSeqId));
		}
		return result;
	}

	/**
	 * 保存Receipt(新)
	 * @param receipt
	 */
	public void saveReceipt(Receipt receipt){
		if(receipt.getId()==null) {
			receipt.setId(this.sequenceUtil.getSequenceLong("SEQ_CSMSRECEIPT_NO"));
		}
		receipt.setReceiptNo("HB"+System.currentTimeMillis()+new Random().nextInt(10));
		receipt.setPrintNum(0);
		Map<String,Object> map = FieldUtil.getPreFieldMap(Receipt.class,receipt);
		StringBuffer sql = new StringBuffer("insert into csms_receipt");
		sql.append(map.get("insertNameStrNotNull"));
		saveOrUpdate(sql.toString(),(List)map.get("paramNotNull"));
	}

	/**
	 * 根据id找Receipt
	 * @param id
	 * @return
	 */
	public Receipt findById_(Long id){
		String sql = "select " + FieldUtil.getFieldMap(Receipt.class,new Receipt()).get("nameStr") + " from csms_receipt where id = ?";
		List<Receipt> receiptList = super.queryObjectList(sql,Receipt.class,id);
		if(!CollectionUtils.isEmpty(receiptList)){
			return receiptList.get(0);
		}
		return new Receipt();
	}
	
	/**
	 * 香港联营卡系统
	 * 根据卡号和业务大类小类查找回执对象
	 * @param cardNo
	 * @param parentTypeCode
	 * @param typeCode
	 * @return
	 */
	public List<Map<String, Object>> findReceiptsByCardNoAndType(String cardNo, String parentTypeCode, String typeCode) {
		String sql = "";
		if (parentTypeCode == "3") {
			// 卡片大类
			sql = "select csms_receipt.receiptno as oldReceiptNo "
					+ " from csms_receipt "
					+ " left join csms_accountc_bussiness on csms_receipt.businessid=csms_accountc_bussiness.id "
					+ " where csms_accountc_bussiness.cardno='" + cardNo + "'"
					+ " and csms_accountc_bussiness.state='" + typeCode + "'"
					+ " order by csms_receipt.createtime desc";
		} else if (parentTypeCode == "4") {
			// 电子标签大类
			sql = "select csms_receipt.receiptno as oldReceiptNo "
					+ " from csms_receipt "
					+ " left join CSMS_Tag_BusinessRecord on csms_receipt.businessid=CSMS_Tag_BusinessRecord.id "
					+ " where CSMS_Tag_BusinessRecord.Tagno='" + cardNo + "'"
					+ " and CSMS_Tag_BusinessRecord.Businesstype='" + typeCode + "'"
					+ " order by csms_receipt.createtime desc";
		} else if (parentTypeCode == "7") {
			// 其他大类
			sql = "select csms_receipt.receiptno as oldReceiptNo "
					+ " from csms_receipt "
					+ " left join csms_vehicle_bussiness on csms_receipt.businessid=csms_vehicle_bussiness.id "
					+ " where 1=1"
					+ " and csms_vehicle_bussiness.Type='" + typeCode + "'"
					+ " order by csms_receipt.createtime desc";
		} else {
			return null;
		}
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}
	
	/**
	 * 香港联营卡系统
	 * 查询回执
	 * @param pager
	 * @param customer
	 * @return
	 */
	public Pager findReceiptsForACMS(Pager pager, Customer customer, String cardNo, Date startTime, Date endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer("select id, parenttypecode, typecode, printnum, createtime, cardno, tagno"
				+ " from csms_receipt"
				+ " where 1=1" 
				+ " and organ='" + customer.getOrgan() + "'");
		if (cardNo != null && !cardNo.isEmpty()) {
			sql.append(" and (cardno='" + cardNo + "'" + " or tagno='" + cardNo + "')");
		} // if
		if (startTime != null && endTime != null) {
			sql.append(" and (createtime>=to_date('" + format.format(startTime) + "','YYYY-MM-DD HH24:MI:SS')"
					+ " and createtime<=to_date('" + format.format(endTime) + "','YYYY-MM-DD HH24:MI:SS'))");
		} // if
		sql.append(" order by createtime desc");
		SqlParamer params = new SqlParamer();
		Object[] objects = params.getList().toArray();
		return findByPages(sql.toString(), pager, objects);
	}
	
}
