package com.hgsoft.agentCard.dao;

import com.hgsoft.agentCard.entity.CardBusinessInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CardBusinessInfoDao extends BaseDao {

	private static final Logger logger = LoggerFactory.getLogger(CardBusinessInfoDao.class);

	public void saveCardBusinessInfo(CardBusinessInfo cardBusinessInfo) {
		StringBuffer sql = new StringBuffer("insert into CSMS_Card_business_info(");
		sql.append(FieldUtil.getFieldMap(CardBusinessInfo.class, cardBusinessInfo).get("nameStr") + ") values(");
		sql.append(FieldUtil.getFieldMap(CardBusinessInfo.class, cardBusinessInfo).get("valueStr") + ")");
		save(sql.toString());
	}

	public Pager findCardBusinessInfosByNum(Pager pager, Long dataNum) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper,row_number() over (order by c.id) as num "
				+ "from CSMS_Card_business_info c where systemType='1' ");
		if (dataNum != null) {
			paramList.add(dataNum);
			sql.append("and rownum<= ? ");
		}
		return this.findByPages(sql.toString(), pager, paramList.toArray());
	}

	public Pager findCardBusinessInfosByFileName(Pager pager, String fileName) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper,row_number() over (order by c.id) as num "
				+ "from CSMS_Card_business_info c where 1=1 ");
		if (fileName != null) {
			paramList.add(fileName);
			sql.append("and fileName = ? ");
		}
		return this.findByPages(sql.toString(), pager, paramList.toArray());
	}


	public List<CardBusinessInfo> findByFileName(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw new ApplicationException("数据异常：CardBusinessInfoDao.findByFileName方法查询条件空");
		}

		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ " UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ " idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ " linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ " importTime,importPlace,importOper from CSMS_Card_business_info where 1=1 ");
		sql.append(" and fileName = ? ");
		sql.append(" order by id asc");
		return super.queryObjectList(sql.toString(), CardBusinessInfo.class, fileName);
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		CardBusinessInfoDao cardBusinessInfoDao = (CardBusinessInfoDao) context.getBean("cardBusinessInfoDao");
		System.out.println();
	}

	/***
	 * 根据卡号、业务类型查找正常处理业务记录（remark为空，isTransact = 1 ）
	 * @param cardNo
	 * @param creditCardNo
	 * @param oldCardNo
	 * @param oldCreditCardNo
	 * @param businessType
	 * @return
	 */
	public List<CardBusinessInfo> findBycardNoType(String cardNo, String creditCardNo, String oldCardNo, String oldCreditCardNo, String businessType) {
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper from CSMS_Card_business_info where remark is null and isTransact = 1 ");
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(cardNo)) {
			params.eq("UTCardNo", cardNo);
		}
		if (StringUtil.isNotBlank(creditCardNo)) {
			params.eq("creditCardNo", creditCardNo);
		}
		if (StringUtil.isNotBlank(oldCardNo)) {
			params.eq("oldUTCardNo", oldCardNo);
		}
		if (StringUtil.isNotBlank(oldCreditCardNo)) {
			params.eq("oldCreditCardNo", oldCreditCardNo);
		}
		if (StringUtil.isNotBlank(businessType)) {
			params.eq("businessType", businessType);
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects = list.toArray();

		List<Map<String, Object>> mapList = queryList(sql.toString(), Objects);


		CardBusinessInfo cardBusinessInfo = null;
		List<CardBusinessInfo> cardBusinessInfos = new ArrayList<CardBusinessInfo>();
		if (mapList != null && !mapList.isEmpty()) {
			for (Map<String, Object> c : mapList) {
				cardBusinessInfo = new CardBusinessInfo();
				this.convert2Bean(c, cardBusinessInfo);

				cardBusinessInfos.add(cardBusinessInfo);

			}
		}

		return cardBusinessInfos;

	}

	/***
	 * 查找存在09销卡业务记录且不存在换卡、补领、非过户补领
	 * @param cardNo
	 * @param creditCardNo
	 * @return
	 */
	public List<CardBusinessInfo> findAvailableCancelRecord(String cardNo, String creditCardNo) {
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper from CSMS_Card_business_info p where remark is null and isTransact = 1 " +
				"AND UTCardNo = ? " +
				"AND creditCardNo = ? " +
				"AND businesstype = '09' " +
				"AND NOT EXISTS(select * from CSMS_Card_business_info t where remark IS NULL AND isTransact = 1 AND t.BUSINESSTYPE  IN ('05','07','16') and p.UTCARDNO  = t.OLDUTCARDNO )");

		return super.queryObjectList(sql.toString(), CardBusinessInfo.class, cardNo, creditCardNo);
	}


	/***
	 * 根据卡号、查找开户业务记录（remark为空，isTransact = 1 ，businessType 为02,06,08,16）
	 * @param cardNo
	 * @return
	 */
	public List<CardBusinessInfo> findIssueBusinessRecordBycardNo(String cardNo) {
		String sql = "select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper from CSMS_Card_business_info where remark is null and isTransact = 1 and UTCardNo=?"
				+ " and businessType in ('02','06','08','16')";

		return super.queryObjectList(sql, CardBusinessInfo.class, cardNo);
	}

	/**
	 * 根据文件名，找出备注不为空的数据
	 *
	 * @param fileName
	 * @return
	 */
	public List<CardBusinessInfo> findByErrorAndName(String fileName) {
		String sql = "select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper from CSMS_Card_business_info where fileName=?"
				+ " and remark is not null and isTransact='2' order by id asc";
		return super.queryObjectList(sql, CardBusinessInfo.class, fileName);
	}

	/**
	 * 根据文件名，找到未处理的数据
	 *
	 * @param fileName
	 * @return
	 */
	public List<CardBusinessInfo> findByFileNameAndTransact(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw new ApplicationException("数据异常：CardBusinessInfoDao.findByFileNameAndTransact方法查询条件空");
		}
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ " UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ " idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ " linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ " importTime,importPlace,importOper,serviceFlowNO,dealTime,operId,operName,"
				+ " placeId,placeName,operNo,placeNo,systemType,servicePwd,bankCode,errorCode from CSMS_Card_business_info where 1=1 and isTransact='0' ");
		sql.append(" and fileName = ? ");
		sql.append(" order by id asc");

		return super.queryObjectList(sql.toString(), CardBusinessInfo.class, fileName);
	}

	/**
	 * 根据文件名，找到处理成功的数据
	 *
	 * @param fileName
	 * @return
	 */
	public List<CardBusinessInfo> findByFileNameAndTransactSuccess(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw new ApplicationException("数据异常：CardBusinessInfoDao.findByFileNameAndTransactSuccess方法查询条件空");
		}
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper,serviceFlowNO,dealTime,operId,operName,"
				+ "placeId,placeName,operNo,placeNo,systemType,servicePwd,bankCode,errorCode from CSMS_Card_business_info where 1=1 and isTransact='1' ");
		sql.append(" and fileName = ? ");
		sql.append(" order by id asc");
		return super.queryObjectList(sql.toString(), CardBusinessInfo.class, fileName);
	}

	public void update(CardBusinessInfo cardBusinessInfo) {
		StringBuffer sql = new StringBuffer("update CSMS_Card_business_info set ");
		sql.append(FieldUtil.getFieldMap(CardBusinessInfo.class, cardBusinessInfo).get("nameAndValue") + " where id=?");
		update(sql.toString(), cardBusinessInfo.getId());
	}

	public CardBusinessInfo findById(Long cardBusinessInfoId) {
		StringBuffer sql = new StringBuffer("select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper,serviceFlowNO,dealTime,operId,operName,"
				+ "placeId,placeName,operNo,placeNo,systemType,servicePwd,bankCode,errorCode from CSMS_Card_business_info c where 1=1 and id = ? ");
		sql.append("and id = ? ");
		List<CardBusinessInfo> businessInfos = super.queryObjectList(sql.toString(), CardBusinessInfo.class, cardBusinessInfoId);
		if (businessInfos == null || businessInfos.isEmpty()) {
			return null;
		}
		return businessInfos.get(0);
	}


	/**
	 * 联名卡（信用卡）查询客服流水列表
	 *
	 * @param pager
	 * @param customer
	 * @param createStartTime
	 * @param createEndTime
	 * @param businessStartTime
	 * @param businessEndTime
	 * @param cardType
	 * @param cardNo
	 * @param businessType
	 * @return
	 */
	public Pager findCreditServiceFlowlist(Pager pager, Customer customer, String createStartTime, String createEndTime,
	                                       String businessStartTime, String businessEndTime, String cardType, String cardNo, String businessType, ServiceFlowRecord serviceFlowRecord) {

		StringBuffer sql = new StringBuffer("select ID,BusinessTime,BusinessType,UTCardNo,CreditCardNo,"
				+ "OldUTCardNo,OldCreditCardNo,Organ,UserType,IdType,IdCode,CusTel,CusMobile,CusAddr,"
				+ "CusZipCode,CusEmail,LinkMan,LinkTel,LinkMobile,LinkAddr,LinkZipCode,Remark,FileName,"
				+ "IsTransact,ImportTime,ImportPlace,ImportOper,ServiceFlowNO,dealTime,operId,operName,"
				+ "placeId,placeName,operNo,placeNo,systemType,servicePwd,bankCode,errorCode  from CSMS_Card_business_info c where systemType='4' and IsTransact = '1' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		SqlParamer params = new SqlParamer();

		if (StringUtil.isNotBlank(createStartTime)) {
			params.geDate("dealTime", createStartTime + " 00:00:00");
		}
		if (StringUtil.isNotBlank(createEndTime)) {
			params.leDate("dealTime", createEndTime + " 23:59:59");
		}
		if (StringUtil.isNotBlank(businessStartTime)) {
			params.geDate("BusinessTime", businessStartTime + " 00:00:00");
		}
		if (StringUtil.isNotBlank(businessEndTime)) {
			params.leDate("BusinessTime", businessEndTime + " 23:59:59");
		}
		if (StringUtil.isNotBlank(businessType)) {
			params.eq("BusinessType", businessType);
		}
		if (serviceFlowRecord != null) {
			params.eq("placeId", serviceFlowRecord.getPlaceID());
			params.eq("placeNo", serviceFlowRecord.getPlaceNo());
		}
		if (StringUtil.isNotBlank(cardType) && StringUtil.isNotBlank(cardNo)) {
			params.eq(cardType, cardNo);
			/*if(cardType.equals("UTCardNo")){
				params.eq("UTCardNo", cardNo);
				//sql.append(" and UTCardNo='"+cardNo+"' ");
			}else if(cardType.equals("creditCardNo")){
				params.eq("CreditCardNo", cardNo);
			}else if(cardType.equals("oldUTCardNo")){
				params.eq("OldUTCardNo", cardNo);
			}else if(cardType.equals("oldCreditCardNo")){
				params.eq("OldCreditCardNo", cardNo);
			}*/
		}

		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects = list.toArray();
		sql.append(" order by c.id desc ");
		pager = this.findByPages(sql.toString(), pager, Objects);
		return pager;
	}

	public Map<String, Object> findServiceFlowDetailId(Long id) {
		String sql = "select ID,BusinessTime,BusinessType,UTCardNo,CreditCardNo,"
				+ "OldUTCardNo,OldCreditCardNo,Organ,UserType,IdType,IdCode,CusTel,CusMobile,CusAddr,"
				+ "CusZipCode,CusEmail,LinkMan,LinkTel,LinkMobile,LinkAddr,LinkZipCode,Remark,FileName,"
				+ "IsTransact,ImportTime,ImportPlace,ImportOper,ServiceFlowNO,dealTime,operId,operName,"
				+ "placeId,placeName,operNo,placeNo,systemType,servicePwd,bankCode,errorCode from CSMS_Card_business_info c where ServiceFlowNO is not null "
				+ " and id=?";
		List<Map<String, Object>> list = queryList(sql, id);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}


	/***
	 * 根据卡号、查找开户业务记录（remark为空，isTransact = 1 ，businessType 为06,08,16）
	 * @param cardNo 记账卡卡号
	 * @return
	 */
	public List<CardBusinessInfo> findBusinessRecordBycardNo(String cardNo) {
		String sql = "select ID,businessTime,businessType,"
				+ "UTCardNo,creditCardNo,oldUTCardNo,oldCreditCardNo,organ,userType,"
				+ "idType,idCode,cusTel,cusMobile,cusAddr,cusZipCode,cusEmail,linkMan,"
				+ "linkTel,linkMobile,linkAddr,linkZipCode,remark,fileName,isTransact,"
				+ "importTime,importPlace,importOper from CSMS_Card_business_info where isTransact = '1' and UTCardNo=?"
				+ " and businessType in ('06','08','16') order by businessTime desc ";

		return super.queryObjectList(sql, CardBusinessInfo.class, cardNo);
	}
}
