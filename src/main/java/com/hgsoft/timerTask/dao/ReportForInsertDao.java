package com.hgsoft.timerTask.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hgsoft.common.dao.BaseDao;

@Repository
@Transactional
public class ReportForInsertDao extends BaseDao {
	
	/**
	 * 生成账户余额报表数据
	 */
	public void generateAccountBalanceReportData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		 String sqlString = " INSERT INTO CSMS_ACCOUNTBALANCE_REPORT (ID,generatetime,customername,IDTYPE,IDCODE, SECONDNO, SECONDNAME, STATE,BALANCE,PREFERENTIALBALANCE,AVAILABLEBALANCE,FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,BAILFEE,BAILFROZENBALANCE) "
					+ " SELECT SEQ_CSMSACCOUNTBALANCEREPORT.nextval as id,to_DATE(?, 'yyyy-mm-dd') as REPORTDATE,ORGAN,IDTYPE,IDCODE,SECONDNO,SECONDNAME,STATE,BALANCE,PREFERENTIALBALANCE,AVAILABLEBALANCE,FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,BAILFEE,BAILFROZENBALANCE "
				    + " FROM (SELECT D1.ORGAN, D1.IDTYPE, D1.IDCODE,D1.SECONDNO, D1.SECONDNAME, D1.STATE,SUM(D2.BALANCE) AS BALANCE, SUM(D2.PREFERENTIALBALANCE) AS PREFERENTIALBALANCE,SUM(D2.AVAILABLEBALANCE) AS AVAILABLEBALANCE, SUM(D2.FROZENBALANCE) AS FROZENBALANCE, SUM(D2.AVAILABLEREFUNDBALANCE) AS AVAILABLEREFUNDBALANCE, SUM(D2.REFUNDAPPROVEBALANCE) AS REFUNDAPPROVEBALANCE, SUM(T.BAILFEE) AS BAILFEE, SUM(T.BAILFROZENBALANCE) AS BAILFROZENBALANCE  FROM CSMS_BAILACCOUNT_INFO T LEFT JOIN CSMS_CUSTOMER D1 ON T.MAINID = D1.ID " 
				 + " AND D1.STATE in ('1', '2')  LEFT JOIN CSMS_MAINACCOUNT_INFO D2 ON T.MAINID = D2.MAINID GROUP BY D1.ORGAN,D1.IDTYPE,D1.IDCODE,D1.SECONDNO,D1.SECONDNAME,D1.STATE) TMP ";
			save(sqlString,date);
	}
	
	/**
	 * 生成银行转账余额报表数据
	 */
	public void generateBankTransferBalanceReportData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		String sqlString = "INSERT INTO CSMS_BANK_TRANSFER_BALANCE (ID,GENERATETIME,BANK_ACCOUNT_NAME,BANK_NO,TRANSFER_BALANCE,BALANCE,ARRIVAL_TIME,ACCOUNT_TIME)"
				+ " SELECT SEQ_CSMSBANKTRANSFERBALANCE.nextval, to_date('"
				+ date
				+ "', 'yyyy-mm-dd hh24:mi:ss'), c.PayName, c.BankNo, c.TransferBlanace,"
				+ " c.Blanace, c.ArrivalTime, c.accountTime FROM CSMS_BankTransfer_Info c where c.Blanace > 0"; // 余额  大于  0
		save(sqlString);
	}
	
	/**
	 * 生成电子标签提货金额余额报表数据
	 */
	public void generateOBUPickUPBalanceReportData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		String sqlString = "INSERT INTO CSMS_OBU_PICKUP_BALANCE_REPORT (ID,GENERATE_TIME,ID_TYPE,ID_NO,CUSTOMER_NAME,TAKE_BALANCE)"
				+ " SELECT SEQ_CSMSOBUPICKUPBALANCEREPORT.nextval, TO_DATE ('" + date + "', 'yyyy-mm-dd hh24:mi:ss'), c.CertType, c.CertNumber,"
				+ "c.ClientName, c.TakeBalance FROM CSMS_TagTakeFee_Info c WHERE c.TAKEBALANCE > 0"; // 余额  大于  0
		save(sqlString);
	}

	public void generateAccountBalanceSumReportData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		 String sqlString =" INSERT INTO CSMS_ACCOUNTBALANCESUM_REPORT "
				+" (ID,GENERATETIME,OBJECTNAME,AVAILABLEBALANCE,FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,BAILFEE,BAILFROZENBALANCE) "
				 +" SELECT SEQ_CSMSACCOUNTBALANCESUM.NEXTVAL AS ID,TO_DATE(?, 'YYYY-MM-DD') AS REPORTDATE, '联合电服',AVAILABLEBALANCE,FROZENBALANCE,AVAILABLEREFUNDBALANCE,REFUNDAPPROVEBALANCE,BAILFEE,BAILFROZENBALANCE "
				 +" FROM (SELECT T.GENERATETIME,SUM(T.AVAILABLEBALANCE) AS AVAILABLEBALANCE,SUM(T.FROZENBALANCE) AS FROZENBALANCE,SUM(T.AVAILABLEREFUNDBALANCE) AS AVAILABLEREFUNDBALANCE, SUM(T.REFUNDAPPROVEBALANCE) AS REFUNDAPPROVEBALANCE,SUM(T.BAILFEE) AS BAILFEE, SUM(T.BAILFROZENBALANCE) AS BAILFROZENBALANCE  FROM CSMS_ACCOUNTBALANCE_REPORT T  WHERE T.GENERATETIME = TO_DATE(?, 'YYYY-MM-DD') GROUP BY T.GENERATETIME) TMP WHERE TMP.GENERATETIME IS NOT NULL ";
		 save(sqlString,date,date);
		
	}

	public void insertPrepaidSum(String startmonth) {
		String sqlString = "call INSERT_CSMS_AGENCYSCADD_REPORT (?)";
		save(sqlString,startmonth);		
	}

	public void insertPrePayTotal(String time) {
		String sqlString = "call INSERT_CSMS_PREPAYTOTAL_REPORT (?)";
		save(sqlString,time);	
	}

	public void insertStockGBCardDetail(String time) {
		String sqlString = "call INSERT_StockGBCardREPORT (?)";
		save(sqlString,time);
	}

	public void insertCancelCardDetail(String time) {
		String sqlString = "call INSERT_CancelGBCardREPORT (?)";
		save(sqlString,time);
	}
	
}
