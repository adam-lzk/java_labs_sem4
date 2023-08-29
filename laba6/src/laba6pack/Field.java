package laba6pack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Field extends JPanel {

    private boolean paused;
    private boolean pausedBlue;

    private ArrayList<laba6pack.BouncingBall> balls = new ArrayList<laba6pack.BouncingBall>(10);

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {

            repaint();
        }
    });


    public Field() {

        setBackground(Color.WHITE);

        repaintTimer.start();
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;

        for (laba6pack.BouncingBall ball : balls) {
            ball.paint(canvas);
        }
    }

    public void addBall() {

        balls.add(new laba6pack.BouncingBall(this));
    }

    public synchronized void pause() {

        paused = true;
    }
    public synchronized void pauseBlue() {

        pausedBlue = true;
    }

    public synchronized void resume() {

        paused = false;
        pausedBlue = false;

        notifyAll();
    }

    public synchronized void canMove(laba6pack.BouncingBall ball) throws
            InterruptedException {
        if (paused) {

            wait();
        }

        if (pausedBlue && (2*(ball.getGreen()+ball.getRed())) < ball.getBlue()){
            wait();  //  приостановить шары, в которых в два раза больше компонент синего, чем красного и зелёного
        }
    }
}