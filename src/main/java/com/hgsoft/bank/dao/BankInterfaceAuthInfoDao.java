package com.hgsoft.bank.dao;

import com.hgsoft.bank.entity.BankInterfaceAuthInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 孙晓伟
 * file : BankInterfaceAuthInfoDao.java
 * date : 2017/7/27
 * time : 9:36
 */
@Repository
public class BankInterfaceAuthInfoDao extends BaseDao {
    public List<BankInterfaceAuthInfo> findBankAuthInfo(String bankCode, String transactionCode, String businessCode, String businessType) {
        StringBuffer sql = new StringBuffer("select id,bankCode,transactionCode,businessCode,businessType,enableFlag from CSMS_BANKINTERFACE_AUTHINFO where enableFlag = '1' ");
        SqlParamer params=new SqlParamer();
        if(StringUtil.isNotBlank(bankCode)){
            params.eq("bankCode", bankCode);
        }
        if(StringUtil.isNotBlank(transactionCode)){
            params.eq("transactionCode", transactionCode);
        }
        if(StringUtil.isNotBlank(businessCode)){
            params.eq("businessCode", businessCode);
        }
        if(StringUtil.isNotBlank(businessType)){
            params.eq("businessType", businessType);
        }

        sql.append(params.getParam());
        List list = params.getList();
        Object[] Objects= list.toArray();

        List<Map<String, Object>> mapList = queryList(sql.toString(),Objects);


        BankInterfaceAuthInfo bankInterfaceAuthInfo = null;
        List<BankInterfaceAuthInfo> bankInterfaceAuthInfos = new ArrayList<BankInterfaceAuthInfo>();
        if (list!=null&&!list.isEmpty()) {
            for (Map<String, Object> c : mapList) {
                bankInterfaceAuthInfo = new BankInterfaceAuthInfo();
                this.convert2Bean(c, bankInterfaceAuthInfo);

                bankInterfaceAuthInfos.add(bankInterfaceAuthInfo);

            }
        }

        return bankInterfaceAuthInfos;

    }
}
