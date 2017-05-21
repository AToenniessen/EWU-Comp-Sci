package Dungeon.DungeonCharacter.Heros;
import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

import java.util.Scanner;

public class Hero extends DungeonCharacter {
    private double chanceToBlock;

    public Hero(String name, int hitPoints, int attackSpeed, double chanceToHit, int damageMin, int damageMax, double chanceToBlock, Attack attack, Attack specialAttack) {

        super(name, hitPoints, attackSpeed, chanceToHit, damageMin, damageMax, attack, specialAttack);
        this.chanceToBlock = chanceToBlock;
        readName();
    }

    private void readName() {
        Scanner Keyboard = new Scanner(System.in);
        System.out.print("Enter character name: ");
        this.setName(Keyboard.nextLine());
    }

    private boolean defend() {
        return Math.random() <= chanceToBlock;

    }

    public void subtractHitPoints(int hitPoints) {
        if (defend()) {
            System.out.println(this.getName() + " BLOCKED the attack!");
        } else {
            super.subtractHitPoints(hitPoints);
        }
    }
}