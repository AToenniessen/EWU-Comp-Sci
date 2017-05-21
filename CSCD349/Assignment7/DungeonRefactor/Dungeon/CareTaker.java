//Code written by Alex Toenniessen and Jared Regan
package Dungeon;

import java.io.*;
import java.util.ArrayList;

public class CareTaker {
    private static ArrayList<Memento> heros = new ArrayList<>();
    private static ArrayList<Memento> monsters = new ArrayList<>();
    public static void add(Memento h, Memento m){
        heros.add(h);
        monsters.add(m);
    }
    public static boolean isEmpty(){
        return heros.isEmpty() && monsters.isEmpty();
    }
    public static ArrayList<Memento> getHero(){
        return heros;
    }
    public static ArrayList<Memento> getMonster(){
        return monsters;
    }
    public static void save(){
        try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("GameState.txt", false));
                out.writeObject(heros);
                out.writeObject(monsters);
                out.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static void load(){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("GameState.txt"));
            heros = (ArrayList<Memento>) in.readObject();
            monsters = (ArrayList<Memento>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("No Saved File at this time");
        }
    }
}
