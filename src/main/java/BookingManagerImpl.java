import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple implementation of the BookingManager interface.
 * Manages booking records and provides methods to create, retrieve, and modify bookings.
 */
public class BookingManagerImpl implements BookingManager {
    private final Map<String, List<Seat>> bookings = new ConcurrentHashMap();
    private static final AtomicInteger counter = new AtomicInteger(1);


    public static String generateBookingId() {
        int idNumber = counter.getAndIncrement();
        return Constants.BOOKING_ID_PREFIX + String.format(Constants.BOOKING_ID_FORMAT, idNumber);
    }

    @Override
    public String createBooking(List<Seat> seats) throws BookingException {
        if (seats == null || seats.isEmpty()) {
            throw new BookingException(Constants.ERROR_NULL_OR_EMPTY_SEAT_LIST);
        }

        String bookingId = generateBookingId();
        bookings.put(bookingId, seats);
        return bookingId;
    }

    @Override
    public Optional<List<Seat>> getBooking(String bookingId) throws BookingException {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new BookingException(Constants.ERROR_INVALID_BOOKING_ID);
        }

        if (!bookings.containsKey(bookingId)) {
            throw new BookingException(Constants.ERROR_BOOKING_ID_NOT_FOUND + bookingId);
        }

        List<Seat> seats = bookings.get(bookingId);
        return Optional.ofNullable(seats);
    }

    @Override
    public void modifyBooking(String bookingId, List<Seat> newSeats) throws BookingException {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new BookingException(Constants.ERROR_INVALID_BOOKING_ID);
        }

        if (!bookings.containsKey(bookingId)) {
            throw new BookingException(Constants.ERROR_BOOKING_ID_NOT_FOUND + bookingId);
        }

        if (newSeats == null || newSeats.isEmpty()) {
            throw new BookingException(Constants.ERROR_NULL_OR_EMPTY_SEAT_LIST);
        }

        bookings.put(bookingId, newSeats);
    }
}
