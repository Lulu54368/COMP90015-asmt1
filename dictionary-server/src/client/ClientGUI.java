package client;

import server.Operation;
import server.ServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
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


    public ClientGUI() {
        this.initialiseGUI();

        setOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(operationOption.getSelectedIndex());
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
                new Thread(()->ClientServer.submitRequest(operationInUse, wordInUse, defInUse)).start();
                JOptionPane.showMessageDialog(ClientGUI.this, "Successfully submit the request!" );



            }
        });
    }

    public void setResultTextArea(String result) {
        this.resultTextArea.setText(result);
    }

    private void initialiseGUI() {
        System.out.println("initialize the gui...");
        setContentPane(clientPanel);
        setTitle("Dictionary Client GUI");
        setSize(640, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
