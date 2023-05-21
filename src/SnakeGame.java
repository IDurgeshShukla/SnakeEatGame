import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        // initialising our board
        board = new Board();
        // add it to Board
        add(board);
        pack();
        //setResizable(false);
        setVisible(true);

    }

    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();
    }
}
