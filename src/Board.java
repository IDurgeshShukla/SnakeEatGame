import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_Width = 400;
    int B_height = 400;
    int max_dots = 1600;
    int dot_size = 10;
    int dots;
    int x[] = new int[max_dots];
    int y[] = new int[max_dots];

    int appleX;
    int appleY;
    // Images
    Image body, head, apple;
    Timer timer;
    int delay = 200;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean ingame = true;

    Board() {
        setVisible(true);
        setPreferredSize(new Dimension(B_Width, B_height));
        setBackground(Color.blue);
        intializeGame();
        loadImages();

        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);

    }
   Board(boolean exit){
       setVisible(true);
       setPreferredSize(new Dimension(B_Width, B_height));
       setBackground(Color.yellow);
   }
    public void intializeGame() {
        dots = 3;
        x[0] = 350;
        y[0] = 300;
        for (int i = 1; i < dots; i++) {
            x[i] = x[0] + dot_size * i;
            y[i] = y[0];
        }
        locateApple();

        timer = new Timer(delay, this); // every 0.3 sec timer is increamented
        timer.start();
        checkApple();

        // intialise Apple

    }

    // load images from resources
    public void loadImages() {
        ImageIcon bodyIcon = new ImageIcon("src/resourses/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resourses/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resourses/apple.png");
        apple = appleIcon.getImage();
    }

    // draw image
    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        g.drawImage(apple, appleX, appleY, this);
        if(ingame) {
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
        else {
            setBackground(Color.red);

            DisPlayGameOver(g);

            timer.stop();
        }
    }

    public void locateApple() {
        appleX = (int) (Math.random() * 39) * dot_size;

        appleY = (int) (Math.random() * 39) * dot_size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkApple();
        move();
        checkGameOver();
        repaint();

    }
    public void move() {
        for (int i = dots-1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) x[0] -= dot_size;
        if (rightDirection) x[0] += dot_size;
        if (upDirection) y[0] -= dot_size;
        if (downDirection) y[0] += dot_size;
    }
    public void checkApple(){
        if (appleX == x[0] && appleY == y[0]){
            dots++;
            locateApple();
        }
    }
    // gameOver
    public void checkGameOver(){
        for (int i = 1; i < dots; i++){
            if ( i > 4 && x[0] == x[i] && y[0] == y[i]){
                ingame = false;
            }
            if (x[0] < 0 || x[0] >= B_Width) ingame = false;
            if(y[0] < 0 || y[0] >= B_height) ingame = false;
        }
    }
    public void DisPlayGameOver(Graphics g){
        int score = (dots - 3)*10;
        String msg = "Game Over";
        String scoremsg = "Your Score is :"+ Integer.toString(score);
        Font small = new Font("ArialBold", Font.BOLD,20);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.white);
        g.drawString(msg, (B_Width - fontMetrics.stringWidth(msg))/2 ,B_height/4);
        g.drawString(scoremsg, (B_Width - fontMetrics.stringWidth(scoremsg))/2 ,3*B_height/4);

    }
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && ! rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && ! leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && ! downDirection){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && !upDirection){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}
