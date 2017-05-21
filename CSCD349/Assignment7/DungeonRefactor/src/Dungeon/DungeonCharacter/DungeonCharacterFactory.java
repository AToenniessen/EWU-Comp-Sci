package Dungeon.DungeonCharacter;

public class DungeonCharacterFactory {
    public static DungeonCharacter[] createHero(int[] n, int[] b, int[] s) {
        DungeonCharacter[] heros = new DungeonCharacter[n.length];
        for(int m = 0; m < n.length; m++) {
            switch (n[m]) {
                case (1):
                    heros[m] = new Dungeon.DungeonCharacter.Heros.Hero("the Warrior", 125, 4, .8, 35, 60, .2, FlyweightFactory.createAttack(b[m]), FlyweightFactory.createSpecial(s[m]));
                    break;
                case (2):
                    heros[m] = new Dungeon.DungeonCharacter.Heros.Hero("the Sorceress", 75, 5, .7, 25, 50, .3, FlyweightFactory.createAttack(b[m]), FlyweightFactory.createSpecial(s[m]));
                    break;
                case (3):
                    heros[m] = new Dungeon.DungeonCharacter.Heros.Hero("the Thief", 75, 6, .8, 20, 40, .5, FlyweightFactory.createAttack(b[m]), FlyweightFactory.createSpecial(s[m]));
                    break;
                case (4):
                    heros[m] = new Dungeon.DungeonCharacter.Heros.Hero("the Archer", 100, 6, .6, 35, 80, .3, FlyweightFactory.createAttack(b[m]), FlyweightFactory.createSpecial(s[m]));
                    break;
                case (5):
                    heros[m] = new Dungeon.DungeonCharacter.Heros.Hero("the Marauder", 150, 4, .7, 25, 70, .6, FlyweightFactory.createAttack(b[m]), FlyweightFactory.createSpecial(s[m]));
                    break;
                default:
                    System.out.println("invalid choice, returning Thief");
                    heros[m] = new Dungeon.DungeonCharacter.Heros.Hero("the Thief", 75, 6, .8, 20, 40, .5, FlyweightFactory.createAttack(b[m]), FlyweightFactory.createSpecial(s[m]));
                    break;
            }
        }
        return heros;
    }
    public static DungeonCharacter[] createMonster(int[] n) {
        DungeonCharacter[] monsters = new DungeonCharacter[n.length];
        for(int i = 0; i < n.length; i++) {
            switch (n[i]) {
                case (1):
                    monsters[i] =  new Dungeon.DungeonCharacter.Monsters.Monster("Oscar the Ogre", 200, 2, .6, .1, 30, 50, 30, 50, FlyweightFactory.createAttack((int) (Math.random() * 7)));
                    break;
                case (2):
                    monsters[i] =  new Dungeon.DungeonCharacter.Monsters.Monster("Gnarltooth the Gremlin", 70, 5, .8, .4, 15, 30, 20, 40, FlyweightFactory.createAttack((int) (Math.random() * 7)));
                    break;
                case (3):
                    monsters[i] =  new Dungeon.DungeonCharacter.Monsters.Monster("Sargath the Skeleton", 100, 3, .8, .3, 30, 50, 30, 50, FlyweightFactory.createAttack((int) (Math.random() * 7)));
                    break;
                case (4):
                    monsters[i] =  new Dungeon.DungeonCharacter.Monsters.Monster("Phogoth the Omega", 500, 2, .7, .1, 30, 60, 20, 40, FlyweightFactory.createAttack((int) (Math.random() * 7)));
                    break;
                case (5):
                    monsters[i] =  new Dungeon.DungeonCharacter.Monsters.Monster("Sylok the Defiled", 115, 4, .6, .3, 40, 60, 40, 60, FlyweightFactory.createAttack((int) (Math.random() * 7)));
                    break;
                default:
                    monsters[i] =  new Dungeon.DungeonCharacter.Monsters.Monster("Sargath the Skeleton", 100, 3, .8, .3, 30, 50, 30, 50, FlyweightFactory.createAttack((int) (Math.random() * 7)));
                    break;
            }
        }
        return monsters;
    }
}
