package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateAfilliateUseCase Tests")
class CreateAfilliateUseCaseImplTest {

    @Mock
    private AfilliateRepositoryPort repository;

    @InjectMocks
    private CreateAfilliateUseCaseImpl useCase;

    private Afilliate validAfilliate;

    @BeforeEach
    void setUp() {
        validAfilliate = Afilliate.builder()
                .firstName("John")
                .lastname("Doe")
                .document("123456789")
                .email("john@example.com")
                .salary(new BigDecimal("5000.00"))
                .build();
    }

    @Test
    @DisplayName("Should create affiliate successfully with valid data")
    void shouldCreateAffiliateSuccessfully() {
        // Arrange
        when(repository.existsByDocument(anyString())).thenReturn(false);
        when(repository.create(any(Afilliate.class))).thenAnswer(invocation -> {
            Afilliate aff = invocation.getArgument(0);
            aff.setId(1L);
            return aff;
        });

        // Act
        Afilliate result = useCase.create(validAfilliate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(AfilliateStatus.ACTIVE);
        assertThat(result.getRegistrationDate()).isEqualTo(LocalDate.now());

        verify(repository).existsByDocument("123456789");
        verify(repository).create(any(Afilliate.class));
    }

    @Test
    @DisplayName("Should throw exception when salary is zero")
    void shouldThrowExceptionWhenSalaryIsZero() {
        // Arrange
        validAfilliate.setSalary(BigDecimal.ZERO);

        // Act & Assert
        assertThatThrownBy(() -> useCase.create(validAfilliate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Salary must be greater than zero");

        verify(repository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when salary is negative")
    void shouldThrowExceptionWhenSalaryIsNegative() {
        // Arrange
        validAfilliate.setSalary(new BigDecimal("-1000"));

        // Act & Assert
        assertThatThrownBy(() -> useCase.create(validAfilliate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Salary must be greater than zero");

        verify(repository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when document number already exists")
    void shouldThrowExceptionWhenDocumentDuplicate() {
        // Arrange
        when(repository.existsByDocument("123456789")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> useCase.create(validAfilliate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Document number already exists");

        verify(repository).existsByDocument("123456789");
        verify(repository, never()).create(any());
    }

    @Test
    @DisplayName("Should set default status to ACTIVE when not provided")
    void shouldSetDefaultStatusToActive() {
        // Arrange
        validAfilliate.setStatus(null);
        when(repository.existsByDocument(anyString())).thenReturn(false);
        when(repository.create(any(Afilliate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Afilliate result = useCase.create(validAfilliate);

        // Assert
        assertThat(result.getStatus()).isEqualTo(AfilliateStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should set registration date to today when not provided")
    void shouldSetRegistrationDateToToday() {
        // Arrange
        validAfilliate.setRegistrationDate(null);
        when(repository.existsByDocument(anyString())).thenReturn(false);
        when(repository.create(any(Afilliate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Afilliate result = useCase.create(validAfilliate);

        // Assert
        assertThat(result.getRegistrationDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Should preserve provided status when valid")
    void shouldPreserveProvidedStatus() {
        // Arrange
        validAfilliate.setStatus(AfilliateStatus.INACTIVE);
        when(repository.existsByDocument(anyString())).thenReturn(false);
        when(repository.create(any(Afilliate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Afilliate result = useCase.create(validAfilliate);

        // Assert
        assertThat(result.getStatus()).isEqualTo(AfilliateStatus.INACTIVE);
    }
}
