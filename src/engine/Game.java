package engine;

public class Game {
    Board board;
    Score score;
    APlayer playerX, playerO, currentPlayer;


    public Game(String pXName, String pOName) {
        board = new Board();
        score = new Score();
        playerX = new Player(pXName, SquareState.X, false);
        playerO = new Player(pOName, SquareState.O, false);
        playerX = evaluatePlayerType(playerX);
        playerO = evaluatePlayerType(playerO);
        currentPlayer = playerX;
    }

    private APlayer evaluatePlayerType(APlayer player) {
        if (player.getName().equalsIgnoreCase("cpu")) {
            return new CPU(player.getSquareState());
        }
        return player;
    }

    public SquareState makeMove(int row, int col) throws IllegalAccessException {
        if (winner() != null) return SquareState.EMPTY;
        board.setSquare(row, col, currentPlayer.getSquareState());
        return currentPlayer.getSquareState();
    }

    public void swapPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public APlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public APlayer winner() {
        board.checkForWin();
        switch (board.getBoardState()) {
            case X_WINS:
                score.addPlayerX();
                return playerX;
            case O_WINS:
                score.addPlayerO();
                return playerO;
            case DRAW:
                score.addDraw();
                return new Player("draw", null, false);
        }
        return null;
    }

    public void newGame() {
        currentPlayer = playerX;
        this.board = new Board();
    }

    public void reset() {
        newGame();
        score.reset();
    }

    public void display() {
        board.display();
        System.out.println(board.getBoardState());
        System.out.println();
        score.display();
    }

    public Score getScore() {
        return score;
    }

    public String getPlayerXName() {
        return playerX.getName();
    }

    public String getPlayerOName() {
        return playerO.getName();
    }

    public Board getBoard() {
        return board;
    }
}
