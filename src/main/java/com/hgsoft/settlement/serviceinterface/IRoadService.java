package com.hgsoft.settlement.serviceinterface;

import java.util.List;

public interface IRoadService {

    void deleteAll();

    List<Object[]> findAllObjsFromOms();

    void batchSave(List<Object[]> objs);
}
