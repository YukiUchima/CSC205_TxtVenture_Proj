package package01;
import package02.Boss_Troll;
import package02.Reaper;
import package02.SuperMonster;
import package03.Weapon_Knife;
import package03.Weapon_LongSword;

import java.util.ArrayList;
import java.util.HashMap;

//CSC - 205 Group Project
// TEXT VENTURE DUNGEON THEME GAME
// Postgres saving implementation
// Yuki Uchima
// 7/29/2022


public class Story {
    RoomGame game;
    UI ui;
    VisibilityManager vm;
    RandomEncounter randMonster = new RandomEncounter();
    Database db;
    String currentLocation = null;
    SuperMonster curMonster = null;

    //-----------------------Start of progressKey Vals-----------------------
    static int hasTalisman;
    static int hasMap;
    static int hasMonsterKey;
    static int hasHiddenTreasure;
    static int westTrollDefeated;
    static int candle;
    static int trapDoorKey;
    static int hiddenWpn;
    static int enteredWestRoom;
    static int enteredMainEntrance;
    static int bossEncountered;
    static int acceptedQuest;
    static int returnedUser;
    Boss_Troll troll;
    Reaper reaper;
    String currentPlayerProgress; // = Player.getKey();


    //-----------------------End of progressKey Vals-------------------------

    public Story(RoomGame g, UI userInterface, VisibilityManager vManager, Database database){
        game = g;
        ui = userInterface;
        vm = vManager;
        db = database;
    }

//    public void setLocation(String saveLoc) {
//        currentLocation = saveLoc;
//    }

    public void defaultSetup(){

//        SETUPING UP GAME ENVIRONMENT --------------------------------------------------------------------------------
        String introduction = "Welcome to our first text adventure game! This is the main console screen.\n" +
                "\n\n+++ On the right is the player's panel" +
                "\n\n+++ Below is an output panel where you will find hints and directions to help you." +
                "\n\n+++ On the bottom right, you have the option to save/quit, as well as the game's command console.";
        ui.mainTextArea.setText(introduction);
        if(returnedUser != 0){
            ui.outputTextArea.setText("You shall find messages here to help you during your adventure." +
                    "\n\nYou are continuing your journey where you left off...");
        }else {
            ui.outputTextArea.setText("You shall find messages here to help you during your adventure." +
                    "\n\nTo begin your journey, press start...");
        }

        Player.currentWeapon = new Weapon_Knife();
        ui.currentWeaponLabel.setText(Player.currentWeapon.name);

        ui.northBtn.setText("Start");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "dungeonEntrance";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";

        ui.inventoryTitleLabel.setText("INVENTORY");
        ui.item1.setText("");
        ui.item2.setText("");
        ui.item3.setText("");
        ui.item4.setText("");
        ui.item5.setText("");
        ui.item6.setText("");


    }
    //      DATABASE SETUP--------------------------------------------------------------------------------------------------
    public void progressSetup(){
        currentPlayerProgress = Player.getKey();
        System.out.println("Progress key: " + currentPlayerProgress);

//LEFT ROOM - Yuki
        enteredMainEntrance = (int) currentPlayerProgress.charAt(0) - '0'; //progressKey -0
        acceptedQuest = (int) currentPlayerProgress.charAt(1) - '0'; //progressKey - 1
        enteredWestRoom = (int) currentPlayerProgress.charAt(2) - '0'; //progressKey - 2
        candle = (int) currentPlayerProgress.charAt(3) - '0'; //progressKey - 3
        hasMonsterKey = (int) currentPlayerProgress.charAt(4) - '0'; //progressKey - 4
        trapDoorKey = (int) currentPlayerProgress.charAt(5) - '0'; //progressKey - 5
        hiddenWpn = (int) currentPlayerProgress.charAt(6) - '0'; //progressKey - 6
        bossEncountered = (int) currentPlayerProgress.charAt(7) - '0'; //progressKey - 7
        westTrollDefeated = (int) currentPlayerProgress.charAt(8) - '0'; //progressKey - 8
//MIDDLE ROOM - Buthaina
        hasTalisman = (int) currentPlayerProgress.charAt(9) - '0'; //progressKey - 9
        hasHiddenTreasure = (int) currentPlayerProgress.charAt(10) - '0'; //progressKey - 10
//RIGHT ROOM - Mitsuaki
        hasMap = (int) currentPlayerProgress.charAt(11) - '0'; //progressKey - 11
//Check for returning user:
        returnedUser = currentPlayerProgress.charAt(12) - '0'; //progress key - 12

//      PLACE PLAYER IN THE LOCATION LAST PLACED;
        if(returnedUser != 0){
            switch (Player.curLoc) {
                case "westRoom" -> westRoom();
                case "midRoom" -> midRoom();
                case "eastRoom" -> eastRoom();
                default -> mainRoom();
            }
            if(hasMap != 0) ui.item1.setText("Candle");

            if(candle != 0) ui.item1.setText("Candle");
            if(trapDoorKey !=0) ui.item2.setText("small key");
            if(hasTalisman !=0) ui.item3.setText("Talisman");
            if(hasMonsterKey!=0) ui.item4.setText("Heavy Key");
            if(hasMap != 0) ui.item5.setText("Map");
            if(hasHiddenTreasure!=0)ui.item6.setText("Treasure");
            if(hiddenWpn != 0) {
                Player.currentWeapon = new Weapon_LongSword();
                ui.currentWeaponLabel.setText(Player.currentWeapon.name);
            }
        }
        ui.currentHealthLabel.setText("" + Player.hp);

        System.out.println("Treasure: " + hasHiddenTreasure);
        System.out.println("Current progress: " + currentPlayerProgress);
        System.out.println("Value at 1:    " + currentPlayerProgress.charAt(6));
        System.out.println("Candle: " + candle);
    }

