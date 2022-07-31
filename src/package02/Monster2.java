package package02;

import package02.SuperMonster;

public class Monster2 extends SuperMonster {
    String charDesc;
    String attackMsg;

    //Constructor
    public Monster2() {
        setName("ToxicMush");
        setStrength(8);      //predetermined STRENGTH by char type
        setHealth(10);        //predetermined HEALTH by char type 22
        setDefense();
        attackMsg = "ToxicMush used their dangerous fumes!";
        setAttackMsg(attackMsg);
    }

    @Override

    public String classDesc() {
        charDesc = "\n    In humid, dark places but if found, it's too late due to toxic fume.";
        return charDesc;
    }

}
