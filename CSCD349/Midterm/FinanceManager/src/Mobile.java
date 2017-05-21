import Accounts.*;

public class Mobile extends View{
    public void display(){
        // Display stuff on Mobile view
        System.out.println("*Obviously Mobile View*");
        for(Accounts account : accounts){
            System.out.println(account.getAccountName());
            System.out.printf("Balance: %.2f", account.getBalance());
        }
    }
}
