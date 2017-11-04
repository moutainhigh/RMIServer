package com.hgsoft.unifiedInterface.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.invoice.dao.InvoiceDetailDao;
import com.hgsoft.invoice.dao.InvoicePrintDao;
import com.hgsoft.invoice.dao.PassInvoiceDao;
import com.hgsoft.invoice.dao.PassRecordDao;
import com.hgsoft.invoice.entity.InvoicePrint;
import com.hgsoft.invoice.entity.PassInvoice;
import com.hgsoft.invoice.entity.PassRecord;
import com.hgsoft.utils.SequenceUtil;
/**
 * 客户信息业务接口类
 * @author zxy
 * 2016年1月22日14:08:36
 */
@Service
public class InvoicePrintUnifiedInterfaceService{
	
	private static Logger logger = Logger.getLogger(InvoicePrintUnifiedInterfaceService.class.getName());
	
	@Resource
	private PassRecordDao passRecordDao;
	@Resource
	private PassInvoiceDao passInvoiceDao;
	@Resource
	private InvoicePrintDao invoicePrintDao;
	@Resource 
	private InvoiceDetailDao invoiceDetailDao;
	@Resource
	SequenceUtil sequenceUtil;
	
	/**
	 * 通行费发票打印
	 * @author zxy
	 */
	public  boolean savePrint(List<Map<String,Object>> list , PassInvoice passInvoice,
		InvoicePrint invoicePrint,String type) {
		try {
			//通行发票记录
			BigDecimal passInvoice_NO = sequenceUtil.getSequence("SEQ_CSMSpassinvoice_NO");
			passInvoice.setId(Long.valueOf(passInvoice_NO.toString()));
			passInvoiceDao.save(passInvoice);
			//发票打印记录
			BigDecimal invoicePrint_NO = sequenceUtil.getSequence("SEQ_CSMSinvoiceprint_NO");
			invoicePrint.setId(Long.valueOf(invoicePrint_NO.toString()));
			invoicePrint.setPassInvoiceID(passInvoice.getId());
			invoicePrintDao.save(invoicePrint);
			invoiceDetailDao.save(list, passInvoice,type);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage()+"通行费打印失败");
			e.printStackTrace();
			throw new ApplicationException("通行费打印失败");
		}
	}
	
	/**
	 * 通行费发票重打
	 * @author zxy
	 */
	public  boolean savePrintAgain(PassInvoice passInvoice,
			InvoicePrint invoicePrint, InvoicePrint oldInvoicePrint) {
		try {
			//通行发票记录
			passInvoiceDao.update(passInvoice);
			//发票打印记录
			BigDecimal invoicePrint_NO = sequenceUtil.getSequence("SEQ_CSMSinvoiceprint_NO");
			invoicePrint.setId(Long.valueOf(invoicePrint_NO.toString()));
			invoicePrintDao.save(invoicePrint);
			//旧的发票打印记录
			invoicePrintDao.update(oldInvoicePrint);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage()+"通行费打印失败");
			e.printStackTrace();
			throw new ApplicationException("通行费打印失败");
		}
	}
}
