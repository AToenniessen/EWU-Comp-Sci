//Code written by Alex Toenniessen and Jared Regan
package Dungeon.DungeonCharacter.Attacks.SpecialMoves;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class SupriseAttack implements Attack, Serializable{

    public void attack(DungeonCharacter hero, DungeonCharacter Monster){
        double surprise = Math.random();
        if (surprise <= .4)
        {
            System.out.println("Surprise attack was successful!\n" +
                    hero.getName() + " gets an additional turn.");
            hero.incrementNumTurns();
            hero.attack(Monster);
        }//end surprise
        else if (surprise >= .9)
        {
            System.out.println("Uh oh! " + Monster.getName() + " saw you and" +
                    " blocked your attack!");
        }
        else
            hero.attack(Monster);
    }
    public String getAttack(){
        return "";
    }
}
