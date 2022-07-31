package package01;

import package03.SuperWeapon;

public class Player {
    public static int hp;
    public static SuperWeapon currentWeapon;
    public static String currWeapon;
    public static String progresskey;
    public static int player_id;
    public static String curLoc;
    
    public static void set_HP(int health) {
    	hp = health;
    }

    public static void set_Location(String loc){
        curLoc = loc;
    }
    public static String get_CurLoc(){
        return curLoc;
    }

    public static void setProgresskey(String key){
        progresskey = key;
    }
    public static String getKey(){return progresskey;}

    public static void setPlayer_id(int uniqueID){
        player_id = uniqueID;
    }

    public static int getPlayer_id(){
        return player_id;
    }


    public static int getHP() {
    	return hp;
    }
    
    public static void setWeapon(String weapon) {
    	currWeapon = weapon;
    }
    
    public static String getWeaponName() {
    	return currentWeapon.getWeaponName();
    }
    
    
}
