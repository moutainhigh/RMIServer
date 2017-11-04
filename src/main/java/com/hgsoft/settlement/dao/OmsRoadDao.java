package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.OmsBaseDao;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OmsRoadDao extends OmsBaseDao {

    /*public List<Road> findAllFromOms() {
        String sql = " select ID,provinceno,areano,areaname,areaid,roadno,roadname,roadfullname,roadservername,roadserverip,roaddomain,roaddbname,roaddbusername,roaddbpassword,freeposition,ownercode,gentime,status,operateid,operatename,checkid,checkname,checktime,checkflag,operateflag,gencheckpassid " +
                " from tb_road WHERE CHECKFLAG='2' AND OPERATEFLAG='3'";
        return super.queryObjectList(sql, Road.class);
    }*/

    public List<Object[]> findAllObjs() {
        final List<Object[]> roadsObjs = new ArrayList<Object[]>();

        final String sql = " select ID,provinceno,areano,areaname,areaid, roadno,roadname,roadfullname,roadservername,roadserverip, roaddomain,roaddbname,roaddbusername,roaddbpassword,freeposition,ownercode,gentime,status,operateid,operatename,checkid,checkname,checktime,checkflag,operateflag,gencheckpassid " +
                " from oms_road WHERE CHECKFLAG='2' AND OPERATEFLAG='3' order by id";

        super.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Object[] objs = {resultSet.getObject(1), resultSet.getObject(2),resultSet.getObject(3), resultSet.getObject(4), resultSet.getObject(5),
                        resultSet.getObject(6), resultSet.getObject(7), resultSet.getObject(8), resultSet.getObject(9), resultSet.getObject(10),
                        resultSet.getObject(11), resultSet.getObject(12), resultSet.getObject(13), resultSet.getObject(14), resultSet.getObject(15),
                        resultSet.getObject(16), resultSet.getObject(17), resultSet.getObject(18), resultSet.getObject(19), resultSet.getObject(20),
                        resultSet.getObject(21), resultSet.getObject(22),resultSet.getObject(23), resultSet.getObject(24), resultSet.getObject(25),
                        resultSet.getObject(26)
                };
                roadsObjs.add(objs);
            }
        });
        return roadsObjs;
    }
}
