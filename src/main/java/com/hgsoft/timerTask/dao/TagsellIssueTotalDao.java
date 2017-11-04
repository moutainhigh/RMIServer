package com.hgsoft.timerTask.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.DateUtil;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by apple on 2017/1/12.
 */
@Component
public class TagsellIssueTotalDao extends BaseDao {
    public void addTagsellIssueTotal(){
        System.out.println("定时任务执行*******");
    }

    public void addEtcCardSaleCount(){
        String curDate = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
        String curTime = DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
        String curYear = curDate.substring(0,4);
        super.save("call INSERT_CSMS_ETCCARDSALE_REPORT(?,?,?)",curDate,curYear,curTime);
    }


	public void queryInsertDetailByDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String sqlString = "call P_RPT_TAGBUSINESSRECORD_AREA (?)";
		save(sqlString,date);
	}


	public void queryPaymentBalanceReport() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String sqlString =  "INSERT INTO csms_PaymentBalance_report SELECT TO_DATE (?,'YYYY-MM-DD') AS OPERTIME, T .VOUCHERNO,T .BALANCE FROM CSMS_Voucher T WHERE T .BALANCE <> 0";
		save(sqlString,date);
	}

	public void queryServiceMoneyWaterReport() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String sqlString = "call P_RPT_SERVICEWATERTOTAL (?)";
		save(sqlString,date);
	}
    
}
