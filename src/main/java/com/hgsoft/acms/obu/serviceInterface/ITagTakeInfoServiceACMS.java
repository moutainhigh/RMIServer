package com.hgsoft.acms.obu.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.utils.Pager;

public interface ITagTakeInfoServiceACMS {
	public TagTakeInfo tagTakeInfoDetail(Long id);
	public Map<String, Object> saveTagTakeInfo(TagTakeInfo tagTakeInfo,TagTakeFeeInfo tagTakeFeeInfo,Long productInfoId);
	public String deleteTagTakeInfo(Long id,TagTakeInfo temp,String flagStr,Long productInfo);
	public Pager tagTakeInfoList(Pager pager,TagTakeInfo tagTakeInfo,String tagNo,String operName,Date starTime,Date endTime);
	public List tagTakeInfoDetailListByMainID(Long mainId);
	public List tagTakeInfoListByTakeBalance(TagTakeInfo tagTakeInfo);
	public boolean checkTakeBalance(TagTakeFeeInfo tagTakeFeeInfo,TagTakeInfo tagTakeInfo);
	public boolean sureDelete(Long tagTakeInfoId);
	public Map<String, Object> checkInventory(TagTakeInfo tagTakeInfo);
	/**
	 * 根据标签号段，获取产品来源
	 * @param startCode
	 * @param endCode
	 * @return String 若不同来源返回""
	 */
	public String getSourceType(String startCode,String endCode);
}
