package com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper;

import com.pragma.foodcourtservice.domain.model.TrackingModel;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.TrackingFeignDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-29T00:39:48-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class ITrackingFeignDtoMapperImpl implements ITrackingFeignDtoMapper {

    @Override
    public TrackingFeignDto mapToTrackingFeignDto(TrackingModel trackingModel) {
        if ( trackingModel == null ) {
            return null;
        }

        Long orderId = null;
        Long customerId = null;
        String customerEmail = null;
        LocalDateTime datetime = null;
        String statusPrevious = null;
        String status = null;
        Long employeeId = null;
        String employeeEmail = null;

        orderId = trackingModel.getOrderId();
        customerId = trackingModel.getCustomerId();
        customerEmail = trackingModel.getCustomerEmail();
        datetime = trackingModel.getDatetime();
        statusPrevious = trackingModel.getStatusPrevious();
        status = trackingModel.getStatus();
        employeeId = trackingModel.getEmployeeId();
        employeeEmail = trackingModel.getEmployeeEmail();

        TrackingFeignDto trackingFeignDto = new TrackingFeignDto( orderId, customerId, customerEmail, datetime, statusPrevious, status, employeeId, employeeEmail );

        return trackingFeignDto;
    }

    @Override
    public List<TrackingModel> mapToTrackingModelList(List<TrackingFeignDto> trackingFeignDtoList) {
        if ( trackingFeignDtoList == null ) {
            return null;
        }

        List<TrackingModel> list = new ArrayList<TrackingModel>( trackingFeignDtoList.size() );
        for ( TrackingFeignDto trackingFeignDto : trackingFeignDtoList ) {
            list.add( trackingFeignDtoToTrackingModel( trackingFeignDto ) );
        }

        return list;
    }

    protected TrackingModel trackingFeignDtoToTrackingModel(TrackingFeignDto trackingFeignDto) {
        if ( trackingFeignDto == null ) {
            return null;
        }

        TrackingModel trackingModel = new TrackingModel();

        trackingModel.setOrderId( trackingFeignDto.getOrderId() );
        trackingModel.setCustomerId( trackingFeignDto.getCustomerId() );
        trackingModel.setCustomerEmail( trackingFeignDto.getCustomerEmail() );
        trackingModel.setDatetime( trackingFeignDto.getDatetime() );
        trackingModel.setStatusPrevious( trackingFeignDto.getStatusPrevious() );
        trackingModel.setStatus( trackingFeignDto.getStatus() );
        trackingModel.setEmployeeId( trackingFeignDto.getEmployeeId() );
        trackingModel.setEmployeeEmail( trackingFeignDto.getEmployeeEmail() );

        return trackingModel;
    }
}
