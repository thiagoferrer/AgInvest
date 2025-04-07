package com.example.aginvest.util;

import java.math.BigDecimal;

public class TaxService {
    public static BigDecimal calcularTaxaIR(int diasInvestimento, boolean isTaxable) {
        if (!isTaxable) return BigDecimal.ZERO;
        
        // Progressive IR table for Brazil (2024)
        if (diasInvestimento <= 180) return new BigDecimal("0.225");
        else if (diasInvestimento <= 360) return new BigDecimal("0.20");
        else if (diasInvestimento <= 720) return new BigDecimal("0.175");
        else return new BigDecimal("0.15");
    }
}
