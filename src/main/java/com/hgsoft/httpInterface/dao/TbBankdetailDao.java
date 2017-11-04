package com.hgsoft.httpInterface.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.TbBankdetail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by FZH on 2017/7/5.
 */
@Repository
public class TbBankdetailDao extends BaseDao {

    private static Logger logger = Logger.getLogger(TbBankdetailDao.class.getName());

    public List<TbBankdetail> listTbBankdetail(TbBankdetail tbBankdetail){
        List<TbBankdetail> tbBankdetailsList= new ArrayList<TbBankdetail>();
        if(tbBankdetail!=null){
            Map map = FieldUtil.getPreFieldMap(TbBankdetail.class,tbBankdetail);
            StringBuffer sql = new StringBuffer( "select ");
            sql.append(map.get("nameStr"));
            sql.append(" from tb_bankdetail where 1=1 ");
            sql.append(map.get("selectNameStrNotNullAndWhere"));
            logger.debug("sql:"+sql);
            List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
            if(list!=null&&list.size()>0){
                for(Map<String, Object> m:list){
                    TbBankdetail temp = new TbBankdetail();
                    this.convert2Bean(m, temp);
                    tbBankdetailsList.add(temp);
                }
            }

        }
        return tbBankdetailsList;
    }

}
