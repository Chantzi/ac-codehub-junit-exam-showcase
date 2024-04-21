package gr.codelearn.rentbnb.domain;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class HostTest {

    @Test
    public void testBuilderWithNullEmail() {
        assertThrows(NullPointerException.class, () -> {
            Host.builder(null,"TestFirstName", "TestLastName").build();
        });
    }

    @Test
    public void testBuilderWithNullFirstName() {
        assertThrows(NullPointerException.class, () -> {
            Host.builder("test.email@guest.com",null, "TestLastName").build();
        });
    }

    @Test
    public void testBuilderWithNullLastName() {
        assertThrows(NullPointerException.class, () -> {
            Host.builder("test.email@guest.com","TestFirstName", null).build();
        });
    }

}