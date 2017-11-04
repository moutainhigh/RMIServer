package com.hgsoft.timerTask.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.timerTask.dao.ReportForInsertDao;
import com.hgsoft.timerTask.dao.TagsellIssueTotalDao;
import com.hgsoft.timerTask.serviceinterface.IReportTimerTask;
import com.hgsoft.utils.DateUtil;

/**
 * Created by apple on 2017/1/12.
 */
@Service
public class ReportTimerTask implements IReportTimerTask /*,Serializable*/{

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ReportTimerTask.class.getName());

    @Resource
    private TagsellIssueTotalDao tagsellIssueTotalDao;
    
    @Resource
    private ReportForInsertDao reportForInsertDao;

    @Override
    public synchronized void addTagsellIssueTotal() {
        tagsellIssueTotalDao.addTagsellIssueTotal();
    }

    @Override
    public void addEtcCardSaleCount() {
        this.tagsellIssueTotalDao.addEtcCardSaleCount();
    }
    /*
     *
     * 电子标签销售与发行明细
     * */
    public void electronicTagSellIssueDetail(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	System.out.println("-------"+time+"---------");
    	logger.info("电子标签销售与发行明细开始");
    	tagsellIssueTotalDao.queryInsertDetailByDate();
    	logger.info("电子标签销售与发行明细结束");
    }
    
    /*
     * 
     * 缴款单余额报表
     * */
    public void paymentBalanceReport(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	System.out.println("-------"+time+"---------"); 
    	logger.info("缴款单余额报表开始");
    	tagsellIssueTotalDao.queryPaymentBalanceReport();
    	logger.info("缴款单余额报表结束");
    }
    /*
     * 
     * 资金业务流水汇总报表jobServiceMoneyWaterReport
     * */
    public void serviceMoneyWaterReport(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	System.out.println("-------"+time+"---------"); 
    	logger.info("资金业务流水汇总报表开始");
    	tagsellIssueTotalDao.queryServiceMoneyWaterReport();
    	logger.info("资金业务流水汇总报表结束");
    }
    
    /**
	 * 生成账户余额报表数据
	 */
    public void generateAccountBalanceReportData(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	System.out.println("-------"+time+"---------"); 
    	logger.info("账户余额报表数据开始");
    	reportForInsertDao.generateAccountBalanceReportData();
    	reportForInsertDao.generateAccountBalanceSumReportData();
    	logger.info("账户余额报表数据结束");
    }
    
    /**
	 * 生成银行转账余额报表数据
	 */
    public void generateBankTransferBalanceReportData(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	System.out.println("-------"+time+"---------"); 
    	logger.info("银行转账余额报表开始");
    	reportForInsertDao.generateBankTransferBalanceReportData();
    	logger.info("银行转账余额报表结束");
    }
    
    /**
	 * 生成电子标签提货金额余额报表数据
	 */
    public void generateOBUPickUPBalanceReportData(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	System.out.println("-------"+time+"---------"); 
    	logger.info("电子标签提货金额余额报表报表开始");
    	reportForInsertDao.generateOBUPickUPBalanceReportData();
    	logger.info("电子标签提货金额余额报表结束");
    }
    
    /**
 	 * 生成资金账户余额报表数据
 	 */
     public void insertAccountBalanceData(){
     	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	Date date = new Date();
     	String time = format.format(date);
     	logger.info(time+"生成资金账户余额报表数据开始");
     //	reportForInsertDao.insertAccountBalanceData();
     	logger.info(time+"生成资金账户余额报表数据结束");
     }
     
     /**
  	 * 代理充值统计(月结报表)
  	 */
      public void insertPrepaidSum(){
      	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      	Date date = new Date();
      	String time = format.format(date);
      	String startmonth = DateUtil.dateFormat(date, -1);
      	logger.info(time+"生成代理充值统计(月结报表)开始");
      	reportForInsertDao.insertPrepaidSum(startmonth);
      	logger.info(time+"生成代理充值统计(月结报表)结束");
      }
      
      /**
    	 * 联合电服预交款汇总报表
    	 */
    public void insertPrePayTotal(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	String time = format.format(date);
    	logger.info(time+"生成代理充值明细(月结报表)开始");
    	reportForInsertDao.insertPrePayTotal(time);
    	logger.info(time+"生成代理充值明细(月结报表)结束");
    }
    
    /**
	 * 存量国标卡片分类明细表
	 */
	public void insertStockGBCardDetail(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = format.format(date);
		logger.info(time+"存量国标卡片分类明细表开始");
		reportForInsertDao.insertStockGBCardDetail(time);
		logger.info(time+"存量国标卡片分类明细表结束");
	}
	
	 /**
	 * 注销国标卡片分类明细表
	 */
	public void insertCancelCardDetail(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = format.format(date);
		logger.info(time+"生成代理充值明细(月结报表)开始");
		reportForInsertDao.insertCancelCardDetail(time);
		logger.info(time+"生成代理充值明细(月结报表)结束");
	}
}

