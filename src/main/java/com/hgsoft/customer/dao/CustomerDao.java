package com.hgsoft.customer.dao;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerImp;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.RegularUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CustomerDao extends BaseDao{
	private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);

	public Customer findByIdTypeAndIdCode(CustomerImp customerImp){
		StringBuffer sql = new StringBuffer("select * from csms_customer where 1=1");
		SqlParamer param = new SqlParamer();
		if(customerImp!=null){
			String idType = customerImp.getIdType();
			if(StringUtil.isNotBlank(idType)){
				param.eq("idType", idType);
			}
			String idCode = customerImp.getIdCode();
			if(StringUtil.isNotBlank(idCode)){
				param.eq("idCode", idCode);
			}
		}
		
		sql.append(param.getParam());
		List<Map<String,Object>> list = queryList(sql.toString(),param.getList().toArray());
		
		Customer customer = null;
		if(list.size()>0){
			customer = new Customer();
			convert2Bean(list.get(0), customer);
		}
		return customer;
	}
	public Customer findbyTagInfoId(Long id){
		String sql = "select c.* from csms_customer c join csms_tag_info ti on ti.clientId=c.id where ti.id=?";
		List<Customer> customers = super.queryObjectList(sql, Customer.class, id);
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}
	
	public List<Map<String,Object>> findByTypeCodeSecondNo(Long id,String idType, String idCode,String secondNo) {
		StringBuffer sql = new StringBuffer("select * from csms_customer where 1=1 ");
		SqlParamer params=new SqlParamer();
		params.ne("id",String.valueOf(id));
		if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}
		if(StringUtil.isNotBlank(secondNo)){
			params.eq("secondNo", secondNo);
		}
		sql=sql.append(params.getParam());
 		return queryList(sql.toString(), params.getList().toArray());
	}
	
	public Integer hasCardOrTag(Long id){
		String sql = "select sum(c) s from ( select count(*) c from csms_tag_info where clientid=?"
					+" union all"
					+" select count(*) c from csms_accountc_info where customerid=?"
					+" union all"
					+" select count(*) c from csms_prepaidc where customerid=?)";
		List<Map<String,Object>> list = queryList(sql, id,id,id);
		return ((BigDecimal)list.get(0).get("S")).intValue();
	}
	
	public void updateDefaultSerPwd(Long id){
		String sql = "update csms_customer set defaultserpwd='0' where id=?";
		saveOrUpdate(sql, id);
	}
	
	public Customer findByCardNo(String cardNo) {
		Customer customer = null;
		String sql = null;
		if(RegularUtil.checkCardNo(cardNo)) {
			if (RegularUtil.isPrePaidCard(cardNo)) {
				sql = "select c.* from csms_customer c join CSMS_PrePaidC ai on c.id=ai.customerid where ai.cardno=?";
			}else if(!RegularUtil.isPrePaidCard(cardNo)){
				sql = "select c.* from csms_customer c join csms_accountc_info ai on c.id=ai.customerid where ai.cardno=?";
			}
		} else {
			return null;
		}

		List<Customer> customers = super.queryObjectList(sql, Customer.class, cardNo);
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}

	public Customer findCustomer(PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		List<Customer> list = new ArrayList<>();
		Customer customer = null;
		if(paymentCardBlacklistRecv!=null){
			if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getCardCode())){
				String sql = "select c.* from csms_customer c join csms_accountc_info ai on c.id=ai.customerid where ai.cardno=?";
				list = super.queryObjectList(sql, Customer.class, paymentCardBlacklistRecv.getCardCode());
			}else if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getAcbAccount())){
				String sql = "select c.* from csms_customer c join CSMS_AccountC_apply aa on c.id=aa.CustomerID where aa.bankAccount=?";
				list = super.queryObjectList(sql, Customer.class, paymentCardBlacklistRecv.getAcbAccount());
			}

			if (list == null || list.isEmpty()) {
				return null;
			}
			customer = list.get(0);
		}

		return customer;
	}
	
	public Customer findByTypeCode(String idType,String idCode) {
		String sql = "select * from csms_customer where idType=? and idCode=?";
		List<Customer> customers = super.queryObjectList(sql, Customer.class, idType, idCode);
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}
	
	public boolean findByTypeAndCode(String idType,String idCode){
		String sql = "select count(*) from csms_customer where idType=? and idCode=?";
		return super.count(sql, idType, idCode) > 0;
	}
	
	public void save(Customer customer) {
		customer.setHisSeqId(-customer.getId());
		if (customer.getSystemType() == null)
			customer.setSystemType("1");
		Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
		StringBuffer sql = new StringBuffer("insert into CSMS_Customer");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/* save(sql.toString()); */
	}

	public void delete(Long id) {
		String sql = "delete from CSMS_Customer where id=?";
		super.delete(sql, id);
	}

	public void update(Customer customer) {
		Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
		StringBuffer sql = new StringBuffer("update CSMS_Customer set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), customer.getId());
		/*
		 * sql.append(FieldUtil.getFieldMap(Customer.class,customer).get(
		 * "nameAndValue")+" where id="+customer.getId());
		 */
		/* update(sql.toString()); */
	}
	
	public void updateNotNull(Customer customer){
		Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
		StringBuffer sql=new StringBuffer("update CSMS_Customer set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),customer.getId());
	}
	
	//专门给更新不为null又可以为""的对象用
	public void update4NotNullStr(Customer customer){
		Map map = FieldUtil.getPreFieldMap4UpdateNotNull(Customer.class,customer);
		StringBuffer sql=new StringBuffer("update CSMS_Customer set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),customer.getId());
	}

	public Customer findByHisId(Long hisId) {
		String sql = "select * from CSMS_Customer ";
		if (hisId == null) {
			sql = sql + " where HisSeqID is null";
		} else {
			sql = sql + " where HisSeqID=" + hisId;
		}
		List<Map<String, Object>> list = queryList(sql);
		Customer customer = null;
		if (!list.isEmpty()) {
			customer = new Customer();
			this.convert2Bean(list.get(0), customer);
		}

		return customer;
	}

	public Customer findById(Long id) {
		String sql = "select * from CSMS_Customer where id = ?";
		List<Customer> customers = super.queryObjectList(sql, Customer.class, id);
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}

	public List<Map<String, Object>> findAll(Customer customer) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (customer == null) {
			logger.warn("数据异常：CustomerDao.findAll方法查询没有条件");
			return list;
		}
		Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
		String condition = (String)map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("CustomerDao.findAll方法查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_Customer where 1=1 ");
		sql.append(condition);
		list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());

		return list;
	}

	public Pager findByPage(Pager pager, Customer customer) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_Customer t where 1=1");
		if (customer != null) {
			Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			/*
			 * sql.append(FieldUtil.getFieldMap(Customer.class,customer).get(
			 * "nameAndValueNotNull"));
			 */
			sql.append(" order by id desc ");
			return this.findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else {
			sql.append(" order by id desc ");
			return this.findByPages(sql.toString(), pager, null);
		}

	}
	
	public Pager findAll(Pager pager) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_Customer t where 1=1 order by updatetime desc");
		return this.findByPages(sql.toString(), pager, null);

	}

	public Customer find(Customer customer) {
		if (customer == null) {
			logger.warn("数据异常：CustomerDao.find方法查询没有条件");
			return null;
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_Customer  where 1=1");
		Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("CustomerDao.find方法查询条件为空");
		}
		sql.append(condition);
		sql.append(" order by id desc");
		List<Customer> customers = super.queryObjectList(sql.toString(), Customer.class, ((List) map.get("paramNotNull")).toArray());
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}

	public Customer findByCustomer(Customer customer) {
		Customer temp = null;
		if (customer == null) {
			logger.warn("数据异常：CustomerDao.findByCustomer方法查询没有条件");
			return temp;
		}
		Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
		String condition = (String) map.get("selectNameStrNotNull");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("数据异常：CustomerDao.findByCustomer方法查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_Customer ");
		sql.append(condition);
		sql.append(" order by id desc");
		List<Customer> customers = super.queryObjectList(sql.toString(), Customer.class, ((List) map.get("paramNotNull")).toArray());
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}

	public Pager findByPage(Pager pager, Customer customer, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_Customer t where 1=1");
		if (StringUtils.isNotBlank(startDate)) {
			sql.append(" and cancelTime >=to_date('" + startDate + " 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" and cancelTime <=to_date('" + endDate + " 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		if (customer != null) {
			/*
			 * sql.append(FieldUtil.getFieldMap(Customer.class,customer).get(
			 * "nameAndValueNotNull"));
			 */
			Map map = FieldUtil.getPreFieldMap(Customer.class, customer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by cancelTime desc ");
			return this.findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else {
			sql.append(" order by cancelTime desc ");
			return this.findByPages(sql.toString(), pager, null);
		}

	}

	public Customer findByIdNo(String idType, String idCode) {
		return find(new Customer(idType, idCode));
	}
	
	public Customer findByCustomerNameAndIdNo(String customerName,String idType, String idCode) {
		return find(new Customer(customerName,idType, idCode));
	}

	/**
	 * 判断该客户是否可注销（储值卡、记帐卡、电子标签都已销户）
	 * 
	 * @return
	 */
	public boolean checkCustomerToCancel(Long customerId) {
		CallableStatement proc = null;
		boolean result = false;
		Connection con = null;
		try {
			con = jdbcUtil.getJdbcTemplate().getDataSource().getConnection();
			proc = con.prepareCall("{  call cancleisok(?,?) }");
			proc.setLong(1, customerId);
			proc.registerOutParameter(2, OracleTypes.BIGINT);
			proc.execute();
			Long countNum = proc.getLong(2);
			proc.close();
			con.close();
			if (countNum == 0) {
				result = true;
			}
		} catch (SQLException e) {
			logger.error("checkCustomerToCancel", e);
		} finally {
			try {
				if (proc != null)
					proc.close();
			} catch (SQLException e) {
				logger.error("CallableStatement close", e);
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Connection close", e);
			}
		}
		return result;
	}

	public Map<String, Object> authenticationCheck(String idType, String idCode, String servicePwd, String cardNo,
			String type) {
		List<Map<String, Object>> listMap = null;
		StringBuffer sql = new StringBuffer("select * from("
				+ " select c.ID ID,c.UserNo UserNo,c.Organ Organ,c.ServicePwd ServicePwd,c.UserType UserType,c.LinkMan LinkMan,c.systemType systemType,"
				+ " c.IdType IdType,c.IdCode IdCode,c.registeredCapital registeredCapital,c.Tel Tel,c.Mobile Mobile,"
				+ " c.ShortTel ShortTel,c.Addr Addr,c.ZipCode ZipCode,c.Email Email,c.State State,"
				+ " c.cancelTime cancelTime,c.OperId OperId,c.updateTime updateTime,c.firRunTime firRunTime,"
				+ " c.PlaceId PlaceId,c.HisSeqID HisSeqID,p.CardNo CardNo"
				+ "  from CSMS_Customer c  join CSMS_PrePaidC p on p.customerID = c.id where  c.state='1' and p.State = '0'"
				+ " union all "
				+ " select c.ID ID,c.UserNo UserNo,c.Organ Organ,c.ServicePwd ServicePwd,c.UserType UserType,c.LinkMan LinkMan,c.systemType systemType,"
				+ " c.IdType IdType,c.IdCode IdCode,c.registeredCapital registeredCapital,c.Tel Tel,c.Mobile Mobile,"
				+ " c.ShortTel ShortTel,c.Addr Addr,c.ZipCode ZipCode,c.Email Email,c.State State,"
				+ " c.cancelTime cancelTime,c.OperId OperId,c.updateTime updateTime,c.firRunTime firRunTime,"
				+ " c.PlaceId PlaceId,c.HisSeqID HisSeqID,a.CardNo CardNo"
				+ " from CSMS_Customer c  join csms_accountc_info a on a.customerID = c.id where  c.state='1' and a.State = '0') ");
		if ("4".equals(type)) {
			sql.append(" where IdType=? and IdCode=? and  CardNo=?");
			listMap = queryList(sql.toString(), idType, idCode, cardNo);
		} else if ("3".equals(type)) {
			sql.append(" where  CardNo=?");
			listMap = queryList(sql.toString(), cardNo);
		} else {
			return null;
		}
		if (listMap != null && listMap.size() != 0)
			return listMap.get(0);
		return null;
	}

	public Customer getCustomerByPrepaidCardNo(String cardNo) {
		if (StringUtils.isBlank(cardNo)) {
			throw new ApplicationException("customerDao.getCustomerByPrepaidCardNo查询条件为空");
		}
		StringBuffer sql = new StringBuffer(
				"select distinct c.* from csms_prepaidc p inner join csms_customer c on p.customerId = c.id where c.state='1'");
		sql.append(" and p.cardno = ?");
		sql.append(" order by c.id");

		List<Customer> customers = super.queryObjectList(sql.toString(), Customer.class, cardNo);
		if (customers == null || customers.isEmpty()) {
			return null;
		} else if (customers.size() > 1) {
			logger.error("customerDao.getCustomerByPrepaidCardNo卡号[{}]有多条[{}]记录", cardNo, customers.size());
			throw new ApplicationException("customerDao.getCustomerByPrepaidCardNo有多条记录");
		}
		return customers.get(0);
	}

	public Customer getCustomerByAccountCardNo(String cardNo) {
		if (StringUtils.isBlank(cardNo)) {
			throw new ApplicationException("customerDao.getCustomerByAccountCardNo查询条件为空");
		}
		StringBuffer sql = new StringBuffer(
				"select distinct c.* from csms_accountc_info a, csms_customer c where a.customerId = c.id");
		sql.append(" and a.cardno = ?");
		sql.append(" order by c.id");

		List<Customer> customers = super.queryObjectList(sql.toString(), Customer.class, cardNo);
		if (customers == null || customers.isEmpty()) {
			return null;
		} else if (customers.size() > 1) {
			logger.error("customerDao.getCustomerByAccountCardNo卡号[{}]有多条[{}]记录", cardNo, customers.size());
			throw new ApplicationException("customerDao.getCustomerByAccountCardNo有多条记录");
		}
		return customers.get(0);
	}

	/**
	 * 根据客户号和服务密码查询
	 * 
	 * @param userNo
	 *            客户号
	 * @return
	 */
	public Customer findByUserNo(String userNo) {
		
		return find(new Customer(userNo));
	}

	// TODO: 2017/4/19 这里需要改,记帐卡申请已经没有保证金设置
	public List<Map<String, Object>> getCustomerByBank(String bankAccount) {
		StringBuffer sql = new StringBuffer(
				"select a.*,c.id subId from csms_customer a left join csms_accountc_apply b on a.id=b.customerid left join csms_subaccount_info c on b.id=c.ApplyID where a.state='1' and b.AppState='6' and b.bankaccount=?");
		List<Map<String, Object>> list = queryList(sql.toString(), bankAccount);
		return list;
	}

	public Pager getAccountCList(Pager pager, Long customerId, AccountCInfo accountCInfo, VehicleInfo vehicleInfo,
			TagInfo tagInfo) {
		/*StringBuffer sql = new StringBuffer(
				"select a.suit,a.customerid,a.cardno,a.state,a.issuetime,d.vehicleplate,d.vehiclecolor,c.tagno,ROWNUM as num,(case when bs.status='1' or bs.status is null then '未进入黑名单' "
						+ "when bs.status='2' then '挂失黑名单' " + "when bs.status='3' then '低值黑名单' "
						+ "when bs.status='4' then '透支黑名单' " + "when bs.status='5' then '禁用黑名单' "
						+ "when bs.status='6' then '电子标签黑名单进入' " + "else '无卡注销黑名单' end) genCau "
						+ "from csms_accountc_info a " + "left join csms_carobucard_info b on a.id=b.AccountCID "
						+ "left join csms_tag_info c on b.tagid=c.id "
						+ "left join csms_vehicle_info d on b.vehicleid=d.id "
						//csms_dark_list 废除
						//+ "left join csms_dark_list dl on a.cardno = dl.cardno "
						+ "left join CSMS_BLACKLIST_STATUS bs on a.cardno = bs.cardno "
						+ "where a.customerid=" + customerId + " ");*/
		StringBuffer sql = new StringBuffer(
						"select a.suit, " +
						"       a.customerid, " +
						"       a.cardno, " +
						"       a.state, " +
						"       a.issuetime, " +
						"       a.vehicleplate, " +
						"       a.vehiclecolor, " +
						"       a.tagno, " +
						"       b.gencau " +
						"  from (select suit, " +
						"               customerid, " +
						"               cardno, " +
						"               state, " +
						"               issuetime, " +
						"               vehicleplate, " +
						"               vehiclecolor, " +
						"               tagno " +
						"          from (select a.suit, " +
						"                       a.customerid, " +
						"                       a.cardno, " +
						"                       a.state, " +
						"                       a.issuetime, " +
						"                       d.vehicleplate, " +
						"                       d.vehiclecolor, " +
						"                       c.tagno, " +
						"                       ROW_NUMBER() OVER(PARTITION BY a.cardno ORDER BY bs.GENTIME DESC) RN " +
						"                  from csms_accountc_info a " +
						"                  left join csms_carobucard_info b " +
						"                    on a.id = b.AccountCID " +
						"                  left join csms_tag_info c " +
						"                    on b.tagid = c.id " +
						"                  left join csms_vehicle_info d " +
						"                    on b.vehicleid = d.id " +
						"                  left join CSMS_BLACKLIST_TEMP bs " +
						"                    on a.cardno = bs.cardno where a.customerid=? ) " +
						"         where RN = 1) a " +
						"  join (select c.cardno, " +
						"               LISTAGG(c.genCau, ', ') WITHIN GROUP(ORDER BY c.cardno) gencau " +
						"          from (select a.cardno, " +
						"                       (case " +
						"                         when bs.status = '1' or bs.status is null then " +
						"                          '未进入黑名单' " +
						"                         when bs.status = '2' then " +
						"                          '挂失黑名单' " +
						"                         when bs.status = '3' then " +
						"                          '低值黑名单' " +
						"                         when bs.status = '4' then " +
						"                          '透支黑名单' " +
						"                         when bs.status = '5' then " +
						"                          '禁用黑名单' " +
						"                         when bs.status = '6' then " +
						"                          '电子标签黑名单进入' " +
						"                         else " +
						"                          '无卡注销黑名单' " +
						"                       end) genCau " +
						"                  from csms_accountc_info a " +
						"                  left join csms_carobucard_info b " +
						"                    on a.id = b.AccountCID " +
						"                  left join csms_tag_info c " +
						"                    on b.tagid = c.id " +
						"                  left join csms_vehicle_info d " +
						"                    on b.vehicleid = d.id " +
						"                  left join CSMS_BLACKLIST_TEMP bs " +
						"                    on a.cardno = bs.cardno) c " +
						"         group by c.cardno) b " +
						"    on a.cardno = b.cardno "
						+ "where a.customerid =? ");
		//sql.append(customerId);
		SqlParamer sqlPar = new SqlParamer();
		/*
		 * if(accountCInfo!=null &&
		 * StringUtil.isNotBlank(accountCInfo.getCardNo())){
		 * sqlPar.eq("a.cardno", accountCInfo.getCardNo()); }
		 * if(vehicleInfo!=null){
		 * if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
		 * sqlPar.eq("d.vehiclecolor", vehicleInfo.getVehicleColor()); }
		 * if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
		 * sqlPar.eq("d.vehicleplate", vehicleInfo.getVehiclePlate()); } }
		 * if(tagInfo!=null && StringUtil.isNotBlank(tagInfo.getTagNo())){
		 * sqlPar.eq("c.tagno", tagInfo.getTagNo()); }
		 */
		//sql.append(sqlPar.getParam());

		sql.append(" order by a.issuetime desc");
		sqlPar.getList().add(customerId);
		sqlPar.getList().add(customerId);
				
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	
	public Pager getAccountCListForAMMS(Pager pager, Long customerId, AccountCInfo accountCInfo, VehicleInfo vehicleInfo,
		TagInfo tagInfo,String bankCode) {
		StringBuffer sql = new StringBuffer("select ROWNUM as num,ai.suit,ai.customerId,ai.cardNo,ai.state,ai.issueTime issueTime,vi.vehiclePlate,vi.vehicleColor,ti.tagNo,bs.status genCau ");
		sql.append(" from csms_accountc_info ai left join csms_carobucard_info coc on ai.id=coc.accountcid left join csms_tag_info ti on coc.tagid=ti.id ");
		sql.append(" left join csms_vehicle_info vi on coc.vehicleid=vi.id left join csms_blacklist_temp bs on ai.cardno = bs.cardno ");
		sql.append(" left join CSMS_joinCardNoSection j on substr(ai.cardno, 0, length(ai.cardno) - 1) between j.code and j.endcode");
		sql.append(" where j.cardtype ='23' and j.bankno =? and ai.customerid=?");
		
		sql.append(" order by issueTime desc");
		String[] params = new String[2];
		params[0] = bankCode;
		params[1] = customerId.toString();
		return this.findByPages(sql.toString(), pager, params);
	}

	public Pager getPrepaidCList(Pager pager, Long customerId, PrepaidC prePaidc, VehicleInfo vehicleInfo,
			TagInfo tagInfo) {
		StringBuffer sql = new StringBuffer(
				"select b.suit, " +
						"       b.customerid, " +
						"       b.cardno, " +
						"       b.state, " +
						"       b.InvoicePrint, " +
						"       b.SaleTime, " +
						"       b.vehicleplate, " +
						"       b.vehiclecolor, " +
						"       b.tagno, " +
						"       c.gencau " +
						"  from (select suit, " +
						"               customerid, " +
						"               a.cardno, " +
						"               state, " +
						"               InvoicePrint, " +
						"               SaleTime, " +
						"               vehicleplate, " +
						"               vehiclecolor, " +
						"               tagno " +
						"          from (select a.suit, " +
						"                       a.customerid, " +
						"                       a.cardno, " +
						"                       a.state, " +
						"                       a.InvoicePrint, " +
						"                       a.SaleTime, " +
						"                       d.vehicleplate, " +
						"                       d.vehiclecolor, " +
						"                       c.tagno, " +
						"                       ROW_NUMBER() OVER(PARTITION BY a.cardno ORDER BY bs.GENTIME DESC) RN " +
						"                  from csms_prepaidc a " +
						"                  left join csms_carobucard_info b " +
						"                    on a.id = b.prepaidcid " +
						"                  left join csms_tag_info c " +
						"                    on b.tagid = c.id " +
						"                  left join csms_vehicle_info d " +
						"                    on b.vehicleid = d.id " +
						"                  left join CSMS_BLACKLIST_TEMP bs " +
						"                    on a.cardno = bs.cardno where a.customerid=? ) a " +
						"         where RN = 1 " +
						"        ) b " +
						"  left join (select a.cardno, " +
						"               LISTAGG((case " +
						"                         when bs.status = '1' or bs.status is null then " +
						"                          '未进入黑名单' " +
						"                         when bs.status = '2' then " +
						"                          '挂失黑名单' " +
						"                         when bs.status = '3' then " +
						"                          '低值黑名单' " +
						"                         when bs.status = '4' then " +
						"                          '透支黑名单' " +
						"                         when bs.status = '5' then " +
						"                          '禁用黑名单' " +
						"                         when bs.status = '6' then " +
						"                          '电子标签黑名单进入' " +
						"                         else " +
						"                          '无卡注销' " +
						"                       end), " +
						"                       ', ') WITHIN GROUP(ORDER BY a.cardno) gencau " +
						"          from csms_prepaidc a " +
						"          left join CSMS_BLACKLIST_TEMP bs " +
						"            on a.cardno = bs.cardno " +
						"         group by a.cardno) c " +
						"    on c.cardno = b.cardno ");
		sql.append(" where b.customerid =? ");
		//sql.append(customerId);
		/*StringBuffer sql = new StringBuffer(
				"select a.suit,a.customerid,a.cardno,a.state,a.SaleTime,d.vehicleplate,d.vehiclecolor,c.tagno,ROWNUM as num,(case when bs.status='1' or bs.status is null then '未进入黑名单' "
						+ "when bs.status='2' then '挂失黑名单' " + "when bs.status='3' then '低值黑名单' "
						+ "when bs.status='4' then '透支黑名单' " + "when bs.status='5' then '禁用黑名单' "
						+ "when bs.status='6' then '电子标签黑名单进入' " + "else '无卡注销' end) genCau " + "from csms_prepaidc a "
						+ "left join csms_carobucard_info b on a.id=b.prepaidcid "
						+ "left join csms_tag_info c on b.tagid=c.id "
						+ "left join csms_vehicle_info d on b.vehicleid=d.id "
						//csms_dark_list 废除
						//+ "left join csms_dark_list dl on a.cardno = dl.cardno "
						+ "left join CSMS_BLACKLIST_STATUS bs on a.cardno = bs.cardno "
						+ "where a.customerid=" + customerId + " ");*/

		SqlParamer sqlPar = new SqlParamer();
		/*
		 * if(prePaidc!=null && StringUtil.isNotBlank(prePaidc.getCardNo())){
		 * sqlPar.eq("a.cardno", prePaidc.getCardNo()); } if(vehicleInfo!=null){
		 * if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
		 * sqlPar.eq("d.vehiclecolor", vehicleInfo.getVehicleColor()); }
		 * if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
		 * sqlPar.eq("d.vehicleplate", vehicleInfo.getVehiclePlate()); } }
		 * if(tagInfo!=null && StringUtil.isNotBlank(tagInfo.getTagNo())){
		 * sqlPar.eq("c.tagno", tagInfo.getTagNo()); }
		 */
		
		//sql.append(sqlPar.getParam());
		sqlPar.getList().add(customerId);
		sqlPar.getList().add(customerId);
		
		sql.append(" order by b.SaleTime desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	
	public Pager getPrepaidCListForAMMS(Pager pager, Long customerId, PrepaidC prePaidc, VehicleInfo vehicleInfo,
			TagInfo tagInfo,String bankCode) {
		StringBuffer sql = new StringBuffer("select ROWNUM as num,p.suit,p.customerId,p.cardNo,p.state,p.saleTime saleTime,vi.vehiclePlate,vi.vehicleColor,ti.tagNo,bs.status genCau ");
		sql.append(" from csms_prepaidc p left join csms_carobucard_info coc on p.id=coc.prepaidcid left join csms_tag_info ti on coc.tagid=ti.id ");
		sql.append(" left join csms_vehicle_info vi on coc.vehicleid=vi.id left join (select * from csms_blacklist_temp where id in (select max(id) from csms_blacklist_temp  "
				+ "where cardno in(select cardno from csms_prepaidc where customerid=? )group by cardno)) bs on p.cardno = bs.cardno ");
		sql.append(" left join CSMS_joinCardNoSection j on substr(p.cardno, 0, length(p.cardno) - 1) between j.code and j.endcode");
		sql.append(" where j.cardtype ='22' and j.bankno =? and p.customerid=?");
		sql.append(" order by p.saleTime desc");
		String[] params = new String[3];
		params[0] = customerId.toString();
		params[1] = bankCode;
		params[2] = customerId.toString();
		return this.findByPages(sql.toString(), pager, params);
	}

	public Pager getVehicleList(Pager pager, Long customerId, VehicleInfo vehicleInfo) {
		StringBuffer sql = new StringBuffer(
				"select a.customerid,a.vehicleplate,a.vehiclecolor,a.model,a.owner,a.vehicleEngineNo,a.IdentificationCode,"
						+ "a.vehicleWheelBases,ROWNUM as num from csms_vehicle_info a where a.customerID=" + customerId
						+ " ");
		SqlParamer sqlPar = new SqlParamer();
		/*
		 * if(vehicleInfo!=null){
		 * if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
		 * sqlPar.eq("a.vehiclecolor", vehicleInfo.getVehicleColor()); }
		 * if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
		 * sqlPar.eq("a.vehicleplate", vehicleInfo.getVehiclePlate()); } }
		 */
		sql.append(sqlPar.getParam());
		sql.append(" order by a.id desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	
	public Pager getVehicleListForAMMS(Pager pager, Long customerId, VehicleInfo vehicleInfo,String bankCode) {
		String sql = " SELECT allCard.* FROM"
					+" (SELECT vi.id,vi.customerid,vi.vehicleplate,vi.vehiclecolor,vi.model,vi.owner,vi.vehicleEngineNo,vi.identificationCode,vi.vehicleWheelBases,NVL (p.cardno,a.cardno) AS allCardNo,ROWNUM AS num"
					+" FROM csms_vehicle_info vi"
					+" JOIN csms_carobucard_info coc ON coc.vehicleId = vi.id"
					+" LEFT JOIN csms_prepaidc p ON coc.prepaidcid = p.id"
					+" LEFT JOIN csms_accountc_info a ON coc.accountcId = a.id where vi.customerId=?) allCard"
					+" left join csms_joinCardNoSection j on substr(allCard.allCardNo, 0, length(allCard.allCardNo) - 1) between j.code and j.endcode"
					+" where (allCard.allCardNo  is null or j.bankno = ?) order by allCard.id desc";
		
		String[] params = new String[2];
		params[0] = customerId.toString();
		params[1] = bankCode;
		return this.findByPages(sql.toString(), pager,params);
	}

	public Pager getTagInfoList(Pager pager, Long customerId, TagInfo tagInfo) {
		/*
		 * StringBuffer sql = new StringBuffer(
		 * "select a.tagno,a.issuetype,a.salestype,a.Installman,a.Issuetime,a.tagstate state,ROWNUM as num  "
		 * + "from csms_tag_info a where a.ClientID="+customerId+" ");
		 */
		/*StringBuffer sql = new StringBuffer(
				"	select a.tagno,a.oldOrgan,a.issuetype,a.salestype,a.Installman,a.Issuetime,a.tagstate state,a.opername opername, "
						+ " v.vehicleplate vehicleplate,v.vehiclecolor vehiclecolor,v.Model Model,v.IdentificationCode IdentificationCode," +
						" v.vehicleWeightLimits vehicleWeightLimits, (case when bs.status='1' or bs.status is null then '未进入黑名单' "
						+ "when bs.status='2' then '挂失黑名单' " + "when bs.status='3' then '低值黑名单' "
						+ "when bs.status='4' then '透支黑名单' " + "when bs.status='5' then '禁用黑名单' "
						+ "when bs.status='6' then '电子标签黑名单进入' " + "else '无卡注销' end) genCau ,ROWNUM as num  "
						+ " from csms_tag_info a left join csms_carobucard_info  c on a.id = c.tagid "
						+ " left join csms_vehicle_info v on c.vehicleid = v.id" +
						" left join csms_blacklist_temp bs on a.obuSerial = bs.OBUID  where a.ClientID=" + customerId + " ");*/
		StringBuffer sql = new StringBuffer(
				"select customerid,tagno,oldOrgan,issuetype,salestype,Installman,Issuetime,state,opername,vehicleplate,vehiclecolor,"
			  + "Model,IdentificationCode,vehicleWeightLimits,(case when gencau is null then '未进入黑名单' else gencau end) gencau"
			  +	"  from "
			  + "	    (select a.clientid customerid,a.tagno,a.oldOrgan,a.issuetype,a.salestype,a.Installman,a.Issuetime,a.tagstate state,a.opername opername,a.obuSerial obuSerial,"
			  + "	            v.vehicleplate vehicleplate,v.vehiclecolor vehiclecolor,v.Model Model,v.IdentificationCode IdentificationCode,"
			  + "	            v.vehicleWeightLimits vehicleWeightLimits"
			  + "	            from csms_tag_info a "
			  + "	            left join csms_carobucard_info  c on a.id = c.tagid "
			  + "	            left join csms_vehicle_info v on c.vehicleid = v.id where a.clientid=?) c "
			  + "	     left join (select bs.obuid,LISTAGG((case "
			  + "							                        when bs.status = '1' or bs.status is null then "
			  + "							                         '未进入黑名单' "
			  + "							                         when bs.status = '2' then "
			  + "							                          '挂失黑名单' "
			  + "							                         when bs.status = '3' then "
			  + "							                          '低值黑名单' "
			  + "							                         when bs.status = '4' then "
			  + "							                          '透支黑名单' "
			  + "							                        when bs.status = '5' then "
			  + "							                          '禁用黑名单' "
			  + "							                         when bs.status = '6' then "
			  + "							                         '电子标签黑名单进入' "
			  + "							                         else "
			  + "							                          '无卡注销' "
			  + "							                       end), "
			  + "							                       ', ') WITHIN GROUP(ORDER BY bs.obuid) gencau "
			  + "	    from CSMS_BLACKLIST_TEMP bs group by bs.obuid) b on c.obuserial = b.obuid  where  1=1 ");

		SqlParamer sqlPar = new SqlParamer();
		if(customerId != null){
			sqlPar.eq("c.customerid", customerId);
		}
		/*
		 * if(tagInfo!=null && StringUtil.isNotBlank(tagInfo.getTagNo())){
		 * sqlPar.eq("a.tagno", tagInfo.getTagNo()); }
		 */
		sql.append(sqlPar.getParam());
		
		sqlPar.getList().add(customerId);
		
		sql.append(" order by Issuetime desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	
	public Pager getTagInfoListForAMMS(Pager pager, Long customerId, TagInfo tagInfo,String bankCode) {
		String sql = " select allCard.* from (select nvl(p.cardNo,a.cardNo) allCardNo,ti.oldOrgan,ti.tagno,ti.issueTime,ti.tagstate,vi.vehicleplate,vi.vehiclecolor,vi.model,vi.identificationCode,vi.vehicleWeightLimits,ti.BlackFlag"
					+" from csms_tag_info ti"
					+" left join csms_carobucard_info coc on coc.tagid = ti.id"
					+" left join csms_vehicle_info vi on coc.vehicleid = vi.id"
					+" left join csms_prepaidc p on coc.prepaidcid = p.id"
					+" left join csms_accountc_info a on coc.accountcid = a.id where ti.clientid=?) allCard"
					+" left join csms_joinCardNoSection j on substr(allCard.allCardNo, 0, length(allCard.allCardNo) - 1) between j.code and j.endcode"
					+" where (allCard.allCardNo  is null or j.bankno = ?) order by allCard.issueTime desc";
		
		String[] params = new String[2];
		params[0] = customerId.toString();
		params[1] = bankCode;
		return this.findByPages(sql.toString(), pager,params);
	}

	public Pager getUnBindTagInfoList(Pager pager, Long customerId, TagInfo tagInfo) {
		StringBuffer sql = new StringBuffer("select a.tagno, a.oldOrgan, a.issuetype, a.salestype, a.Installman, a.Issuetime, a.blackFlag, "
				+ "a.tagstate state, a.obuSerial, bs.gencau, ROWNUM as num from csms_tag_info a "
				  + "	     left join (select bs.obuid,LISTAGG((case "
				  + "							                        when bs.status = '1' or bs.status is null then "
				  + "							                         '未进入黑名单' "
				  + "							                         when bs.status = '2' then "
				  + "							                          '挂失黑名单' "
				  + "							                         when bs.status = '3' then "
				  + "							                          '低值黑名单' "
				  + "							                         when bs.status = '4' then "
				  + "							                          '透支黑名单' "
				  + "							                        when bs.status = '5' then "
				  + "							                          '禁用黑名单' "
				  + "							                         when bs.status = '6' then "
				  + "							                         '电子标签黑名单进入' "
				  + "							                         else "
				  + "							                          '无卡注销' "
				  + "							                       end), "
				  + "							                       ', ') WITHIN GROUP(ORDER BY bs.obuid) gencau "
				  + "from CSMS_BLACKLIST_TEMP bs group by bs.obuid) bs on a.obuSerial = bs.obuid "
				  + "left join (select TagID from csms_carobucard_info where tagid is not null) t on a.id = t.TagID where t.TagID is null"
				  + " and a.clientid = "
				+ customerId + " ");
		SqlParamer sqlPar = new SqlParamer();
		/*
		 * if(tagInfo!=null && StringUtil.isNotBlank(tagInfo.getTagNo())){
		 * sqlPar.eq("a.tagno", tagInfo.getTagNo()); }
		 */
		sql.append(sqlPar.getParam());
		sql.append(" order by a.Issuetime desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}

	public Pager getUnUseVehicleList(Pager pager, Long customerId) {
		StringBuffer sql = new StringBuffer(
				"select a.customerid,a.vehicleplate,a.vehiclecolor,a.model,a.owner,a.vehicleEngineNo,a.IdentificationCode,"
						+ "a.vehicleWheelBases,ROWNUM as num from csms_vehicle_info a left join csms_carobucard_info b on a.id=b.vehicleid where b.prepaidcid is null and b.accountcid is null and b.tagid is null and a.customerID="
						+ customerId);
		sql.append(" order by a.id desc");
		return this.findByPages(sql.toString(), pager, null);
	}
	/* hzw
	 * 综合信息查询 
	 * 记帐卡子账户列表
	 * 2017年9月9日
	 */
	public Pager getAccountCSubList(Pager pager, Long customerId) {
		StringBuffer sql = new StringBuffer(
				"select caa.bankAccount, caa.accName, caa.AppState, caa.AccountType, "
				+ "csi.BailBalance as bailSum, "
				+ "(select count(cai.id)  from csms_accountc_info cai where csi.id = cai.accountid) as cardNum, "
				+ "(select csi.id from csms_subaccount_info csi where  caa.id =csi.applyid) as accountId "
				+ "from csms_accountc_apply caa join CSMS_SubAccount_info csi on caa.id = csi.applyid "
				+ "where caa.customerid= "
				+ customerId);
		SqlParamer sqlPar = new SqlParamer();
		sql.append(sqlPar.getParam());
		sql.append(" order by caa.apptime desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	
	/* hzw
	 * 综合信息查询 
	 * 记帐卡详情列表
	 * 2017年9月9日
	 */
	public Pager getAccountCInfoList(Pager pager, Long accountCId) {
		StringBuffer sql = new StringBuffer(
				"select cai.cardNo, cai.state, cai.bail, cvi.vehicleColor, cvi.vehiclePlate "
				+ " from csms_accountc_info cai left join Csms_Carobucard_Info cci  "
				+ " on cai.id = cci.accountcid left join csms_vehicle_info cvi on cci.vehicleid = cvi.id  "
				+ " where cai.accountid= "
				+ accountCId );
		SqlParamer sqlPar = new SqlParamer();
		sql.append(sqlPar.getParam());
		sql.append(" order by cai.issuetime desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	

	/**
	 * 接口使用
	 * 
	 * @param customerList
	 * @param type
	 * @return
	 */
	public int[] batchUpdateAddressEmail(final List<Customer> customerList, final String type) {
		String sql = "update csms_customer c set " + (type.equals("1") ? "c.addr=?, c.zipcode=?," : "c.email=?,")
				+ " c.hisseqid=? where c.userno=?";
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				Customer customer = customerList.get(i);
				if (type.equals("1")) {
					ps.setString(1, customer.getAddr());
					ps.setString(2, customer.getZipCode());
					ps.setLong(3, customer.getHisSeqId());
					ps.setString(4, customer.getUserNo());
				} else {
					ps.setString(1, customer.getEmail());
					ps.setLong(2, customer.getHisSeqId());
					ps.setString(3, customer.getUserNo());
				}

			}

			@Override
			public int getBatchSize() {
				return customerList.size();
			}
		});
	}

	public int[] batchUpdateEmail(final List<Customer> customerList) {
		String sql = "update csms_customer c set c.email=?, c.hisseqid=? where c.userno=?";
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				Customer customer = customerList.get(i);
				ps.setString(1, customer.getEmail());
				ps.setLong(2, customer.getHisSeqId());
				ps.setString(3, customer.getUserNo());
			}

			@Override
			public int getBatchSize() {
				return customerList.size();
			}
		});
	}

	/**
	 * 接口使用
	 * 
	 * @param userNos
	 * @return
	 */
	public Map<String, Map<String, Object>> findAllByUserNo(String userNos) {
		StringBuffer sql = new StringBuffer("select * from csms_customer where 1=1 ");
		if (StringUtil.isNotBlank(userNos)) {
			sql.append(" and userno in( " + userNos + " )");
		}
		List<Map<String, Object>> list = queryList(sql.toString());
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).get("userno").toString(), list.get(i));
		}
		return map;
	}

	/**
	 * 根据子帐户id找客户
	 * 
	 * @param subAccountID
	 * @return
	 */
	public Customer findBySubAccountID(Long subAccountID) {
		String sql = "select c.* from csms_customer c " + " join CSMS_MainAccount_Info m on c.id=m.mainid "
				+ " join CSMS_SubAccount_Info s on m.id=s.mainid " + " where s.id=?";

		List<Customer> customers = super.queryObjectList(sql.toString(), Customer.class, subAccountID);
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}
	
	public boolean getCard(Long id){
		String sql = "select p.id from csms_prepaidc p join csms_customer c on p.customerid=c.id where c.id=?"
				+ "union all select ai.id from csms_accountc_info ai join csms_customer c on ai.customerid=c.id where c.id=?"
				+ "union all select ti.id from csms_tag_info ti join csms_customer c on ti.clientid=c.id where  c.id=?";
		List<Map<String,Object>> list = queryList(sql,id,id,id);
		return list.size()<=0;
	}
	public Pager getMacaoAccountCList(Pager pager, Long customerId) {
		StringBuffer sql = new StringBuffer(
				"select a.customerid,a.cardno,a.state,a.issuetime,d.vehicleplate,d.vehiclecolor,c.tagno,ROWNUM as num,dl.genCau,a.suit "
						+ "from csms_accountc_info a "
						+ "join csms_cardholder_info cci on cci.typeid = a.id and cci.type=2 "
						+ "join CSMS_MACAO_BANKACCOUNT cmb on cmb.id = cci.macaobankaccountid "
						+ "left join csms_carobucard_info b on a.id=b.AccountCID "
						+ "left join csms_tag_info c on b.tagid=c.id "
						+ "left join csms_vehicle_info d on b.vehicleid=d.id "
						+ "left join (select bt.cardno,LISTAGG((case "
						+ "					                 when bt.status = '1' or bt.status is null then "
						+ "					                  '未进入黑名单' "
						+ "					                 when bt.status = '2' then "
						+ "					                  '挂失黑名单' "
						+ "					                 when bt.status = '3' then "
						+ "					                  '低值黑名单' "
						+ "					                 when bt.status = '4' then "
						+ "					                  '透支黑名单' "
						+ "					                 when bt.status = '5' then "
						+ "					                  '禁用黑名单' "
						+ "					                 when bt.status = '6' then "
						+ "					                  '电子标签黑名单进入' "
						+ "					                 else "
						+ "					                  '无卡注销' "
						+ "					               end), "
						+ "					               ', ') WITHIN GROUP(order by bt.cardno) gencau "
						+ "					  from CSMS_BLACKLIST_TEMP bt "
						+ "					  inner join csms_accountc_info ai on bt.cardno=ai.cardno "
						+ "					  inner join csms_cardholder_info chi on chi.typeid = ai.id and chi.type=2 "
						+ "					  inner join CSMS_MACAO_BANKACCOUNT mba on mba.id=chi.macaobankaccountid"
						+ "					  where mba.mainid=? group by bt.cardno) dl on a.cardno = dl.cardno "
						+ "where CMB.MainId=? ");

		//SqlParamer sqlPar = new SqlParamer();
		//sql.append(sqlPar.getParam());
		ArrayList paramList = new ArrayList();
		paramList.add(customerId);
		paramList.add(customerId);
		sql.append(" order by a.issuetime desc");
		return this.findByPages(sql.toString(), pager, paramList.toArray());
	}

	public Pager getMacaoTagInfoList(Pager pager, Long customerId) {
		StringBuffer sql = new StringBuffer(
				"	select a.tagno,a.issuetype,a.salestype,a.Installman,a.Issuetime,a.tagstate state,a.opername opername, "
						+ " v.vehicleplate vehicleplate,v.vehiclecolor vehiclecolor,v.Model Model,v.IdentificationCode IdentificationCode,v.vehicleWeightLimits vehicleWeightLimits,dl.genCau, ROWNUM as num  "
						+ " from csms_tag_info a "
						+ " join csms_cardholder_info cci on cci.typeid = a.id and cci.type=3 "
						+ " join CSMS_MACAO_BANKACCOUNT cmb on cmb.id = cci.macaobankaccountid "
						+ " left join csms_carobucard_info  c on a.id = c.tagid "
						+ " left join csms_vehicle_info v on c.vehicleid = v.id "
						+ " left join (select bt.OBUID,LISTAGG((case "
						+ "					                 when bt.status = '1' or bt.status is null then "
						+ "					                  '未进入黑名单' "
						+ "					                 when bt.status = '2' then "
						+ "					                  '挂失黑名单' "
						+ "					                 when bt.status = '3' then "
						+ "					                  '低值黑名单' "
						+ "					                 when bt.status = '4' then "
						+ "					                  '透支黑名单' "
						+ "					                 when bt.status = '5' then "
						+ "					                  '禁用黑名单' "
						+ "					                 when bt.status = '6' then "
						+ "					                  '电子标签黑名单进入' "
						+ "					                 else "
						+ "					                  '无卡注销' "
						+ "					               end), "
						+ "					               ', ') WITHIN GROUP(order by bt.OBUID) gencau "
						+ "					  from CSMS_BLACKLIST_TEMP bt "
						+ "					  inner join csms_tag_info ai on bt.OBUID=ai.obuSerial "
						+ "					  inner join csms_cardholder_info chi on chi.typeid = ai.id and chi.type=3 "
						+ "					  inner join CSMS_MACAO_BANKACCOUNT mba on mba.id=chi.macaobankaccountid"
						+ "					  where mba.mainid=? group by bt.OBUID) dl on a.obuSerial = dl.OBUID "
						+ " where cmb.MainId=? ");

		
		ArrayList paramList = new ArrayList();
		paramList.add(customerId);
		paramList.add(customerId);
		sql.append(" order by a.Issuetime desc");
		return this.findByPages(sql.toString(), pager, paramList.toArray());
	}

	public Pager getMacaoVehicleList(Pager pager, Long id) {
		StringBuffer sql = new StringBuffer(
				"select a.customerid,a.vehicleplate,a.vehiclecolor,a.model,a.owner,a.vehicleEngineNo,a.IdentificationCode,"
						+ "a.vehicleWheelBases,ROWNUM as num from "
						+"csms_vehicle_info a "
						+ "join csms_cardholder_info cci on cci.typeid = a.id and cci.type=1 "
						+ "join CSMS_MACAO_BANKACCOUNT cmb on cmb.id = cci.macaobankaccountid "
						+" where cmb.MainId=" + id
						+ " ");
		SqlParamer sqlPar = new SqlParamer();
		sql.append(sqlPar.getParam());
		sql.append(" order by a.id desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}

	public Pager getMacaoUnBindTagInfoList(Pager pager, Long id) {
		StringBuffer sql = new StringBuffer("select a.tagno,a.issuetype,a.salestype,a.Installman,a.Issuetime,"
				+ "a.tagstate state ,ROWNUM as num from "
				+"csms_tag_info a "
				+ "join csms_cardholder_info cci on cci.typeid = a.id and cci.type='3' "
				+ "join CSMS_MACAO_BANKACCOUNT cmb on cmb.id = cci.macaobankaccountid "
				+" where a.id not in(select TagID from csms_carobucard_info where tagid is not null) and cmb.MainId="
				+ id + " ");
		SqlParamer sqlPar = new SqlParamer();
		/*
		 * if(tagInfo!=null && StringUtil.isNotBlank(tagInfo.getTagNo())){
		 * sqlPar.eq("a.tagno", tagInfo.getTagNo()); }
		 */
		sql.append(sqlPar.getParam());
		sql.append(" order by a.Issuetime desc");
		return this.findByPages(sql.toString(), pager, sqlPar.getList().toArray());
	}
	
	/**
	 * 
	 * @param idType
	 * @param idCode
	 * @param secondNo 可为空
	 * @return Customer
	 */
	public Customer findByIdTypeCodeSecondNo(String idType, String idCode, String secondNo) {
		List<Customer> list = null;
		if(StringUtil.isNotBlank(secondNo)){
			String sql = "select * from CSMS_Customer where idType=? and idCode=? and secondNo=? order by id desc " ;
			list = super.queryObjectList(sql, Customer.class, idType, idCode, secondNo);
		}else{
			String sql = "select * from CSMS_Customer where idType=? and idCode=? and secondNo is null order by id desc " ;
			list = super.queryObjectList(sql, Customer.class, idType, idCode);
		}

		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}

	public Pager findByCardNo(Pager pager, String cardNo) {
		StringBuffer sql23 = new StringBuffer("select c.* from csms_customer c  join csms_accountc_info ai on c.id = ai.customerid  where ai.cardno = ? ");
		StringBuffer sql22 = new StringBuffer("select c.* from csms_customer c  join csms_prepaidc  p on c.id = p.customerid  where p.cardno = ? ");
		if(!RegularUtil.checkCardNo(cardNo)){
			return pager;
		}else{
			if(!RegularUtil.isPrePaidCard(cardNo)){//记帐卡
				return this.findByPages(sql23.toString(), pager, new Object[] {cardNo});
			}else if(RegularUtil.isPrePaidCard(cardNo)){//储值卡
				return this.findByPages(sql22.toString(), pager, new Object[] {cardNo});
			}
		}
		return pager;
	}

	/**
	 * 根据卡号或者银行账号查询客户号
	 * @param paymentCardBlacklistRecv
	 * @return
     */
	public Map<String, Object> getCustomerNo(PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		if(paymentCardBlacklistRecv!=null){
			if (StringUtil.isNotBlank(paymentCardBlacklistRecv.getCardCode())){
				sql.append("select cr.UserNo from csms_customer cr join CSMS_AccountC_info ai on ai.customerID=cr.id " +
						"and ai.CardNo=?");
				list = queryList(sql.toString(),paymentCardBlacklistRecv.getCardCode());
			}else if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getAcbAccount())){
				sql.append("select cr.UserNo from csms_customer cr join CSMS_AccountC_apply aa on aa.CustomerID=cr.id " +
						"and aa.bankAccount=?");
				list = queryList(sql.toString(),paymentCardBlacklistRecv.getAcbAccount());

			}
			if(list.size()>0){
				return list.get(0);
			}
		}

		return null;
	}
	
	
	public Customer findByIdNoWithSecondNo(String idType,String idCode) {
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(Customer.class,new Customer()).get("nameStr")+" from CSMS_Customer  "
				+ " where idType=? and idCode=? and secondNo is not null ");
		sql.append(" order by id desc ");

		List<Customer> customers = super.queryObjectList(sql.toString(), Customer.class, idType, idCode);
		if (customers == null || customers.isEmpty()) {
			return null;
		}
		return customers.get(0);
	}

	public List<Map<String, Object>> findCardNoByIdTypeAndIdCode(Long customerId) {
		List<Map<String, Object>> list = null;
		String sql = "select p.cardno cardno,'5' 卡类型  from csms_prepaidc p "
				+ "where p.state='0' and p.customerid=? "
				+ " union all select a.cardno cardno,'6' 卡类型 from"
				+ " csms_accountc_info a where a.state='0' and a.customerid=?";
		list = queryList(sql, customerId, customerId);
		return list;
	}

	public List<Map<String, Object>> findCustomerList(String organ,
			String idCode, String idType) {

		if(StringUtils.isBlank(organ) || StringUtils.isBlank(idCode) || StringUtils.isBlank(idType)) {
			return new ArrayList<Map<String, Object>>(0);
		}

	    StringBuilder sqlBuilder = new StringBuilder()
				.append(" select c.userno userno, c.organ organ, ")
				.append("  (case ")
				.append("    when c.idtype ='0' then '军官证' ")
				.append("    when c.idtype ='1' then '身份证' ")
				.append("    when c.idtype ='2' then '营业执照' ")
				.append("    when c.idtype ='3' then '其他'")
				.append("    when c.idtype ='4' then '临时身份证' ")
				.append("    when c.idtype ='5' then '入境证' ")
				.append("    when c.idtype ='6' then '驾驶证' ")
				.append("    when c.idtype ='7' then '组织机构代码证' ")
				.append("    when c.idtype ='8' then '护照' ")
				.append("    when c.idtype ='9' then '信用代码证' ")
				.append("    when c.idtype ='10' then '港澳居民来往内地通行证' ")
				.append("    when c.idtype ='11' then '台湾居民来往大陆通行证' ")
				.append("    when c.idtype ='12' then '武警警官证件' ")
				.append("   end) idtype, ")
				.append("  c.idcode idcode, c.secondno secondno, c.secondname secondname ")
				.append(" from csms_customer c ")
				.append(" where 1 = 1  ");

		SqlParamer sqlp=new SqlParamer();
		sqlp.eq("c.organ", organ);
		sqlp.eq("c.idcode", idCode);
		sqlp.eq("c.idtype", idType);
		sqlBuilder.append(sqlp.getParam());
		return queryList(sqlBuilder.toString(),sqlp.getList().toArray());
	}
	public Pager findByUserNO(Pager pager, String userNo) {
		StringBuffer sql = new StringBuffer("select c.* from csms_customer c  where c.userno = ? ");
		return this.findByPages(sql.toString(), pager, new Object[] {userNo});
	}

}
