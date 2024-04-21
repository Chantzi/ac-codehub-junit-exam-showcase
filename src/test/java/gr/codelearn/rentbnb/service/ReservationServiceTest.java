package gr.codelearn.rentbnb.service;

import gr.codelearn.rentbnb.domain.Guest;
import gr.codelearn.rentbnb.domain.PaymentMethod;
import gr.codelearn.rentbnb.domain.Property;
import gr.codelearn.rentbnb.domain.Reservation;
import gr.codelearn.rentbnb.exception.InvalidDateOrderException;
import gr.codelearn.rentbnb.exception.ReservationAvailabilityException;
import gr.codelearn.rentbnb.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    private DataRepositoryService dataRepositoryService;
    private GuestService guestService;
    private MailService mailService;
    private PaymentService paymentService;
    private ReservationService reservationService;


    @BeforeEach
    public void setUp() {
        this.dataRepositoryService = mock(DataRepositoryService.class);
        this.guestService = mock(GuestService.class);
        this.mailService = mock(MailService.class);
        this.paymentService = mock(PaymentService.class);
        this.reservationService = new ReservationServiceImpl(dataRepositoryService, guestService, mailService, paymentService);


    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserve() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH).build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        try {
            Boolean result = reservationService.reserve(reservation, null);
            assertTrue(result);
        }
        catch (InvalidDateOrderException | ReservationAvailabilityException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveNullGuest() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        try {
            Reservation reservation = Reservation.builder(null, property)
                    .checkIn(checkInDate)
                    .checkOut(checkOutDate)
                    .paymentMethod(PaymentMethod.CASH).build();

            when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
            when(dataRepositoryService.save(reservation)).thenReturn(true);
            try {
                Boolean result = reservationService.reserve(reservation, null);
                assertFalse(result);
            }
            catch (InvalidDateOrderException | ReservationAvailabilityException e) {
                fail(e.getMessage());
            }
        }catch (NullPointerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveNullProperty() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        try {
            Reservation reservation = Reservation.builder(guest, null)
                    .checkIn(checkInDate)
                    .checkOut(checkOutDate)
                    .paymentMethod(PaymentMethod.CASH).build();

            when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
            when(dataRepositoryService.save(reservation)).thenReturn(true);
            try {
                Boolean result = reservationService.reserve(reservation, null);
                assertFalse(result);
            }
            catch (InvalidDateOrderException | ReservationAvailabilityException e) {
                fail(e.getMessage());
            }
        }catch (NullPointerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveInvalidDateOrder() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+6);
        Date checkOutDate = getDate(Calendar.DATE,+2);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH).build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        assertThrows(InvalidDateOrderException.class, () -> {
                    reservationService.reserve(reservation, null);
                });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveAvailabilityException() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH).build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(false);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        assertThrows(ReservationAvailabilityException.class, () -> {
            reservationService.reserve(reservation, null);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveAdditionalGuests() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        Guest guest1 = Guest.builder("test1.email@guest.com", new Date()).build();
        Guest guest2 = Guest.builder("test2.email@guest.com", new Date()).build();
        List<Guest> guests = Arrays.asList(guest1, guest2);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH)
                .build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        try {
            Boolean result = reservationService.reserve(reservation, guests);
            assertTrue(result);
        }
        catch (InvalidDateOrderException | ReservationAvailabilityException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveCalculateAdditionalGuestsAgeInfants() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        Date guest1BirthDate = getDate(Calendar.YEAR,-1);

        Guest guest1 = Guest.builder("test1.email@guest.com", guest1BirthDate).build();
        List<Guest> guests = Arrays.asList(guest1);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH)
                .build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        try {
            Boolean result = reservationService.reserve(reservation, guests);
        }
        catch (InvalidDateOrderException | ReservationAvailabilityException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertAll(
                () -> assertEquals(reservation.getNumOfInfants(), 1),
                () -> assertEquals(reservation.getNumOfChildren(), 0),
                () -> assertEquals(reservation.getNumOfAdults(), 1)

        );
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveCalculateAdditionalGuestsAgeChild() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        Date guest1BirthDate = getDate(Calendar.YEAR,-2);

        Guest guest1 = Guest.builder("test1.email@guest.com", guest1BirthDate).build();
        List<Guest> guests = Arrays.asList(guest1);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH)
                .build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        try {
            Boolean result = reservationService.reserve(reservation, guests);
        } catch (InvalidDateOrderException | ReservationAvailabilityException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertAll(
                () -> assertEquals(reservation.getNumOfInfants(), 0),
                () -> assertEquals(reservation.getNumOfChildren(), 1),
                () -> assertEquals(reservation.getNumOfAdults(), 1)

        );
    }

    @Test
    // @DisplayName("Test Method One")
    public void testReserveCalculateAdditionalGuestsAgeAdult() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE, +2);
        Date checkOutDate = getDate(Calendar.DATE, +6);

        Date guest1BirthDate = getDate(Calendar.YEAR, -18);

        Guest guest1 = Guest.builder("test1.email@guest.com", guest1BirthDate).build();
        List<Guest> guests = Arrays.asList(guest1);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH)
                .build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);

        try {
            Boolean result = reservationService.reserve(reservation, guests);
        } catch (InvalidDateOrderException | ReservationAvailabilityException e) {
            System.out.println(e.getMessage());
            fail();
        }

        assertAll(
                () -> assertEquals(reservation.getNumOfInfants(), 0),
                () -> assertEquals(reservation.getNumOfChildren(), 0),
                () -> assertEquals(reservation.getNumOfAdults(), 2)

        );
    }

    @Test
//  @DisplayName("Test Method One")
    public void testCancel() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        Property property = Property.builder("123 Test Address", BigDecimal.valueOf(40.5)).build();

        Date checkInDate = getDate(Calendar.DATE,+2);
        Date checkOutDate = getDate(Calendar.DATE,+6);

        Reservation reservation = Reservation.builder(guest, property)
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .paymentMethod(PaymentMethod.CASH).build();
        when(dataRepositoryService.isPropertyAvailableAtSpecifiedDates(reservation.getProperty().getId(), checkInDate, checkOutDate)).thenReturn(true);
        when(dataRepositoryService.save(reservation)).thenReturn(true);
        when(dataRepositoryService.delete(reservation)).thenReturn(true);

        try {
            Boolean result = reservationService.reserve(reservation, null);
        } catch (InvalidDateOrderException | ReservationAvailabilityException e) {
            System.out.println(e.getMessage());
            fail();
        }

        Boolean result = reservationService.cancel(reservation);
        assertTrue(result);
    }

    public Date getDate(Integer value, Integer amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(value, amount);
        Date newDate = calendar.getTime();

        return newDate;
    }
}