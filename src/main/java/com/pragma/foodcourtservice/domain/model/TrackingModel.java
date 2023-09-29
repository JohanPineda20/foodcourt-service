package com.pragma.foodcourtservice.domain.model;

import java.time.LocalDateTime;

public class TrackingModel {
    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime datetime;
    private String statusPrevious;
    private String status;
    private Long employeeId;
    private String employeeEmail;

    public TrackingModel() {
    }

    public TrackingModel(Long orderId, Long customerId, String customerEmail, LocalDateTime datetime, String statusPrevious, String status, Long employeeId, String employeeEmail) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.datetime = datetime;
        this.statusPrevious = statusPrevious;
        this.status = status;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getStatusPrevious() {
        return statusPrevious;
    }

    public void setStatusPrevious(String statusPrevious) {
        this.statusPrevious = statusPrevious;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
