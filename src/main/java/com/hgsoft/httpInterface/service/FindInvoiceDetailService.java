package com.hgsoft.httpInterface.service;

import com.hgsoft.invoice.dao.InvoiceDetailDao;
import com.hgsoft.httpInterface.serviceInterface.IFindInvoiceDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2016/11/30.
 */
@Service
public class FindInvoiceDetailService implements IFindInvoiceDetailService {
    @Resource
    private InvoiceDetailDao invoiceDetailDao;

    @Override
    public List<Map<String, Object>> findInvoiceTitleList(String cardno) {
        return invoiceDetailDao.findInvoiceTitleList(cardno);
    }
}
