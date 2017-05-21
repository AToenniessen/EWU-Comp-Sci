package Dungeon.DungeonCharacter.Attacks.SpecialMoves;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class IncreaseHP implements Attack, Serializable {
    public void attack(DungeonCharacter hero, DungeonCharacter Monster){
        int hPoints;

        hPoints = (int)(Math.random() * (50 - 25 + 1)) + 25;
        hero.addHitPoints(hPoints);
        System.out.println(hero.getName() + " added [" + hPoints + "] points.\n"
                + "Total hit points remaining are: "
                + hero.getHitPoints());
        System.out.println();
    }
    public String getAttack(){
        return "";
    }
}
