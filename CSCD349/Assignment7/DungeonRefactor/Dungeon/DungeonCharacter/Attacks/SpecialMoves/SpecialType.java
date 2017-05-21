//Code written by Alex Toenniessen and Jared Regan
package Dungeon.DungeonCharacter.Attacks.SpecialMoves;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class SpecialType implements Attack, Serializable{
    private String special;
    private Attack specialMove;
    public SpecialType(String newAttack, Attack move){
        special = newAttack;
        specialMove = move;
    }
    public void attack(DungeonCharacter hero, DungeonCharacter monster){
        specialMove.attack(hero, monster);
    }
    public String getAttack(){
        return special;
    }
}