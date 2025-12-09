package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "afilliate")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfilliateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afilliate_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String document;     // Nuevo *

    private String firstName;
    private String lastname;
    private String email;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private AfilliateStatus status;

    private LocalDate registrationDate;
}


