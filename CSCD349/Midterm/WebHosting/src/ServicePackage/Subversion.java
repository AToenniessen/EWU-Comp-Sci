package ServicePackage;

public class Subversion extends ExtraService {
    private final Service service;
    public Subversion(Service s){
        this.service = s;
    }
    public String getDescription(){
        return service.getDescription() + ", Subversion Hosting";
    }
    public double cost(){
        return 19.99 + service.cost();
    }
}
