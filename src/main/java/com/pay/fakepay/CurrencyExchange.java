package com.pay.fakepay;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.json.JSONObject;


public class CurrencyExchange {
    private static final String REST_BASE_URL = 
            "http://localhost:10000/webapps2020/";
    
    private static final Client client = ClientBuilder.newClient();
    
    public static Float convert(
            Currency currencyOne, 
            Currency currencyTwo,
            float amount) {
        String resp = client.target(REST_BASE_URL)
                .path("conversion")
                .path(currencyOne.toString())
                .path(currencyTwo.toString())
                .path(Float.toString(amount))
                .request().get(String.class);
        JSONObject converted = new JSONObject(resp);
        return converted.getFloat("exchanged");
    }
}
