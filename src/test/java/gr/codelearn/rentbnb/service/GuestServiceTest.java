package gr.codelearn.rentbnb.service;

import gr.codelearn.rentbnb.domain.Guest;
import gr.codelearn.rentbnb.exception.InvalidObjectValuesException;
import gr.codelearn.rentbnb.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuestServiceTest {

    private GuestService guestService;

    private DataRepositoryService dataRepositoryService;


    @BeforeEach
    public void setUp() {
        this.dataRepositoryService = mock(DataRepositoryService.class);
        this.guestService = new GuestServiceImpl(dataRepositoryService);
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterGuest() {

        Guest guest = Guest.builder("test.email@guest.com", getDate(1990, 4,4)).build();
        when(dataRepositoryService.save(guest)).thenReturn(true);
        try {
            boolean result = guestService.register(guest);
            assertTrue(result);
        } catch (InvalidObjectValuesException e) {
            fail();
        }
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterNullGuest() {
        assertThrows(NullPointerException.class, () -> {
            guestService.register(null);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterNumericFirstName() {
        Guest guest = Guest.builder("test.email@guest.com", new Date()).firstname("123TestFirstName").build();
        assertThrows(InvalidObjectValuesException.class, () -> {
            guestService.register(guest);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterNumericLastName() {
        Guest guest = Guest.builder("test.email@guest.com", new Date()).firstname("123TestLastName").build();
        assertThrows(InvalidObjectValuesException.class, () -> {
            guestService.register(guest);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterUnborn() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, +1);
        Date unborn = calendar.getTime();

        Guest guest = Guest.builder("test.email@guest.com", unborn).build();
        assertThrows(InvalidObjectValuesException.class, () -> {
            guestService.register(guest);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterInfant() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();
        assertThrows(InvalidObjectValuesException.class, () -> {
            guestService.register(guest);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterOneYearOld() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        Date oneYearOld = calendar.getTime();

        Guest guest = Guest.builder("test.email@guest.com", oneYearOld).build();
        when(dataRepositoryService.save(guest)).thenReturn(true);
        try {
            boolean result = guestService.register(guest);
            assertTrue(result);
        } catch (InvalidObjectValuesException e) {
            fail();
        }

    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegister120YearOld() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -120);
        Date One20YearOld = calendar.getTime();

        Guest guest = Guest.builder("test.email@guest.com", One20YearOld).build();
        when(dataRepositoryService.save(guest)).thenReturn(true);
        try {
            boolean result = guestService.register(guest);
            assertTrue(result);
        } catch (InvalidObjectValuesException e) {
            fail();
        }

    }

    @Test
    // @DisplayName("Test Method One")
    public void testRegisterElderly() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -121);
        Date One21YearOld = calendar.getTime();

        Guest guest = Guest.builder("test.email@guest.com", One21YearOld).build();
        assertThrows(InvalidObjectValuesException.class, () -> {
            guestService.register(guest);
        });
    }

    @Test
    // @DisplayName("Test Method One")
    public void testUpdateReputation() {

        Guest guest = Guest.builder("test.email@guest.com", new Date()).build();

        guestService.updateReputation(guest, BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(2.0), guest.getReputation());
    }

    @Test
    // @DisplayName("Test Method One")
    public void testGetGuests() {
        Guest guest1 = Guest.builder("test1.email@guest.com", new Date()).build();
        Guest guest2 = Guest.builder("test2.email@guest.com", new Date()).build();
        List<Guest> expectedGuests = Arrays.asList(guest1, guest2);

        when(dataRepositoryService.getGuests()).thenReturn(expectedGuests);

        List<Guest> actualGuests = guestService.getGuests();

        assertEquals(expectedGuests, actualGuests);
    }

    @Test
    // @DisplayName("Test Method One")
    public void testGetGuestById() {
        Guest expectedGuest = Guest.builder("test.email@guest.com", new Date()).build();

        when(dataRepositoryService.getGuest(1L)).thenReturn(expectedGuest);

        Guest actualGuest = guestService.getGuest(1L);

        assertEquals(expectedGuest, actualGuest);
    }

    @Test
    // @DisplayName("Test Method One")
    public void testGetGuestByEmail() {
        Guest expectedGuest = Guest.builder("test.email@guest.com", new Date()).build();

        when(dataRepositoryService.getGuest("test.email@guest.com")).thenReturn(expectedGuest);

        Guest actualGuest = guestService.getGuest("test.email@guest.com");

        assertEquals(expectedGuest, actualGuest);
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }
}