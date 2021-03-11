package com.pay.fakepay;

public enum Currency {
    GBP("£"), EUR("€"), USD("$");
    
    private final String symbol;
    
    Currency(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() {
        return this.symbol;
    }
}
