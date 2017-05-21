package Commands;


public class unpack implements Command {
    private HardDrive hdd;
    public unpack(HardDrive hd){
        this.hdd = hd;
    }
    public void execute(){
        System.out.println("Unpacking...");
        int completion = estimatedDuration();
        while(completion > 0)
            completion--;
        hdd.setUnpack();
    }
    public int estimatedDuration(){
        return 1000000000;
    }
}
