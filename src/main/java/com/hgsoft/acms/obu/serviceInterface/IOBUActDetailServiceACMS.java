package com.hgsoft.acms.obu.serviceInterface;

import java.util.List;

import com.hgsoft.obu.entity.OBUActDetail;

public interface IOBUActDetailServiceACMS {

	public OBUActDetail findById(OBUActDetail obuActDetail);
	public List<OBUActDetail> findList(OBUActDetail obuActDetail);
}
