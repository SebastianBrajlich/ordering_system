package com.ltrlabs.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    private static final int SCALE = 4;

    public static BigDecimal calculateTax(BigDecimal taxPercent, BigDecimal netPrice, int quantity) {
        if (taxPercent == null || netPrice == null || quantity == 0) return BigDecimal.ZERO;
        if (taxPercent.equals(BigDecimal.ZERO)) return taxPercent;
        return netPrice.multiply(BigDecimal.valueOf(quantity)).multiply(taxPercent.divide(BigDecimal.valueOf(100), RoundingMode.CEILING));
    }

    public static BigDecimal calculateTax(BigDecimal taxPercent, BigDecimal netTotal) {
        if (taxPercent == null || netTotal == null) return BigDecimal.ZERO;
        if (taxPercent.equals(BigDecimal.ZERO)) return taxPercent;
        return netTotal.multiply(taxPercent.divide(BigDecimal.valueOf(100), RoundingMode.CEILING));
    }

    public static BigDecimal calculateTotal(BigDecimal taxPercent, BigDecimal netTotal) {
        if (taxPercent == null || netTotal == null) return BigDecimal.ZERO;
        if (taxPercent.equals(BigDecimal.ZERO)) return netTotal;
        return netTotal.multiply(taxPercent.divide(BigDecimal.valueOf(100), RoundingMode.CEILING).add(BigDecimal.ONE));
    }

    public static BigDecimal calculateNetTotal(BigDecimal netPrice, int quantity) {
        return netPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
