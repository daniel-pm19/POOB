package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class   CityGUI extends JFrame{
    public static final int SIDE=20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel  controlPanel;
    private PhotoCity photo;
    private City theCity;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem importMenuItem;
    private JMenuItem exportAsMenuItem;
    private JMenuItem exitMenuItem;

   
    
    private CityGUI() {
        theCity=new City();
        SIZE=theCity.getSize();
        prepareElements();
        prepareElementsMenu();
        prepareActionsMenu();
        prepareActions();
    }
    
    private void prepareElements() {
        setTitle("Schelling City");
        photo=new PhotoCity(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+72)); 
        setResizable(false);
        photo.repaint();
    }

    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveAsMenuItem = new JMenuItem("Save as");
        importMenuItem = new JMenuItem("Import");
        exportAsMenuItem = new JMenuItem("Export as");
        exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(importMenuItem);
        fileMenu.add(exportAsMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    private void prepareActionsMenu() {
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newOptionAction();
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openOptionAction();
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsOptionAction();
            }
        });

        importMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importOptionAction();
            }
        });

        exportAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportAsOptionAction();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitOptionAction();
            }
        });
    }

    private void exitOptionAction() {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.NO_OPTION) {
            return;
        } else {
            System.exit(0);
        }
    }

    private void newOptionAction() {
        theCity=new City();
        photo.repaint();
    }

    private void openOptionAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("DAT files", "dat"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try{
                City loadedCity = theCity.open01(selectedFile);
                if (loadedCity != null){
                    theCity = loadedCity;
                    JOptionPane.showMessageDialog(this, "File opened successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    photo.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Error loading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (CityException e){
                JOptionPane.showMessageDialog(this,CityException.OPENING_FILE_ERROR+ ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAsOptionAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("DAT files", "dat"));

        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".dat")) {
                file = new File(file.getAbsolutePath() + ".dat");
            }
            try {
                theCity.save01(file);
                JOptionPane.showMessageDialog(this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (CityException e) {
                JOptionPane.showMessageDialog(this, CityException.FILE_NOT_SAVED + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importOptionAction() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theCity.import03(selectedFile);
                JOptionPane.showMessageDialog(this, "File imported successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                photo.repaint();
            } catch (CityException e) {
                JOptionPane.showMessageDialog(this, CityException.IMPORTING_FILE_ERROR + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportAsOptionAction() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theCity.export03(selectedFile);
                JOptionPane.showMessageDialog(this, "File exported successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (CityException e) {
                JOptionPane.showMessageDialog(this, CityException.EXPORTING_FILE_ERROR + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void prepareActions(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        ticTacButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    ticTacButtonAction();
                }
            });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

    }
    private void exit(){
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.NO_OPTION) {
            return;
        } else {
            System.exit(0);
        }
    }

    private void ticTacButtonAction() {
        theCity.ticTac();
        photo.repaint();
    }

    public City gettheCity(){
        return theCity;
    }
    
    public static void main(String[] args) {
        CityGUI cg=new CityGUI();
        cg.setVisible(true);
    }  
}

class PhotoCity extends JPanel{
    private CityGUI gui;

    public PhotoCity(CityGUI gui) {
        this.gui=gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE*gui.SIZE+10, gui.SIDE*gui.SIZE+10));         
    }


    public void paintComponent(Graphics g){
        City theCity=gui.gettheCity();
        super.paintComponent(g);
         
        for (int c=0;c<=theCity.getSize();c++){
            g.drawLine(c*gui.SIDE,0,c*gui.SIDE,theCity.getSize()*gui.SIDE);
        }
        for (int f=0;f<=theCity.getSize();f++){
            g.drawLine(0,f*gui.SIDE,theCity.getSize()*gui.SIDE,f*gui.SIDE);
        }       
        for (int f=0;f<theCity.getSize();f++){
            for(int c=0;c<theCity.getSize();c++){
                if (theCity.getItem(f,c)!=null){
                    g.setColor(theCity.getItem(f,c).getColor());
                    if (theCity.getItem(f,c).shape()==Item.SQUARE){                  
                        if (theCity.getItem(f,c).isActive()){
                            g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);
                        }else{
                            g.drawRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);    
                        }
                    }else {
                        if (theCity.getItem(f,c).isActive()){
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        } else {
                            g.drawOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        }
                    }
                    if (theCity.getItem(f,c).isAgent()){
                        g.setColor(Color.red);
                        if (((Agent)theCity.getItem(f,c)).isHappy()){
                            g.drawString("u",gui.SIDE*c+6,gui.SIDE*f+15);
                        } else if (((Agent)theCity.getItem(f,c)).isIndifferent()){ 
                            g.drawString("_",gui.SIDE*c+7,gui.SIDE*f+10);
                        } else if (((Agent)theCity.getItem(f,c)).isDissatisfied()){
                            g.drawString("~",gui.SIDE*c+6,gui.SIDE*f+17);
                        }
                    }    
                }
            }
        }
    }
}