    public void selectPosition(String nextPosition){

        switch (nextPosition) {
            //    WEST ROOM
            case "dungeonEntrance" -> dungeonEntrance();
            case "mainRoom" -> mainRoom();
            case "battleReaper" -> battleReaper();
            case "quest" -> quest();
            case "turnBack" -> turnBack();
            case "westRoomEncounter" -> {
                currentLocation = "westRoom";
                monsterEncounter();
            }
            case "midRoomEncounter" -> {
                currentLocation = "midRoom";
                monsterEncounter();
            }
            case "eastRoomEncounter" -> {
                currentLocation = "eastRoom";
                monsterEncounter();
            }
            case "westRoom" -> westRoom();
            case "table" -> westRoomTable();
            case "wardrobe" -> wardrobe();
            case "hidden" -> hidden();
            case "trapDoor" -> trapDoor();
            case "westUnderground" -> westUnderground();
            case "fightWestBoss" -> bossFight();
            case "playerAttack" -> playerAttack();
            case "monsterAttack" -> monsterAttack();
            case "speakTroll" -> speakTroll();
            case "correctRiddle" -> correctRiddle();
            case "wrongRiddle" -> wrongRiddle();
            case "run" -> run();
            case "lose" -> lose();
            case "exit" -> {
                vm.showTitleScreen();
            }


//    MIDDLE DOOR ------------------------------------------------------------------------------------------ MIDDLE DOOR
//    MIDDLE DOOR ------------------------------------------------------------------------------------------ MIDDLE DOOR
            case "midRoom" -> midRoom();
            case "lake" -> lake();
            case "hiddenStairway" -> hiddenStairway();
            case "altar" -> altar();
            case "talisman" -> talisman();
            case "hiddenTreasure" -> hiddenTreasure();


//    RIGHT DOOR -------------------------------------------------------------------------------------------- RIGHT DOOR
            case "eastRoom" -> eastRoom();
            case "rightTable" -> rightTable();
            case "rightTableNoMap" -> rightTableNoMap();
            case "receiveMap" -> receiveMap();
            case "wakeUp" -> wakeUp();
            default -> {
            }
        }
    }
    //    Locations -- Text Area Story Line ---------------------------------------------------------------------------
    public void dungeonEntrance(){
        ui.saveBtn.setVisible(false);
        ui.mainTextArea.setText("""
                You realize the daylight has past quickly - it is dark as you approach the door.\s

                Around the door you see brush and rocks, the path ends here, with trees around blocking your view except the door.\s

                Only way forward is through this heavy steel door, between the ominous, lowly lit torches""");

        ui.outputTextArea.setText("");


        ui.northBtn.setText("North");
        ui.eastBtn.setText("");
        ui.southBtn.setText("South");
        ui.westBtn.setText("");

        game.nextPosition1 = "mainRoom";
        game.nextPosition2 = "";
        game.nextPosition3 = "turnBack";
        game.nextPosition4 = "";
    }

