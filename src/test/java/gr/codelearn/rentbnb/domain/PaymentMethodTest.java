package gr.codelearn.rentbnb.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {

    @Test
    public void testCashDiscount() {
        PaymentMethod paymentMethod = PaymentMethod.CASH;
        assertEquals(0f, paymentMethod.getDiscount(), 0.001f);
    }

    @Test
    public void testOnlineDiscount() {
        PaymentMethod paymentMethod = PaymentMethod.ONLINE;
        assertEquals(0.10f, paymentMethod.getDiscount(), 0.001f);
    }

}