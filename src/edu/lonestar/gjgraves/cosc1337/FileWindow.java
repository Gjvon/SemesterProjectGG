package edu.lonestar.gjgraves.cosc1337;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by ${Gjvon} on 5/8/2016.
 */
public class FileWindow extends JFrame {
    private final int WINDOW_WIDTH = 650;
    private final int WINDOW_HEIGHT = 490;
    private String title;
    private Document documentObject;
    private JTextField file_path;
    private JTextArea fileText;
    private JButton btnStats;
    private JRadioButton normalTextView;
    private JRadioButton parsedTextView;
    private JRadioButton sortedTextView;
    ButtonGroup displayGroup;

    public FileWindow(String title) {
        this.title = title;
        setTitle(title);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // add controls
        buildWindow();
        pack();
        setLocationRelativeTo(null); // centers the window on the screen
        setVisible(true);
    }

    private void buildWindow() {
        // fill in the NORTH section with the file path
        JPanel filePathPanel = new JPanel();
        JLabel labelPath = new JLabel("Enter input file path: ");
        file_path = new JTextField(36); // using class field so accessible by event
        // handler
        JButton btnOpen = new JButton("Open");
        btnOpen.addActionListener(new OpenButtonListener());
        filePathPanel.add(labelPath);
        filePathPanel.add(file_path);
        filePathPanel.add(btnOpen);
        add(filePathPanel, BorderLayout.NORTH);

        // fill in the CENTER with the file display
        JPanel fileInfoPanel = new JPanel();
        fileText = new JTextArea();
        fileText.setRows(20);
        fileText.setEditable(false);
        JScrollPane scrollFileInfo = new JScrollPane(fileText);
        scrollFileInfo.setPreferredSize(new Dimension(600, 300));
        fileInfoPanel.add(scrollFileInfo);
        add(fileInfoPanel, BorderLayout.CENTER);

        // fill in the bottom with options
        JPanel fileOpPanel = new JPanel();
        fileOpPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        /**
         * create a button group for Parse, Normal, and Sorted
         */
        displayGroup = new ButtonGroup();
        btnStats = new JButton("File Statistics");
        btnStats.addActionListener(new StatsButtonListener());
        btnStats.setEnabled(false); // only gets enabled after file opens Ok
        JButton btnExit = new JButton("Exit");
        //normal implementation of file contents
        normalTextView = new JRadioButton("Normal");
        //disable until a file is available to read
        normalTextView.setEnabled(false);
        parsedTextView = new JRadioButton("Parsed");
        parsedTextView.setEnabled(false);
        //sorted radio button
        sortedTextView = new JRadioButton("Sorted");
        /**
         * Text viewed by the user via the opened file.
         */
        sortedTextView.setEnabled((false));
        /**
         * Button Group
         */
        displayGroup.add(normalTextView);
        displayGroup.add(parsedTextView);
        displayGroup.add(sortedTextView);
        /**
         * Add new objects to the panel
         */
        fileInfoPanel.add(normalTextView);
        fileInfoPanel.add(sortedTextView);
        fileInfoPanel.add(parsedTextView);

        //Listeners
        /**
         * Exit Button Listener
         */
        btnExit.addActionListener(new ExitButtonListener());
        /**
         * Sorting button listener
         */
        sortedTextView.addActionListener(new SortedRadioListener());
        /**
         * Parsed Button listener
         */
        parsedTextView.addActionListener(new ParsedRadioListener());
        normalTextView.addActionListener(new OpenButtonListener());
        // add objects to panel
        fileOpPanel.add(btnStats);
        fileOpPanel.add(btnExit);
        add(fileOpPanel, BorderLayout.SOUTH);
    }

    private class ParsedRadioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileText.setText(documentObject.toString().replaceAll("(" + System.lineSeparator() + ")+$", ""));
            fileText.setCaretPosition(0);
        }
    }

    private class SortedRadioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TreeSet<String> lines = new TreeSet<String>();
                fileText.setText("");
                lines = documentObject.getSortedSentences();
                for (String s : lines) {
                    fileText.append(s + "\n");
                }
                fileText.setCaretPosition(0); // set scrollbars at top
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class OpenButtonListener implements ActionListener {
        /**
         * Creates the FileMetrics object and enables the Statistics button when
         * the file path is valid.
         *
         * @param e the event object
         */
        public void actionPerformed(ActionEvent e) {
            fileText.setText("");
            btnStats.setEnabled(false);
            sortedTextView.setEnabled(true);
            normalTextView.setEnabled(true);
            parsedTextView.setEnabled(true);
            try {
                /**
                 * Try to create an object from the file path. If file is not available then catch the exception.
                 */
                documentObject = Document.getDocumentFromPath(file_path.getText());
                /**
                 * ArrayList of string objects that we will hold as lines
                 */
                ArrayList<String> lines = documentObject.getLines();
                for (String s : lines) {
                    fileText.append(s + "\n");
                }
                fileText.setCaretPosition(0); // set scrollbars at top
                btnStats.setEnabled(true); // we got here so assume it all worked
                //FileWindow.this.setTitle(FileWindow.this.title + " : " + documentObject.getName());
            } catch (FileNotFoundException ex) {
                /**
                 * If no file can't be read and the exception was caught, set all buttons to be unusable.
                 */
                sortedTextView.setEnabled(false);
                normalTextView.setEnabled(false);
                parsedTextView.setEnabled(false);
                JOptionPane.showMessageDialog(FileWindow.this, "Error: could not open input file:\n" + ex.getMessage(),
                        FileWindow.this.title + " : Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Set a listener for File Statistics
     */
    private class StatsButtonListener implements ActionListener {
        /**
         * Displays the FileMetrics object.
         *
         * @param e the event object
         */
        public void actionPerformed(ActionEvent e) {
            String msg = String.format(
                    "File has been parsed and sorted. %s\n  Paragraphs: %d\n  Lines: %d\n  Sentences: %d\n  Words: %d",
                    "Information:", documentObject.getParagraphCount(), documentObject.getLineCount(), documentObject.getSentenceCount(), documentObject.getWordCount());
            JOptionPane.showMessageDialog(null, msg, documentObject.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Set a listener for when the user clicks "exit"
     */
    private class ExitButtonListener implements ActionListener {
        /**
         * Closes the application.
         *
         * @param e the event object
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
