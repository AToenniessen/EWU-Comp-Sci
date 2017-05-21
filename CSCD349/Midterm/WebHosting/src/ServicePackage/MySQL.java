package ServicePackage;

public class MySQL extends ExtraService {
    private int numDataBase = 0;
    private final Service service;
    public MySQL(Service s, int i){
        this.service = s;
        this.numDataBase = i;
    }
    public String getDescription(){
        return service.getDescription() + ", MySQL Hosting(with " + this.numDataBase + " databases)";
    }
    public double cost(){
        return (numDataBase * 4.99) + service.cost();
    }
}
