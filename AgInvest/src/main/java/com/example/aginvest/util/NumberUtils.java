package com.example.aginvest.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberUtils {
    public static BigDecimal parseCurrency(String value) throws ParseException {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        // Remove currency symbols and normalize decimal separators
        String normalized = value.replace("R$", "")
                .replace("$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();

        try {
            return new BigDecimal(normalized);
        } catch (NumberFormatException e) {
            // Try parsing with locale-specific format
            NumberFormat format = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
            return BigDecimal.valueOf(format.parse(normalized).doubleValue());
        }
    }
}