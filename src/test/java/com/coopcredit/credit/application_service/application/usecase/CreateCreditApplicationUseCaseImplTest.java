package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateCreditApplicationUseCase Tests")
class CreateCreditApplicationUseCaseImplTest {

    @Mock
    private CreditApplicationRepositoryPort creditRepository;

    @Mock
    private AfilliateRepositoryPort affiliateRepository;

    @InjectMocks
    private CreateCreditApplicationUseCaseImpl useCase;

    private Afilliate activeAffiliate;
    private CreditApplication validApplication;

    @BeforeEach
    void setUp() {
        activeAffiliate = Afilliate.builder()
                .id(1L)
                .firstName("John")
                .lastname("Doe")
                .document("123456789")
                .email("john@example.com")
                .salary(new BigDecimal("5000.00"))
                .status(AfilliateStatus.ACTIVE)
                .registrationDate(LocalDate.now().minusMonths(6))
                .build();

        validApplication = CreditApplication.builder()
                .amount(new BigDecimal("10000.00"))
                .term(12)
                .afilliateId(1L)
                .build();
    }

    @Test
    @DisplayName("Should create credit application successfully with valid data")
    void shouldCreateCreditApplicationSuccessfully() {
        // Arrange
        when(affiliateRepository.getById(1L)).thenReturn(Optional.of(activeAffiliate));
        when(creditRepository.create(any(CreditApplication.class))).thenAnswer(invocation -> {
            CreditApplication app = invocation.getArgument(0);
            app.setId(1L);
            return app;
        });

        // Act
        CreditApplication result = useCase.create(validApplication);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(ApplicationStatus.PENDING);
        assertThat(result.getRequestedDate()).isNotNull();

        verify(affiliateRepository).getById(1L);
        verify(creditRepository).create(any(CreditApplication.class));
    }

    @Test
    @DisplayName("Should throw exception when affiliate not found")
    void shouldThrowExceptionWhenAffiliateNotFound() {
        // Arrange
        when(affiliateRepository.getById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.create(validApplication))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Affiliate not found");

        verify(creditRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when affiliate is inactive")
    void shouldThrowExceptionWhenAffiliateInactive() {
        // Arrange
        activeAffiliate.setStatus(AfilliateStatus.INACTIVE);
        when(affiliateRepository.getById(1L)).thenReturn(Optional.of(activeAffiliate));

        // Act & Assert
        assertThatThrownBy(() -> useCase.create(validApplication))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Affiliate must be active");

        verify(creditRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when seniority is insufficient")
    void shouldThrowExceptionWhenSeniorityInsufficient() {
        // Arrange
        activeAffiliate.setRegistrationDate(LocalDate.now().minusMonths(2)); // Only 2 months
        when(affiliateRepository.getById(1L)).thenReturn(Optional.of(activeAffiliate));

        // Act & Assert
        assertThatThrownBy(() -> useCase.create(validApplication))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least 3 months of seniority");

        verify(creditRepository, never()).create(any());
    }

    @ParameterizedTest(name = "Amount={0}, Term={1}, Salary={2}, Should Pass={3}")
    @CsvSource({
            "12000, 12, 5000, true", // 1000/month, 40% of 5000 = 2000, OK
            "24000, 12, 5000, true", // 2000/month, exactly 40%, OK
            "30000, 12, 5000, false", // 2500/month, exceeds 40% (2000), FAIL
            "6000, 12, 5000, true", // 500/month, well below 40%, OK
            "1200, 6, 1000, true" // 200/month, 20% of 1000 = 400, OK
    })
    @DisplayName("Should validate installment ratio correctly")
    void shouldValidateInstallmentRatio(String amount, int term, String salary, boolean shouldPass) {
        // Arrange
        activeAffiliate.setSalary(new BigDecimal(salary));
        validApplication.setAmount(new BigDecimal(amount));
        validApplication.setTerm(term);

        when(affiliateRepository.getById(1L)).thenReturn(Optional.of(activeAffiliate));
        if (shouldPass) {
            when(creditRepository.create(any())).thenAnswer(inv -> inv.getArgument(0));
        }

        // Act & Assert
        if (shouldPass) {
            assertThatCode(() -> useCase.create(validApplication))
                    .doesNotThrowAnyException();
        } else {
            assertThatThrownBy(() -> useCase.create(validApplication))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("40% of salary");
        }
    }

    @Test
    @DisplayName("Should set status to PENDING on creation")
    void shouldSetStatusToPending() {
        // Arrange
        when(affiliateRepository.getById(1L)).thenReturn(Optional.of(activeAffiliate));
        when(creditRepository.create(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        CreditApplication result = useCase.create(validApplication);

        // Assert
        assertThat(result.getStatus()).isEqualTo(ApplicationStatus.PENDING);
    }

    @Test
    @DisplayName("Should set requested date to current time")
    void shouldSetRequestedDateToNow() {
        // Arrange
        when(affiliateRepository.getById(1L)).thenReturn(Optional.of(activeAffiliate));
        when(creditRepository.create(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        CreditApplication result = useCase.create(validApplication);

        // Assert
        assertThat(result.getRequestedDate()).isNotNull();
        assertThat(result.getRequestedDate()).isCloseTo(
                java.time.LocalDateTime.now(),
                within(1, java.time.temporal.ChronoUnit.SECONDS));
    }
}
