package View;

import BusinessLogic.SimulationManager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GraphicInterface extends JPanel {
    private JButton simulateBtn;
    private JLabel clientsLabel;
    private JLabel queuesLabel;
    private JTextArea outputTxtArea;
    private JTextField clientsTextBox;
    private JTextField queuesTextBox;
    private JTextField simTextBox;
    private JTextField arrLowerBound;
    private JLabel simTimeLabel;
    private JTextField arrUpperBound;
    private JTextField serviceLowerBound;
    private JTextField serviceUpperBound;
    private JLabel arrivalLabel;
    private JLabel boundsLabel;

    SimulationManager sm;

    private static GraphicInterface instance;

    public static GraphicInterface getInstance(){
        if(instance == null){
            instance = new GraphicInterface();
        }
        return instance;
    }

    private GraphicInterface() {
        //construct components
        simulateBtn = new JButton("Simulate");
        clientsLabel = new JLabel("Clients");
        queuesLabel = new JLabel("Queues");
        outputTxtArea = new JTextArea(5, 5);
        clientsTextBox = new JTextField(5);
        queuesTextBox = new JTextField(5);
        simTextBox = new JTextField(5);
        arrLowerBound = new JTextField(5);
        simTimeLabel = new JLabel("Simulation Time");
        arrUpperBound = new JTextField(5);
        serviceLowerBound = new JTextField(5);
        serviceUpperBound = new JTextField(5);
        arrivalLabel = new JLabel("Arrival Bounds");
        boundsLabel = new JLabel("Service Bounds");

        //adjust size and set layout
        setPreferredSize(new Dimension(944, 563));
        setLayout(null);

        //add components
        add(simulateBtn);
        add(clientsLabel);
        add(queuesLabel);
        add(outputTxtArea);
        add(clientsTextBox);
        add(queuesTextBox);
        add(simTextBox);
        add(arrLowerBound);
        add(simTimeLabel);
        add(arrUpperBound);
        add(serviceLowerBound);
        add(serviceUpperBound);
        add(arrivalLabel);
        add(boundsLabel);

        //set component bounds (only needed by Absolute Positioning)
        simulateBtn.setBounds(370, 280, 100, 20);
        clientsLabel.setBounds(15, 60, 100, 25);
        queuesLabel.setBounds(145, 60, 100, 25);
        outputTxtArea.setBounds(175, 315, 505, 205);
        clientsTextBox.setBounds(15, 90, 100, 25);
        queuesTextBox.setBounds(145, 90, 100, 25);
        simTextBox.setBounds(275, 90, 100, 25);
        arrLowerBound.setBounds(15, 170, 40, 25);
        simTimeLabel.setBounds(275, 60, 100, 25);
        arrUpperBound.setBounds(75, 170, 40, 25);
        serviceLowerBound.setBounds(145, 170, 40, 25);
        serviceUpperBound.setBounds(205, 170, 40, 25);
        arrivalLabel.setBounds(15, 140, 100, 25);
        boundsLabel.setBounds(145, 140, 100, 25);

        simulateBtn.addActionListener(new SimulateBtnListener());
    }

    public void writeTextArea(String txt){
        outputTxtArea.setText(txt);
    }

    private class SimulateBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int simTime        = 0;
            int nbQueues       = 0;
            int nbClients      = 0;
            int minArrivalTime = 0;
            int maxArrivalTime = 0;
            int minServTime    = 0;
            int maxServTime    = 0;

            try {
                simTime        = Integer.parseInt(simTextBox.getText());
                nbQueues       = Integer.parseInt(queuesTextBox.getText());
                nbClients      = Integer.parseInt(clientsTextBox.getText());
                minArrivalTime = Integer.parseInt(arrLowerBound.getText());
                maxArrivalTime = Integer.parseInt(arrUpperBound.getText());
                minServTime    = Integer.parseInt(serviceLowerBound.getText());
                maxServTime    = Integer.parseInt(serviceUpperBound.getText());
            }catch (NumberFormatException ex){
                System.err.println("Number format exception");
                return;
            }
            sm = new SimulationManager(simTime,nbQueues);
            sm.generateRandomTasks(nbClients,minArrivalTime,maxArrivalTime,minServTime,maxServTime);
            Thread t = new Thread(sm);
            t.start();
        }
    }
}