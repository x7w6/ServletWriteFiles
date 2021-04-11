package com.zacharyweaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


//BuggedWeek4 Class
public class DebugMe {

    // Declare file name/path
    public static final String FILE_PATH = "buggedWeek4.txt";
    private JFrame frame; //Instantiate JFrame object

    // Create buttons for saving the file and exiting window
    private JButton saveFile = new JButton("OK");
    private JButton exitWindow = new JButton("EXIT");

    // Create Label and Text field for input
    private JLabel inputLabel = new JLabel("Enter a number: ");
    private JTextField inputField = new JTextField(5);
    
    // Panels
    JPanel buttonPanel;

    // BuggedWeek4 Constructor
    public DebugMe() {
        // Set Frame parameters
        frame = new JFrame("BuggedWeek4");
        frame.setSize(450, 140);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Set JPanels for file save and exit buttons
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveFile);
        buttonPanel.add(exitWindow);

        // Set JPanel for text fields
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        // Set JPanels to frame
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(inputPanel, BorderLayout.NORTH);
    }

    //Method for action listeners
    public void launchBuggedWeek4(){
        //saveFile Action Listener
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isInt = true;
                int input = 0;
                try{
                    input = Integer.parseInt(inputField.getText());
                }
                catch(NumberFormatException nfe){
                    isInt = false;
                    JOptionPane.showMessageDialog(null,
                            "Please Enter a valid INTEGER for the input",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if(isInt){
                    writeToFile(input, inputField);
                }
            }
        });


    }

    private static void writeToFile(int input, JTextField inputField) {

        // Instantiate new file object
        File file = new File(FILE_PATH);
        // Check for input error
        try{
            //Instantiate new FileWriter object
            FileWriter fileWriter = new FileWriter(file, true);

            //BufferedWriter Object and text to write
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Your input: " + input +
                    "\nThanks for your input!");
            bufferedWriter.close();

            // Display successful file save to user
            JOptionPane.showMessageDialog(null,
                    "Your file has been saved");

            // Reset TextField
            inputField.setText("");
        }
        // If input exception detected
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); //print error
        }
    }

// -----------------------------------------------------------------------------------

    //Main Method
    public static void main(String[] args) {

        // Instantiate new BuggedWeek4 object
        DebugMe buggedWeek4 = new DebugMe();
        
        buggedWeek4.launchBuggedWeek4(); // Call on launch method

    }
}