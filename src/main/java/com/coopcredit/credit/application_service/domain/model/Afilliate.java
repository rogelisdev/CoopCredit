package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Afilliate {
    private Long id;
    private String firtsName;
    private String lastname;
    private String email;
    private BigDecimal salary;
    private AfilliateStatus status;
    private LocalDate registrationDate;

    public Afilliate(Long id, String firtsName, String lastname, String email, BigDecimal salary, AfilliateStatus status, LocalDate registrationDate) {
        this.id = id;
        this.firtsName = firtsName;
        this.lastname = lastname;
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

    public String getFirtsName() {
        return firtsName;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
