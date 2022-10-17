package client;

import javax.swing.*;

public class MainFrame extends JFrame{

    private JPanel mainPanel;

    public MainFrame(String appName){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();



    }



    public static void main(String[] args) {

        JFrame frame = new MainFrame("White Board");

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
