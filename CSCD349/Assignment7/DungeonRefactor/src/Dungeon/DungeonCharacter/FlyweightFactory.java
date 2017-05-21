package Dungeon.DungeonCharacter;

import Dungeon.DungeonCharacter.Attacks.Attack;
import Dungeon.DungeonCharacter.Attacks.basicAttacks.AttackType;


import java.util.HashMap;
import java.util.Map;

class FlyweightFactory {
    private static Map<String, Attack> attacks = new HashMap<>();
    private static Map<String, Attack> specials = new HashMap<>();
    private static Attack getAttack(String attackName){
        Attack attack = attacks.get(attackName);
        if(attack == null){
            attack = new AttackType(attackName);
            attacks.put(attackName, attack);
        }
        return attack;
    }
    private static Attack getSpecial(String specialName, Attack move){
        Attack attack = specials.get(specialName);
        if(attack == null){
            attack = new Dungeon.DungeonCharacter.Attacks.SpecialMoves.SpecialType(specialName, move);
            specials.put(specialName, attack);
        }
        return attack;
    }
    static Attack createAttack(int i){
        switch(i){
            case(1):
                return getAttack(" slices their rusty blade at ");
            case(2):
                return getAttack(" slowly swings a club toward's ");
            case(3):
                return getAttack(" slices with their dagger ");
            case(4):
                return getAttack(" swings a mighty sword at ");
            case(5):
                return getAttack(" casts a spell of fireball at ");
            case(6):
                return getAttack(" shoots his arrow at ");
            case(7):
                return getAttack(" swings his greatsword at ");
            case(8):
                return getAttack(" jabs his kris at ");
            default:
                return getAttack(" sissy slaps ");
        }
    }
    static Attack createSpecial(int i){
        switch (i){
            case(1):
                return getSpecial("Crushing Blow", new Dungeon.DungeonCharacter.Attacks.SpecialMoves.CrushingBlow());
            case(2):
                return getSpecial("Increase Hit Points", new Dungeon.DungeonCharacter.Attacks.SpecialMoves.IncreaseHP());
            case(3):
                return getSpecial("Surprise Attacks", new Dungeon.DungeonCharacter.Attacks.SpecialMoves.SupriseAttack());
            default:
                return getSpecial("420 blaze it", new Dungeon.DungeonCharacter.Attacks.SpecialMoves.BlazeIt());
        }
    }
}
