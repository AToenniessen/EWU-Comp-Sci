package Dungeon.DungeonCharacter.Attacks.SpecialMoves;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class CrushingBlow implements Attack, Serializable{
    public void attack(DungeonCharacter hero, DungeonCharacter Monster){
        if (Math.random() <= .4)
        {
            int blowPoints = (int)(Math.random() * 76) + 100;
            System.out.println(hero.getName() + " lands a CRUSHING BLOW for " + blowPoints
                    + " damage!");
            Monster.subtractHitPoints(blowPoints);
        }
        else {
            System.out.println(hero.getName() + " failed to land a crushing blow");
            System.out.println();
        }
    }
    public String getAttack(){
        return "";
    }
}
