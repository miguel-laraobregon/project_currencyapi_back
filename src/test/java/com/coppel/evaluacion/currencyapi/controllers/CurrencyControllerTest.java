package com.coppel.evaluacion.currencyapi.controllers;

import com.coppel.evaluacion.currencyapi.entities.Currency;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequest;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;
import com.coppel.evaluacion.currencyapi.modelsDTO.CurrencyDTO;
import com.coppel.evaluacion.currencyapi.modelsDTO.CurrencyRequestValueDTO;
import com.coppel.evaluacion.currencyapi.services.CurrencyRequestValueService;
import com.coppel.evaluacion.currencyapi.services.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private CurrencyRequestValueService currencyRequestValueService;

    @InjectMocks
    private CurrencyController currencyController;

    private Currency currency1;
    private Currency currency2;
    private CurrencyRequest currencyRequest;
    private CurrencyRequestValue currencyRequestValue1;
    private CurrencyRequestValue currencyRequestValue2;

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
        currencyRequestValue1.setId(1L);
        currencyRequestValue1.setCurrency(currency1);
        currencyRequestValue1.setCurrencyRequest(currencyRequest);
        currencyRequestValue1.setValue(BigDecimal.valueOf(19.45));

        currencyRequestValue2 = new CurrencyRequestValue();
        currencyRequestValue2.setId(2L);
        currencyRequestValue2.setCurrency(currency2);
        currencyRequestValue2.setCurrencyRequest(currencyRequest);
        currencyRequestValue2.setValue(BigDecimal.valueOf(1));

    }

    @Test
    void getCurrenciesCode_ValidResponse() {
        // Lista con 2 elementos
        List<Currency> currencies = Arrays.asList(currency1, currency2);
        // Cuando en el proceso se intente consultar a la db devolvemos pagina con 2 resultados
        when(currencyService.getCurrencies()).thenReturn(currencies);

        // Mandamos llamar funcionalidad a validar
        ResponseEntity<Object> response = currencyController.getCurrenciesCode();

        // Validamos que no sea null, el estado de la respuesta sea ok y que sea de tipo List
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());

        // Validamos contenido
        List<CurrencyDTO> responseData = (List<CurrencyDTO>) response.getBody();
        assertEquals(2, responseData.size());
        assertEquals("MXN", responseData.get(0).getCode());
        assertEquals("USD", responseData.get(1).getCode());
    }

    @Test
    void getCurrenciesValues_ValidResponse() {
        String currencyCode = "all";
        int page = 0;
        int size = 10;

        // Lista con 2 elementos
        List<CurrencyRequestValue> requestValues = Arrays.asList(currencyRequestValue1, currencyRequestValue2);
        Page<CurrencyRequestValue> pageResult = new PageImpl<>(requestValues, PageRequest.of(page, size), requestValues.size());

        // Cuando en el proceso se intente consultar a la db devolvemos pagina con 2 resultados
        when(currencyRequestValueService.getCurrencyRequestValues(currencyCode, null, null, page, size)).thenReturn(pageResult);

        // Mandamos llamar funcionalidad a validar
        ResponseEntity<Object> response = currencyController.getCurrenciesValues(currencyCode,null, null, page, size);

        // Validamos que no sea null, el estado de la respuesta sea ok y que sea de tipo Page
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Page.class, response.getBody());

        // Validamos contenido
        Page<CurrencyRequestValueDTO> responseData = (Page<CurrencyRequestValueDTO>) response.getBody();
        assertEquals(2, responseData.getContent().size());
        assertEquals("MXN", responseData.getContent().get(0).getCode());
        assertEquals(BigDecimal.valueOf(19.45), responseData.getContent().get(0).getValue());
    }

    @Test
    void getCurrenciesValues_InvalidCurrencyCode() {
        String currencyCode = "124";
        int page = 0;
        int size = 10;
        when(currencyRequestValueService.getCurrencyRequestValues(currencyCode, null, null, page, size))
                .thenThrow(new IllegalArgumentException("No se encontro la clave de divisa en el catalogo."));

        // Mandamos llamar funcionalidad a validar
        ResponseEntity<Object> response = currencyController.getCurrenciesValues(currencyCode, null, null, page, size);

        // Validar la respuesta, que devuelva bad request y mensaje de error
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No se encontro la clave de divisa en el catalogo.", response.getBody());
    }

    @Test
    void getCurrenciesValues_InvalidDateFormat() {
        String currencyCode = "all";

        when(currencyRequestValueService.getCurrencyRequestValues(eq(currencyCode), any(), any(), anyInt(), anyInt()))
                .thenThrow(new IllegalArgumentException("Formato de fecha invalido. Use 'YYYY-MM-DDThh:mm:ss'."));

        // Mandamos llamar funcionalidad a validar
        ResponseEntity<Object> response = currencyController.getCurrenciesValues(currencyCode, "invalid_date", null, 0, 10);

        // Validar la respuesta, que devuelva bad request y mensaje de error
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Formato de fecha invalido. Use 'YYYY-MM-DDThh:mm:ss'.", response.getBody());
    }
}