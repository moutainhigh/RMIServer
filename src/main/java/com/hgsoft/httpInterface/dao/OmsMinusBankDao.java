package com.hgsoft.httpInterface.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.OmsMinusBank;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by FZH on 2017/7/5.
 */
@Repository
public class OmsMinusBankDao extends BaseDao {
    private static Logger logger = Logger.getLogger(OmsMinusBankDao.class.getName());

    public List<OmsMinusBank> listOmsMinusBank(OmsMinusBank omsMinusBank){
        OmsMinusBank tb = new OmsMinusBank();
        List<OmsMinusBank> omsMinusBanksList = new ArrayList<OmsMinusBank>();
        if(omsMinusBank!=null){
            Map map = FieldUtil.getPreFieldMap(OmsMinusBank.class,omsMinusBank);
            StringBuffer sql = new StringBuffer( "select ");
            sql.append(map.get("nameStr"));
            sql.append(" from OMS_minusBank where 1=1 ");
            sql.append(map.get("selectNameStrNotNullAndWhere"));
            logger.debug("sql:"+sql);
            List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
            if(!list.isEmpty()){
                for(Map<String, Object> m:list){
                    OmsMinusBank temp = new OmsMinusBank();
                    this.convert2Bean(m, temp);
                    omsMinusBanksList.add(temp);
                }
            }
        }
        return omsMinusBanksList;
    }
    
    /**
     * group by 查询bankname,clearingbankcode
     * @param omsMinusBank
     * @return List<OmsMinusBank>
     */
    public List<OmsMinusBank> listOmsBankNameClearCode(OmsMinusBank omsMinusBank){
        OmsMinusBank tb = new OmsMinusBank();
        List<OmsMinusBank> omsMinusBanksList = new ArrayList<OmsMinusBank>();
        if(omsMinusBank!=null){
            Map map = FieldUtil.getPreFieldMap(OmsMinusBank.class,omsMinusBank);
            StringBuffer sql = new StringBuffer( "select bankname,clearingbankcode from OMS_minusBank where 1=1 ");
            sql.append(map.get("selectNameStrNotNullAndWhere"));
            sql.append(" group by bankname,clearingbankcode ");
            logger.debug("sql:"+sql);
            List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
            if(!list.isEmpty()){
                for(Map<String, Object> m:list){
                    OmsMinusBank temp = new OmsMinusBank();
                    this.convert2Bean(m, temp);
                    omsMinusBanksList.add(temp);
                }
            }
        }
        return omsMinusBanksList;
    }
}
