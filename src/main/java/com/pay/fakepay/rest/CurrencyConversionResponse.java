package com.pay.fakepay.rest;

import com.pay.fakepay.Currency;

public class CurrencyConversionResponse {
    private Currency currencyOne;
    private Currency currencyTwo;
    private float amount;
    private float exchanged;

    public CurrencyConversionResponse(
            Currency currencyOne, 
            Currency currencyTwo, 
            float amount) {
        this.currencyOne = currencyOne;
        this.currencyTwo = currencyTwo;
        this.amount = amount;
    }
    
    public CurrencyConversionResponse() { }

    public Currency getCurrencyOne() {
        return currencyOne;
    }

    public void setCurrencyOne(Currency currencyOne) {
        this.currencyOne = currencyOne;
    }

    public Currency getCurrencyTwo() {
        return currencyTwo;
    }

    public void setCurrencyTwo(Currency currencyTwo) {
        this.currencyTwo = currencyTwo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getExchanged() {
        return exchanged;
    }

    public void setExchanged(float exchanged) {
        this.exchanged = exchanged;
    }
}