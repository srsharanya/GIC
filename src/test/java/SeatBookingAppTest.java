import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SeatBookingAppTest {
    private SeatBookingApp app;

    @BeforeEach
    void setUp() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testInitialize() {
        String simulatedInput = "Inception 8 10";
        Scanner scanner = new Scanner(simulatedInput);
        app = new SeatBookingApp(scanner);
        app.initialize();
        assertEquals(app.getMovie().title(), "Inception");
        assertEquals(app.getSeatManager().getNumCols(), 10);
        assertEquals(app.getSeatManager().getSeatingChart().length, 8);
        assertEquals(app.getSeatManager().getAvailableSeats().size(), 80);
    }

    @Test
    void testNewBooking() {
        String simulatedInput = "Inception 8 10\n1\n4\n\n3";
        Scanner scanner = new Scanner(simulatedInput);
        app = new SeatBookingApp(scanner);
        app.initialize();
        assertEquals(80, app.getSeatManager().getAvailableSeats().size()); // Check initial state
        app.runApp(); // Make booking
        assertEquals(76, app.getSeatManager().getAvailableSeats().size()); // Check state after booking
    }
    @Test
    void testModifyBooking() {
        String simulatedInput = "Inception 8 10\n1\n10\nB03\n\n3";
        Scanner scanner = new Scanner(simulatedInput);
        app = new SeatBookingApp(scanner);
        app.initialize();
        assertEquals(80, app.getSeatManager().getAvailableSeats().size()); // Check initial state
        app.runApp(); // Make booking
        assertEquals(70, app.getSeatManager().getAvailableSeats().size()); // Check state after booking
    }
    @Test
    void testMultipleNewBooking() {
        String simulatedInput = "Inception 8 10\n1\n10\n\n1\n5\n\n3";
        Scanner scanner = new Scanner(simulatedInput);
        app = new SeatBookingApp(scanner);
        app.initialize();
        assertEquals(80, app.getSeatManager().getAvailableSeats().size()); // Check initial state
        app.runApp(); // Make booking
        assertEquals(65, app.getSeatManager().getAvailableSeats().size()); // Check state after booking
    }
    @Test
    void testViewBooking() {
        String simulatedInput = "Inception 8 10\n1\n4\n\n2\nGIC0001\n\n3";
        Scanner scanner = new Scanner(simulatedInput);
        app = new SeatBookingApp(scanner);
        app.initialize();
        assertEquals(80, app.getSeatManager().getAvailableSeats().size()); // Check initial state
        app.runApp(); // Make booking
        assertEquals(76, app.getSeatManager().getAvailableSeats().size());
        Optional<List<Seat>> booking = app.getBookingManager().getBooking("GIC0001");
        assertTrue(booking.isPresent(), "Booking with ID GIC0001 should exist.");

        assertThrows(BookingException.class, () -> app.getBookingManager().getBooking("XYZ")
                .orElseThrow(() -> new BookingException("Booking ID not found: XYZ")), "Expected BookingException to be thrown for non-existent booking ID.");
    }

}