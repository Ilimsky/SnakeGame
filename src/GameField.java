import org.omg.CORBA.TRANSACTION_MODE;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class GameField extends JPanel implements ActionListener{
    //Fields
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean rigth = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    //Constructor
    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
    }

    //Functions
    private void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++){
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    private void createApple() {
        appleX = new Random().nextInt(20 ) * DOT_SIZE;
        appleY = new Random().nextInt(20 ) * DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot =  iid.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple, appleX,  appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i],this);
            }
        }else {
            String str = "Game over";
            g.setColor(Color.WHITE);
            g.drawString(str, 125, SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        }if (rigth){
            x[0] += DOT_SIZE;
        }if (up){
            y[0] -= DOT_SIZE;
        }if (down){
            x[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame =false;
            }
        }
        if (x[0] > SIZE){
            inGame = false;
        }if (x[0] < 0){
            inGame = false;
        }if (y[0] > SIZE){
            inGame = false;
        }if (y[0] < 0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT ){
                left = true;
                rigth = false;
                up = false;
                down = false;
            }if (key == KeyEvent.VK_RIGHT){
                rigth = true;
                left = false;
                up = false;
                down = false;
            }if (key == KeyEvent.VK_UP){
                up = true;
                down = false;
                rigth = false;
                left = false;
            }if (key == KeyEvent.VK_DOWN) {
                down = true;
                up = false;
                rigth = false;
                left = false;
            }
    }

    }
}
