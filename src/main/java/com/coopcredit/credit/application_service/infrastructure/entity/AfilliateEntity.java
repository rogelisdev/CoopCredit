package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "affiliate", indexes = {
        @Index(name = "idx_affiliate_document", columnList = "document")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfilliateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "affiliate_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String document;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastname;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AfilliateStatus status;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    // Relationship: Affiliate 1..* CreditApplication
    @OneToMany(mappedBy = "affiliate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<CreditApplicationEntity> creditApplications = new ArrayList<>();

    // Relationship: Affiliate 1..1 User (optional, for ROLE_AFILIADO users)
    @OneToOne(mappedBy = "affiliate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private UserEntity user;

    // Helper methods
    public void addCreditApplication(CreditApplicationEntity application) {
        creditApplications.add(application);
        application.setAffiliate(this);
    }

    public void removeCreditApplication(CreditApplicationEntity application) {
        creditApplications.remove(application);
        application.setAffiliate(null);
    }
}
