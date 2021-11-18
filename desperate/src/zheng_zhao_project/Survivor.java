package zheng_zhao_project;
/**
 * This class is used to create Survivor Object
 * Class is created by Wenzhuo and Weijie
 */
public class Survivor {
    private String userName;
    private int day;
    private int hp;
    private int energy;
    private int food;
    
/**
 * Five parameters constructor to create a Survivor Object
 * @param userName username is the name of player in the game
 * @param day day is how many days does the player survive
 * @param hp hp is the health points of the player
 * @param energy energy is the energy points of the player
 * @param food food is the hunger point of the player 
 */
    public Survivor(String userName, int day, int hp, int energy, int food) {
        this.userName = userName;
        this.day = day;
        this.hp = hp;
        this.energy = energy;
        this.food = food;
    }
    
/**
 * This method is to get the name of the Survivor
 * @return the player's name of Survivor
 */
    public String getUserName() {
        return userName;
    }
    
/**
 * This method is to set the name of the Survivor
 * @param userName the name of the Survivor
 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
/**
 * This method is to get the amount of days that the survivor has been survived
 * @return the amount of days that the survivor has been survived
 */
    public int getDay() {
        return day;
    }
    
/**
 * This method is to set the amount of days that the survivor has been survived
 * @param day the amount of days that the survivor has been survived
 */
    public void setDay(int day) {
        this.day = day;
    }
    
/**
 * This method is to get the health point of the survivor
 * @return the health point of the survivor
 */
    public int getHp() {
        return hp;
    }
    
/**
 * This method is to used to set the health point of the Survivor
 * @param hp the health point of the Survivor
 */
    public void setHp(int hp) {
        this.hp = hp;
    }
    
/**
 * This method is to get the energy point of the survivor
 * @return the energy point of the survivor
 */
    public int getEnergy() {
        return energy;
    }
    
/**
 * This method is to used to set the energy point of the Survivor
 * @param energy the energy point of the Survivor
 */
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    
/**
 * This method is to get the food of the survivor
 * @return the food of the survivor
 */
    public int getFood() {
        return food;
    }
    
 /**
  * This method is to used to set the food of the Survivor
  * @param food the food of the Survivor
  */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * This method is used to override the toString method
     * @return a string of the name of the survivor
     */
    @Override
    public String toString() {
        return userName;
    }
}