    public void mainRoom() {
        currentLocation = "mainRoom";
        Player.set_Location(currentLocation);
        ui.saveBtn.setVisible(true);

        if (enteredMainEntrance < 1) {
            ui.mainTextArea.setText("""
                    You Entered into the main dungeon room.

                    A ghostly figure appears before you and...floats towards you.

                    Reaper: 'For you to continue forward, you must accept my quest or face the unknown... '""");

            ui.saveBtn.setVisible(false);
            enteredMainEntrance = 1;
            ui.outputTextArea.setText("You must decide to accept or refuse the quest.");

            ui.northBtn.setText("Accept");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Refuse!");
            ui.westBtn.setText("");

            game.nextPosition1 = "quest";
            game.nextPosition2 = "";
            game.nextPosition3 = "battleReaper";
            game.nextPosition4 = "";
        } else if(acceptedQuest == 1 && hasHiddenTreasure < 1){
            ui.mainTextArea.setText("""
                    The Reaper vaporized in to thin air... what will you do next?

                    There are three doors:
                      Left door (WEST)
                      Middle Door (NORTH)
                      Right Door (EAST)""");
            ui.savePanel.setVisible(true);
            acceptedQuest += 1;
            ui.northBtn.setText("North");
            ui.eastBtn.setText("East");
            ui.southBtn.setText("South");
            ui.westBtn.setText("West");

            game.nextPosition1 = "midRoomEncounter";
            game.nextPosition2 = "eastRoomEncounter";
            game.nextPosition3 = "exit";
            game.nextPosition4 = "westRoomEncounter";
        }else if(hasHiddenTreasure!=0){
            ui.saveBtn.setVisible(false);
            ui.mainTextArea.setText("""
                    As you reached the main room for the last time, you can feel the floor crumble beneath your feet.\s
                    
                    You immediately start to fall as it seems like the whole dungeon is imploding on itself!
            """);
            ui.outputTextArea.setText("Continue to see what is happening....");
            ui.northBtn.setText(">");
            ui.eastBtn.setText("");
            ui.southBtn.setText("");
            ui.westBtn.setText("");

            game.nextPosition1 = "wakeUp";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        }
        else {
            ui.mainTextArea.setText("""
                        You returned to the main dungeon room.
                                            
                        Which way do you want to go?...There are three doors:
                                              Left door (WEST)
                                              Middle Door (NORTH)
                                              Right Door (EAST)""");
            ui.northBtn.setText("North");
            ui.eastBtn.setText("East");
            ui.southBtn.setText("South");
            ui.westBtn.setText("West");

            game.nextPosition1 = "midRoomEncounter";
            game.nextPosition2 = "eastRoomEncounter";
            game.nextPosition3 = "exit";
            game.nextPosition4 = "westRoomEncounter";

            ui.outputTextArea.setText("Explore another area...");
        }
    }

