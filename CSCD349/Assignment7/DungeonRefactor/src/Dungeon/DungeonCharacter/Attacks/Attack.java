package Dungeon.DungeonCharacter.Attacks;

import Dungeon.DungeonCharacter.DungeonCharacter;

public interface Attack {
    void attack(DungeonCharacter attacker, DungeonCharacter defender);
    String getAttack();
}
