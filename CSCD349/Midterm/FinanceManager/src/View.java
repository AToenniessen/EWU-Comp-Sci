import Accounts.Accounts;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public abstract class View implements Observer{
    HashSet<Accounts> accounts;

    View() {
        accounts = new HashSet<Accounts>();
    }
    public abstract void display();
    void addAccount(Accounts account) {
        accounts.add(account);
        account.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        Double newBalance = (Double) arg;
        Accounts account = (Accounts) o;
        account.setBalance(newBalance);
        accounts.add(account);
        display();
    }
}
