package package02;

import package02.SuperMonster;

public class Monster1 extends SuperMonster {

        String attackMsg;
        String charDesc;

        //Constructor
        public Monster1() {
            setName("Basilisk");
            setStrength(6);      //predetermined STRENGTH by char type
            setHealth(10);        //predetermined HEALTH by char type 45
            setDefense();
            attackMsg = "Basilisk used his venomous fangs!";
            setAttackMsg(attackMsg);
        }

        @Override
        public String classDesc() {
            charDesc ="\n    Watch out for its venom...";
            return charDesc;
        }
}

