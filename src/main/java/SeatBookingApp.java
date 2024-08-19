import java.util.*;

public class SeatBookingApp {
    private SeatManager seatManager;
    private Movie movie;
    private BookingManager bookingManager;
    private Scanner scanner;

    public SeatManager getSeatManager() {
        return seatManager;
    }

    public Movie getMovie() {
        return movie;
    }

    public BookingManager getBookingManager() {
        return bookingManager;
    }

    public SeatBookingApp(Scanner scanner) {
        this.scanner = scanner;
    }
    public static void main(String[] args) {
        SeatBookingApp app = new SeatBookingApp(new Scanner(System.in));
        app.initialize();
        app.runApp();
    }

    void initialize() {
        System.out.println(Constants.INPUT_PROMPT);
        String[] inputParts = scanner.nextLine().split(" ");

        if (inputParts.length != 3) {
            System.out.println(Constants.INVALID_INPUT_FORMAT);
            return;
        }

        try {
            movie = new Movie(inputParts[0]);
            int numRows = Integer.parseInt(inputParts[1]);
            int numCols = Integer.parseInt(inputParts[2]);

            if (!isValidSize(numRows, numCols)) {
                System.out.println(Constants.INVALID_ROWS_COLS);
                return;
            }

            seatManager = new SeatManagerImpl(numRows, numCols);
            bookingManager = new BookingManagerImpl();
        } catch (NumberFormatException e) {
            System.out.println(Constants.INVALID_NUMBER_FORMAT);
        }
    }

    private static boolean isValidSize(int numRows, int numCols) {
        return numRows >= 1 && numRows <= 26 && numCols >= 1 && numCols <= 50;
    }

     void runApp() {
        while (true) {
            int userChoice = getUserChoice();
            switch (userChoice) {
                case 1 -> handleNewBooking();
                case 2 -> handleViewBooking();
                case 3 -> {
                    System.out.println(Constants.EXITING_MESSAGE);
                    return;
                }
                default -> System.out.println(Constants.INVALID_OPTION);
            }
        }
    }

    private int getUserChoice() {
        int availableSeats = countAvailableSeats();
        System.out.println(Constants.WELCOME_MESSAGE);
        System.out.printf(Constants.BOOK_TICKETS, movie.title(), availableSeats);
        System.out.println(Constants.CHECK_BOOKINGS);
        System.out.println(Constants.EXIT);
        System.out.print(Constants.PLEASE_ENTER);

        return getValidUserChoice();
    }

