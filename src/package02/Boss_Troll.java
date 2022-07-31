package package02;

import package02.SuperMonster;

public class Boss_Troll extends SuperMonster {

    String charDesc;
    String attackMsg;

    //Constructor
    public Boss_Troll() {
        setName("Troll");
        setStrength(7);      //predetermined STRENGTH by char type
        setHealth(25);        //predetermined HEALTH by char type
        setDefense();
        attackMsg = "The troll used his massive club!";
        setAttackMsg(attackMsg);
    }

    @Override
    public String classDesc() {
        charDesc ="\n    Hibernates in chamber until food finds him during his slumber.";
        return charDesc;
    }
}

