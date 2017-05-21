//Code written by Alex Toenniessen and Jared Regan
package Dungeon.DungeonCharacter.Attacks.basicAttacks;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class AttackType implements Attack, Serializable{
    private String attack;
    public AttackType(String newAttack){
        attack = newAttack;
    }
    public void attack(DungeonCharacter attacker, DungeonCharacter defender){
        boolean canAttack;
        int damage;

        canAttack = Math.random() <= attacker.getchanceToHit();

        System.out.println("\n" + attacker.getName() + attack + defender.getName() + ":");

        if (canAttack)
        {
            damage = (int)(Math.random() * (attacker.getdamageMax() - attacker.getdamageMin() + 1))
                    + attacker.getdamageMin() ;
            defender.subtractHitPoints(damage);

            System.out.println();
        }
        else
        {

            System.out.println(attacker.getName() + "'s attack on " + defender.getName() +
                    " failed!");
            System.out.println();
        }
    }
    public String getAttack(){
        return attack;
    }
}
