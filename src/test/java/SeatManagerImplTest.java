import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SeatManagerImplTest {

    private SeatManagerImpl seatManager;

    @BeforeEach
    void setUp() {
        seatManager = new SeatManagerImpl(5, 5);
    }

    @Test
    void testGetSeatingChart() {
        SeatState[][] chart = seatManager.getSeatingChart();
        assertNotNull(chart);
        assertEquals(5, chart.length);
        assertEquals(5, chart[0].length);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(SeatState.AVAILABLE, chart[i][j]);
            }
        }
    }

    @Test
    void testIsSeatAvailable() {
        assertTrue(seatManager.isSeatAvailable(1, 1));
        seatManager.bookSeats(List.of(new Seat(1, 1)));
        assertFalse(seatManager.isSeatAvailable(1, 1));
    }

    @Test
    void testBookSeats() {
        List<Seat> seats = List.of(new Seat(1, 1), new Seat(1, 2));
        seatManager.bookSeats(seats);
        assertFalse(seatManager.isSeatAvailable(1, 1));
        assertFalse(seatManager.isSeatAvailable(1, 2));
    }

    @Test
    void testClearSeats() {
        List<Seat> seats = List.of(new Seat(1, 1));
        seatManager.bookSeats(seats);
        seatManager.clearSeats(seats);
        assertTrue(seatManager.isSeatAvailable(1, 1));
    }

    @Test
    void testGetAvailableSeats() {
        List<Seat> availableSeats = seatManager.getAvailableSeats();
        assertEquals(25, availableSeats.size());
    }
}
