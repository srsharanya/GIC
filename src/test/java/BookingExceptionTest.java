import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingExceptionTest {

    @Test
    void testBookingExceptionMessage() {
        String message = "Test error message";
        BookingException exception = new BookingException(message);
        assertEquals(message, exception.getMessage());
    }
}
