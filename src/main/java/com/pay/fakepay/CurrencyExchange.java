package com.pay.fakepay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class CurrencyExchange {
    private static final String REST_BASE_URL = 
            "http://localhost:10000/webapps2020/resources";
    
    private static final Client client = ClientBuilder.newClient();
    
    public static Float convert(
            Currency currencyOne, 
            Currency currencyTwo,
            float amount) {
        Float converted = client.target(REST_BASE_URL)
                .path("conversion")
                .path(currencyOne.toString())
                .path(currencyTwo.toString())
                .path(Float.toString(amount))
                .request().get(Float.class);
        BigDecimal bd = new BigDecimal(Float.toString(converted));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
