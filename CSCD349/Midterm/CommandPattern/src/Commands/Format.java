package Commands;

public class Format implements Command {
    private HardDrive hdd;
    public Format(HardDrive hd){
        this.hdd = hd;
    }
    public void execute(){
        System.out.println("Formatting...");
        int completion = estimatedDuration();
        while(completion > 0)
            completion--;
        hdd.setFormat();
    }
    public int estimatedDuration(){
        return 1000000000;
    }
}
