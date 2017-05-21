package Commands;


public class copy implements Command {
    private HardDrive hdd;
    public copy(HardDrive hd){
        this.hdd = hd;
    }
    public void execute(){
        System.out.println("Copying...");
        int completion = estimatedDuration();
        while(completion > 0)
            completion--;
        hdd.setCopy();
    }
    public int estimatedDuration(){
        return 1000000000;
    }
}
