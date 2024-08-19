import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatManagerImpl implements SeatManager {
    private final SeatState[][] seatingChart;
    private final int numRows;
    private final int numCols;

    public SeatManagerImpl(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.seatingChart = new SeatState[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            Arrays.fill(seatingChart[i], SeatState.AVAILABLE);
        }
    }

    @Override
    public SeatState[][] getSeatingChart() {
        return seatingChart;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    @Override
    public boolean isSeatAvailable(int row, int col) {
        return seatingChart[row][col] == SeatState.AVAILABLE;
    }

    @Override
    public void bookSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            seatingChart[seat.getRow()][seat.getColumn()] = SeatState.BOOKED;
        }
    }

    @Override
    public void clearSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            seatingChart[seat.getRow()][seat.getColumn()] = SeatState.AVAILABLE;
        }
    }

    @Override
    public List<Seat> getAvailableSeats() {
        List<Seat> availableSeats = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (seatingChart[row][col] == SeatState.AVAILABLE) {
                    availableSeats.add(new Seat(row, col));
                }
            }
        }
        return availableSeats;
    }
}
