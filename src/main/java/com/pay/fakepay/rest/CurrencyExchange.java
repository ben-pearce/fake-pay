package com.pay.fakepay.rest;

import com.pay.fakepay.Currency;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

class Conversion {
    Currency currencyOne;
    Currency currencyTwo;

    public Conversion(Currency currencyOne, Currency currencyTwo) {
        this.currencyOne = currencyOne;
        this.currencyTwo = currencyTwo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.currencyOne);
        hash = 97 * hash + Objects.hashCode(this.currencyTwo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conversion other = (Conversion) obj;
        return Objects.equals(this.currencyOne, other.currencyOne);
    }
}

@Path("/")
public class CurrencyExchange {
    
    private final static HashMap<Conversion, Float> exchangeRates = 
            new HashMap<Conversion, Float>() {{
        put(new Conversion(Currency.GBP, Currency.EUR), 1.17f);
        put(new Conversion(Currency.GBP, Currency.USD), 1.39f);
        put(new Conversion(Currency.EUR, Currency.GBP), 0.86f);
        put(new Conversion(Currency.EUR, Currency.USD), 1.19f);
        put(new Conversion(Currency.USD, Currency.GBP), 0.72f);
        put(new Conversion(Currency.USD, Currency.EUR), 0.84f);
    }};
    
    @GET
    @Path("/{currencyOne}/{currencyTwo}/{amount}")
    public Response getCurrencyConversion(
            @PathParam("currencyOne") final Currency currencyOne, 
            @PathParam("currencyTwo") final Currency currencyTwo, 
            @PathParam("amount") final float amount) {
        JSONObject resp = new JSONObject() {{
            put("from", currencyOne);
            put("to", currencyTwo);
            put("amount", amount);
        }};
        
        Conversion c = new Conversion(currencyOne, currencyTwo);
        float rate = exchangeRates.containsKey(c) ? exchangeRates.get(c) : 1f;
        
        BigDecimal bd = new BigDecimal(Float.toString(amount * rate));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        resp.put("exchanged", bd.floatValue());
        
        return Response
                .status(Response.Status.OK)
                .entity(resp.toString())
                .build();
    }
}
