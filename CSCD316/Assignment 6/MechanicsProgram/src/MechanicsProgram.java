import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

//Alex Toenniessen
public class MechanicsProgram {
    private static ArrayList<NodeP> Prices = new ArrayList<>();
    private static ArrayList<NodeB> Bills = new ArrayList<>();
    public static void main(String...args)throws IOException{
        BufferedReader Fone = openFile("InputOne.txt");
        BufferedReader Ftwo = openFile("InputTwo.txt");
        readPrice(Ftwo);
        readBill(Fone);
        calcBill();
    }
    private static void calcBill(){
        for(NodeB tempB : Bills){
            for(NodeP p : Prices){
                if(p.contains(tempB.job)){
                    tempB.jPrice = p.price;
                    break;
                }
            }
            tempB.Total = tempB.Hours * tempB.jPrice;
            tempB.PriceATax = tempB.Total + (.08 * tempB.Total);
        }
        printBill();
    }
    private static void printBill(){
        for(NodeB b : Bills){
            System.out.printf("Customer name:    " + b.name+ "\nDate:    " + b.date + "\nWork Done:    " + b.job
                    + "\nHours Worked:    %.2f"  + "\nPrice per Hour:    $%.2f" +  "\nTotal Cost before Tax:    $%.2f"
                    + "\nTotal Cost after Tax:    $ %.2f\n\n",b.Hours, b.jPrice, b.Total, b.PriceATax);
        }
    }
    private static void readBill(BufferedReader f)throws IOException{
        while (f.ready()){
            String name = f.readLine();
            String d = f.readLine();
            String j = f.readLine();
            double h = Double.parseDouble(f.readLine());
            NodeB n = new NodeB(name,d,j,h);
            Bills.add(n);
        }
    }
    private static void readPrice(BufferedReader f)throws IOException{
        while(f.ready()){
            String s = f.readLine();
            double i = Double.parseDouble(f.readLine());
            NodeP n = new NodeP(s,i);
            Prices.add(n);
        }
    }
    private static BufferedReader openFile(String f){
        try{
            return new BufferedReader(new FileReader(f));
        }catch(FileNotFoundException e){
            e.getMessage();
        }
        return null;
    }
    public static class NodeB{
        String name;
        String date;
        String job;
        double jPrice;
        double Hours;
        double Total;
        double PriceATax;
        private NodeB(String n, String d, String j, double h){
            name = n;
            date = d;
            job = j;
            Hours = h;
        }
    }
    public static class NodeP{
        String job;
        double price;
        private NodeP(String s, double i){
            job = s;
            price = i;
        }
        private boolean contains(String s){
            return this.job.compareTo(s) == 0;
        }
    }
}
