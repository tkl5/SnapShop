/*
 * TCSS 305 - Spring 2016
 * Assignment 4 - SnapShop
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import filters.AbstractFilter;
import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;

/**
 * This program builds the Graphical User Interface for the SnapShop image editing application.
 * 
 * @author Tim Liu
 * @version 2 May 2016
 */
public class SnapShopGUI extends JFrame {

    /** A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 1L;

    /** The number of rows for the Filter Button Grid Layout. */
    private static final int FILTER_BUTTON_ROW = 7;

    /** The number of columns for the Filter Button Grid Layout. */
    private static final int COL = 1;
    
    /** The number of rows for the last three buttons Grid Layout. 
     * Columns are the same as the Filter Buttons. */
    private static final int OTHER_BUTTON_ROW = 3;

    /** JButton object for the Open button.  */
    private JButton myOpenButton;

    /** JButton object for the Save As button. */
    private JButton mySaveAsButton;

    /** JButton object for the Close Image button. */
    private JButton myCloseImageButton;

    /** The PixelImge object. */
    private PixelImage myImage;

    /** The JLabel object. */
    private JLabel myLabel;

    /** The parent directory the Open button opens to.  */
    private File myParentDirectory;

    /** Array of AbstractFilter classes. */
    private final AbstractFilter[] myAbsFilterArray = {new EdgeDetectFilter(),
        new EdgeHighlightFilter(), new FlipHorizontalFilter(),
        new FlipVerticalFilter(), new GrayscaleFilter(), new SharpenFilter(),
        new SoftenFilter()};

    /** Array of JButtons objects for the filter buttons. */
    private JButton[] myFilterButtonArray = {new JButton("Edge Detect"),
        new JButton("Edge Highlight"), new JButton("Flip Horizontal"),
        new JButton("Flip Vertical"), new JButton("Grayscale"),
        new JButton("Sharpen"), new JButton("Soften")};

    /**
     * Initializes all the fields.
     */
    public SnapShopGUI() {
        super("TCSS 305 SnapShop");
        myLabel = new JLabel();

    }

    /**
     * Set up components to make the GUI visible.
     */
    public void setUpComponents() {

        setLayout(new BorderLayout());
        final JPanel westPanel = new JPanel(new BorderLayout());
        final JPanel northGrid = new JPanel(new GridLayout(FILTER_BUTTON_ROW, COL));
        final JPanel southGrid = new JPanel(new GridLayout(OTHER_BUTTON_ROW, COL));

        westPanel.add(northGrid, BorderLayout.NORTH);
        westPanel.add(southGrid, BorderLayout.SOUTH);

        add(westPanel, BorderLayout.WEST);

        for (int i = 0; i < myFilterButtonArray.length; i++) {
            northGrid.add(myFilterButtonArray[i]);
            myFilterButtonArray[i].setEnabled(false);

        }

        myOpenButton = new JButton("Open...");
        mySaveAsButton = new JButton("Save As...");
        myCloseImageButton = new JButton("Close Image");

        southGrid.add(myOpenButton);
        southGrid.add(mySaveAsButton);
        southGrid.add(myCloseImageButton);

        mySaveAsButton.setEnabled(false);
        myCloseImageButton.setEnabled(false);
        pack();

    }

    /** 
     * Add action listeners to the Open and Save As buttons. 
     */
    public void doOpenAndSaveActionListeners() {
        myParentDirectory = new File("./");
        /** Local inner class */
        class MyButtonListener implements ActionListener {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final JFileChooser fileChooser = new JFileChooser();

                /** Handle the Open button */
                fileChooser.setCurrentDirectory(myParentDirectory);
                if (theEvent.getSource() == myOpenButton) {
                    final int returnVal = fileChooser.showOpenDialog(SnapShopGUI.this);
                    setMinimumSize(new Dimension(0, 0));
                    try {
                        myImage = PixelImage.load(fileChooser.getSelectedFile());

                    } catch (final IOException e) {
                        JOptionPane.showMessageDialog(null,
                                        "The selected file did not contain an image!",
                                        "Invalid File",
                                        JOptionPane.ERROR_MESSAGE);
                    } catch (final IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (final NullPointerException e) {
                        e.printStackTrace();
                    }

                    myLabel.setIcon(new ImageIcon(myImage));
                    add(myLabel, BorderLayout.CENTER);

                    for (int i = 0; i < myFilterButtonArray.length; i++) {
                        myFilterButtonArray[i].setEnabled(true);
                    }

                    mySaveAsButton.setEnabled(true);
                    myCloseImageButton.setEnabled(true);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        myParentDirectory = fileChooser.getSelectedFile();
                    }

                    pack();
                    setMinimumSize(getSize());

                /** Handle the Save As button */
                } else if (theEvent.getSource() == mySaveAsButton) {
                    final int returnVal = fileChooser.showSaveDialog(SnapShopGUI.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        myParentDirectory = fileChooser.getSelectedFile();
                        try {
                            myImage.save(fileChooser.getSelectedFile());
                        } catch (final IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        myOpenButton.addActionListener(new MyButtonListener());
        mySaveAsButton.addActionListener(new MyButtonListener());
        pack();

    }

    /** 
     * Add action listeners for the filter buttons. 
     */
    public void doFilterActionListeners() {        
        /** Local inner class */
        class MyFilterButtonListener implements ActionListener {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                for (int i = 0; i < myFilterButtonArray.length; i++) {
                    if (theEvent.getSource() == myFilterButtonArray[i]) {
                        myAbsFilterArray[i].filter(myImage);
                        myLabel.setIcon(new ImageIcon(myImage));
                    }
                }
            }
        }

        for (int i = 0; i < myFilterButtonArray.length; i++) {
            myFilterButtonArray[i].addActionListener(new MyFilterButtonListener());
        }
    }

    /** Add action listeners for the Close Image button. 
     * 
     */
    public void doCloseImageActionListener() {
        /** Local inner class */
        class MyCloseImageListener implements ActionListener {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                if (theEvent.getSource() == myCloseImageButton) {
                    myLabel.setIcon(new ImageIcon());
                    
                    for (int i = 0; i < myFilterButtonArray.length; i++) {
                        myFilterButtonArray[i].setEnabled(false);
                    }
                    mySaveAsButton.setEnabled(false);
                    myCloseImageButton.setEnabled(false);
                    
                    setMinimumSize(new Dimension(0, 0));
                    pack();

                }
            }
        }

        myCloseImageButton.addActionListener(new MyCloseImageListener());
        setMinimumSize(getSize());

    }

    /** Initiate the GUI. */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpComponents();
        doOpenAndSaveActionListeners();
        doFilterActionListeners();
        doCloseImageActionListener();

        setLocationRelativeTo(null);
        setVisible(true);

    }
}
