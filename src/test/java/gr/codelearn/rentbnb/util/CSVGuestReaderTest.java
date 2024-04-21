package gr.codelearn.rentbnb.util;

import gr.codelearn.rentbnb.domain.Guest;
import gr.codelearn.rentbnb.exception.InvalidObjectValuesException;
import gr.codelearn.rentbnb.service.DataRepositoryService;
import gr.codelearn.rentbnb.service.GuestService;
import gr.codelearn.rentbnb.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CSVGuestReaderTest {

    private CSVGuestReader csvGuestReader = new CSVGuestReader();
    private GuestService guestService;

    private DataRepositoryService dataRepositoryService;

   private  List<Guest> guests;

    @BeforeEach
    public void setUp() {
        this.dataRepositoryService = mock(DataRepositoryService.class);
        this.guestService = new GuestServiceImpl(dataRepositoryService);

        this.guests = csvGuestReader.restoreFromCsv("for_testing.csv");
    }

    @Test
    public void testCSVGuestReader() {
        for (Guest guest : guests) {
             try {
                 assertTrue(guestService.register(guest));
             } catch (InvalidObjectValuesException e) {
                 fail("Invalid guest object values: " + e.getMessage());
             } catch (NullPointerException e) {
                 fail("Guest object cannot be null: " + e.getMessage());
             }
        }
    }

}