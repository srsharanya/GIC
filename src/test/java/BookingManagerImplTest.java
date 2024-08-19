import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerImplTest {

    private BookingManagerImpl bookingManager;
    private final List<Seat> seats = new ArrayList<>();

    @BeforeEach
    void setUp() {
        bookingManager = new BookingManagerImpl();
        seats.add(new Seat(1, 1));
        seats.add(new Seat(1, 2));
    }

    @Test
    void testCreateBooking() throws BookingException {
        String bookingId = bookingManager.createBooking(seats);
        assertNotNull(bookingId);
        Optional<List<Seat>> retrievedSeats = bookingManager.getBooking(bookingId);
        assertTrue(retrievedSeats.isPresent());
        assertEquals(seats, retrievedSeats.get());
    }

    @Test
    void testGetBooking() throws BookingException {
        String bookingId = bookingManager.createBooking(seats);
        Optional<List<Seat>> retrievedSeats = bookingManager.getBooking(bookingId);
        assertTrue(retrievedSeats.isPresent());
        assertEquals(seats, retrievedSeats.get());
    }

    @Test
    void testModifyBooking() throws BookingException {
        String bookingId = bookingManager.createBooking(seats);
        List<Seat> newSeats = new ArrayList<>();
        newSeats.add(new Seat(2, 1));
        bookingManager.modifyBooking(bookingId, newSeats);
        Optional<List<Seat>> retrievedSeats = bookingManager.getBooking(bookingId);
        assertTrue(retrievedSeats.isPresent());
        assertEquals(newSeats, retrievedSeats.get());
    }

    @Test
    void testCreateBookingWithNullSeats() {
        assertThrows(BookingException.class, () -> bookingManager.createBooking(null));
    }

    @Test
    void testCreateBookingWithEmptySeats() {
        assertThrows(BookingException.class, () -> bookingManager.createBooking(new ArrayList<>()));
    }

    @Test
    void testGetBookingWithNoBookingId() {
        assertThrows(BookingException.class, () -> bookingManager.getBooking(""));
    }

    @Test
    void testGetBookingWithInvalidId() {
        assertThrows(BookingException.class, () -> bookingManager.getBooking("242342234"));
    }

    @Test
    void testModifyBookingWithInvalidId() {
        assertThrows(BookingException.class, () -> bookingManager.modifyBooking("InvalidId", seats));
    }
}
