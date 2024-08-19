// Constants.java
public final class Constants {
    // Prefix for booking IDs
    public static final String BOOKING_ID_PREFIX = "GIC";

    // Error messages
    public static final String ERROR_NULL_OR_EMPTY_SEAT_LIST = "Cannot create or modify booking with null or empty seat list.";
    public static final String ERROR_INVALID_BOOKING_ID = "Invalid booking ID.";
    public static final String ERROR_BOOKING_ID_NOT_FOUND = "Booking ID not found: ";

    // Booking ID format
    public static final String BOOKING_ID_FORMAT = "%04d";

    public static final String INPUT_PROMPT = "Please define movie title and seating map in [title] [rows] [seatsPerRow] format:";
    public static final String INVALID_INPUT_FORMAT = "Please enter valid input format.";
    public static final String INVALID_NUMBER_FORMAT = "Please enter valid number format.";
    public static final String INVALID_ROWS_COLS = "Invalid number of rows or columns. Rows must be between 1 and 26, and columns between 1 and 50.";
    public static final String WELCOME_MESSAGE = "Welcome to GIC Cinemas";
    public static final String BOOK_TICKETS = "[1] Book Tickets for %s (%d seats available) %n";
    public static final String CHECK_BOOKINGS = "[2] Check Bookings";
    public static final String EXIT = "[3] Exit";
    public static final String SELECTED_SEATS = "Selected Seats:";
    public static final String PLEASE_ENTER = "Please enter your selection:";

    public static final String INVALID_OPTION = "Invalid option, please try again.";
    public static final String PROMPT_ENTER_TICKETS = "Enter number of tickets to book, or enter blank to go back to main menu:";
    public static final String PROMPT_ENTER_BOOKING_ID = "Enter your booking ID:";
    public static final String NOT_ENOUGH_SEATS_MESSAGE = "Sorry, there are only %d seats are available.%n";
    public static final String BOOKING_SUCCESSFUL_MESSAGE = "Successfully reserved %d %s tickets. %n";

    public static final String BOOKING_ID = "Booking id: %s %n";
    public static final String INVALID_BOOKING_ID_MESSAGE = "Please enter valid Booking id.";
    public static final String INVALID_NUMBER_MESSAGE = "Please enter a number.";
    public static final String INVALID_COLUMN_NUMBER_MESSAGE = "Please enter valid column number.";
    public static final String START_POSITION_OUT_OF_BOUNDS_MESSAGE = "Starting position is out of bounds.";
    public static final String COULD_NOT_FIND_SEATS_MESSAGE = "Could not find enough seats for the modification.";
    public static final String PROMPT_PRESS_ENTER_OR_MODIFY = "Enter blank to accept seat selection, or enter new seating position: ";
    public static final String EXITING_MESSAGE = "Exiting...";

    public static final String BOOKING_CONFIRMED = "Booking id: %s confirmed. %n";

    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
}
