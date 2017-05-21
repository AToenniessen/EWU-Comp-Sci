package Commands;


public class install implements Command {
    private HardDrive hdd;
    public install(HardDrive hd){
        this.hdd = hd;
    }
    public void execute(){
        System.out.println("Installing...");
        int completion = estimatedDuration();
        while(completion > 0)
            completion--;
        hdd.setInstall();
    }
    public int estimatedDuration(){
        return 1000000000;
    }
}
