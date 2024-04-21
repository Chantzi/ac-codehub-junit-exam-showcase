package gr.codelearn.rentbnb.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReservationTest {


    @Test
    public void testBuilderWithNullAddress() {
        assertThrows(NullPointerException.class, () -> {
            Reservation.builder(null, mock(Property.class)).build();
        });
    }

    @Test
    public void testBuilderWithNullPricePerDay() {
        assertThrows(NullPointerException.class, () -> {
            Reservation.builder(mock(Guest.class),null).build();
        });
    }

}