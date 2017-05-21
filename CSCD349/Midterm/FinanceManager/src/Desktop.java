import Accounts.Accounts;

public class Desktop extends View{
    public void display(){
        // Display stuff on Desktop view
        System.out.println("*Obviously Desktop View*");
        for(Accounts account : accounts){
            System.out.println(account.getAccountName());
            System.out.printf("Balance: %.2f", account.getBalance());
        }
    }
}
