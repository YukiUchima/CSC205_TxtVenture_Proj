package package02;

import java.util.Random;

public abstract class SuperMonster {
    Random rand = new Random();
    protected String name;
    private int strength;
    private int health;
    private int defense;
    private String attackMsg;

    //NAME
    public void setName(String n1) {
        this.name = n1;
    }

    public String getName() {
        return name;
    }

    //DEFENSE

    public void setAttackMsg(String msg){
        attackMsg = msg;
    }
    public String getAttackMsg(){
        return attackMsg;
    }
    public void setDefense() {
        this.defense = 1 + rand.nextInt(8);  //adds randomness
    }

    public int getDefense() {
        return defense;
    }

    //STRENGTH
    public void setStrength(int s1) {
        this.strength = s1 + rand.nextInt(5);
    }

    public int getStrength() {
        return strength;
    }

    //HEALTH
    public void setHealth(int h1) {
        this.health = h1 + rand.nextInt(10);
    }


    public int getHealth() {
        if (health < 0) {
            health = 0;
        }
        return health;
    }

    public int attack() {
        Random rand = new Random();
        return rand.nextInt(strength);
    }

    public void hit(int points) {
        health = health - points;
    }
    public abstract String classDesc();

    @Override
    public String toString() {
        return getName() + "\n\t" + classDesc() + "\n\t\t+Strength: " + getStrength() + "\n\t\t+Health: " + getHealth() +
                "\n\t\t+Defense: " + getDefense() + "\n";
    }
}
