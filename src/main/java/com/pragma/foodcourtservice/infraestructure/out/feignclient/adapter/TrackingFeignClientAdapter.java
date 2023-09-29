package com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter;

import com.pragma.foodcourtservice.domain.model.TrackingModel;
import com.pragma.foodcourtservice.domain.spi.feignclient.ITrackingFeignClientPort;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.ITraceabilityFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.TrackingFeignDto;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper.ITrackingFeignDtoMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrackingFeignClientAdapter implements ITrackingFeignClientPort {

    private final ITraceabilityFeignClient traceabilityFeignClient;
    private final ITrackingFeignDtoMapper trackingFeignDtoMapper;

    @Override
    public void trackingOrder(TrackingModel trackingModel) {
        traceabilityFeignClient.trackingOrder(trackingFeignDtoMapper.mapToTrackingFeignDto(trackingModel));
    }
    @Override
    public List<TrackingModel> getHistoryOrder(Long id) {
        return trackingFeignDtoMapper.mapToTrackingModelList(traceabilityFeignClient.getHistoryOrder(id));
    }
}
