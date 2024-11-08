package com.coppel.evaluacion.currencyapi.services;

import com.coppel.evaluacion.currencyapi.entities.Currency;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequest;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRepository;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRequestRepository;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRequestValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyRequestValueServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyRequestRepository currencyRequestRepository;

    @Mock
    private CurrencyRequestValueRepository currencyRequestValueRepository;

    @InjectMocks
    private CurrencyRequestValueService currencyRequestValueService;

    private Currency currency1;
    private Currency currency2;
    private CurrencyRequest currencyRequest;
    private CurrencyRequestValue currencyRequestValue1;
    private CurrencyRequestValue currencyRequestValue2;
    private List<CurrencyRequestValue> requestValues1;
    private List<CurrencyRequestValue> requestValues2;

    @BeforeEach
    void setUp() {
        // Creamos instancias de tipo Currency para las pruebas
        currency1 = new Currency(1L, "MXN", "");
        currency2 = new Currency(2L, "USD", "");

        // Creamos instancia de tipo currencyRequest para las pruebas
        currencyRequest = new CurrencyRequest();
        currencyRequest.setCreatedAt(LocalDateTime.now());
        currencyRequest.setStatus("COMPLETED");

        // Creamos instancias de tipo CurrencyRequestValue para las pruebas
        currencyRequestValue1 = new CurrencyRequestValue();
        currencyRequestValue1.setCurrency(currency1);
        currencyRequestValue1.setCurrencyRequest(currencyRequest);
        currencyRequestValue1.setValue(BigDecimal.valueOf(19.45));

        currencyRequestValue2 = new CurrencyRequestValue();
        currencyRequestValue2.setCurrency(currency1);
        currencyRequestValue2.setCurrencyRequest(currencyRequest);
        currencyRequestValue2.setValue(BigDecimal.valueOf(1));

        // Lista con 1 elemento
        requestValues1 = Collections.singletonList(currencyRequestValue1);

        // Lista con 2 elementos
        requestValues2 = Arrays.asList(currencyRequestValue1, currencyRequestValue2);

    }

    @Test
    void getCurrencyRequestValues_ValidResponseAllCurrencies() {

        String currencyCode = "all";
        LocalDateTime startDate = LocalDateTime.parse("2024-11-06T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endDate = LocalDateTime.parse("2024-11-08T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        Pageable pageable = PageRequest.of(0, 10); // Página 0, tamaño 10

        Page<CurrencyRequestValue> pageResult = new PageImpl<>(requestValues2, pageable, requestValues2.size());

        // Cuando en el proceso se intente consultar a la db devolvemos pagina con 2 resultados
        when(currencyRequestValueRepository.findByCurrencyRequest_CreatedAtBetween(startDate, endDate, pageable)).thenReturn(pageResult);

        // Mandamos llamar funcionalidad a validar
        Page <CurrencyRequestValue> result = currencyRequestValueService.getCurrencyRequestValues(currencyCode, "2024-11-06T23:59:59", "2024-11-08T23:59:59", 0, 10);

        // Validamos que no sea null y que tenga dos elementos
        assertNotNull(result, "El resultado no debe ser nulo.");
        assertFalse(result.isEmpty(), "El resultado no debe estar vacío.");
        assertEquals(2, result.getContent().size(), "Debe haber 2 resultados.");
    }

    @Test
    void getCurrencyRequestValues_ValidResponseOneCurrency() {
        // Cuando en el proceso se intente consultar a la db devolvemos resultado definido previamente
        when(currencyRepository.findByCode("MXN")).thenReturn(currency1);

        LocalDateTime startDate = LocalDateTime.parse("2024-11-06T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endDate = LocalDateTime.parse("2024-11-08T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        Pageable pageable = PageRequest.of(0, 10); // Página 0, tamaño 10

        Page<CurrencyRequestValue> pageResult = new PageImpl<>(requestValues1, pageable, requestValues1.size());

        // Cuando en el proceso se intente consultar a la db devolvemos pagina con 1 resultados
        when(currencyRequestValueRepository.findByCurrency_CodeAndCurrencyRequest_CreatedAtBetween(
                "MXN", startDate, endDate, pageable))
                .thenReturn(pageResult);

        String currencyCode = "MXN";
        Page<CurrencyRequestValue> result = currencyRequestValueService.getCurrencyRequestValues(currencyCode, "2024-11-06T23:59:59", "2024-11-08T23:59:59", 0, 10);

        // Validamos que no sea null y que tenga 1 elemento
        assertNotNull(result, "El resultado no debe ser nulo.");
        assertFalse(result.isEmpty(), "El resultado no debe estar vacío.");
        assertEquals(1, result.getContent().size(), "Debe haber 1 resultado.");

    }

    @Test
    void getCurrencyRequestValues_InvalidCurrencyCodeLength() {
        String currencyCode = "US"; // Solo 2 caracteres
        String finit = "2024-11-06T23:59:59";
        String fend = "2024-11-08T23:59:59";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyRequestValueService.getCurrencyRequestValues(currencyCode, finit, fend, 0, 10);
        });

        // Validamos que devuelva excepcion
        assertEquals("La clave de divisa debe ser de 3 caracteres", exception.getMessage());
    }

    @Test
    void getCurrencyRequestValues_CurrencyCodeNotFound() {
        String currencyCode = "EUR";
        String finit = "2024-11-06T23:59:59";
        String fend = "2024-11-08T23:59:59";

        when(currencyRepository.findByCode(currencyCode)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyRequestValueService.getCurrencyRequestValues(currencyCode, finit, fend, 0, 10);
        });

        // Validamos que devuelva excepcion
        assertEquals("No se encontro la clave de divisa en el catalogo.", exception.getMessage());

    }

    @Test
    void getCurrencyRequestValues_InvalidDateFormat() {
        String currencyCode = "all";
        String finit = "2024-11-06T00-00-00";
        String fend = "2024-11-07T00-00-00";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyRequestValueService.getCurrencyRequestValues(currencyCode, finit, fend, 0, 10);
        });

        // Validamos que devuelva excepcion
        assertEquals("Formato de fecha invalido. Use 'YYYY-MM-DDThh:mm:ss'.", exception.getMessage());
    }



}