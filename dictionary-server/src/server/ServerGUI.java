package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerGUI extends JFrame {

    private boolean isServerStart = false;
    private int threadNumber;

    private ServerSocket serverSocket;
    private JLabel threadNumberLabel;
    private JTextField threadNumberField;
    private JButton startButton;
    private JPanel serverGUIPanel;
    private JButton threadNumberSetButton;
    private final int DEFAULT_THREAD_NUMBER=10;
    private Thread serverThread;
    public ServerGUI() {
        this.threadNumber = 0;
        this.serverSocket = null;
        this.initialiseGUI();

        // try to shut down the server when window is closed by the user
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException ioe) {
                        System.err.println("ServerError: unable to close socket");
                    }
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startButton.getText() == "start" && isServerStart == false){
                    serverThread = new Thread(()->DictionaryServer.startServer());
                    serverThread.start();
                    isServerStart = true;
                    JOptionPane.showMessageDialog(ServerGUI.this,
                            "The server started!" );
                    startButton.setText("stop");
                }
                else if(startButton.getText() == "stop" && isServerStart == true){
                    serverThread.interrupt();
                    serverThread = new Thread(()->DictionaryServer.stopServer());
                    serverThread.start();
                    JOptionPane.showMessageDialog(ServerGUI.this,
                            "The server stopped!" );
                    isServerStart = false;
                    startButton.setText("start");
                }

            }
        });

        threadNumberSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(threadNumberField.getText()) <= 0){
                    JOptionPane.showMessageDialog(ServerGUI.this,
                            "Please enter a number larger than 0!" );
                    threadNumberField.setText(String.valueOf(DEFAULT_THREAD_NUMBER));
                    return;
                }
                threadNumber = Integer.parseInt(threadNumberField.getText());
                DictionaryServer.setThreadNumber(threadNumber);
                JOptionPane.showMessageDialog(ServerGUI.this,
                        "The thread number is successfully set to "+threadNumber );
            }
        });
    }


    public boolean isServerStart() {
        return isServerStart;
    }

    /** Prepare and display the client GUI. */
    private void initialiseGUI() {
        System.out.println("initialize the gui...");
        setContentPane(serverGUIPanel);
        setTitle("Dictionary Server GUI");
        setSize(640, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


}
