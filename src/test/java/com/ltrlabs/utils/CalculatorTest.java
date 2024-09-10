package com.ltrlabs.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void calculateTax() {
        BigDecimal netPrice = new BigDecimal("123.25");
        BigDecimal taxPercent = new BigDecimal("23.00");
        int quantity = 12;
        BigDecimal expectedValue = new BigDecimal("340.1700");
        BigDecimal result = Calculator.calculateTax(taxPercent, netPrice, quantity);
        assertEquals(expectedValue, result);
    }

    @Test
    void testCalculateTax() {
        BigDecimal netTotal = new BigDecimal("123.25");
        BigDecimal taxPercent = new BigDecimal("23.00");
        BigDecimal expectedValue = new BigDecimal("28.3475");
        BigDecimal result = Calculator.calculateTax(taxPercent, netTotal);
        assertEquals(expectedValue, result);
    }

    @Test
    void calculateTotal() {
        BigDecimal netTotal = new BigDecimal("100.25");
        BigDecimal taxPercent = new BigDecimal("23.00");
        BigDecimal expectedValue = new BigDecimal("123.3075");
        BigDecimal result = Calculator.calculateTotal(taxPercent, netTotal);
        assertEquals(expectedValue, result);
    }

    @Test
    void calculateNetTotal() {
        BigDecimal netPrice = new BigDecimal("100.25");
        int quantity = 0;
        BigDecimal result = Calculator.calculateNetTotal(netPrice, quantity);
        BigDecimal expectedValue = new BigDecimal("0.00");
        assertEquals(expectedValue, result);
    }
}