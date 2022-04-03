import BusinessLogic.SimulationManager;
import View.GraphicInterface;

import javax.swing.*;


public class Main {

    public static void main(String[] args){

        JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (GraphicInterface.getInstance());
        frame.pack();
        frame.setResizable(false);
        frame.setVisible (true);
    }
}
