package com.hgsoft.httpInterface.serviceInterface;

import com.hgsoft.httpInterface.entity.BusinessAccredit;
import com.hgsoft.httpInterface.entity.BusinessAccreditAdmin;
import com.hgsoft.utils.Pager;

import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2016/12/12.
 */
public interface IAuthorizationService {
    public Map<String, Object> saveBusinessAccredit(BusinessAccredit businessAccredit);
    public Map<String, Object> deleteBusinessAccredit(Long id);
    public Map<String, Object> deleteBusinessAccreditAdmin(Long id);
    public Pager findBusinessAccreditList(Pager pager,String name);
    public Map<String, Object> modifyBusinessAccredit(BusinessAccredit businessAccredit);
    public Map<String, Object> switchBusinessAccredit(BusinessAccredit businessAccredit);
    public Map<String, Object> saveBusinessAccreditAdmin(BusinessAccreditAdmin businessAccreditAdmin);
    public Map<String, Object> findBusinessAccredit(Long id);
    public Pager findBusinessAccreditAdminList(Pager pager,Long id);
}
