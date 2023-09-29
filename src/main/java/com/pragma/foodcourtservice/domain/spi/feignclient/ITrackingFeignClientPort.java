package com.pragma.foodcourtservice.domain.spi.feignclient;

import com.pragma.foodcourtservice.domain.model.TrackingModel;

import java.util.List;

public interface ITrackingFeignClientPort {
    void trackingOrder(TrackingModel trackingModel);
    List<TrackingModel> getHistoryOrder(Long id);
}
