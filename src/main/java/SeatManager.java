import java.util.List;

/**
 * Manages the seating arrangement for a booking system.
 * Provides methods to access, book, and manage seats in a seating chart.
 */
public interface SeatManager {

    /**
     * Retrieves the current seating chart.
     *
     * @return a 2D array representing the seating chart where each element
     *         is an instance of {@link SeatState} indicating the state of each seat.
     */
    SeatState[][] getSeatingChart();

    /**
     * Gets the number of columns in the seating chart.
     *
     * @return the number of columns in the seating chart.
     */
    int getNumCols();

    /**
     * Checks if a specific seat is available for booking.
     *
     * @param row the row index of the seat (0-based).
     * @param col the column index of the seat (0-based).
     * @return true if the seat is available; false otherwise.
     */
    boolean isSeatAvailable(int row, int col);

    /**
     * Books a list of seats, marking them as occupied.
     *
     * @param seats a list of {@link Seat} objects to be booked.
     * @throws IllegalArgumentException if any seat in the list is not available.
     */
    void bookSeats(List<Seat> seats);

    /**
     * Clears the booking status of a list of seats, marking them as available.
     *
     * @param seats a list of {@link Seat} objects to be cleared.
     * @throws IllegalArgumentException if any seat in the list is not currently booked.
     */
    void clearSeats(List<Seat> seats);

    /**
     * Retrieves a list of all available seats.
     *
     * @return a list of {@link Seat} objects representing all available seats.
     */
    List<Seat> getAvailableSeats();
}
