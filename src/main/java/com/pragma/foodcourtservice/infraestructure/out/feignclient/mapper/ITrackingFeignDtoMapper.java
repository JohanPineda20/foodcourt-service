package com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper;

import com.pragma.foodcourtservice.domain.model.TrackingModel;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.TrackingFeignDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITrackingFeignDtoMapper {

    TrackingFeignDto mapToTrackingFeignDto(TrackingModel trackingModel);
    List<TrackingModel> mapToTrackingModelList(List<TrackingFeignDto> trackingFeignDtoList);

}