    public void quest(){
        ui.saveBtn.setVisible(false);
        acceptedQuest = 1;
        ui.mainTextArea.setText("Reaper: 'The unknown paths are through each of the three doors... Will you survive?'");

        ui.outputTextArea.setText("Continue to see what awaits you on your Quest");

        ui.northBtn.setText(">");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = currentLocation;
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void turnBack(){
        ui.saveBtn.setVisible(false);
        ui.mainTextArea.setText("You are deciding to abandon the story... would you like to quit or enter the dungeon?");

        ui.northBtn.setText("Enter");
        ui.eastBtn.setText("");
        ui.southBtn.setText("Quit");
        ui.westBtn.setText("");

        game.nextPosition1 = "mainRoom";
        game.nextPosition2 = "";
        game.nextPosition3 = "exit";
        game.nextPosition4 = "";

    }

    //    WEST DOOR         LEFT DOOR                  YUKI                 LEFT DOOR         ------------------------
//    WEST DOOR         LEFT DOOR                  YUKI                  LEFT DOOR         ------------------------
    public void westRoom() {
        ui.saveBtn.setVisible(true);
        currentLocation = "westRoom";
        Player.set_Location(currentLocation);

        if (enteredWestRoom == 0) {
            ui.mainTextArea.setText("""
                    You decide to walk cautiously to the left door and look around you.\s

                        You see a note on a table to the WEST.
                        You see a wardrobe to the EAST.
                        North of you is a trapdoor before you reach the north wall.""");
            ui.outputTextArea.setText("Explore this room in order to progress.");
            enteredWestRoom = 1;
            ui.northBtn.setText("North");
            ui.eastBtn.setText("East");
            ui.southBtn.setText("South");
            ui.westBtn.setText("West");

            game.nextPosition1 = "trapDoor";
            game.nextPosition2 = "wardrobe";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "table";
        }else if(westTrollDefeated != 0 && hasTalisman == 0 && hasMonsterKey != 1){
            ui.mainTextArea.setText("""
                    You have defeated the Troll and found a heavy key.
                    
                    (You have been returned to the west room)
                    
                    What will you do now?
                    """);
            hasMonsterKey=1;
            ui.item4.setText("Heavy Key");
            ui.outputTextArea.setText("You retrieved a heavy key!");
            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "";
        }else if(hasMonsterKey > 0){
            ui.mainTextArea.setText("""
                    There's nothing more to do here...
                    
                    """);
            hasMonsterKey=1;
            ui.outputTextArea.setText("Explore other areas to progress in your quest.");
            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "";
        }
        else{
            ui.mainTextArea.setText("""
                    You have returned to the west room.

                   What will you do now?...""");
            ui.northBtn.setText("North");
            ui.eastBtn.setText("East");
            ui.southBtn.setText("South");
            ui.westBtn.setText("West");

            game.nextPosition1 = "trapDoor";
            game.nextPosition2 = "wardrobe";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "table";
        }
    }

    public void westRoomTable(){
        ui.saveBtn.setVisible(true);
        if(candle < 1){
            ui.mainTextArea.setText("""
                    As you reached the table, you see a note... You reach for the note and read the following:

                    " One who wishes to complete his quest must first face the\s
                     darkness below. Take the candle on the table to move forth in your journey...""");

            ui.outputTextArea.setText("You now have a candle!");
            candle=1;
            ui.item1.setText("Candle");
        }else{
            ui.mainTextArea.setText("""
                    You find the note again on the table. It reads:

                    " One who wishes to complete his quest must first face the darkness below. Take the candle on the table to move forth in your journey...""");

            ui.outputTextArea.setText("");
        }

        ui.northBtn.setText("North");
        ui.eastBtn.setText("East");
        ui.southBtn.setText("South");
        ui.westBtn.setText("");

        game.nextPosition1 = "trapDoor";
        game.nextPosition2 = "wardrobe";
        game.nextPosition3 = "mainRoom";
        game.nextPosition4 = "";
    }

    public void wardrobe(){
        ui.saveBtn.setVisible(true);
        if(trapDoorKey < 1) {
            ui.mainTextArea.setText("You reached the wardrobe in wander of what you will find. You notice a metal key...");

            ui.outputTextArea.setText("You received the small key for the trapdoor!");
            trapDoorKey = 1;
            ui.item2.setText("small key");

            ui.northBtn.setText("Trapdoor");
            ui.eastBtn.setText("Drawer");
            ui.southBtn.setText("Leave");
            ui.westBtn.setText("");

            game.nextPosition1 = "trapDoor";
            game.nextPosition2 = "hidden";
            game.nextPosition3 = "westRoom";
            game.nextPosition4 = "";

        }else if(hiddenWpn < 1){
            ui.mainTextArea.setText("You reached the wardrobe again.\n" +
                    "There is not much left to find...");

            ui.northBtn.setText("Trapdoor");
            ui.eastBtn.setText("Drawer");
            ui.southBtn.setText("Leave");
            ui.westBtn.setText("");

            game.nextPosition1 = "trapDoor";
            game.nextPosition2 = "hidden";
            game.nextPosition3 = "westRoom";
            game.nextPosition4 = "";
        }else{
            ui.mainTextArea.setText("There is nothing else to find here.");
            ui.northBtn.setText("Trapdoor");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Leave");
            ui.westBtn.setText("");

            game.nextPosition1 = "trapDoor";
            game.nextPosition2 = "";
            game.nextPosition3 = "westRoom";
            game.nextPosition4 = "";
        }
    }

    public void hidden(){
        ui.saveBtn.setVisible(true);
        ui.mainTextArea.setText("Your curiosity has rewarded you this time... You have " +
                "found a new weapon for your journey.");

        hiddenWpn = 1;

        ui.outputTextArea.setText("You obtained a long sword!");
        Player.currentWeapon = new Weapon_LongSword();
        ui.currentWeaponLabel.setText(Player.currentWeapon.name);

        ui.northBtn.setText("Trapdoor");
        ui.eastBtn.setText("");
        ui.southBtn.setText("Leave");
        ui.southBtn.setText("Table");

        game.nextPosition1 = "trapDoor";
        game.nextPosition2 = "";
        game.nextPosition3 = "mainRoom";
        game.nextPosition4 = "table";
    }

    public void trapDoor(){
        if (trapDoorKey > 0 && candle > 0){
            ui.mainTextArea.setText("You used the key you found in the wardrobe. You unlocked the trapdoor, will you open it?");
            ui.outputTextArea.setText("You can continue your journey below...");

            ui.northBtn.setText("Open");
            ui.eastBtn.setText("Wardrobe");
            ui.southBtn.setText("Leave");
            ui.westBtn.setText("Table");

            game.nextPosition1 = "westUnderground";
            game.nextPosition2 = "wardrobe";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "table";

        }else if(trapDoorKey < 1){
            ui.mainTextArea.setText("You find that the trapdoor is locked...\n");
            ui.outputTextArea.setText("Hint: You may need to search...");
            ui.northBtn.setText("Locked...");
            ui.eastBtn.setText("Wardrobe");
            ui.southBtn.setText("Leave");
            ui.westBtn.setText("Table");

            game.nextPosition1 = "trapDoor";
            game.nextPosition2 = "wardrobe";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "table";
        }else{
            ui.mainTextArea.setText("It will be too dark to enter...\n");
            ui.outputTextArea.setText("Hint: You may need to search...");
            ui.northBtn.setText("");
            ui.eastBtn.setText("Wardrobe");
            ui.southBtn.setText("Leave");
            ui.westBtn.setText("Table");

            game.nextPosition1 = "";
            game.nextPosition2 = "wardrobe";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "table";
        }
    }

    public void westUnderground(){
        ui.saveBtn.setVisible(false);
        westTrollDefeated = 1;
        ui.mainTextArea.setText("You walked down with the sound of stone steps of a cold chamber. You approach " +
                "the end of this stairway to a large open, chamber.\n\nTroll: \"Who disturbs me?!\"");
        bossEncountered = 1;
        ui.outputTextArea.setText("You have encountered a beast!");

        ui.northBtn.setText("Fight");
        ui.eastBtn.setText("Speak");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "fightWestBoss";
        game.nextPosition2 = "speakTroll";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void speakTroll(){
        ui.saveBtn.setVisible(false);
        ui.mainTextArea.setText("The Troll is taken aback you are speaking to them...\n\n" +
                "Monster: \"Solve my riddle and I shall bestow upon thee a gift.\"" +
                "\n\n    What exists while hidden, but ceases to exist when revealed?");

        ui.outputTextArea.setText("Hint: Solve the riddle to face the consequences...");
        ui.northBtn.setText("Thoughts");
        ui.eastBtn.setText("Promises");
        ui.southBtn.setText("Secrets");
        ui.westBtn.setText("Prayers");

        game.nextPosition1 = "wrongRiddle";
        game.nextPosition2 = "wrongRiddle";
        game.nextPosition3 = "correctRiddle";
        game.nextPosition4 = "wrongRiddle";
    }

    public void wrongRiddle(){
        ui.saveBtn.setVisible(false);
        ui.mainTextArea.setText("""
                You failed to answer the riddle correctly. The troll towers over you with a an angry look...

                 Brace yourself for a fight!""");

        ui.outputTextArea.setText("Hint: You must defeat him!");
        ui.northBtn.setText(">");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "fightWestBoss";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void correctRiddle(){
        ui.saveBtn.setVisible(false);
        ui.mainTextArea.setText("""
                You solved the monster's riddle correctly.\s

                Monster: "Take this key as a reward. Good luck on your quest""");
        ui.outputTextArea.setText("You received a large Heavy Key!");
        ui.item4.setText("Heavy Key");
        hasMonsterKey = 1;

        ui.northBtn.setText("Return");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "mainRoom";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

//  North DOOR         Middle DOOR                  BUTHAINA                  Middle DOOR         ------------------------
//  North DOOR         Middle DOOR                  BUTHAINA                  Middle DOOR         ------------------------

    public void midRoom() {
        currentLocation = "midRoom";
        Player.set_Location(currentLocation);

        ui.saveBtn.setVisible(true);
        if (hasMonsterKey == 1 && hasMap == 0) {
            ui.mainTextArea.setText("A pair of worn statues mark the entrance to this dungeon... "
                    + "\n\nBeyond the pair of statues lies a narrow, foggy room covered in cobwebs, crawling insects and rubble.");

            ui.outputTextArea.setText("You can explore this room.");

            ui.northBtn.setText("North");
            ui.eastBtn.setText("?");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "altar";
            game.nextPosition2 = "hiddenStairway";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "";
        }

        else if (hasMonsterKey == 0){
            ui.mainTextArea.setText("A pair of worn statues mark the entrance to this dungeon."
                    + "\n\n Beyond the pair of statues lies a narrow, foggy room covered in cobwebs, crawling insects and rubble."
                    + "\n\n The room appears to be dark. You cannot enter without a LARGE key.");
            ui.outputTextArea.setText("Hint: you may want to search elsewhere");

            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "";

        }

        else if (hasMonsterKey == 1 && hasMap == 1){
            if (hasHiddenTreasure == 0) {
                ui.mainTextArea.setText("You are back in the middle room. Once again, "
                        + "you notice the gloomy room covered in cobwebs, crawling insects and rubble up ahead.");
                ui.outputTextArea.setText("You can explore this area.");

                ui.northBtn.setText("Altar");
                ui.eastBtn.setText("");
                ui.southBtn.setText("Return");
                ui.westBtn.setText("");

                game.nextPosition1 = "altar";
                game.nextPosition2 = "";
                game.nextPosition3 = "mainRoom";
                game.nextPosition4 = "";
            }
            else {
                ui.mainTextArea.setText("You are back in the middle room, and have already obtained the hidden treasure");
                ui.outputTextArea.setText("Hint: explore elsewhere to escape the looming danger of this dungeon");

                ui.northBtn.setText("");
                ui.eastBtn.setText("");
                ui.southBtn.setText("Return");
                ui.westBtn.setText("");

                game.nextPosition1 = "";
                game.nextPosition2 = "";
                game.nextPosition3 = "mainRoom";
                game.nextPosition4 = "";
            }
        }
    }

    public void altar() {
        ui.saveBtn.setVisible(true);
        if (hasTalisman < 1 || hasMap<1) {
            ui.mainTextArea.setText("You notice an altar to the North that has an eerie atmosphere to it."
                    + "\n\n Surrounding the altar lie rotting bodies that have been impaled with various weapons."
                    + "\n\n It appears that no one has been able to approach this altar without reaching an inevitable death.");

            ui.outputTextArea.setText("Hint: You may want to search this area.");

            ui.northBtn.setText("North");
            ui.eastBtn.setText("?");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("West");

            game.nextPosition1 = "";
            game.nextPosition2 = "hiddenStairway";
            game.nextPosition3 = "midRoom";
            game.nextPosition4 = "";

        }else {
            ui.mainTextArea.setText("You are back at the altar. As you approach closer, you reach an area where no being has gotten near."
                    + "\n\n Suddenly, arrows and swords come shooting at you from all directions. "
                    + "\n\n You notice your talisman releasing a strange form of energy, and suddenly the attack stops."
                    + "\n\n A shining orb ascends upon the altar, and as you reach for it, a bright light blinds your vision.");

            ui.northBtn.setText("Continue");
            ui.eastBtn.setText("");
            ui.southBtn.setText("");
            ui.westBtn.setText("");

            game.nextPosition1 = "lake";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        }
    }

    public void hiddenStairway() {
        ui.saveBtn.setVisible(true);
        if (hasTalisman == 0 && hasMonsterKey > 0) {
            ui.mainTextArea.setText("In a dark corner, you notice a hidden stairway. Going down the stairway, up ahead, "
                    + "there is a large iron door that is shut tight."
                    + "You remember that you obtained a key in a previous room that might help you open this door");

            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("Use Key");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "midRoom";
            game.nextPosition4 = "talisman";
        }else {
            ui.mainTextArea.setText("You are back at the hidden stairway where you found the talisman.\s" +
                    "\nLet's return back...");

            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "midRoom";
            game.nextPosition4 = "";
        }
    }

    public void talisman() {
        ui.saveBtn.setVisible(true);
        hasTalisman = 1;

        ui.mainTextArea.setText("As you open the door, the inside reveals a bright object "
                + "floating in the middle of the room. You decide to retrieve the object.");
        ui.item3.setText("Talisman");

        ui.outputTextArea.setText("You have obtained a talisman!");

        ui.northBtn.setText("");
        ui.eastBtn.setText("");
        ui.southBtn.setText("Return");
        ui.westBtn.setText("");

        game.nextPosition1 = "";
        game.nextPosition2 = "";
        game.nextPosition3 = "mainRoom";
        game.nextPosition4 = "";

    }

    public void lake() {

        ui.mainTextArea.setText("Looking at your surroundings, you see smooth, oval rocks lining the bank of a mysterious lake."
                + " Seemingly neglected, the lake is overlaid with spongy moss and "
                + " fallen, decaying leaves from the withering trees above. "
                + " Looking at your map, it appears that this is the final destination leading to the hidden treasure."
                + " The map indicates to head west.");

        ui.outputTextArea.setText("You can explore this area");
        ui.northBtn.setText("");
        ui.eastBtn.setText("");
        ui.southBtn.setText("Return");
        ui.westBtn.setText("West");

        game.nextPosition1 = "";
        game.nextPosition2 = "";
        game.nextPosition3 = "midRoom";
        game.nextPosition4 = "hiddenTreasure";

    }

    public void hiddenTreasure() {
        ui.saveBtn.setVisible(true);
        hasHiddenTreasure = 1;

        ui.mainTextArea.setText("As you go west, a force seems to be pulling you towards the lake, prompting"
                + " you to swim into the darkness. \n\nAs you swim deeper, you notice a chest with ancient engravings imprinted upon it. "
                + " Opening the chest, you find an old relic.");
        ui.outputTextArea.setText("You obtained the relic!");
        ui.item6.setText("Relic");

        ui.northBtn.setText(">");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "mainRoom";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }



// EAST ROOM -------------------MITSUAKI ----------------------------------------------------------------------EAST ROOM
// EAST ROOM -------------------MITSUAKI ----------------------------------------------------------------------EAST ROOM


    public void eastRoom() {
        currentLocation = "eastRoom";
        Player.set_Location(currentLocation);
        ui.saveBtn.setVisible(true);
        if (hasTalisman != 1) {
            ui.mainTextArea.setText("""
                    As you push open the right door...\s
                    
                    You find that you cannot move forward without a special item.  """);

            ui.outputTextArea.setText("May be best to turn back around and return once you explore other rooms.");

            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "";
        } else {
            ui.mainTextArea.setText("""
                    As you push open the right door...\s
                    
                    The talisman in your bag shines brightly and illuminates the space around you.
                    As you take out the talisman out of your bag, a table with a large map laid atop of it comes into view.
                    """);

            ui.outputTextArea.setText("Head North to interact with the table.");

            ui.northBtn.setText("North");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "rightTable";
            game.nextPosition2 = "";
            game.nextPosition3 = "mainRoom";
            game.nextPosition4 = "";

        }
    }

    public void rightTable() {
        if (hasMap != 1) {
            ui.mainTextArea.setText("You decide to approach the old dusty table in front of you...\n" +
                    "As you step closer, you begin to make out the drawings on the map. This is a map of a huge lake!");

            ui.outputTextArea.setText("Do you wish to take the map?");

            ui.northBtn.setText("");
            ui.eastBtn.setText("No");
            ui.southBtn.setText("");
            ui.westBtn.setText("Yes");

            game.nextPosition1 = "";
            game.nextPosition2 = "rightTableNoMap";
            game.nextPosition3 = "";
            game.nextPosition4 = "receiveMap";
        } else {
            ui.mainTextArea.setText("This is where you found the map to the lake.");

            ui.outputTextArea.setText("Click Return to head back.");

            ui.northBtn.setText("");
            ui.eastBtn.setText("");
            ui.southBtn.setText("Return");
            ui.westBtn.setText("");

            game.nextPosition1 = "";
            game.nextPosition2 = "";
            game.nextPosition3 = "midRoom";
            game.nextPosition4 = "";
        }
    }

    public void rightTableNoMap() {
        ui.outputTextArea.setText("You decided to leave the map.");

        ui.northBtn.setText("Return");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "mainRoom";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void receiveMap() {
        ui.outputTextArea.setText("You have received a map of the 'Secret Lake'");


        hasMap = 1;
        ui.item5.setText("Map");

        ui.northBtn.setText("Return");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "mainRoom";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }


    //    FIGHT SIMULATION -------------------------------------------------------------------------------------------------
//    FIGHT SIMULATION -------------------------------------------------------------------------------------------------
    public void run(){
        int runAway = new java.util.Random().nextInt(100);
        if(runAway > 40){
            ui.outputTextArea.setText("You successfully escaped! \n\n You may continue your Journey.");
            game.nextPosition1 = currentLocation;
            ui.saveBtn.setVisible(true);
        }else{
            ui.outputTextArea.setText(curMonster.getName() + " health: " + curMonster.getHealth() +
                    "\n\nYou failed to escape! The monster attacks!");
            game.nextPosition1 = "monsterAttack";
        }
        ui.northBtn.setText(">");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void bossFight(){
        ui.saveBtn.setVisible(false);
        currentLocation = "westRoom";
        westTrollDefeated = 1;
        troll = new Boss_Troll();
        curMonster = troll;
        ui.mainTextArea.setText("You are in a boss battle with " + curMonster.getName() + " (HP): " + curMonster.getHealth() +  "\n\nThere's no escape!");
        ui.outputTextArea.setText("");

        ui.northBtn.setText("Attack");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "playerAttack";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void battleReaper(){
        ui.saveBtn.setVisible(false);
        currentLocation = "mainRoom";
        reaper = new Reaper();
        curMonster = reaper;
        ui.mainTextArea.setText("The " + curMonster.getName() + " confronts you!\n\nThere's no escape!");
        ui.outputTextArea.setText("");

        ui.northBtn.setText("Attack");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "playerAttack";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";

    }

    public void monsterEncounter() {

        if (randMonster.monsterSpawned()) {
            ui.saveBtn.setVisible(false);
            curMonster = randMonster.monster();

            ui.mainTextArea.setText(curMonster.getName() + "(HP: " + curMonster.getHealth() +") appeared!" +
                    "\n\nWhat will you do?");

            ui.outputTextArea.setText("You can choose to attack or run...");

            ui.northBtn.setText("Attack");
            ui.eastBtn.setText("Run");
            ui.southBtn.setText("");
            ui.westBtn.setText("");

            game.nextPosition1 = "playerAttack";
            game.nextPosition2 = "run";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        }else{
            switch (currentLocation) {
                case "westRoom" -> westRoom();
                case "midRoom" -> midRoom();
                case "eastRoom" -> eastRoom();
                default -> mainRoom();
            }
        }

    }

    public void monsterAttack(){
        System.out.println("The Monster Attacked!");
        int monsterDamage = curMonster.attack();
        System.out.println("Monster damage: " + monsterDamage);
        if(monsterDamage < 1){
            ui.mainTextArea.setText(curMonster.getName() + "'s attack missed!");
        }else{
            ui.mainTextArea.setText(curMonster.getAttackMsg() + " \n\t" +  monsterDamage + " damage was inflicted!");
            Player.hp -= monsterDamage;
            if(Player.hp < 0){
                Player.hp = 0;
            }
        }

        ui.currentHealthLabel.setText("" + Player.hp);
        ui.northBtn.setText(">");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        if (Player.hp > 0) {
            game.nextPosition1 = "playerAttack";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        } else {
            game.nextPosition1 = "lose";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        }
    }

    public void playerAttack(){
        System.out.println("You attacked the monster with your " + Player.currentWeapon.name + "!");
        int playerDamage = new java.util.Random().nextInt(Player.currentWeapon.damage);
        if(playerDamage > 0) {
            curMonster.hit(playerDamage);
            ui.mainTextArea.setText("You attacked the monster with your " + Player.currentWeapon.name + " and inflicted " + playerDamage + " damage!");
        }else{
            ui.mainTextArea.setText("You tried to attack with your " + Player.currentWeapon.name + " but missed!");
        }

        ui.outputTextArea.setText(curMonster.getName() + " health: " + curMonster.getHealth() +
                "\n\n");
        ui.northBtn.setText(">Continue");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        if (curMonster.getHealth() > 0) {
            game.nextPosition1 = "monsterAttack";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        } else {
            randMonster.monsterSetup();
            game.nextPosition1 = currentLocation;
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
        }
    }

    public void save() {
        String saveLoc = Player.get_CurLoc();
        int savedID = Player.getPlayer_id();
        int savedHealth = Player.getHP();

//        update progress values    ------------------------------------------------------------------------------------
        int a0 = enteredMainEntrance;        // = (int) currentPlayerProgress.charAt(0) - '0'; //progressKey -0
        int b1 = acceptedQuest;               // = (int) currentPlayerProgress.charAt(1) - '0'; //progressKey - 1
        int c2 = enteredWestRoom;                     // = (int) currentPlayerProgress.charAt(2) - '0'; //progressKey - 2
        int d3 = candle;                              // = (int) currentPlayerProgress.charAt(3) - '0'; //progressKey - 3
        int e4 = hasMonsterKey;                       // = (int) currentPlayerProgress.charAt(4) - '0'; //progressKey - 4
        int f5 = trapDoorKey;                        // = (int) currentPlayerProgress.charAt(5) - '0'; //progressKey - 5
        int g6 = hiddenWpn;                           // = (int) currentPlayerProgress.charAt(6) - '0'; //progressKey - 6
        int h7 = bossEncountered;                     // = (int) currentPlayerProgress.charAt(7) - '0'; //progressKey - 7
        int i8 = westTrollDefeated;                   // = (int) currentPlayerProgress.charAt(8) - '0'; //progressKey - 8
        int j9 = hasTalisman;                       // = (int) currentPlayerProgress.charAt(9) - '0'; //progressKey - 9

        int k10 = hasHiddenTreasure;                // = (int) currentPlayerProgress.charAt(10) - '0'; //progressKey - 10

        int l11 = hasMap;//asMap;                           // = (int) currentPlayerProgress.charAt(11) - '0'; //progressKey - 11
        int m12 = 1;

        String savedKey = String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s", a0, b1, c2, d3, e4, f5,g6,h7, i8,j9,k10,l11,m12);
        System.out.println("Progress key: " + savedKey);
        System.out.println("Location: " + saveLoc);
        System.out.println("Player ID: " + savedID);
        System.out.println("Health: " + savedHealth);

        db.saveUserInfo(savedID, savedKey, savedHealth, saveLoc);



//        db.saveUserInfo(Player.getPlayer_id(), key);

        System.out.println("Game saved...");

        ui.outputTextArea.setText("Game has been saved...");
        ui.outputTextArea.setText("Return to main menu or continue your game...");

        ui.northBtn.setText("Main Menu");
        ui.eastBtn.setText("");
        ui.southBtn.setText("Cont'");
        ui.westBtn.setText("");

        game.nextPosition1 = "exit";
        game.nextPosition2 = "";
        game.nextPosition3 = currentLocation;
        game.nextPosition4 = "";
    }

    public void lose() {
        ui.mainTextArea.setText("You lost the battle and must start over...");
        ui.northBtn.setText(">");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "exit";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }

    public void wakeUp(){
        ui.mainTextArea.setText("""
                As the world darkens around you, all turns black. Then you slowly feel your eyes open as you realize it was all a dream.
                
                Congratulations, you have woken from your curse and beat the game!
                """);
        ui.outputTextArea.setText("");

        ui.northBtn.setText("Victory!");
        ui.eastBtn.setText("");
        ui.southBtn.setText("");
        ui.westBtn.setText("");

        game.nextPosition1 = "exit";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
    }















//    MIDDLE DOOR

}

//Frame LOCATIONS
//  Front entrance; main room
//      >LEFT door
//          > search room; weapon, note, key, cellar
//              >   Enter cellar; BOSS
//                    >Boss fight, solve riddle, retreat
//                      Fight; win, lose > retrieve KEY ITEM / lose game
//                      Riddle: win, lose > retrieve weapon and key item / fight or leave
//
//      >Middle
//      >Right
//  Loc: front of entrance
//      open door, turn back
//  Loc: main room
//      left door, middle door, right door,

//      loc: left room