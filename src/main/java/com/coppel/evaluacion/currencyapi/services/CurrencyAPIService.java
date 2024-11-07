package com.coppel.evaluacion.currencyapi.services;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.coppel.evaluacion.currencyapi.entities.Currency;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequest;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRepository;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRequestRepository;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRequestValueRepository;


@Service
public class CurrencyAPIService {

    private String apiUrl = "https://api.currencyapi.com/v3/latest" ;

    @Value("${currencyapi.apikey}")
    private String apiKey;

    @Value("${currencyapi.timeoutSeconds}")
    private Integer timeoutSeconds;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyRequestRepository currencyRequestRepository;

    @Autowired
    private CurrencyRequestValueRepository currencyRequestValueRepository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    public CurrencyAPIService() {

    }

    public void requestAndSaveResponseApiCurrency(){

        RestTemplate restTemplate = createRestTemplateWithTimeouts(timeoutSeconds * 1000);
        URI uri = uriCurrencyApi();

        // Creamos registro de solicitud   
        CurrencyRequest currencyRequest = createCurrencyRequest();
        
        try {
            // Definimos tiempo de inicio
            long startTime = System.currentTimeMillis();
            
            // Se realiza la petición a la API externa
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            
            // Calculamos duracion
            float responseTime = (System.currentTimeMillis() - startTime);
            
            // Accedemos a la clave 'meta' dentro de la respuesta para obtener el valor de last_updated_at
            Map<String, Object> meta = (Map<String, Object>) response.get("meta");
            String lastUpdatedAtString = (String) meta.get("last_updated_at");
            LocalDateTime lastUpdatedAt = LocalDateTime.parse(lastUpdatedAtString, DateTimeFormatter.ISO_DATE_TIME);
            
            // Actualizamos la solicitud con tiempo de respuesta y fecha de ultima actualizacion
            updateCurrencyRequestSuccess(currencyRequest, lastUpdatedAt, responseTime);

            // Mandamos llamar funcion para procesar las divisas
            processCurrencyData(currencyRequest, response);


        } catch (Exception e) {
            // Actualizamos el estado de la solicitud 
            updateCurrencyRequestError(currencyRequest, "FAILED");
            log.error("Error inesperado: {}", e.getMessage());
        }

    }

    private RestTemplate createRestTemplateWithTimeouts(int timeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(timeout);

        return new RestTemplate(factory);
    }

    private URI  uriCurrencyApi(){
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .queryParam("apikey", apiKey)
            .build()
            .toUri();
        return uri;
    }

    private CurrencyRequest createCurrencyRequest() {
        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.setStatus("PENDING");
        currencyRequest.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return currencyRequestRepository.save(currencyRequest);
    }

    private void updateCurrencyRequestSuccess(CurrencyRequest currencyRequest, LocalDateTime lastUpdatedAt, float responseTime) {
        currencyRequest.setStatus("COMPLETED");
        currencyRequest.setLastUpdatedAt(lastUpdatedAt);
        currencyRequest.setResponseTime(responseTime);
        currencyRequest.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        currencyRequestRepository.save(currencyRequest);
    }

    private void updateCurrencyRequestError(CurrencyRequest currencyRequest, String status) {
        currencyRequest.setStatus(status);
        currencyRequest.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        currencyRequestRepository.save(currencyRequest);
    }

    private Currency findOrCreateCurrency(String code) {
        Currency currency = currencyRepository.findByCode(code);

        if (currency == null) {
            currency = new Currency();
            currency.setCode(code);
            currencyRepository.save(currency);
        }
        return currency;
    }

    private void createCurrencyRequestValue(CurrencyRequest currencyRequest, Currency currency, Double value) {
        CurrencyRequestValue currencyRequestValue = new CurrencyRequestValue();
        currencyRequestValue.setCurrencyRequest(currencyRequest);
        currencyRequestValue.setCurrency(currency);
        currencyRequestValue.setValue(new BigDecimal(value.toString()));
        currencyRequestValueRepository.save(currencyRequestValue);
        System.out.println(currencyRequestValue.toString());
    }

    private void processCurrencyData(CurrencyRequest currencyRequest, Map<String, Object> response) {
        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) response.get("data");

        data.forEach((code, valueMap) -> {
            Currency currency = findOrCreateCurrency(code);

            Object valueObject = valueMap.get("value");
            Double value = 0.0;
            if (valueObject instanceof Integer) {
                value = ((Integer) valueObject).doubleValue();
            } else if (valueObject instanceof Double) {
                value = (Double) valueObject;
            }

            createCurrencyRequestValue(currencyRequest, currency, value);
            
        });
    }

    
    

    
}
