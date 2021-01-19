package engine;

public class Player extends APlayer {
    public Player(String name, SquareState squareState, boolean isCPU) {
        super(name, squareState, isCPU);
    }

    @Override
    public Move playMove(Board board) {
        return null;
    }
}
