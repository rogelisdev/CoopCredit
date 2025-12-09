package com.coopcredit.credit.application_service.application.dto;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfilliateResponse {

        private Long id;
        private String document;
        private String firstName;
        private String lastname;
        private String email;
        private BigDecimal salary;
        private AfilliateStatus status;
        private LocalDate registrationDate;

        // Helper para nombre completo
        public String getFullName() {
            return firstName + " " + lastname;
        }
}
