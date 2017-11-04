package com.hgsoft.clearInterface.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.serviceInterface.IClearInvoiceInfoService;
import com.hgsoft.invoice.dao.InvoiceDetailDao;
import com.hgsoft.utils.Pager;
@Service
public class ClearInvoiceInfoService implements IClearInvoiceInfoService {
	/*@Resource
	private ScInvoiceDao scInvoiceDao;
	@Resource
	private ScInvoiceDetailsDao scInvoiceDetailsDao;
	@Resource
	private AcInvoiceDao acInvoiceDao;
	@Resource
	private AcInvoiceDetailsDao acInvoiceDetailsDao;*/
	@Resource
	private InvoiceDetailDao invoiceDetailDao;
	
	/*public Pager findScInvoiceByPage(Pager pager,String cardNo,Date starTime,Date endTime){
		//return scInvoiceDao.findByPage(pager, cardNo, starTime, endTime);
		return getValidInvoice(scInvoiceDao.findByPage(pager, cardNo, starTime, endTime), cardNo, starTime, endTime);
	}
	public Map<String, Object> findScInvoice(Long reckonlistno){
		return scInvoiceDao.find(reckonlistno);
	}
	public List<Map<String, Object>> findScInvoiceAll(List<Long> reckonlistnos){
		return scInvoiceDao.findAll(reckonlistnos);
	}
	public List<Map<String, Object>> findScInvoiceDetail(Long reckonlistno){
		return scInvoiceDetailsDao.findAll(reckonlistno);
	}
	
	
	public Pager findAcInvoiceByPage(Pager pager,String cardNo,Date starTime,Date endTime){
		//return acInvoiceDao.findByPage(pager, cardNo, starTime, endTime);
		return getValidInvoice(acInvoiceDao.findByPage(pager, cardNo, starTime, endTime), cardNo, starTime, endTime);
	}
	public Map<String, Object> findAcInvoice(Long reckonlistno){
		return acInvoiceDao.find(reckonlistno);
	}
	public  List<Map<String, Object>> findAcInvoiceAll(List<Long> reckonlistnos){
		return acInvoiceDao.findAll(reckonlistnos);
	}
	public List<Map<String, Object>> findAcInvoiceDetail(Long reckonlistno){
		return acInvoiceDetailsDao.findAll(reckonlistno);
	}*/
	
	private Pager getValidInvoice(Pager pager,String cardNo,Date starTime,Date endTime){
		 List<Map<String, Object>> l=invoiceDetailDao.findInvoice(cardNo, starTime, endTime);
		 List<Map<String, Object>> list=pager.getResultList();
		 if(l==null || l.size()==0){
			 return pager;
		 }
		 Map<String, Object> map=null;
		 Map<String, Object> m=null;
		 for(int i=0;i<list.size();i++){
			 map=list.get(i);
			 map.put("recState", "0");
			 for(int j=0;j<l.size();j++){
				m=l.get(j);
				if(map.get("RECKONLISTNO").toString().equals(m.get("RECKONLISTNO").toString())){
					map.put("recState", "1");
				}
			 }
		 }
		return pager;
	}

}
