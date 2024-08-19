public enum SeatState {
    AVAILABLE('.'),
    BOOKED('*'),
    TEMP_BOOKED('#');

    private final char symbol;

    SeatState(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static SeatState fromSymbol(char symbol) {
        for (SeatState state : values()) {
            if (state.getSymbol() == symbol) {
                return state;
            }
        }
        throw new IllegalArgumentException("Invalid seat symbol: " + symbol);
    }
}