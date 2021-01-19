package engine;

public abstract class APlayer {
    private final String name;
    private SquareState squareState;
    private boolean isCPU;

    public APlayer(String name, SquareState squareState, boolean isCPU) {
        this.name = name;
        this.squareState = squareState;
        this.isCPU = isCPU;
    }

    /**
     * @return String : the name of the player
     */
    public String getName() {
        return name;
    }

    public SquareState getSquareState() {
        return squareState;
    }

    public void setSquareState(SquareState squareState) {
        this.squareState = squareState;
    }

    public abstract Move playMove(Board board) throws IllegalAccessException;

    public boolean isCPU() {
        return isCPU;
    }
}
