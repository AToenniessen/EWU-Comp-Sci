package Commands;


public class defragement implements Command {
    private HardDrive hdd;
    public defragement(HardDrive hd){
        this.hdd = hd;
    }
    public void execute(){
        System.out.println("Defragmenting...");
        int completion = estimatedDuration();
        while(completion > 0)
            completion--;
        hdd.setDefragment();
    }
    public int estimatedDuration(){
        return 1000000000;
    }
}
