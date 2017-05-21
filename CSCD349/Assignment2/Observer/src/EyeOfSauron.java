import java.util.Observable;

class EyeOfSauron extends Observable {
    private int hobbits, elves, dwarves, men;
    void setEnemies(int h, int e, int d, int m){
        this.hobbits = h;
        this.elves = e;
        this.dwarves = d;
        this.men = m;
        setChanged();
        notifyObservers(new int[] {hobbits, elves, dwarves, men});
        clearChanged();
    }
}
