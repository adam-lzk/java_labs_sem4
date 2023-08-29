import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private static final String FRAME_TITLE = "Instant messaging client";
    private static final int FRAME_MINIMUM_WIDTH = 500;
    private static final int FRAME_MINIMUM_HEIGHT = 500;
    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;
    private static final int SERVER_PORT = 8126;
    private final JTextField textFieldFrom;
    private final JTextField textFieldTo;
    private final JTextArea textAreaIncoming;
    private final JTextArea textAreaOutgoing;

    private Action Offline;

    private Action Online;

    private Boolean IsActive = true;

    private final JMenuBar menubar = new JMenuBar();
    public MainFrame() {  // создание окна
        super(FRAME_TITLE);
        setMinimumSize(
                new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));

        final Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - getWidth()) / 2,
                (kit.getScreenSize().height - getHeight()) / 2);

        textAreaIncoming = new JTextArea(INCOMING_AREA_DEFAULT_ROWS, 0);
        textAreaIncoming.setEditable(false);

        setJMenuBar(menubar);

        JMenu ActiveMenu = new JMenu("Status");  // создание меню
        Online = new AbstractAction("Online") {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsActive = true;
                Offline.setEnabled(true);
                Online.setEnabled(false);
            }
        };
        Offline = new AbstractAction("Offline") {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsActive = false;
                Online.setEnabled(true);
                Offline.setEnabled(false);
            }
        };

        menubar.add(ActiveMenu);
        ActiveMenu.add(Online);
        ActiveMenu.add(Offline);
        Online.setEnabled(false);

        final JScrollPane scrollPaneIncoming =
                new JScrollPane(textAreaIncoming);

        final JLabel labelFrom = new JLabel("Sender's name");
        final JLabel labelTo = new JLabel("Recipient's address");

        textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);  // поля ввода
        textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);

        textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);
        textAreaOutgoing.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    sendMessage();
                }
            }
        });

        final JScrollPane scrollPaneOutgoing = new JScrollPane(textAreaOutgoing);

        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(
                BorderFactory.createTitledBorder("Message"));

        final JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        final GroupLayout layout2 = new GroupLayout(messagePanel);  // размещение элементов в окне
        messagePanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout2.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldFrom)
                                .addGap(LARGE_GAP)
                                .addComponent(labelTo)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldTo))
                        .addComponent(scrollPaneOutgoing)
                        .addComponent(sendButton))
                .addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFrom)
                        .addComponent(textFieldFrom)
                        .addComponent(labelTo)
                        .addComponent(textFieldTo))
                .addGap(MEDIUM_GAP)
                .addComponent(scrollPaneOutgoing)
                .addGap(MEDIUM_GAP)
                .addComponent(sendButton)
                .addContainerGap());

        final GroupLayout layout1 = new GroupLayout(getContentPane());
        setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout1.createParallelGroup()
                        .addComponent(scrollPaneIncoming)
                        .addComponent(messagePanel))
                .addContainerGap());
        layout1.setVerticalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneIncoming)
                .addGap(MEDIUM_GAP)
                .addComponent(messagePanel)
                .addContainerGap());

        new Thread(new Runnable() {  // создание потока
            @Override
            public void run() {
                try {
                    final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);  // создание серверного сокета, программв будет висеть и выполнится на этом порте
                    while (!Thread.interrupted()) {
                        final Socket socket = serverSocket.accept();
                        final DataInputStream in = new DataInputStream(socket.getInputStream());  // для получения информации от клиента

                        /*Date currentDate = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                        String time = format.format(currentDate);
                        System.out.println(time);*/

                        final String senderName = in.readUTF();  // пришедшее имя
                        final String message = in.readUTF();  // сообщение
                        socket.close();
                        final String address =
                                ((InetSocketAddress) socket
                                        .getRemoteSocketAddress())
                                        .getAddress()
                                        .getHostAddress();
                        textAreaIncoming.append(senderName +
                                " (" + address + "): " + //"[" + time + "] " +
                                message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Server error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private void sendMessage() {  // отправление сообщения клиенту
        try {

            final String senderName = textFieldFrom.getText();
            final String destinationAddress = textFieldTo.getText();
            final String message = textAreaOutgoing.getText();

            if (senderName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter the sender's name", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (destinationAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter destination host address", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter the message", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            final Socket socket =
                    new Socket(destinationAddress, SERVER_PORT);
            final DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());
            out.writeUTF(senderName);
            out.writeUTF(message);
            socket.close();
            String str = null;
            if(IsActive){
                str = "Online";
            } else {
                str = "Offline";
            }
            textAreaIncoming.append("Me -> " + "{" + str + "} " + destinationAddress + ": "
                    + message + "\n");
            textAreaOutgoing.setText("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Failed to send message: destination host address is not found",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Failed to send message", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
