package package02;

import package02.SuperMonster;

public class Reaper extends SuperMonster {

    String charDesc;
    String attackMsg;

    //Constructor
    public Reaper() {
        setName("Deathly Reaper");
        setStrength(9999);      //predetermined STRENGTH by char type 3
        setHealth(9999);        //predetermined HEALTH by char type
        setDefense();
        attackMsg = "The Reaper consumed you with the slice of their Scythe!";
        setAttackMsg(attackMsg);
    }

    @Override
    public String classDesc() {
        charDesc = "\n    Consumes souls with the touch of their Scythe";
        return charDesc;
    }
}
