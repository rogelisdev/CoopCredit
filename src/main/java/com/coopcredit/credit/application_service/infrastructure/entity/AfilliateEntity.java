package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "afilliate")
@Getter
@Setter
public class AfilliateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afilliate_id")
    private Long id;

    @Column(nullable = true)
    private String firtsName;

    @Column(nullable = true)
    private String lastname;

    private String email;

    private BigDecimal salary;

    private AfilliateStatus status;
    private LocalDate registrationDate;

}
