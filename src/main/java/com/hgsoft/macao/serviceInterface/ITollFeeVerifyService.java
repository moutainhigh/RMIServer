package com.hgsoft.macao.serviceInterface;

import java.math.BigDecimal;

import com.hgsoft.macao.entity.MacaoPassageDetail;
import com.hgsoft.utils.Pager;

public interface ITollFeeVerifyService {

	public Pager findTollFeeList(Pager pager,MacaoPassageDetail macaoPassageDetail);
	public BigDecimal getTotalTollFee(MacaoPassageDetail macaoPassageDetail);
	public void batchSaveTollFee(MacaoPassageDetail macaoPassageDetail);
}
