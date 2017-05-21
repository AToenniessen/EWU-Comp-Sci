import java.util.Observable;
import java.util.Observer;

class BadGuy implements Observer{
    private Observable observed;
    private String name;
    BadGuy(EyeOfSauron e, String s){
        this.observed = e;
        this.name = s;
        this.observed.addObserver(this);
    }
    @Override
    public void update(Observable o, Object obj){
        if(obj instanceof int[]){
            int[] temp = (int[]) obj;
            System.out.printf(name + " now knows about these enemies in the realm:\n" +
                    "Hobbits: %d\nElves: %d\nDwarfs: %d\nHumans: %d\n",
                    temp[0],temp[1],temp[2],temp[3]);
        }
    }
    void defeated(){
        observed.deleteObserver(this);
    }
}
