import ServicePackage.*;

public class ServiceTester {
    public static void main(String[] args) {
        Service myService = new BasicService();
        myService = new Subversion(myService);
        myService = new FlashMedia(myService);
        myService = new MySQL(myService, 3);
        printBill(myService);
    }
    private static void printBill(Service service){
        System.out.printf(service.getDescription() + "\nTotal Cost: $%.2f\n\n", service.cost());
    }
}
