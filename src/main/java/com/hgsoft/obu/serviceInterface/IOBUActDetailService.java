package com.hgsoft.obu.serviceInterface;

import java.util.List;

import com.hgsoft.obu.entity.OBUActDetail;

public interface IOBUActDetailService {

	public OBUActDetail findById(OBUActDetail obuActDetail);
	public List<OBUActDetail> findList(OBUActDetail obuActDetail);
	public List<OBUActDetail> findListByMainId(OBUActDetail obuActDetail);
}
