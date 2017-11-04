package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.CardFeeDataDao;
import com.hgsoft.settlement.entity.CardFeeData;
import com.hgsoft.settlement.serviceinterface.ICardFeeDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangzhongji on 17/10/19.
 */
@Service
public class CardFeeDataService implements ICardFeeDataService {
    @Resource
    private CardFeeDataDao cardFeeDataDao;

    @Override
    public List<CardFeeData> listCardFeeData() {
        return cardFeeDataDao.listCardFeeData();
    }

    @Override
    public int delete(Long id) {
        return cardFeeDataDao.delete(id);
    }
}
