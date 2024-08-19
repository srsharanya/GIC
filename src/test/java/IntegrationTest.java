import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private SeatManager seatManager;
    private BookingManager bookingManager;

    @BeforeEach
    void setUp() {
        seatManager = new SeatManagerImpl(5, 5);
        bookingManager = new BookingManagerImpl();
    }

    @Test
    void testBookingFlow() throws BookingException {
        // Book some seats
        List<Seat> seatsToBook = List.of(new Seat(0, 0), new Seat(0, 1));
        String bookingId = bookingManager.createBooking(seatsToBook);
        seatManager.bookSeats(seatsToBook);
        // Verify booking
        assertNotNull(bookingId);
        assertTrue(bookingManager.getBooking(bookingId).isPresent());

        // Verify seats are booked
        assertFalse(seatManager.isSeatAvailable(0, 0));
        assertFalse(seatManager.isSeatAvailable(0, 1));

        // Modify the booking
        seatManager.clearSeats(seatsToBook);
        List<Seat> newSeats = List.of(new Seat(1, 0), new Seat(1, 1));
        bookingManager.modifyBooking(bookingId, newSeats);
        seatManager.bookSeats(newSeats);
        // Verify modification
        assertTrue(bookingManager.getBooking(bookingId).isPresent());
        assertFalse(seatManager.isSeatAvailable(1, 0));
        assertFalse(seatManager.isSeatAvailable(1, 1));

        // Verify old seats are now available
        assertTrue(seatManager.isSeatAvailable(0, 0));
        assertTrue(seatManager.isSeatAvailable(0, 1));
    }
}
