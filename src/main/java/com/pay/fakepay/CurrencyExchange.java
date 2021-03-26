package com.pay.fakepay;

import com.pay.fakepay.rest.CurrencyConversionResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


public class CurrencyExchange {
    private static final String REST_BASE_URL = 
            "http://localhost:10000/webapps2020/";
    
    private static final Client client = ClientBuilder.newClient();
    
    public static Float convert(
            Currency currencyOne, 
            Currency currencyTwo,
            float amount) {
        CurrencyConversionResponse resp = client.target(REST_BASE_URL)
                .path("conversion")
                .path(currencyOne.toString())
                .path(currencyTwo.toString())
                .path(Float.toString(amount))
                .request(MediaType.APPLICATION_JSON)
                .get(CurrencyConversionResponse.class);
        return resp.getExchanged();
    }
}
