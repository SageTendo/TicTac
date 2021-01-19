package engine;

public class Score {
    int playerX;
    int playerO;
    int draw;

    /**
     * Initializes the Scores, setting the to 0.
     */
    public Score() {
        playerX = 0;
        playerO = 0;
        draw = 0;
    }

    /**
     * Increments the PlayerX value by 1
     */
    public void addPlayerX() {
        playerX += 1;
    }

    /**
     * Increments the PlayerO value by 1
     */
    public void addPlayerO() {
        playerO += 1;
    }

    /**
     * Increments the draw value by 1
     */
    public void addDraw() {
        draw += 1;
    }

    /**
     * Resets all the score values
     */
    public void reset() {
        playerX = playerO = draw = 0;
    }

    /**
     * @return int : The number of wins by PlayerX
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * @return int : The number of wins by PlayerO
     */
    public int getPlayerO() {
        return playerO;
    }

    /**
     * @return int : The number of draws
     */
    public int getDraw() {
        return draw;
    }

    /**
     * Displays the scoring information
     */
    public void display() {
        System.out.println(
                String.format("PlayerX: %5d", playerX)
        );
        System.out.println(
                String.format("PlayerO: %5d", playerO)
        );
        System.out.println(
                String.format("Draw: %5d", draw)
        );
    }
}
