package com.pragma.foodcourtservice.domain.model;

public class UserModel {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String cellphone;
    private String email;
    private String role;

    public UserModel(){}

    public UserModel(Long id, String name, String lastname, String documentNumber, String cellphone, String email, String role) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.documentNumber = documentNumber;
        this.cellphone = cellphone;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
