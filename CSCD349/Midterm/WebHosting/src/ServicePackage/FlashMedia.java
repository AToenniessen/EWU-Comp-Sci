package ServicePackage;

public class FlashMedia extends ExtraService {
    private final Service service;
    public FlashMedia(Service s){
        this.service = s;
    }
    public String getDescription(){
        return service.getDescription() + ", Flash Media Server Hosting";
    }
    public double cost(){
        return 14.98 + service.cost();
    }
}
