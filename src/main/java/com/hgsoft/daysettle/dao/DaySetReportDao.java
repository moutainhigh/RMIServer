package com.hgsoft.daysettle.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.CashDepositDaySet;
import com.hgsoft.daysettle.entity.CustomPoint;
import com.hgsoft.daysettle.entity.DaySetApprove;
import com.hgsoft.daysettle.entity.DaySetCorrectRecord;
import com.hgsoft.daysettle.entity.FeeReportRecord;
import com.hgsoft.daysettle.entity.FeeReportRecordVo;
import com.hgsoft.daysettle.entity.LongFeeCorrect;
import com.hgsoft.daysettle.entity.RemarkInfo;
import com.hgsoft.daysettle.entity.Role;
import com.hgsoft.daysettle.entity.SysWare;
import com.hgsoft.daysettle.entity.WareReportRecord;
import com.hgsoft.daysettle.entity.WareUntake;
import com.hgsoft.daysettle.entity.WareUntakeVo;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class DaySetReportDao extends BaseDao {

	@Resource
	SequenceUtil sequenceUtil;

	public Pager findFeeWareList(Pager pager, Long placeId, String settleDay, String state, Boolean isDirector) {
		StringBuilder innerBuilder = new StringBuilder(
				"SELECT CDA.ID,CDA.SETTLEDAY,CC.PLACENAME,CC.OPERNAME,CDA.STATE FROM CSMS_DAYSET_APPROVAL CDA JOIN( ");
		innerBuilder.append("SELECT PLACENAME,OPERNAME,DAYSETID FROM CSMS_FEEREPORT_RECORD WHERE 1=1 ");
		if (!isDirector) {
			innerBuilder.append(" AND PLACEID = " + placeId);
		}
		if (!StringUtil.isEmpty(settleDay)) {
			innerBuilder.append(" AND SETTLEDAY ='" + settleDay + "' ");
		}
		innerBuilder.append(" UNION SELECT PLACENAME,OPERNAME,DAYSETID FROM CSMS_WAREREPORT_RECORD WHERE 1=1 ");
		if (!isDirector) {
			innerBuilder.append(" AND PLACEID = " + placeId);
		}
		if (!StringUtil.isEmpty(settleDay)) {
			innerBuilder.append(" AND SETTLEDAY ='" + settleDay + "' ");
		}
		innerBuilder.append(") CC ON CDA.ID = CC.DAYSETID ");

		if (!StringUtil.isEmpty(state)) {
			innerBuilder.append(" AND CDA.STATE = '" + state + "'");
		}
		if (!StringUtil.isEmpty(settleDay)) {
			innerBuilder.append(" AND CDA.SETTLEDAY = '" + settleDay + "'");
		}
		if (isDirector) {
			CustomPoint customPoint = findCurrentPointById(placeId);
			innerBuilder.append(" AND CDA.SALESDEP = " + customPoint.getParent());
		}
		String sql = "SELECT distinct * FROM (" + innerBuilder.toString() + ") WHERE 1=1 ";
		sql += " ORDER BY SETTLEDAY DESC ";
		SqlParamer params = new SqlParamer();
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();
		return this.findByPages(sql, pager, Objects);
	}

	public void deleteFee(String id) {
		String sql = "delete from CSMS_FeeReport_Record where id = " + id;
		delete(sql);
	}

	public void deleteWare(String id) {
		String sql = "delete from CSMS_WareReport_Record where id = " + id;
		delete(sql); 
	}

	public void saveFee(FeeReportRecord fee) {
		// 元转化为分
		if(fee!=null) {
			if (fee.getCash() != null) {
				fee.setCash(BigDecimal.valueOf(fee.getCash()).multiply(new BigDecimal("100")).doubleValue());
			}
			if (fee.getPos() != null) {
				fee.setPos(BigDecimal.valueOf(fee.getPos()).multiply(new BigDecimal("100")).doubleValue());
			}
			if (fee.getTransferAccount() != null) {
				fee.setTransferAccount(
						BigDecimal.valueOf(fee.getTransferAccount()).multiply(new BigDecimal("100")).doubleValue());
			}
			if (fee.getWechat() != null) {
				fee.setWechat(BigDecimal.valueOf(fee.getWechat()).multiply(new BigDecimal("100")).doubleValue());
			}
			if (fee.getAlipay() != null) {
				fee.setAlipay(BigDecimal.valueOf(fee.getAlipay()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(fee.getBill()!=null) {
				fee.setBill(BigDecimal.valueOf(fee.getBill()).multiply(new BigDecimal("100")).doubleValue());
			}
		}
		
		StringBuffer sql = null;
		if (fee.getId() != null) {
			sql = new StringBuffer("update CSMS_FEEREPORT_RECORD set ");
			sql.append("PAYEE = " + fee.getPayee() + ",");
			sql.append("PAYEECODE = '" + fee.getPayeeCode() + "',");
			sql.append("PAYEENAME = '" + fee.getPayeeName() + "',");
			sql.append("CASH = " + fee.getCash() + ",");
			sql.append("POS = " + fee.getPos() + ",");
			sql.append("TRANSFERACCOUNT = " + fee.getTransferAccount() + ",");
			sql.append("WECHAT = " + fee.getWechat() + ",");
			sql.append("ALIPAY = " + fee.getAlipay() + ",");
			sql.append("OPERID = " + fee.getOperid() + ",");
			sql.append("OPERCODE = '" + fee.getOperCode() + "',");
			sql.append("OPERNAME = '" + fee.getOperName() + "',");
			sql.append("PLACEID = " + fee.getPlaceid() + ",");
			sql.append("PLACECODE = '" + fee.getPlaceCode() + "',");
			sql.append("PLACENAME = '" + fee.getPlaceName() + "'");
			sql.append(" WHERE ID = " + fee.getId());
			update(sql.toString());
		} else {
			fee.setId(sequenceUtil.getSequenceLong("SEQ_CSMSFEEREPORTRECORD_NO"));
			Map map = FieldUtil.getPreFieldMap(FeeReportRecord.class, fee);
			sql = new StringBuffer("insert into CSMS_FEEREPORT_RECORD");
			sql.append(map.get("insertNameStr"));
			saveOrUpdate(sql.toString(), (List) map.get("param"));
		}

	}

	public void saveWare(WareReportRecord ware) {

		StringBuffer sql = null;
		if (ware.getId() != null) {
			sql = new StringBuffer("UPDATE CSMS_WAREREPORT_RECORD SET ");
			sql.append("PRODUCTCODE = '" + ware.getProductCode() + "',");
			sql.append("PRODUCTNAME = '" + ware.getProductName() + "',");
			sql.append("WARECOUNT = " + ware.getWareCount() + ",");
			sql.append("OPERID = " + ware.getOperid() + ",");
			sql.append("OPERCODE = '" + ware.getOperCode() + "',");
			sql.append("OPERNAME = '" + ware.getOperName() + "',");
			sql.append("PLACEID = " + ware.getPlaceid() + ",");
			sql.append("PLACECODE = '" + ware.getPlaceCode() + "',");
			sql.append("PLACENAME = '" + ware.getPlaceName() + "',");
			sql.append("PRODUCTSTATE = '" + ware.getProductState() + "',");
			sql.append("PRODUCTSOURCE = '" + ware.getProductSource() + "'");
			sql.append(" WHERE ID = " + ware.getId());
			update(sql.toString());
		} else {
			ware.setId(sequenceUtil.getSequenceLong("SEQ_CSMSWAREREPORTRECORD_NO"));
			Map map = FieldUtil.getPreFieldMap(WareReportRecord.class, ware);
			sql = new StringBuffer("insert into CSMS_WAREREPORT_RECORD(");
			sql.append(
					"ID,DAYSETID,SETTLEDAY,PRODUCTCODE,PRODUCTNAME,WARECOUNT,OPERID,OPERCODE,OPERNAME,PLACEID,PLACECODE,PLACENAME,PRODUCTSTATE,PRODUCTSOURCE");
			sql.append(") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(ware.getId());
			paramList.add(ware.getDaySetID());
			paramList.add(ware.getSettleDay());
			paramList.add(ware.getProductCode());
			paramList.add(ware.getProductName());
			paramList.add(ware.getWareCount());
			paramList.add(ware.getOperid());
			paramList.add(ware.getOperCode());
			paramList.add(ware.getOperName());
			paramList.add(ware.getPlaceid());
			paramList.add(ware.getPlaceCode());
			paramList.add(ware.getPlaceName());
			paramList.add(ware.getProductState());
			paramList.add(ware.getProductSource());
			saveOrUpdate(sql.toString(), paramList);
		}

	}

	public DaySetApprove findByOperAndDay(DaySetApprove daySetApprove) {
		String sql = "select * from CSMS_DAYSET_APPROVAL where SALESDEP = " + daySetApprove.getSalesDep()
				+ " and SETTLEDAY = '" + daySetApprove.getSettleDay() + "'";
		List<Map<String, Object>> list = queryList(sql);
		if (!list.isEmpty()) {
			daySetApprove = new DaySetApprove();
			this.convert2Bean(list.get(0), daySetApprove);
			return daySetApprove;
		} else {
			return null;
		}
	}

	public void saveDaySetApprove(DaySetApprove daySetApprove) {
		String sql = "insert into CSMS_DAYSET_APPROVAL(ID,SETTLEDAY,SALESDEP,SALESDEPNAME,STATE) VALUES("
				+ daySetApprove.getId() + ",'" + daySetApprove.getSettleDay() + "'," + daySetApprove.getSalesDep()
				+ ",'" + daySetApprove.getSalesDepName() + "','" + daySetApprove.getState() + "')";
		save(sql);

	}

//	public List<FeeReportRecordVo> findFeeReportByApproveId(Long id) {
//		StringBuffer sql = new StringBuffer(
//				"SELECT REPORT.PLACEID AS ,REPORT.CASH,REPORT.POS,REPORT.TRANSFERACCOUNT,REPORT.WECHAT,REPORT.ALIPAY,REPORT.PLACECODE,REPORT.PLACENAME, ");
//		sql.append(
//				" SYS.CASH AS SYSCASH,SYS.POS AS SYSPOS,SYS.TRANSFERACCOUNT AS SYSTRANSFERACCOUNT,SYS.WECHAT AS SYSWECHAT,SYS.ALIPAY AS SYSALIPAY");
//		sql.append("  FROM ( ");
//		sql.append(
//				" SELECT SUM(CASH) AS CASH,SUM(POS) AS POS,SUM(TRANSFERACCOUNT) AS TRANSFERACCOUNT,SUM(WECHAT) AS WECHAT,");
//		sql.append(" SUM(ALIPAY) AS ALIPAY,PLACECODE,PLACEID,PLACENAME,SETTLEDAY FROM CSMS_FEEREPORT_RECORD");
//		sql.append(" where DAYSETID = " + id + " GROUP BY PLACEID,PLACECODE,PLACENAME,SETTLEDAY");
//		sql.append(" ) REPORT");
//		sql.append(
//				" LEFT JOIN CSMS_SYSFEE SYS ON REPORT.PLACEID = SYS.PLACEID AND TO_CHAR(SYS.ENDTIME,'YYYY-MM-DD') = REPORT.SETTLEDAY ORDER BY PLACEID ASC");
//		List<FeeReportRecordVo> feeList = null;
//		List<Map<String, Object>> list = queryList(sql.toString());
//		if (list.size() > 0) {
//			feeList = new ArrayList<FeeReportRecordVo>();
//			for (Map<String, Object> map : list) {
//				FeeReportRecordVo fee = new FeeReportRecordVo();
//				fee = (FeeReportRecordVo) this.convert2Bean((Map<String, Object>) map, new FeeReportRecordVo());
//
//				feeList.add(fee);
//
//			}
//
//		}
//		return feeList;
//
//	}

	public List<FeeReportRecordVo> findFeeReportByApproveId(DaySetApprove daySetApprove) {
		StringBuffer sql = new StringBuffer(
				"SELECT OCP.CODE AS PLACECODE,OCP.NAME AS PLACENAME,OCP.ID AS PLACEID,CFR.CASH,CFR.POS,CFR.TRANSFERACCOUNT,CFR.WECHAT,CFR.ALIPAY,CFR.BILL,CFR.DAYSETID FROM oms_custompoint OCP ");
		sql.append(" LEFT JOIN CSMS_FEEREPORT_RECORD CFR ON OCP.CODE = CFR.PLACECODE AND CFR.DAYSETID = "
				+ daySetApprove.getId() + " WHERE OCP.PARENT = " + daySetApprove.getSalesDep()
				+ " ORDER BY OCP.CODE ASC");
		List<FeeReportRecordVo> feeList = null;
		List<Map<String, Object>> list = queryList(sql.toString());
		if (list.size() > 0) {
			feeList = new ArrayList<FeeReportRecordVo>();
			for (Map<String, Object> map : list) {
				FeeReportRecordVo fee = new FeeReportRecordVo();
				fee = (FeeReportRecordVo) this.convert2Bean((Map<String, Object>) map, new FeeReportRecordVo());
				// 分转化为元
				if(fee!=null) {
					if (fee.getCash() != null) {
						fee.setCash(BigDecimal.valueOf(fee.getCash()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getPos() != null) {
						fee.setPos(BigDecimal.valueOf(fee.getPos()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getTransferAccount() != null) {
						fee.setTransferAccount(
								BigDecimal.valueOf(fee.getTransferAccount()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getWechat() != null) {
						fee.setWechat(BigDecimal.valueOf(fee.getWechat()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getAlipay() != null) {
						fee.setAlipay(BigDecimal.valueOf(fee.getAlipay()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getBill() != null) {
						fee.setBill(BigDecimal.valueOf(fee.getBill()).multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				feeList.add(fee);

			}

		}
		return feeList;

	}

	public List<FeeReportRecord> findFeeReportByApproveId(Long id, String payeeCode, Long placeId) {
		String sql = "SELECT * FROM CSMS_FEEREPORT_RECORD WHERE DAYSETID = " + id;
		if (payeeCode != null && !StringUtil.isEmpty(payeeCode)) {
			sql += " AND PAYEECODE = '" + payeeCode + "'";
		}
		if (placeId != null) {
			sql += " AND PLACEID = " + placeId;
		}

		List<FeeReportRecord> feeList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			feeList = new ArrayList<FeeReportRecord>();
			for (Map<String, Object> map : list) {
				FeeReportRecord fee = new FeeReportRecord();
				fee = (FeeReportRecord) this.convert2Bean((Map<String, Object>) map, new FeeReportRecord());
				// 分转化成元
				if (fee.getCash() != null) {
					fee.setCash(BigDecimal.valueOf(fee.getCash()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				if (fee.getPos() != null) {
					fee.setPos(BigDecimal.valueOf(fee.getPos()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				if (fee.getTransferAccount() != null) {
					fee.setTransferAccount(BigDecimal.valueOf(fee.getTransferAccount()).multiply(new BigDecimal("0.01"))
							.doubleValue());
				}
				if (fee.getWechat() != null) {
					fee.setWechat(BigDecimal.valueOf(fee.getWechat()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				if (fee.getAlipay() != null) {
					fee.setAlipay(BigDecimal.valueOf(fee.getAlipay()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				if (fee.getBill() != null) {
					fee.setBill(BigDecimal.valueOf(fee.getBill()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				feeList.add(fee);

			}

		}
		return feeList;
	}

	public List<WareReportRecord> findWareReportByApproveId(Long id) {
		String sql = "SELECT * FROM CSMS_WAREREPORT_RECORD WHERE DAYSETID = " + id + " ORDER BY PLACECODE ASC";
		List<WareReportRecord> wareList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			wareList = new ArrayList<WareReportRecord>();
			for (Map<String, Object> map : list) {
				WareReportRecord fee = new WareReportRecord();
				fee = (WareReportRecord) this.convert2Bean((Map<String, Object>) map, new WareReportRecord());
				wareList.add(fee);

			}

		}
		return wareList;

	}

	public List<WareReportRecord> findWareReportByApproveId(Long id, String productCode, String productState,
			String productSource, Long placeId) {
		String sql = "SELECT * FROM CSMS_WAREREPORT_RECORD WHERE DAYSETID = " + id;
		if (!StringUtil.isEmpty(productCode)) {
			sql += " AND PRODUCTCODE = '" + productCode + "'";
		}
		if (!StringUtil.isEmpty(productState)) {
			sql += " AND PRODUCTSTATE = '" + productState + "'";
		}
		if (!StringUtil.isEmpty(productSource)) {
			sql += " AND PRODUCTSOURCE = '" + productSource + "'";
		}
		if (placeId != null) {
			sql += " AND PLACEID = " + placeId;
		}

		List<WareReportRecord> wareList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			wareList = new ArrayList<WareReportRecord>();
			for (Map<String, Object> map : list) {
				WareReportRecord fee = new WareReportRecord();
				fee = (WareReportRecord) this.convert2Bean((Map<String, Object>) map, new WareReportRecord());

				wareList.add(fee);
			}

		}
		return wareList;

	}

	public DaySetApprove findDaySetApproveById(Long id) {
		String sql = "select * from CSMS_DAYSET_APPROVAL where id = " + id;
		List<Map<String, Object>> list = queryList(sql);
		DaySetApprove daySetApprove = null;
		if (!list.isEmpty()) {
			daySetApprove = new DaySetApprove();
			this.convert2Bean(list.get(0), daySetApprove);
		}

		return daySetApprove;
	}

	public DaySetApprove findDaySetApproveByDate(String date, Long stockPlace) {
		String sql = "select * from CSMS_DAYSET_APPROVAL where SETTLEDAY = '" + date + "'AND SALESDEP = " + stockPlace;
		List<Map<String, Object>> list = queryList(sql);
		DaySetApprove daySetApprove = null;
		if (!list.isEmpty()) {
			daySetApprove = new DaySetApprove();
			this.convert2Bean(list.get(0), daySetApprove);
		}

		return daySetApprove;
	}

	public CashDepositDaySet findCashDeposiDaySet(Long id) {
		String sql = "select * from CSMS_CashDepositDaySet where ID = " + id;
		List<Map<String, Object>> list = queryList(sql);
		CashDepositDaySet cashDepositDaySet = null;
		if (!list.isEmpty()) {
			cashDepositDaySet = new CashDepositDaySet();
			this.convert2Bean(list.get(0), cashDepositDaySet);
		}
		if(cashDepositDaySet!=null) {
			// 分转化为元
			if (cashDepositDaySet.getPublicAcc() != null) {
				cashDepositDaySet.setPublicAcc(BigDecimal.valueOf(cashDepositDaySet.getPublicAcc())
						.multiply(new BigDecimal("0.01")).doubleValue());
			}
			if (cashDepositDaySet.getWithholdAcc() != null) {
				cashDepositDaySet.setWithholdAcc(BigDecimal.valueOf(cashDepositDaySet.getWithholdAcc())
						.multiply(new BigDecimal("0.01")).doubleValue());
			}
			if (cashDepositDaySet.getOutstandingAmount() != null) {
				cashDepositDaySet.setOutstandingAmount(BigDecimal.valueOf(cashDepositDaySet.getOutstandingAmount())
						.multiply(new BigDecimal("0.01")).doubleValue());
			}
		}
		
		return cashDepositDaySet;
	}

	public List<CashDepositDaySet> findCashDepositDayByApprove(Long approveId, Long placeId) {
		String sql = "SELECT * FROM CSMS_CashDepositDaySet WHERE DAYSETID = " + approveId;
		// 如果有传入网点id，则表示网点的查询，否则表示营业厅的查询
		if (placeId != null) {
			sql += " and placeid = " + placeId;
		}
		sql += " ORDER BY PLACECODE ASC";
		List<CashDepositDaySet> cashList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			cashList = new ArrayList<CashDepositDaySet>();
			for (Map<String, Object> map : list) {
				CashDepositDaySet cash = new CashDepositDaySet();
				cash = (CashDepositDaySet) this.convert2Bean((Map<String, Object>) map, new CashDepositDaySet());
				if(cash!=null) {
					// 分转化为元
					if (cash.getPublicAcc() != null) {
						cash.setPublicAcc(BigDecimal.valueOf(cash.getPublicAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getWithholdAcc() != null) {
						cash.setWithholdAcc(BigDecimal.valueOf(cash.getWithholdAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getOutstandingAmount() != null) {
						cash.setOutstandingAmount(BigDecimal.valueOf(cash.getOutstandingAmount())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					// 分转化为元
					if (cash.getPublicAcc() != null) {
						cash.setPublicAcc(BigDecimal.valueOf(cash.getPublicAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getWithholdAcc() != null) {
						cash.setWithholdAcc(BigDecimal.valueOf(cash.getWithholdAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getOutstandingAmount() != null) {
						cash.setOutstandingAmount(BigDecimal.valueOf(cash.getOutstandingAmount())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				
				cashList.add(cash);

			}

		}
		
		return cashList;
	}

	public List<CashDepositDaySet> findCashDepositDayByDate(String settleDay) {
		String sql = "SELECT * FROM CSMS_CashDepositDaySet WHERE SETTLEDAY = '" + settleDay + "'  ORDER BY PLACEID ASC";
		List<CashDepositDaySet> cashList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			cashList = new ArrayList<CashDepositDaySet>();
			for (Map<String, Object> map : list) {
				CashDepositDaySet cash = new CashDepositDaySet();
				cash = (CashDepositDaySet) this.convert2Bean((Map<String, Object>) map, new CashDepositDaySet());
				if(cash!=null) {
					// 分转化为元
					if (cash.getPublicAcc() != null) {
						cash.setPublicAcc(BigDecimal.valueOf(cash.getPublicAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getWithholdAcc() != null) {
						cash.setWithholdAcc(BigDecimal.valueOf(cash.getWithholdAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getOutstandingAmount() != null) {
						cash.setOutstandingAmount(BigDecimal.valueOf(cash.getOutstandingAmount())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				cashList.add(cash);

			}

		}
		return cashList;
	}

	public List<CashDepositDaySet> findCashDepositDayByDate(DaySetApprove daySetApprove) {
		StringBuffer sql = new StringBuffer(
				"SELECT OCP.CODE AS PLACECODE,OCP.NAME AS PLACENAME,OCP.ID AS PLACEID,CDD.DAYSETID,CDD.SETTLEDAY,CDD.PUBLICACC, ");
		sql.append(
				"CDD.WITHHOLDACC,CDD.OUTSTANDINGAMOUNT FROM oms_custompoint OCP LEFT JOIN CSMS_CashDepositDaySet CDD ");
		sql.append("ON OCP.CODE = CDD.PLACECODE AND CDD.DAYSETID = " + daySetApprove.getId() + " WHERE OCP.PARENT = "
				+ daySetApprove.getSalesDep() + " ORDER BY OCP.CODE ASC");
		List<CashDepositDaySet> cashList = null;
		List<Map<String, Object>> list = queryList(sql.toString());
		if (list.size() > 0) {
			cashList = new ArrayList<CashDepositDaySet>();
			for (Map<String, Object> map : list) {
				CashDepositDaySet cash = new CashDepositDaySet();
				cash = (CashDepositDaySet) this.convert2Bean((Map<String, Object>) map, new CashDepositDaySet());
				if(cash!=null) {
					// 分转化为元
					if (cash.getPublicAcc() != null) {
						cash.setPublicAcc(BigDecimal.valueOf(cash.getPublicAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getWithholdAcc() != null) {
						cash.setWithholdAcc(BigDecimal.valueOf(cash.getWithholdAcc())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (cash.getOutstandingAmount() != null) {
						cash.setOutstandingAmount(BigDecimal.valueOf(cash.getOutstandingAmount())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				
				cashList.add(cash);

			}

		}
		return cashList;
	}

	public void insertCashDepositDay(CashDepositDaySet cashDepositDaySet) {
		if(cashDepositDaySet!=null) {
			// 元转化为分
			if (cashDepositDaySet.getPublicAcc() != null) {
				cashDepositDaySet.setPublicAcc(BigDecimal.valueOf(cashDepositDaySet.getPublicAcc())
						.multiply(new BigDecimal("100")).doubleValue());
			}
			if (cashDepositDaySet.getWithholdAcc() != null) {
				cashDepositDaySet.setWithholdAcc(BigDecimal.valueOf(cashDepositDaySet.getWithholdAcc())
						.multiply(new BigDecimal("100")).doubleValue());
			}
			if (cashDepositDaySet.getOutstandingAmount() != null) {
				cashDepositDaySet.setOutstandingAmount(BigDecimal.valueOf(cashDepositDaySet.getOutstandingAmount())
						.multiply(new BigDecimal("100")).doubleValue());
			}
		}
		
		cashDepositDaySet.setId(sequenceUtil.getSequenceLong("SEQ_CSMSCASHDEPOSITDAYSET_NO"));
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_CashDepositDaySet(id,daySetID,settleDay,publicAcc,withholdAcc,");
		sql.append("outstandingAmount,operid,operCode,operName,placeid,placeCode,placeName) values");
		sql.append("(" + cashDepositDaySet.getId() + ",");
		sql.append(cashDepositDaySet.getDaySetID() + ",");
		sql.append("'" + cashDepositDaySet.getSettleDay() + "',");
		sql.append(cashDepositDaySet.getPublicAcc() + ",");
		sql.append(cashDepositDaySet.getWithholdAcc() + ",");
		sql.append(cashDepositDaySet.getOutstandingAmount() + ",");
		sql.append(cashDepositDaySet.getOperid() + ",");
		sql.append("'" + cashDepositDaySet.getOperCode() + "',");
		sql.append("'" + cashDepositDaySet.getOperName() + "',");
		sql.append(cashDepositDaySet.getPlaceid() + ",");
		sql.append("'" + cashDepositDaySet.getPlaceCode() + "',");
		sql.append("'" + cashDepositDaySet.getPlaceName() + "')");
		save(sql.toString());

	}

	public void updateCashDepositDay(CashDepositDaySet cashDepositDaySet) {
		StringBuffer sql = null;
		if(cashDepositDaySet!=null) {
			// 元转化成分
			if (cashDepositDaySet.getPublicAcc() != null) {
				cashDepositDaySet.setPublicAcc(BigDecimal.valueOf(cashDepositDaySet.getPublicAcc())
						.multiply(new BigDecimal("100")).doubleValue());
			}
			if (cashDepositDaySet.getWithholdAcc() != null) {
				cashDepositDaySet.setWithholdAcc(BigDecimal.valueOf(cashDepositDaySet.getWithholdAcc())
						.multiply(new BigDecimal("100")).doubleValue());
			}
			if (cashDepositDaySet.getOutstandingAmount() != null) {
				cashDepositDaySet.setOutstandingAmount(BigDecimal.valueOf(cashDepositDaySet.getOutstandingAmount())
						.multiply(new BigDecimal("100")).doubleValue());
			}
		}
		
		sql = new StringBuffer("UPDATE CSMS_CashDepositDaySet SET ");
		sql.append("PUBLICACC = ?,");
		sql.append("WITHHOLDACC = ?,");
		sql.append("OUTSTANDINGAMOUNT = ?,");
		sql.append("OPERID = ?,");
		sql.append("OPERCODE = ?,");
		sql.append("OPERNAME = ?,");
		sql.append("PLACEID = ?,");
		sql.append("PLACECODE = ?,");
		sql.append("PLACENAME = ?");
		sql.append(" WHERE ID = ?");
		update(sql.toString(), cashDepositDaySet.getPublicAcc(), cashDepositDaySet.getWithholdAcc(),
				cashDepositDaySet.getOutstandingAmount(), cashDepositDaySet.getOperid(),
				cashDepositDaySet.getOperCode(), cashDepositDaySet.getOperName(), cashDepositDaySet.getPlaceid(),
				cashDepositDaySet.getPlaceCode(), cashDepositDaySet.getPlaceName(), cashDepositDaySet.getId());
	}

	public Pager findCashDepositList(Pager pager, Long operPlaceId, String date, String state) {
		String sql = "SELECT CC.ID,CDA.SETTLEDAY,CC.PLACENAME,CC.OPERNAME,CDA.STATE,CC.PUBLICACC/100 AS PUBLICACC,CC.WITHHOLDACC/100 AS WITHHOLDACC,CC.OUTSTANDINGAMOUNT/100 AS OUTSTANDINGAMOUNT FROM CSMS_CASHDEPOSITDAYSET cc JOIN CSMS_DAYSET_APPROVAL CDA "
				+ "ON CC.DAYSETID = CDA.id WHERE CC.PLACEID = " + operPlaceId;
		if (!StringUtil.isEmpty(state)) {
			sql += " AND CDA.STATE = '" + state + "'";
		}
		if (!StringUtil.isEmpty(date)) {
			sql += " AND CDA.SETTLEDAY = '" + date + "'";
		}

		sql += " ORDER BY CC.SETTLEDAY DESC ";
		SqlParamer params = new SqlParamer();
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();
		return this.findByPages(sql, pager, Objects);
	}

	public Pager findDaySetCorrectList(Pager pager, Long operPlaceId, String date, String state) {
		String sql = "SELECT CDA.ID,CDA.SETTLEDAY,CC.PLACENAME,CC.OPERNAME,CDA.STATE FROM CSMS_CASHDEPOSITDAYSET CC JOIN CSMS_DAYSET_APPROVAL CDA ON CC.DAYSETID = CDA.ID WHERE CC.PLACEID = "
				+ operPlaceId;
		if (!StringUtil.isEmpty(state)) {
			sql += " AND CDA.STATE = '" + state + " '";
		}
		if (!StringUtil.isEmpty(date)) {
			sql += " AND CDA.SETTLEDAY = '" + date + "'";
		}

		sql += " ORDER BY CC.SETTLEDAY DESC ";
		SqlParamer params = new SqlParamer();
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();
		return this.findByPages(sql, pager, Objects);
	}

	public List<DaySetCorrectRecord> findDaySetCorrectRecordList(Long approveId, Long placeId) {
		String sql = "SELECT * FROM CSMS_DAYSETCORRECT_RECORD WHERE DAYSETID =" + approveId;
		// 如果传入的网点id为空，则表示查询的是整个营业部的记录
		if (placeId != null) {
			sql += " AND PLACEID = " + placeId ;
		}
		sql+=" ORDER BY PLACECODE ASC";
		List<DaySetCorrectRecord> correctList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			correctList = new ArrayList<DaySetCorrectRecord>();
			for (Map<String, Object> map : list) {
				DaySetCorrectRecord correct = new DaySetCorrectRecord();
				correct = (DaySetCorrectRecord) this.convert2Bean((Map<String, Object>) map, new DaySetCorrectRecord());
				//分转化为元
				if (correct.getCorrectFee() != null) {
					correct.setCorrectFee(BigDecimal.valueOf(correct.getCorrectFee())
							.multiply(new BigDecimal("0.01")).doubleValue());
				}
				correctList.add(correct);

			}

		}
		return correctList;
	}

	public DaySetCorrectRecord findDaySetCorrectRecordByDate(String date) {
		String sql = "SELECT * FROM CSMS_DAYSETCORRECT_RECORD WHERE SETTLEDAY ='" + date + "'";
		List<Map<String, Object>> list = queryList(sql);
		DaySetCorrectRecord daySetCorrectRecord = null;
		if (!list.isEmpty()) {
			daySetCorrectRecord = new DaySetCorrectRecord();
			this.convert2Bean(list.get(0), daySetCorrectRecord);
		}
		//分转化成元
		if(daySetCorrectRecord!=null) {
			if (daySetCorrectRecord.getCorrectFee() != null) {
				daySetCorrectRecord.setCorrectFee(BigDecimal.valueOf(daySetCorrectRecord.getCorrectFee())
						.multiply(new BigDecimal("0.01")).doubleValue());
			}
		}
		
		return daySetCorrectRecord;
	}

	public void insertDaySetCorrectRecord(DaySetCorrectRecord daySetCorrectRecord) {
		StringBuffer sql = null;
		//元转化为分
		if(daySetCorrectRecord!=null) {
			if (daySetCorrectRecord.getCorrectFee() != null) {
				daySetCorrectRecord.setCorrectFee(BigDecimal.valueOf(daySetCorrectRecord.getCorrectFee())
						.multiply(new BigDecimal("100")).doubleValue());
			}
		}
		
		daySetCorrectRecord.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDAYSETCORRECTRECORD_NO"));
		Map map = FieldUtil.getPreFieldMap(DaySetCorrectRecord.class, daySetCorrectRecord);
		sql = new StringBuffer("insert into CSMS_DAYSETCORRECT_RECORD");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public void updateDaySetCorrectRecord(DaySetCorrectRecord daySetCorrectRecord) {
		StringBuffer sql = null;
		//元转化为分
		if(daySetCorrectRecord!=null) {
			if (daySetCorrectRecord.getCorrectFee() != null) {
				daySetCorrectRecord.setCorrectFee(BigDecimal.valueOf(daySetCorrectRecord.getCorrectFee())
						.multiply(new BigDecimal("100")).doubleValue());
			}
		}
		sql = new StringBuffer("UPDATE CSMS_DAYSETCORRECT_RECORD SET ");
		sql.append("PAYEE = '" + daySetCorrectRecord.getPayee() + "',");
		sql.append("ACTUALPAYEE='" + daySetCorrectRecord.getActualPayee() + "',");
		sql.append("CORRECTFEE=" + daySetCorrectRecord.getCorrectFee() + ",");
		sql.append("CORRECTTYPE='" + daySetCorrectRecord.getCorrectType() + "'");
		sql.append(" WHERE ID = " + daySetCorrectRecord.getId());
		update(sql.toString());
	}

	public Pager findApproveList(Pager pager, Long stockId, String state, String date) {
		String sql = "SELECT * FROM CSMS_DAYSET_APPROVAL WHERE SALESDEP = " + stockId;
		if (!StringUtil.isEmpty(state)) {
			sql += " AND STATE = '" + state + "'";
		}
		if (!StringUtil.isEmpty(date)) {
			sql += " AND SETTLEDAY = '" + date + "'";
		}

		sql += " ORDER BY SETTLEDAY DESC ";
		SqlParamer params = new SqlParamer();
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();
		return this.findByPages(sql, pager, Objects);
	}

	public List<Map<String, Object>> findSysFee(List<FeeReportRecord> feeList, DaySetApprove daySetApprove)
			throws ParseException {
		String placeCodeStr = "";
		for (FeeReportRecord feeReportRecord : feeList) {
			placeCodeStr += "'" + feeReportRecord.getPlaceCode() + "',";
		}
		placeCodeStr = placeCodeStr.substring(0, placeCodeStr.length() - 1);
		String startTime = DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd") + "000000";
		String endTime = DateUtil.formatDate(DateUtil.toDate(daySetApprove.getSettleDay()), "yyyyMMdd") + "235959";

		// 查询缴款 and TransactionType = '1' and iscorrect is null
		StringBuilder reChargeInfosql = new StringBuilder(
				"select sum(TakeBalance)/100 as TakeBalance,PayMentType,PLACENO from csms_rechargeinfo");
		reChargeInfosql.append(" where to_char(OperTime,'YYYYMMDDHH24MISS')>='" + startTime
				+ "' and to_char(OperTime,'YYYYMMDDHH24MISS')<='" + endTime + "' and");
		reChargeInfosql.append(" placeno in( " + placeCodeStr + ") group by PLACENO,PayMentType ");

		// 电子标签提货缴款
		StringBuilder tagTakefeeInfosql = new StringBuilder(
				"select sum(ChargeFee)/100 as TakeBalance,ChargeType as PayMentType,PLACENO from csms_tagtakefee_info");
		tagTakefeeInfosql.append(" where  to_char(RegisterDate,'YYYYMMDDHH24MISS')>='" + startTime
				+ "'  and to_char(RegisterDate,'YYYYMMDDHH24MISS')<='" + endTime + "'");
		tagTakefeeInfosql.append(" and placeno in(  " + placeCodeStr + " ) group by PLACENO,ChargeType");

		StringBuilder sql = new StringBuilder("select sum(TakeBalance) as TakeBalance,PayMentType,PLACENO from(");
		sql.append(reChargeInfosql.toString() + " union " + tagTakefeeInfosql.toString());
		sql.append(") GROUP BY PayMentType,PLACENO");

		return queryList(sql.toString());
	}

	public List<Map<String, Object>> findPlaceSysFee(String placeNo, String startTime, String endTime)
			throws ParseException {

		// 查询缴款 and TransactionType = '1' and iscorrect is null
		StringBuilder reChargeInfosql = new StringBuilder(
				"select sum(TakeBalance)/100 as TakeBalance,PayMentType,PLACENO from csms_rechargeinfo");
		reChargeInfosql.append(" where to_char(OperTime,'YYYYMMDDHH24MISS')>='" + startTime
				+ "' and to_char(OperTime,'YYYYMMDDHH24MISS')<='" + endTime + "' and");
		reChargeInfosql.append(" placeno ='" + placeNo + "' group by PLACENO,PayMentType ");

		// 电子标签提货缴款
		StringBuilder tagTakefeeInfosql = new StringBuilder(
				"select sum(ChargeFee)/100 as TakeBalance,case ChargeType when '4' then '6' when '5' then '8' when '6' then '7' else chargetype end as PayMentType,PLACENO from csms_tagtakefee_info");
		tagTakefeeInfosql.append(" where  to_char(RegisterDate,'YYYYMMDDHH24MISS')>='" + startTime
				+ "'  and to_char(RegisterDate,'YYYYMMDDHH24MISS')<='" + endTime + "'");
		tagTakefeeInfosql.append(" and placeno ='" + placeNo + "' group by PLACENO,ChargeType");

		StringBuilder sql = new StringBuilder("select sum(TakeBalance) as TakeBalance,PayMentType,PLACENO from(");
		sql.append(reChargeInfosql.toString() + " union all " + tagTakefeeInfosql.toString());
		sql.append(") GROUP BY PayMentType,PLACENO");

		return queryList(sql.toString());
	}

	public void deleteCorrect(String correctId) {
		String sql = "delete from CSMS_DAYSETCORRECT_RECORD where id = " + correctId;
		delete(sql);
	}

	public void updateDaySetApprove(DaySetApprove daySetApprove) {
		StringBuilder sql = new StringBuilder("UPDATE CSMS_DAYSET_APPROVAL SET" + " APPROVER = "
				+ daySetApprove.getApprover() + "," + "APPROVERNO='" + daySetApprove.getApproverNo() + "',"
				+ "APPROVERNAME='" + daySetApprove.getApproverName() + "'," + "APPTIME=to_date('"
				+ DateUtil.formatDate(daySetApprove.getAppTime(), "yyyy-MM-dd HH:mm:ss") + "','yyyy-mm-dd hh24:mi:ss'),"
				+ "APPOPINION='" + daySetApprove.getAppOpinion() + "'," + "SYSSTARTTIME=to_date('"
				+ DateUtil.formatDate(daySetApprove.getSysStartTime(), "yyyy-MM-dd HH:mm:ss")
				+ "','yyyy-mm-dd hh24:mi:ss')," + "SYSENDTIME=to_date('"
				+ DateUtil.formatDate(daySetApprove.getSysEndTime(), "yyyy-MM-dd HH:mm:ss")
				+ "','yyyy-mm-dd hh24:mi:ss')," + "STOCKPLACE='" + daySetApprove.getStockPlace() + "'," + "STATE='"
				+ daySetApprove.getState() + "'" + " WHERE ID = " + daySetApprove.getId());
		update(sql.toString());

	}

	public Pager findCorrectList(Pager pager, Long operPlaceId, String state, String date) {
		StringBuffer sql = new StringBuffer("SELECT DISTINCT * FROM CSMS_DAYSET_APPROVAL CDR ");
		sql.append(" WHERE CDR.SALESDEP = " + operPlaceId);
		if (!StringUtil.isEmpty(state)) {
			sql.append(" AND CDR.STATE='" + state + "'");
		}
		if (!StringUtil.isEmpty(date)) {
			sql.append(" AND CDR.SETTLEDAY='" + date + "'");
		}
		return this.findByPages(sql.toString(), pager, null);
	}

	public void saveMemo(RemarkInfo remarkInfo) {
		StringBuffer sql = null;
		remarkInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSREMARKINFO_NO"));
		Map map = FieldUtil.getPreFieldMap(RemarkInfo.class, remarkInfo);
		sql = new StringBuffer("insert into CSMS_REMARK_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public List<RemarkInfo> findMemoList(RemarkInfo remarkInfo) {
		String sql = "select * from CSMS_REMARK_INFO where TABLENAME = '" + remarkInfo.getTableName()
				+ "' and BUSINESSID = " + remarkInfo.getBusinessId();
		List<RemarkInfo> memoList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			memoList = new ArrayList<RemarkInfo>();
			for (Map<String, Object> map : list) {
				RemarkInfo memo = new RemarkInfo();
				memo = (RemarkInfo) this.convert2Bean((Map<String, Object>) map, new RemarkInfo());
				memoList.add(memo);

			}

		}
		return memoList;
	}

	public Date findSettleDay(Long operPlaceId) {
		StringBuilder sql = new StringBuilder("SELECT max(SETTLEDAY) SETTLEDAY FROM (");
		sql.append(
				" select max(to_date(SETTLEDAY,'yyyy-mm-dd'))+1 as SETTLEDAY from CSMS_WAREREPORT_RECORD where PLACEID = '"
						+ operPlaceId + "'");
		sql.append(" UNION ");
		sql.append(
				"select max(to_date(SETTLEDAY,'yyyy-mm-dd'))+1 as SETTLEDAY from CSMS_FEEREPORT_RECORD where PLACEID = '"
						+ operPlaceId + "'");
		sql.append(")");
		List<Map<String, Object>> list = queryList(sql.toString());
		if (!list.isEmpty()) {
			return (Date) list.get(0).get("SETTLEDAY");
		}

		return null;
	}

//	public SysFee findSysFee(Long placeId, Long daySetId) {
//		String sql = "SELECT * FROM CSMS_SYSFEE WHERE PLACEID =" + placeId + " AND DAYSETID = " + daySetId;
//		List<Map<String, Object>> list = queryList(sql);
//		SysFee sysFee = null;
//		if (!list.isEmpty()) {
//			sysFee = new SysFee();
//			this.convert2Bean(list.get(0), sysFee);
//		}
//		return sysFee;
//	}

//	public SysFee findLastSysFee(Long placeId, String preDay) {
//		// String sql = "select * from CSMS_SYSFEE where PLACEID = "+placeId+" AND
//		// to_char(STARTTIME,'YYYY-MM-DD')='"+preDay+"'";
//		String sql = "SELECT * FROM (SELECT * FROM CSMS_SYSFEE WHERE SETTLEDAY<'" + preDay + "' AND PLACEID =" + placeId
//				+ "  ORDER BY SETTLEDAY DESC) WHERE ROWNUM = 1";
//		List<Map<String, Object>> list = queryList(sql);
//		SysFee sysFeeDaySetReport = null;
//		if (!list.isEmpty()) {
//			sysFeeDaySetReport = new SysFee();
//			this.convert2Bean(list.get(0), sysFeeDaySetReport);
//		}
//
//		return sysFeeDaySetReport;
//	}

//	public void saveSysFee(SysFee sysFeeDaySetReport) {
//		StringBuffer sql = null;
//		sysFeeDaySetReport.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSYSFEE_NO"));
//		Map map = FieldUtil.getPreFieldMap(SysFee.class, sysFeeDaySetReport);
//		sql = new StringBuffer("insert into CSMS_SYSFEE");
//		sql.append(map.get("insertNameStr"));
//		saveOrUpdate(sql.toString(), (List) map.get("param"));
//
//	}

	public List<FeeReportRecord> findFeeByDate(Long cusPoint, String settleDay) {
		String sql = "select * from CSMS_FEEREPORT_RECORD where PLACEID = " + cusPoint + " and SETTLEDAY = '"
				+ settleDay + "' ORDER BY PLACEID ASC";
		List<FeeReportRecord> feeReportList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			feeReportList = new ArrayList<FeeReportRecord>();
			for (Map<String, Object> map : list) {
				FeeReportRecord fee = new FeeReportRecord();
				fee = (FeeReportRecord) this.convert2Bean((Map<String, Object>) map, new FeeReportRecord());
				//分转化成元
				if(fee!=null) {
					if (fee.getCash() != null) {
						fee.setCash(BigDecimal.valueOf(fee.getCash()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getPos() != null) {
						fee.setPos(BigDecimal.valueOf(fee.getPos()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getTransferAccount() != null) {
						fee.setTransferAccount(
								BigDecimal.valueOf(fee.getTransferAccount()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getWechat() != null) {
						fee.setWechat(BigDecimal.valueOf(fee.getWechat()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getAlipay() != null) {
						fee.setAlipay(BigDecimal.valueOf(fee.getAlipay()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (fee.getBill() != null) {
						fee.setBill(BigDecimal.valueOf(fee.getBill()).multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				feeReportList.add(fee);

			}

		}
		return feeReportList;
	}

	public List<WareReportRecord> findWareByDate(Long cusPoint, String settleDay) {
		String sql = "select * from CSMS_WAREREPORT_RECORD where PLACEID = " + cusPoint + " and SETTLEDAY = '"
				+ settleDay + "' ORDER BY PLACEID ASC";
		List<WareReportRecord> wareReportList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			wareReportList = new ArrayList<WareReportRecord>();
			for (Map<String, Object> map : list) {
				WareReportRecord ware = new WareReportRecord();
				ware = (WareReportRecord) this.convert2Bean((Map<String, Object>) map, new WareReportRecord());

				wareReportList.add(ware);

			}

		}
		return wareReportList;
	}

	public Date findCashDepositSettleDay(Long operPlaceId) {
		String sql = "select max(to_date(SETTLEDAY,'yyyy-mm-dd'))+1 as SETTLEDAY from CSMS_CashDepositDaySet where PLACEID = "
				+ operPlaceId;
		List<Map<String, Object>> list = queryList(sql.toString());
		try {
			if (!list.isEmpty()) {
				return (Date) list.get(0).get("SETTLEDAY");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<RemarkInfo> findFeeMemoList(FeeReportRecord feeReportRecord) {
		String sql = "select cri.* from CSMS_REMARK_INFO cri join CSMS_FEEREPORT_RECORD cfr ON cri.businessid = cfr.id where cfr.daysetid = "
				+ feeReportRecord.getDaySetID() + " and cfr.placeid=" + feeReportRecord.getPlaceid();

		List<RemarkInfo> memoList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			memoList = new ArrayList<RemarkInfo>();
			for (Map<String, Object> map : list) {
				RemarkInfo memo = new RemarkInfo();
				memo = (RemarkInfo) this.convert2Bean((Map<String, Object>) map, new RemarkInfo());
				memoList.add(memo);
			}

		}
		return memoList;
	}

	public List<SysWare> findSysWare(Long id) {
		String sql = "SELECT * FROM CSMS_SYSWARE WHERE SETTLEID = " + id;
		List<SysWare> wareList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			wareList = new ArrayList<SysWare>();
			for (Map<String, Object> map : list) {
				SysWare sysWare = new SysWare();
				sysWare = (SysWare) this.convert2Bean((Map<String, Object>) map, new SysWare());
				wareList.add(sysWare);

			}

		}
		return wareList;
	}

//	public void saveSysWare(SysWare sysWare) {
//		StringBuffer sql = null;
//		sysWare.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSYSWARE_NO"));
//		Map map = FieldUtil.getPreFieldMap(SysWare.class, sysWare);
//		sql = new StringBuffer("insert into CSMS_SYSWARE");
//		sql.append(map.get("insertNameStr"));
//		saveOrUpdate(sql.toString(), (List) map.get("param"));
//
//	}

	public Boolean findPreFeeApprove(Long placeId, String settleDay) {
		String sql = "select COUNT(1) AS NUM from CSMS_FEEREPORT_RECORD CFR join CSMS_DAYSET_APPROVAL CDA ON CFR.DAYSETID = CDA.ID WHERE CDA.STATE = '0' AND CDA.SETTLEDAY = '"
				+ settleDay + "' AND CFR.PLACEID =" + placeId;
		List<Map<String, Object>> list = queryList(sql.toString());
		try {
			if (!list.isEmpty()) {
				Integer num = Integer.parseInt(list.get(0).get("NUM").toString());
				return num > 0 ? true : false;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean findPreWareApprove(Long placeId, String settleDay) {
		String sql = "select COUNT(1) as NUM from CSMS_WAREREPORT_RECORD CWR JOIN CSMS_DAYSET_APPROVAL CDA ON CWR.DAYSETID = CDA.ID WHERE CDA.STATE = '0' AND CDA.SETTLEDAY = '"
				+ settleDay + "' AND CWR.PLACEID =" + placeId;
		List<Map<String, Object>> list = queryList(sql.toString());
		try {
			if (!list.isEmpty()) {
				Integer num = Integer.parseInt(list.get(0).get("NUM").toString());
				return num > 0 ? true : false;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DaySetApprove findLastApprove(Long salesDep, String settleDay) {
		String sql = "SELECT * FROM (SELECT * FROM CSMS_DAYSET_APPROVAL WHERE SETTLEDAY < '" + settleDay
				+ "' AND SALESDEP = " + salesDep + " ORDER BY SETTLEDAY DESC) WHERE ROWNUM = 1";
		List<Map<String, Object>> list = queryList(sql);
		DaySetApprove daySetApprove = null;
		if (!list.isEmpty()) {
			daySetApprove = new DaySetApprove();
			this.convert2Bean(list.get(0), daySetApprove);
		}

		return daySetApprove;
	}

	public List<CustomPoint> findCustomPointList(Long salesDep) {
		String sql = "select * from oms_custompoint where parent=" + salesDep;
		List<CustomPoint> customPointList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			customPointList = new ArrayList<CustomPoint>();
			for (Map<String, Object> map : list) {
				CustomPoint sysWare = new CustomPoint();
				sysWare = (CustomPoint) this.convert2Bean((Map<String, Object>) map, new CustomPoint());
				customPointList.add(sysWare);

			}

		}
		return customPointList;
	}

	public Pager findUntakeWareList(Pager pager, Long stockId, String date, Long depId, String state) {
		StringBuffer sql = new StringBuffer("SELECT DISTINCT * FROM ( ");
		sql.append(" SELECT CDA.* FROM CSMS_DAYSET_WAREUNTAKE CDW FULL JOIN CSMS_DAYSET_APPROVAL CDA ");
		sql.append(" ON CDW.SETTLEDAY = CDA.SETTLEDAY AND CDW.DEPID =  " + stockId);
		sql.append("  WHERE CDA.SALESDEP = " + depId + "  ");
		if (state != null) {
			sql.append("AND CDA.STATE='" + state + "' ");
		}
		sql.append(" ) TB WHERE 1=1 ");
		if (!StringUtil.isEmpty(date)) {
			sql.append(" AND SETTLEDAY = '" + date + "'");
		}
		sql.append(" ORDER BY SETTLEDAY DESC");
		return this.findByPages(sql.toString(), pager, null);
	}

	public WareUntake findUntakeWare(WareUntake wareUntake) {
		String sql = "SELECT * FROM CSMS_DAYSET_WAREUNTAKE WHERE ID = " + wareUntake.getId();
		List<Map<String, Object>> list = queryList(sql);
		WareUntake daySetApprove = null;
		if (!list.isEmpty()) {
			daySetApprove = new WareUntake();
			this.convert2Bean(list.get(0), daySetApprove);
		}

		return daySetApprove;
	}

	public void saveWareUntake(WareUntake wareUntake) {
		StringBuffer sql = null;
		if (wareUntake.getId() == null) {

			wareUntake.setId(sequenceUtil.getSequenceLong("SEQ_DAYSET_WAREUNTAKE"));
			sql = new StringBuffer("insert into CSMS_DAYSET_WAREUNTAKE(");
			sql.append(FieldUtil.getFieldMap(WareUntake.class, wareUntake).get("nameStr") + ") values(");
			sql.append(FieldUtil.getFieldMap(WareUntake.class, wareUntake).get("valueStr") + ")");
			save(sql.toString());
		} else {
			sql = new StringBuffer(
					"UPDATE CSMS_DAYSET_WAREUNTAKE SET PRODUCTCODE=?, PRODUCTNAME=?, PRODUCTSOURCE=?, PRODUCTSTATE=?, WARECOUNT=?,  OPERID=?, OPERCODE=?, OPERNAME=? WHERE ID=? ");
			update(sql.toString(), wareUntake.getProductCode(), wareUntake.getProductName(),
					wareUntake.getProductSource(), wareUntake.getProductState(), wareUntake.getWareCount(),
					wareUntake.getOperId(), wareUntake.getOperCode(), wareUntake.getOperName(), wareUntake.getId());
		}

	}

	/*
	 * 查询网点的产品库存信息
	 */
	public List<Map<String, Object>> findSysWareList(Long salesDep, String startTime, String endTime) {
		StringBuffer sql = new StringBuffer(
				"SELECT OIT.SOURCE,OIT.SOURCENAME,OIT.PRODUCTSTATE,count(OIT.PRODUCTCODE) AS TOTALCOUNT,OPI.CODE AS PRODUCTCODE,OPI.NAME AS PRODUCTNAME,NVL(OIT.SOURCETYPE,-1) AS PRODUCTSOURCE FROM ");
		sql.append(
				" OMS_PRODUCTBACK OIT JOIN OMS_PRODUCTINFO OPI ON OIT.PRODUCTINFO = OPI.ID JOIN OMS_CUSTOMPOINT OCP ON OIT.SOURCE = OCP.ID WHERE (OIT.PRODUCTSTATE = '4' OR OIT.PRODUCTSTATE = '5') AND ");
		sql.append("TO_CHAR(OIT.OPERATETIME,'YYYYMMDDHH24MISS')>='" + startTime
				+ "' AND TO_CHAR(OIT.OPERATETIME,'YYYYMMDDHH24MISS') <='" + endTime + "' AND OCP.PARENT= " + salesDep);
		sql.append(" GROUP BY OIT.SOURCE,OIT.SOURCENAME,OIT.PRODUCTSTATE,OPI.CODE,OIT.SOURCETYPE,OPI.NAME UNION ");
		sql.append(
				"SELECT CUSTOMPOINT AS SOURCE,POINTNAME AS SOURCENAME,'2' AS PRODUCTSTATE,TAKECOUNT AS TOTALCOUNT,PRODUCTCODE,PRODUCTNAME,SOURCETYPE AS PRODUCTSOURCE FROM VW_OMS_PRODUCTTAKELIST VW JOIN OMS_CUSTOMPOINT OCP ");
		sql.append(" ON VW.CUSTOMPOINT = OCP.ID WHERE TO_CHAR(VW.ENDDAY,'YYYYMMDD')='" + endTime.substring(0, 8)
				+ "' AND OCP.PARENT = " + salesDep);
		return queryList(sql.toString());
	}

	public List<WareUntake> findUntakeWareList(Long salesDep, String settleDay) {
		String sql = "SELECT * FROM CSMS_DAYSET_WAREUNTAKE WHERE DEPID = " + salesDep + " AND SETTLEDAY = '" + settleDay
				+ "' ORDER BY PRODUCTCODE ASC";
		List<WareUntake> wareUntakeList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			wareUntakeList = new ArrayList<WareUntake>();
			for (Map<String, Object> map : list) {
				WareUntake sysWare = new WareUntake();
				sysWare = (WareUntake) this.convert2Bean((Map<String, Object>) map, new WareUntake());
				wareUntakeList.add(sysWare);

			}

		}
		return wareUntakeList;
	}

	public List<WareUntakeVo> findSysUntakeWareList(Long salesDep, String settleDay) {
		StringBuffer sql = new StringBuffer(
				"SELECT CDW.SETTLEDAY,CDW.PRODUCTCODE,CDW.PRODUCTNAME,CDW.PRODUCTSOURCE,CDW.PRODUCTSTATE,CDW.WARECOUNT,VOP.COUNT AS SYSCOUNT,CDW.DEPID FROM VE_OMS_PRODUCTUNTAKELIST VOP LEFT JOIN CSMS_DAYSET_WAREUNTAKE CDW ON ");
		sql.append(" VOP.STOCKPLACE = CDW.DEPID AND ");
		sql.append(
				" VOP.PRODUCTCODE = CDW.PRODUCTCODE AND VOP.SOURCETYPE = CDW.PRODUCTSOURCE AND VOP.PRODUCTSTATE = CDW.PRODUCTSTATE");
		sql.append(" WHERE ");
		sql.append(" TO_CHAR(VOP.ENDDAY,'YYYY-MM-DD')=CDW.SETTLEDAY AND CDW.DEPID = ? AND CDW.SETTLEDAY = ?");
		List<WareUntakeVo> wareUntakeList = null;
		List<Map<String, Object>> list = queryList(sql.toString(), salesDep, settleDay);
		if (list.size() > 0) {
			wareUntakeList = new ArrayList<WareUntakeVo>();
			for (Map<String, Object> map : list) {
				WareUntakeVo sysWare = new WareUntakeVo();
				sysWare = (WareUntakeVo) this.convert2Bean((Map<String, Object>) map, new WareUntakeVo());
				wareUntakeList.add(sysWare);

			}

		}
		return wareUntakeList;
	}

	public void updateRechargeInfo(String settleDay, String sysTime, String placeIdList) {
		String sql = "update CSMS_RECHARGEINFO set IsDaySet = 1,SettleDay = '" + settleDay + "',SettletTime=to_date('"
				+ sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " where nvl(IsDaySet,'0')='0' AND OperTime<=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " AND PLACENO IN(" + placeIdList + ")";
		update(sql);
	}

	public void updatePrepaidCBussiness(String settleDay, String sysTime, String placeIdList) {
		String sql = "update CSMS_PREPAIDC_BUSSINESS set IsDaySet = 1,SettleDay = '" + settleDay
				+ "',SettletTime=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " where nvl(IsDaySet,'0')='0' AND TRADETIME<=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss')";
		sql += " AND PLACENO IN(" + placeIdList + ")";
		update(sql);
	}

	public void updateAccountCBussiness(String settleDay, String sysTime, String placeIdList) {
		String sql = "update CSMS_AccountC_bussiness set IsDaySet = 1,SettleDay = '" + settleDay
				+ "',SettletTime=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " where nvl(IsDaySet,'0')='0' AND TRADETIME<=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss')";
		sql += " AND PLACENO IN(" + placeIdList + ")";
		update(sql);
	}

	public void updateTagTakeFee(String settleDay, String sysTime, String placeIdList) {
		String sql = "update CSMS_TagTakeFee_Info set IsDaySet = 1,SettleDay = '" + settleDay
				+ "',SettletTime=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " where nvl(IsDaySet,'0')='0' AND RegisterDate<=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss')";
		sql += " AND PLACENO IN(" + placeIdList + ")";
		update(sql);
	}

	public void updateTagTakeInfo(String settleDay, String sysTime, String placeIdList) {
		String sql = "update CSMS_TagTake_Info set IsDaySet = 1,SettleDay = '" + settleDay + "',SettletTime=to_date('"
				+ sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " where nvl(IsDaySet,'0')='0' AND TakeDate<=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss')";
		sql += " AND PLACENO IN(" + placeIdList + ")";
		update(sql);
	}

	public void updateTagBusiness(String settleDay, String sysTime, String placeIdList) {
		String sql = "update CSMS_Tag_BusinessRecord set IsDaySet = 1,SettleDay = '" + settleDay
				+ "',SettletTime=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss') ";
		sql += " where nvl(IsDaySet,'0')='0' AND OperTime<=to_date('" + sysTime + "','yyyy-MM-dd:hh24:mi:ss')";
		sql += " AND PLACENO IN(" + placeIdList + ")";
		update(sql);
	}

	public CustomPoint findCustomPoint(Long cusPoint) {
		String sql = "select * from oms_custompoint where ID=(select parent from oms_custompoint where id = " + cusPoint
				+ ")";
		List<Map<String, Object>> list = queryList(sql);
		CustomPoint customPoint = null;
		if (!list.isEmpty()) {
			customPoint = new CustomPoint();
			this.convert2Bean(list.get(0), customPoint);
		}

		return customPoint;
	}

	public CustomPoint findCurrentPointById(Long cusPointId) {
		String sql = "select * from oms_custompoint where ID=" + cusPointId;
		List<Map<String, Object>> list = queryList(sql);
		CustomPoint customPoint = null;
		if (!list.isEmpty()) {
			customPoint = new CustomPoint();
			this.convert2Bean(list.get(0), customPoint);
		}

		return customPoint;
	}

	public void saveSysFeeSolid(FeeReportRecordVo feeReportRecordVo, Long daysetId) {
		//元转化为分
		if(feeReportRecordVo!=null) {
			if(feeReportRecordVo.getCash()!=null) {
				feeReportRecordVo.setCash(BigDecimal.valueOf(feeReportRecordVo.getCash()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getSysCash()!=null) {
				feeReportRecordVo.setSysCash(BigDecimal.valueOf(feeReportRecordVo.getSysCash()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getPos()!=null) {
				feeReportRecordVo.setPos(BigDecimal.valueOf(feeReportRecordVo.getPos()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getSysPos()!=null) {
				feeReportRecordVo.setSysPos(BigDecimal.valueOf(feeReportRecordVo.getSysPos()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getTransferAccount()!=null) {
				feeReportRecordVo.setTransferAccount(BigDecimal.valueOf(feeReportRecordVo.getTransferAccount()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getSysTransferAccount()!=null) {
				feeReportRecordVo.setSysTransferAccount(BigDecimal.valueOf(feeReportRecordVo.getSysTransferAccount()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getWechat()!=null) {
				feeReportRecordVo.setWechat(BigDecimal.valueOf(feeReportRecordVo.getWechat()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getSysWechat()!=null) {
				feeReportRecordVo.setSysWechat(BigDecimal.valueOf(feeReportRecordVo.getSysWechat()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getAlipay()!=null) {
				feeReportRecordVo.setAlipay(BigDecimal.valueOf(feeReportRecordVo.getAlipay()).multiply(new BigDecimal("100")).doubleValue());
			}
			if(feeReportRecordVo.getSysAlipay()!=null) {
				feeReportRecordVo.setSysAlipay(BigDecimal.valueOf(feeReportRecordVo.getSysAlipay()).multiply(new BigDecimal("100")).doubleValue());
			}
			if (feeReportRecordVo.getBill() != null) {
				feeReportRecordVo.setBill(BigDecimal.valueOf(feeReportRecordVo.getBill()).multiply(new BigDecimal("100")).doubleValue());
			}
			if (feeReportRecordVo.getSysBill() != null) {
				feeReportRecordVo.setSysBill(BigDecimal.valueOf(feeReportRecordVo.getSysBill()).multiply(new BigDecimal("100")).doubleValue());
			}
		}
		String sql = "INSERT INTO CSMS_DAYSET_FEESOLID (ID,DAYSETID,PLACEID,PLACENAME,CASH,SYSCASH,POS,SYSPOS,TRANSFERACCOUNT,SYSTRANSFERACCOUNT,WECHAT,SYSWECHAT,ALIPAY,SYSALIPAY,PLACECODE,BILL,SYSBILL) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		save(sql, sequenceUtil.getSequenceLong("SEQ_DAYSET_FEESOLID"), daysetId, feeReportRecordVo.getPlaceid(),
				feeReportRecordVo.getPlaceName(), feeReportRecordVo.getCash(), feeReportRecordVo.getSysCash(),
				feeReportRecordVo.getPos(), feeReportRecordVo.getSysPos(), feeReportRecordVo.getTransferAccount(),
				feeReportRecordVo.getSysTransferAccount(), feeReportRecordVo.getWechat(),
				feeReportRecordVo.getSysWechat(), feeReportRecordVo.getAlipay(), feeReportRecordVo.getSysAlipay(),
				feeReportRecordVo.getPlaceCode(),
				feeReportRecordVo.getBill(),
				feeReportRecordVo.getSysBill());

	}

	public void saveSysWareSolid(WareReportRecord wareReportRecord, Long daysetId) {
		String sql = "INSERT INTO CSMS_DAYSET_WARESOLID(ID,DAYSETID,PLACEID,PLACENAME,PRODUCTCODE,PRODUCTNAME,PRODUCTSOURCE,PRODUCTSTATE,WARECOUNT,SYSCOUNT,PLACECODE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		save(sql, sequenceUtil.getSequenceLong("SEQ_DAYSET_WARESOLID"), daysetId, wareReportRecord.getPlaceid(),
				wareReportRecord.getPlaceName(), wareReportRecord.getProductCode(), wareReportRecord.getProductName(),
				wareReportRecord.getProductSource(), wareReportRecord.getProductState(),
				wareReportRecord.getWareCount(), wareReportRecord.getSysCount(), wareReportRecord.getPlaceCode());
	}

	public List<FeeReportRecordVo> findFeeSolid(Long id) {
		String sql = "SELECT * FROM CSMS_DAYSET_FEESOLID WHERE DAYSETID = ? ORDER BY PLACECODE ASC";
		List<FeeReportRecordVo> feeReportRecordVo = null;
		List<Map<String, Object>> list = queryList(sql, id);
		if (list.size() > 0) {
			feeReportRecordVo = new ArrayList<FeeReportRecordVo>();
			for (Map<String, Object> map : list) {
				FeeReportRecordVo sysWare = new FeeReportRecordVo();
				sysWare = (FeeReportRecordVo) this.convert2Bean((Map<String, Object>) map, new FeeReportRecordVo());
				
				//分转化为元
				if(sysWare!=null) {
					if(sysWare.getCash()!=null) {
						sysWare.setCash(BigDecimal.valueOf(sysWare.getCash()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getSysCash()!=null) {
						sysWare.setSysCash(BigDecimal.valueOf(sysWare.getSysCash()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getPos()!=null) {
						sysWare.setPos(BigDecimal.valueOf(sysWare.getPos()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getSysPos()!=null) {
						sysWare.setSysPos(BigDecimal.valueOf(sysWare.getSysPos()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getTransferAccount()!=null) {
						sysWare.setTransferAccount(BigDecimal.valueOf(sysWare.getTransferAccount()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getSysTransferAccount()!=null) {
						sysWare.setSysTransferAccount(BigDecimal.valueOf(sysWare.getSysTransferAccount()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getWechat()!=null) {
						sysWare.setWechat(BigDecimal.valueOf(sysWare.getWechat()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getSysWechat()!=null) {
						sysWare.setSysWechat(BigDecimal.valueOf(sysWare.getSysWechat()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getAlipay()!=null) {
						sysWare.setAlipay(BigDecimal.valueOf(sysWare.getAlipay()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if(sysWare.getSysAlipay()!=null) {
						sysWare.setSysAlipay(BigDecimal.valueOf(sysWare.getSysAlipay()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (sysWare.getBill() != null) {
						sysWare.setBill(BigDecimal.valueOf(sysWare.getBill()).multiply(new BigDecimal("0.01")).doubleValue());
					}
					if (sysWare.getSysBill() != null) {
						sysWare.setSysBill(BigDecimal.valueOf(sysWare.getSysBill()).multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				feeReportRecordVo.add(sysWare);

			}

		}
		return feeReportRecordVo;
	}

	public List<WareReportRecord> findWareSolid(Long id) {
		String sql = "SELECT * FROM CSMS_DAYSET_WARESOLID WHERE DAYSETID = ? ORDER BY PLACECODE ASC";
		List<WareReportRecord> WareReportRecord = null;
		List<Map<String, Object>> list = queryList(sql, id);
		if (list.size() > 0) {
			WareReportRecord = new ArrayList<WareReportRecord>();
			for (Map<String, Object> map : list) {
				WareReportRecord sysWare = new WareReportRecord();
				sysWare = (WareReportRecord) this.convert2Bean((Map<String, Object>) map, new WareReportRecord());
				WareReportRecord.add(sysWare);

			}

		}
		return WareReportRecord;
	}
	
	public WareReportRecord findWareSolidById(Long id) {
		String sql = "SELECT * FROM CSMS_DAYSET_WARESOLID WHERE ID = ?";
		WareReportRecord wareReportRecord = null;
		List<Map<String, Object>> list = queryList(sql, id);
		if (!list.isEmpty()) {
			wareReportRecord = new WareReportRecord();
			this.convert2Bean(list.get(0), wareReportRecord);
		}

		return wareReportRecord;
	}

	public void deleteWareUntake(WareUntake wareUntake) {
		String sql = "DELETE FROM CSMS_DAYSET_WAREUNTAKE WHERE ID = ?";
		delete(sql, wareUntake.getId());
	}

	public void deleteWareUntake(Long id) {
		String sql = "DELETE FROM CSMS_DAYSET_WAREUNTAKE WHERE ID = ?";
		delete(sql, id);
	}

	public Long findFeeWareByCustonPoint(Long cusPoint, String tableName) {
		String sql = "SELECT count(1) as TOTALCOUNT FROM " + tableName + " WHERE PLACEID=?";
		List<Map<String, Object>> list = queryList(sql, cusPoint);
		Long totalCount = null;
		if (!list.isEmpty()) {
			totalCount = Long.valueOf(list.get(0).get("TOTALCOUNT").toString());
		}
		return totalCount;
	}

	public List<CashDepositDaySet> findCashByDate(Long cusPoint, String preDay) {
		String sql = "SELECT * FROM CSMS_CashDepositDaySet WHERE PLACEID=? AND SETTLEDAY = ?";
		List<CashDepositDaySet> cashList = null;
		List<Map<String, Object>> list = queryList(sql, cusPoint, preDay);
		if (list.size() > 0) {
			cashList = new ArrayList<CashDepositDaySet>();
			for (Map<String, Object> map : list) {
				CashDepositDaySet cash = new CashDepositDaySet();
				cash = (CashDepositDaySet) this.convert2Bean((Map<String, Object>) map, new CashDepositDaySet());
				// 分转化为元
				if (cash.getPublicAcc() != null) {
					cash.setPublicAcc(
							BigDecimal.valueOf(cash.getPublicAcc()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				if (cash.getWithholdAcc() != null) {
					cash.setWithholdAcc(
							BigDecimal.valueOf(cash.getWithholdAcc()).multiply(new BigDecimal("0.01")).doubleValue());
				}
				if (cash.getOutstandingAmount() != null) {
					cash.setOutstandingAmount(BigDecimal.valueOf(cash.getOutstandingAmount())
							.multiply(new BigDecimal("0.01")).doubleValue());
				}
				cashList.add(cash);

			}

		}
		return cashList;
	}

	public List<WareUntake> findWareUntakeByDate(Long stockPlace, String preDay) {
		String sql = "SELECT * FROM CSMS_DAYSET_WAREUNTAKE WHERE DEPID = ? AND SETTLEDAY=?";
		List<WareUntake> wareUntakeList = null;
		List<Map<String, Object>> list = queryList(sql, stockPlace, preDay);
		if (list.size() > 0) {
			wareUntakeList = new ArrayList<WareUntake>();
			for (Map<String, Object> map : list) {
				WareUntake sysWare = new WareUntake();
				sysWare = (WareUntake) this.convert2Bean((Map<String, Object>) map, new WareUntake());
				wareUntakeList.add(sysWare);

			}

		}
		return wareUntakeList;
	}

	public Long findUntakeWareCount(Long stockPlace, String settleDay) {
		String sql = "SELECT count(1) as TOTALCOUNT FROM CSMS_DAYSET_WAREUNTAKE WHERE DEPID = ? AND SETTLEDAY<>?";
		List<Map<String, Object>> list = queryList(sql, stockPlace, settleDay);
		Long totalCount = null;
		if (!list.isEmpty()) {
			totalCount = Long.valueOf(list.get(0).get("TOTALCOUNT").toString());
		}
		return totalCount;
	}

	public Long findUnReportCount(Long stockPlace, String settleDay, String tableName) {
		StringBuilder sql = new StringBuilder("SELECT COUNT(1) AS TOTALCOUNT FROM ");
		sql.append("(SELECT OCP.CODE,OCP.NAME,CWR.PLACEID FROM oms_custompoint OCP LEFT JOIN " + tableName
				+ " CWR ON OCP.ID = CWR.PLACEID AND CWR.SETTLEDAY = ? WHERE OCP.PARENT = ?");
		sql.append(" ) AA WHERE AA.PLACEID IS NULL");
		List<Map<String, Object>> list = queryList(sql.toString(), settleDay, stockPlace);
		Long totalCount = null;
		if (!list.isEmpty()) {
			totalCount = Long.valueOf(list.get(0).get("TOTALCOUNT").toString());
		}
		return totalCount;
	}

	public List<DaySetCorrectRecord> findDaySetCorrectRecord(String sysStartTime, String sysEndTime, Long parentId) {
		String sql = "select CDR.* from CSMS_DAYSETCORRECT_RECORD CDR JOIN OMS_CUSTOMPOINT OC ON CDR.PLACEID = OC.ID  WHERE TO_CHAR(CDR.CORRECTTIME,'YYYYMMDDHH24MISS')>=? AND TO_CHAR(CDR.CORRECTTIME,'YYYYMMDDHH24MISS')<=? AND OC.PARENT =?";
		List<DaySetCorrectRecord> correctList = null;
		List<Map<String, Object>> list = queryList(sql, sysStartTime, sysEndTime, parentId);
		if (list.size() > 0) {
			correctList = new ArrayList<DaySetCorrectRecord>();
			for (Map<String, Object> map : list) {
				DaySetCorrectRecord correct = new DaySetCorrectRecord();
				correct = (DaySetCorrectRecord) this.convert2Bean((Map<String, Object>) map, new DaySetCorrectRecord());
				//分转化为元
				if(correct!=null) {
					if (correct.getCorrectFee() != null) {
						correct.setCorrectFee(BigDecimal.valueOf(correct.getCorrectFee())
								.multiply(new BigDecimal("0.01")).doubleValue());
					}
				}
				correctList.add(correct);

			}

		}
		return correctList;
	}

	/**
	 * @param daySetId
	 *            日结审批ID
	 * @param startTime
	 *            系统计算开始时间
	 * @param endTime
	 *            系统计算结束时间
	 * @param parentId
	 *            营业部id
	 * @param settleDay
	 *            日结日
	 * @param depId
	 *            库存地id
	 * @return
	 */
	public List<WareReportRecord> findTotalWare(Long daySetId, String startTime, String endTime, Long parentId,
			String settleDay) {
		StringBuffer sql = new StringBuffer(
				"SELECT TABCODE.PLACEID,CCP.CODE AS PLACECODE,CCP.NAME AS PLACENAME,TABCODE.PRODUCTCODE,TABCODE.PRODUCTNAME,");
		sql.append(" TABCODE.PRODUCTSOURCE,TABCODE.PRODUCTSTATE,TABCODE.WARECOUNT,TABCODE.SYSCOUNT FROM (");
		sql.append(" SELECT  ");
		sql.append(" CASE WHEN AA.PLACEID IS NULL THEN BB.SOURCE ELSE AA.PLACEID END AS PLACEID, ");
		sql.append(" CASE WHEN AA.PLACECODE IS NULL THEN BB.PLACECODE ELSE AA.PLACECODE END AS PLACECODE, ");
		sql.append(" CASE WHEN AA.PLACENAME IS NULL THEN BB.SOURCENAME ELSE AA.PLACENAME END AS PLACENAME,");
		sql.append(" CASE WHEN AA.PRODUCTCODE IS NULL THEN BB.PRODUCTCODE ELSE AA.PRODUCTCODE END AS PRODUCTCODE,");
		sql.append(" CASE WHEN AA.PRODUCTNAME IS NULL THEN BB.PRODUCTNAME ELSE AA.PRODUCTNAME END AS PRODUCTNAME, ");
		sql.append(
				" CASE WHEN AA.PRODUCTSOURCE IS NULL THEN BB.PRODUCTSOURCE ELSE AA.PRODUCTSOURCE END AS PRODUCTSOURCE, ");
		sql.append(
				" CASE WHEN AA.PRODUCTSTATE IS NULL THEN BB.PRODUCTSTATE ELSE AA.PRODUCTSTATE END AS PRODUCTSTATE,   ");
		sql.append(" AA.WARECOUNT, ");
		sql.append(" BB.TOTALCOUNT AS SYSCOUNT  ");
		sql.append(" FROM  ");
		sql.append(
				" (SELECT PLACEID,PLACECODE,PLACENAME,PRODUCTSTATE,WARECOUNT,PRODUCTCODE,PRODUCTNAME,PRODUCTSOURCE FROM CSMS_WAREREPORT_RECORD CWR  WHERE DAYSETID = "
						+ daySetId + ") AA ");
		sql.append(" FULL JOIN ");
		sql.append(" ( ");
		sql.append(" SELECT OCP.CODE AS PLACECODE,");
		sql.append(" OIT.SOURCE, ");
		sql.append(" OIT.SOURCENAME, ");
		sql.append(" OIT.PRODUCTSTATE, ");
		sql.append(" count(OIT.PRODUCTCODE) AS TOTALCOUNT,");
		sql.append(" OPI.CODE AS PRODUCTCODE, ");
		sql.append(" OPI.NAME AS PRODUCTNAME, ");
		sql.append(" NVL(OIT.SOURCETYPE,-1) AS PRODUCTSOURCE ");
		sql.append(" FROM OMS_PRODUCTBACK OIT ");
		sql.append(" JOIN OMS_PRODUCTINFO OPI ON OIT.PRODUCTINFO = OPI.ID ");
		sql.append(" JOIN  OMS_CUSTOMPOINT OCP ON OIT.SOURCE = OCP.ID  ");
		sql.append(" WHERE (OIT.PRODUCTSTATE = '4' OR OIT.PRODUCTSTATE = '5') ");
		sql.append(" AND  TO_CHAR(OIT.OPERATETIME,'YYYYMMDDHH24MISS')>='" + startTime
				+ "'  AND TO_CHAR(OIT.OPERATETIME,'YYYYMMDDHH24MISS') <='" + endTime + "' AND OCP.PARENT=  "
				+ parentId);
		sql.append("  GROUP BY OIT.SOURCE,OIT.SOURCENAME,OIT.PRODUCTSTATE,OPI.CODE,OIT.SOURCETYPE,OPI.NAME,OCP.CODE  ");
		sql.append(" UNION  ");
		sql.append(" SELECT OCP.CODE AS PRODUCTCODE,");
		sql.append(" CUSTOMPOINT AS SOURCE,");
		sql.append(" POINTNAME AS SOURCENAME,");
		sql.append(" '2' AS PRODUCTSTATE,");
		sql.append(" TAKECOUNT AS TOTALCOUNT,");
		sql.append(" PRODUCTCODE,");
		sql.append(" PRODUCTNAME,");
		sql.append(" SOURCETYPE AS PRODUCTSOURCE FROM  VW_OMS_PRODUCTTAKELIST VW JOIN ");
		sql.append(" OMS_CUSTOMPOINT OCP  ON VW.CUSTOMPOINT = OCP.ID WHERE TO_CHAR(VW.ENDDAY,'YYYY-MM-DD')='"
				+ settleDay + "' AND OCP.PARENT = " + parentId + ") BB ON AA.PLACEID = BB.SOURCE ");
		sql.append(
				" AND AA.PRODUCTCODE = BB.PRODUCTCODE AND AA.PRODUCTSOURCE = BB.PRODUCTSOURCE AND AA.PRODUCTSTATE = BB.PRODUCTSTATE ) TABCODE");
		sql.append(" RIGHT JOIN OMS_CUSTOMPOINT CCP ON TABCODE.PLACEID = CCP.ID WHERE CCP.PARENT=" + parentId
				+ " ORDER BY PLACECODE,PRODUCTCODE ASC");
		List<WareReportRecord> wareList = null;
		List<Map<String, Object>> list = queryList(sql.toString());
		if (list.size() > 0) {
			wareList = new ArrayList<WareReportRecord>();
			for (Map<String, Object> map : list) {
				WareReportRecord fee = new WareReportRecord();
				fee = (WareReportRecord) this.convert2Bean((Map<String, Object>) map, new WareReportRecord());
				wareList.add(fee);

			}

		}
		return wareList;
	}

	public List<WareUntakeVo> findUntakeWareListAll(Long stockId, String settleDay) {
		StringBuffer sql = new StringBuffer(
				"SELECT PRODUCTCODE,PRODUCTNAME,PRODUCTSOURCE,PRODUCTSTATE,SUM(WARECOUNT) AS WARECOUNT,SUM(SYSCOUNT) AS SYSCOUNT FROM( ");
		sql.append(" SELECT  ");
		sql.append(" CASE WHEN CDW.PRODUCTCODE IS NULL THEN VOP.PRODUCTCODE ELSE CDW.PRODUCTCODE END AS PRODUCTCODE,");
		sql.append(" CASE WHEN CDW.PRODUCTNAME IS NULL THEN VOP.PRODUCTNAME ELSE CDW.PRODUCTNAME END AS PRODUCTNAME,");
		sql.append(
				" CASE WHEN CDW.PRODUCTSOURCE IS NULL THEN VOP.SOURCETYPE ELSE CDW.PRODUCTSOURCE END AS PRODUCTSOURCE,");
		sql.append(
				" CASE WHEN CDW.PRODUCTSTATE IS NULL THEN VOP.PRODUCTSTATE ELSE CDW.PRODUCTSTATE END AS PRODUCTSTATE,");
		sql.append(" CDW.WARECOUNT,");
		sql.append(" VOP.COUNT AS SYSCOUNT,");
		sql.append(" CDW.DEPID");
		sql.append(" FROM VE_OMS_PRODUCTUNTAKELIST VOP");
		sql.append(
				" FULL JOIN CSMS_DAYSET_WAREUNTAKE CDW ON   VOP.STOCKPLACE = CDW.DEPID  AND  VOP.PRODUCTCODE = CDW.PRODUCTCODE  AND VOP.SOURCETYPE = CDW.PRODUCTSOURCE ");
		sql.append(" AND VOP.PRODUCTSTATE = CDW.PRODUCTSTATE AND TO_CHAR(VOP.ENDDAY,'YYYY-MM-DD')=CDW.SETTLEDAY");
		sql.append(" WHERE CDW.DEPID = " + stockId + " AND CDW.SETTLEDAY = '" + settleDay + "'  ");
		sql.append(" AND NVL(VOP.ISREPLACEBACK,0) <>1 ");
		sql.append(" UNION ALL ");
		sql.append(" SELECT  ");
		sql.append(" CASE WHEN CDW.PRODUCTCODE IS NULL THEN VOP.PRODUCTCODE ELSE CDW.PRODUCTCODE END AS PRODUCTCODE,");
		sql.append(" CASE WHEN CDW.PRODUCTNAME IS NULL THEN VOP.PRODUCTNAME ELSE CDW.PRODUCTNAME END AS PRODUCTNAME,");
		sql.append(
				" CASE WHEN CDW.PRODUCTSOURCE IS NULL THEN VOP.SOURCETYPE ELSE CDW.PRODUCTSOURCE END AS PRODUCTSOURCE,");
		sql.append(
				" CASE WHEN CDW.PRODUCTSTATE IS NULL THEN VOP.PRODUCTSTATE ELSE CDW.PRODUCTSTATE END AS PRODUCTSTATE,");
		sql.append(" CDW.WARECOUNT,");
		sql.append(" VOP.COUNT AS SYSCOUNT,");
		sql.append(" CDW.DEPID");
		sql.append(
				"  FROM VE_OMS_PRODUCTUNTAKELIST VOP  FULL JOIN CSMS_DAYSET_WAREUNTAKE CDW ON   VOP.STOCKPLACE = CDW.DEPID  AND ");
		sql.append(
				" VOP.PRODUCTCODE = CDW.PRODUCTCODE  AND VOP.SOURCETYPE = CDW.PRODUCTSOURCE  AND VOP.PRODUCTSTATE = CDW.PRODUCTSTATE");
		sql.append(" AND TO_CHAR(VOP.ENDDAY,'YYYY-MM-DD')=CDW.SETTLEDAY  WHERE   ");
		sql.append(" CDW.DEPID = " + stockId + " AND CDW.SETTLEDAY = TO_CHAR(TO_DATE('" + settleDay
				+ "','YYYY-MM-DD')-1,'YYYY-MM-DD') ");
		sql.append(" AND VOP.ISREPLACEBACK = 1) AA GROUP BY PRODUCTCODE,PRODUCTNAME,PRODUCTSOURCE,PRODUCTSTATE ORDER BY PRODUCTCODE");
		List<WareUntakeVo> wareUntakeList = null;
		List<Map<String, Object>> list = queryList(sql.toString());
		if (list.size() > 0) {
			wareUntakeList = new ArrayList<WareUntakeVo>();
			for (Map<String, Object> map : list) {
				WareUntakeVo sysWare = new WareUntakeVo();
				sysWare = (WareUntakeVo) this.convert2Bean((Map<String, Object>) map, new WareUntakeVo());
				wareUntakeList.add(sysWare);

			}

		}
		return wareUntakeList;
	}

	public List<Role> findUserRole(Long operId) {
		StringBuffer sql = new StringBuffer("SELECT t.*,r.name ,r.propertyType FROM ");
		sql.append(
				" (select u.subuumstem,u.uumsname,a.role from uums_admin_subuumstem u left join Uums_Adminsubuumstem_Role a ");
		sql.append("    on u.id = a.adminsubuumstem where u.admin =?)");
		sql.append("     t left join uums_role r on t.role = r.id");
		List<Role> userRoleList = null;
		List<Map<String, Object>> list = queryList(sql.toString(), operId);
		if (list.size() > 0) {
			userRoleList = new ArrayList<Role>();
			for (Map<String, Object> map : list) {
				Role userRole = new Role();
				userRole = (Role) this.convert2Bean((Map<String, Object>) map, new Role());
				userRoleList.add(userRole);

			}

		}
		return userRoleList;
	}

	public void saveUntakeSolid(WareUntakeVo wareUntakeVo, DaySetApprove daySetApprove) {
		Long id = sequenceUtil.getSequenceLong("SEQ_DAYSET_UNTAKEWARESOLID");
		String sql = "INSERT INTO CSMS_DAYSET_UNTAKEWARESOLID (ID,DAYSETID,PRODUCTCODE,PRODUCTNAME,"
				+ "PRODUCTSOURCE,PRODUCTSTATE,WARECOUNT,SYSCOUNT) " + "VALUES (?,?,?,?,?,?,?,?)";
		save(sql, id, daySetApprove.getId(), wareUntakeVo.getProductCode(), wareUntakeVo.getProductName(),
				wareUntakeVo.getProductSource(), wareUntakeVo.getProductState(), wareUntakeVo.getWareCount(),
				wareUntakeVo.getSysCount());
	}

	public List<WareUntakeVo> findUntakeSolid(Long id) {
		String sql = "SELECT * FROM CSMS_DAYSET_UNTAKEWARESOLID WHERE DAYSETID=? ORDER BY PRODUCTCODE ASC";
		List<WareUntakeVo> wareUntakeList = null;
		List<Map<String, Object>> list = queryList(sql, id);
		if (list.size() > 0) {
			wareUntakeList = new ArrayList<WareUntakeVo>();
			for (Map<String, Object> map : list) {
				WareUntakeVo sysWare = new WareUntakeVo();
				sysWare = (WareUntakeVo) this.convert2Bean((Map<String, Object>) map, new WareUntakeVo());
				wareUntakeList.add(sysWare);

			}

		}
		return wareUntakeList;
	}

	public WareUntake findLastUntake(Long stockPlace) {
		String sql = "SELECT * FROM ( SELECT * FROM CSMS_DAYSET_WAREUNTAKE WHERE DEPID=? ORDER BY REPLACE(SETTLEDAY, '-', '') DESC ) TB WHERE ROWNUM = 1 ";
		List<Map<String, Object>> list = queryList(sql, stockPlace);
		WareUntake daySetApprove = null;
		if (!list.isEmpty()) {
			daySetApprove = new WareUntake();
			this.convert2Bean(list.get(0), daySetApprove);
		}

		return daySetApprove;
	}

	public List<LongFeeCorrect> findLongFeeList(Long salesDep, String settleDay) {
		String sql1 = "SELECT CCP.ID AS PLACEID,CCP.CODE AS PLACECODE,CRI.placename,'账户缴款' as BALANCETYPE,CRI.TAKEBALANCE/100 AS TAKEBALANCE,CRI.opername,CRI.PayMember,CRI.PayMember as ACTUALMEMBER "
				+ "FROM CSMS_RechargeInfo CRI ,OMS_CUSTOMPOINT CCP WHERE CRI.PayMentType = 6 AND CRI.SETTLEDAY = '"
				+ settleDay + "' " + "AND CRI.PLACENO = CCP.CODE AND CCP.PARENT = " + salesDep;
		String sql2 = "SELECT CCP.ID AS PLACEID,CCP.CODE AS PLACECODE,CTI.placeName,'电子标签提货金额登记' as BALANCETYPE,CTI.TakeBalance/100 AS TAKEBALANCE,CTI.opername,CTI.ClientName as PAYMEMBER,CTI.ClientName as ACTUALMEMBER "
				+ "FROM CSMS_TagTakeFee_Info CTI,OMS_CUSTOMPOINT CCP WHERE CTI.ChargeType = 4 AND CTI.SETTLEDAY = '"
				+ settleDay + "' " + "AND CTI.PLACENO = CCP.CODE AND CCP.PARENT = " + salesDep;
		String sql ="SELECT * FROM ("+ sql1 + " UNION ALL " + sql2+") ORDER BY PLACECODE";

		List<LongFeeCorrect> longFeeList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			longFeeList = new ArrayList<LongFeeCorrect>();
			for (Map<String, Object> map : list) {
				LongFeeCorrect sysWare = new LongFeeCorrect();
				sysWare = (LongFeeCorrect) this.convert2Bean((Map<String, Object>) map, new LongFeeCorrect());
				longFeeList.add(sysWare);

			}

		}
		return longFeeList;
	}
	
	public List<LongFeeCorrect> findCustomLongFeeList(Long customId, String settleDay) {
		String sql1 = "SELECT CCP.ID AS PLACEID,CCP.CODE AS PLACENAME,CRI.placename,'账户缴款' as BALANCETYPE,CRI.TAKEBALANCE/100 AS TAKEBALANCE,CRI.opername,CRI.PayMember,CRI.PayMember as ACTUALMEMBER,OperTime AS SETTLETTIME,memo "
				+ "FROM CSMS_RechargeInfo CRI ,OMS_CUSTOMPOINT CCP WHERE CRI.PayMentType = 6 AND CRI.SETTLEDAY = '"
				+ settleDay + "' " + "AND CRI.PLACENO = CCP.CODE AND CCP.ID = " + customId;
		String sql2 = "SELECT CCP.ID AS PLACEID,CCP.CODE AS PLACENAME,CTI.placeName,'电子标签提货金额登记' as BALANCETYPE,CTI.TakeBalance/100 AS TAKEBALANCE,CTI.opername,CTI.ClientName as PAYMEMBER,CTI.ClientName as ACTUALMEMBER,REGISTERDATE AS SETTLETTIME,memo "
				+ "FROM CSMS_TagTakeFee_Info CTI,OMS_CUSTOMPOINT CCP WHERE CTI.ChargeType = 4 AND CTI.SETTLEDAY = '"
				+ settleDay + "' " + "AND CTI.PLACENO = CCP.CODE AND CCP.ID = " + customId;
		String sql = sql1 + " UNION ALL " + sql2;

		List<LongFeeCorrect> longFeeList = null;
		List<Map<String, Object>> list = queryList(sql);
		if (list.size() > 0) {
			longFeeList = new ArrayList<LongFeeCorrect>();
			for (Map<String, Object> map : list) {
				LongFeeCorrect sysWare = new LongFeeCorrect();
				sysWare = (LongFeeCorrect) this.convert2Bean((Map<String, Object>) map, new LongFeeCorrect());
				longFeeList.add(sysWare);

			}

		}
		return longFeeList;
	}

	public List<WareUntake> findUntakeWareListBySettleDay(Long stockPlace, String settleDay) {
		String sql = "SELECT * FROM CSMS_DAYSET_WAREUNTAKE WHERE SETTLEDAY=? AND DEPID=? ORDER BY PRODUCTCODE ASC";
		List<WareUntake> wareUntakeList = null;
		List<Map<String, Object>> list = queryList(sql, settleDay, stockPlace);
		if (list.size() > 0) {
			wareUntakeList = new ArrayList<WareUntake>();
			for (Map<String, Object> map : list) {
				WareUntake sysWare = new WareUntake();
				sysWare = (WareUntake) this.convert2Bean((Map<String, Object>) map, new WareUntake());
				wareUntakeList.add(sysWare);

			}

		}
		return wareUntakeList;
	}

	public WareUntake findUntakeWare(Long depId, String settleDay, String productCode, String productSource,
			String productState) {
		String sql = "SELECT * FROM CSMS_DAYSET_WAREUNTAKE WHERE DEPID=? AND SETTLEDAY=? AND PRODUCTCODE=? AND PRODUCTSOURCE=? AND PRODUCTSTATE=?";
		List<Map<String, Object>> list = queryList(sql,depId,settleDay,productCode,productSource,productState);
		WareUntake daySetApprove = null;
		if (!list.isEmpty()) {
			daySetApprove = new WareUntake();
			this.convert2Bean(list.get(0), daySetApprove);
		}

		return daySetApprove;
	}

}
