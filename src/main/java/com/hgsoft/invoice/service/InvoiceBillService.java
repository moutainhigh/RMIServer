package com.hgsoft.invoice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.invoice.dao.InvoiceBillDao;
import com.hgsoft.invoice.entity.AddBill;
import com.hgsoft.invoice.entity.InvoiceRecord;
import com.hgsoft.invoice.entity.PassBill;
import com.hgsoft.invoice.serviceInterface.IInvoiceBillService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class InvoiceBillService implements IInvoiceBillService{
	
	private static Logger logger = Logger.getLogger(InvoiceBillService.class.getName());
	
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private InvoiceBillDao invoiceBillDao;

	@Override
	public void save(PassBill pb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pager getClearACBillByCardNo(Pager pager, String cardNo,
			String startDate, String endDate) {
		return invoiceBillDao.getClearACBillByCardNo(pager,cardNo,startDate,endDate);
	}

	@Override
	public Pager getClearACBillByBankNo(Pager pager ,String bankNo,
			String startDate, String endDate) {
		return invoiceBillDao.getClearACBillByBankNo(pager, bankNo,startDate,endDate);
	}

	@Override
	public List<Map<String, Object>> getACPassBillByBankNo(String bankNo,
			String startDate, String endDate) {
		return invoiceBillDao.getACPassBillByBankNo(bankNo,startDate,endDate);
	}

	@Override
	public List<Map<String, Object>> getACPassBillByCardNo(String cardNo,
			String startDate, String endDate) {
		return invoiceBillDao.getACPassBillByCardNo(cardNo, startDate, endDate);
	}

	@Override
	public List<PassBill> getACBillByCardno(String cardNo, String billMonth) {
		return invoiceBillDao.getACBillByCardno(cardNo ,billMonth);
	}

	@Override
	public List<Map<String, Object>> getClearACBillByBankNo(String bankNo,
			String billMonth) {
		return invoiceBillDao.getClearACBillByBankNo(bankNo ,billMonth);
	}

	@Override
	public List<PassBill> getACBillByBankno(String bankNo, String billMonth) {
		return invoiceBillDao.getACBillByBankno(bankNo ,billMonth);
	}

	@Override
	public List<Map<String, Object>> getClearACBillByCardNo(String cardNo,
			String billMonth) {
		return invoiceBillDao.getClearACBillByCardNo(cardNo ,billMonth);
	}

	@Override
	public List<Map<String, Object>> getACClearDetails(String cardNo,
			String billMonth) {
		return invoiceBillDao.getACClearDetails(cardNo ,billMonth);
	}

	@Override
	public void saveBillAndInvoice(List<PassBill> passBills,
			List<InvoiceRecord> invoiceRecords,String billType) {
		try {
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

			for (int i = 0; i < passBills.size(); i++) {
				PassBill passBill = passBills.get(i);
				
				passBill.setID(Long.valueOf(sequenceUtil.getSequence("SEQ_CSMSPASSBILL_NO").toString()));
				if (!StringUtil.isNotBlank(passBill.getCardNo())) {
					passBill.setCardNo("所有卡号");
				}
				passBill.setCardType(billType);
				passBill.setState("1");
				passBill.setPrintNum(1);
				try {
					passBill.setPassMonth(format.parse(passBill.getPassMonth().toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				InvoiceRecord invoice = invoiceRecords.get(i);
				invoice.setId(Long.valueOf(sequenceUtil.getSequence("SEQ_CSMSINVOICERECORD_NO").toString()));
				invoice.setBillType("1");
				invoice.setState("1");
				invoice.setPassBillID(passBill.getID());
				invoice.setPrintTime(new Date());
				invoiceBillDao.saveBill(passBill);
				invoiceBillDao.saveInvoice(invoice);
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"保存账单、发票失败");
			throw new ApplicationException();
		}		
		
	}

	/**
	 * 查询记帐卡发票打印类型。
	 */
	@Override
	public String checkCardPrint(String cardNo,
			String printType) {
		
		return invoiceBillDao.checkCardPrint(cardNo, printType);
	}

	@Override
	public Long findInvoiceRecrod(String invoiceNum, String invoiceCode) {
		return invoiceBillDao.findInvoiceRecrod(invoiceNum, invoiceCode);
	}

	@Override
	public void updateState(String invoiceNum,String invoiceCode, String state) {
		try {
			invoiceBillDao.updateState(invoiceNum,invoiceCode,state);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"修改发票状态失败");
			throw new ApplicationException();
		}	
	}

	@Override
	public void reprint(List<PassBill> passBills,
			List<InvoiceRecord> invoiceRecords) {
		try {
			
			for (int i = 0; i < passBills.size(); i++) {
				PassBill passBill = passBills.get(i);
				passBill.setPrintNum(passBill.getPrintNum()+1);
				passBill.setState("2");//已重打
				invoiceBillDao.update(passBill);
				
				InvoiceRecord invoice = invoiceRecords.get(i);
				invoice.setId(Long.valueOf(sequenceUtil.getSequence("SEQ_CSMSINVOICERECORD_NO").toString()));
				invoice.setBillType("1");
				invoice.setState("1");
				invoice.setPrintTime(new Date());
				invoice.setPassBillID(passBill.getID());
				invoiceBillDao.saveInvoice(invoice);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"重打账单、发票失败");
			throw new ApplicationException();
		}
		
	}

	@Override
	public List<Map<String,Object>> getACBillDetail(String flag, String cardNo,
			String billMonth) {
		return invoiceBillDao.getACBillDetail(flag,cardNo ,billMonth);
	}

	@Override
	public Pager getSCBillByCardNo(Pager pager, String cardNo,
			String startDate, String endDate) {
		return invoiceBillDao.getSCBillByCardNo(pager,cardNo ,startDate ,endDate);
	}

	@Override
	public List<Map<String, Object>> getSCPassBillByCardNo(String cardNo,
			String startDate, String endDate) {
		return invoiceBillDao.getSCPassBillByCardNo(cardNo,startDate,endDate);
	}

	@Override
	public List<Map<String, Object>> getClearSCBillByCardNo(String cardNo,
			String billMonth) {
		return invoiceBillDao.getClearSCBillByCardNo(cardNo ,billMonth);
	}

	@Override
	public List<Map<String, Object>> getSCClearDetails(String cardNo,
			String billMonth) {
		return invoiceBillDao.getSCClearDetails(cardNo,billMonth);
	}

	@Override
	public Pager findAddBillList(Pager pager,
			String cardNo, String startDate, String endDate) {
		return invoiceBillDao.findAddBillList(pager,cardNo,startDate,endDate);
	}

	@Override
	public AddBill findAddBillByID(Long id) {
		return invoiceBillDao.findAddBillByID(id);
	}

	@Override
	public void saveAddBillAndInvoice(AddBill addBill,
			InvoiceRecord invoiceRecord) {
		try {
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			addBill.setState("1");
			addBill.setPrintNum(addBill.getPrintNum()+1);
			
			invoiceRecord.setId(Long.valueOf(sequenceUtil.getSequence("SEQ_CSMSINVOICERECORD_NO").toString()));
			invoiceRecord.setBillType("2");
			invoiceRecord.setState("1");//已打印
			invoiceRecord.setAddBillID(addBill.getId());
			invoiceRecord.setPrintTime(new Date());
			invoiceBillDao.updateAddBill(addBill);
			invoiceBillDao.saveInvoice(invoiceRecord);
			
			
			
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"打印充值发票失败");
			throw new ApplicationException();
		}
	}

	@Override
	public List<Map<String, Object>> findAddBillDetail(Long id) {
		return invoiceBillDao.findAddBillDetail(id);
	}

	@Override
	public void addBillReprint(AddBill addBill, InvoiceRecord invoiceRecord) {
		try {
			addBill.setPrintNum(addBill.getPrintNum()+1);
			addBill.setState("2");//已重打
			invoiceBillDao.update(addBill);
			
			invoiceRecord.setId(Long.valueOf(sequenceUtil.getSequence("SEQ_CSMSINVOICERECORD_NO").toString()));
			invoiceRecord.setBillType("2");//充值发票
			invoiceRecord.setState("1");
			invoiceRecord.setAddBillID(addBill.getId());
			invoiceRecord.setPrintTime(new Date());
			invoiceBillDao.saveInvoice(invoiceRecord);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"重打充值发票失败");
			throw new ApplicationException();
		}
	}

	@Override
	public List<Map<String, Object>> getSCBillDetail(String cardNo,
			String billMonth) {
		return invoiceBillDao.getSCBillDetail(cardNo,billMonth);
	}


}

