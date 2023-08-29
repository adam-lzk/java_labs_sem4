package laba6pack;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem pauseBlueItem;
    // Поле, по которому прыгают мячи
    private Field field = new Field();

    public MainFrame() {
        super("Многопоточное программирование");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();

        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() &&
                        !resumeMenuItem.isEnabled()) {

                    pauseMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение"){
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
                pauseBlueItem.setEnabled(false);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
                pauseBlueItem.setEnabled(true);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);


        Action pauseBlue = new AbstractAction("Приостановить синие") {
            public void actionPerformed(ActionEvent event) {
                field.pauseBlue();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(true);
                pauseBlueItem.setEnabled(false);
            }
        };
        pauseBlueItem = controlMenu.add(pauseBlue);
        pauseBlueItem.setEnabled(true);



        getContentPane().add(field, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();  // создание десктопного окна
        frame.setVisible(true);  // делает его видимым
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // для возможности закрывать окно
    }
}