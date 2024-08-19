import java.util.List;
import java.util.Optional;

/**
 * Interface for managing bookings.
 */
public interface BookingManager {

    /**
     * Creates a new booking with the given seats.
     *
     * @param seats the list of seats to book
     * @return a unique booking ID for the created booking
     * @throws BookingException if the booking fails
     */
    String createBooking(List<Seat> seats) throws BookingException;

    /**
     * Retrieves the seats for a given booking ID.
     *
     * @param bookingId the ID of the booking to retrieve
     * @return an Optional containing the list of seats if the booking exists, or an empty Optional if not
     * @throws BookingException if the booking retrieval fails
     */
    Optional<List<Seat>> getBooking(String bookingId) throws BookingException;

    /**
     * Modifies an existing booking with new seats.
     *
     * @param bookingId the ID of the booking to modify
     * @param newSeats the new list of seats for the booking
     * @throws BookingException if the booking modification fails
     */
    void modifyBooking(String bookingId, List<Seat> newSeats) throws BookingException;
}
