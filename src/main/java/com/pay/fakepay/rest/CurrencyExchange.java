package com.pay.fakepay.rest;

import java.util.HashMap;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

class Conversion {
    String currencyOne;
    String currencyTwo;

    public Conversion(String currencyOne, String currencyTwo) {
        this.currencyOne = currencyOne.toLowerCase();
        this.currencyTwo = currencyTwo.toLowerCase();
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

@Path("/conversion/{currencyOne}/{currencyTwo}/{amount}")
public class CurrencyExchange {
    
    private final static HashMap<Conversion, Float> exchangeRates = 
            new HashMap<Conversion, Float>() {{
        put(new Conversion("gbp", "eur"), 1.17f);
        put(new Conversion("gbp", "usd"), 1.39f);
        put(new Conversion("eur", "gbp"), 0.86f);
        put(new Conversion("eur", "usd"), 1.19f);
        put(new Conversion("usd", "gbp"), 0.72f);
        put(new Conversion("usd", "eur"), 0.84f);
    }};
    
    @GET
    @Produces("text/plain")
    public Response getCurrencyConversion(
            @PathParam("currencyOne") String currencyOne, 
            @PathParam("currencyTwo") String currencyTwo, 
            @PathParam("amount") float amount) {
        // TODO: Look into producing a JSON reply?
        if(currencyOne.equals(currencyTwo)) {
            return Response
                    .status(Response.Status.OK)
                    .entity(amount)
                    .build();
        }
        Conversion c = new Conversion(currencyOne, currencyTwo);
        if(!exchangeRates.containsKey(c)) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        float rate = exchangeRates.get(c);
        return Response
                .status(Response.Status.OK)
                .entity(amount * rate)
                .build();
    }
}
