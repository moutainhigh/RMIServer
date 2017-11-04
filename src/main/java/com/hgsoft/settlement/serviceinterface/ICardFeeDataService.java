package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.CardFeeData;

import java.util.List;

/**
 * Created by yangzhongji on 17/10/19.
 */
public interface ICardFeeDataService {
    List<CardFeeData> listCardFeeData();

    int delete(Long id);
}
