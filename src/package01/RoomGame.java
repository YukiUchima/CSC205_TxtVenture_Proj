package package01;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class RoomGame {
    ChoiceHandler msHandler = new ChoiceHandler();
    Database db = new Database();
    UI ui = new UI();
    VisibilityManager vm = new VisibilityManager(ui);
    Story story = new Story(this, ui, vm, db);
    RandomEncounter monsterEncounter = new RandomEncounter();

    String nextPosition1, nextPosition2, nextPosition3, nextPosition4;

//

    public static void main(String[] args) {
        new RoomGame();
    }

    public RoomGame() {
        ui.createUI(msHandler);                 //create window frame
        vm.showTitleScreen();                   //show login screen
        monsterEncounter.monsterSetup();        //setup monsters
        story.defaultSetup();                   //default setup
    }

    public class ChoiceHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	String userName = ui.usernameTf.getText();
        	String passWord =  new String(ui.passwordTf.getPassword());
            String yourChoice = e.getActionCommand();
            HashMap<String, Integer> saveInfo = new HashMap<>();
            switch (yourChoice) {
                case "save":
                    story.save();
                    System.out.println("Saved!");
                    break;
                case "signUp":
                	db.signUp(userName, passWord);
                	break;
                case "signIn":
                    db.logIn(userName, passWord);
                    if(db.signInSuccess !=0){
                        vm.showGameScreen();
                        story.defaultSetup();
                        story.progressSetup();
                    }
                    break;
                case "quit":
                    vm.showTitleScreen();
                case "c1":
                    story.selectPosition(nextPosition1);
                    break;
                case "c2":
                    story.selectPosition(nextPosition2);
                    break;
                case "c3":
                    story.selectPosition(nextPosition3);
                    break;
                case "c4":
                    story.selectPosition(nextPosition4);
                    break;
                default:
                    break;
            }
        }
    }
}