    private int getValidUserChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
                System.out.println(Constants.INVALID_OPTION);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_NUMBER_MESSAGE);
            }
        }
    }

    void handleNewBooking() {
        System.out.println(Constants.PROMPT_ENTER_TICKETS);
        int numTickets = getPositiveIntegerInput();

        if (!areEnoughSeatsAvailable(numTickets)) {
            return;
        }

        List<Seat> bookedSeats = new ArrayList<>(Objects.requireNonNull(findSeats(numTickets)));
        String bookingId = bookingManager.createBooking(bookedSeats);
        seatManager.bookSeats(bookedSeats);
        displayBookingDetails(numTickets, bookingId);
        handleUserResponse(bookingId);
    }

    private boolean areEnoughSeatsAvailable(int numTickets) {
        int availableSeats = countAvailableSeats();
        if (numTickets > availableSeats) {
            System.out.printf(Constants.NOT_ENOUGH_SEATS_MESSAGE, availableSeats);
            return false;
        }
        return true;
    }

    private  void displayBookingDetails(int numTickets, String bookingId) {
        System.out.printf(Constants.BOOKING_SUCCESSFUL_MESSAGE, numTickets, movie.title());
        System.out.printf(Constants.BOOKING_ID, bookingId);
        System.out.println(Constants.SELECTED_SEATS);
        displaySeatingChart(seatManager.getSeatingChart());
    }

    private  int getPositiveIntegerInput() {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    return number;
                }
                System.out.println(Constants.INVALID_NUMBER_MESSAGE);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_NUMBER_MESSAGE);
            }
        }
    }

    private  void handleViewBooking() {
        System.out.println(Constants.PROMPT_ENTER_BOOKING_ID);
        String bookingId = scanner.nextLine();

        Optional<List<Seat>> bookedSeatsOpt = bookingManager.getBooking(bookingId);
        if (bookedSeatsOpt.isEmpty()) {
            System.out.println(Constants.INVALID_BOOKING_ID_MESSAGE);
            return;
        }

        List<Seat> bookedSeats = bookedSeatsOpt.get();
        SeatState[][] tempSeatingChart = copySeatingChart(seatManager.getSeatingChart());
        markSeatsAsViewed(tempSeatingChart, bookedSeats);
        displaySeatingChart(tempSeatingChart);
    }

    private  Set<Seat> findSeats(int numTickets) {
        Set<Seat> bookedSeats = new HashSet<>();
        SeatState[][] seatingChart = seatManager.getSeatingChart();

        for (int i = seatingChart.length - 1; i >= 0; i--) {
            if (attemptToFindSeatsForRow(seatingChart, i, numTickets, bookedSeats)) {
                return bookedSeats;
            }
        }
        return bookedSeats.size() == numTickets ? bookedSeats : null;
    }

    private  boolean attemptToFindSeatsForRow(SeatState[][] seatingChart, int row, int numTickets, Set<Seat> bookedSeats) {
        int startCol = seatingChart[0].length / 2;
        int leftCol = startCol;
        int rightCol = startCol;

        while (leftCol >= 0 || rightCol < seatingChart[0].length) {
            if (tryAddSeat(bookedSeats, row, leftCol, numTickets)) return true;
            if (tryAddSeat(bookedSeats, row, rightCol, numTickets)) return true;

            leftCol--;
            rightCol++;
        }
        return false;
    }

    private  boolean tryAddSeat(Set<Seat> seatsList, int row, int col, int numTickets) {
        SeatState[][] seatingChart = seatManager.getSeatingChart();
        if (col >= 0 && col < seatingChart[0].length && seatManager.isSeatAvailable(row, col)) {
            seatsList.add(new Seat(row, col));
            return seatsList.size() == numTickets;
        }
        return false;
    }

    private  void modifyBooking(String startPosition, String bookingId) {
        int[] coords = parseStartPosition(startPosition);
        if (coords == null) {
            System.out.println(Constants.INVALID_COLUMN_NUMBER_MESSAGE);
            return;
        }

        int startRow = coords[0];
        int startCol = coords[1];

        if (!isValidSeatPosition(startRow, startCol)) {
            System.out.println(Constants.START_POSITION_OUT_OF_BOUNDS_MESSAGE);
            return;
        }

        Optional<List<Seat>> oldBookedSeatsOpt = bookingManager.getBooking(bookingId);
        if (oldBookedSeatsOpt.isEmpty()) {
            System.out.println(Constants.INVALID_BOOKING_ID_MESSAGE);
            return;
        }

        List<Seat> oldBookedSeats = oldBookedSeatsOpt.get();
        seatManager.clearSeats(oldBookedSeats);

        List<Seat> newBookedSeats = findSeatsForModification(startRow, startCol, oldBookedSeats.size());

        if (newBookedSeats.size() < oldBookedSeats.size()) {
            System.out.println(Constants.COULD_NOT_FIND_SEATS_MESSAGE);
        } else {
            seatManager.bookSeats(newBookedSeats);
            bookingManager.modifyBooking(bookingId, newBookedSeats);

            // Show updated booking and seating chart
            System.out.printf(Constants.BOOKING_ID, bookingId);
            System.out.println(Constants.SELECTED_SEATS);
            displaySeatingChart(seatManager.getSeatingChart());

            // Do not call handleUserResponse here; it's handled in the loop in handleUserResponse
        }
    }

    private  void handleUserResponse(String bookingId) {
        while (true) {
            // Prompt user for modification or to accept the booking
            System.out.println(Constants.PROMPT_PRESS_ENTER_OR_MODIFY);
            String response = scanner.nextLine().trim();

            if (response.isEmpty()) {
                return; // Exit the loop if the response is empty (user accepts the booking)
            }

            // Modify the booking based on user input
            modifyBooking(response, bookingId);

            // After modification, display updated seating chart
            System.out.printf(Constants.BOOKING_ID, bookingId);
            System.out.println(Constants.SELECTED_SEATS);
            displaySeatingChart(seatManager.getSeatingChart());
        }
    }


    private  int[] parseStartPosition(String startPosition) {
        if (startPosition.length() < 2) return null;

        char rowChar = Character.toUpperCase(startPosition.charAt(0));
        int colNum;
        try {
            colNum = Integer.parseInt(startPosition.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }
        return new int[]{seatManager.getSeatingChart().length - 1 - (rowChar - 'A'), colNum - 1};
    }

    private  boolean isValidSeatPosition(int row, int col) {
        return row >= 0 && row < seatManager.getSeatingChart().length && col >= 0 && col < seatManager.getSeatingChart()[0].length;
    }

    private static void displaySeatingChart(SeatState[][] chart) {
        for (int i = 0; i < chart.length; i++) {
            System.out.print((char) ('A' + (chart.length - 1 - i)) + " ");
            for (SeatState seat : chart[i]) {
                System.out.print(seat.getSymbol() + " ");
            }
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 1; i <= chart[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static SeatState[][] copySeatingChart(SeatState[][] original) {
        SeatState[][] copy = new SeatState[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    private  void markSeatsAsViewed(SeatState[][] chart, List<Seat> seats) {
        for (Seat seat : seats) {
            chart[seat.row()][seat.column()] = SeatState.TEMP_BOOKED;
        }
    }

    private  int countAvailableSeats() {
        int count = 0;
        SeatState[][] seatingChart = seatManager.getSeatingChart();
        for (SeatState[] row : seatingChart) {
            for (SeatState seat : row) {
                if (seat == SeatState.AVAILABLE) {
                    count++;
                }
            }
        }
        return count;
    }

    private  List<Seat> findSeatsForModification(int startRow, int startCol, int numSeats) {
        List<Seat> newBookedSeats = new ArrayList<>();
        SeatState[][] seatingChart = seatManager.getSeatingChart();
        boolean seatsPlaced = false;

        for (int i = startRow; i >= 0 && !seatsPlaced; i--) {
            int col = startCol;
            while (col < seatManager.getNumCols() && newBookedSeats.size() < numSeats) {
                if (seatingChart[i][col] == SeatState.AVAILABLE) {
                    newBookedSeats.add(new Seat(i, col));
                }
                col++;
            }
            if (newBookedSeats.size() < numSeats) {
                startCol = 0;
            } else {
                seatsPlaced = true;
            }
        }
        return newBookedSeats;
    }
}
