import Accounts.*;



public class Tester {
    public static void main(String[] args) {
        View d = new Desktop();
        Checking checking = new Checking("Chase Checking", 165.50);
        Savings savings = new Savings("Chase Savings", 485.20);
        CreditCard card1 = new CreditCard("Discover", 234.34);
        CreditCard card2 = new CreditCard("Amazon", 454.34);
        d.addAccount(checking);
        d.addAccount(savings);
        d.addAccount(card1);
        d.addAccount(card2);
        d.display();
        savings.setBalance(savings.getBalance() + 234.50);
    }
}
