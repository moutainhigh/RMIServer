package com.hgsoft.daysettle.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetDetailDao;
import com.hgsoft.daysettle.entity.DaySetDetail;
import com.hgsoft.daysettle.entity.DaySetRecord;
import com.hgsoft.daysettle.serviceInterface.IDaySetDetailService;
import com.hgsoft.utils.Pager;

@Service
public class DaySetDetailService implements IDaySetDetailService{
	@Resource
	private DaySetDetailDao  daySetDetailDao;
}
