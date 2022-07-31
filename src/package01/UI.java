package package01;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class UI {

    JFrame window;
    Container container;

//    Title screen panels
    JPanel gameTitlePanel, usernamePanel, passwordPanel, signPanel;
//    Main Game Panels
    JPanel mainPanel, playerPanel, statsPanel, inventoryPanel, outputPanel, commandPanel, savePanel;
    JTextArea mainTextArea, outputTextArea;
    JLabel gameTitleLbl, usernameLbl, passwordLbl, healthLabel,currentHealthLabel, weaponLabel, inventoryTitleLabel;
    JLabel currentWeaponLabel, item1, item2, item3, item4, item5,item6;
    JLabel blank1, blank3, blank5, blank7, blank9;
    JButton signUpBtn, signInBtn, northBtn, eastBtn,southBtn,westBtn, saveBtn, quitBtn;
    JTextField usernameTf;
    JPasswordField passwordTf;

    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 22);
    Font choiceFont = new Font("Times New Roman", Font.PLAIN, 26);
    Font loginFont = new Font("Lucida Grande", Font.PLAIN, 24);
    Font itemFont = new Font("Times New Roman", Font.PLAIN, 18);
    Font savePanelFont = new Font("Times New Roman", Font.PLAIN, 18);
    Font inventoryFont = new Font("Times New Roman", Font.BOLD, 28);

    Border border = new LineBorder(Color.white, 2, true);
    Border inventoryBorder = new LineBorder(Color.blue, 1, true);


    public void createUI(RoomGame.ChoiceHandler msHandler) {
        int width = 1200;
        int height = 700;

//      MAIN WINDOW SCREEN      MAIN WINDOW SCREEN      MAIN WINDOW SCREEN      MAIN WINDOW SCREEN
//      MAIN WINDOW SCREEN      MAIN WINDOW SCREEN      MAIN WINDOW SCREEN      MAIN WINDOW SCREEN
        window = new JFrame();
        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane()
                .setBackground(Color.black);
        window.setLayout(null);
        container = window.getContentPane();
//        int containerXCenter = width/2
//        container.setBounds();

//      LOGIN SCREEN          LOGIN SCREEN          LOGIN SCREEN          LOGIN SCREEN          LOGIN SCREEN
//      LOGIN SCREEN          LOGIN SCREEN          LOGIN SCREEN          LOGIN SCREEN          LOGIN SCREEN

        //game title panel setup
        int gameTitleWidth = 600;
        int gameTitleHeight = 100;
        int gameTitleXCenter = width/2 - (gameTitleWidth/2);
        gameTitlePanel = new JPanel();
        gameTitlePanel.setBounds(gameTitleXCenter,100,gameTitleWidth,gameTitleHeight);
        gameTitlePanel.setBackground(Color.yellow);

        gameTitleLbl = new JLabel();
        gameTitleLbl.setText("CSC205-Project");
        gameTitleLbl.setForeground(Color.black);
        gameTitleLbl.setFont(titleFont);
        gameTitlePanel.add(gameTitleLbl);

        //username panel setup
        int userNameWidth = 300;
        int userNameHeight = 40;
        int userNameXCenter = width/2 - (userNameWidth/2);
        usernamePanel = new JPanel();
        usernamePanel.setBounds(userNameXCenter, 260, userNameWidth,userNameHeight);
        usernamePanel.setBackground(Color.black);

        usernameLbl = new JLabel();
        usernameLbl.setText("Username");
        usernameLbl.setForeground(Color.white);
        usernameLbl.setFont(loginFont);
        usernamePanel.add(usernameLbl);

        int userTFwidth = 300;
        int userTFheight = 40;
        int userTFXCenter = width/2 - userTFwidth/2;
        usernameTf = new JTextField();
        usernameTf.setBackground(Color.white);
        usernameTf.setFont(loginFont);
        usernameTf.setEditable(true);
        usernameTf.setBounds(userTFXCenter,320, userTFwidth,userTFheight);

        int passwordWidth = 400;
        int passwordHeight = 40;
        int passwordXCenter = width/2 - passwordWidth/2;
        passwordPanel = new JPanel();
        passwordPanel.setBounds(passwordXCenter, 380, passwordWidth,passwordHeight);
        passwordPanel.setBackground(Color.black);

        passwordLbl = new JLabel();
        passwordLbl.setText("Password");
        passwordLbl.setForeground(Color.white);
        passwordLbl.setFont(loginFont);
        passwordPanel.add(passwordLbl);

        int passwordTfWidth = 300;
        int passwordTfheight = 40;
        int passwordTfXCenter = width/2 - passwordTfWidth/2;
        passwordTf = new JPasswordField();
        passwordTf.setBackground(Color.white);
        passwordTf.setEditable(true);
        passwordTf.setFont(loginFont);
        passwordTf.setBounds(passwordTfXCenter,440, passwordTfWidth,passwordTfheight);

        signPanel = new JPanel();
        int signPanelWidth = 400;
        int signPanelXCenter = width/2 - signPanelWidth/2;
        signPanel.setBounds(signPanelXCenter, 500, signPanelWidth, 60);
        signPanel.setLayout(new GridLayout(1,2));
        signPanel.setBackground(Color.black);

        signUpBtn = new JButton("NEW GAME");
        signUpBtn.setBackground(new Color(0,0,0));
        signUpBtn.setBorderPainted(false);
        signUpBtn.setForeground(Color.white);
        signUpBtn.setFont(loginFont);
        signUpBtn.setFocusPainted(false);
        signUpBtn.addActionListener(msHandler);
        signUpBtn.setActionCommand("signUp");


        signInBtn = new JButton("LOAD");
        signInBtn.setBackground(Color.black);
        signInBtn.setBorderPainted(false);
        signInBtn.setForeground(Color.white);
        signInBtn.setFont(loginFont);
        signInBtn.setFocusPainted(false);
        signInBtn.addActionListener(msHandler);
        signInBtn.setActionCommand("signIn");

        signPanel.add(signUpBtn);
        signPanel.add(signInBtn);

//          GAME SCREEN          GAME SCREEN          GAME SCREEN          GAME SCREEN          GAME SCREEN
//          GAME SCREEN          GAME SCREEN          GAME SCREEN          GAME SCREEN          GAME SCREEN

//         `````````````````````````````````````````````````MAIN TOP-LEFT Panel```````````````````````````````````````````````
         mainPanel = new JPanel();
         mainPanel.setBounds(100,20, 600, 400);
         mainPanel.setBackground(Color.black);
         mainPanel.setBorder(border);

         mainTextArea = new JTextArea("Fight Area\nNavigation UI");
         mainTextArea.setBounds(40,20, 560, 560);
         mainTextArea.setBackground(Color.black);
         mainTextArea.setForeground(Color.white);
         mainTextArea.setFont(normalFont);
         mainTextArea.setWrapStyleWord(true);
         mainTextArea.setLineWrap(true);
         mainPanel.add(mainTextArea);

//         `````````````````````````````````````````````````PLAYER Panel```````````````````````````````````````````````
         playerPanel = new JPanel();
         playerPanel.setBounds(700, 20, 400, 400);
         playerPanel.setLayout(null);
         playerPanel.setBackground(Color.black);
         playerPanel.setBorder(border);
//         `````````````````````````````````````````````````STATS Panel```````````````````````````````````````````````
         statsPanel = new JPanel();
         statsPanel.setBounds(10,20, 380, 40);
         statsPanel.setBackground(Color.black);
         statsPanel.setLayout(new GridLayout(1, 4));
         playerPanel.add(statsPanel);
//         `````````````````````````````````````````````````HEALTH Label```````````````````````````````````````````````
         healthLabel = new JLabel("Health: ");
         healthLabel.setFont(normalFont);
         healthLabel.setForeground(Color.white);
         statsPanel.add(healthLabel);

         currentHealthLabel = new JLabel("20");
         currentHealthLabel.setFont(normalFont);
         currentHealthLabel.setForeground(Color.green);
         statsPanel.add(currentHealthLabel);
//         `````````````````````````````````````````````````WEAPON Panel```````````````````````````````````````````````
         weaponLabel = new JLabel("Weapon: ");
         weaponLabel.setFont(normalFont);
         weaponLabel.setForeground(Color.white);
         statsPanel.add(weaponLabel);

         currentWeaponLabel = new JLabel("None");
         currentWeaponLabel.setFont(normalFont);
         currentWeaponLabel.setForeground(Color.white);
         statsPanel.add(currentWeaponLabel);
//         `````````````````````````````````````````````````INVENTORY Panel````````````````````````````````````````````
        inventoryTitleLabel = new JLabel("Inventory", SwingConstants.CENTER);
        inventoryTitleLabel.setBounds(40,120, 320, 40);
        inventoryTitleLabel.setBackground(Color.black);
        inventoryTitleLabel.setForeground(Color.white);
        inventoryTitleLabel.setFont(inventoryFont);
        inventoryTitleLabel.setBorder(inventoryBorder);
        playerPanel.add(inventoryTitleLabel);

        inventoryPanel = new JPanel();
         inventoryPanel.setBounds(40,160, 320, 200);
         inventoryPanel.setBackground(Color.lightGray);
         inventoryPanel.setForeground(Color.black);
         inventoryPanel.setLayout(new GridLayout(3,3));
         playerPanel.add(inventoryPanel);


        item1 = new JLabel("1-1", SwingConstants.CENTER);
        item1.setFont(itemFont);
        item1.setForeground(Color.blue);
        item1.setBorder(inventoryBorder);

        item2 = new JLabel("2-1", SwingConstants.CENTER);
        item2.setFont(itemFont);
        item2.setForeground(Color.blue);
        item2.setBorder(inventoryBorder);

        item3 = new JLabel("1-2", SwingConstants.CENTER);
        item3.setFont(itemFont);
        item3.setForeground(Color.blue);
        item3.setBorder(inventoryBorder);

        item4 = new JLabel("2-2", SwingConstants.CENTER);
        item4.setFont(itemFont);
        item4.setForeground(Color.blue);
        item4.setBorder(inventoryBorder);

        item5 = new JLabel("1-3", SwingConstants.CENTER);
        item5.setFont(itemFont);
        item5.setForeground(Color.blue);
        item5.setBorder(inventoryBorder);

        item6 = new JLabel("2-3", SwingConstants.CENTER);
        item6.setFont(itemFont);
        item6.setForeground(Color.blue);
        item6.setBorder(inventoryBorder);

        inventoryPanel.add(item1);
        inventoryPanel.add(item2);
        inventoryPanel.add(item3);
        inventoryPanel.add(item4);
        inventoryPanel.add(item5);
        inventoryPanel.add(item6);


//         `````````````````````````````````````````````````OUTPUT Panel```````````````````````````````````````````````
         outputPanel = new JPanel();
         outputPanel.setBounds(100, 420, 600,200);
         outputPanel.setBackground(Color.black);
         outputPanel.setBorder(border);


        outputTextArea = new JTextArea();
        outputTextArea.setBounds(10,40, 520, 120);
        outputTextArea.setBackground(Color.black);
        outputTextArea.setForeground(Color.white);
        outputTextArea.setEditable(false);
        outputTextArea.setFont(normalFont);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputPanel.add(outputTextArea);


//         `````````````````````````````````````````````````COMMAND Panel```````````````````````````````````````````````
         commandPanel = new JPanel();
         commandPanel.setBounds(700, 420, 400, 140);
         commandPanel.setBackground(Color.black);
         commandPanel.setBorder(border);
         commandPanel.setLayout(new GridLayout(3,3));

         blank1 = new JLabel("");
         blank1.setBackground(Color.black);
         blank3 = new JLabel();
         blank3.setBackground(Color.black);
         blank5 = new JLabel();
         blank5.setBackground(Color.black);
         blank7 = new JLabel();
         blank7.setBackground(Color.black);
         blank9 = new JLabel();
         blank9.setBackground(Color.black);

         northBtn = new JButton("North");
         northBtn.setBackground(Color.black);
         northBtn.setForeground(Color.white);
         northBtn.setBorder(null);
         northBtn.setFont(choiceFont);
         northBtn.setFocusPainted(false);
         northBtn.setVisible(true);
         northBtn.addActionListener(msHandler);
         northBtn.setActionCommand("c1");

         eastBtn = new JButton("East");
         eastBtn.setBackground(Color.black);
         eastBtn.setForeground(Color.white);
         eastBtn.setBorder(null);
         eastBtn.setFont(choiceFont);
         eastBtn.setFocusPainted(false);
         eastBtn.addActionListener(msHandler);
         eastBtn.setActionCommand("c2");

         southBtn = new JButton("South");
         southBtn.setBackground(Color.black);
         southBtn.setForeground(Color.white);
         southBtn.setBorder(null);
         southBtn.setFont(choiceFont);
         southBtn.setFocusPainted(false);
         southBtn.addActionListener(msHandler);
         southBtn.setActionCommand("c3");

         westBtn = new JButton("West");
         westBtn.setBackground(Color.black);
         westBtn.setForeground(Color.white);
         westBtn.setBorder(null);
         westBtn.setFont(choiceFont);
         westBtn.setFocusPainted(false);
         westBtn.addActionListener(msHandler);
         westBtn.setActionCommand("c4");

         commandPanel.add(blank1);
         commandPanel.add(northBtn);
         commandPanel.add(blank3);
         commandPanel.add(westBtn);
         commandPanel.add(blank5);
         commandPanel.add(eastBtn);
         commandPanel.add(blank7);
         commandPanel.add(southBtn);
         commandPanel.add(blank9);

        //         `````````````````````````````````````````````````SAVE Panel```````````````````````````````````````````````
        savePanel = new JPanel();
        savePanel.setBounds(700, 560, 400, 60);
//        700, 420, 400, 100
        savePanel.setBackground(Color.black);
        savePanel.setBorder(border);
        savePanel.setLayout(null);

        saveBtn = new JButton("SAVE");
        saveBtn.setBackground(Color.black);
        saveBtn.setForeground(Color.green);
        saveBtn.setBounds(20, 5, 150, 50);
        saveBtn.setFont(savePanelFont);
        saveBtn.setBorder(null);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(msHandler);
        saveBtn.setActionCommand("save");
        savePanel.add(saveBtn);

        quitBtn = new JButton("QUIT");
        quitBtn.setBackground(Color.black);
        quitBtn.setForeground(Color.red);
        quitBtn.setBounds(220, 5, 150, 50);
        quitBtn.setFont(savePanelFont);
        quitBtn.setBorder(null);
        quitBtn.setFocusPainted(false);
        quitBtn.addActionListener(msHandler);
        quitBtn.setActionCommand("quit");
        savePanel.add(quitBtn);
//        commandPanel.add(savePanel);

        //add to container
        container.add(mainPanel);
        container.add(playerPanel);
        container.add(outputPanel);
        container.add(commandPanel);
        container.add(usernamePanel);
        container.add(gameTitlePanel);
        container.add(usernameTf);
        container.add(passwordPanel);
        container.add(passwordTf);
        container.add(signPanel);
        container.add(savePanel);

        window.setVisible(true);
    }
}

