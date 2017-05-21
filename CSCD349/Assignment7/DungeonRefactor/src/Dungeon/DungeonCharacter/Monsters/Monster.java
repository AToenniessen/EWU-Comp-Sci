package Dungeon.DungeonCharacter.Monsters;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.DungeonCharacter;

public class Monster extends DungeonCharacter
{
    private double chanceToHeal;
    private int minHeal, maxHeal;

    public Monster(String name, int hitPoints, int attackSpeed, double chanceToHit, double chanceToHeal, int damageMin, int damageMax, int minHeal, int maxHeal, Attack basic)
    {
        super(name, hitPoints, attackSpeed, chanceToHit, damageMin, damageMax, basic, null);
        this.chanceToHeal = chanceToHeal;
        this.maxHeal = maxHeal;
        this.minHeal = minHeal;
    }

    private void heal()
    {
        boolean canHeal;
        int healPoints;

        canHeal = (Math.random() <= chanceToHeal) && (getHitPoints() > 0);

        if (canHeal)
        {
            healPoints = (int)(Math.random() * (maxHeal - minHeal + 1)) + minHeal;
            addHitPoints(healPoints);
            System.out.println(this.getName() + " healed itself for " + healPoints + " points.\n"
                    + "Total hit points remaining are: " + getHitPoints());
            System.out.println();
        }
    }
    public void subtractHitPoints(int hitPoints)
    {
        super.subtractHitPoints(hitPoints);
        heal();
    }
}