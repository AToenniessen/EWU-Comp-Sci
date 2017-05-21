import Dungeon.*;
import Dungeon.DungeonCharacter.DungeonCharacter;
import Dungeon.DungeonCharacter.DungeonCharacterFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Dungeon {
    private static Scanner Keyboard = new Scanner(System.in);
    private static DungeonCharacter[] theHero;
    private static DungeonCharacter[] theMonster;

    public static void main(String[] args) {
        do {
            startGame();
        }while(playAgain());
        System.out.println("Ending game");
    }//end main method
    private static void startGame(){
        System.out.println("Would you like to load an existing game? (y/n)");
        String c = Keyboard.next();
        Keyboard.nextLine();
        if (c.charAt(0) == 'y') {
            CareTaker.load();
            ArrayList<Memento> heros = CareTaker.getHero();
            ArrayList<Memento> monsters = CareTaker.getMonster();
            popCharacters(heros, monsters);
            if (theHero.length == 0 || theMonster.length == 0)
                c = "n";
            else {
                battle(theHero, theMonster);
            }
        }
        if (c.charAt(0) == 'n') {
            theHero = chooseHero();
            theMonster = generateMonster(theHero.length);
            battle(theHero, theMonster);
        }
    }
    private static void popCharacters(ArrayList<Memento> h, ArrayList<Memento> m){
        theHero = new DungeonCharacter[h.size()];
        theMonster = new DungeonCharacter[m.size()];
        int i = 0;
        for(Memento t : h){
            theHero[i] = t.getSavedState();
            i++;
        }
        i = 0;
        for(Memento t : m){
            theMonster[i] = t.getSavedState();
            i++;
        }
    }
    private static int readInt(){
        int t;
            try{
                t = Keyboard.nextInt();
            }catch(Exception e){
                Keyboard.next();
                return readInt();
            }
        Keyboard.nextLine();
        return t;
    }

    private static DungeonCharacter[] chooseHero() {
        System.out.print("Please enter how many characters on your team you would like to play with\n");
        int n = readInt();
        int[] choice = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nChoose a hero:\n" +
                    "1. Warrior\n" +
                    "2. Sorceress\n" +
                    "3. Thief\n" +
                    "4. Archer\n" +
                    "5. Marauder\n");
            choice[i] = readInt();
        }
        int[] basics = new int[n];
        for (int i = 1; i <= n; i++) {
            System.out.println("\nChoose hero " + i + "'s basic weapon:\n" +
                    "1. Rusty Blade\n" +
                    "2. Wood Club\n" +
                    "3. Dagger\n" +
                    "4. Mighty Sword\n" +
                    "5. FireBall\n" +
                    "6. Arrow\n" +
                    "7. Greatsword\n" +
                    "8. Kris\n");
            basics[i - 1] = readInt();
        }
        int[] specials = new int[n];
        for (int i = 1; i <= n; i++) {
            System.out.println("\nChoose hero " + i + "'s Special move:\n" +
                    "1. Crushing Blow\n" +
                    "2. Heal\n" +
                    "3. Surprise Attack\n");
            specials[i - 1] = readInt();
        }
        return DungeonCharacterFactory.createHero(choice, basics, specials);
    }//end chooseHero method

    private static DungeonCharacter[] generateMonster(int n) {
        int[] choice = new int[n];
        for (int i = 0; i < n; i++) {
            choice[i] = (int) (Math.random() * 4);
        }
        return DungeonCharacterFactory.createMonster(choice);
    }//end generateMonster method

    private static boolean playAgain() {
        char again = 'o';
        while(again != 'y' && again != 'Y' && again != 'n') {
            System.out.println("Play again (y/n)?");
            again = Keyboard.next().charAt(0);
        }
        return (again == 'Y' || again == 'y');
    }//end playAgain method

    private static int select(DungeonCharacter[] choice){
        int n = 1;
        for(DungeonCharacter cur : choice) {
            if (cur.isAlive())
                System.out.println(n + ") " + cur.getName());
            else
                System.out.println(n + ") DEAD");
            n++;
        }
        System.out.println();
        n = readInt() - 1;
        if(!choice[n].isAlive())
            return select(choice);
        return n;
    }
    private static void battle(DungeonCharacter[] theHero, DungeonCharacter[] theMonster) {
        char pause;
        //do battle
        DungeonCharacter hero, monster;
        do{
            System.out.println("\nPlease select a Hero to attack with");
            int h = select(theHero);
            hero = theHero[h];
            System.out.println("\nPlease select a Monster to attack");
            int m = select(theMonster);
            monster = theMonster[m];
            System.out.println(hero.getName() + " battles " +
                    monster.getName());
            System.out.println("---------------------------------------------");
            //hero goes first
            hero.battleChoices(monster);

            //monster's turn (provided it's still alive!)
            if (monster.isAlive())
                monster.attack(hero);

            //let the player bail out if desired
            System.out.print("\n-->q to save and quit, anything else to continue: ");
            pause = Keyboard.next().charAt(0);

        }while(Alive(theHero) && Alive(theMonster) && pause != 'q');//end battle loop

        if(pause == 'q'){//both are alive so user quit the game
            System.out.println("Saving game");
            saveGame();
        }
        else if (!monster.isAlive())
            System.out.println("Your team was victorious!");
        else if (!hero.isAlive())
            System.out.println("Your team was defeated :-(");
    }//end battle method
    private static void saveGame(){
        if(CareTaker.isEmpty()) {
            for (int i = 0; i < theHero.length; i++) {
                CareTaker.add(new Memento(theHero[i]), new Memento(theMonster[i]));
            }
        }
        CareTaker.save();
    }

    private static boolean Alive(DungeonCharacter[] cur){
        int alive = 0;
        for(DungeonCharacter n : cur){
            if(n.isAlive())
                alive++;
        }
        return alive > 0;
    }
}//end Dungeon class