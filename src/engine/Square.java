package engine;

public class Square {
    private SquareState state = SquareState.EMPTY;

    /**
     * Sets the state of the square
     * @param state : The provided engine.Square State
     */
    public void setState(SquareState state) {
        this.state = state;
    }

    /**
     * @return engine.SquareState : The state of the square
     */
    public SquareState getState() {
        return this.state;
    }
}
