package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class RoadDao extends BaseDao {

    /*public void saveAllFromOms() {
        String sql = "INSERT INTO tb_road " +
                " (ID,provinceno,areano,areaname,areaid,roadno,roadname,roadfullname,roadservername,roadserverip,roaddomain,roaddbname,roaddbusername,roaddbpassword,freeposition,ownercode,gentime,status,operateid,operatename,checkid,checkname,checktime,checkflag,operateflag,gencheckpassid) " +
                " select ID,provinceno,areano,areaname,areaid,roadno,roadname,roadfullname,roadservername,roadserverip,roaddomain,roaddbname,roaddbusername,roaddbpassword,freeposition,ownercode,gentime,status,operateid,operatename,checkid,checkname,checktime,checkflag,operateflag,gencheckpassid " +
                " from " + urlUtils.getOmsuser() + ".tb_road WHERE CHECKFLAG='2' AND OPERATEFLAG='3'";

        super.update(sql);
    }*/

    public void deleteAll() {
        String sql = "delete from TB_ROAD";
        super.update(sql);
    }

    public void batchSave(List<Object[]> objs) {
        String sql = "INSERT INTO tb_road " +
                " (ID,provinceno,areano,areaname,areaid, roadno,roadname,roadfullname,roadservername,roadserverip, roaddomain,roaddbname,roaddbusername,roaddbpassword,freeposition, ownercode,gentime,status,operateid,operatename, checkid,checkname,checktime,checkflag,operateflag, gencheckpassid) " +
                " values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)  ";
        int[] argsType = {Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.BIGINT,
                Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.BIGINT, Types.VARCHAR,
                Types.BIGINT, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR,
                Types.BIGINT
        };
        super.batchUpdate(sql, objs, argsType);
    }
}
