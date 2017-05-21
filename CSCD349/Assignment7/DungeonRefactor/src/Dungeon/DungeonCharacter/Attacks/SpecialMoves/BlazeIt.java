package Dungeon.DungeonCharacter.Attacks.SpecialMoves;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class BlazeIt implements Attack, Serializable {
    public void attack(DungeonCharacter hero, DungeonCharacter monster){
        double toke = Math.random();
        if (toke <= .4)
        {
            System.out.println(hero.getName() + " smokes a joint by himself!\n" +
                    hero.getName() + " gets the munchies and looses a turn to hunt for food.");
            hero.decrementNumTurns();
            hero.attack(monster);
        }//end surprise
        else if (toke >= .9)
        {
            System.out.println("Uh oh! " + monster.getName() + " saw your joint and took it for himself!"
            + "\n He is too high to battle this turn");
            hero.battleChoices(monster); //this might recursively break the program
        }
        else
            hero.attack(monster);
    }
    public String getAttack(){
        return "";
    }
}
