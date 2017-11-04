package com.hgsoft.acms.other.serviceInterface;

import java.util.Map;

import com.hgsoft.other.entity.TestCard;
import com.hgsoft.utils.Pager;

public interface ITestCardServiceACMS {

	Pager findByPager(Pager pager, TestCard testCard);

	Map<String, Object> save(TestCard testCard);

	TestCard findById(Long id);

	Map<String, Object> update(TestCard oldTestCard, TestCard testCard);

}
