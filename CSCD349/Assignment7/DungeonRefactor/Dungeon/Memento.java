//Code written by Alex Toenniessen and Jared Regan
package Dungeon;

import Dungeon.DungeonCharacter.DungeonCharacter;

import java.io.Serializable;

public class Memento implements Serializable{
    private final DungeonCharacter state;

    public Memento(DungeonCharacter c){
        state = c;
    }
    public DungeonCharacter getSavedState(){
        return state;
    }
}
