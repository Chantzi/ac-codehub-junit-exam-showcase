package gr.codelearn.rentbnb.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    public void testBuilderWithNullAddress() {
        assertThrows(NullPointerException.class, () -> {
            Property.builder(null, BigDecimal.valueOf(45)).build();
        });
    }

    @Test
    public void testBuilderWithNullPricePerDay() {
        assertThrows(NullPointerException.class, () -> {
            Property.builder("123 Test Address",null).build();
        });
    }

}