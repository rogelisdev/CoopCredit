package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Afilliate {
    private Long id;
    private String firstName;
    private String lastname;
    private String document;
    private String email;
    private BigDecimal salary;
    private AfilliateStatus status;
    private LocalDate registrationDate;

    public Afilliate(Long id, String firstName, String lastname, String document, String email, BigDecimal salary, AfilliateStatus status, LocalDate registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.document = document;
        this.email = email;
        this.salary = salary;
        this.status = status;
        this.registrationDate = registrationDate;
    }

    public Afilliate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public AfilliateStatus getStatus() {
        return status;
    }

    public void setStatus(AfilliateStatus status) {
        this.status = status;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}