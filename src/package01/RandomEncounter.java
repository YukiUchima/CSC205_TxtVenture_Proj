package package01;

import package02.Monster1;
import package02.Monster2;
import package02.SuperMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomEncounter {
    private static ArrayList<SuperMonster> monsters;

    public void monsterSetup() {
//        Assigning various monsters to choose from randomly
        monsters = new ArrayList<>();

        Monster1 basilisk1 = new Monster1();
        Monster2 toxicMush = new Monster2();

        monsters.add(basilisk1);
        monsters.add(toxicMush);
    }

    public boolean monsterSpawned(){
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if(chance > 50){
            return true;}
        return false;
    }

    public SuperMonster monster(){
        Collections.shuffle(monsters);
        return monsters.get(0);
    }
}
