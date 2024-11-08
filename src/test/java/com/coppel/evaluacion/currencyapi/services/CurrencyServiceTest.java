package com.coppel.evaluacion.currencyapi.services;

import com.coppel.evaluacion.currencyapi.entities.Currency;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    private Currency currency1;
    private Currency currency2;

    @BeforeEach
    void setUp() {
        // Creamos instancias de Currency para las pruebas
        currency1 = new Currency(1L, "MXN", "");
        currency2 = new Currency(2L, "USD", "");
    }

    @Test
    void getCurrencies_ReturnList() {
        // Creamos lista con dos elementos
        List<Currency> mockCurrencies = Arrays.asList(currency1, currency2);
        // Cuando en el proceso se intente consultar a la db retornamos la lista creada
        when(currencyRepository.findAll()).thenReturn(mockCurrencies);

        // Mandamos llamar funcionalidad a validar
        List<Currency> currencies = currencyService.getCurrencies();

        // Validamos que no sea null y que contenta los dos elementos
        assertNotNull(currencies);
        assertEquals(2, currencies.size(), "La lista debe tener 2 divisas");
        assertTrue(currencies.contains(currency1), "La lista debe contener USD");
        assertTrue(currencies.contains(currency2), "La lista debe contener EUR");
    }

    @Test
    void getCurrencies_ReturnEmptyList() {
        // Simulamos que el repositorio no devuelve divisas
        when(currencyRepository.findAll()).thenReturn(Arrays.asList());

        List<Currency> currencies = currencyService.getCurrencies();

        // Verificamos que la lista está vacía
        assertNotNull(currencies, "La lista no debe ser nula");
        assertTrue(currencies.isEmpty(), "La lista debe estar vacía");
    }
}