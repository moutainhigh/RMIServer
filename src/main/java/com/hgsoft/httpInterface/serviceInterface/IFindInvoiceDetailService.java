package com.hgsoft.httpInterface.serviceInterface;

import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2016/11/30.
 */
public interface IFindInvoiceDetailService {
    public List<Map<String,Object>> findInvoiceTitleList(String cardno);
}
