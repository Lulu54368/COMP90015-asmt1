package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Operation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ClientGUI extends JFrame{
    private Operation operationInUse;
    private String wordInUse;
    private String defInUse;
    private JLabel operation;
    private JComboBox operationOption;

    private JLabel wordLabel;
    private JTextField wordText;
    private JButton setOperationButton;
    private JButton setWordButton;
    private JLabel definition;
    private JTextField definitionText;
    private JButton setDefinitionButton;
    private JButton submitRequestButton;
    private JTextArea resultTextArea;
    private JLabel resultLabel;
    private JPanel clientPanel;

    Logger logger = LogManager.getLogger(ClientGUI.class);
    public ClientGUI() {
        this.initialiseGUI();

        setOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationInUse = Arrays.asList(Operation.GET, Operation.CREATE, Operation.UPDATE, Operation.DELETE)
                        .get(operationOption.getSelectedIndex());
                JOptionPane.showMessageDialog(ClientGUI.this, "Successfully set the operation!" );
            }
        });
        setWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordInUse = wordText.getText();
                JOptionPane.showMessageDialog(ClientGUI.this, "Successfully set the word!" );

            }
        });
        setDefinitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defInUse = definitionText.getText();
                JOptionPane.showMessageDialog(ClientGUI.this, "Successfully set the definition!" );

            }
        });
        submitRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread submitThread = new Thread(()->ClientServer.submitRequest(operationInUse, wordInUse, defInUse));
                    submitThread.start();
                    submitThread.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(ClientGUI.this, "Successfully submit the request!" );



            }
        });
    }

    public void setResultTextArea(String result) {
        this.resultTextArea.setText(result);
    }

    private void initialiseGUI() {
        logger.info("initialize the gui...");
        setContentPane(clientPanel);
        setTitle("Dictionary Client GUI");
        setSize(640, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
