package gr.codelearn.rentbnb.domain;

import gr.codelearn.rentbnb.exception.InvalidObjectValuesException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testBuilderWithNullEmail() {
        assertThrows(NullPointerException.class, () -> {
            Guest.builder(null,new Date()).build();
        });
    }

    @Test
    public void testBuilderWithNullDateOfBirth() {
        assertThrows(NullPointerException.class, () -> {
            Guest.builder("test.email@guest.com",null).build();
        });
    }

    @Test
    public void testValidEmail() {
        assertTrue(validator.validate("test.email@guest.com").isEmpty());
    }

//    @Test
//    public void testInvalidEmail() {
//        assertThrows(ConstraintViolationException.class, () -> {
//            Guest.builder("invalid-email",new Date()).build();
//        });
//    }

    @Test
    public void testValidAge() {
        Guest guest = Guest.builder("test.email@guest.com", new Date(System.currentTimeMillis())).build();



        assertTrue(validator.validate(guest).isEmpty());
    }

//    @Test
//    public void testInvalidAge() {
//        assertThrows(ConstraintViolationException.class, () -> {
//            Guest.builder("test.email@guest.com",new Date(System.currentTimeMillis() + 1)).build();
//        });
//    }


}