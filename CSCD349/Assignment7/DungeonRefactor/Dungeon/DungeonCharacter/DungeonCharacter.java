//Code written by Alex Toenniessen and Jared Regan
package Dungeon.DungeonCharacter;

import Dungeon.DungeonCharacter.Attacks.Attack;

import java.io.Serializable;
import java.util.Scanner;

public abstract class DungeonCharacter implements Comparable, Serializable {

    private String name;
    private int hitPoints;
    private int attackSpeed;
    private int numTurns;
    private double chanceToHit;
    private int damageMin, damageMax;
    private Attack basic;
    private Attack special;

    public int compareTo(Object o) {
        return 1;
    }

    public DungeonCharacter(String name, int hitPoints, int attackSpeed, double chanceToHit, int damageMin, int damageMax, Attack attack, Attack specialAttack) {

        this.name = name;
        this.hitPoints = hitPoints;
        this.attackSpeed = attackSpeed;
        this.chanceToHit = chanceToHit;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.special = specialAttack;
        this.basic = attack;
    }

    public String getName() {
        return name;
    }

    protected void setName(String s) {
        this.name = s + " " + this.name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    private int getAttackSpeed() {
        return attackSpeed;
    }

    public void incrementNumTurns() {
        numTurns++;
    }

    public void decrementNumTurns() {
        if (numTurns >= 1)
            numTurns--;
    }

    public int getdamageMin() {
        return this.damageMin;
    }

    public int getdamageMax() {
        return this.damageMax;
    }

    public double getchanceToHit() {
        return this.chanceToHit;
    }

    public void addHitPoints(int hitPoints) {
        if (hitPoints <= 0)
            System.out.println("Hitpoint amount must be positive.");
        else {
            this.hitPoints += hitPoints;
        }
    }

    public void subtractHitPoints(int hitPoints) {
        if (hitPoints < 0)
            System.out.println("Hitpoint amount must be positive.");
        else if (hitPoints > 0) {
            this.hitPoints -= hitPoints;
            if (this.hitPoints < 0)
                this.hitPoints = 0;
            System.out.println(getName() + " hit " +
                    " for <" + hitPoints + "> points damage.");
            System.out.println(getName() + " now has " +
                    getHitPoints() + " hit points remaining.");
            System.out.println();
        }

        if (this.hitPoints == 0)
            System.out.println(name + " has been killed :-(");


    }

    public boolean isAlive() {
        return (hitPoints > 0);
    }
    public void attack(DungeonCharacter opponent){
        basic.attack(this, opponent);
    }

    public void battleChoices(DungeonCharacter opponent) {
        numTurns = attackSpeed / opponent.getAttackSpeed();

        if (numTurns == 0)
            numTurns++;

        System.out.println("Number of turns this round is: " + numTurns);

        int choice;
        Scanner Keyboard = new Scanner(System.in);

        do {
            System.out.println("1. Attacks Opponent");
            System.out.println("2. " + special.getAttack());
            System.out.print("Choose an option: ");
            String ans = Keyboard.next();
            while(!(ans.equals("1") || ans.equals("2")))
            {
                System.out.println("Invalid selection, try again");
                ans = Keyboard.next();
            }
            choice = Integer.parseInt(ans);

            switch (choice) {
                case 1:
                    basic.attack(this, opponent);
                    break;
                case 2:
                    special.attack(this, opponent);
                    break;
                default:
                    System.out.println("invalid choice!");
            }
            numTurns--;
            if (numTurns > 0)
                System.out.println("Number of turns remaining is: " + numTurns);

        } while (numTurns > 0 && hitPoints > 0 && opponent.getHitPoints() > 0);
    }
}
